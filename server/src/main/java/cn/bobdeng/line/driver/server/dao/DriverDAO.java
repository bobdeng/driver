package cn.bobdeng.line.driver.server.dao;

import cn.bobdeng.line.db.DriverDO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.stream.Stream;

public interface DriverDAO extends CrudRepository<DriverDO,Integer> {
    List<DriverDO> findByMobile(String mobile);
}