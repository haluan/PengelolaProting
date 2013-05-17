/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package penpot.Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import penpot.Koneksi.JembatanLogin;
import penpot.Model.Dosen;
import penpot.Model.Mahasiswa;

/**
 *
 * @author haluan
 */
public class kelolaBaru {
         
     public Dosen insertKaprodi(Dosen d) throws SQLException {
        PreparedStatement st = JembatanLogin.getMyLgn().getConnDB().
                prepareStatement("insert into Admin values(?,?,?)");
        st.setString(1, d.getNip());
        st.setString(2, d.getNama());
        st.setString(3, d.getPassword());
        st.executeUpdate();
        return d;
    }
     
     public void updatePassKaprodi(Dosen d) throws SQLException{
          PreparedStatement st=JembatanLogin.getMyLgn().getConnDB().prepareStatement("update "
                + "kaprodi set password=? where nip=?");        
        st.setString(1, d.getPassword());
        st.setString(2, d.getNip());
        st.executeUpdate();
     }
     
     public Dosen getData(String nip) throws SQLException {
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        Dosen d = new Dosen();
        String query = "select nip, nama from kaprodi "
                + "where nip ='"+nip+"'";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            
            d.setNip(rs.getString("nip"));
            d.setNama(rs.getString("nama"));
            
        }

        return d;
    }
}
