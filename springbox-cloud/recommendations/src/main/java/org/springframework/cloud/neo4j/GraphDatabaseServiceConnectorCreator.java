package org.springframework.cloud.neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.cloud.service.AbstractServiceConnectorCreator;
import org.springframework.cloud.service.ServiceConnectorConfig;
import org.springframework.data.neo4j.rest.SpringCypherRestGraphDatabase;

public class GraphDatabaseServiceConnectorCreator extends AbstractServiceConnectorCreator<GraphDatabaseService, GraphDatabaseServiceInfo> {

    @Override
    public GraphDatabaseService create(GraphDatabaseServiceInfo neo4JServiceInfo, ServiceConnectorConfig serviceConnectorConfig) {
        return new SpringCypherRestGraphDatabase(neo4JServiceInfo.getUri(), neo4JServiceInfo.getUserName(), neo4JServiceInfo.getPassword());
    }
}
