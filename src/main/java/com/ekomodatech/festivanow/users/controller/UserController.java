package com.ekomodatech.festivanow.users.controller;

import com.ekomodatech.festivanow.users.entity.User;
import com.ekomodatech.festivanow.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.ok("User created");
    }

    //Delete by username, taking into account that for keycloak a username should be unique
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestBody Map<String, String> body){
        userService.deleteUser(body.get("username"));
        return ResponseEntity.ok("Deleted");
    }

}
