package com.nour.views;
import java.util.List;
import java.util.Scanner;

import com.nour.entities.Compte;
import com.nour.enums.Etat;
import com.nour.enums.Role;
import com.nour.views.impl.ViewImpl;

public class CompteView extends ViewImpl{

    public CompteView(Scanner scanner) {
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
        String login,password,email;
        login=super.obligatoire("Veuillez donner le login du nouvel utilisateur", "Le login est obligatoire");
        password=super.obligatoire("Veuillez donner le mot de passe du nouvel utilisateur", "Le mot de passe est obligatoire");
        email=super.obligatoire("Veuillez donner l'email du nouvel utilisateur", "L'email est obligatoire");
        return new Compte(login, password, email, Etat.Activé);
    }

    public Compte getByLogin(List<Compte> compteList,String login){
        return compteList.stream().filter(compte -> compte. getLogin().compareTo(login)==0).findFirst().orElse(null);
    }

    public Compte select(List<Compte> compteList) {
        if (compteList.isEmpty()) {
            System.out.println("Aucun compte n'existe encore");
            return null;
        }
        System.out.println("Veuillez entrer le login du compte ou 0 pour annuler");
        compteList.stream().forEach(System.out::println);
        String login = scanner.nextLine();
        if (login.compareTo("0") != 0) {
            Compte compte = this.getByLogin(compteList, login);
            boolean ok = compte == null;
            while (ok) {
                System.out.println("Aucun compte ne correspond à ce login");
                System.out.println("Veuillez entrer le login du compte ou 0 pour annuler");
                login = scanner.nextLine();
                if (login.compareTo("0") == 0) {
                    ok = false;
                } else {
                    compte = this.getByLogin(compteList, login);
                    ok = compte == null;
                }
            }
            if (compte==null) {
                System.out.println("Opération annulée");
            }
            return compte;
        }
        System.out.println("Opération annulée");
        return null;
    }

    public Role selectRole(){
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
