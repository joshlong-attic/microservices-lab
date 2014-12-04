package passport;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@ComponentScan
@Configuration
@EnableAutoConfiguration
@EnableEurekaClient
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private com.netflix.discovery.DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    private void dump() {
        InstanceInfo photoServiceInstanceInfo = discoveryClient.getNextServerFromEureka("photo-service", false);
        System.out.println("photoService: " + ToStringBuilder.reflectionToString(photoServiceInstanceInfo, ToStringStyle.MULTI_LINE_STYLE));

        InstanceInfo bookmarkServiceInstanceInfo = discoveryClient.getNextServerFromEureka(
                "bookmark-service", false);
        System.out.println("bookmarkService: " + ToStringBuilder.reflectionToString(
                bookmarkServiceInstanceInfo, ToStringStyle.MULTI_LINE_STYLE));

        InstanceInfo.InstanceStatus bookmarkStatus = bookmarkServiceInstanceInfo.getStatus();
        System.out.println("instance status: " + bookmarkStatus);

        InstanceInfo.InstanceStatus photoStatus = photoServiceInstanceInfo.getStatus();
        System.out.println("photo status: " + photoStatus);

        Map mapOfPhotoData = this.restTemplate.getForObject(
                "http://bookmark-service/{userId}/bookmarks", Map.class, "mstine");

        for (Object k : mapOfPhotoData.keySet())
            System.out.println(k + "=" + mapOfPhotoData.get(k));
    }

//    @Bean
    CommandLineRunner dumpOnStartup() {
        return args -> this.dump();
    }

    @RequestMapping("/discover")
    void discover() {
        this.dump();
    }
}
