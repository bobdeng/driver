package cn.bobdeng.line.driver.server.lineup;

import cn.bobdeng.line.db.QueueDO;
import cn.bobdeng.line.driver.domain.queue.Queue;
import cn.bobdeng.line.driver.domain.queue.QueueRepository;
import cn.bobdeng.line.driver.server.dao.QueueDAO;
import com.tucodec.utils.BeanCopier;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class QueueRepositoryJpaImpl implements QueueRepository {
    public static final String ENQUEUE = "ENQUEUE";
    private static final long TIMEOUT = 5000;
    @Autowired
    QueueDAO queueDAO;
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    private static final String LOCK_QUEUE_PREFIX = "lock_queue_";

    @Override
    public List<Queue> getOrgQueue(int orgId) {
        return queueDAO.findByOrgId(orgId).stream()
                .map(this::fromDO)
                .collect(Collectors.toList());
    }

    private Queue fromDO(QueueDO queueDO) {
        Queue queue=new Queue();
        BeanUtils.copyProperties(queueDO,queue);
        return queue;
    }

    @Override
    public void lockQueue(int orgId) {
        long start = System.nanoTime();
        while (!redisTemplate.opsForValue().setIfAbsent(LOCK_QUEUE_PREFIX + orgId, ENQUEUE)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (System.nanoTime() - start > TIMEOUT) {
                throw new RuntimeException("lock time out");
            }
        }
        redisTemplate.opsForValue().set(LOCK_QUEUE_PREFIX + orgId, ENQUEUE, 5, TimeUnit.SECONDS);
    }

    @Override
    public void unlockQueue(int orgId) {
        redisTemplate.delete(LOCK_QUEUE_PREFIX + orgId);
    }

    @Override
    public void createQueue(Queue queue) {
        queueDAO.save(queueToDo(queue));
    }

    private QueueDO queueToDo(Queue queue) {
        QueueDO queueDO = QueueDO.builder().build();
        BeanUtils.copyProperties(queue, queueDO);
        return queueDO;
    }
}
