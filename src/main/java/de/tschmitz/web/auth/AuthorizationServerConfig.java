package de.tschmitz.web.auth;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * Configuration of the authorization server.
 *
 * Provides an in-memory client details service.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    private final OAuthProperties properties;

    public AuthorizationServerConfig(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
                                     @Qualifier("jwtTokenEnhancer") JwtAccessTokenConverter jwtAccessTokenConverter,
                                     OAuthProperties properties) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
        this.properties = properties;
    }

    /**
     * Configures an in-memory OAuth client details service.
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(properties.getClientId())
                .secret(passwordEncoder.encode(properties.getClientSecret()))
                .accessTokenValiditySeconds(properties.getAccessTokenValiditySeconds())
                .refreshTokenValiditySeconds(properties.getRefreshTokenValiditySeconds())
                .authorizedGrantTypes(properties.getAuthorizedGrantTypes())
                .scopes(properties.getScopes())
                .resourceIds(properties.getResourceIds());
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .accessTokenConverter(jwtAccessTokenConverter)
                .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.checkTokenAccess("isAuthenticated()");
    }
}