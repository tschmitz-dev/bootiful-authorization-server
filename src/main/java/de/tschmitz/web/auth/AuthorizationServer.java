package de.tschmitz.web.auth;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * Application class to run the authorization server.
 */
@SpringBootApplication
@Configuration
@EnableConfigurationProperties(OAuthProperties.class)
public class AuthorizationServer {

    @Bean
    @Qualifier("jwtTokenEnhancer")
    JwtAccessTokenConverter accessTokenConverter() {
        return new JwtAccessTokenConverter();
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServer.class, args);
    }
}
