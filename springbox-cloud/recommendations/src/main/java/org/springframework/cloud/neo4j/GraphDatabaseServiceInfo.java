package org.springframework.cloud.neo4j;

import org.springframework.cloud.service.BaseServiceInfo;

public class GraphDatabaseServiceInfo extends BaseServiceInfo {

    private String uri;
    private String username;
    private String password;

    public GraphDatabaseServiceInfo(String id, String uri, String username, String password) {
        super(id);
        this.uri = uri;
        this.username = username;
        this.password = password;
    }

    @ServiceProperty
    public String getUri() {
        return uri;
    }

    @ServiceProperty
    public String getUsername() {
        return username;
    }

    @ServiceProperty
    public String getPassword() {
        return password;
    }
}
