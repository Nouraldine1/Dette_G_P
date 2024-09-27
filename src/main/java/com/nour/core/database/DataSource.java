package com.nour.core.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DataSource <T>{
    void getConnection() throws SQLException;
    void closeConnection() throws SQLException;
    ResultSet executeQuery() throws SQLException;
    int executeUpdate() throws SQLException;
    void initPreparedStatement(String sql) throws SQLException;
    String generateSql(T object) throws SQLException, IllegalAccessException;
    void setField(T t) throws SQLException ;
}