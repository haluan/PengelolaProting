/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package penpot.Koneksi;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author haluan
 */
public class LoginAction {

    private String username;
    private String password;
    private String koneksi = "localhost";
    private boolean isOK = false;
    private String cURLDB;
    private java.sql.Connection conn;
    private String url = "jdbc:oracle:thin:@localhost:1521:XE";
    private Statement stm = null;

    /**
     * Creates a new instance of Login
     */
    public LoginAction() {
        this.username = "";
        this.password = "";
    }

    public LoginAction(java.lang.String cUser, java.lang.String passwd) {
        this.username = cUser;
        this.password = passwd;
    }

    @Override
    protected void finalize() {
        conn = null;
    }

    public String getUsername() {
        return this.username = "";
    }

    public String getPassword() {
        return this.password = "";
    }

    public boolean cekLogin() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
        } catch (java.lang.ClassNotFoundException cnfe) {
            System.out.println("Error loading driver: " + cnfe);
        } catch (java.lang.InstantiationException e) {
            System.out.println("Error loading driver: " + e);
        } catch (java.lang.IllegalAccessException e) {
            System.out.println("Error loading driver: " + e);
        } 
        
        try {
            conn = java.sql.DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(true);
            isOK = true;
            cURLDB = url;
        } catch (Exception e) {
            System.out.println(e);
            isOK = false;
        }
        return isOK;
    }

//    public void closeConnDB() {
//        try {
//            conn.close();
//        } catch (Exception e) {
//            System.out.println("Tidak dapat menutup koneksi database!" + e);
//        }
//        conn = null;
//    }

    public java.sql.Connection getConnDB() {
        return this.conn;
    }

//    public Statement sambung() { //method utk memanggil driver oracle  
//
//        try {
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//        } catch (ClassNotFoundException e) {
//
//            e.printStackTrace();
//        }
//
//        try {
//            conn = DriverManager.getConnection(url, username, password);
//        } catch (SQLException e) {
//
//            e.printStackTrace();
//        }
//
//        try {
//            stm = conn.createStatement();
//        } catch (SQLException e) {
//
//            e.printStackTrace();
//        }
//
//        return stm;
//    }
//
//    public boolean getIsOK() {
//        return this.isOK;
//    }
}
