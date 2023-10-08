package com.ekomodatech.festivanow.users.controller;

import com.ekomodatech.festivanow.users.entity.LoginRequest;
import com.ekomodatech.festivanow.users.entity.LogoutRequest;
import com.ekomodatech.festivanow.users.service.OIDCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private OIDCService loginService;

    @PostMapping("/login")
    public Map login(@RequestBody LoginRequest userLogin){
        return loginService.login(userLogin);
    }


    @PostMapping("/logout")
    public Map logout(@RequestBody LogoutRequest userLogout){
        return loginService.logout(userLogout);
    }
}
