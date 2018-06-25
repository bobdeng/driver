package cn.bobdeng.line.driver.server.lineup.facade;

import java.util.List;

public interface LineupServiceFacade {
    List<OrgVO> findOrgs(String mobile);
}
