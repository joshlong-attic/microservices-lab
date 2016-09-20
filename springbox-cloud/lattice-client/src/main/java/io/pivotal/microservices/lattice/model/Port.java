package io.pivotal.microservices.lattice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Port {
    @JsonProperty("container_port")
    private int containerPort;
    @JsonProperty("host_port")
    private int hostPort;

    public int getContainerPort() {
        return containerPort;
    }

    public void setContainerPort(int containerPort) {
        this.containerPort = containerPort;
    }

    public int getHostPort() {
        return hostPort;
    }

    public void setHostPort(int hostPort) {
        this.hostPort = hostPort;
    }

    @Override
    public String toString() {
        return "Port{" +
                "containerPort=" + containerPort +
                ", hostPort=" + hostPort +
                '}';
    }
}
