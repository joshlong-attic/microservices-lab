package io.pivotal.microservices.apigateway.services.recommendations;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.ObservableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

import java.util.List;

@Service
public class RecommendationsIntegrationService {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand
    public Observable<List<Movie>> getRecommendations(final String mlId) {
        return new ObservableResult<List<Movie>>() {
            @Override
            public List<Movie> invoke() {
                ParameterizedTypeReference<List<Movie>> responseType = new ParameterizedTypeReference<List<Movie>>() {
                };
                return restTemplate.exchange("http://recommendations-service/recommendations/forMovie/{mlId}", HttpMethod.GET, null, responseType, mlId).getBody();
            }
        };
    }
}
