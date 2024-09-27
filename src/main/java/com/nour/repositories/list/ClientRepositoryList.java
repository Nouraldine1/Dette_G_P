package com.nour.repositories.list;

import java.util.List;

import com.nour.core.repositories.impl.RepositoryListImpl;
import com.nour.entities.Client;
import com.nour.repositories.ClientRepository;

public class ClientRepositoryList extends RepositoryListImpl <Client> implements ClientRepository{
    @Override
    public List<Client> selectNoneAccountClients(){
        return super.list.stream().filter(client -> client.getCompte()==null).toList();
    }

    @Override
    public Client getByTelephone(String telephone) {
        return super.list.stream().filter(client -> client.getTelephone().compareTo(telephone)==0).findFirst().orElse(null);
    }
}
