package cn.bobdeng.line.driver.server.dao;

import cn.bobdeng.line.db.QueueDO;
import cn.bobdeng.line.driver.domain.queue.Queue;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QueueDAO extends CrudRepository<QueueDO,Integer> {
    List<QueueDO> findByOrgId(int orgId);
}
