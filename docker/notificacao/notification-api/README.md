# Notification API
Microservice for sending notification to users.


# Features
- Scales vertically and horizontally
- Supports High Availability with RabbitMQ


# Design
Service for sending notification for users which scales vertically and
horizontally with high availability.


# ROADMAP
- [X] Migrate existing code from PDSA API
- [ ] MVP with same functionality as the notification service with it
  is embedded into the PDSA-RS API;
- [ ] Simplify usage interface;
- [ ] Migrate SNS logging from lambda;
- [ ] Have message templates;
- [ ] Work with any OAuth2 identity server, not only Keycloak;
- [ ] Make RabbitMQ optional;


# Estado atual
- tem a primeira versao
- NAO suporta as categorias/preferencias de usuarios

# Futuro
- Trabalahndo na api nova baseada no API de notifaccoes do android
- Para reduzir o boilerplate sera criado uma blioteca java
- Biblioteca com metodos mockados sera criada ate amanha a noite, assim glenio consegue seguir o trabalho enquatno a biblioteca eh finalizada


## Instaling the client library

```sh
$ ./gradle client-library:publishToMavenLocal
```

