package com.javatechie.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Carpooling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private long  IdDriver ;
    private long  Num ;
    private String CarMarke ;
    private String Matricule ;
    private Boolean Music ;
    private Boolean Fume ;
    private String Depart ;
    private String HeureDepart ;
    private String Arrivage ;
    private int Bagage ;
    private int Place ;
    @ElementCollection
    private List<Long> clientIds;

}
