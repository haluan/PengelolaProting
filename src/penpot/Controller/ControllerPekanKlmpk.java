/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package penpot.Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import penpot.Koneksi.JembatanLogin;
import penpot.Model.pekanKlmpk;

/**
 *
 * @author haluan
 */
public class ControllerPekanKlmpk {
    public pekanKlmpk insert(pekanKlmpk p) throws SQLException {
        PreparedStatement st = JembatanLogin.getMyLgn().getConnDB().
                prepareStatement("insert into pekankelompok (nip,idkelompok,pekan,nilai)"
                + " values(?,?,?,?)");
        st.setString(1, p.getNip());
        st.setInt(2, p.getIdKelompok());
        st.setString(3, p.getPekan());
        st.setString(4, p.getNilai());
        st.executeUpdate();
        return p;
    }

    public List<pekanKlmpk> getAll(String nip, String idkelompok) throws SQLException {
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        String query = "select pekan, "
                + "nilai from pekankelompok where "
                + "nip='" + nip + "' and idkelompok='" + idkelompok + "'"
                + "order by pekan asc";
        ResultSet rs = st.executeQuery(query);
        List<pekanKlmpk> listNilai = new ArrayList<pekanKlmpk>();
        while (rs.next()) {
            pekanKlmpk p = new pekanKlmpk();
            p.setPekan(rs.getString("pekan"));
            p.setNilai(rs.getString("nilai"));

            listNilai.add(p);
        }

        return listNilai;
    }

    public List<pekanKlmpk> getAll(String idproyek) throws SQLException {
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        String query = "select judul, pekan, nilai from pekankelompok join proyek using (idproyek) where idproyek='" 
                + idproyek + "'"
                + "order by pekan asc";
        ResultSet rs = st.executeQuery(query);
        List<pekanKlmpk> listNilai = new ArrayList<pekanKlmpk>();
        while (rs.next()) {
            pekanKlmpk p = new pekanKlmpk();
            p.setJudul(rs.getString("judul"));
            p.setPekan(rs.getString("pekan"));
            p.setNilai(rs.getString("nilai"));

            listNilai.add(p);
        }

        return listNilai;
    }
    
}
