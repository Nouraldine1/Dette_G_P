package com.nour.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)

public class Article extends Entity{
    private String libelle;
    private double prix;
    private int qteStock;
    
    public Article(String libelle, double prix, int qteStock) {
        this.libelle = libelle;
        this.prix = prix;
        this.qteStock = qteStock;
    }

    public Article() {
        //TODO Auto-generated constructor stub
    }
    
}
