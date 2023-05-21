package com.example.projetetudiant.security.repository;

import com.example.projetetudiant.security.entities.appUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface appUserRep extends JpaRepository<appUser,String> {
    appUser findByUsername(String username);
    void deleteByUsername(String username);
    Page<appUser> findAllByUsernameContains(String key, Pageable pageable);


    Optional<Object> findById(Long id);
}
