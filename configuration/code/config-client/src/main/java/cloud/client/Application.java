package cloud.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {


    @Bean
    @RefreshScope
    Holder holder() {
        return new Holder();
    }

    public static void main(String[] args) {
        Environment environment = SpringApplication.run(Application.class, args).getBean(Environment.class);
        String projectName = environment.getProperty("configuration.projectName");
        System.out.println(projectName);
    }
}

@RestController
class HolderRestController {

    @Autowired
    private Holder holder;

    @RequestMapping("/holder-value")
    String string() {
        return this.holder.getX();
    }
}

class Holder {

    @Value("${configuration.projectName}")
    private String x;

    public String getX() {
        return x;
    }

    @Override
    public String toString() {
        return "Holder{" +
                "x='" + x + '\'' +
                '}';
    }
}
