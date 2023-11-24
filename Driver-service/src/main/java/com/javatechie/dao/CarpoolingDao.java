package com.javatechie.dao;

import com.javatechie.Entity.Carpooling;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarpoolingDao extends JpaRepository<Carpooling, Long> {


    Carpooling findCarpollingById(Long carpoolingId);
}
