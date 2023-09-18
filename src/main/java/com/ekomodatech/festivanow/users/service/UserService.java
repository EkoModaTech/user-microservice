package com.ekomodatech.festivanow.users.service;

import com.ekomodatech.festivanow.users.entity.User;
import lombok.NonNull;
import lombok.val;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private Keycloak keycloak;

    //TODO add REALM as ENV
    private static final String REALM = "test-realm";

    public void addUser(@NonNull User user){
        val credentials = new CredentialRepresentation();
        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(user.getPassword());

        val userR = new UserRepresentation();
        userR.setUsername(user.getUsername());
        userR.setEmail(user.getEmail());
        userR.setCredentials(List.of(credentials));
        userR.setEnabled(true);

        //TODO Handle exception
        keycloak.realm(REALM).users().create(userR);
    }


    public void deleteUser(String username) {
        //TODO HANDLE EXCEPTION
        keycloak.realm(REALM).users().delete(username);
    }

    public String login(User user){
        val instance = Keycloak.getInstance("http://keycloak:8080/",
                REALM, user.getUsername(), user.getPassword(),"admin-cli", "zchEGXgbrTEPMQGakf9yhwRGnmcc6KMc");
        val tokenM = instance.tokenManager();
        return tokenM.getAccessToken().getToken();
    }
}
