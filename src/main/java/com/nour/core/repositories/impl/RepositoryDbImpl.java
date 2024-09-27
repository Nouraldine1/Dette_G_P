package com.nour.core.repositories.impl;


import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nour.core.database.impl.DataSourceImpl;
import com.nour.core.repositories.Repository;
import com.nour.entities.Entity;

public abstract class RepositoryDbImpl <T extends Entity> extends DataSourceImpl<T> implements Repository<T>{
    protected String repositoryName;
    protected Class<T> clazz;

    @Override
    public boolean insert(T data){
        int nbre = 0;
        String sql="";
        try {
            sql = generateSql(data);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            this.getConnection();
            this.initPreparedStatement(sql);
            this.setField(data);
            nbre = this.executeUpdate();
            ResultSet resultSet=this.preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                data.setId(resultSet.getInt(1));
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

    @Override
    public List<T> select() {
        List<T> datas=new ArrayList<>();
        String sql="SELECT * FROM "+ this.repositoryName;
        try {
            this.getConnection();
            this.initPreparedStatement(sql);
            ResultSet resultSet = this.executeQuery();
            while (resultSet.next()) {
                try {
                    datas.add(convertToObject(resultSet, clazz));
                } catch (ReflectiveOperationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datas;
    }

    public T convertToObject (ResultSet resultSet, Class<T> clazz) throws SQLException, ReflectiveOperationException{
                // Crée une nouvelle instance de la classe donnée
            T object = clazz.getDeclaredConstructor().newInstance();
    
            // Récupère tous les champs de la classe
            Field[] fields = clazz.getDeclaredFields();
    
            // Parcourt chaque champ de la classe
            for (Field field : fields) {
                // Rendre les champs privés accessibles
                field.setAccessible(true);
    
                try {
                    // Récupère la valeur de la colonne correspondant au nom du champ
                    Object value = resultSet.getObject(field.getName());
    
                    // Assure que la valeur n'est pas nulle avant de l'assigner
                    if (value != null) {
                        field.set(object, value);
                    }
                } catch (SQLException e) {
                    // Ignore les champs qui n'existent pas dans le ResultSet
                    System.out.println("La colonne " + field.getName() + " n'existe pas dans le ResultSet");
                }
            }
    
            return object;
    }


}