/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package penpot.Proses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import penpot.Koneksi.JembatanLogin;
import penpot.Objek.Dosen;

/**
 *
 * @author haluan
 */
public class ControllerDosen {
    private Connection conn = JembatanLogin.getMyLgn().getConnDB();
    private String query;
    
    public Dosen insert(Dosen d) throws SQLException {
        PreparedStatement st = JembatanLogin.getMyLgn().getConnDB().
                prepareStatement("insert into dosen "
                + " values(?,?,?)");
        st.setString(1, d.getNip());
        st.setString(2, d.getNama());
        st.setString(3, d.getPassword());
        
        st.executeUpdate();
        return d;
    }
     public List<Dosen> getAll() throws SQLException {
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        query = "select nip, nama from dosen "
                + "order by nip asc";
        ResultSet rs = st.executeQuery(query);
        List<Dosen> listDsn = new ArrayList<Dosen>();
        while (rs.next()) {
                Dosen d = new Dosen();
                d.setNip(rs.getString("nip"));
                d.setNama(rs.getString("nama"));
                listDsn.add(d);
            }
        
        return listDsn;
    }
     
     public void update(Dosen d) throws SQLException {
        PreparedStatement st=JembatanLogin.getMyLgn().getConnDB().prepareStatement("update "
                + "dosen set nama=? where nip=?");
        
        st.setString(1, d.getNama());
        st.executeUpdate();
    }
     
     public void delete(String nip) throws SQLException {
       PreparedStatement st=JembatanLogin.getMyLgn().getConnDB().prepareStatement
               ("delete from dosen where nip=?");
        st.setString(1, nip);
        st.executeUpdate();
    }
    
}
