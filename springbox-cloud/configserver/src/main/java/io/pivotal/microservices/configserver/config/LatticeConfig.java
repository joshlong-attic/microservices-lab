package io.pivotal.microservices.configserver.config;

import io.pivotal.microservices.lattice.client.LatticeClient;
import io.pivotal.microservices.lattice.model.LongRunningProcess;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("lattice")
public class LatticeConfig {

    @Value("${lattice.receptor.host}")
    String receptorHost;

    @Bean
    public LatticeClient latticeClient() {
        return new LatticeClient(receptorHost);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        LongRunningProcess lrp = latticeClient().findLongRunningProcess("rabbitmq");
        return new CachingConnectionFactory(lrp.getAddress(), lrp.getPorts()[0].getHostPort());
    }
}
