package com.javatechie.repository;

import com.javatechie.entity.Role;
import com.javatechie.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserApp,Integer> {
    Optional<UserApp> findByName(String username);

    Optional<UserApp> findById(Long id);

    Optional<UserApp> findByEmail(String username);

    List<UserApp> findByRole(Role role);



}
