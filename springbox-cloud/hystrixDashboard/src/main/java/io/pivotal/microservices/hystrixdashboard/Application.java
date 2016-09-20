package io.pivotal.microservices.hystrixdashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableHystrixDashboard
@Controller
public class Application {

    @RequestMapping("/")
    public String home() {
        return "forward:/hystrix";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
