package cn.bobdeng.line.driver.domain.queue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QueueServiceImplTest {
    public static final int ORG_ID = 1;
    public static final int USER_ID = 1;
    @InjectMocks
    QueueServiceImpl queueService = new QueueServiceImpl();
    @Mock
    QueueRepository queueRepository;

    @Test
    public void joinQueue_null() {
        when(queueRepository.getOrgQueue(ORG_ID)).thenReturn(null);
        Queue queue = Queue.builder().orgId(ORG_ID).build();
        int before = queueService.joinQueue(queue);
        assertEquals(before, 0);
        assertEquals(queue.getOrderNumber(), 1);
    }

    @Test
    public void joinQueue_empty() {
        when(queueRepository.getOrgQueue(ORG_ID)).thenReturn(new ArrayList<>());
        Queue queue = Queue.builder().orgId(ORG_ID).build();
        int before = queueService.joinQueue(queue);
        assertEquals(before, 0);
        assertEquals(queue.getOrderNumber(), 1);
    }

    @Test
    public void joinQueue_not_empty() {
        List<Queue> queues = IntStream.range(0, 10).mapToObj(i -> Queue.builder().orderNumber(i).build())
                .collect(Collectors.toList());
        when(queueRepository.getOrgQueue(ORG_ID)).thenReturn(queues);
        Queue queue = Queue.builder().orgId(ORG_ID).userId(USER_ID).build();
        int before = queueService.joinQueue(queue);
        assertEquals(before, 10);
        assertEquals(queue.getOrderNumber(), 10);
    }
    @Test(expected = RuntimeException.class)
    public void joinQueue_not_repeat() {
        when(queueRepository.getOrgQueue(ORG_ID)).thenReturn(Arrays.asList(Queue.builder().userId(USER_ID).build()));
        Queue queue = Queue.builder().orgId(ORG_ID).userId(USER_ID).build();
        int before = queueService.joinQueue(queue);
        assertEquals(before, 10);
        assertEquals(queue.getOrderNumber(), 10);
    }
}