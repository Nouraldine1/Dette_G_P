package com.nour.services;

import java.util.List;

import com.nour.entities.Compte;
import com.nour.repositories.CompteRepository;

public class CompteService {
    
    private CompteRepository compteRepository;

    public CompteService() {
    }

    public CompteService(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    public void create(Compte compte){
        compteRepository.insert(compte);
    }

    public List<Compte> findAll(){
        return compteRepository.select();
    }
}
