package cn.bobdeng.line.driver.server.transfer;

import cn.bobdeng.discomput.lock.Lock;
import cn.bobdeng.line.driver.domain.Driver;
import cn.bobdeng.line.driver.domain.DriverRepository;
import cn.bobdeng.line.driver.server.lineup.facade.DriverVO;
import cn.bobdeng.line.queue.domain.queue.QueueService;
import cn.bobdeng.line.userclient.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferFacadeImpl implements TransferFacade {
    @Autowired
    QueueService queueService;
    @Autowired
    DriverRepository driverRepository;

    @Override
    @Lock(value = "'queue_lock_'.concat(#orgId)", timeout = 60000)
    public void transfer(UserDTO user, int orgId, String mobile) {
        Driver targetDriver = driverRepository.findDriverByMobile(orgId, mobile)
                .orElseThrow(() -> new RuntimeException("没有这个司机"));
        Driver fromDriver = driverRepository.findDriverByMobile(orgId, user.getMobile())
                .orElseThrow(() -> new RuntimeException("无权限"));
        queueService.transfer(fromDriver.getId(), targetDriver.getId());
    }

    @Override
    public List<TransferDriverVO> getDriversCanTransfer(UserDTO userDTO, int orgId) {
        return driverRepository.searchDriver("", orgId)
                .map(driver -> TransferDriverVO.builder()
                        .name(driver.getName())
                        .mobile(driver.getMobile())
                        .build())
                .collect(Collectors.toList());
    }
}
