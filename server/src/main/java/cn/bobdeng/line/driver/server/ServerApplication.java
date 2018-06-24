package cn.bobdeng.line.driver.server;

import com.tucodec.controller.EnableBaseController;
import com.tucodec.logger.EnableLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableBaseController
@EnableLogger
@EnableEurekaClient
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
