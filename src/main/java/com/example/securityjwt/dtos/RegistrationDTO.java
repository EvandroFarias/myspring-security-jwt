package com.example.securityjwt.dtos;

import com.example.securityjwt.models.UserModel;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {




    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public static UserModel dtoToModel(RegistrationDTO dto){
        return UserModel.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .enabled(false)
                .build();
    }
}
