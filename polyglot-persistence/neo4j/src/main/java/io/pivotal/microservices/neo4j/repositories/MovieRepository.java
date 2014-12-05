package io.pivotal.microservices.neo4j.repositories;

import io.pivotal.microservices.neo4j.model.Movie;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface MovieRepository extends GraphRepository<Movie> {
    Movie findByMlId(String mlId);

    @Query("MATCH (p:Person) WHERE p.userName = {0} MATCH p-[:LIKES]->movie<-[:LIKES]-slm-[:LIKES]->recommendations " +
            "WHERE not(p = slm) and not (p--recommendations) return recommendations")
    Iterable<Movie> recommendedMoviesFor(String userName);
}
