package com.nour.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)

public class Client extends Entity{
    
    private String surname,adresse,telephone;
    private Compte compte;

    public Client(String surname, String adresse, String telephone) {
        this.surname = surname;
        this.adresse = adresse;
        this.telephone = telephone;
    }

    public Client() {
        //TODO Auto-generated constructor stub
    }

}
