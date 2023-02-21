package com.example.securityjwt.controllers;

import com.example.securityjwt.dtos.LoginDTO;
import com.example.securityjwt.dtos.TokenResponse;
import com.example.securityjwt.dtos.RegistrationDTO;
import com.example.securityjwt.models.RoleModel;
import com.example.securityjwt.services.UserService;
import com.example.securityjwt.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("create-role")
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
