package com.nour.views.impl;


import java.util.Scanner;

import com.nour.views.View;

import java.util.List;

public class ViewImpl implements View{

    protected Scanner scanner;

    public ViewImpl(Scanner scanner){
        this.scanner=scanner;
    }

    @Override
    public int choixSousMenu(String menuTxt, int valueSup) {
        Boolean ok = true;
        int choixSm;
        do {
            System.out.println(menuTxt);
            choixSm = scanner.nextInt();
            scanner.nextLine();
            ok = choixSm < 1 || choixSm > valueSup;
            if (ok) {
                System.out.println("Veuillez faire un bon choix");
            }
        } while (ok);
        return choixSm;
    }

    @Override
    public <T> void showListElements(List<T> list,String entity){
        if (list.isEmpty()) {
            System.out.println(entity + " n'existe encore");
        } else {
            list.stream().forEach(System.out::println);
        }
    }
    
    @Override
    public String obligatoire(String menuTxt, String errorTxt) {
        String var;
        do {
            System.out.println(menuTxt);
            var = this.scanner.nextLine();
            if (var.trim() == "") {
                System.out.println(errorTxt);
            }
        } while (var.trim() == "");
        return var;
    }
}
