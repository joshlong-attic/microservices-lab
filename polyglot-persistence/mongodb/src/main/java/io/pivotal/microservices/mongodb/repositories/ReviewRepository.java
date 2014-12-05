package io.pivotal.microservices.mongodb.repositories;

import io.pivotal.microservices.mongodb.models.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "reviews", path = "reviews")
public interface ReviewRepository extends MongoRepository<Review, String> {
}
