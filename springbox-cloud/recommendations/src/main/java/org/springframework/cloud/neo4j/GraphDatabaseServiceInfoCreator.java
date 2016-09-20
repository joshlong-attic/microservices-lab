package org.springframework.cloud.neo4j;

import org.springframework.cloud.cloudfoundry.CloudFoundryServiceInfoCreator;
import org.springframework.cloud.cloudfoundry.Tags;

import java.util.Map;

public class GraphDatabaseServiceInfoCreator extends CloudFoundryServiceInfoCreator<GraphDatabaseServiceInfo> {
    public GraphDatabaseServiceInfoCreator() {
        super(new Tags("pivotal", "neo4j"));
    }

    @Override
    public GraphDatabaseServiceInfo createServiceInfo(Map<String, Object> serviceData) {
        @SuppressWarnings("unchecked")
        Map<String, Object> credentials = (Map<String, Object>) serviceData.get("credentials");
        String id = (String) serviceData.get("name");

        if (acceptUpsi(serviceData)) {
            return new GraphDatabaseServiceInfo(id, getStringFromCredentials(credentials, "neo4jUri"));
        } else {
            String host = getStringFromCredentials(credentials, "host");
            String username = getStringFromCredentials(credentials, "username");
            String password = getStringFromCredentials(credentials, "password");
            int httpPort = getIntFromCredentials(credentials, "http_port");
            return new GraphDatabaseServiceInfo(id, "http", host, httpPort, username, password, "/db/data");
        }
    }

    @Override
    public boolean accept(Map<String, Object> serviceData) {
        return super.accept(serviceData) || acceptUpsi(serviceData);
    }

    private boolean acceptUpsi(Map<String, Object> serviceData) {
        @SuppressWarnings("unchecked")
        Map<String, Object> credentials = (Map<String, Object>) serviceData.get("credentials");
        String uri = getStringFromCredentials(credentials, "neo4jUri");
        return uri != null && !uri.isEmpty();
    }

    /* TODO: Inlined from 1.1.1 */
    private int getIntFromCredentials(Map<String, Object> credentials, String... keys) {
        for (String key : keys) {
            if (credentials.containsKey(key)) {
                // allows the value to be quoted as a String or native integer type
                return Integer.parseInt(credentials.get(key).toString());
            }
        }
        return -1;
    }
}
