package com.example.securityjwt.services;

import com.example.securityjwt.dtos.LoginDTO;
import com.example.securityjwt.dtos.TokenResponse;
import com.example.securityjwt.dtos.RegistrationDTO;
import com.example.securityjwt.dtos.UserViewDTO;
import com.example.securityjwt.enums.RoleEnum;
import com.example.securityjwt.models.RoleModel;
import com.example.securityjwt.models.UserModel;
import com.example.securityjwt.repositories.RoleRepository;
import com.example.securityjwt.repositories.UserRepository;
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
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public UserViewDTO register(RegistrationDTO registrationDTO) {

        try {
            if (userRepository.existsByEmail(registrationDTO.getEmail())) {
                throw new IllegalArgumentException("Email already taken");
            }
            registrationDTO.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));

            return UserViewDTO.modelToDTO(userRepository.save(RegistrationDTO.dtoToModel(registrationDTO)));
        }
        catch (Exception ex) {
            throw ex;
        }
    }

    public TokenResponse authenticate(LoginDTO loginDTO) {
        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getPassword()
                    )
            );
            var user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            var jwtToken = jwtService.generateToken(user);
            return TokenResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (Exception ex){
            throw ex;
        }
    }

    public UserViewDTO activate(UUID userId) {
        UserModel user = userRepository.findById(userId).orElseThrow(()-> new NoSuchElementException("User not found"));

        if(user.isEnabled()){
            throw new IllegalArgumentException("User is already active");
        }

        user.setEnabled(true);
        user.getRoles().add(roleRepository.findByName(RoleEnum.USER)
                .orElseThrow(() -> new NoSuchElementException("Role does not exists")));

        userRepository.save(user);
        return UserViewDTO.modelToDTO(user);
    }

    public UserViewDTO addRole(UUID userId, RoleEnum roleName){
        UserModel user = userRepository.findById(userId)
                .orElseThrow(()-> new NoSuchElementException("User not found"));
        RoleModel role = roleRepository.findByName(roleName)
                .orElseThrow(()-> new NoSuchElementException("Role does not exist"));

        if(!user.isEnabled()){
            throw new IllegalArgumentException("Cannot assign a role to inactive user");
        }
        if(user.getRoles().contains(role)){
            throw new IllegalArgumentException("User already have this role");
        }


        user.getRoles().add(role);
        return UserViewDTO.modelToDTO(userRepository.save(user));
    }
}
