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
import penpot.Model.Pesan;

/**
 *
 * @author haluan
 */
public class prosesPesan {

    private Connection conn = JembatanLogin.getMyLgn().getConnDB();
    private String query;

    public Pesan dosenKirimSiswa(String pengirimNip, String penerimaNim, Pesan p) throws SQLException {
        PreparedStatement st = conn.
                prepareStatement("insert into pesan (isi,nim,nip,tanggal,sender,jam"
                + ",proto,kepada) values(?,?,?,?,?,?,?,?)");
        st.setString(1, p.getIsi());
        st.setString(2, penerimaNim);
        st.setString(3, pengirimNip);
        st.setString(4, p.getTgl());
        st.setString(5, pengirimNip);
        st.setString(6, p.getJam());
        st.setString(7, p.getProto());
        st.setString(8, penerimaNim);
        st.executeUpdate();
        return p;
    }

    public Pesan siswaKirimDosen(String pengirimNim, String penerimaNip, Pesan p) throws SQLException {
        PreparedStatement st = conn.
                prepareStatement("insert into pesan (isi,nim,nip,tanggal,sender,jam"
                + ",proto,kepada) values(?,?,?,?,?,?,?,?)");
        st.setString(1, p.getIsi());
        st.setString(2, pengirimNim);
        st.setString(3, penerimaNip);
        st.setString(4, p.getTgl());
        st.setString(5, pengirimNim);
        st.setString(6, p.getJam());
        st.setString(7, p.getProto());
        st.setString(8, penerimaNip);
        st.executeUpdate();
        return p;
    }
    
    public String jumlahMailMasuk(String userNow) throws SQLException{
        String hasil="0";
       Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        query = "select count(kepada) from mahasiswa m join pesan using (nim)"
                + " join dosen d using (nip) where kepada='"+userNow+"'";
        
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {            
           hasil=rs.getString(1);
        }
         return hasil;
    }
    
    public String jumlahMailKeluar(String userNow) throws SQLException{
       String hasil="0";
       Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        query = "select count(sender) from mahasiswa m join pesan using (nim)"
                + " join dosen d using (nip) where sender='"+userNow+"'";
        
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {            
           hasil=rs.getString(1);          
        }
         return hasil;
    }

    public List<Pesan> getAllMasuk(String userNow, String status, int temp) throws SQLException {
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        if(temp==0){
        query = "select m.nama, d.nama, isi,tanggal, sender, jam,nip from mahasiswa m "
                + "join pesan using (nim)"
                + "join dosen d using (nip)"
                + "where kepada='"+userNow+"' and hapusdosen='no' order by proto desc,jam desc";
        }
        else{
         query = "select m.nama, d.nama, isi,tanggal, sender, jam,nip from mahasiswa m "
                + "join pesan using (nim)"
                + "join dosen d using (nip)"
                + "where kepada='"+userNow+"' and hapussiswa='no' order by proto desc,jam desc";   
        }
        ResultSet rs = st.executeQuery(query);
        List<Pesan> listMasuk = new ArrayList<Pesan>();
        while (rs.next()) {            
                Pesan pesan = new Pesan();
                pesan.setIsi(rs.getString("isi"));
                pesan.setTgl(rs.getString("tanggal"));
                pesan.setJam(rs.getString("jam").toString());
                if(status.equals("siswa")){
                pesan.setPengirim(rs.getString(2));
                }else
                {
                    pesan.setPengirim(rs.getString(1));
                }
                pesan.setIdOrang(rs.getString("nip"));
                listMasuk.add(pesan);            
        }
        return listMasuk;
    }

    public List<Pesan> getAllKeluar(String userNow, String status, int temp) throws SQLException {
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        if(temp==0){
        query = "select m.nama, d.nama, isi,tanggal, sender, jam,nip from mahasiswa m "
                + "join pesan using (nim)"
                + "join dosen d using (nip)"
                + "where sender='"+userNow+"' and hapusdosen='no' order by proto desc,jam desc";
        }else{
             query = "select m.nama, d.nama, isi,tanggal, sender, jam,nip from mahasiswa m "
                + "join pesan using (nim)"
                + "join dosen d using (nip)"
                + "where sender='"+userNow+"' and hapussiswa='no' order by proto desc,jam desc";
        }
        ResultSet rs = st.executeQuery(query);
        List<Pesan> listKeluar = new ArrayList<Pesan>();
        while (rs.next()) {
            if (userNow.equals(rs.getString("sender"))) {
                Pesan pesan = new Pesan();
                pesan.setIsi(rs.getString("isi"));
                pesan.setTgl(rs.getString("tanggal"));
                pesan.setJam(rs.getString("jam").toString());
                if(status.equals("siswa")){
                pesan.setPenerima(rs.getString(2));
                }else
                {
                    pesan.setPenerima(rs.getString(1));
                }
                pesan.setIdOrang(rs.getString("nip"));
                listKeluar.add(pesan);
            }
        }
        return listKeluar;
    }
    
    public void komit() throws SQLException{
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        query = "commit";
        ResultSet rs = st.executeQuery(query);
    }
    
    public void deletePsnMhsMsuk(Pesan p, int temp) throws SQLException {
        
        if(temp==0){
        PreparedStatement st=JembatanLogin.getMyLgn().getConnDB().
                prepareStatement("update pesan set hapusdosen='yes' where "
                + "tanggal=? and jam =? and isi=?");
        st.setString(1, p.getTgl());
        st.setString(2, p.getJam());
        st.setString(3, p.getIsi());
        st.executeUpdate();
        }else{
            PreparedStatement st=JembatanLogin.getMyLgn().getConnDB().
                prepareStatement("update pesan set hapussiswa='yes' where nim=? and nip=? "
                + "and tanggal=? and jam =? and sender=? and isi=?");
            st.setString(1, p.getPenerima());
        st.setString(2, p.getIdOrang());
        st.setString(3, p.getTgl());
        st.setString(4, p.getJam());
        st.setString(5, p.getIdOrang());
        st.setString(6, p.getIsi());
        st.executeUpdate();
        }
        
        
    }
    
    public void deletePsnMhsKlr(Pesan p, int temp) throws SQLException {
        if(temp==0){
        PreparedStatement st=JembatanLogin.getMyLgn().getConnDB().
                prepareStatement("update pesan set hapusdosen='yes' where "
                + "tanggal=? and jam =? and isi=?");
        
        st.setString(1, p.getTgl());
        st.setString(2, p.getJam());
        st.setString(3, p.getIsi());
        st.executeUpdate();
        }else{
           PreparedStatement st=JembatanLogin.getMyLgn().getConnDB().
                prepareStatement("update pesan set hapussiswa='yes' where nim=? and nip=? "
                + "and tanggal=? and jam =? and sender=? and isi=?");
        st.setString(1, p.getPengirim());
        st.setString(2, p.getIdOrang());
        st.setString(3, p.getTgl());
        st.setString(4, p.getJam());
        st.setString(5, p.getPengirim());
        st.setString(6, p.getIsi());
        st.executeUpdate();    
        }
       
    }
}