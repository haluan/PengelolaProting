/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package penpot.Controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
                + "dosen set password=? where nip=?");
        
        st.setString(1, d.getPassword());
        st.setString(2, d.getNip());
        st.executeUpdate();
     }
}
