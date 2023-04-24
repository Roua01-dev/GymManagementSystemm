package com.example.gymmanagementsystemm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {
   public static Connection connectionDb(){

        String nomDriver="com.mysql.cj.jdbc.Driver";
        Connection con;
        try {
            Class.forName(nomDriver);
             con = DriverManager.getConnection("jdbc:mysql://localhost/gym","root","");


        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);}


        return con;


    }



}
