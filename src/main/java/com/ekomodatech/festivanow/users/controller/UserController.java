package com.ekomodatech.festivanow.users.controller;

import com.ekomodatech.festivanow.users.entity.User;
import com.ekomodatech.festivanow.users.service.UserService;
import jakarta.ws.rs.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/test")
    public String test(){
        return "Hello World !";
    }

    @PostMapping
    public void addUser(@RequestBody User user){
        userService.addUser(user);
    }

    //Delete by username, taking into account that for keycloak a username should be unique
    @DeleteMapping
    public void deleteUser(@QueryParam("username") String username){
        userService.deleteUser(username);
    }

    @PostMapping("/auth")
    public String login(User user){
        return userService.login(user);
    }
}
