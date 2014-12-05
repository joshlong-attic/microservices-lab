package cloud.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    @Value("${configuration.projectName}")
    private String projectName;

    @RequestMapping("/project-name")
    String projectName() {
        return this.projectName;
    }

    public static void main(String[] args) {
        Environment environment = SpringApplication.run(Application.class, args).getBean(Environment.class);
        String projectName = environment.getProperty("configuration.projectName");
        System.out.println(projectName);
    }
}
