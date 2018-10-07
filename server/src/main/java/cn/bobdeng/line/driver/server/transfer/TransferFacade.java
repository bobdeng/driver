package cn.bobdeng.line.driver.server.transfer;


import cn.bobdeng.line.driver.server.lineup.facade.DriverVO;
import cn.bobdeng.line.userclient.UserDTO;

import java.util.List;

public interface TransferFacade {
    void transfer(UserDTO user,int orgId,String mobile);
    List<TransferDriverVO> getDriversCanTransfer(UserDTO userDTO,int orgId);
}
