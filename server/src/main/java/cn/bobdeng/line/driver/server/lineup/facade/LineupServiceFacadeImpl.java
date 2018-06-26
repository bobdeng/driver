package cn.bobdeng.line.driver.server.lineup.facade;

import cn.bobdeng.line.driver.domain.org.Orgnization;
import cn.bobdeng.line.driver.domain.org.OrgnizationRepository;
import cn.bobdeng.line.driver.domain.queue.Queue;
import cn.bobdeng.line.driver.domain.queue.QueueRepository;
import cn.bobdeng.line.driver.domain.queue.QueueService;
import cn.bobdeng.line.userclient.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LineupServiceFacadeImpl implements LineupServiceFacade {
    @Autowired
    OrgnizationRepository orgnizationRepository;
    @Autowired
    QueueService queueService;
    @Autowired
    QueueRepository queueRepository;

    @Override
    public List<OrgVO> findOrgs(String mobile) {
        return orgnizationRepository.findByDriver(mobile)
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
    public List<QueueVO> listQueue(int orgId) {
        Map<Integer, String> businessMap = orgnizationRepository.listBusiness(orgId)
                .collect(Collectors.toMap(business -> business.getId(), b -> b.getName()));
        return queueRepository.getOrgQueue(orgId)
                .stream()
                .map(this::queueToVO)
                .peek(queueVO -> queueVO.setBusiness(businessMap.getOrDefault(queueVO.getBusinessId(),"无")))
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
        Queue queue = Queue.builder()
                .orgId(orgId)
                .userId(user.getId())
                .internalNumber(enqueueForm.getInternalNumber())
                .number(enqueueForm.getNumber())
                .name(enqueueForm.getName())
                .mobile(enqueueForm.getMobile())
                .businessId(enqueueForm.getBusinessId())
                .beginTime(System.currentTimeMillis())
                .build();
        int before = queueService.joinQueue(queue);
        return EnQueueResult.builder().before(before).queue(queueToVO(queue)).build();
    }

    @Override
    public List<BusinessVO> listBusiness(int orgId) {
        return orgnizationRepository.listBusiness(orgId)
                .map(business -> BusinessVO.builder()
                        .id(business.getId())
                        .name(business.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
