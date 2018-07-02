package cn.bobdeng.line.driver.server.dao;

import cn.bobdeng.line.db.QueueDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QueueDAO extends CrudRepository<QueueDO,Integer> {
    @Query("select a from QueueDO a where a.orgId=:orgId")
    List<QueueDO> findByOrgId(@Param("orgId") int orgId);
}
