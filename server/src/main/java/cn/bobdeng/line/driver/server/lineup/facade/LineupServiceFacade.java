package cn.bobdeng.line.driver.server.lineup.facade;

import cn.bobdeng.line.userclient.UserDTO;

import java.util.List;

public interface LineupServiceFacade {
    List<OrgVO> findOrgs(String mobile);

    List<QueueVO> listQueue(int orgId);

    EnQueueResult enqueue(UserDTO user, int orgId, EnqueueForm enqueueForm);

    List<BusinessVO> listBusiness(int orgId);

    DriverVO getDriver(UserDTO user, int orgId);
}
