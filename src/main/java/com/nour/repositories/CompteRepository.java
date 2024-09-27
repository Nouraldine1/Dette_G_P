package com.nour.repositories;

import com.nour.core.repositories.Repository;
import com.nour.entities.Compte;

public interface CompteRepository extends Repository<Compte>{
    Compte getCompteById(int id);
}
