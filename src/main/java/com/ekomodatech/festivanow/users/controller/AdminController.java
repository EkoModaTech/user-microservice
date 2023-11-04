package com.ekomodatech.festivanow.users.controller;

import com.ekomodatech.festivanow.users.model.dto.StatusResponseInfo;
import com.ekomodatech.festivanow.users.model.dto.UserDTO;
import com.ekomodatech.festivanow.users.model.request.UpdateRoleRequest;
import com.ekomodatech.festivanow.users.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAnyAuthority('ADMIN')")
@Validated
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PutMapping("/role")
    public StatusResponseInfo changeUserRole(@Valid @RequestBody UpdateRoleRequest roleRequest) {
        this.adminService.updateRole(roleRequest);
        return StatusResponseInfo.builder().status(HttpStatus.OK)
                .message("Role Updated").timestamp(LocalDateTime.now())
                .build();
    }

    @GetMapping("/users")
    public Iterable<UserDTO> getAll(){
        return adminService.getAllUsers();
    }
}