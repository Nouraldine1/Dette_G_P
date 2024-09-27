package com.nour.repositories.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.nour.core.repositories.impl.RepositoryDbImpl;
import com.nour.entities.Article;
import com.nour.repositories.ArticleRepository;

public class ArticleRepositoryDb extends RepositoryDbImpl <Article> implements ArticleRepository{

    public ArticleRepositoryDb(){
        super.repositoryName="article";
        super.clazz=Article.class;
    }
    
    @Override
    public boolean insert(Article article) {
        int nbre = 0;
        String sql="INSERT INTO `article` (`libelle`, `prix`, `qteStock`) VALUES (?, ?, ?);";
        try {
            this.getConnection();
            this.initPreparedStatement(sql);
            this.preparedStatement.setString(1, article.getLibelle());
            this.preparedStatement.setDouble(2, article.getPrix());
            this.preparedStatement.setInt(3, article.getQteStock());
            nbre = this.executeUpdate();
            ResultSet resultSet=this.preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                article.setId(resultSet.getInt(1));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return nbre == 0;
    }

    // @Override
    // public List<Article> select() {
    //     List<Article> articles=new ArrayList<>();
    //     String sql="SELECT * FROM "+ super.repositoryName;
    //     try {
    //         this.getConnection();
    //         this.initPreparedStatement(sql);
    //         ResultSet resultSet = this.executeQuery();
    //         while (resultSet.next()) {
    //             try {
    //                 articles.add(super.convertToObject(resultSet,Article.class));
    //             } catch (ReflectiveOperationException e) {
    //                 // TODO Auto-generated catch block
    //                 e.printStackTrace();
    //             }
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return articles;
    // }

    // @Override
    // public Article convertToObject (ResultSet resultSet) throws SQLException{
    //     Article article=new Article();
    //     try {
    //         article.setId(resultSet.getInt("id"));
    //         article.setLibelle(resultSet.getString("libelle"));
    //         article.setPrix(resultSet.getDouble("prix"));
    //         article.setQteStock(resultSet.getInt("prix"));

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     return article;
    // }
}
