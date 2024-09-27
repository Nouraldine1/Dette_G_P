package com.nour.views;
import java.util.List;
import java.util.Scanner;

import com.nour.entities.Compte;
import com.nour.entities.Dette;
import com.nour.enums.Etat;
import com.nour.enums.Role;
import com.nour.views.impl.ViewImpl;

public class DetteView extends ViewImpl{

    public DetteView(Scanner scanner) {
        super(scanner);
    }

    public int menu(){
        int choix;
        do {
            System.out.println("1: Créer un compte utilisateur");
            System.out.println("2: Désactiver/Activer un compte utilisateur");
            System.out.println("3: Les comptes utilisateurs");
            System.out.println("4: Retour au menu principal");
            System.out.println("5: Quitter");
            choix=scanner.nextInt();
            scanner.nextLine();
            if (choix<1 || choix>5) {
                System.out.println("Veuillez faire un bon choix");
            }
        } while (choix<1 || choix >5);
        return choix;
    }

    public Compte saisie(){
        String telephone,password,email;
        telephone=super.obligatoire("Veuillez donner le telephone du nouvel utilisateur", "Le telephone est obligatoire");
        password=super.obligatoire("Veuillez donner le mot de passe du nouvel utilisateur", "Le mot de passe est obligatoire");
        email=super.obligatoire("Veuillez donner l'email du nouvel utilisateur", "L'email est obligatoire");
        return new Compte(telephone, password, email, Etat.Activé);
    }

    public Dette getByReste(List<Dette> DetteList,String telephone){
        return DetteList.stream().filter(Dette -> Dette. getReste()==0).findFirst().orElse(null);
    }

    public Dette select(List<Dette> DetteList) {
        if (DetteList.isEmpty()) {
            System.out.println("Aucun Dette n'existe encore");
            return null;
        }
        System.out.println("Veuillez entrer le telephone du Dette ou 0 pour annuler");
        DetteList.stream().forEach(System.out::println);
        String telephone = scanner.nextLine();
        if (telephone.compareTo("0") != 0) {
            Dette Dette = this.getByReste(DetteList, telephone);
            boolean ok = Dette == null;
            while (ok) {
                System.out.println("Aucun Dette ne correspond à ce telephone");
                System.out.println("Veuillez entrer le telephone du Dette ou 0 pour annuler");
                telephone = scanner.nextLine();
                if (telephone.compareTo("0") == 0) {
                    ok = false;
                } else {
                    Dette = this.getByReste(DetteList, telephone);
                    ok = Dette == null;
                }
            }
            if (Dette==null) {
                System.out.println("Opération annulée");
            }
            return Dette;
        }
        System.out.println("Opération annulée");
        return null;
    }

    public Role selecRole(){
        int choix;
        Boolean ok;
        do {
            for (Role role : Role.values()) {
                System.out.println(role.ordinal()+1 + ": " + role);
            } 
            choix=scanner.nextInt();
            ok=choix<1 || choix >Role.values().length;
            if (ok) {
                System.out.println("Veuillez faire un bon choix");
            }
        }while (ok);
        return Role.values()[choix-1];
    }
}
