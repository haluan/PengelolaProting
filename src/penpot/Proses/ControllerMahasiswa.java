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
import penpot.Objek.Kelompok;
import penpot.Objek.Mahasiswa;

/**
 *
 * @author haluan
 */
public class ControllerMahasiswa {

    private Connection conn = JembatanLogin.getMyLgn().getConnDB();
    private String query;
    
    
    
    public Dosen getDataDepe(String nim) throws SQLException {
        Dosen d = new Dosen();
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        String query = "select nip, nim, dosen.nama from mahasiswa "
                + "join kelompok using (idkelompok) join proyek using (idproyek)"
                + " join dosen using (nip) where nim='" + nim + "'";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            d.setNip(rs.getString("nip"));
            d.setNama(rs.getString("nama"));
        }
        return d;
    }
    
    public Kelompok getDataKelompok(String nim) throws SQLException {
        Kelompok k= new Kelompok();
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        String query = "select idkelompok, k.nama from mahasiswa "
                + "join kelompok k using (idkelompok)"+
                "where nim='" + nim + "' order by nim asc";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            k.setIdKelompok(rs.getString(1));
            k.setNamaKelompok(rs.getString(2));
        }
        return k;
    }

    public Mahasiswa insert(Mahasiswa m) throws SQLException {
        PreparedStatement st = JembatanLogin.getMyLgn().getConnDB().
                prepareStatement("insert into mahasiswa (nim,nama,password,kelas,status"
                + ",jeniskelamin,jabatan)"
                + " values(?,?,?,?,?,?,?)");
        st.setString(1, m.getNim());
        st.setString(2, m.getNama());
        st.setString(3, m.getPassword());
        st.setString(4, m.getKelas());
        st.setString(5, m.getStatus());
        st.setString(6, m.getStatus());
        st.setString(7, m.getJabatan());
        st.executeUpdate();
        return m;
    }
    
    public List<Mahasiswa> getKelompok(String idkelompok) throws SQLException {
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        query = "select m.nim, m.nama, kelas,status, jabatan from mahasiswa "
                + "m join kelompok using (idkelompok) where idkelompok='"+idkelompok+"'"
                + "order by nim";
        ResultSet rs = st.executeQuery(query);
        List<Mahasiswa> listMhs = new ArrayList<Mahasiswa>();
        while (rs.next()) {
            Mahasiswa m = new Mahasiswa();
            m.setNim(rs.getString(1));
            m.setNama(rs.getString(2));
            m.setKelas(rs.getString(3));
            m.setStatus(rs.getString(4));
            m.setJabatan(rs.getString("jabatan"));
            listMhs.add(m);
        }

        return listMhs;
    }

    public List<Mahasiswa> getAll() throws SQLException {
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        query = "select nim, nama, kelas,status,jabatan, jeniskelamin from mahasiswa "
                + "order by nim asc";
        ResultSet rs = st.executeQuery(query);
        List<Mahasiswa> listMhs = new ArrayList<Mahasiswa>();
        while (rs.next()) {
            Mahasiswa m = new Mahasiswa();
            m.setNim(rs.getString("nim"));
            m.setNama(rs.getString("nama"));
            m.setKelas(rs.getString("kelas"));
            m.setStatus(rs.getString("status"));
            m.setJabatan(rs.getString("jabatan"));
            m.setJenisKelamin(rs.getString("jeniskelamin"));
            listMhs.add(m);
        }

        return listMhs;
    }

    public void update(Mahasiswa m) throws SQLException {
        PreparedStatement st = JembatanLogin.getMyLgn().getConnDB().prepareStatement("update "
                + "mahasiswa set nama=?,kelas=?,status=? where nim=?");

        st.setString(1, m.getNama());
        st.setString(2, m.getKelas());
        st.setString(3, m.getStatus());
        st.setString(4, m.getNim());
        st.executeUpdate();
    }

    public void delete(String nim) throws SQLException {
        PreparedStatement st = JembatanLogin.getMyLgn().getConnDB().
                prepareStatement("delete from mahasiswa where nim=?");
        st.setString(1, nim);
        st.executeUpdate();
    }
}
