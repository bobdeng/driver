package cn.bobdeng.line.driver.server.lineup.facade;

import cn.bobdeng.line.business.domain.BusinessRepository;
import cn.bobdeng.line.driver.domain.Driver;
import cn.bobdeng.line.driver.domain.DriverRepository;
import cn.bobdeng.line.driver.domain.Truck;
import cn.bobdeng.line.driver.domain.TruckRepository;
import cn.bobdeng.line.orgnization.domain.OrgRepository;
import cn.bobdeng.line.orgnization.domain.Orgnization;
import cn.bobdeng.line.queue.domain.queue.Queue;
import cn.bobdeng.line.queue.domain.queue.QueueRepository;
import cn.bobdeng.line.queue.domain.queue.QueueService;
import cn.bobdeng.line.userclient.UserDTO;
import com.tucodec.utils.BeanCopier;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    BusinessRepository businessRepository;
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
    public List<QueueVO> listQueue(UserDTO user, int orgId) {
        return queueService.getOrgQueue(orgId)
                .getQueues()
                .stream()
                .map(this::queueToVO)
                .peek(queueVO -> queueVO.setMe(user.getId() == queueVO.getUserId()))
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
    public EnQueueResult enqueue(UserDTO user, int orgId, EnqueueForm enqueueForm) {
        Truck truck = truckRepository.findById(enqueueForm.getTruckId(), orgId).get();
        Driver driver = driverRepository.findDriverByMobile(orgId, user.getMobile()).get();

        Queue queue = Queue.builder()
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
        int before = queueService.join(queue);
        return EnQueueResult.builder().before(before).queue(queueToVO(queue)).build();
    }

    @Override
    public List<BusinessVO> listBusiness(int orgId) {
        return businessRepository.listBusiness(orgId)
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
        List<TruckVO> trucks = driverRepository.getDriverTrucks(driver)
                .map(truck -> BeanCopier.copyFrom(truck, TruckVO.class))
                .collect(Collectors.toList());
        driverVO.setTrucks(trucks);
        return driverVO;
    }
}
