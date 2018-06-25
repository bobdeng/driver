package cn.bobdeng.line.driver.domain.org;

import java.util.List;
import java.util.stream.Stream;

public interface OrgnizationRepository {
    Stream<Orgnization> findByDriver(String mobile);
}
