package cn.bobdeng.line.driver.domain.queue;

import java.util.List;

public interface QueueRepository {
    List<Queue> getOrgQueue(int orgId);

    void lockQueue(int orgId);

    void unlockQueue(int orgId);

    void createQueue(Queue queue);

    Long getLastQueueUpdate(String key);

    void getAndIncLastUpdate(String key);

    String findCounterById(int counterId);

    Driver getDriver(String mobile, int orgId);
}
