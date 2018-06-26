package cn.bobdeng.line.driver.domain.org;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface OrgnizationRepository {
    Stream<Orgnization> findByDriver(String mobile);

    Stream<Business> listBusiness(int orgId);

    Optional<Orgnization> findById(int orgId);
}
