package auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

/**
 * Easy to retrieve an access token using:
 * {@code curl -X POST -vu acme:acmesecret http://localhost:8002/auth/oauth/token -H "Accept: application/json" -d "password=password&username=jlong&grant_type=password&scope=read&client_secret=acmesecret&client_id=acme" }
 * <p/>
 * Then, send the access token to an OAuth2 secured REST resource using:
 * {@code curl http://localhost:8080/api -H "Authorization: Bearer _INSERT TOKEN_}
 *
 * @author Josh Long
 */
@SpringBootApplication
@EnableEurekaClient
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    UserDetailsService userDetailsService(JdbcTemplate jdbcTemplate) {

        RowMapper<User> userRowMapper = (rs, i) ->
                new User(
                        rs.getString("ACCOUNT_NAME"),
                        rs.getString("PASSWORD"),
                        rs.getBoolean("ENABLED"),
                        rs.getBoolean("ENABLED"),
                        rs.getBoolean("ENABLED"),
                        rs.getBoolean("ENABLED"),
                        AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN"));

        return username -> jdbcTemplate.queryForObject(
                "select * from ACCOUNT where ACCOUNT_NAME = ?", userRowMapper, username);
    }

    @Bean
    ClientDetailsService clientDetailsService(JdbcTemplate jdbcTemplate) {

        RowMapper<ClientDetails> clientDetailsRowMapper = (rs, i) -> {
            BaseClientDetails baseClientDetails = new BaseClientDetails(
                    rs.getString("CLIENT_ID"),
                    rs.getString("RESOURCE_IDS"),
                    rs.getString("SCOPES"),
                    rs.getString("GRANT_TYPES"),
                    rs.getString("AUTHORITIES"));
            baseClientDetails.setClientSecret(rs.getString("CLIENT_SECRET"));
            return baseClientDetails;
        };
        return clientId -> jdbcTemplate.queryForObject(
                "select * from CLIENT_DETAILS where CLIENT_ID=?", clientDetailsRowMapper, clientId);
    }

    @Bean
    AuthenticationManager authenticationManager(
            ObjectPostProcessor<Object> objectPostProcessor,
            UserDetailsService userDetailsService) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = new AuthenticationManagerBuilder(objectPostProcessor);
        authenticationManagerBuilder.userDetailsService(userDetailsService);
        return authenticationManagerBuilder.getOrBuild();
    }

    @Configuration
    @EnableAuthorizationServer
    static class OAuth2AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private ClientDetailsService clientDetailsService;

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.withClientDetails(clientDetailsService);
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.authenticationManager(authenticationManager);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
            oauthServer.checkTokenAccess("permitAll()");
        }
    }
}
