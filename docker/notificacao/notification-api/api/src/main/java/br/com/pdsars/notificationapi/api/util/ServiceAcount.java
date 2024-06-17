package br.com.pdsars.notificationapi.api.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public record ServiceAcount(
    UUID keycloakId,
    String clientId,
    List<String> roles
) {

    public static ServiceAcount fromSecurityContext() {
        final var jwt = (Jwt) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();
        final var keycloakId = UUID.fromString(jwt.getSubject());
        // azp (Authorized Party) claim is the client id
        final var clientId = jwt.getClaimAsString("azp");
        @SuppressWarnings("unchecked") final var roles = (List<String>) jwt.getClaimAsMap("realm_access")
            .get("roles");

        final var username = jwt.getClaimAsString("preferred_username");
        if (username == null || !username.startsWith("service-account-")) {
            // FIXME: throw a nicer error message for the client
            throw new IllegalArgumentException("User is not a service account");
        }

        return new ServiceAcount(
            keycloakId,
            clientId,
            Collections.unmodifiableList(roles)
        );
    }
}
