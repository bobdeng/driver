package cn.bobdeng.line.driver.server.dao;

import cn.bobdeng.line.db.MessagePoolDO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface MessagePoolDAO extends CrudRepository<MessagePoolDO,Integer> {
    @Modifying
    @Query("delete from MessagePoolDO a where a.messageId=:id")
    @Transactional
    void deleteByMessageId(@Param("id") String id);
}
