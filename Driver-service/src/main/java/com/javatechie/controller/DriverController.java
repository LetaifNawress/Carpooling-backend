package com.javatechie.controller;

import com.javatechie.Entity.Carpooling;
import com.javatechie.dao.CarpoolingDao;
import com.javatechie.dto.CarpoolingDT0;
import com.javatechie.dto.ParticipationDTO;
import com.javatechie.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/driver")
public class DriverController {

    @Autowired
    private DriverService service;
    @Autowired
    private CarpoolingDao carpoolingDao;

    @PostMapping("/add/{driverId}")
    public ResponseEntity<String> addCarpooling(@PathVariable("driverId") Long driverId, @RequestBody CarpoolingDT0 carpoolingDto) {
        try {
            Carpooling carpooling = new Carpooling();
            carpooling.setIdDriver(driverId);
            carpooling.setCarMarke(carpoolingDto.getCarMarke());
            carpooling.setMatricule(carpoolingDto.getMatricule());
            carpooling.setMusic(carpoolingDto.getMusic());
            carpooling.setFume(carpoolingDto.getFume());
            carpooling.setDepart(carpoolingDto.getDepart());
            carpooling.setArrivage(carpoolingDto.getArrivage());
            carpooling.setBagage(carpoolingDto.getBagage());
            carpooling.setPlace(carpoolingDto.getPlace());
            carpoolingDao.save(carpooling);

            return ResponseEntity.ok("Carpooling ajouté avec succès");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Échec de l'ajout du covoiturage.");
        }
    }

    @GetMapping("/all")
    public List<Carpooling> getAllCarpoolings() {
        List<Carpooling> carpoolings = carpoolingDao.findAll();
       return carpoolings ;
    }
    @PostMapping("/addClient/{carpoolingId}")
    public ResponseEntity<String> addClientToCarpooling(
            @PathVariable("carpoolingId") Long carpoolingId,
            @RequestBody ParticipationDTO participationDTO
    ) {
        try {
            Carpooling carpooling = carpoolingDao.findCarpollingById(carpoolingId);

            if (carpooling != null && carpooling.getPlace() >= participationDTO.getNbr_place()
                    && carpooling.getBagage() >= participationDTO.getBagage()) {
                carpooling.getClientIds().add(participationDTO.getIdClient());
                carpooling.setPlace(carpooling.getPlace() - participationDTO.getNbr_place());
                carpooling.setBagage(carpooling.getBagage() - participationDTO.getBagage());
                service.updateCarpooling(carpooling);
                return ResponseEntity.ok("Client ajouté au covoiturage avec succès");
            } else {
                return ResponseEntity.badRequest().body("Impossible d'ajouter le client au covoiturage. Pas assez de places ou de bagages disponibles.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Une erreur est survenue lors de l'ajout du client au covoiturage.");
        }
    }
}
