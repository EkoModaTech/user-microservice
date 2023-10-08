package com.ekomodatech.festivanow.users.controller;

import com.ekomodatech.festivanow.users.entity.UserLogin;
import com.ekomodatech.festivanow.users.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public Map login(@RequestBody UserLogin userLogin){
        return loginService.getToken(userLogin);
    }
}
