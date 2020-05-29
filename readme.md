# Spring Bootiful Authorization Server

A simple OAuth2 Authorization Server that provides an in-memory client details service with only one client. 

Get a token with curl:
```
curl ce920188d47a2f51d7c6:8b562ac7f64785c96a5d207f4a6cc13725a06cbb@localhost:8081/oauth/token  \
    -d grant_type=password \
    -d username=demo \
    -d password=demo
```

## Dockerfile

The project uses the Spotify dockerfile-maven-plugin to build a Docker image each time you build the project with
`mvn clean package`.

Start a new container with:
```
docker run --rm -it -p 8081:8081 tschmitz-dev/spring-bootiful-authorization-server:1.0.0-SNAPSHOT
```

To override default client-id and secret start a container with:
```
docker run --rm -it -p 8081:8081 -e CLIENT_ID=yourid -e CLIENT_SECRET=yoursecret \
    tschmitz-dev/spring-bootiful-authorization-server:1.0.0-SNAPSHOT
```

To override properties from application.yml start a container with:
```
docker run --rm -it -p 8081:8081 tschmitz-dev/spring-bootiful-authorization-server:1.0.0-SNAPSHOT \
    --oauth.server.refresh-token-validity-seconds=7200 \
    --oauth.server.access-token-validity-seconds=3600 \
    --oauth.server.client-id=yourid \
    --oauth.server.client-secret=yoursecret
```
