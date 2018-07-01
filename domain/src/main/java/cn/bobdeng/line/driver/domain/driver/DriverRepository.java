package cn.bobdeng.line.driver.domain.driver;

import java.util.Optional;

public interface DriverRepository {
    Optional<Driver> findByMobileAndOrg(String mobile, int orgId);
}
