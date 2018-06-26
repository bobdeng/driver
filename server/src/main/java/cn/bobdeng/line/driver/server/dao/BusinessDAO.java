package cn.bobdeng.line.driver.server.dao;

import cn.bobdeng.line.db.BusinessDO;
import cn.bobdeng.line.driver.domain.org.Business;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.stream.Stream;

public interface BusinessDAO extends CrudRepository<BusinessDO,Integer> {
    List<BusinessDO> findByOrgId(int orgId);
}
