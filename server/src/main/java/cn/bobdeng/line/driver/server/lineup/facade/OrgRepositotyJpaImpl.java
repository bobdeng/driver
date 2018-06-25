package cn.bobdeng.line.driver.server.lineup.facade;

import cn.bobdeng.line.db.OrgnizationDO;
import cn.bobdeng.line.driver.domain.org.Orgnization;
import cn.bobdeng.line.driver.domain.org.OrgnizationRepository;
import cn.bobdeng.line.driver.server.dao.DriverDAO;
import cn.bobdeng.line.driver.server.dao.OrgnizationDAO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class OrgRepositotyJpaImpl implements OrgnizationRepository {
    @Autowired
    DriverDAO driverDAO;
    @Autowired
    OrgnizationDAO orgnizationDAO;

    @Override
    public Stream<Orgnization> findByDriver(String mobile) {
        return driverDAO.findByMobile(mobile)
                .stream()
                .map(driver -> orgnizationDAO.findOne(driver.getOrgId()))
                .map(this::orgDOtoOrgnization);
    }

    private Orgnization orgDOtoOrgnization(OrgnizationDO orgnizationDO) {
        Orgnization orgnization = new Orgnization();
        BeanUtils.copyProperties(orgnizationDO, orgnization);
        return orgnization;
    }
}
