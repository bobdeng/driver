package cn.bobdeng.line.driver.server.lineup.facade;

import cn.bobdeng.discomput.lock.Lock;
import cn.bobdeng.line.business.domain.BusinessRepository;
import cn.bobdeng.line.counter.domain.Counter;
import cn.bobdeng.line.counter.domain.CounterRepository;
import cn.bobdeng.line.driver.domain.*;
import cn.bobdeng.line.orgnization.domain.OrgRepository;
import cn.bobdeng.line.orgnization.domain.Orgnization;
import cn.bobdeng.line.queue.domain.queue.Queue;
import cn.bobdeng.line.queue.domain.queue.QueueRepository;
import cn.bobdeng.line.queue.domain.queue.QueueService;
import cn.bobdeng.line.userclient.UserDTO;
import com.tucodec.utils.Assert;
import com.tucodec.utils.BeanCopier;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LineupServiceFacadeImpl implements LineupServiceFacade {
    @Autowired
    OrgRepository orgnizationRepository;
    @Autowired
    QueueService queueService;
    @Autowired
    QueueRepository queueRepository;
    @Autowired
    DriverRepository driverRepository;
    @Autowired
    DriverService driverService;
    @Autowired
    BusinessRepository businessRepository;
    @Autowired
    CounterRepository counterRepository;
    @Autowired
    TruckRepository truckRepository;

    @Override
    public List<OrgVO> findOrgs(String mobile) {
        return driverRepository.findDriverByMobile(mobile)
                .map(driver -> orgnizationRepository.findById(driver.getOrgId()).orElse(null))
                .filter(orgnization -> orgnization != null)
                .map(this::orgToVO).collect(Collectors.toList());
    }

    private OrgVO orgToVO(Orgnization orgnization) {
        return OrgVO.builder()
                .id(orgnization.getId())
                .name(orgnization.getName())
                .maxDistance(orgnization.getMaxDistance())
                .location(parseLocation(orgnization.getLocation()))
                .build();
    }

    private Location parseLocation(String location) {
        try {
            String[] locations = location.split(",");
            return Location.builder()
                    .longitude(Double.parseDouble(locations[0]))
                    .latitude(Double.parseDouble(locations[1]))
                    .build();
        } catch (Exception e) {

        }
        return Location.builder().build();
    }

    @Override
    @Lock(value = "'queue_lock_'.concat(#orgId)", timeout = 60000)
    public List<QueueVO> listQueue(UserDTO user, int orgId) {
        Map<Integer, String> businesses = businessRepository.listBusiness(orgId)
                .stream()
                .collect(Collectors.toMap(cn.bobdeng.line.business.domain.Business::getId, cn.bobdeng.line.business.domain.Business::getName));
        Map<Integer, String> counters = counterRepository.findCounterByOrg(orgId)
                .stream()
                .collect(Collectors.toMap(Counter::getId, Counter::getName));
        return queueService.getOrgQueue(orgId)
                .getQueues()
                .stream()
                .map(this::queueToVO)
                .peek(queueVO -> queueVO.setMe(user.getId() == queueVO.getUserId()))
                .peek(queueVO -> queueVO.setCounterName(counters.getOrDefault(queueVO.getCounterId(), "")))
                .peek(queueVO -> queueVO.setBusinessName(businesses.getOrDefault(queueVO.getBusinessId(), "")))
                .collect(Collectors.toList());

    }

    private QueueVO queueToVO(Queue queue) {
        QueueVO queueVO = new QueueVO();
        BeanUtils.copyProperties(queue, queueVO);
        queueVO.setMobile(encryptMobile(queueVO.getMobile()));
        return queueVO;
    }

    private String encryptMobile(String mobile) {
        return mobile.substring(0, 3) + "****" + mobile.substring(mobile.length() - 4);
    }

    @Override
    @Transactional
    @Lock(value = "'queue_lock_'.concat(#orgId)", timeout = 60000)
    public EnQueueResult enqueue(UserDTO user, int orgId, EnqueueForm enqueueForm) {
        Truck truck = getTruck(orgId, enqueueForm);
        checkOrgnization(orgId, truck);
        Driver driver = driverRepository.findDriverByMobile(orgId, user.getMobile()).get();
        setDriverLastUseTruck(truck, driver);
        Queue queue = buildQueue(user, orgId, enqueueForm, truck, driver);
        queueService.join(queue);
        return EnQueueResult.builder().before(0).queue(queueToVO(queue)).build();
    }

    private Queue buildQueue(UserDTO user, int orgId, EnqueueForm enqueueForm, Truck truck, Driver driver) {
        return Queue.builder()
                    .orgId(orgId)
                    .userId(user.getId())
                    .internalNumber(truck.getInternalNumber())
                    .number(truck.getNumber())
                    .operatorId(user.getId())
                    .name(driver.getName())
                    .mobile(user.getMobile())
                    .businessId(enqueueForm.getBusinessId())
                    .beginTime(System.currentTimeMillis())
                    .build();
    }

    private void setDriverLastUseTruck(Truck truck, Driver driver) {
        driver.setLastTruck(truck);
        driverService.setLastTruck(driver);
    }

    private void checkOrgnization(int orgId, Truck truck) {
        Orgnization orgnization = orgnizationRepository.findById(orgId).get();
        orgnization.checkExpire(() -> new RuntimeException("企业账号已经过期，请联系客服"));
        if (!orgnization.getConfig().getDriverConfig().isAllowEmpty()) {
            Assert.assertNotNull(truck.getNumber(), "必须输入车牌号");
        }
    }

    private Truck getTruck(int orgId, EnqueueForm enqueueForm) {
        return truckRepository.findById(enqueueForm.getTruckId(), orgId).orElse(Truck.builder()
                    .internalNumber(enqueueForm.getInternalNumber())
                    .number(enqueueForm.getNumber())
                    .build());
    }

    @Override
    public List<BusinessVO> listBusiness(int orgId) {
        return businessRepository.listBusiness(orgId)
                .stream()
                .map(business -> BusinessVO.builder()
                        .id(business.getId())
                        .name(business.getName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public DriverVO getDriver(UserDTO user, int orgId) {
        Driver driver = driverRepository.findDriverByMobile(orgId, user.getMobile()).get();
        DriverVO driverVO = BeanCopier.copyFrom(driver, DriverVO.class);
        Orgnization orgnization = orgnizationRepository.findById(orgId).get();
        List<TruckVO> trucks = driverRepository.getDriverTrucks(driver)
                .map(truck -> BeanCopier.copyFrom(truck, TruckVO.class))
                .collect(Collectors.toList());
        if (driver.getLastTruck() != null) {
            driverVO.setLastTruck(BeanCopier.copyFrom(driver.getLastTruck(), TruckVO.class));
        }
        driverVO.setTrucks(trucks);
        driverVO.setAllowEmpty(orgnization.getConfig().getDriverConfig().isAllowEmpty());
        driverVO.setAllowInput(orgnization.getConfig().getDriverConfig().isAllowInput());
        return driverVO;
    }
}
