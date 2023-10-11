package com.ekomodatech.festivanow.users.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "keycloak-config")
@Component
public class KeycloakConfig {
    private String clientId;
    private String clientSecret;
    private String url;
    private String realm;
}
