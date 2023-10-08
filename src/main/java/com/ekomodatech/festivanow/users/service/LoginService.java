package com.ekomodatech.festivanow.users.service;

import com.ekomodatech.festivanow.users.entity.UserLogin;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class LoginService {
    @Autowired
    private RestTemplate restTemplate;

    private final String URL = "http://keycloak:8080/realms/test-realm/protocol/openid-connect/token";


    public Map getToken(@NonNull UserLogin userLogin){
        val data = new LinkedMultiValueMap<String, String>();

        data.add("grant_type", "password");
        data.add("client_id", "apisix");
        data.add("client_secret", "KhD0TYWUQ7fuwhHA2iDw1Fcuzr2RpbZE");
        data.add("username", userLogin.getUsername());
        data.add("password", userLogin.getPassword());

        val headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(data, headers);


        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                entity,
                Map.class
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            // Handle error
            log.error(String.valueOf(responseEntity.getStatusCodeValue()));
            throw new RuntimeException("Nose");
        }
    }
}
