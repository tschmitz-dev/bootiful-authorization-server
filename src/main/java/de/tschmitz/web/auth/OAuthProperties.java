package de.tschmitz.web.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.server")
@Data
@NoArgsConstructor
public class OAuthProperties {

    private String clientId;

    private String clientSecret;

    private int accessTokenValiditySeconds = 43200; // 12 hours

    private int refreshTokenValiditySeconds = 2592000; // 30 days

    private String[] authorizedGrantTypes = new String[]{"password", "authorization_code", "refresh_token"};

    private String[] scopes = new String[]{"read", "write"};

    private String[] resourceIds = new String[]{"api"};
}
