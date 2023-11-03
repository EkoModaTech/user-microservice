package com.ekomodatech.festivanow.users.service;

import com.ekomodatech.festivanow.users.config.keycloak.KeycloakConfig;
import com.ekomodatech.festivanow.users.model.request.UpdatePasswordRequest;
import com.ekomodatech.festivanow.users.model.entity.User;
import com.ekomodatech.festivanow.users.model.entity.UserRoles;
import com.ekomodatech.festivanow.users.model.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    //REF: https://gist.github.com/thomasdarimont/c4e739c5a319cf78a4cff3b87173a84b
    @Autowired
    private Keycloak keycloak;

    @Autowired
    private KeycloakConfig keycloakConfig;

    @Autowired
    private ObjectMapper objectMapper;

    public void addUser(@NonNull User user){

        val credentials = new CredentialRepresentation();
        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(user.getPassword());

        val userR = new UserRepresentation();
        userR.setUsername(user.getUsername());
        userR.setEmail(user.getEmail());
        userR.setCredentials(List.of(credentials));
        userR.setEnabled(true);
        //userR.setRealmRoles(List.of(UserRoles.CLIENT.name()));

        try(val response = keycloak.realm(keycloakConfig.getRealm()).users().create(userR)){
            val status = HttpStatus.valueOf(response.getStatus());
            if(status.is4xxClientError()){
                throw new ResponseStatusException(status, status.getReasonPhrase());
            }
            this.addRoleToUser(CreatedResponseUtil.getCreatedId(response), UserRoles.CLIENT);
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


    protected void addRoleToUser(@NonNull String userId,@NonNull UserRoles role){
        val realm = keycloak.realm(keycloakConfig.getRealm());
        val realmRole = realm.roles().get(role.name()).toRepresentation();
        val user = realm.users().get(userId);
        user.roles().realmLevel().add(List.of(realmRole));
    }

    protected UserResource getUserByUsername(String username){
        val user = keycloak.realm(keycloakConfig.getRealm()).users().list().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Username not EXIST"));

        return keycloak.realm(keycloakConfig.getRealm()).users().get(user.getId());
    }

    public Iterable<UserDTO> getAllUsers() {
        val users = keycloak.realm(keycloakConfig.getRealm()).users().list();
        return users.stream()
                .map(userR -> objectMapper.convertValue(userR, UserDTO.class))
                .collect(Collectors.toSet());
    }
}
