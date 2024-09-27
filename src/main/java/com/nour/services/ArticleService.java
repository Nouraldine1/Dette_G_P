package com.nour.services;

import java.util.List;

import com.nour.entities.Article;
import com.nour.repositories.ArticleRepository;

public class ArticleService {
    
     private ArticleRepository articleRepository;

    public ArticleService() {
    }

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void create(Article Article){
        articleRepository.insert(Article);
    }

    public List<Article> findAll(){
        return articleRepository.select();
    }
}
