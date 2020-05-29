package de.tschmitz.web.auth;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration test that demonstrates how to obtain a token.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AuthorizationServerApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private OAuthProperties properties;

    private String tokenEndpoint;

    @BeforeEach
    public void beforeEach() {
        tokenEndpoint = String.format("http://localhost:%s/oauth/token", port);
    }

    @Test
    public void returnToken_withValidUserCredentials() {
        Map<String, String> params = headerMap("demo", "demo");
        Response response = RestAssured.given().auth().preemptive()
                .basic(properties.getClientId(), properties.getClientSecret())
                .and().with().params(params).when()
                .post(tokenEndpoint);
        assertThat(getAccessToken(response)).isNotNull();
    }

    @Test
    public void returnNoToken_withInValidUserCredentials() {
        Map<String, String> params = headerMap("user1", "demo2");

        Response response = RestAssured.given().auth().preemptive()
                .basic(properties.getClientId(), properties.getClientSecret())
                .and().with().params(params).when()
                .post(tokenEndpoint);

        assertThat(getAccessToken(response)).isNull();
        assertThat(getErrorDescription(response)).isEqualTo("Bad credentials");    // bad request
    }

    private String getAccessToken(Response response) {
        return response.jsonPath().getString("access_token");
    }

    private String getErrorDescription(Response response) {
        return response.jsonPath().getString("error_description");
    }

    private Map<String, String> headerMap(String username, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", password);
        return params;
    }
}
