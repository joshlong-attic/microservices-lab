package io.pivotal.microservices.lattice.client;

import io.pivotal.microservices.lattice.model.LongRunningProcess;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class LatticeClient {

    private final String receptorHost;

    public LatticeClient(String receptorHost) {
        this.receptorHost = receptorHost;
    }

    public List<LongRunningProcess> findLongRunningProcesses(String guid) {
        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<List<LongRunningProcess>> responseType = new ParameterizedTypeReference<List<LongRunningProcess>>() {};
        return restTemplate.exchange("http://{receptorHost}/v1/actual_lrps/{guid}", HttpMethod.GET, null, responseType, receptorHost, guid).getBody();
    }

    public LongRunningProcess findLongRunningProcess(String guid) {
        return findLongRunningProcesses(guid).get(0);
    }

}
