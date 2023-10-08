package com.ekomodatech.festivanow.users.entity;

import lombok.Data;

@Data
public class LogoutRequest {
    private String refreshToken;
}
