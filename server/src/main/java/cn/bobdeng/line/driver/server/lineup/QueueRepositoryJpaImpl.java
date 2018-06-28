package cn.bobdeng.line.driver.server.lineup;

import cn.bobdeng.line.db.CounterDO;
import cn.bobdeng.line.db.QueueDO;
import cn.bobdeng.line.driver.domain.queue.Queue;
import cn.bobdeng.line.driver.domain.queue.QueueRepository;
import cn.bobdeng.line.driver.server.dao.CounterDAO;
import cn.bobdeng.line.driver.server.dao.QueueDAO;
import lombok.extern.java.Log;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Log
public class QueueRepositoryJpaImpl implements QueueRepository {
    public static final String ENQUEUE = "ENQUEUE";
    private static final long TIMEOUT = 5000;
    @Autowired
    QueueDAO queueDAO;
    @Autowired
    CounterDAO counterDAO;
    @Autowired
    RedissonClient redissonClient;
    public static final String QUEUE_LOCK_KEY_PREFIX = "queue_lock_key_prefix_";

    @Override
    public List<Queue> getOrgQueue(int orgId) {
        return queueDAO.findByOrgId(orgId).stream()
                .map(this::fromDO)
                .collect(Collectors.toList());
    }

    private Queue fromDO(QueueDO queueDO) {
        Queue queue = new Queue();
        BeanUtils.copyProperties(queueDO, queue);
        return queue;
    }

    @Override
    public void lockQueue(int orgId) {
        redissonClient.getLock(QUEUE_LOCK_KEY_PREFIX + orgId).lock(1, TimeUnit.MINUTES);
    }

    @Override
    public void unlockQueue(int orgId) {
        redissonClient.getLock(QUEUE_LOCK_KEY_PREFIX + orgId).unlock();
    }

    @Override
    public Long getLastQueueUpdate(String key) {

        return checkAtomAndGet(key);
    }

    @Override
    public void getAndIncLastUpdate(String key) {
        checkAtomAndGet(key);
        redissonClient.getAtomicLong(key).getAndIncrement();
    }

    private Long checkAtomAndGet(String key) {
        RAtomicLong keyAtom = redissonClient.getAtomicLong(key);
        long ret = keyAtom.get();
        if (ret==0) {
            long lastUpdate = System.currentTimeMillis();
            if (keyAtom.compareAndSet(0,lastUpdate)) {
                return lastUpdate;
            }else{
                return keyAtom.get();
            }
        }
        return  ret;
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

    @Override
    public String findCounterById(int counterId) {
        if(counterId==0) return "";
        return Optional.ofNullable(counterDAO.findOne(counterId))
                .map(CounterDO::getName)
                .orElse("");
    }
}
