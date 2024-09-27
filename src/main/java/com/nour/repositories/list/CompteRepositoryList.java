package com.nour.repositories.list;

import com.nour.core.repositories.impl.RepositoryListImpl;
import com.nour.entities.Compte;
import com.nour.repositories.CompteRepository;

public class CompteRepositoryList extends RepositoryListImpl <Compte> implements CompteRepository{
    public Compte getCompteById(int id){
        return null;
    }
}
