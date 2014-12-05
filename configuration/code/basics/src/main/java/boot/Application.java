package boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

// -D and env vars work too!
@ComponentScan
@Configuration
@EnableAutoConfiguration
public class Application {

    @Autowired
    void setConfigurationProjectProperties(ConfigurationProjectProperties cp) {
        System.out.println( "configurationProjectProperties.projectName = " +  cp.getProjectName());
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}

@Component
@ConfigurationProperties("configuration")
class ConfigurationProjectProperties {

    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}

