# Spring Bootiful Authorization Server

A simple OAuth2 Authorization Server that provides an in-memory client details service with only one client. 

Get a token with curl:
```
curl ce920188d47a2f51d7c6:8b562ac7f64785c96a5d207f4a6cc13725a06cbb@localhost:8081/oauth/token  \
    -d grant_type=password \
    -d username=demo \
    -d password=demo
```
