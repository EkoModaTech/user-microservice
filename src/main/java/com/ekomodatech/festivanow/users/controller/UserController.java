package com.ekomodatech.festivanow.users.controller;

import com.ekomodatech.festivanow.users.model.request.UpdatePasswordRequest;
import com.ekomodatech.festivanow.users.model.entity.User;
import com.ekomodatech.festivanow.users.model.dto.UserDTO;
import com.ekomodatech.festivanow.users.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Iterable<UserDTO> getAll(){
        return userService.getAllUsers();
    }

    @PostMapping
    @ResponseBody public ResponseEntity<String> addUser(@Valid @RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.ok("User created");
    }

    @PutMapping
    public ResponseEntity<Map<String, String>> updatePassword(@AuthenticationPrincipal @NonNull Jwt jwt, @Valid @RequestBody UpdatePasswordRequest request){
        userService.updatePassword(jwt ,request);
        return ResponseEntity.ok(Map.of("Message", "Updated Password"));
    }

    //Delete by username, taking into account that for keycloak a username should be unique
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestBody Map<String, String> body){
        userService.deleteUser(body.get("username"));
        return ResponseEntity.ok("Deleted");
    }

}
