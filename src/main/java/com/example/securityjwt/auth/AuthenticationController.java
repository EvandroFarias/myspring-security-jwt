package com.example.securityjwt.auth;

import com.example.securityjwt.enums.RoleEnum;
import com.example.securityjwt.model.RoleModel;
import com.example.securityjwt.service.AuthenticationService;
import com.example.securityjwt.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final RoleService roleService;

    @PostMapping("/register")
    public ResponseEntity<Object> register (
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/activate")
    public ResponseEntity<Object> activate(@RequestParam(name = "user") UUID userId) {
        return ResponseEntity.ok(authenticationService.activate(userId));
    }

    @PostMapping("create-role")
    public ResponseEntity<Object> createRole(@RequestBody RoleModel role){
        return ResponseEntity.ok(roleService.createRole(role));
    }
}
