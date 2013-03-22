/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package penpot;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import penpot.Objek.Pesan;
import penpot.Proses.prosesPesan;
import penpot.View.FormInduk;

/**
 *
 * @author haluan
 */
public class Test extends Thread implements Runnable {

    Pesan pes = new Pesan();
     prosesPesan popes = new prosesPesan();

    public String showTimeNow() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String hasil = sdf.format(cal.getTime());
        return hasil;
    }

    public String showDateNow() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyy");
        String hasil = sdf.format(cal.getTime());
        return hasil;
    }

    @Override
    public synchronized void run() {
        try {
            this.jalan();
        } catch (InterruptedException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void jalan() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            try {
                pes.setIsi("Helo siswa bangga sekali dengan anda untuk yang ke-" + i);
                pes.setTgl(this.showDateNow());
                pes.setJam(this.showTimeNow());
                popes.siswaKirimDosen("36", "D1", pes);
                pes.setIsi("Helo dosen bangga sekali dengan anda untuk yang ke-" + i);
                popes.dosenKirimSiswa("D1", "36", pes);
                Thread.sleep(100);
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
