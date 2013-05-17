/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package penpot.Model;

/**
 *
 * @author haluan
 */
public class pekanKlmpk extends Proyek{
    private String nip,pekan,nilai;
    private int idProyek;

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getPekan() {
        return pekan;
    }

    public void setPekan(String pekan) {
        this.pekan = pekan;
    }

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }

    public int getIdProyek() {
        return idProyek;
    }

    public void setIdProyek(int idProyek) {
        this.idProyek = idProyek;
    }
    
}
