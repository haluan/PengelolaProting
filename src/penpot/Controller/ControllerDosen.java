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
import penpot.Model.Proyek;

/**
 *
 * @author haluan
 */
public class ControllerDosen {
    private Connection conn = JembatanLogin.getMyLgn().getConnDB();
    private String query;
    
     public Dosen getData(String nip) throws SQLException {
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        Dosen d = new Dosen();
        query = "select nip, nama from dosen "
                + "where nip ='"+nip+"'";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            
            d.setNip(rs.getString("nip"));
            d.setNama(rs.getString("nama"));
            
        }

        return d;
    }
    
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
        query = "select judul, nim, m.nama, "
                + "kelas,m.status,jabatan from mahasiswa m "
                + "join proyek using (idproyek) "
                + "join dosen using (nip)"
                + "where nip ='"+nip+"'"
                + "order by judul asc";
        ResultSet rs = st.executeQuery(query);
        List<Mahasiswa> listMhs = new ArrayList<Mahasiswa>();
        while (rs.next()) {
            Mahasiswa m = new Mahasiswa();
            m.setNim(rs.getString("nim"));
            m.setNama(rs.getString(3));
            m.setKelas(rs.getString("kelas"));
            m.setStatus(rs.getString("status"));
            m.setJabatan(rs.getString("jabatan"));
            m.setJudul(rs.getString("judul"));
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
     
    public List<Proyek> getAllKelompk(String nip) throws SQLException{
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        query = "select judul from proyek join dosen using (nip) where nip='"+nip+"'"
                + "order by nip asc";
        ResultSet rs = st.executeQuery(query);
        List<Proyek> listPro=new ArrayList<Proyek>();
        Proyek pro=new Proyek();
        while(rs.next()){
            pro.setJudul(rs.getString("judul"));
            listPro.add(pro);
        }
        return listPro;
    }
     
     public void update(Dosen d) throws SQLException {
        PreparedStatement st=JembatanLogin.getMyLgn().getConnDB().prepareStatement("update "
                + "dosen set nama=? where nip=?");
        
        st.setString(1, d.getNama());
        st.executeUpdate();
    }
     
      public void updatePassDosen(Dosen d) throws SQLException{
          PreparedStatement st=JembatanLogin.getMyLgn().getConnDB().prepareStatement("update "
                + "dosen set password=? where nip=?");
        
        st.setString(1, d.getPassword());
        st.setString(2, d.getNip());
        st.executeUpdate();
     }
     
     public void delete(String nip) throws SQLException {
       PreparedStatement st=JembatanLogin.getMyLgn().getConnDB().prepareStatement
               ("delete from dosen where nip=?");
        st.setString(1, nip);
        st.executeUpdate();
    }
    
}
