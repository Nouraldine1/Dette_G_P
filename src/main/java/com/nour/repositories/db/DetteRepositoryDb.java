package com.nour.repositories.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.nour.core.repositories.impl.RepositoryDbImpl;
import com.nour.entities.Dette;
import com.nour.repositories.DetteRepository;

public class DetteRepositoryDb extends RepositoryDbImpl <Dette> implements DetteRepository{

    public DetteRepositoryDb(){
        super.repositoryName="dette";
        super.clazz=Dette.class;
    }

    @Override
    public boolean insert(Dette dette) {
        int nbre = 0;
        String sql="INSERT INTO `dette` (`surname`, `adresse`, `telephone`) VALUES ?, ?, ?);";
        try {
            this.getConnection();
            this.initPreparedStatement(sql);
            
            nbre = this.executeUpdate();
            ResultSet resultSet=this.preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                dette.setId(resultSet.getInt(1));
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
    // public List<Dette> select() {
    //     List<Dette> dettes=new ArrayList<>();
    //     String sql="SELECT * FROM "+ super.repositoryName;
    //     try {
    //         this.getConnection();
    //         this.initPreparedStatement(sql);
    //         ResultSet resultSet = this.executeQuery();
    //         while (resultSet.next()) {
    //             try {
    //                 dettes.add(super.convertToObject(resultSet,Dette.class));
    //             } catch (ReflectiveOperationException e) {
    //                 // TODO Auto-generated catch block
    //                 e.printStackTrace();
    //             }
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return dettes;
    // }

    // @Override
    // public Dette convertToObject(ResultSet resultSet) throws SQLException {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'convertToObject'");
    // }
    
}
