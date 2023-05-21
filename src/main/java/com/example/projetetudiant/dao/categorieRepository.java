package com.example.projetetudiant.dao;

import com.example.projetetudiant.entities.categorie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface categorieRepository extends JpaRepository<categorie,Long> {
    Page<categorie> findAllByNomContains(String key, Pageable pageable);

}
