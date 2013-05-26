/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package penpot.Model;

import javax.swing.JOptionPane;

/**
 *
 * @author haluan
 */
public class Proyek extends Dosen{
    private String judul, nip, tahunAkademik, tingkat, 
            status,jumlah,ajukan,setuju,pesan, deskripsi;
    private int idProyek, maksKelompok;

    public int getMaksKelompok() {
        return maksKelompok;
    }

    public void setMaksKelompok(int maksKelompok) {
        this.maksKelompok = maksKelompok;
    }
    
    
    
    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

   

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getAjukan() {
        return ajukan;
    }

    public void setAjukan(String ajukan) {
        this.ajukan = ajukan;
    }

    public String getSetuju() {
        return setuju;
    }

    public void setSetuju(String setuju) {
        this.setuju = setuju;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTingkat() {
        return tingkat;
    }

    public void setTingkat(String tingkat) {
        this.tingkat = tingkat;
    }
    
    

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getTahunAkademik() {
        return tahunAkademik;
    }

    public void setTahunAkademik(String tahunAkademik) {
        this.tahunAkademik = tahunAkademik;
    }

    public int getIdProyek() {
        return idProyek;
    }

    public void setIdProyek(int idProyek) {
        this.idProyek = idProyek;
    }
   
    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }
    
    
}
