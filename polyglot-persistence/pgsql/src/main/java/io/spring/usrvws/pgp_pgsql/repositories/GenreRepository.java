package io.spring.usrvws.pgp_pgsql.repositories;

import io.spring.usrvws.pgp_pgsql.models.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "genres", path = "genres")
public interface GenreRepository extends CrudRepository<Genre, Long> {
}
