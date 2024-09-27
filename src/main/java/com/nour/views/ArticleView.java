package com.nour.views;
import java.util.List;
import java.util.Scanner;

import com.nour.entities.Article;
import com.nour.views.impl.ViewImpl;

public class ArticleView extends ViewImpl{

    public ArticleView(Scanner scanner) {
        super(scanner);
    }

    public int menu(){
        int choix;
        do {
            System.out.println("1: Créer un article");
            System.out.println("2: Les articles");
            System.out.println("3: Mettre à jour la quantité en stock s'un article");
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

    public Article saisie(){
        String libelle;
        double prix;
        int qteStck;
        libelle=super.obligatoire("Veuillez donner le libelle de l'article", "Le libelle est obligatoire");
        do {
            System.out.println("Veuillez donner le prix de l'article");
            prix=scanner.nextInt();
            if (prix<=0) {
                System.out.println("Le prix doit être positif");
            }
        } while (prix<0);
        do {
            System.out.println("Veuillez donner la quantité en stock de l'article");
            qteStck=scanner.nextInt();
            if (qteStck<=0) {
                System.out.println("La quantité en stock doit être positif");
            }
        } while (qteStck<0);
        return new Article(libelle, prix, qteStck);
    }

    public Article getByLibelle(List<Article> articleList,String libelle){
        return articleList.stream().filter(article -> article. getLibelle().compareTo(libelle)==0).findFirst().orElse(null);
    }

    public Article select(List<Article> articleList) {
        if (articleList.isEmpty()) {
            System.out.println("Aucun article n'existe encore");
            return null;
        }
        System.out.println("Veuillez entrer le libelle du article ou 0 pour annuler");
        articleList.stream().forEach(System.out::println);
        String libelle = scanner.nextLine();
        if (libelle.compareTo("0") != 0) {
            Article article = this.getByLibelle(articleList, libelle);
            boolean ok = article == null;
            while (ok) {
                System.out.println("Aucun article ne correspond à ce libelle");
                System.out.println("Veuillez entrer le libelle de l'article ou 0 pour annuler");
                libelle = scanner.nextLine();
                if (libelle.compareTo("0") == 0) {
                    ok = false;
                } else {
                    article = this.getByLibelle(articleList, libelle);
                    ok = article == null;
                }
            }
            if (article==null) {
                System.out.println("Opération annulée");
            }
            return article;
        }
        System.out.println("Opération annulée");
        return null;
    }

}
