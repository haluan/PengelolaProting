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
import penpot.Objek.Pesan;

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
        st.setString(7, p.getTgl());
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
        st.setString(7, p.getTgl());
        st.setString(8, penerimaNip);
        st.executeUpdate();
        return p;
    }

    public List<Pesan> getAllMasuk(String userNow, String status) throws SQLException {
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        query = "select m.nama, d.nama, isi,tanggal, sender, jam,nip from mahasiswa m "
                + "join pesan using (nim)"
                + "join dosen d using (nip)"
                + "where kepada='"+userNow+"' order by proto desc,jam desc";
        ResultSet rs = st.executeQuery(query);
        List<Pesan> listMasuk = new ArrayList<Pesan>();
        while (rs.next()) {            
                Pesan pesan = new Pesan();
                pesan.setIsi(rs.getString("isi"));
                pesan.setTgl(rs.getString("tanggal"));
                pesan.setJam(rs.getString("jam"));
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

    public List<Pesan> getAllKeluar(String userNow) throws SQLException {
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        query = "select m.nama, d.nama, isi,tanggal, sender, jam,nip from mahasiswa m "
                + "join pesan using (nim)"
                + "join dosen d using (nip)"
                + "where sender='"+userNow+"' order by proto desc,jam desc";
        ResultSet rs = st.executeQuery(query);
        List<Pesan> listKeluar = new ArrayList<Pesan>();
        while (rs.next()) {
            if (userNow.equals(rs.getString("sender"))) {
                Pesan pesan = new Pesan();
                pesan.setIsi(rs.getString("isi"));
                pesan.setTgl(rs.getString("tanggal"));
                pesan.setJam(rs.getString("jam"));
                pesan.setPenerima(rs.getString(2));
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
    
    public void deletePsnMhsMsuk(Pesan p) throws SQLException {
        PreparedStatement st=JembatanLogin.getMyLgn().getConnDB().
                prepareStatement("delete from pesan where nim=? and nip=? "
                + "and tanggal=? and jam =? and sender=? and isi=?");
        st.setString(1, p.getPenerima());
        st.setString(2, p.getIdOrang());
        st.setString(3, p.getTgl());
        st.setString(4, p.getJam());
        st.setString(5, p.getIdOrang());
        st.setString(6, p.getIsi());
        st.executeUpdate();
        System.out.println(""+p.getIdOrang()+p.getPenerima()+p.getPengirim()+p.getJam()
                +p.getTgl()+p.getIsi());
    }
    
    public void deletePsnMhsKlr(Pesan p) throws SQLException {
        PreparedStatement st=JembatanLogin.getMyLgn().getConnDB().
                prepareStatement("delete from pesan where nim=? and nip=? "
                + "and tanggal=? and jam =? and sender=? and isi=?");
        st.setString(1, p.getPengirim());
        st.setString(2, p.getIdOrang());
        st.setString(3, p.getTgl());
        st.setString(4, p.getJam());
        st.setString(5, p.getPengirim());
        st.setString(6, p.getIsi());
        st.executeUpdate();
        System.out.println("NIM : "+p.getPengirim()+" NIP : "+p.getIdOrang()
                +"TGL : "+p.getTgl()+" JAM : "+p.getJam()+" SENDER : "+p.getIdOrang()+
                "ISI : "+p.getIsi());
    }
}