package com.example.securityjwt.dtos;

import com.example.securityjwt.models.RoleModel;
import com.example.securityjwt.models.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserViewDTO {

    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
    private List<RoleModel> roles;

    public static UserViewDTO modelToDTO(UserModel userModel){
        return UserViewDTO.builder()
                .id(userModel.getId())
                .firstname(userModel.getFirstName())
                .lastname(userModel.getLastName())
                .email(userModel.getEmail())
                .roles(userModel.getRoles())
                .build();
    }
}
