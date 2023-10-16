package com.ekomodatech.festivanow.users.config;

import com.ekomodatech.festivanow.users.config.oidc.OIDCConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GlobalConfig {

    @Autowired
    private OIDCConfig oidcConfig;
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withIssuerLocation(oidcConfig.getIssuer_url()).build();
    }


}
