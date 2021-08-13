/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Asignment.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Van Tan Exfick
 */
public class NguoiDungDao {

    Connection connect;

    public NguoiDungDao() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connect = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=FPL;", "FPL", "fplfplfpl");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int checkLogin(String user, String pass) {
        //tra ve 0 la nhan vien
        //tra ve 1 neu GV
        // -1  neu login sai
        int kq;
        try {
            PreparedStatement pps = connect.prepareStatement("select * from users where username=? and password = ?");
            pps.setString(1, user);
            pps.setString(2, pass);
            ResultSet rs = pps.executeQuery();
            if (rs.next()) {
                kq = rs.getInt(3);

            } else {
                kq = -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            kq = -1;
        }
        return kq;
    }
}
