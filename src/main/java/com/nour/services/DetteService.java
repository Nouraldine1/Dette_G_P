package com.nour.services;

import java.util.List;

import com.nour.entities.Dette;
import com.nour.repositories.DetteRepository;

public class DetteService {
    
    private DetteRepository detteRepository;

    public DetteService(DetteRepository detteRepository) {
        this.detteRepository = detteRepository;
    }

    public DetteService() {
    }

    public void create( Dette  Dette){
        detteRepository.insert( Dette);
    }

    public List< Dette> findAll(){
        return detteRepository.select();
    }
}
