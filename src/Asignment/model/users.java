/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Asignment.model;

/**
 *
 * @author Van Tan Exfick
 */
public class users {
    String userName;
    String password;
    int role;// 1 la GV , 0 là nhân viên

    public users(String userName, String password, int role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public users() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
    
}
