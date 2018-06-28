package cn.bobdeng.line.driver.server.dao;

import cn.bobdeng.line.db.CounterDO;
import org.springframework.data.repository.CrudRepository;

public interface CounterDAO extends CrudRepository<CounterDO,Integer> {
}
