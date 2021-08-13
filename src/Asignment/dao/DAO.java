/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Asignment.dao;

import Asignment.model.student;
import Asignment.model.users;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

/**
 *
 * @author Van Tan Exfick
 */
public class DAO {

    Connection connec;

    public DAO() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connec = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=FPL;", "FPL", "fplfplfpl");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    ArrayList<student> sv = new ArrayList<>();
    ArrayList<users> us = new ArrayList<>();

 
}
