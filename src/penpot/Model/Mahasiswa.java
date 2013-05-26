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
public class Mahasiswa extends Kelompok{
    private String nim,nama,password,kelas,status,jenisKelamin
            , jabatan;
    private int nilaiTotal;

    public int getNilaiTotal() {
        return nilaiTotal;
    }
    
    public void setNilaiTotal(int nilaiTotal) {
        this.nilaiTotal += nilaiTotal;
    }
    private int idProyek;

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public int getIdProyek() {
        return idProyek;
    }

    public void setIdProyek(int idProyek) {
        this.idProyek = idProyek;
    }

   

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        
        try{
        Integer.parseInt(nim);
        this.nim = nim;
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Tolong Masukan Angka");
        }
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
}
