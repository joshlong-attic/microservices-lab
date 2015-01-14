package io.pivotal.microservices.lattice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class LongRunningProcess {
    @JsonProperty("process_guid")
    private String processGuid;
    @JsonProperty("instance_guid")
    private String instanceGuid;
    @JsonProperty("cell_id")
    private String cellId;
    private String domain;
    private int index;
    private String address;
    private Port[] ports;
    private String state;
    private long since;

    public String getProcessGuid() {
        return processGuid;
    }

    public void setProcessGuid(String processGuid) {
        this.processGuid = processGuid;
    }

    public String getInstanceGuid() {
        return instanceGuid;
    }

    public void setInstanceGuid(String instanceGuid) {
        this.instanceGuid = instanceGuid;
    }

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Port[] getPorts() {
        return ports;
    }

    public void setPorts(Port[] ports) {
        this.ports = ports;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getSince() {
        return since;
    }

    public void setSince(long since) {
        this.since = since;
    }

    @Override
    public String toString() {
        return "LongRunningProcess{" +
                "processGuid='" + processGuid + '\'' +
                ", instanceGuid='" + instanceGuid + '\'' +
                ", cellId='" + cellId + '\'' +
                ", domain='" + domain + '\'' +
                ", index=" + index +
                ", address='" + address + '\'' +
                ", ports=" + Arrays.toString(ports) +
                ", state='" + state + '\'' +
                ", since=" + since +
                '}';
    }
}
