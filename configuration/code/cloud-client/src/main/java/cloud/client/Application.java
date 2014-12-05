package cloud.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@ComponentScan
@Configuration
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        Environment environment = SpringApplication.run(Application.class, args).getBean(Environment.class);
        String projectName = environment.getProperty("configuration.projectName");
        System.out.println( projectName) ;
    }
/*
    @Value("${configuration.projectName}")
    void setProjectName(String projectName) {
        System.out.println("setProjectName('" + projectName + ");");
    }*/

}
