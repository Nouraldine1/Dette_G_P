package com.nour.views;
import java.util.List;
import java.util.Scanner;

import com.nour.entities.Client;
import com.nour.enums.Role;
import com.nour.views.impl.ViewImpl;

public class ClientView extends ViewImpl{

    public ClientView(Scanner scanner) {
        super(scanner);
    }

    public int menu(){
        int choix;
        do {
            System.out.println("1: Créer un client");
            System.out.println("2: Lister les clients");
            System.out.println("3: Retour au menu principal");
            System.out.println("4: Quitter");
            choix=scanner.nextInt();
            scanner.nextLine();
            if (choix<1 || choix>4) {
                System.out.println("Veuillez faire un bon choix");
            }
        } while (choix<1 || choix >5);
        return choix;
    }

    public Client saisie(){
        String surname,adresse,telephone;
        surname=super.obligatoire("Veuillez donner le surname du client", "Le surname est obligatoire");
        adresse=super.obligatoire("Veuillez donner l'adresse du client", "L'adresse' est obligatoire");
        telephone=super.obligatoire("Veuillez donner le telephone du client", "Le telephone est obligatoire");
        return new Client(surname, adresse, telephone);
    }

    public Client getByTelephone(List<Client> clientList,String telephone){
        return clientList.stream().filter(client -> client. getTelephone().compareTo(telephone)==0).findFirst().orElse(null);
    }

    public Client select(List<Client> clientList) {
        if (clientList.isEmpty()) {
            System.out.println("Aucun client n'existe encore");
            return null;
        }
        System.out.println("Veuillez entrer le telephone du Client ou 0 pour annuler");
        clientList.stream().forEach(System.out::println);
        String telephone = scanner.nextLine();
        if (telephone.compareTo("0") != 0) {
            Client client = this.getByTelephone(clientList, telephone);
            boolean ok = client == null;
            while (ok) {
                System.out.println("Aucun client ne correspond à ce telephone");
                System.out.println("Veuillez entrer le telephone du client ou 0 pour annuler");
                telephone = scanner.nextLine();
                if (telephone.compareTo("0") == 0) {
                    ok = false;
                } else {
                    client = this.getByTelephone(clientList, telephone);
                    ok = client == null;
                }
            }
            if (client==null) {
                System.out.println("Opération annulée");
            }
            return client;
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
