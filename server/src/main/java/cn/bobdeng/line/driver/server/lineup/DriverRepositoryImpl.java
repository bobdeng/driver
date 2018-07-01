package cn.bobdeng.line.driver.server.lineup;

import cn.bobdeng.line.driver.domain.driver.Driver;
import cn.bobdeng.line.driver.domain.driver.DriverRepository;
import cn.bobdeng.line.driver.server.dao.DriverDAO;
import com.tucodec.utils.BeanCopier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DriverRepositoryImpl implements DriverRepository {
    @Autowired
    DriverDAO driverDAO;

    @Override
    public Optional<Driver> findByMobileAndOrg(String mobile, int orgId) {
        return Optional.ofNullable(driverDAO.findByMobileAndOrgId(mobile, orgId))
                .map(driverDO -> BeanCopier.copyFrom(driverDO, Driver.class));
    }
}
