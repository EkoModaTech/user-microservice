package com.ekomodatech.festivanow.users.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "keycloak-config")
@Component
public class KeycloakConfig {
    @Getter(AccessLevel.NONE)
    private boolean initializeOnStartup;

    public boolean initializeOnStartup() {
        return initializeOnStartup;
    }

    private String masterRealm;
    private String applicationRealm;
    private String clientId;
    private String username;
    private String password;
    private String url;
}
