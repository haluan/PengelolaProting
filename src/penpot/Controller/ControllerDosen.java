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
import penpot.Model.Dosen;
import penpot.Model.Mahasiswa;

/**
 *
 * @author haluan
 */
public class ControllerDosen {
    private Connection conn = JembatanLogin.getMyLgn().getConnDB();
    private String query;
    
    public Mahasiswa getDataDepe(String nim) throws SQLException {
        Mahasiswa m = new Mahasiswa();
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        String query = "select nim, nama from mahasiswa "
              +"where nim='" + nim + "'";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            m.setNim(rs.getString("nim"));
            m.setNama(rs.getString("nama"));
        }
        return m;
    }
    
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
    public List<Mahasiswa> getAllBinaan(String nip) throws SQLException {
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        query = "select nim, m.nama, "
                + "kelas,status from mahasiswa m "
                + "join kelompok using (idkelompok)"
                + "join proyek using (idproyek) "
                + "join dosen using (nip)"
                + "where nip ='"+nip+"'"
                + "order by nim asc";
        ResultSet rs = st.executeQuery(query);
        List<Mahasiswa> listMhs = new ArrayList<Mahasiswa>();
        while (rs.next()) {
            Mahasiswa m = new Mahasiswa();
            m.setNim(rs.getString("nim"));
            m.setNama(rs.getString(2));
            m.setKelas(rs.getString("kelas"));
            m.setStatus(rs.getString("status"));
            listMhs.add(m);
        }

        return listMhs;
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
