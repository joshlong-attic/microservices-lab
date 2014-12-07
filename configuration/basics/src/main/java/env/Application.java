package env;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan
@PropertySource("simple.properties")
public class Application {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${configuration.projectName}")
    void setProjectName(String projectName) {
        System.out.println("setProjectName('" + projectName + ");");
    }

    public static void main(String args[]) throws Throwable {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Application.class);
        Environment environment = ac.getBean(Environment.class);
        System.out.println("env=" + environment.getProperty("configuration.projectName"));
    }
}
