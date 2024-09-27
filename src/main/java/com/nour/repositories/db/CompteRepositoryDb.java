package com.nour.repositories.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.nour.core.repositories.impl.RepositoryDbImpl;
import com.nour.entities.Compte;
import com.nour.enums.Etat;
import com.nour.enums.Role;
import com.nour.repositories.CompteRepository;

public class CompteRepositoryDb extends RepositoryDbImpl <Compte> implements CompteRepository{

    public CompteRepositoryDb(){
        super.repositoryName="compte";
        super.clazz=Compte.class;
    }
    
    
    @Override
    public boolean insert(Compte compte) {
        int nbre = 0;
        String sql=String.format(
            "INSERT INTO `%S` (`login`, `password`, `email`, `etatId`, `roleId`) VALUES (?, ?, ?, ?, ?);",
            super.repositoryName);
        try {
            this.getConnection();
            this.initPreparedStatement(sql);
            this.preparedStatement.setString(1, compte.getLogin());
            this.preparedStatement.setString(2, compte.getPassword());
            this.preparedStatement.setString(3, compte.getEmail());
            this.preparedStatement.setInt(4, compte.getEtat()==Etat.Activé?1:2);
            this.preparedStatement.setInt(5, compte.getRole()== Role.Client?1:compte.getRole()==Role.Boutiquier?2:3);
            nbre = this.executeUpdate();
            ResultSet resultSet=this.preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                compte.setId(resultSet.getInt(1));
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
    // public List<Compte> select() {
    //     List<Compte> comptes=new ArrayList<>();
    //     String sql="SELECT * FROM "+ super.repositoryName;
    //     try {
    //         this.getConnection();
    //         this.initPreparedStatement(sql);
    //         ResultSet resultSet = this.executeQuery();
    //         while (resultSet.next()) {
    //             try {
    //                 comptes.add(convertToObject(resultSet,Compte.class));
    //             } catch (ReflectiveOperationException e) {
    //                 // TODO Auto-generated catch block
    //                 e.printStackTrace();
    //             }
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return comptes;
    // }



    // @Override
    // public Compte convertToObject(ResultSet resultSet) throws SQLException{
    //     Compte compte=new Compte();

    //     try {
    //         compte.setEmail(resultSet.getString("email"));
    //         compte.setEtat(resultSet.getInt("etatId")==1?Etat.Activé:Etat.Désactivé);
    //         compte.setLogin(resultSet.getString("login"));
    //         compte.setPassword(resultSet.getString("password"));
    //         compte.setRole(resultSet.getInt("roleId")==1?Role.Client:resultSet.getInt("roleId")==2?Role.Boutiquier:Role.Admin);

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     return compte;
    // }

    @Override
    public Compte getCompteById(int id) {
        Compte compte=null;
        String sql= String.format("SELECT * FROM `%s` WHERE id = ?", super.repositoryName);
        try {
            this.getConnection();
            this.initPreparedStatement(sql);
            this.preparedStatement.setInt(1, id);
            ResultSet resultSet=this.executeQuery();
            if (resultSet.next()) {
                try {
                    compte=super.convertToObject(resultSet,Compte.class);
                } catch (ReflectiveOperationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return compte;
    }

}
