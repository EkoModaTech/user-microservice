package com.ekomodatech.festivanow.users.controller;

import com.ekomodatech.festivanow.users.entity.User;
import com.ekomodatech.festivanow.users.service.UserService;
import jakarta.ws.rs.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.*;
import org.springframework.cloud.client.discovery.DiscoveryClient;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/test")
    public ServiceInstance test(){
        return discoveryClient.getInstances("api-gateway").get(0);
    }

    @GetMapping("/test2")
    public ServiceInstance test2(){
        return discoveryClient.getInstances("user-microservice").get(0);
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
