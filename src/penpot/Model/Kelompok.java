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
public class Kelompok extends Proyek{
    private String  namaKelompok, diajukan;

    public String getDiajukan() {
        return diajukan;
    }

    public void setDiajukan(String diajukan) {
        this.diajukan = diajukan;
    }
    private int idKelompok,proposal,mingguan,presentasi,dokumentasi;

    public int getProposal() {
        return proposal;
    }

    public void setProposal(int proposal) {
        if(proposal<0){
            JOptionPane.showMessageDialog(null, "Nilai Harus Postif");
        }else if(proposal>20){
            JOptionPane.showMessageDialog(null, "Nilai maksimal 20");
        }else{
            this.proposal = proposal;
        }
    }

    public int getMingguan() {
        return mingguan;
    }

    public void setMingguan(int mingguan) {
         if(proposal<0){
            JOptionPane.showMessageDialog(null, "Nilai Harus Postif");
        }else if(proposal>50){
            JOptionPane.showMessageDialog(null, "Nilai maksimal 50");
        }else{
            this.mingguan = mingguan;
        }
    }

    public int getPresentasi() {
        return presentasi;
    }

    public void setPresentasi(int presentasi) {
       if(proposal<0){
            JOptionPane.showMessageDialog(null, "Nilai Harus Postif");
        }else if(proposal>10){
            JOptionPane.showMessageDialog(null, "Nilai maksimal 10");
        }else{
            this.presentasi = presentasi;
        }
    }

    public int getDokumentasi() {
        return dokumentasi;
    }

    public void setDokumentasi(int dokumentasi) {
       if(proposal<0){
            JOptionPane.showMessageDialog(null, "Nilai Harus Postif");
        }else if(proposal>20){
            JOptionPane.showMessageDialog(null, "Nilai maksimal 20");
        }else{
            this.dokumentasi = dokumentasi;
        }
    }

  
    public int getIdKelompok() {
        return idKelompok;
    }

    public void setIdKelompok(int idKelompok) {
        this.idKelompok = idKelompok;
    }

    public String getNamaKelompok() {
        return namaKelompok;
    }

    public void setNamaKelompok(String namaKelompok) {
        this.namaKelompok = namaKelompok;
    }
    
}
