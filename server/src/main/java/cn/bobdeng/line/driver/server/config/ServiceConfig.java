package cn.bobdeng.line.driver.server.config;

import cn.bobdeng.line.driver.domain.message.MessageRepository;
import cn.bobdeng.line.driver.domain.message.MessageService;
import cn.bobdeng.line.driver.domain.message.MessageServiceImpl;
import cn.bobdeng.line.driver.domain.queue.QueueRepository;
import cn.bobdeng.line.driver.domain.queue.QueueService;
import cn.bobdeng.line.driver.domain.queue.QueueServiceImpl;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class ServiceConfig {
    @Value("${spring.redis.host:localhost}")
    private String redisHost;
    @Value("${spring.redis.port:6379}")
    private int redisPort;
    @Value("${spring.redis.password:}")
    private String redisPassword;
    @Bean
    RedissonClient redisson(){
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        SingleServerConfig singleServerConfig = config.useSingleServer().setAddress("redis://" + redisHost + ":" + redisPort);
        if(!StringUtils.isEmpty(redisPassword)){
            singleServerConfig.setPassword(redisPassword);
        }
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
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
