/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package penpot.Proses;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import penpot.Koneksi.JembatanLogin;
import penpot.Objek.Dosen;
import penpot.Objek.Mahasiswa;

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
}
