package com.shoplite.userservice.repository;

import com.shoplite.userservice.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(@NotBlank String email);
    boolean existsByUsername(String userName);
}
