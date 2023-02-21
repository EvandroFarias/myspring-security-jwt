package com.example.securityjwt.repository;

import com.example.securityjwt.enums.RoleEnum;
import com.example.securityjwt.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleModel, UUID> {

    Optional<RoleModel> findByName(RoleEnum name);
}
