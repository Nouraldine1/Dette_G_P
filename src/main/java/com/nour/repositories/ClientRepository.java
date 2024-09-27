package com.nour.repositories;

import java.util.List;

import com.nour.core.repositories.Repository;
import com.nour.entities.Client;

public interface ClientRepository extends Repository<Client>{
    List<Client> selectNoneAccountClients();
    Client getByTelephone(String telephone);
}
