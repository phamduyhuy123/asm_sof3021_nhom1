package com.nhom2.asmsof3021.security;

import com.nhom2.asmsof3021.security.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmailAndPassword(String email,String password);
    @Query("select u from User u where u.email = ?1 or u.profileName = ?1")
    Optional<User> findByEmailOrUsername(String username);
//    Optional<User> findByEmail(String username);
    Optional<User> findByEmailAndRole(String email, Role role);
}
