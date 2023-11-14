package com.javatechie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
public class CarpoolingDT0 {
  public long  IdDriver ;
  public long  Num ;
  public String CarMarke ;
  private String HeureDepart ;
  public String Matricule ;
  public Boolean Music ;
  public Boolean Fume ;
  public String Depart ;
  public String Arrivage ;
  public int Bagage ;
  public int Place ;
  private Long clientIds;


}
