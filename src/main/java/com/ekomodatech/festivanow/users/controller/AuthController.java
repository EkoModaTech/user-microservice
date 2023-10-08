package com.ekomodatech.festivanow.users.controller;

import com.ekomodatech.festivanow.users.entity.LoginRequest;
import com.ekomodatech.festivanow.users.entity.RefreshTokenRequest;
import com.ekomodatech.festivanow.users.service.OIDCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private OIDCService oidcService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest userLogin){
        return oidcService.login(userLogin);
    }

    @PostMapping("/refresh")
    public Map<String, String> refresh(@RequestBody RefreshTokenRequest token){
        return oidcService.refreshToken(token.getRefreshToken());
    }


    @PostMapping("/logout")
    public Map<String, String> logout(@RequestBody RefreshTokenRequest userLogout, @RequestHeader("Authorization") String token){
        return oidcService.logout(userLogout, token);
    }
}
