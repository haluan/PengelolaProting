/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package penpot.Model;

/**
 *
 * @author haluan
 */
public class Proyek extends Dosen{
    private String idPro, judul, nip, tahunAkademik, tingkat, 
            status,jumlah,ajukan,setuju,pesan, kelompok, deskripsi;
    private int proposal,mingguan,presentasi,dokumentasi;

    public int getProposal() {
        return proposal;
    }

    public void setProposal(int proposal) {
        this.proposal = proposal;
    }

    public int getMingguan() {
        return mingguan;
    }

    public void setMingguan(int mingguan) {
        this.mingguan = mingguan;
    }

    public int getPresentasi() {
        return presentasi;
    }

    public void setPresentasi(int presentasi) {
        this.presentasi = presentasi;
    }

    public int getDokumentasi() {
        return dokumentasi;
    }

    public void setDokumentasi(int dokumentasi) {
        this.dokumentasi = dokumentasi;
    }
    
    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getKelompok() {
        return kelompok;
    }

    public void setKelompok(String kelompok) {
        this.kelompok = kelompok;
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
    public String getIdPro() {
        return idPro;
    }

    public void setIdPro(String idPro) {
        this.idPro = idPro;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }
    
    
}
