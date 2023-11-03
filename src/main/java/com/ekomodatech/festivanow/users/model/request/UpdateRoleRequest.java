package com.ekomodatech.festivanow.users.model.request;

import com.ekomodatech.festivanow.users.model.entity.UserRoles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRoleRequest {
    @NotBlank
    @NotNull
    private String username;

    @NotBlank
    @NotNull
    private UserRoles role;
}
