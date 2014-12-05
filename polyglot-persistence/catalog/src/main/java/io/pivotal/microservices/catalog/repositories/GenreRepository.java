package io.pivotal.microservices.catalog.repositories;

import io.pivotal.microservices.catalog.models.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "genres", path = "genres")
public interface GenreRepository extends CrudRepository<Genre, Long> {
}
