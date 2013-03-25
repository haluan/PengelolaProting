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
import penpot.Model.Proyek;

/**
 *
 * @author haluan
 */
public class ControllerProyek {
    public Proyek insert(Proyek p) throws SQLException {
        PreparedStatement st = JembatanLogin.getMyLgn().getConnDB().
                prepareStatement("insert into proyek"
                + " values(?,?,?,?,?)");
        st.setString(1, p.getIdPro());
        st.setString(2, p.getJudul());
        st.setString(3, p.getTahunAkademik());
        st.setString(4, p.getNip());
        st.setString(5, p.getTingkat());
        
        st.executeUpdate();
        return p;
    }
    
    public List<Proyek> getAll() throws SQLException {
        String query;
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        query = "select judul,tingkat, nama, tahunakademik from proyek "
                + "join dosen using (nip)"
                + "order by nip asc";
        ResultSet rs = st.executeQuery(query);
        List<Proyek> listPro = new ArrayList<Proyek>();
        while (rs.next()) {
                Proyek p = new Proyek();
                p.setJudul(rs.getString("judul"));
                p.setNama(rs.getString("nama"));
                p.setTingkat(rs.getString("tingkat"));
                p.setTahunAkademik(rs.getString("tahunakademik"));
                listPro.add(p);
            }
        
        return listPro;
    }
    
}
