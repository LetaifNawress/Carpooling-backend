package com.javatechie.service;

import com.javatechie.Entity.Carpooling;
import com.javatechie.dao.CarpoolingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    @Autowired
    private CarpoolingDao carpoolingDAO;

    public void updateCarpooling(Carpooling carpooling) {
        carpoolingDAO.save(carpooling);
    }


}
