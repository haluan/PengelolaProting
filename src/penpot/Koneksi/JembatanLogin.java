/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package penpot.Koneksi;

import javax.swing.JOptionPane;

/**
 *
 * @author haluan
 */
public class JembatanLogin {
    private static LoginAction myLgn;

    public static LoginAction getMyLgn() {
        return myLgn;
    }
    
    public JembatanLogin(){
        myLgn = new LoginAction("proting", "z");


        if (myLgn.cekLogin()) {
            
        } else {

            JOptionPane.showMessageDialog(null,
                    "Login Anda tidak diterima server.\n"
                    + "Silahkan di periksa ulang!", "Konfirmasi...",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    
}
