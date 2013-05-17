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
public class Pegawai extends Orang{
    private String nip, password;

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        try{
        Integer.parseInt(nip);
        this.nip = nip;
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Tolong Masukan Angka");
        }
    }

  
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
