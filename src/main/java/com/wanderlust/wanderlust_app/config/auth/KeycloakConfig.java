package com.wanderlust.wanderlust_app.config.auth;


import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    private final String serverUrl;
    private final String adminUsername;
    private final String adminPassword;
    private final String realm;

    public KeycloakConfig(
            @Value("${KEYCLOAK_AUTH_SERVER_URL}") String serverUrl,
            @Value("${KEYCLOAK_REALM}") String realm,
            @Value("${KEYCLOAK_ADMIN}") String adminUsername,
            @Value("${KEYCLOAK_ADMIN_PASSWORD}") String adminPassword
    ) {
        this.serverUrl = serverUrl;
        this.realm = realm;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    @Bean
    public Keycloak keycloakAdminClient() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId("admin-cli")
                .username(adminUsername)
                .password(adminPassword)
                .build();
    }

}
