package com.nour.entities;

import com.nour.enums.Etat;
import com.nour.enums.Role;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)

public class Compte extends Entity{
    private String login,password,email;
    private Etat etat;
    private Role role;
    public Compte(String login, String password, String email,Etat etat) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.etat = etat;
    }
    public Compte() {
        //TODO Auto-generated constructor stub
    }
    
}
