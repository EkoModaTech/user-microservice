package com.ekomodatech.festivanow.users.entity;

import lombok.Data;

@Data
public class User {
    //TODO add roles and personal things (names, surnames, etc)
    private String username;
    private String email;
    private String password;
}
