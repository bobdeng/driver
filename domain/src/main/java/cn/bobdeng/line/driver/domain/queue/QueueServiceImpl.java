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
            int maxOrder = queues.stream().mapToInt(Queue::getOrder)
                    .max()
                    .orElse(0);
            queue.setOrder(maxOrder + 1);
            queueRepository.createQueue(queue);
            return queues.size();
        } finally {
            queueRepository.unlockQueue(queue.getOrgId());
        }
    }
}
