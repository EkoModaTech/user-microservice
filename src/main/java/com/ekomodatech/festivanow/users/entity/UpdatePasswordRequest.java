package com.ekomodatech.festivanow.users.entity;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class UpdatePasswordRequest {
    @NotNull
    @NotBlank
    private String newPassword;
}
