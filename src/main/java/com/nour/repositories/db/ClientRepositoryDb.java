package com.nour.repositories.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nour.core.repositories.impl.RepositoryDbImpl;
import com.nour.entities.Client;
import com.nour.repositories.ClientRepository;
import com.nour.repositories.CompteRepository;

public class ClientRepositoryDb extends RepositoryDbImpl<Client> implements ClientRepository {

    private CompteRepository compteRepository;

    public ClientRepositoryDb(CompteRepository compteRepository){
        super.repositoryName="client";
        super.clazz=Client.class;
        this.compteRepository = compteRepository;
    }

    @Override
    public boolean insert(Client client) {
        int nbre = 0;
        String sql="INSERT INTO `client` (`surname`, `adresse`, `telephone`) VALUES (?, ?, ?);";
        try {
            this.getConnection();
            this.initPreparedStatement(sql);
            this.preparedStatement.setString(1, client.getSurname());
            this.preparedStatement.setString(2, client.getAdresse());
            this.preparedStatement.setString(3, client.getTelephone());
            nbre = this.executeUpdate();
            ResultSet resultSet=this.preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                client.setId(resultSet.getInt(1));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return nbre == 0;
    }

    

    @Override
    public List<Client> selectNoneAccountClients() {
        List<Client> clients=new ArrayList<>();
        String sql=String.format("SELECT * FROM %s where compteId IS NULL", super.repositoryName);
        try {
            this.getConnection();
            this.initPreparedStatement(sql);
            ResultSet resultSet = this.executeQuery();
            while (resultSet.next()) {
                try {
                    clients.add(convertToObject(resultSet,Client.class));
                } catch (ReflectiveOperationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public Client getByTelephone(String telephone) {
        Client client=null;
        try {
            String sql= "SELECT * FROM client WHERE telephone LIKE ?";
            this.getConnection();
            this.initPreparedStatement(sql);
            this.preparedStatement.setString(1, telephone);
            ResultSet resultSet=this.executeQuery();
            if (resultSet.next()) {
                try {
                    client=convertToObject(resultSet,Client.class);
                } catch (ReflectiveOperationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }


}
