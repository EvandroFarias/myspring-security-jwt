package com.example.securityjwt.controllers;

import com.example.securityjwt.dtos.LoginDTO;
import com.example.securityjwt.dtos.TokenResponse;
import com.example.securityjwt.dtos.RegistrationDTO;
import com.example.securityjwt.enums.RoleEnum;
import com.example.securityjwt.models.RoleModel;
import com.example.securityjwt.services.UserService;
import com.example.securityjwt.services.RoleService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @PostMapping("/register")
    public ResponseEntity<Object> register (@RequestBody RegistrationDTO registrationDTO){
        try {
            return ResponseEntity.ok(userService.register(registrationDTO));
        } catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception ex){
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate (@RequestBody LoginDTO loginDTO){
        try {
            return ResponseEntity.ok(userService.authenticate(loginDTO));
        } catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception ex){
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @GetMapping("/activate")
    public ResponseEntity<Object> activate(@RequestParam(name = "user") UUID userId) {
        try {
            return ResponseEntity.ok(userService.activate(userId));
        } catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception ex){
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/add-role")
    public ResponseEntity<Object> addRole(@RequestParam(value = "user") UUID userId, @RequestParam(value = "role") RoleEnum roleName){
        try {
            return ResponseEntity.ok().body(userService.addRole(userId, roleName));
        } catch (NoSuchElementException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex){
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-role")
    public ResponseEntity<Object> createRole(@RequestBody RoleModel role){
        try {
            return ResponseEntity.ok(roleService.createRole(role));
        } catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception ex){
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }
}
