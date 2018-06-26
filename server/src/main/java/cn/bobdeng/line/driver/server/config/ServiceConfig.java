package cn.bobdeng.line.driver.server.config;

import cn.bobdeng.line.driver.domain.message.MessageRepository;
import cn.bobdeng.line.driver.domain.message.MessageService;
import cn.bobdeng.line.driver.domain.message.MessageServiceImpl;
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

    @Bean
    MessageService messageService(MessageRepository messageRepository) {
        MessageServiceImpl messageService = new MessageServiceImpl();
        messageService.setMessageRepository(messageRepository);
        return messageService;
    }
}
