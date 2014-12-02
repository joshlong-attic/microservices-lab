package io.spring.usrvws.pgp_pgsql.repositories;

import io.spring.usrvws.pgp_pgsql.models.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "movies", path = "movies")
public interface MovieRepository extends CrudRepository<Movie, Long> {
}
