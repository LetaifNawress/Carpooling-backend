package com.example.projetetudiant.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduit;
    @NotEmpty
    @Size(min = 4,max = 15)
    private String nom;
    @Min(value = 10)
    @Max(value = 10000)
    private Double prixProduit;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    private Date dateCreation;

@ManyToOne
    private categorie categorie;
    // ... constructors and other methods ...

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}

