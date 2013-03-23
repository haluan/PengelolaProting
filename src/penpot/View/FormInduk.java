/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package penpot.View;

import com.sun.java.swing.plaf.windows.WindowsMenuBarUI;
import com.sun.java.swing.plaf.windows.WindowsMenuItemUI;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import penpot.Objek.Admin;
import penpot.Objek.Dosen;
import penpot.Objek.Kelompok;
import penpot.Objek.Mahasiswa;
import penpot.Objek.Pesan;
import penpot.Proses.ControllerAdmin;
import penpot.Proses.ControllerDosen;
import penpot.Proses.ControllerMahasiswa;
import penpot.Proses.kelolaBaru;
import penpot.Proses.prosesMasuk;
import penpot.Proses.prosesPesan;

/**
 *
 * @author haluan
 */
public class FormInduk extends javax.swing.JFrame {

    /**
     * Creates new form FormInduk
     */
    private Thread t1 = new indor();
    private int sinyalemen;
    private int sinyalemenDaftar;
    prosesPesan propes = new prosesPesan();
    private kelolaBaru kb = new kelolaBaru();
    private prosesMasuk posmas = new prosesMasuk();
    //Record u/ tabel
    private List<Pesan> recordMasuk = new ArrayList<Pesan>();
    private List<Pesan> recordKeluar = new ArrayList<Pesan>();
    private List<Mahasiswa> recordSiswa = new ArrayList<Mahasiswa>();
    private List<Dosen> recordDosen = new ArrayList<>();
    private List<Mahasiswa> recordTemanKelompok = new ArrayList<>();
    //Instansiasi Controller
    private ControllerMahasiswa cm = new ControllerMahasiswa();
    private ControllerDosen cd = new ControllerDosen();
    private ControllerAdmin ca = new ControllerAdmin();
    private prosesPesan prosespesan = new prosesPesan();
    //variabel tambahan
    private int row;
    private static int seen;
    Pesan pesaN = new Pesan();
    private static String onUsed = "", tempPengirim, tempPenerima;
    private static int sinyalHapus;
    private static String nim, nip;
    private String idKelompok;

    public FormInduk() {
        initComponents();
        this.peringatan1();
        this.setTitle("APPT");
        newMail.setSize(600, 400);
        bacaPsnMasukMhs.setSize(600, 400);
        editMahasiswa.setSize(400, 300);
        t1.start();
        jComboBox1.addActionListener(new combox());
        jComboBox1.setModel(new DefaultComboBoxModel(new String[]{"mahasiswa", "kaprodi", "dosen", "admin"}));

        statusDaftar.addActionListener(new comboxDaftar());
        statusDaftar.setModel(new DefaultComboBoxModel(new String[]{"mahasiswa", "kaprodi", "dosen", "admin"}));
    }

    void isiTabelMasuk(String status) {
        Object data[][] = new Object[recordMasuk.size()][4];
        int x = 0;
        for (Pesan pes : recordMasuk) {
            data[x][0] = pes.getTgl();
            data[x][3] = pes.getIsi();
            data[x][2] = pes.getPengirim();
            data[x][1] = pes.getJam();
            x++;
        }
        if (x == 0) {
            totalInMail.setText("total pesan masuk : 0");
        } else {

            totalInMail.setText("total pesan masuk : " + x);
        }
        String judul[] = {"tanggal", "jam", "dari", "isi"};
        if (status.equals("siswa")) {
            pesanMasukSiswa.setModel(new DefaultTableModel(data, judul));
            jScrollPane2.setViewportView(pesanMasukSiswa);
        } else {
            pesanMasukDosen.setModel(new DefaultTableModel(data, judul));
            jScrollPane4.setViewportView(pesanMasukDosen);
        }

    }

    void isiTabelKeluar(String status) {
        int x = 0;
        Object data[][] = new Object[recordKeluar.size()][4];
        for (Pesan pes : recordKeluar) {
            data[x][0] = pes.getTgl();
            data[x][3] = pes.getIsi();
            data[x][2] = pes.getPenerima();
            data[x][1] = pes.getJam();
            x++;
        }
        if (x == 0) {
            totalOutMail.setText("total pesan keluar : " + x);
        } else {
            totalOutMail.setText("total pesan keluar : " + x);
        }
        String judul[] = {"tanggal", "jam", "ke", "isi"};
        if (status.equals("siswa")) {
            pesanKeluarSiswa.setModel(new DefaultTableModel(data, judul));
            jScrollPane3.setViewportView(pesanKeluarSiswa);
        } else {
            pesanKeluarDosen.setModel(new DefaultTableModel(data, judul));
            jScrollPane5.setViewportView(pesanKeluarDosen);
        }
    }

    void isiTabeSiswa() {
        int x = 0;
        Object data[][] = new Object[recordSiswa.size()][4];
        for (Mahasiswa m : recordSiswa) {
            data[x][0] = m.getNim();
            data[x][1] = m.getNama();
            data[x][2] = m.getKelas();
            data[x][3] = m.getStatus();
            x++;
        }
        String judul[] = {"nim", "nama", "kelas", "status"};
        tabelSiswaAdmin.setModel(new DefaultTableModel(data, judul));
        jScrollPane7.setViewportView(tabelSiswaAdmin);
    }

    void isiTabelTemanKelompok() {
        int x = 0;
        Object data[][] = new Object[recordTemanKelompok.size()][5];
        for (Mahasiswa m : recordTemanKelompok) {
            data[x][0] = m.getNim();
            data[x][1] = m.getNama();
            data[x][2] = m.getKelas();
            data[x][3] = m.getStatus();
            data[x][4] = m.getJabatan();
            x++;
        }
        String judul[] = {"nim", "nama", "kelas", "status", "jabatan"};
        tabelTemanKelompok.setModel(new DefaultTableModel(data, judul));
        jScrollPane10.setViewportView(tabelTemanKelompok);
    }

    void isiTabelDosen() {
        int x = 0;
        Object data[][] = new Object[recordSiswa.size()][2];
        for (Dosen d : recordDosen) {
            data[x][0] = d.getNip();
            data[x][1] = d.getNama();
            x++;
        }
        String judul[] = {"nip", "nama"};
        tabelDosenAdmin.setModel(new DefaultTableModel(data, judul));
        jScrollPane8.setViewportView(tabelDosenAdmin);
    }

    private void loadPesan(String status) throws SQLException {
        recordMasuk = prosespesan.getAllMasuk(onUsed, status);
        recordKeluar = prosespesan.getAllKeluar(onUsed, status);
        isiTabelMasuk(status);
        isiTabelKeluar(status);
    }

    private class combox implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            // TODO Auto-generated method stub
            sinyal();

        }
    }

    private class comboxDaftar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            // TODO Auto-generated method stub
            sinyalDaftar();

        }
    }

    private void sinyal() {
        if (jComboBox1.getSelectedIndex() == 0) {
            sinyalemen = 0;
            used.setText("nim");
        }
        if (jComboBox1.getSelectedIndex() == 1) {
            sinyalemen = 1;
            used.setText("nip");
        }
        if (jComboBox1.getSelectedIndex() == 2) {
            sinyalemen = 2;
            used.setText("nip");
        }
        if (jComboBox1.getSelectedIndex() == 3) {
            sinyalemen = 3;
            used.setText("nip");
        }

    }

    private void sinyalDaftar() {
        if (statusDaftar.getSelectedIndex() == 0) {
            sinyalemenDaftar = 0;//siswa
            idDaftar.setText("nim");
            kelasBaru.setText("kelas");
            kelasDaftarBaru.show();
        }
        if (statusDaftar.getSelectedIndex() == 1) {
            sinyalemenDaftar = 1;//kaprodi
            idDaftar.setText("nip");
            kelasBaru.setText("");
            kelasDaftarBaru.hide();
        }
        if (statusDaftar.getSelectedIndex() == 2) {
            sinyalemenDaftar = 2;//dosen
            idDaftar.setText("nip");
            kelasBaru.setText("");
            kelasDaftarBaru.hide();
        }
        if (statusDaftar.getSelectedIndex() == 3) {
            sinyalemenDaftar = 3;//admin
            idDaftar.setText("nip");
            kelasBaru.setText("");
            kelasDaftarBaru.hide();
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

    public void update() {
        if (sinyalemen == 0) {
            tgl1.setText(this.showDateNow());
            waktu1.setText(this.showTimeNow() + " WIB");
        } else if (sinyalemen == 2) {
            tgl2.setText(this.showDateNow());
            waktu2.setText(this.showTimeNow() + " WIB");
        } else if (sinyalemen == 1) {
            tgl3.setText(this.showDateNow());
            waktu3.setText(this.showTimeNow() + " WIB");
        } else {
            tgl4.setText(this.showDateNow());
            waktu4.setText(this.showTimeNow() + " WIB");
        }
    }

    void peringatan1() {
        peringatanMasuk.hide();
    }

    void kosongAwal() {
        userMasuk.setText(null);
        passwordMasuk.setText(null);
    }

    void keluar() {

        CardLayout cad = (CardLayout) induk.getLayout();
        cad.show(induk, "masuk");
        onUsed = "";
    }

    class indor extends Thread implements Runnable {

        @Override
        public synchronized void run() {
            while (isAlive()) {
                update();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        about = new javax.swing.JFrame();
        jPanel12 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        newMail = new javax.swing.JFrame();
        untuk = new javax.swing.JLabel();
        isiPesan = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        mailContent = new javax.swing.JTextArea();
        cancelMail = new javax.swing.JButton();
        sendMail = new javax.swing.JButton();
        namaDosenSurat = new javax.swing.JLabel();
        bacaPsnMasukMhs = new javax.swing.JFrame();
        jLabel15 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        isiBukaPesan = new javax.swing.JTextArea();
        editMahasiswa = new javax.swing.JFrame();
        jPanel29 = new javax.swing.JPanel();
        idEditUserAdmin = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        kelasMhsAdmin = new javax.swing.JLabel();
        statusMhsAdmin = new javax.swing.JLabel();
        editNimAdmin = new javax.swing.JTextField();
        editNamaAdmin = new javax.swing.JTextField();
        editKelasAdmin = new javax.swing.JTextField();
        editStatusAdmin = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        newMail1 = new javax.swing.JFrame();
        untuk1 = new javax.swing.JLabel();
        isiPesan1 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        mailContent1 = new javax.swing.JTextArea();
        cancelMail1 = new javax.swing.JButton();
        sendMail1 = new javax.swing.JButton();
        daftarBinaanDosen = new javax.swing.JComboBox();
        induk = new javax.swing.JPanel();
        masuk = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        userMasuk = new javax.swing.JTextField();
        used = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        passwordMasuk = new javax.swing.JPasswordField();
        peringatanMasuk = new javax.swing.JLabel();
        ikonIT = new javax.swing.JLabel();
        ikonIF = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        menu = new javax.swing.JPanel();
        navigasi = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        tgl1 = new javax.swing.JLabel();
        waktu1 = new javax.swing.JLabel();
        jButton25 = new javax.swing.JButton();
        konten = new javax.swing.JPanel();
        nilai = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        statistikNilaiMhs = new javax.swing.JButton();
        projek = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        kelompok = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tabelTemanKelompok = new javax.swing.JTable();
        jLabelan1 = new javax.swing.JLabel();
        jLabelan = new javax.swing.JLabel();
        namaDosenMhs = new javax.swing.JLabel();
        namaKelompokMhs = new javax.swing.JLabel();
        upload = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        pesan = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        tabMailMhs = new javax.swing.JTabbedPane();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        pesanMasukSiswa = new javax.swing.JTable();
        totalInMail = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        pesanKeluarSiswa = new javax.swing.JTable();
        totalOutMail = new javax.swing.JLabel();
        pesanBaruMhs = new javax.swing.JToggleButton();
        refreshPesanMhs = new javax.swing.JToggleButton();
        setelanMhs = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        dosen = new javax.swing.JPanel();
        navDos = new javax.swing.JPanel();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        tgl2 = new javax.swing.JLabel();
        waktu2 = new javax.swing.JLabel();
        kontenDos = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        pesanMasukDosen = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        pesanKeluarDosen = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel13 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        kaprodi = new javax.swing.JPanel();
        navKap = new javax.swing.JPanel();
        jButton17 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        tgl3 = new javax.swing.JLabel();
        waktu3 = new javax.swing.JLabel();
        kontenKap = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        admin = new javax.swing.JPanel();
        navKap1 = new javax.swing.JPanel();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        tgl4 = new javax.swing.JLabel();
        waktu4 = new javax.swing.JLabel();
        jButton27 = new javax.swing.JButton();
        daftarUser = new javax.swing.JButton();
        kontenAd = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        namaDaftar = new javax.swing.JLabel();
        idDaftar = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        namaDaftarBaru = new javax.swing.JTextField();
        idDaftarBaru = new javax.swing.JTextField();
        passwordDfatarBaru = new javax.swing.JTextField();
        daftarOlehAdmin = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        statusDaftar = new javax.swing.JComboBox();
        kelasBaru = new javax.swing.JLabel();
        kelasDaftarBaru = new javax.swing.JTextField();
        jPanel25 = new javax.swing.JPanel();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel26 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabelSiswaAdmin = new javax.swing.JTable();
        jPanel27 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tabelDosenAdmin = new javax.swing.JTable();
        jPanel28 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tabelAdminAdmin = new javax.swing.JTable();
        jMenuBar2 = new javax.swing.JMenuBar();
        help = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        about.setAlwaysOnTop(true);
        about.setResizable(false);

        jLabel31.setText("diproduksi oleh :");

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel32.setText("P.T INOVASI TEKNIKA");

        jLabel33.setText("2013");

        jLabel3.setText("anggota :");

        jLabel34.setText("Haluan Mohammad Irsad");

        jLabel35.setText("Laura Sinar Utami");

        jLabel36.setText("Syara Zhuhriyami");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addGap(52, 52, 52))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel36)
                            .addComponent(jLabel35))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(37, 37, 37))
                    .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(58, 58, 58))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel33)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout aboutLayout = new javax.swing.GroupLayout(about.getContentPane());
        about.getContentPane().setLayout(aboutLayout);
        aboutLayout.setHorizontalGroup(
            aboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aboutLayout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(86, Short.MAX_VALUE))
        );
        aboutLayout.setVerticalGroup(
            aboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aboutLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        untuk.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        untuk.setText("untuk");

        isiPesan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        isiPesan.setText("pesan");

        mailContent.setColumns(20);
        mailContent.setRows(5);
        jScrollPane1.setViewportView(mailContent);

        cancelMail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cancelMail.setText("batal");
        cancelMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelMailActionPerformed(evt);
            }
        });

        sendMail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sendMail.setText("kirim");
        sendMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMailActionPerformed(evt);
            }
        });

        namaDosenSurat.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        namaDosenSurat.setForeground(new java.awt.Color(51, 51, 255));
        namaDosenSurat.setText("Dosen Pembimbing");

        javax.swing.GroupLayout newMailLayout = new javax.swing.GroupLayout(newMail.getContentPane());
        newMail.getContentPane().setLayout(newMailLayout);
        newMailLayout.setHorizontalGroup(
            newMailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newMailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(newMailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newMailLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(sendMail, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelMail, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(newMailLayout.createSequentialGroup()
                        .addGroup(newMailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(isiPesan)
                            .addGroup(newMailLayout.createSequentialGroup()
                                .addComponent(untuk)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(namaDosenSurat)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        newMailLayout.setVerticalGroup(
            newMailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newMailLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(newMailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(untuk)
                    .addComponent(namaDosenSurat))
                .addGap(33, 33, 33)
                .addComponent(isiPesan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(newMailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelMail)
                    .addComponent(sendMail))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("dari :");

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(51, 51, 255));
        jLabel43.setText("Dosen Pembimbing");

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel44.setText("jLabel44");

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel45.setText("jLabel45");

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel46.setText("pesan");

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setText("keluar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton26.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton26.setText("hapus");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));

        isiBukaPesan.setEditable(false);
        isiBukaPesan.setColumns(20);
        isiBukaPesan.setRows(5);
        jScrollPane6.setViewportView(isiBukaPesan);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout bacaPsnMasukMhsLayout = new javax.swing.GroupLayout(bacaPsnMasukMhs.getContentPane());
        bacaPsnMasukMhs.getContentPane().setLayout(bacaPsnMasukMhsLayout);
        bacaPsnMasukMhsLayout.setHorizontalGroup(
            bacaPsnMasukMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bacaPsnMasukMhsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bacaPsnMasukMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bacaPsnMasukMhsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(bacaPsnMasukMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bacaPsnMasukMhsLayout.createSequentialGroup()
                                .addComponent(jButton26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bacaPsnMasukMhsLayout.createSequentialGroup()
                                .addComponent(jLabel44)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel45))))
                    .addGroup(bacaPsnMasukMhsLayout.createSequentialGroup()
                        .addGroup(bacaPsnMasukMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel46)
                            .addGroup(bacaPsnMasukMhsLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel43)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        bacaPsnMasukMhsLayout.setVerticalGroup(
            bacaPsnMasukMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bacaPsnMasukMhsLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(bacaPsnMasukMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel43))
                .addGap(27, 27, 27)
                .addGroup(bacaPsnMasukMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(jLabel45))
                .addGap(10, 10, 10)
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(bacaPsnMasukMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton26))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        editMahasiswa.getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        idEditUserAdmin.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        idEditUserAdmin.setText("nim");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("nama");

        kelasMhsAdmin.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        kelasMhsAdmin.setText("kelas");

        statusMhsAdmin.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        statusMhsAdmin.setText("status");

        editNimAdmin.setEditable(false);

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton4.setText("update");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(idEditUserAdmin)
                            .addComponent(jLabel6)
                            .addComponent(kelasMhsAdmin)
                            .addComponent(statusMhsAdmin))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(editKelasAdmin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                                .addComponent(editNimAdmin, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(editStatusAdmin))
                            .addComponent(editNamaAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idEditUserAdmin)
                    .addComponent(editNimAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(editNamaAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kelasMhsAdmin)
                    .addComponent(editKelasAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMhsAdmin)
                    .addComponent(editStatusAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(jButton4)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        editMahasiswa.getContentPane().add(jPanel29);

        untuk1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        untuk1.setText("untuk");

        isiPesan1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        isiPesan1.setText("pesan");

        mailContent1.setColumns(20);
        mailContent1.setRows(5);
        jScrollPane11.setViewportView(mailContent1);

        cancelMail1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cancelMail1.setText("batal");
        cancelMail1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelMail1ActionPerformed(evt);
            }
        });

        sendMail1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sendMail1.setText("kirim");
        sendMail1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMail1ActionPerformed(evt);
            }
        });

        daftarBinaanDosen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout newMail1Layout = new javax.swing.GroupLayout(newMail1.getContentPane());
        newMail1.getContentPane().setLayout(newMail1Layout);
        newMail1Layout.setHorizontalGroup(
            newMail1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newMail1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(newMail1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newMail1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(sendMail1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelMail1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(newMail1Layout.createSequentialGroup()
                        .addGroup(newMail1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(isiPesan1)
                            .addGroup(newMail1Layout.createSequentialGroup()
                                .addComponent(untuk1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(daftarBinaanDosen, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        newMail1Layout.setVerticalGroup(
            newMail1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newMail1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(newMail1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(untuk1)
                    .addComponent(daftarBinaanDosen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(isiPesan1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(newMail1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelMail1)
                    .addComponent(sendMail1))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        induk.setLayout(new java.awt.CardLayout());

        jPanel2.setBackground(new java.awt.Color(51, 51, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("masuk");

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/login.png"))); // NOI18N
        jButton1.setText("masuk");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/3.png"))); // NOI18N
        jButton2.setText("batal");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        userMasuk.setText("36");

        used.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        used.setText("nim");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("password");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        passwordMasuk.setText("36");

        peringatanMasuk.setForeground(new java.awt.Color(204, 0, 0));
        peringatanMasuk.setText("Akses anda ditolak");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(157, 157, 157)
                .addComponent(peringatanMasuk)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(172, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(32, 32, 32))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(used)
                                .addGap(62, 62, 62)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(passwordMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(userMasuk, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(176, 176, 176))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(117, 117, 117))))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(153, 153, 153)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(19, 19, 19)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel1))
                    .addContainerGap(241, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(peringatanMasuk)
                .addGap(5, 5, 5)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(used)
                    .addComponent(userMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(24, 24, 24)
                    .addComponent(jLabel1)
                    .addGap(122, 122, 122)
                    .addComponent(jButton1)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        ikonIT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/itt.png"))); // NOI18N

        ikonIF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/IF.jpg"))); // NOI18N

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/appt.png"))); // NOI18N

        jLabel18.setText("diproduksi oleh :");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel19.setText("P.T INOVASI TEKNIKA");

        jLabel20.setText("2013");

        javax.swing.GroupLayout masukLayout = new javax.swing.GroupLayout(masuk);
        masuk.setLayout(masukLayout);
        masukLayout.setHorizontalGroup(
            masukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(masukLayout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addGroup(masukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ikonIT, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, masukLayout.createSequentialGroup()
                        .addComponent(ikonIF)
                        .addGap(18, 18, 18)))
                .addGroup(masukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(masukLayout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(masukLayout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(392, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, masukLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(masukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addGroup(masukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, masukLayout.createSequentialGroup()
                            .addComponent(jLabel19)
                            .addGap(29, 29, 29))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, masukLayout.createSequentialGroup()
                            .addComponent(jLabel20)
                            .addGap(117, 117, 117)))))
        );
        masukLayout.setVerticalGroup(
            masukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(masukLayout.createSequentialGroup()
                .addGroup(masukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(masukLayout.createSequentialGroup()
                        .addComponent(ikonIT)
                        .addGap(64, 64, 64)
                        .addComponent(ikonIF))
                    .addGroup(masukLayout.createSequentialGroup()
                        .addGroup(masukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, masukLayout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(jLabel16))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, masukLayout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(54, 54, 54)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel20)
                .addGap(8, 8, 8))
        );

        induk.add(masuk, "masuk");

        navigasi.setBackground(new java.awt.Color(51, 51, 255));

        jButton6.setBackground(new java.awt.Color(51, 51, 255));
        jButton6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/task_completed.png"))); // NOI18N
        jButton6.setText("data akademik");
        jButton6.setBorderPainted(false);
        jButton6.setName(""); // NOI18N
        jButton6.setOpaque(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(51, 51, 255));
        jButton7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/project-plan.png"))); // NOI18N
        jButton7.setText("proyek");
        jButton7.setBorderPainted(false);
        jButton7.setName(""); // NOI18N
        jButton7.setOpaque(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(51, 51, 255));
        jButton8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/1363865588_config-users.png"))); // NOI18N
        jButton8.setText("kelompok");
        jButton8.setBorderPainted(false);
        jButton8.setName(""); // NOI18N
        jButton8.setOpaque(false);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(51, 51, 255));
        jButton9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/load_upload.png"))); // NOI18N
        jButton9.setText("upload");
        jButton9.setBorderPainted(false);
        jButton9.setName(""); // NOI18N
        jButton9.setOpaque(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(51, 51, 255));
        jButton10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/indicator-messages-new.png"))); // NOI18N
        jButton10.setText("pesan");
        jButton10.setBorderPainted(false);
        jButton10.setName(""); // NOI18N
        jButton10.setOpaque(false);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(51, 51, 255));
        jButton11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/keluar.png"))); // NOI18N
        jButton11.setText("keluar");
        jButton11.setBorderPainted(false);
        jButton11.setName(""); // NOI18N
        jButton11.setOpaque(false);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        tgl1.setFont(new java.awt.Font("Trajan Pro", 0, 14)); // NOI18N
        tgl1.setForeground(new java.awt.Color(255, 255, 255));
        tgl1.setText("jLabel3");

        waktu1.setFont(new java.awt.Font("Trajan Pro", 0, 14)); // NOI18N
        waktu1.setForeground(new java.awt.Color(255, 255, 255));
        waktu1.setText("jLabel3");

        jButton25.setBackground(new java.awt.Color(51, 51, 255));
        jButton25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton25.setForeground(new java.awt.Color(255, 255, 255));
        jButton25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/configuration 1.png"))); // NOI18N
        jButton25.setText("setelan");
        jButton25.setBorderPainted(false);
        jButton25.setName(""); // NOI18N
        jButton25.setOpaque(false);
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout navigasiLayout = new javax.swing.GroupLayout(navigasi);
        navigasi.setLayout(navigasiLayout);
        navigasiLayout.setHorizontalGroup(
            navigasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navigasiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(navigasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(navigasiLayout.createSequentialGroup()
                        .addGroup(navigasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tgl1)
                            .addComponent(waktu1))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        navigasiLayout.setVerticalGroup(
            navigasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navigasiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tgl1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(waktu1)
                .addGap(30, 30, 30)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        konten.setBackground(new java.awt.Color(255, 255, 255));
        konten.setLayout(new java.awt.CardLayout());

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel9.setText("DATA AKADEMIK");

        statistikNilaiMhs.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        statistikNilaiMhs.setText("statistik nilai");
        statistikNilaiMhs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statistikNilaiMhsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout nilaiLayout = new javax.swing.GroupLayout(nilai);
        nilai.setLayout(nilaiLayout);
        nilaiLayout.setHorizontalGroup(
            nilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nilaiLayout.createSequentialGroup()
                .addGroup(nilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nilaiLayout.createSequentialGroup()
                        .addGap(422, 422, 422)
                        .addComponent(jLabel9))
                    .addGroup(nilaiLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(statistikNilaiMhs)))
                .addContainerGap(358, Short.MAX_VALUE))
        );
        nilaiLayout.setVerticalGroup(
            nilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nilaiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(statistikNilaiMhs)
                .addContainerGap(538, Short.MAX_VALUE))
        );

        konten.add(nilai, "nilai");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel10.setText("PROYEK");

        javax.swing.GroupLayout projekLayout = new javax.swing.GroupLayout(projek);
        projek.setLayout(projekLayout);
        projekLayout.setHorizontalGroup(
            projekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(projekLayout.createSequentialGroup()
                .addGap(484, 484, 484)
                .addComponent(jLabel10)
                .addContainerGap(455, Short.MAX_VALUE))
        );
        projekLayout.setVerticalGroup(
            projekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(projekLayout.createSequentialGroup()
                .addComponent(jLabel10)
                .addGap(0, 592, Short.MAX_VALUE))
        );

        konten.add(projek, "projek");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel11.setText("KELOMPOK");

        tabelTemanKelompok.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane10.setViewportView(tabelTemanKelompok);

        jLabelan1.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N
        jLabelan1.setText("dosen pembimbing");

        jLabelan.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N
        jLabelan.setText("nama kelompok");

        namaDosenMhs.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N
        namaDosenMhs.setText("jLabel5");

        namaKelompokMhs.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N
        namaKelompokMhs.setText("jLabel8");

        javax.swing.GroupLayout kelompokLayout = new javax.swing.GroupLayout(kelompok);
        kelompok.setLayout(kelompokLayout);
        kelompokLayout.setHorizontalGroup(
            kelompokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kelompokLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(469, 469, 469))
            .addGroup(kelompokLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kelompokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 1052, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(kelompokLayout.createSequentialGroup()
                        .addGroup(kelompokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelan1)
                            .addComponent(jLabelan))
                        .addGap(18, 18, 18)
                        .addGroup(kelompokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(namaKelompokMhs)
                            .addComponent(namaDosenMhs))))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        kelompokLayout.setVerticalGroup(
            kelompokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kelompokLayout.createSequentialGroup()
                .addComponent(jLabel11)
                .addGap(55, 55, 55)
                .addGroup(kelompokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelan1)
                    .addComponent(namaDosenMhs))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(kelompokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelan)
                    .addComponent(namaKelompokMhs))
                .addGap(77, 77, 77)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 324, Short.MAX_VALUE))
        );

        konten.add(kelompok, "kelompok");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel12.setText("UPLOAD");

        javax.swing.GroupLayout uploadLayout = new javax.swing.GroupLayout(upload);
        upload.setLayout(uploadLayout);
        uploadLayout.setHorizontalGroup(
            uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uploadLayout.createSequentialGroup()
                .addGap(483, 483, 483)
                .addComponent(jLabel12)
                .addContainerGap(459, Short.MAX_VALUE))
        );
        uploadLayout.setVerticalGroup(
            uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uploadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addContainerGap(581, Short.MAX_VALUE))
        );

        konten.add(upload, "upload");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel13.setText("PESAN");

        tabMailMhs.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        pesanMasukSiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(pesanMasukSiswa);

        totalInMail.setText("total pesan masuk : ");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(totalInMail)
                        .addGap(0, 891, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(totalInMail)
                .addGap(24, 24, 24))
        );

        tabMailMhs.addTab("masuk", jPanel21);

        pesanKeluarSiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(pesanKeluarSiswa);

        totalOutMail.setText("totalpesan keluar : ");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(totalOutMail)
                .addContainerGap(905, Short.MAX_VALUE))
            .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 998, Short.MAX_VALUE)))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(425, Short.MAX_VALUE)
                .addComponent(totalOutMail)
                .addGap(21, 21, 21))
            .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel22Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(74, Short.MAX_VALUE)))
        );

        tabMailMhs.addTab("keluar", jPanel22);

        pesanBaruMhs.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pesanBaruMhs.setText("baru");
        pesanBaruMhs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pesanBaruMhsActionPerformed(evt);
            }
        });

        refreshPesanMhs.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        refreshPesanMhs.setText("segarkan");
        refreshPesanMhs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshPesanMhsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pesanLayout = new javax.swing.GroupLayout(pesan);
        pesan.setLayout(pesanLayout);
        pesanLayout.setHorizontalGroup(
            pesanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pesanLayout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(pesanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pesanLayout.createSequentialGroup()
                        .addComponent(pesanBaruMhs, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refreshPesanMhs)
                        .addGap(872, 872, 872))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pesanLayout.createSequentialGroup()
                        .addComponent(tabMailMhs, javax.swing.GroupLayout.PREFERRED_SIZE, 1013, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49))))
            .addGroup(pesanLayout.createSequentialGroup()
                .addGap(478, 478, 478)
                .addComponent(jLabel13)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pesanLayout.setVerticalGroup(
            pesanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pesanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(38, 38, 38)
                .addGroup(pesanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pesanBaruMhs, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshPesanMhs, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(tabMailMhs))
        );

        konten.add(pesan, "pesan");

        jLabel41.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel41.setText("SETELAN");

        javax.swing.GroupLayout setelanMhsLayout = new javax.swing.GroupLayout(setelanMhs);
        setelanMhs.setLayout(setelanMhsLayout);
        setelanMhsLayout.setHorizontalGroup(
            setelanMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, setelanMhsLayout.createSequentialGroup()
                .addContainerGap(475, Short.MAX_VALUE)
                .addComponent(jLabel41)
                .addGap(452, 452, 452))
        );
        setelanMhsLayout.setVerticalGroup(
            setelanMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(setelanMhsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel41)
                .addContainerGap(581, Short.MAX_VALUE))
        );

        konten.add(setelanMhs, "setelanMhs");

        javax.swing.GroupLayout menuLayout = new javax.swing.GroupLayout(menu);
        menu.setLayout(menuLayout);
        menuLayout.setHorizontalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuLayout.createSequentialGroup()
                .addComponent(navigasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(konten, javax.swing.GroupLayout.PREFERRED_SIZE, 1094, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        menuLayout.setVerticalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(navigasi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(konten, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        induk.add(menu, "menu");

        navDos.setBackground(new java.awt.Color(51, 51, 255));

        jButton12.setBackground(new java.awt.Color(51, 51, 255));
        jButton12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/project-plan.png"))); // NOI18N
        jButton12.setText("proyek");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setBackground(new java.awt.Color(51, 51, 255));
        jButton13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/tutorials.png"))); // NOI18N
        jButton13.setText("bimbingan");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setBackground(new java.awt.Color(51, 51, 255));
        jButton14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton14.setForeground(new java.awt.Color(255, 255, 255));
        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/indicator-messages-new.png"))); // NOI18N
        jButton14.setText("pesan");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton15.setBackground(new java.awt.Color(51, 51, 255));
        jButton15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton15.setForeground(new java.awt.Color(255, 255, 255));
        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/notes.png"))); // NOI18N
        jButton15.setText("penilaian");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setBackground(new java.awt.Color(51, 51, 255));
        jButton16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton16.setForeground(new java.awt.Color(255, 255, 255));
        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/configuration 1.png"))); // NOI18N
        jButton16.setText("setelan");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton18.setBackground(new java.awt.Color(51, 51, 255));
        jButton18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton18.setForeground(new java.awt.Color(255, 255, 255));
        jButton18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/keluar.png"))); // NOI18N
        jButton18.setText("keluar");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        tgl2.setFont(new java.awt.Font("Trajan Pro", 0, 14)); // NOI18N
        tgl2.setForeground(new java.awt.Color(255, 255, 255));
        tgl2.setText("jLabel3");

        waktu2.setFont(new java.awt.Font("Trajan Pro", 0, 14)); // NOI18N
        waktu2.setForeground(new java.awt.Color(255, 255, 255));
        waktu2.setText("jLabel3");

        javax.swing.GroupLayout navDosLayout = new javax.swing.GroupLayout(navDos);
        navDos.setLayout(navDosLayout);
        navDosLayout.setHorizontalGroup(
            navDosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navDosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(navDosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tgl2)
                    .addComponent(waktu2)
                    .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        navDosLayout.setVerticalGroup(
            navDosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navDosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tgl2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(waktu2)
                .addGap(18, 18, 18)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        kontenDos.setBackground(new java.awt.Color(255, 255, 51));
        kontenDos.setLayout(new java.awt.CardLayout());

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel22.setText("BIMBINGAN");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(590, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addGap(380, 380, 380))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel22)
                .addGap(0, 606, Short.MAX_VALUE))
        );

        kontenDos.add(jPanel3, "bimbingan");

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel23.setText("PESAN");

        jTabbedPane2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        pesanMasukDosen.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pesanMasukDosen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(pesanMasukDosen);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 981, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("inbox", jPanel15);

        pesanKeluarDosen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(pesanKeluarDosen);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 981, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("outbox", jPanel16);

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton5.setText("new");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton28.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton28.setText("refresh");
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel23)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton28))
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1006, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23)
                .addGap(87, 87, 87)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        kontenDos.add(jPanel4, "pesan");

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel24.setText("PENILAIAN");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(521, Short.MAX_VALUE)
                .addComponent(jLabel24)
                .addGap(458, 458, 458))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel24)
                .addContainerGap(575, Short.MAX_VALUE))
        );

        kontenDos.add(jPanel5, "penilaian");

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel25.setText("SETELAN");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(533, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addGap(469, 469, 469))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addContainerGap(595, Short.MAX_VALUE))
        );

        kontenDos.add(jPanel6, "setelan");

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel21.setText("PROYEK");

        jLabel37.setText("mengajukan judul ke kaprodi");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel37)
                .addContainerGap(841, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel37)
                .addContainerGap(488, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("mengajukan", jPanel13);

        jLabel38.setText("melihat progres kelompok binaan");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38)
                .addContainerGap(838, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel38)
                .addContainerGap(487, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("progres binaan", jPanel14);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(455, 455, 455)
                .addComponent(jLabel21)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1009, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        kontenDos.add(jPanel1, "proyekDos");

        javax.swing.GroupLayout dosenLayout = new javax.swing.GroupLayout(dosen);
        dosen.setLayout(dosenLayout);
        dosenLayout.setHorizontalGroup(
            dosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dosenLayout.createSequentialGroup()
                .addComponent(navDos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(kontenDos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        dosenLayout.setVerticalGroup(
            dosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dosenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(navDos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(dosenLayout.createSequentialGroup()
                        .addComponent(kontenDos, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        induk.add(dosen, "dosen");

        navKap.setBackground(new java.awt.Color(51, 51, 255));

        jButton17.setBackground(new java.awt.Color(51, 51, 255));
        jButton17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton17.setForeground(new java.awt.Color(255, 255, 255));
        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/project-plan.png"))); // NOI18N
        jButton17.setText("proyek");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton19.setBackground(new java.awt.Color(51, 51, 255));
        jButton19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton19.setForeground(new java.awt.Color(255, 255, 255));
        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/configuration 1.png"))); // NOI18N
        jButton19.setText("setelan");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton20.setBackground(new java.awt.Color(51, 51, 255));
        jButton20.setForeground(new java.awt.Color(255, 255, 255));
        jButton20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/keluar.png"))); // NOI18N
        jButton20.setText("keluar");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        tgl3.setFont(new java.awt.Font("Trajan Pro", 0, 14)); // NOI18N
        tgl3.setForeground(new java.awt.Color(255, 255, 255));
        tgl3.setText("jLabel3");

        waktu3.setFont(new java.awt.Font("Trajan Pro", 0, 14)); // NOI18N
        waktu3.setForeground(new java.awt.Color(255, 255, 255));
        waktu3.setText("jLabel3");

        javax.swing.GroupLayout navKapLayout = new javax.swing.GroupLayout(navKap);
        navKap.setLayout(navKapLayout);
        navKapLayout.setHorizontalGroup(
            navKapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navKapLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(navKapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(navKapLayout.createSequentialGroup()
                        .addGroup(navKapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tgl3)
                            .addComponent(waktu3))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navKapLayout.createSequentialGroup()
                        .addGroup(navKapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(23, 23, 23))))
        );
        navKapLayout.setVerticalGroup(
            navKapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navKapLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tgl3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(waktu3)
                .addGap(22, 22, 22)
                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 365, Short.MAX_VALUE)
                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        kontenKap.setLayout(new java.awt.CardLayout());

        jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel26.setText("PROYEK");

        jTabbedPane3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1012, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 503, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("menyetujui", jPanel17);

        jTabbedPane4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 987, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 452, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("tingkat I", jPanel19);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 987, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 452, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("tingkat II", jPanel20);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane4)
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane4)
                .addContainerGap())
        );

        jTabbedPane3.addTab("data proyek", jPanel18);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(466, 466, 466)
                        .addComponent(jLabel26))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1017, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        kontenKap.add(jPanel7, "proyekKap");

        jLabel27.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel27.setText("SETELAN");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(542, Short.MAX_VALUE)
                .addComponent(jLabel27)
                .addGap(467, 467, 467))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel27)
                .addGap(0, 574, Short.MAX_VALUE))
        );

        kontenKap.add(jPanel8, "setelanKap");

        javax.swing.GroupLayout kaprodiLayout = new javax.swing.GroupLayout(kaprodi);
        kaprodi.setLayout(kaprodiLayout);
        kaprodiLayout.setHorizontalGroup(
            kaprodiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kaprodiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(navKap, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(kontenKap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        kaprodiLayout.setVerticalGroup(
            kaprodiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kaprodiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kaprodiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(navKap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(kaprodiLayout.createSequentialGroup()
                        .addComponent(kontenKap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 32, Short.MAX_VALUE)))
                .addContainerGap())
        );

        induk.add(kaprodi, "kaprodi");

        navKap1.setBackground(new java.awt.Color(51, 51, 255));

        jButton21.setBackground(new java.awt.Color(51, 51, 255));
        jButton21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton21.setForeground(new java.awt.Color(255, 255, 255));
        jButton21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/project-plan.png"))); // NOI18N
        jButton21.setText("proyek");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jButton22.setBackground(new java.awt.Color(51, 51, 255));
        jButton22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton22.setForeground(new java.awt.Color(255, 255, 255));
        jButton22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/configuration 1.png"))); // NOI18N
        jButton22.setText("setelan");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jButton23.setBackground(new java.awt.Color(51, 51, 255));
        jButton23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton23.setForeground(new java.awt.Color(255, 255, 255));
        jButton23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/keluar.png"))); // NOI18N
        jButton23.setText("keluar");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jButton24.setBackground(new java.awt.Color(51, 51, 255));
        jButton24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton24.setForeground(new java.awt.Color(255, 255, 255));
        jButton24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/notes.png"))); // NOI18N
        jButton24.setText("dokumen");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        tgl4.setFont(new java.awt.Font("Trajan Pro", 0, 14)); // NOI18N
        tgl4.setForeground(new java.awt.Color(255, 255, 255));
        tgl4.setText("jLabel3");

        waktu4.setFont(new java.awt.Font("Trajan Pro", 0, 14)); // NOI18N
        waktu4.setForeground(new java.awt.Color(255, 255, 255));
        waktu4.setText("jLabel3");

        jButton27.setBackground(new java.awt.Color(51, 51, 255));
        jButton27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton27.setForeground(new java.awt.Color(255, 255, 255));
        jButton27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/1363852247_user-group-new.png"))); // NOI18N
        jButton27.setText("tambah pengguna");
        jButton27.setPreferredSize(new java.awt.Dimension(75, 23));
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        daftarUser.setBackground(new java.awt.Color(51, 51, 255));
        daftarUser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        daftarUser.setForeground(new java.awt.Color(255, 255, 255));
        daftarUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/1363865588_config-users.png"))); // NOI18N
        daftarUser.setText("daftar pengguna");
        daftarUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                daftarUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout navKap1Layout = new javax.swing.GroupLayout(navKap1);
        navKap1.setLayout(navKap1Layout);
        navKap1Layout.setHorizontalGroup(
            navKap1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navKap1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(navKap1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(navKap1Layout.createSequentialGroup()
                        .addGroup(navKap1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addGroup(navKap1Layout.createSequentialGroup()
                        .addGroup(navKap1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(navKap1Layout.createSequentialGroup()
                                .addGroup(navKap1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tgl4)
                                    .addComponent(waktu4))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(daftarUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        navKap1Layout.setVerticalGroup(
            navKap1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navKap1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tgl4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(waktu4)
                .addGap(18, 18, 18)
                .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(daftarUser, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        kontenAd.setBackground(new java.awt.Color(153, 0, 0));
        kontenAd.setLayout(new java.awt.CardLayout());

        jLabel29.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel29.setText("DOKUMEN");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(457, 457, 457)
                .addComponent(jLabel29)
                .addContainerGap(517, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel29)
                .addGap(0, 606, Short.MAX_VALUE))
        );

        kontenAd.add(jPanel10, "dokumenAd");

        jLabel30.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel30.setText("SETELAN");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(463, 463, 463)
                .addComponent(jLabel30)
                .addContainerGap(529, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30)
                .addContainerGap(595, Short.MAX_VALUE))
        );

        kontenAd.add(jPanel11, "setelanAd");

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel28.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel28.setText("PROYEK");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(443, 443, 443)
                .addComponent(jLabel28)
                .addContainerGap(556, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addContainerGap(595, Short.MAX_VALUE))
        );

        kontenAd.add(jPanel9, "proyekAd");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setText("DATA BARU");

        namaDaftar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        namaDaftar.setText("nama");

        idDaftar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        idDaftar.setText("nim");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("password");

        daftarOlehAdmin.setText("daftar");
        daftarOlehAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                daftarOlehAdminActionPerformed(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel39.setText("status");

        statusDaftar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        statusDaftar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        kelasBaru.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        kelasBaru.setText("kelas");

        kelasDaftarBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kelasDaftarBaruActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGap(517, 517, 517)
                        .addComponent(jLabel2))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                                .addGap(454, 454, 454)
                                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(kelasBaru)
                                    .addComponent(jLabel7))
                                .addGap(18, 18, 18))
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addGap(474, 474, 474)
                                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel39)
                                    .addComponent(namaDaftar)
                                    .addGroup(jPanel24Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(idDaftar)))
                                .addGap(26, 26, 26)))
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(statusDaftar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(idDaftarBaru, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                                .addComponent(passwordDfatarBaru, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(kelasDaftarBaru, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                                .addComponent(namaDaftarBaru, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                                .addComponent(daftarOlehAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(434, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(97, 97, 97)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(statusDaftar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(namaDaftar)
                    .addComponent(namaDaftarBaru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idDaftar)
                    .addComponent(idDaftarBaru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(passwordDfatarBaru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kelasDaftarBaru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kelasBaru))
                .addGap(18, 18, 18)
                .addComponent(daftarOlehAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(249, Short.MAX_VALUE))
        );

        kontenAd.add(jPanel24, "daftarAd");

        jPanel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTabbedPane5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tabelSiswaAdmin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tabelSiswaAdmin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(tabelSiswaAdmin);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1057, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane5.addTab("mahasiswa", jPanel26);

        tabelDosenAdmin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tabelDosenAdmin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane8.setViewportView(tabelDosenAdmin);

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 1057, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane5.addTab("dosen pembimbing", jPanel27);

        tabelAdminAdmin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tabelAdminAdmin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane9.setViewportView(tabelAdminAdmin);

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 1057, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane5.addTab("admin", jPanel28);

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane5)
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane5, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        kontenAd.add(jPanel25, "listAd");

        javax.swing.GroupLayout adminLayout = new javax.swing.GroupLayout(admin);
        admin.setLayout(adminLayout);
        adminLayout.setHorizontalGroup(
            adminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(adminLayout.createSequentialGroup()
                .addComponent(navKap1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1152, Short.MAX_VALUE))
            .addGroup(adminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adminLayout.createSequentialGroup()
                    .addContainerGap(283, Short.MAX_VALUE)
                    .addComponent(kontenAd, javax.swing.GroupLayout.PREFERRED_SIZE, 1102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        adminLayout.setVerticalGroup(
            adminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adminLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(navKap1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(adminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(adminLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(kontenAd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        induk.add(admin, "admin");

        getContentPane().add(induk);

        help.setText("about");

        jMenuItem1.setText("IT Telkom");
        help.add(jMenuItem1);

        jMenuItem2.setText("Fakultas Informatika");
        help.add(jMenuItem2);

        jMenuItem3.setText("P.T Inovasi Teknika");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        help.add(jMenuItem3);

        jMenuBar2.add(help);

        setJMenuBar(jMenuBar2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        CardLayout cad = (CardLayout) konten.getLayout();
        cad.show(konten, "nilai");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        CardLayout cad = (CardLayout) konten.getLayout();
        cad.show(konten, "projek");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        tabelTemanKelompok.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                row = tabelTemanKelompok.getSelectedRow();
                if (row != -1) {
                }
            }
        });
        Kelompok k = new Kelompok();
        try {
            k = cm.getDataKelompok(nim);
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
        Dosen d = new Dosen();
        try {
            d = cm.getDataDepe(nim);
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
        idKelompok = k.getIdKelompok();
        namaKelompokMhs.setText(k.getNamaKelompok());
        namaDosenMhs.setText(d.getNama());
        CardLayout cad = (CardLayout) konten.getLayout();
        cad.show(konten, "kelompok");
        try {
            // TODO add your handling code here:
            recordTemanKelompok = cm.getKelompok(idKelompok);
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
        isiTabelTemanKelompok();

    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        CardLayout cad = (CardLayout) konten.getLayout();
        cad.show(konten, "upload");
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        pesanKeluarSiswa.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                row = pesanKeluarSiswa.getSelectedRow();
                if (row != -1) {
                    sinyalHapus = 1;
                    jLabel15.setText("ke    : ");
                    jLabel43.setText("Dosen Pembimbing");
                    Pesan pesan = recordKeluar.get(row);
                    jLabel44.setText(pesan.getTgl());
                    jLabel45.setText(pesan.getJam());
                    isiBukaPesan.setText(pesan.getIsi());
                    tempPengirim = onUsed;
                    tempPenerima = pesan.getIdOrang();
                    bacaPsnMasukMhs.show();
                }
            }
        });
        pesanMasukSiswa.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                row = pesanMasukSiswa.getSelectedRow();
                if (row != -1) {
                    sinyalHapus = 0;
                    Pesan pesan = recordMasuk.get(row);
                    jLabel15.setText("dari  : ");
                    jLabel43.setText("Dosen Pembimbing");
                    jLabel44.setText(pesan.getTgl());
                    jLabel45.setText(pesan.getJam());
                    isiBukaPesan.setText(pesan.getIsi());
                    tempPengirim = pesan.getIdOrang();//untuk dosen
                    tempPenerima = onUsed;
                    bacaPsnMasukMhs.show();


                }
            }
        });
        try {
            // TODO add your handling code here:
            recordMasuk = prosespesan.getAllMasuk(onUsed, "siswa");
            recordKeluar = prosespesan.getAllKeluar(onUsed, "siswa");
            isiTabelMasuk("siswa");
            isiTabelKeluar("siswa");
            CardLayout cad = (CardLayout) konten.getLayout();
            cad.show(konten, "pesan");
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        kosongAwal();

        this.keluar();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        CardLayout cad = (CardLayout) kontenDos.getLayout();
        cad.show(kontenDos, "proyekDos");
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        CardLayout cad = (CardLayout) kontenDos.getLayout();
        cad.show(kontenDos, "bimbingan");
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        List<Mahasiswa> lm = new ArrayList<>();
        List<String> ls = new ArrayList<>();
        int i = 0;
        try {
            lm = cd.getAllBinaan(nip);
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
        daftarBinaanDosen.removeAllItems();
        for (Mahasiswa mhs : lm) {
            ls.add((mhs.getNim() + ". " + mhs.getNama()));
        }
        for (String s : ls) {
            daftarBinaanDosen.addItem(s);
            System.out.println("" + s);
        }

        pesanMasukDosen.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                row = pesanMasukDosen.getSelectedRow();
                if (row != -1) {
                    sinyalHapus = 0;
                    jLabel15.setText("dari    : ");
                    jLabel43.setText("siswa");
                    Pesan pesan = recordMasuk.get(row);
                    jLabel44.setText(pesan.getTgl());
                    jLabel45.setText(pesan.getJam());
                    isiBukaPesan.setText(pesan.getIsi());
                    tempPengirim = onUsed;
                    tempPenerima = pesan.getIdOrang();
                    bacaPsnMasukMhs.show();
                }
            }
        });

        pesanKeluarDosen.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                row = pesanKeluarDosen.getSelectedRow();
                if (row != -1) {
//                    isiText();
                    sinyalHapus = 1;
                    jLabel15.setText("ke    : ");
                    jLabel43.setText("siswa");
                    Pesan pesan = recordKeluar.get(row);
                    jLabel44.setText(pesan.getTgl());
                    jLabel45.setText(pesan.getJam());
                    isiBukaPesan.setText(pesan.getIsi());
                    tempPengirim = onUsed;
                    tempPenerima = pesan.getIdOrang();
                    bacaPsnMasukMhs.show();
                }
            }
        });
        try {
            // TODO add your handling code here:
            this.loadPesan("dosen");
            CardLayout cad = (CardLayout) konten.getLayout();
            cad.show(konten, "pesan");
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
        CardLayout cad = (CardLayout) kontenDos.getLayout();
        cad.show(kontenDos, "pesan");
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        CardLayout cad = (CardLayout) kontenDos.getLayout();
        cad.show(kontenDos, "penilaian");
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        CardLayout cad = (CardLayout) kontenDos.getLayout();
        cad.show(kontenDos, "setelan");
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        kosongAwal();

        this.keluar();
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
        kosongAwal();

        this.keluar();
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        CardLayout cad = (CardLayout) kontenKap.getLayout();
        cad.show(kontenKap, "proyekKap");
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
        CardLayout cad = (CardLayout) kontenKap.getLayout();
        cad.show(kontenKap, "setelanKap");
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
        CardLayout cad = (CardLayout) kontenAd.getLayout();
        cad.show(kontenAd, "proyekAd");
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // TODO add your handling code here:
        CardLayout cad = (CardLayout) kontenAd.getLayout();
        cad.show(kontenAd, "setelanAd");
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        // TODO add your handling code here:
        kosongAwal();
        this.keluar();
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        // TODO add your handling code here:
        CardLayout cad = (CardLayout) kontenAd.getLayout();
        cad.show(kontenAd, "dokumenAd");
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:        
        about.setSize(400, 300);
        about.setLocation(400, 200);
        about.show();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:
        CardLayout cad = (CardLayout) konten.getLayout();
        cad.show(konten, "setelanMhs");
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        nim = userMasuk.getText();
        nip = userMasuk.getText();
        if (userMasuk.getText().length() != 0 && passwordMasuk.getText().length() != 0) {
            if (posmas.periksaMasuk(userMasuk.getText(), passwordMasuk.getText(), sinyalemen) == true) {
                peringatanMasuk.hide();
                CardLayout cad = (CardLayout) induk.getLayout();
                if (sinyalemen == 0) {
                    cad.show(induk, "menu");
                }
                if (sinyalemen == 1) {
                    cad.show(induk, "kaprodi");
                }
                if (sinyalemen == 2) {
                    cad.show(induk, "dosen");
                }
                if (sinyalemen == 3) {
                    cad.show(induk, "admin");
                }
                onUsed = userMasuk.getText();
            } else {
                peringatanMasuk.show();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void refreshPesanMhsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshPesanMhsActionPerformed
        try {
            // TODO add your handling code here:
            this.loadPesan("siswa");
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_refreshPesanMhsActionPerformed

    private void pesanBaruMhsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pesanBaruMhsActionPerformed
        // TODO add your handling code here:
        newMail.show();
    }//GEN-LAST:event_pesanBaruMhsActionPerformed

    private void sendMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMailActionPerformed
        // TODO add your handling code here:
        Dosen d = new Dosen();
        try {
            d = cm.getDataDepe(nim);
            this.loadPesan("siswa");
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (mailContent.getText().length() != 0) {
            Pesan pesan = new Pesan();
            pesan.setIsi(mailContent.getText());
            String dosenPenerima = d.getNip();
            try {
                pesan.setTgl(this.showDateNow());
                pesan.setJam(this.showTimeNow());
                propes.siswaKirimDosen(onUsed, dosenPenerima, pesan);
                mailContent.setText("");
                this.loadPesan("siswa");
                newMail.hide();
                newMail.dispose();
            } catch (SQLException ex) {
                Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(mailContent, "isi pesan tidak boleh kosong");
        }
    }//GEN-LAST:event_sendMailActionPerformed

    private void cancelMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelMailActionPerformed
        // TODO add your handling code here:
        newMail.hide();
        newMail.dispose();
    }//GEN-LAST:event_cancelMailActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        bacaPsnMasukMhs.hide();
        bacaPsnMasukMhs.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
                "Yakin?", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
            if (sinyalHapus == 0) {
                Pesan pesan = new Pesan();
                pesan.setIdOrang(tempPengirim);
                pesan.setIsi(isiBukaPesan.getText());
                pesan.setTgl(jLabel44.getText());
                pesan.setJam(jLabel45.getText());
                pesan.setPenerima(tempPenerima);
                try {
                    propes.deletePsnMhsMsuk(pesan);
                    propes.komit();
                } catch (SQLException ex) {
                    Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (sinyalHapus == 1) {
                Pesan pesan = new Pesan();
                pesan.setIdOrang(tempPenerima);
                pesan.setIsi(isiBukaPesan.getText());
                pesan.setTgl(jLabel44.getText());
                pesan.setJam(jLabel45.getText());
                pesan.setPenerima(tempPenerima);
                pesan.setPengirim(onUsed);
                try {
                    propes.deletePsnMhsKlr(pesan);
                    propes.komit();
                } catch (SQLException ex) {
                    Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            bacaPsnMasukMhs.hide();
            try {
                this.loadPesan("siswa");
            } catch (SQLException ex) {
                Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton26ActionPerformed

    private void statistikNilaiMhsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statistikNilaiMhsActionPerformed
        // TODO add your handling code here:
        PieChart piechart = new PieChart();
        piechart.pack();
        piechart.show();
    }//GEN-LAST:event_statistikNilaiMhsActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // TODO add your handling code here:
        CardLayout cad = (CardLayout) kontenAd.getLayout();
        cad.show(kontenAd, "daftarAd");

    }//GEN-LAST:event_jButton27ActionPerformed

    private void daftarOlehAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_daftarOlehAdminActionPerformed
        // TODO add your handling code here:
        if (namaDaftarBaru.getText().length() != 0 && idDaftarBaru.getText().length() != 0
                && passwordDfatarBaru.getText().length() != 0) {
            if (sinyalemenDaftar == 0) {
                try {
                    Mahasiswa m = new Mahasiswa();
                    m.setNim(idDaftarBaru.getText());
                    m.setNama(namaDaftarBaru.getText());
                    m.setPassword(passwordDfatarBaru.getText());
                    m.setKelas(kelasDaftarBaru.getText());
                    cm.insert(m);
                } catch (SQLException ex) {
                    Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sinyalemenDaftar == 1) {
                Dosen d = new Dosen();
                d.setNip(idDaftarBaru.getText());
                d.setNama(namaDaftarBaru.getText());
                d.setPassword(passwordDfatarBaru.getText());
                try {
                    kb.insertKaprodi(d);
                } catch (SQLException ex) {
                    Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sinyalemenDaftar == 2) {
                Dosen d = new Dosen();
                d.setNip(idDaftarBaru.getText());
                d.setNama(namaDaftarBaru.getText());
                d.setPassword(passwordDfatarBaru.getText());
                try {
                    cd.insert(d);
                } catch (SQLException ex) {
                    Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (sinyalemenDaftar == 3) {
                Admin d = new Admin();
                d.setNip(idDaftarBaru.getText());
                d.setNama(namaDaftarBaru.getText());
                d.setPassword(passwordDfatarBaru.getText());
                try {
                    ca.insert(d);
                } catch (SQLException ex) {
                    Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        idDaftarBaru.setText("");
        namaDaftarBaru.setText("");
        passwordDfatarBaru.setText("");
        kelasDaftarBaru.setText("");
    }//GEN-LAST:event_daftarOlehAdminActionPerformed

    private void kelasDaftarBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kelasDaftarBaruActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kelasDaftarBaruActionPerformed

    private void daftarUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_daftarUserActionPerformed
        tabelSiswaAdmin.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                row = tabelSiswaAdmin.getSelectedRow();
                if (row != -1) {
                    kelasMhsAdmin.setText("kelas");
                    statusMhsAdmin.setText("status");
                    editKelasAdmin.show();
                    editStatusAdmin.show();
                    Mahasiswa m = recordSiswa.get(row);
                    editNimAdmin.setText(m.getNim());
                    editNamaAdmin.setText(m.getNama());
                    editKelasAdmin.setText(m.getKelas());
                    editStatusAdmin.setText(m.getStatus());

                    editMahasiswa.show();
                }
            }
        });
        tabelDosenAdmin.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                row = tabelDosenAdmin.getSelectedRow();
                if (row != -1) {
                    idEditUserAdmin.setText("nip");
                    kelasMhsAdmin.setText("");
                    statusMhsAdmin.setText("");
                    editKelasAdmin.hide();
                    editStatusAdmin.hide();
                    Dosen d = recordDosen.get(row);
                    editNimAdmin.setText(d.getNip());
                    editNamaAdmin.setText(d.getNama());

                    editMahasiswa.show();
                }
            }
        });
        try {
            // TODO add your handling code here:
            recordSiswa = cm.getAll();
            recordDosen = cd.getAll();
            isiTabeSiswa();
            isiTabelDosen();
            CardLayout cad = (CardLayout) kontenAd.getLayout();
            cad.show(kontenAd, "listAd");
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_daftarUserActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        Mahasiswa m = new Mahasiswa();
        m.setNim(editNimAdmin.getText());
        m.setNama(editNamaAdmin.getText());
        m.setKelas(editKelasAdmin.getText());
        m.setStatus(editStatusAdmin.getText());
        try {
            cm.update(m);
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
        editMahasiswa.hide();
        editMahasiswa.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void cancelMail1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelMail1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cancelMail1ActionPerformed

    private void sendMail1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMail1ActionPerformed
        // TODO add your handling code here:
        Mahasiswa m = new Mahasiswa();

        try {
            m = cd.getDataDepe(nim);
            loadPesan("siswa");
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (mailContent1.getText().length() != 0) {
            Pesan pesan = new Pesan();
            pesan.setIsi(mailContent1.getText());
            String temp = daftarBinaanDosen.getSelectedItem().toString();
            String temp2 = "";
            for (int i = 0; i < temp.length(); i++) {
                if (temp.charAt(i) != '.') {
                    temp2 += temp.charAt(i);
                }
                if (temp.charAt(i) == '.') {
                    break;
                }
            }
            System.out.println("YOOOO : " + temp2);
//            System.out.println("" + daftarBinaanDosen.getSelectedItem().toString());
            String nimPenerima = temp2;
            try {
                pesan.setTgl(this.showDateNow());
                pesan.setJam(this.showTimeNow());
                propes.dosenKirimSiswa(onUsed, nimPenerima, pesan);
                mailContent1.setText("");
                newMail1.hide();
                newMail1.dispose();
                this.loadPesan("dosen");
            } catch (SQLException ex) {
                Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(mailContent, "isi pesan tidak boleh kosong");
        }
    }//GEN-LAST:event_sendMail1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        newMail1.setSize(500, 500);
        newMail1.show();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        try {
            // TODO add your handling code here:
            this.loadPesan("dosen");
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton28ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormInduk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormInduk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormInduk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormInduk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormInduk().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame about;
    private javax.swing.JPanel admin;
    private javax.swing.JFrame bacaPsnMasukMhs;
    private javax.swing.JButton cancelMail;
    private javax.swing.JButton cancelMail1;
    private javax.swing.JComboBox daftarBinaanDosen;
    private javax.swing.JButton daftarOlehAdmin;
    private javax.swing.JButton daftarUser;
    private javax.swing.JPanel dosen;
    private javax.swing.JTextField editKelasAdmin;
    private javax.swing.JFrame editMahasiswa;
    private javax.swing.JTextField editNamaAdmin;
    private javax.swing.JTextField editNimAdmin;
    private javax.swing.JTextField editStatusAdmin;
    private javax.swing.JMenu help;
    private javax.swing.JLabel idDaftar;
    private javax.swing.JTextField idDaftarBaru;
    private javax.swing.JLabel idEditUserAdmin;
    private javax.swing.JLabel ikonIF;
    private javax.swing.JLabel ikonIT;
    private javax.swing.JPanel induk;
    private javax.swing.JTextArea isiBukaPesan;
    private javax.swing.JLabel isiPesan;
    private javax.swing.JLabel isiPesan1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelan;
    private javax.swing.JLabel jLabelan1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JPanel kaprodi;
    private javax.swing.JLabel kelasBaru;
    private javax.swing.JTextField kelasDaftarBaru;
    private javax.swing.JLabel kelasMhsAdmin;
    private javax.swing.JPanel kelompok;
    private javax.swing.JPanel konten;
    private javax.swing.JPanel kontenAd;
    private javax.swing.JPanel kontenDos;
    private javax.swing.JPanel kontenKap;
    private javax.swing.JTextArea mailContent;
    private javax.swing.JTextArea mailContent1;
    private javax.swing.JPanel masuk;
    private javax.swing.JPanel menu;
    private javax.swing.JLabel namaDaftar;
    private javax.swing.JTextField namaDaftarBaru;
    private javax.swing.JLabel namaDosenMhs;
    private javax.swing.JLabel namaDosenSurat;
    private javax.swing.JLabel namaKelompokMhs;
    private javax.swing.JPanel navDos;
    private javax.swing.JPanel navKap;
    private javax.swing.JPanel navKap1;
    private javax.swing.JPanel navigasi;
    private javax.swing.JFrame newMail;
    private javax.swing.JFrame newMail1;
    public javax.swing.JPanel nilai;
    private javax.swing.JTextField passwordDfatarBaru;
    private javax.swing.JPasswordField passwordMasuk;
    private javax.swing.JLabel peringatanMasuk;
    private javax.swing.JPanel pesan;
    private javax.swing.JToggleButton pesanBaruMhs;
    private javax.swing.JTable pesanKeluarDosen;
    private javax.swing.JTable pesanKeluarSiswa;
    private javax.swing.JTable pesanMasukDosen;
    private javax.swing.JTable pesanMasukSiswa;
    private javax.swing.JPanel projek;
    private javax.swing.JToggleButton refreshPesanMhs;
    private javax.swing.JButton sendMail;
    private javax.swing.JButton sendMail1;
    private javax.swing.JPanel setelanMhs;
    private javax.swing.JButton statistikNilaiMhs;
    private javax.swing.JComboBox statusDaftar;
    private javax.swing.JLabel statusMhsAdmin;
    private javax.swing.JTabbedPane tabMailMhs;
    private javax.swing.JTable tabelAdminAdmin;
    private javax.swing.JTable tabelDosenAdmin;
    private javax.swing.JTable tabelSiswaAdmin;
    private javax.swing.JTable tabelTemanKelompok;
    private javax.swing.JLabel tgl1;
    private javax.swing.JLabel tgl2;
    private javax.swing.JLabel tgl3;
    private javax.swing.JLabel tgl4;
    private javax.swing.JLabel totalInMail;
    private javax.swing.JLabel totalOutMail;
    private javax.swing.JLabel untuk;
    private javax.swing.JLabel untuk1;
    private javax.swing.JPanel upload;
    private javax.swing.JLabel used;
    private javax.swing.JTextField userMasuk;
    private javax.swing.JLabel waktu1;
    private javax.swing.JLabel waktu2;
    private javax.swing.JLabel waktu3;
    private javax.swing.JLabel waktu4;
    // End of variables declaration//GEN-END:variables
}
