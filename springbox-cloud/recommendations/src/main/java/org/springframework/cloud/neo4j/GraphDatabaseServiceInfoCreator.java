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
            String host = getStringFromCredentials(credentials, "neo4jUri");
            String username = getStringFromCredentials(credentials, "neo4jUsername");
            String password = getStringFromCredentials(credentials, "neo4jPassword");
            return new GraphDatabaseServiceInfo(id, host, username, password, 80, 443);
        } else {
            String host = getStringFromCredentials(credentials, "host");
            String username = getStringFromCredentials(credentials, "username");
            String password = getStringFromCredentials(credentials, "password");
            int httpPort = getIntFromCredentials(credentials, "http_port");
            int httpsPort = getIntFromCredentials(credentials, "https_port");

            return new GraphDatabaseServiceInfo(id, host, username, password, httpPort, httpsPort);
        }
    }

    @Override
    public boolean accept(Map<String, Object> serviceData) {
        return super.accept(serviceData) || acceptUpsi(serviceData);
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

    private boolean acceptUpsi(Map<String, Object> serviceData) {
        @SuppressWarnings("unchecked")
        Map<String, Object> credentials = (Map<String, Object>) serviceData.get("credentials");

        String uri = getStringFromCredentials(credentials, "neo4jUri");
        String username = getStringFromCredentials(credentials, "neo4jUsername");
        String password = getStringFromCredentials(credentials, "neo4jPassword");

        return uri != null && !uri.isEmpty() &&
                username != null && !username.isEmpty() &&
                password != null && !password.isEmpty();
    }
}
