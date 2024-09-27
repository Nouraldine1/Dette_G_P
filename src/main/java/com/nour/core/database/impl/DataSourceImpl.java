package com.nour.core.database.impl;

import java.sql.Statement;

import com.nour.core.database.DataSource;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DataSourceImpl<T> implements DataSource<T> {

    private String USER = "root";
    private String PSWD = "";
    private String DBNAME = "projet_java_S5";
    private String URL = "jdbc:mysql://localhost:3306/" + DBNAME;
    private String CLASSNAME = "com.mysql.cj.jdbc.Driver";
    protected Connection connection=null;
    protected PreparedStatement preparedStatement=null;

    @Override
    public void getConnection() throws SQLException{
        
        try {
            Class.forName(CLASSNAME);
            connection = DriverManager.getConnection(URL, USER, PSWD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } 
    }

    @Override
    public void closeConnection() throws SQLException{
        if (connection!=null && connection.isClosed()) {
            connection.close();
        }
    }

    @Override
    public ResultSet executeQuery() throws SQLException{
        return preparedStatement.executeQuery();
    }

    @Override
    public int executeUpdate() throws SQLException{
        return preparedStatement.executeUpdate();
    }

    @Override
    public void setField(T data) throws SQLException {
        // Récupère la classe de l'objet
        Class<?> clazz = data.getClass();

        // Récupère tous les champs de l'objet
        Field[] fields = clazz.getDeclaredFields();
        // Remplir le PreparedStatement avec les valeurs des champs de l'objet
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            Object value=null;
            try {
                value = field.get(data);
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            preparedStatement.setObject(i + 1, value); // Les indices JDBC commencent à 1
        }
    }

    @Override
    public void initPreparedStatement(String sql) throws SQLException{
        if (sql.trim().toUpperCase().startsWith("INSERT")) {
            preparedStatement=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        } else {
            preparedStatement=connection.prepareStatement(sql);
        }
    }

    @Override
    public String generateSql(T object) throws SQLException, IllegalAccessException {
        // Récupère la classe de l'objet
        Class<?> clazz = object.getClass();

        // Récupère tous les champs de l'objet
        Field[] fields = clazz.getDeclaredFields();

        // Construire dynamiquement la requête SQL d'INSERT
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(clazz.getSimpleName().toLowerCase()); // Le nom de la table doit correspondre au nom de la classe (en minuscule)
        sql.append(" (");

        StringBuilder placeholders = new StringBuilder("VALUES (");

        // Ajoute les noms de champs et les placeholders dans la requête
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            sql.append(field.getName());
            placeholders.append("?");
            if (i < fields.length - 1) {
                sql.append(", ");
                placeholders.append(", ");
            }
        }

        sql.append(") ");
        placeholders.append(")");

        // Combine la partie champs et la partie placeholders
        sql.append(placeholders.toString());

        return sql.toString();
    }
    
}