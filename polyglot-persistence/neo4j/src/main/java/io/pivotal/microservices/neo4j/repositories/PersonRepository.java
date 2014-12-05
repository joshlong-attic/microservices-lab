package io.pivotal.microservices.neo4j.repositories;

import io.pivotal.microservices.neo4j.model.Person;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface PersonRepository extends GraphRepository<Person> {
    Person findByUserName(String userName);
}
