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
import penpot.Model.Mahasiswa;
import penpot.Model.pekanMhs;

/**
 *
 * @author haluan
 */
public class ControllerPekanMhs {

    public pekanMhs insert(pekanMhs p) throws SQLException {
        PreparedStatement st = JembatanLogin.getMyLgn().getConnDB().
                prepareStatement("insert into pekanmahasiswa (nip,nim,pekan,nilai)"
                + " values(?,?,?,?)");
        st.setString(1, p.getNip());
        st.setString(2, p.getNim());
        st.setString(3, p.getPekan());
        st.setString(4, p.getNilai());
        st.executeUpdate();
        return p;
    }

    public List<pekanMhs> getAll(String nip, String nim) throws SQLException {
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        String query = "select pekan, nilai from pekanmahasiswa where nip='" + nip + "' and nim='" + nim + "'"
                + "order by pekan asc";
        ResultSet rs = st.executeQuery(query);
        List<pekanMhs> listNilai = new ArrayList<pekanMhs>();
        while (rs.next()) {
            pekanMhs p = new pekanMhs();
            p.setPekan(rs.getString("pekan"));
            p.setNilai(rs.getString("nilai"));

            listNilai.add(p);
        }

        return listNilai;
    }

    public List<pekanMhs> getAll(String nim) throws SQLException {
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        String query = "select nama, pekan, nilai from pekanmahasiswa join mahasiswa using (nim) where nim='" + nim + "'"
                + "order by pekan asc";
        ResultSet rs = st.executeQuery(query);
        List<pekanMhs> listNilai = new ArrayList<pekanMhs>();
        while (rs.next()) {
            pekanMhs p = new pekanMhs();
            p.setNama(rs.getString("nama"));
            p.setPekan(rs.getString("pekan"));
            p.setNilai(rs.getString("nilai"));

            listNilai.add(p);
        }

        return listNilai;
    }
}
