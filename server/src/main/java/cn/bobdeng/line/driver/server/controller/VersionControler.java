package cn.bobdeng.line.driver.server.controller;

import cn.bobdeng.line.driver.domain.queue.QueueRepository;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhiguodeng on 2017/7/3.
 */
@RestController
public class VersionControler {

    public static final String KEY_123 = "key2";
    @Value("${build.version}")
    String version;
    @Autowired
    RedissonClient redissonClient;

    @GetMapping("/version")
    public String version() {
        System.out.println(redissonClient.getAtomicLong(KEY_123).compareAndSet(0,100));
        return version;
    }
}
