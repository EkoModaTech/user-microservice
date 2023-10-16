package com.ekomodatech.festivanow.users.service;

import com.ekomodatech.festivanow.users.config.keycloak.KeycloakConfig;
import com.ekomodatech.festivanow.users.entity.UpdatePasswordRequest;
import com.ekomodatech.festivanow.users.entity.User;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class UserService {
    //REF: https://gist.github.com/thomasdarimont/c4e739c5a319cf78a4cff3b87173a84b
    @Autowired
    private Keycloak keycloak;

    @Autowired
    private KeycloakConfig keycloakConfig;

    public void addUser(@NonNull User user){

        val credentials = new CredentialRepresentation();
        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(user.getPassword());

        val userR = new UserRepresentation();
        userR.setUsername(user.getUsername());
        userR.setEmail(user.getEmail());
        userR.setCredentials(List.of(credentials));
        userR.setEnabled(true);

        try(val response = keycloak.realm(keycloakConfig.getRealm()).users().create(userR)){
            val status = HttpStatus.valueOf(response.getStatus());
            if(status.is4xxClientError()){
                throw new ResponseStatusException(status, status.getReasonPhrase());
            }
        }
    }



    public void deleteUser(String username) {
        try(val response = keycloak.realm(keycloakConfig.getRealm()).users().delete(username)){
            val status = HttpStatus.valueOf(response.getStatus());
            if(status.is4xxClientError()){
                throw new ResponseStatusException(status, status.getReasonPhrase());
            }
        }
    }

    public void updatePassword(Jwt jwt, UpdatePasswordRequest request) {
        @NonNull val username = (String) jwt.getClaims().get("preferred_username");

        val credentials = new CredentialRepresentation();
        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(request.getNewPassword());

        val id = keycloak.realm(keycloakConfig.getRealm()).users().list().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .map(UserRepresentation::getId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Username not EXIST"));


        keycloak.realm(keycloakConfig.getRealm()).users().get(id).resetPassword(credentials);
    }
}
