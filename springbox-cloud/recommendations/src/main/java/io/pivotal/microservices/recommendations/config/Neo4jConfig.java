package io.pivotal.microservices.recommendations.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;

@Configuration
public class Neo4jConfig extends Neo4jConfiguration {
    public Neo4jConfig() {
        setBasePackage("io.pivotal.microservices.recommendations.model");
    }
}
