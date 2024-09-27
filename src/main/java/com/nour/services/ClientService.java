package com.nour.services;

import java.util.List;

import com.nour.entities.Client;
import com.nour.repositories.ClientRepository;

public class ClientService {

    private ClientRepository clientRepository;

    public ClientService() {
    }

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void create(Client client) {
        clientRepository.insert(client);
    }

    public List<Client> findAll() {
        return clientRepository.select();
    }

    public List<Client> findNoneAccountClients() {
        return clientRepository.selectNoneAccountClients();
    }
}
