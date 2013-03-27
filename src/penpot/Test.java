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
import penpot.Koneksi.JembatanLogin;
import penpot.Model.Admin;
import penpot.Model.Dosen;
import penpot.Model.Kelompok;
import penpot.Model.Mahasiswa;
import penpot.Model.Pesan;
import penpot.Model.Proyek;
import penpot.Controller.ControllerAdmin;
import penpot.Controller.ControllerDosen;
import penpot.Controller.ControllerMahasiswa;
import penpot.Controller.ControllerProyek;
import penpot.Controller.prosesPesan;
import penpot.View.FormInduk;

/**
 *
 * @author haluan
 */
public class Test extends Thread implements Runnable {

    Pesan pes = new Pesan();
    prosesPesan popes = new prosesPesan();

    public void testProyek() {
        try {
            ControllerProyek cp = new ControllerProyek();

            Proyek p = new Proyek();
            for (int i = 0; i < 50; i++) {
                p.setIdPro("N" + (i + 1));
                p.setJudul("APP" + (i + 1));
                p.setTahunAkademik("2013");
                p.setTingkat("II");
                p.setNip("190");
                cp.insert(p);
            }


        } catch (SQLException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
    public String showDateNowS() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
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
                pes.setTgl(this.showDateNowS());
                pes.setJam(this.showTimeNow());
                popes.siswaKirimDosen("36", "190", pes);
                pes.setIsi("Helo dosen bangga sekali dengan anda untuk yang ke-" + i);
                popes.dosenKirimSiswa("190", "36", pes);
                Thread.sleep(1);
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void testAdmin(){
        ControllerMahasiswa cm = new ControllerMahasiswa();
        ControllerDosen cd = new ControllerDosen();
        ControllerAdmin ca = new ControllerAdmin();
        
        Mahasiswa m = new Mahasiswa();
        Dosen d = new Dosen();
        Admin a = new Admin();
        
        for(int i=0;i<100;i++){
            try {
                d.setNip("112345677"+(i+1));
                d.setNama("Dosen "+(i+1));
                d.setPassword(""+(i+1));
                cd.insert(d);
            } catch (SQLException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        for (int i=1;i<33;i++){
            try {
                m.setNim("6131100"+i);
                m.setNama("MHS "+i);
                m.setPassword(""+i);
                m.setKelas("D3 IF 35 01");
                m.setStatus("II");
                m.setJabatan("ANALYST");
                m.setJenisKelamin("n/a");
                m.setIdKelompok("K1");
                cm.insert(m);
            } catch (SQLException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        for(int i=200;i<300;i++){
            try {
                a.setNip("112345677"+i);
                a.setNama("Admin"+i);
                a.setPassword(""+i);
                ca.insert(a);
            } catch (SQLException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    public static void main(String[] args) {
         new JembatanLogin();
        Test t = new Test();
//        t.testProyek();
//
        t.run();
//        t.testProyek();
//        t.testAdmin();
    }
}
