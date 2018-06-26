package cn.bobdeng.line.driver.domain.queue;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class QueueServiceImpl implements QueueService {
    QueueRepository queueRepository;

    @Override
    public int joinQueue(Queue queue) {
        queueRepository.lockQueue(queue.getOrgId());
        try {
            List<Queue> queues = queueRepository.getOrgQueue(queue.getOrgId());
            if (queues == null) queues = new ArrayList<>();
            queues.stream()
                    .filter(queue1 -> queue1.getUserId()==queue.getUserId())
                    .findFirst()
                    .ifPresent(queue1 -> {
                        throw new RuntimeException("不允许重复取号");
                    });
            int maxOrder = queues.stream().mapToInt(Queue::getOrderNumber)
                    .max()
                    .orElse(0);
            queue.setOrderNumber(maxOrder + 1);
            queueRepository.createQueue(queue);
            return (int)queues.stream()
                    .filter(q->q.getBusinessId()==queue.getBusinessId())
                    .count();
        } finally {
            queueRepository.unlockQueue(queue.getOrgId());
        }
    }
}
