package org.springframework.cloud.neo4j;

import org.springframework.cloud.cloudfoundry.CloudFoundryServiceInfoCreator;
import org.springframework.cloud.cloudfoundry.Tags;

import java.util.Map;

public class GraphDatabaseServiceInfoCreator extends CloudFoundryServiceInfoCreator<GraphDatabaseServiceInfo> {
    public GraphDatabaseServiceInfoCreator() {
        super(new Tags("pivotal","neo4j"));
    }

    @Override
    public GraphDatabaseServiceInfo createServiceInfo(Map<String, Object> serviceData) {
        @SuppressWarnings("unchecked")
        Map<String, Object> credentials = (Map<String, Object>) serviceData.get("credentials");

        String id = (String) serviceData.get("name");
        String host = getStringFromCredentials(credentials, "host");
        String username = getStringFromCredentials(credentials, "username");
        String password = getStringFromCredentials(credentials, "password");
        int httpPort = getIntFromCredentials(credentials, "http_port");
        int httpsPort = getIntFromCredentials(credentials, "https_port");

        return new GraphDatabaseServiceInfo(id, host, username, password, httpPort, httpsPort);
    }
}
