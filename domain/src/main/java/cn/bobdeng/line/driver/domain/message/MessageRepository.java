package cn.bobdeng.line.driver.domain.message;

import java.util.Optional;

public interface MessageRepository {
    Optional<Message> findByIdAndUserId(String id, int userId);

    void updateConfirmTime(String id, int userId, long millis);

    void removeMessagePool(String id, int userId);
}
