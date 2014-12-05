package io.pivotal.microservices.apigateway.services.reviews;

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
public class ReviewsIntegrationService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand
    public Observable<List<Review>> reviewsFor(String mlId) {
        return new ObservableResult<List<Review>>() {
            @Override
            public List<Review> invoke() {
                ParameterizedTypeReference<List<Review>> responseType = new ParameterizedTypeReference<List<Review>>() {
                };
                return restTemplate.exchange("http://reviews-service/reviews/reviews/{mlId}", HttpMethod.GET, null, responseType, mlId).getBody();
            }
        };
    }
}
