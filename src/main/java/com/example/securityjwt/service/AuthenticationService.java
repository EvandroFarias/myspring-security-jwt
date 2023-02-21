package com.example.securityjwt.service;

import com.example.securityjwt.auth.AuthenticationRequest;
import com.example.securityjwt.auth.AuthenticationResponse;
import com.example.securityjwt.auth.RegisterRequest;
import com.example.securityjwt.enums.RoleEnum;
import com.example.securityjwt.model.RoleModel;
import com.example.securityjwt.model.UserModel;
import com.example.securityjwt.repository.RoleRepository;
import com.example.securityjwt.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public UserModel register(RegisterRequest request) {
        var user = UserModel.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(false)
                .build();
        return userRepository.save(user);

//        var jwtToken = jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public String activate(UUID userId) {
        UserModel user = userRepository.findById(userId).orElseThrow(()-> new NoSuchElementException("User not found"));
        user.setEnabled(true);

        user.getRoles().add(roleRepository.findByName(RoleEnum.USER)
                .orElseThrow(() -> new NoSuchElementException("Role does not exists")));

        userRepository.save(user);
        return "User " + user.getEmail() + " activated";
    }
}
