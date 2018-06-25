package cn.bobdeng.line.driver.server.config;

import cn.bobdeng.line.driver.domain.queue.QueueRepository;
import cn.bobdeng.line.driver.domain.queue.QueueService;
import cn.bobdeng.line.driver.domain.queue.QueueServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    @Bean
    QueueService queueService(QueueRepository queueRepository) {
        QueueServiceImpl queueService = new QueueServiceImpl();
        queueService.setQueueRepository(queueRepository);
        return queueService;
    }
}
