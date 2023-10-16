package com.ekomodatech.festivanow.users.config.keycloak;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakInstanceConfig {
    @Autowired
    KeycloakConfig keycloakInitializerConfigurationProperties;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .realm(keycloakInitializerConfigurationProperties.getRealm())
                .clientId(keycloakInitializerConfigurationProperties.getClientId())
                .clientSecret(keycloakInitializerConfigurationProperties.getClientSecret())
                .serverUrl(keycloakInitializerConfigurationProperties.getUrl())
                .build();
    }
}
