package com.example.securityjwt.service;

import com.example.securityjwt.model.RoleModel;
import com.example.securityjwt.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleModel createRole(RoleModel roleModel){
        return roleRepository.save(roleModel);
    }
}
