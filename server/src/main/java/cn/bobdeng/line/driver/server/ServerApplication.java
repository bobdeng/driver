package cn.bobdeng.line.driver.server;

import com.tucodec.check.EnableInputCheck;
import com.tucodec.controller.EnableBaseController;
import com.tucodec.logger.EnableLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableBaseController
@EnableLogger
@EnableInputCheck
@EnableFeignClients("cn.bobdeng")
@ComponentScan("cn.bobdeng")
@EnableJpaRepositories("cn.bobdeng")
@EntityScan("cn.bobdeng.line.db")
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
