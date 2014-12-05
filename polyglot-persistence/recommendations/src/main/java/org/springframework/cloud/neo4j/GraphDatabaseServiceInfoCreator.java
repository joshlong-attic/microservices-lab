package org.springframework.cloud.neo4j;

import org.springframework.cloud.cloudfoundry.CloudFoundryServiceInfoCreator;
import org.springframework.cloud.cloudfoundry.Tags;

import java.util.Map;

public class GraphDatabaseServiceInfoCreator extends CloudFoundryServiceInfoCreator<GraphDatabaseServiceInfo> {
    public GraphDatabaseServiceInfoCreator() {
        super(new Tags());
    }

    @Override
    public boolean accept(Map<String, Object> serviceData) {
        @SuppressWarnings("unchecked")
        Map<String, Object> credentials = (Map<String, Object>) serviceData.get("credentials");

        String uri = getStringFromCredentials(credentials, "neo4jUri");
        String username = getStringFromCredentials(credentials, "neo4jUsername");
        String password = getStringFromCredentials(credentials, "neo4jPassword");

        return uri != null && !uri.isEmpty() &&
                username != null && !username.isEmpty() &&
                password != null && !password.isEmpty();
    }

    @Override
    public GraphDatabaseServiceInfo createServiceInfo(Map<String, Object> serviceData) {
        @SuppressWarnings("unchecked")
        Map<String, Object> credentials = (Map<String, Object>) serviceData.get("credentials");

        String id = (String) serviceData.get("name");
        String uri = getStringFromCredentials(credentials, "neo4jUri");
        String username = getStringFromCredentials(credentials, "neo4jUsername");
        String password = getStringFromCredentials(credentials, "neo4jPassword");

        return new GraphDatabaseServiceInfo(id, uri, username, password);
    }
}
