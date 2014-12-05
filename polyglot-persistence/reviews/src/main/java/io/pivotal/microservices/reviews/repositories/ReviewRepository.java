package io.pivotal.microservices.reviews.repositories;

import io.pivotal.microservices.reviews.models.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "reviews", path = "reviews")
public interface ReviewRepository extends MongoRepository<Review, String> {
}
