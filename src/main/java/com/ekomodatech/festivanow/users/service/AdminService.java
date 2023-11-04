package com.ekomodatech.festivanow.users.service;

import com.ekomodatech.festivanow.users.config.keycloak.KeycloakConfig;
import com.ekomodatech.festivanow.users.model.dto.UserDTO;
import com.ekomodatech.festivanow.users.model.entity.UserRoles;
import com.ekomodatech.festivanow.users.model.request.UpdateRoleRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private KeycloakConfig keycloakConfig;

    @Autowired
    private Keycloak keycloak;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<RoleRepresentation> USER_ROLES ;



    public void updateRole(UpdateRoleRequest roleRequest){
        val user = this.userService.getUserByUsername(roleRequest.getUsername());
        changeUserRole(user, roleRequest.getRole());
    }

    protected void changeUserRole(UserResource user, UserRoles role) {
        user.roles().realmLevel().remove(USER_ROLES);
        user.roles().realmLevel().add(List.of(keycloak.realm(keycloakConfig.getRealm())
                .roles().get(role.name()).toRepresentation()));
    }

    public Iterable<UserDTO> getAllUsers() {
        val users = keycloak.realm(keycloakConfig.getRealm()).users().list();
        return users.stream()
                .map(userR -> objectMapper.convertValue(userR, UserDTO.class))
                .collect(Collectors.toSet());
    }


    @PostConstruct
    private void initializeRoles(){
        this.USER_ROLES = Arrays.stream(UserRoles.values())
                .map(r -> keycloak.realm(keycloakConfig.getRealm()).roles().get(r.name()).toRepresentation())
                .toList();
    }
}
