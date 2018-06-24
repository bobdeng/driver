package cn.bobdeng.line.driver.server.config;

import cn.bobdeng.line.userclient.UserClient;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    @Bean
    public FilterRegistrationBean testFilterRegistration(UserClient userClient) {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setUserClient(userClient);
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(loginFilter);
        registration.addUrlPatterns("/*");
        registration.setName("testFilter");
        registration.setOrder(1);
        return registration;
    }

}
