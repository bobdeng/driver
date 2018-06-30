package cn.bobdeng.line.driver.domain.message;

import java.util.Optional;

public interface MessageRepository {
    Optional<Message> findByIdAndUserId(int id, int userId);

    void updateConfirmTime(int id, int userId, long millis);

    void removeMessagePool(int id, int userId);
}
