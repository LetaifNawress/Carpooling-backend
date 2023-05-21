package com.example.projetetudiant.dao;

import com.example.projetetudiant.entities.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit,Long> {

    Page<Produit> findAllByNomContains(String key, Pageable pageable);
}
