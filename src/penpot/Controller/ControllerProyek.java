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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import penpot.Koneksi.JembatanLogin;
import penpot.Model.Proyek;

/**
 *
 * @author haluan
 */
public class ControllerProyek {

    public Proyek insert(Proyek p) throws SQLException {
        PreparedStatement st = JembatanLogin.getMyLgn().getConnDB().
                prepareStatement("insert into proyek (tahun,nip,judul,tingkat,"
                + "jumlah,ajukan,setuju,pesan,kelompok, deskripsi)"
                + " values(?,?,?,?,?,?,?,?,?,?)");
        st.setString(3, p.getJudul());
        st.setString(1, p.getTahunAkademik());
        st.setString(2, p.getNip());
        st.setString(4, p.getTingkat());
        st.setString(5, p.getJumlah());
        st.setString(6, p.getAjukan());
        st.setString(7, "");
        st.setString(8, "");
        st.setString(9, "");
        st.setString(10, p.getDeskripsi());
        try {
            st.executeUpdate();
        } catch (SQLException sq) {
            System.out.println("" + sq.getMessage());
        }
        return p;
    }

    public void update(Proyek p) throws SQLException {
        PreparedStatement st = JembatanLogin.getMyLgn().getConnDB().prepareStatement("update "
                + "proyek set status=?, setuju=?, pesan=?, deskripsi=? where idproyek=?");

        st.setString(1, p.getStatus());
        st.setString(2, p.getSetuju());
        st.setString(3, p.getPesan());
        st.setString(4, p.getDeskripsi());
        st.setString(5, p.getIdPro());
        st.executeUpdate();
    }

    public List<Proyek> getAll() throws SQLException {
        String query;
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        query = "select idproyek,judul,tingkat, nama, tahun, status,jumlah, "
                + "ajukan,pesan,setuju, kelompok, deskripsi from proyek "
                + "join dosen using (nip)"
                
                + "order by nip asc";
        ResultSet rs = st.executeQuery(query);
        List<Proyek> listPro = new ArrayList<Proyek>();
        while (rs.next()) {
            Proyek p = new Proyek();
            p.setIdPro(rs.getString("idproyek"));
            p.setJudul(rs.getString("judul"));
            p.setNama(rs.getString("nama"));
            p.setTingkat(rs.getString("tingkat"));
            p.setTahunAkademik(rs.getString("tahun"));
            p.setStatus(rs.getString("status"));
            p.setJumlah(rs.getString("jumlah"));
            p.setAjukan(rs.getString("ajukan"));
            p.setPesan(rs.getString("pesan"));
            p.setSetuju(rs.getString("setuju"));
            p.setKelompok(rs.getString("kelompok"));
            p.setDeskripsi(rs.getString("deskripsi"));
            listPro.add(p);
        }

        return listPro;
    }
    
     public List<Proyek> getAllPTI() throws SQLException {
        String query;
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        query = "select idproyek,judul,tingkat, nama, tahun, status,jumlah, "
                + "ajukan,pesan,setuju, kelompok, deskripsi from proyek "
                + "join dosen using (nip)"
                +"where tingkat='I' and status='yes'"
                + "order by nip asc";
        ResultSet rs = st.executeQuery(query);
        List<Proyek> listPro = new ArrayList<Proyek>();
        while (rs.next()) {
            Proyek p = new Proyek();
            p.setIdPro(rs.getString("idproyek"));
            p.setJudul(rs.getString("judul"));
            p.setNama(rs.getString("nama"));
            p.setTingkat(rs.getString("tingkat"));
            p.setTahunAkademik(rs.getString("tahun"));
            p.setStatus(rs.getString("status"));
            p.setJumlah(rs.getString("jumlah"));
            p.setAjukan(rs.getString("ajukan"));
            p.setPesan(rs.getString("pesan"));
            p.setSetuju(rs.getString("setuju"));
            p.setKelompok(rs.getString("kelompok"));
            p.setDeskripsi(rs.getString("deskripsi"));
            listPro.add(p);
        }

        return listPro;
    }
     
     public List<Proyek> getAllPTII() throws SQLException {
        String query;
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        query = "select idproyek,judul,tingkat, nama, tahun, status,jumlah, "
                + "ajukan,pesan,setuju, kelompok, deskripsi from proyek "
                + "join dosen using (nip)"
                +"where tingkat='II' and status='yes'"
                + "order by nip asc";
        ResultSet rs = st.executeQuery(query);
        List<Proyek> listPro = new ArrayList<Proyek>();
        while (rs.next()) {
            Proyek p = new Proyek();
            p.setIdPro(rs.getString("idproyek"));
            p.setJudul(rs.getString("judul"));
            p.setNama(rs.getString("nama"));
            p.setTingkat(rs.getString("tingkat"));
            p.setTahunAkademik(rs.getString("tahun"));
            p.setStatus(rs.getString("status"));
            p.setJumlah(rs.getString("jumlah"));
            p.setAjukan(rs.getString("ajukan"));
            p.setPesan(rs.getString("pesan"));
            p.setSetuju(rs.getString("setuju"));
            p.setKelompok(rs.getString("kelompok"));
            p.setDeskripsi(rs.getString("deskripsi"));
            listPro.add(p);
        }

        return listPro;
    }

    public List<Proyek> getAllDOsen(String nip) throws SQLException {
        String query;
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        query = "select idproyek,judul,tingkat, nama, tahun, status,jumlah,"
                + " ajukan,pesan,setuju, deskripsi from proyek "
                + "join dosen using (nip)"
                + "where nip='" + nip + "'"
                + "order by nip asc ";
        ResultSet rs = st.executeQuery(query);
        List<Proyek> listPro = new ArrayList<Proyek>();
        while (rs.next()) {
            Proyek p = new Proyek();
            p.setIdPro(rs.getString("idproyek"));
            p.setJudul(rs.getString("judul"));
            p.setNama(rs.getString("nama"));
            p.setTingkat(rs.getString("tingkat"));
            p.setTahunAkademik(rs.getString("tahun"));
            p.setStatus(rs.getString("status"));
            p.setJumlah(rs.getString("jumlah"));
            p.setAjukan(rs.getString("ajukan"));
            p.setPesan(rs.getString("pesan"));
            p.setSetuju(rs.getString("setuju"));
            p.setDeskripsi(rs.getString("deskripsi"));
            listPro.add(p);
        }

        return listPro;
    }
    
    public void updateJumlah(Proyek p){
        try {
            PreparedStatement st = JembatanLogin.getMyLgn().getConnDB().prepareStatement("update "
                   + "proyek set jumlah=? where idproyek=?");

           st.setString(1, p.getJumlah());
           st.setString(2, p.getIdPro());
           st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ControllerProyek.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
