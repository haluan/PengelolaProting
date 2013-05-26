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
import penpot.Model.Kelompok;
import penpot.Model.Proyek;

/**
 *
 * @author haluan
 */
public class ControllerProyek {

    public Proyek insert(Proyek p) throws SQLException {
        PreparedStatement st = JembatanLogin.getMyLgn().getConnDB().
                prepareStatement("insert into proyek (tahun,nip,judul,tingkat,"
                + "jumlah,ajukan,setuju,pesan, deskripsi, maksimalkelompok)"
                + " values(?,?,?,?,?,?,?,?,?,?)");
        st.setString(3, p.getJudul());
        st.setString(1, p.getTahunAkademik());
        st.setString(2, p.getNip());
        st.setString(4, p.getTingkat());
        st.setString(5, p.getJumlah());
        st.setString(6, p.getAjukan());
        st.setString(7, "");
        st.setString(8, "");
        st.setString(9, p.getDeskripsi());
        st.setInt(10, p.getMaksKelompok());
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
        st.setInt(5, p.getIdProyek());
        st.executeUpdate();
    }

    public List<Proyek> getAll() throws SQLException {
        String query;
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        query = "select idproyek,judul,tingkat, nama, tahun, status,jumlah, "
                + "ajukan,pesan,setuju, deskripsi"
                + " from proyek "
                + "join dosen using (nip)"
                
                + "order by nip asc";
        ResultSet rs = st.executeQuery(query);
        List<Proyek> listPro = new ArrayList<Proyek>();
        while (rs.next()) {
            Proyek p = new Proyek();
            p.setIdProyek(rs.getInt("idproyek"));
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
    
    
    
     public List<Proyek> getAllPTI() throws SQLException {
        String query;
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        query = "select idproyek,judul,tingkat, nama, tahun, status,jumlah, "
                + "ajukan,pesan,setuju, deskripsi,maksimalkelompok from proyek "
                + "join dosen using (nip)"
                +"where tingkat='I' and status='yes'"
                + "order by nip asc";
        ResultSet rs = st.executeQuery(query);
        List<Proyek> listPro = new ArrayList<Proyek>();
        while (rs.next()) {
            Proyek p = new Proyek();
            p.setIdProyek(rs.getInt("idproyek"));
            p.setJudul(rs.getString("judul"));
            p.setNama(rs.getString("nama"));
            p.setTingkat(rs.getString("tingkat"));
            p.setTahunAkademik(rs.getString("tahun"));
            p.setStatus(rs.getString("status"));
            p.setJumlah(rs.getString("jumlah"));
            p.setAjukan(rs.getString("ajukan"));
            p.setPesan(rs.getString("pesan"));
            p.setSetuju(rs.getString("setuju"));
            p.setMaksKelompok(rs.getInt("maksimalkelompok"));
            p.setDeskripsi(rs.getString("deskripsi"));
            listPro.add(p);
        }

        return listPro;
    }
     
     public List<Proyek> getAllPTII() throws SQLException {
        String query;
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        query = "select idproyek,judul,tingkat, nama, tahun, status,jumlah, "
                + "ajukan,pesan,setuju, deskripsi,maksimalkelompok from proyek "
                + "join dosen using (nip)"
                +"where tingkat='II' and status='yes'"
                + "order by nip asc";
        ResultSet rs = st.executeQuery(query);
        List<Proyek> listPro = new ArrayList<Proyek>();
        while (rs.next()) {
            Proyek p = new Proyek();
            p.setIdProyek(rs.getInt("idproyek"));
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
            p.setMaksKelompok(rs.getInt("maksimalkelompok"));
            listPro.add(p);
        }

        return listPro;
    }
     
     public List<Kelompok> getAllKelompok(String nip) throws SQLException{
          String query;
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        query = "select idkelompok, k.nama, p.judul, p.tahun"
                + "  from kelompok k join proyek p using (idproyek)"
                + "join dosen using (nip)"
                + "where nip='" + nip + "'"
                + "order by nip asc ";
        ResultSet rs = st.executeQuery(query);
        List<Kelompok> listKel=new ArrayList<>();
        while(rs.next()){
            Kelompok kel=new Kelompok();
            kel.setIdKelompok(rs.getInt("idkelompok"));
            kel.setNamaKelompok(rs.getString(2));
            kel.setJudul(rs.getString(3));
            kel.setTahunAkademik(rs.getString(4));
            listKel.add(kel);
        }
        return listKel;
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
            p.setIdProyek(rs.getInt("idproyek"));
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
           st.setInt(2, p.getIdProyek());
           st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ControllerProyek.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
