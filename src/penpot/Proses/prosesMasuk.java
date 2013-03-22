/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package penpot.Proses;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import penpot.Koneksi.JembatanLogin;

/**
 *
 * @author haluan
 */
public class prosesMasuk {

    private boolean masuk = false;
    private Connection conn = JembatanLogin.getMyLgn().getConnDB();

    public Connection getConn() {
        return conn;
    }

    public boolean periksaMasuk(String kondisi, String password, int sinyalemen) {
        String query = "";
        if (sinyalemen == 0) {
            query = "SELECT * FROM MAHASISWA";
            if (kondisi != null && password != null) {
                query = query + " WHERE nim ='" + kondisi + "'"
                        + " and password = '" + password + "'";
            }
        }
        if (sinyalemen == 1) {
            query = "SELECT * FROM KAPRODI";
            if (kondisi != null && password != null) {
                query = query + " WHERE NIP ='" + kondisi + "'"
                        + " and PASSWORD = '" + password + "'";
            }
        }
        if (sinyalemen == 2) {
            query = "SELECT * FROM DOSEN";
            if (kondisi != null && password != null) {
                query = query + " WHERE NIP ='" + kondisi + "'"
                        + " and PASSWORD = '" + password + "'";
            }
        }
        if (sinyalemen == 3) {
            query = "SELECT * FROM ADMIN";
            if (kondisi != null && password != null) {
                query = query + " WHERE NIP ='" + kondisi + "'"
                        + " and PASSWORD = '" + password + "'";
            }
        }
        try {

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            this.masuk = false;
            while (resultSet.next()) {
                if (resultSet != null) {
                    masuk = true;
                } else {
                    masuk = false;
                }
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Error : " + e);
        }
        return masuk;
    }
}
