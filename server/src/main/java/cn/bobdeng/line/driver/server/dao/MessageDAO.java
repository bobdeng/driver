package cn.bobdeng.line.driver.server.dao;

import cn.bobdeng.line.db.MessageDO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface MessageDAO extends CrudRepository<MessageDO, Integer> {
    MessageDO findByIdAndUserId(int id, int userId);

    @Modifying
    @Transactional
    @Query("update MessageDO a set a.verifyTime=:millis where a.id=:id and a.userId=:userId")
    void updateConfirmTime(@Param("id") int id, @Param("userId") int userId, @Param("millis") long millis);
}
