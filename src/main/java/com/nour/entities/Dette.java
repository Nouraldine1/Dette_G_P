package com.nour.entities;

import java.time.LocalDate;

import com.nour.enums.DetteEtat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)

public class Dette extends Entity{

    public Dette(LocalDate date, double montant, double paye, Client client) {
        this.date = date;
        this.montant = montant;
        this.paye = paye;
        this.reste = montant - paye;
        this.client = client;
        this.detteEtat = montant == paye ? DetteEtat.Archivé : DetteEtat.Désarchivé;
    }

    public Dette() {
        //TODO Auto-generated constructor stub
    }

    private LocalDate date;
    private double montant;
    private double paye;
    private double reste;
    private Client client;
    private DetteEtat detteEtat;

    public void setPaye(double paye){
        this.paye+=paye;
        this.reste=this.reste-paye;
    }
}
