package cn.bobdeng.line.driver.server.lineup;

import cn.bobdeng.line.db.OrgnizationDO;
import cn.bobdeng.line.driver.domain.org.Business;
import cn.bobdeng.line.driver.domain.org.Orgnization;
import cn.bobdeng.line.driver.domain.org.OrgnizationRepository;
import cn.bobdeng.line.driver.server.dao.BusinessDAO;
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
    @Autowired
    BusinessDAO businessDAO;

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

    @Override
    public Stream<Business> listBusiness(int orgId) {
        return businessDAO.findByOrgId(orgId)
                .stream()
                .map(businessDO -> Business.builder()
                        .id(businessDO.getId())
                        .name(businessDO.getName())
                        .build());
    }
}
