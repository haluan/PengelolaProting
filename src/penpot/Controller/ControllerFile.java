/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package penpot.Controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import penpot.Koneksi.JembatanLogin;
import penpot.Model.Dokumen;

/**
 *
 * @author haluan
 */
public class ControllerFile {

    public void upload(String nip, String nim, String tgl, String namafile, String fromFileURL, String toFileURL) {
        try {
            
            PreparedStatement st = JembatanLogin.getMyLgn().getConnDB().
                    prepareStatement("insert into dokumen "
                    + " values(?,?,?,?)");
            st.setString(1, nip);
            st.setString(2, nim);
            st.setString(3, tgl);
            st.setString(4, namafile);
            st.executeUpdate();
            System.out.println("INSERT DATABASE");
            FileChannel fromFile = null;
            FileChannel toFile = null;
            try {
                fromFile = new FileInputStream(fromFileURL).getChannel();
                toFile = new FileOutputStream(toFileURL).getChannel();
                toFile.transferFrom(fromFile, 0, fromFile.size());
                
              
                JOptionPane.showMessageDialog(null, "Upload success");
                //masuk = true;
            } catch (Throwable f) {
                f.printStackTrace();
            } finally {
                try {
                    fromFile.close();
                    toFile.close();
                } catch (IOException ex) {
                    Logger.getLogger(ControllerFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControllerFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Dokumen> getAll() throws SQLException{
         Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        String query = "select url from dokumen";
        ResultSet rs = st.executeQuery(query);
        List<Dokumen> listDok=new ArrayList<>();
        while(rs.next()){
            Dokumen dok = new Dokumen();
            dok.setUrl(rs.getString("url"));
            listDok.add(dok);
        }
        return listDok;
    }
}
