package com.nhom2.asmsof3021.security;

import com.nhom2.asmsof3021.security.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmailAndPassword(String email,String password);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndRole(String email, Role role);
}
