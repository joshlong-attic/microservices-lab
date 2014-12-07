package io.pivotal.microservices.apigateway;

import io.pivotal.microservices.apigateway.models.MovieDetails;
import io.pivotal.microservices.apigateway.services.catalog.CatalogIntegrationService;
import io.pivotal.microservices.apigateway.services.recommendations.RecommendationsIntegrationService;
import io.pivotal.microservices.apigateway.services.reviews.ReviewsIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;
import rx.Observer;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@EnableHystrix
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

@RestController
class MovieRestController {

    @Autowired
    private CatalogIntegrationService catalogIntegrationService;

    @Autowired
    private ReviewsIntegrationService reviewsIntegrationService;

    @Autowired
    private RecommendationsIntegrationService recommendationsIntegrationService;

    @RequestMapping("/movie/{mlId}")
    DeferredResult<MovieDetails> movieDetails(@PathVariable String mlId) {
        Observable<MovieDetails> details = Observable.zip(catalogIntegrationService.getMovie(mlId),
                reviewsIntegrationService.reviewsFor(mlId),
                recommendationsIntegrationService.getRecommendations(mlId), (movie, reviews, recommendations) -> {
                    MovieDetails movieDetails = new MovieDetails();
                    movieDetails.setMlId(movie.getMlId());
                    movieDetails.setTitle(movie.getTitle());
                    movieDetails.setReviews(reviews);
                    movieDetails.setRecommendations(recommendations);
                    return movieDetails;
                }
        );
        return toDeferredResult(details);
    }

    private DeferredResult<MovieDetails> toDeferredResult(Observable<MovieDetails> details) {
        DeferredResult<MovieDetails> result = new DeferredResult<>();
        details.subscribe(new Observer<MovieDetails>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onNext(MovieDetails movieDetails) {
                result.setResult(movieDetails);
            }
        });
        return result;
    }

}