package com.example.securityjwt.repositories;

import com.example.securityjwt.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {

    Optional<UserModel> findByEmail(String email);
    Boolean existsByEmail(String email);
}
