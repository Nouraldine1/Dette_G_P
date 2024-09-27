package com.nour.entities;

import lombok.Data;

@Data

public class Entity {
    private static int nbrObj;
    private int id;
    
    public void setId(int id) {
        this.id = id;
    }
    public void setId() {
        nbrObj+=1;
        this.id = nbrObj;
    }

    
}
