/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package penpot.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import penpot.Koneksi.JembatanLogin;
import penpot.Model.Kelas;
import penpot.Model.Mahasiswa;

/**
 *
 * @author haluan
 */
public class KelasController {
     private Connection conn = JembatanLogin.getMyLgn().getConnDB();
    private String query;
    
     public Kelas insert(Kelas k) throws SQLException {
        PreparedStatement st = JembatanLogin.getMyLgn().getConnDB().
                prepareStatement("insert into kelas (namakelas)"
                + " values(?)");
        st.setString(1, k.getKelas());
        st.executeUpdate();
        return k;
     }
     
     public List<Kelas> getAll() throws SQLException {
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        query = "select * from kelas "
                + "order by namakelas asc";
         ResultSet rs = st.executeQuery(query);
        List<Kelas> listKls = new ArrayList<Kelas>();
        Kelas k = new Kelas();
        while (rs.next()) {
            
            k.setKelas(rs.getString("namakelas"));
            listKls.add(k);
        }

        return listKls;
     }
    
}
