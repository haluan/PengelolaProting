/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package penpot.Model;

/**
 *
 * @author haluan
 */
public class Pesan {
    private String isi,pengirim,penerima,tgl, jam,idOrang,proto;

    public String getIdOrang() {
        return idOrang;
    }

    public String getProto() {
        return proto;
    }

    public void setProto(String proto) {
        this.proto = proto;
    }
    public void setIdOrang(String idOrang) {
        this.idOrang = idOrang;
    }

    
    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }
    
    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getPengirim() {
        return pengirim;
    }

    public void setPengirim(String pengirim) {
        this.pengirim = pengirim;
    }

    public String getPenerima() {
        return penerima;
    }

    public void setPenerima(String penerima) {
        this.penerima = penerima;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }
    
    
}
