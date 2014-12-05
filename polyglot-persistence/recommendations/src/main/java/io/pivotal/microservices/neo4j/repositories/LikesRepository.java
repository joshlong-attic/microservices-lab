package io.pivotal.microservices.neo4j.repositories;

import io.pivotal.microservices.neo4j.model.Likes;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface LikesRepository extends GraphRepository<Likes> {
}
