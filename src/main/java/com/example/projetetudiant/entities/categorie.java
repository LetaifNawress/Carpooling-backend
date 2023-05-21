package com.example.projetetudiant.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
@Entity
@Data
@AllArgsConstructor
public class categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCat;
    @NotEmpty
    @Size(min = 4,max = 15)
    private String nom;
    @NotEmpty
    @Size(min = 4,max = 20)
    private String descriptionCat;
    @OneToMany(mappedBy = "categorie", fetch = FetchType.EAGER)
    private List<Produit> produits;


    public categorie() {
    }

    public String getNom() {
        return nom;
    }

    public Long getIdCat() {
        return idCat;
    }
}
