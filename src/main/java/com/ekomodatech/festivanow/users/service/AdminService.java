package com.ekomodatech.festivanow.users.service;

import com.ekomodatech.festivanow.users.config.keycloak.KeycloakConfig;
import com.ekomodatech.festivanow.users.model.entity.UserRoles;
import com.ekomodatech.festivanow.users.model.request.UpdateRoleRequest;
import lombok.val;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private KeycloakConfig keycloakConfig;

    @Autowired
    private Keycloak keycloak;

    @Autowired
    private UserService userService;

    private static final List<String> USER_ROLES = Arrays.stream(UserRoles.values())
            .map(Enum::name)
            .toList();



    public void updateRole(UpdateRoleRequest roleRequest){
        val user = this.userService.getUserByUsername(roleRequest.getUsername());
        changeUserRole(user, roleRequest.getRole());
    }

    protected void changeUserRole(UserResource user, UserRoles role){
        val userR = user.toRepresentation();
        val realmRoles = userR.getRealmRoles();

        if (realmRoles != null) {
            realmRoles.removeIf(AdminService.USER_ROLES::contains);
            realmRoles.add(role.name());
            userR.setRealmRoles(realmRoles);
        }
        user.update(userR);
    }
}
