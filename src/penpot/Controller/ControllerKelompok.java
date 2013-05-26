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
import penpot.Koneksi.JembatanLogin;
import penpot.Model.Kelompok;
import penpot.Model.Mahasiswa;
import penpot.Model.Proyek;

/**
 *
 * @author haluan
 */
public class ControllerKelompok {

    public List<Kelompok> getAll(String status) throws SQLException {
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        String query = "select k.nama, k.jumlah,k.dibuat,k.idkelompok"
                + " from kelompok k join proyek p using (idproyek)"
                + "where p.tingkat='"+status+"'"
                + "order by k.nama asc";
        ResultSet rs = st.executeQuery(query);
        List<Kelompok> listKel = new ArrayList<>();
        
        while(rs.next()){
            Kelompok k = new Kelompok();
            k.setNamaKelompok(rs.getString(1));
            k.setJumlah(""+rs.getInt(2));
            k.setDiajukan(rs.getString(3));
            k.setIdKelompok(rs.getInt(4));
            listKel.add(k);
        }
        return listKel;
    }

    public Kelompok insert(Kelompok k) throws SQLException {
        
        PreparedStatement st = JembatanLogin.getMyLgn().getConnDB().
                prepareStatement("insert into kelompok (nama,idProyek,jumlah, dibuat)"
                + " values(?,?,?,?)");
        st.setString(1, k.getNamaKelompok());
        st.setInt(2, k.getIdProyek());
        st.setInt(3, k.getMaksKelompok());
        st.setString(4, k.getDiajukan());
        st.executeUpdate();
        Statement str = JembatanLogin.getMyLgn().getConnDB().createStatement();
            String query = "select idkelompok from kelompok where dibuat='"+k.getDiajukan()+"'";
            ResultSet rs = str.executeQuery(query);
            while(rs.next()){
            k.setIdKelompok(rs.getInt(1));
            }
        return k;
    }

    public Kelompok getNilaiPT(String nim) {

        Kelompok p = new Kelompok();
        try {

            Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
            String query = "select proposal,mingguan,presentasi,dokumentasi from mahasiswa join kelompok using (idkelompok)"
                    + "where nim='" + nim + "'";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                p.setProposal(rs.getInt("proposal"));
                p.setMingguan(rs.getInt("mingguan"));
                p.setPresentasi(rs.getInt("presentasi"));
                p.setDokumentasi(rs.getInt("dokumentasi"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControllerMahasiswa.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    public void insertNilaiProposal(Kelompok p) {
        try {
            PreparedStatement st = JembatanLogin.getMyLgn().getConnDB().prepareStatement("update "
                    + "kelompok set proposal=? where idkelompok=? ");

            st.setInt(1, p.getProposal());
            st.setInt(2, p.getIdKelompok());
            
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ControllerKelompok.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertNilaiMingguan(Kelompok p) {
        try {
            PreparedStatement st = JembatanLogin.getMyLgn().getConnDB().prepareStatement("update "
                    + "kelompok set mingguan=? where idKelompok=?");

            st.setInt(1, p.getMingguan());
            st.setInt(2, p.getIdKelompok());
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ControllerKelompok.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertNilaiPresentasi(Kelompok p) {
        try {
            PreparedStatement st = JembatanLogin.getMyLgn().getConnDB().prepareStatement("update "
                    + "kelompok set presentasi=? where idKelompok=?");

            st.setInt(1, p.getPresentasi());
            st.setInt(2, p.getIdKelompok());
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ControllerKelompok.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertNilaiDokumentasi(Kelompok p) {
        try {
            PreparedStatement st = JembatanLogin.getMyLgn().getConnDB().prepareStatement("update "
                    + "kelompok set dokumentasi=? where idKelompok=?");

            st.setInt(1, p.getDokumentasi());
            st.setInt(2, p.getIdKelompok());
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ControllerKelompok.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
