package com.ekomodatech.festivanow.users.service;

import com.ekomodatech.festivanow.users.config.OIDCConfig;
import com.ekomodatech.festivanow.users.entity.LoginRequest;
import com.ekomodatech.festivanow.users.entity.LogoutRequest;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
@Slf4j
public class OIDCService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OIDCConfig oidcConfig;




    public Map login(@NonNull LoginRequest userLogin){
        val data = new LinkedMultiValueMap<String, String>();

        data.add("grant_type", "password");
        data.add("client_id", oidcConfig.getClient_id());
        data.add("client_secret", oidcConfig.getClient_secret());
        data.add("username", userLogin.getUsername());
        data.add("password", userLogin.getPassword());
        data.add("scope", "openid");

        val headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(data, headers);


        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                oidcConfig.getToken_url(),
                HttpMethod.POST,
                entity,
                Map.class
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Request to authorization server");
        }
    }

    public Map logout(@NonNull LogoutRequest logoutRequest, String token){
        val requestParams = new LinkedMultiValueMap<String, String>();
        requestParams.add("client_id", oidcConfig.getClient_id());
        requestParams.add("client_secret", oidcConfig.getClient_secret());
        requestParams.add("refresh_token", logoutRequest.getRefreshToken());


        val headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", token);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestParams, headers);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                oidcConfig.getToken_url(),
                HttpMethod.POST,
                entity,
                Map.class
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Request to authorization server");
        }
    }
}
