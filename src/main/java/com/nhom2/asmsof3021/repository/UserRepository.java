package com.nhom2.asmsof3021.repository;

import com.nhom2.asmsof3021.model.User;
import com.nhom2.asmsof3021.security.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmailAndPassword(String email,String password);
    @Query("select u from User u where u.email = ?1 or u.profileName = ?1")
//    Optional<User> findByEmailOrUsername(String username);
    Optional<User> findByEmail(String username);
    Optional<User> findByEmailAndRole(String email, Role role);
    @Query("select u from User u where u.email= ?1")
    Optional<User> findUserByEmail(String email);
    @Query("select u from User u where u.username=?1")
    Optional<User> findUserByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
}
