package io.pivotal.microservices.catalog.repositories;

import io.pivotal.microservices.catalog.models.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "movies", path = "movies")
public interface MovieRepository extends CrudRepository<Movie, Long> {
}
