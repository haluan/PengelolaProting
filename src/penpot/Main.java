/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package penpot;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import penpot.Koneksi.JembatanLogin;
import penpot.View.FormInduk;

/**
 *
 * @author haluan
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        try {
            // TODO code application logic here
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        new JembatanLogin();
        FormInduk fid = new FormInduk();
        fid.show();
//        fid.setLayout(new MigLayout());

//       Test t= new Test();
//       t.run();


    }
}
