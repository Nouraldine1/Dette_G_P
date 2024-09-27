package com.nour;

import java.sql.SQLException;
import java.util.Scanner;

import com.nour.entities.Article;
import com.nour.entities.Client;
import com.nour.entities.Compte;
import com.nour.enums.DetteEtat;
import com.nour.enums.Etat;
import com.nour.enums.Role;
import com.nour.repositories.ArticleRepository;
import com.nour.repositories.ClientRepository;
import com.nour.repositories.CompteRepository;
import com.nour.repositories.DetteRepository;
import com.nour.repositories.db.ArticleRepositoryDb;
import com.nour.repositories.db.ClientRepositoryDb;
import com.nour.repositories.db.CompteRepositoryDb;
import com.nour.repositories.db.DetteRepositoryDb;
import com.nour.services.ArticleService;
import com.nour.services.ClientService;
import com.nour.services.CompteService;
import com.nour.services.DetteService;
import com.nour.views.ArticleView;
import com.nour.views.ClientView;
import com.nour.views.CompteView;
import com.nour.views.DetteView;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {

        //Fabrique
        
        //Views
        CompteView compteView = new CompteView(scanner);
        ClientView clientView = new ClientView(scanner);
        ArticleView articleView = new ArticleView(scanner);
        DetteView detteView = new DetteView(scanner);

        //Repositories
        CompteRepository compteRepository=new CompteRepositoryDb();
        ClientRepository clientRepository = new ClientRepositoryDb(compteRepository);
        ArticleRepository articleRepository=new ArticleRepositoryDb();
        DetteRepository detteRepository=new DetteRepositoryDb();

        //Services
        CompteService compteService = new CompteService(compteRepository);
        ClientService clientService = new ClientService(clientRepository);
        ArticleService articleService = new ArticleService(articleRepository);
        DetteService detteService = new DetteService(detteRepository);

        int choix;

        do {
            System.out.println("1: Comptes");
            System.out.println("2: Articles");
            System.out.println("3: Clients");
            System.out.println("4: Archiver les dettes soldées");
            System.out.println("5: Quitter");
            choix = scanner.nextInt();
            int choixMenu;
            int choixSousMenu;
            scanner.nextLine();
            switch (choix) {
                case 1:
                    do {
                        choixMenu = compteView.menu();
                        Compte compte;
                        switch (choixMenu) {
                            case 1:
                                Boolean ok = true;
                                Client client = null;
                                choixSousMenu = compteView.choixSousMenu(
                                        "1: Pour un client\n2: Pour un boutiquier\n3: Pour un admin\n4: Annuler", 4);
                                if (choixMenu == 4) {
                                    System.out.println("Opération annulée");
                                } else if (choixSousMenu == 1) {
                                    client = clientView.select(clientService.findNoneAccountClients());
                                    if (client == null) {
                                        ok = false;
                                    }
                                }
                                if (ok) {
                                    Role role = Role.values()[choixSousMenu - 1];
                                    compte = compteView.saisie();
                                    compte.setRole(role);
                                    compteService.create(compte);
                                    if (client != null) {
                                        client.setCompte(compte);
                                    }
                                    System.out.println("Compte crée avec succès");
                                }
                                break;

                            case 2:
                                compte = compteView.select(compteService.findAll());
                                if (compte != null) {
                                    if (compte.getEtat() == Etat.Activé) {
                                        compte.setEtat(Etat.Désactivé);
                                        System.out.println("Compte désactivé avec succès");
                                    } else {
                                        compte.setEtat(Etat.Activé);
                                        System.out.println("Compte activé avec succès");
                                    }
                                }
                                break;

                            case 3:
                                if (compteService.findAll().isEmpty()) {
                                    System.out.println("Aucun compte n'existe encore");
                                } else {
                                    do {
                                        choixSousMenu = compteView
                                                .choixSousMenu("1: Tous\n2: Actifs\n3: Par rôle\n4: Retour", 4);
                                        switch (choixSousMenu) {
                                            case 1:
                                                compteView.showListElements(compteService.findAll(), "Aucun compte");
                                                break;

                                            case 2:
                                                compteView.showListElements(
                                                        compteService.findAll().stream()
                                                                .filter(c -> c.getEtat() == Etat.Activé).toList(),
                                                        "Aucun compte actif");
                                                break;

                                            case 3:
                                                Role role = compteView.selectRole();
                                                compteView.showListElements(
                                                        compteService.findAll().stream()
                                                                .filter(c -> c.getRole() == role).toList(),
                                                        "Aucun "+role.toString().toLowerCase());
                                                break;

                                            case 4:
                                                break;
                                                
                                            default:
                                                System.out.println("Veuillez faire un bon choix");
                                                break;
                                        }
                                    } while (choixSousMenu != 4);
                                }
                                break;

                            case 4:

                                break;

                            case 5:
                                choix = 4;
                                break;

                            default:
                                System.out.println("Veuillez faire un bon choix");
                                break;
                        }
                    } while (choixMenu != 4 && choixMenu != 5);
                    break;

                case 2:
                    do {
                        choixMenu = articleView.menu();
                        Article article;
                        switch (choixMenu) {
                            case 1:
                                article = articleView.saisie();
                                articleService.create(article);
                                System.out.println("Article crée avec succès");
                                break;

                            case 2:
                                if (articleService.findAll().isEmpty()) {
                                    System.out.println("Aucun article n'existe encore");
                                } else {
                                    do {
                                        choixSousMenu = articleView.choixSousMenu("1: Tous\n2: Disponibles\n3: Retour",
                                                3);
                                        switch (choixSousMenu) {
                                            case 1:
                                                articleView.showListElements(articleService.findAll(), "Aucun article");
                                                break;

                                            case 2:
                                                articleView.showListElements(
                                                        articleService.findAll().stream()
                                                                .filter(c -> c.getQteStock() > 0).toList(),
                                                        "Aucun article disponible");
                                                break;

                                            case 3:
                                                break;

                                            default:
                                                System.out.println("Veuillez faire un bon choix");
                                                break;
                                        }
                                    } while (choixSousMenu != 3);
                                }
                                break;

                            case 3:
                                article = articleView.select(articleService.findAll());
                                if (article != null) {
                                    int qteStock;
                                    do {
                                        System.out
                                                .println("Veuillez donner la nouvelle quantité en stock de l'article");
                                        qteStock = scanner.nextInt();
                                        if (qteStock < 0) {
                                            System.out.println("La quantité en stock doit être positif");
                                        }
                                    } while (qteStock < 0);
                                    article.setQteStock(qteStock);
                                    System.out.println("Quantité mise à jour avec succès");
                                }
                                break;

                            case 4:

                                break;

                            case 5:
                                choix = 4;
                                break;

                            default:
                                System.out.println("Veuillez faire un bon choix");
                                break;
                        }
                    } while (choixMenu != 4 && choixMenu != 5);
                    break;

                case 3:
                    do {
                        choixMenu = clientView.menu();
                        Client client;
                        switch (choixMenu) {
                            case 1:
                                client = clientView.saisie();
                                clientService.create(client);
                                System.out.println("Client crée avec succès");
                                break;

                            case 2:
                                if (clientService.findAll().isEmpty()) {
                                    System.out.println("Aucun client n'existe encore");
                                } else {
                                    do {
                                        choixSousMenu = clientView.choixSousMenu("1: Tous\n2: N'ayant pas de compte\n3: Retour",
                                                3);
                                        switch (choixSousMenu) {
                                            case 1:
                                                clientView.showListElements(clientService.findAll(), "Aucun client");
                                                break;

                                            case 2:
                                                clientView.showListElements(
                                                        clientService.findAll().stream()
                                                                .filter(c -> c.getCompte() ==null).toList(),
                                                        "Aucun client disponible");
                                                break;

                                            case 3:
                                                break;

                                            default:
                                                System.out.println("Veuillez faire un bon choix");
                                                break;
                                        }
                                    } while (choixSousMenu != 3);
                                }
                                break;

                            case 3:
                                
                                break;

                            case 4:
                                choix = 4;
                                break;

                            default:
                                System.out.println("Veuillez faire un bon choix");
                                break;
                        }
                    } while (choixMenu != 3 && choixMenu != 4);
                    break;

                case 4:
                    detteView.showListElements(detteService.findAll(), "Aucune dette");
                    detteService.findAll().stream()
                            .filter(dette -> dette.getReste() == 0 && dette.getMontant() == dette.getPaye())
                            .forEach(dette -> dette.setDetteEtat(DetteEtat.Archivé));
                    System.out.println("################################################################");
                    detteView.showListElements(detteService.findAll(), "Aucune dette");
                    break;

                case 5:

                    break;

                default:
                    System.out.println("Veuillez faire un bon choix!!!!!!!!!!!!");
                    break;
            }
        } while (choix != 5);
        System.out.println("Bye bye!!!!!!!!!!!!!!!!");
        scanner.close();
    }
}