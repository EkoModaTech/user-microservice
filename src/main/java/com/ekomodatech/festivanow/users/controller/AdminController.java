package com.ekomodatech.festivanow.users.controller;

import com.ekomodatech.festivanow.users.model.dto.StatusResponseInfo;
import com.ekomodatech.festivanow.users.model.request.UpdateRoleRequest;
import com.ekomodatech.festivanow.users.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@Validated
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping
    public StatusResponseInfo changeUserRole(@Valid @RequestBody UpdateRoleRequest roleRequest) {
        this.adminService.updateRole(roleRequest);
        return StatusResponseInfo.builder().status(HttpStatus.OK)
                .message("Role Updated").timestamp(LocalDateTime.now())
                .build();
    }
}