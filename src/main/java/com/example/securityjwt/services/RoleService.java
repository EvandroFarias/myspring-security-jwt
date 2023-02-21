package com.example.securityjwt.services;

import com.example.securityjwt.models.RoleModel;
import com.example.securityjwt.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleModel createRole(RoleModel roleModel){
        try {
            if(roleRepository.findByName(roleModel.getName()).isPresent()){
                throw new IllegalArgumentException("Role already registered");
            }
            return roleRepository.save(roleModel);
        } catch (Exception ex){
            throw ex;
        }

    }
}
