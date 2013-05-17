/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package penpot.View;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import penpot.Model.Admin;
import penpot.Model.Dosen;
import penpot.Model.Kelompok;
import penpot.Model.Mahasiswa;
import penpot.Model.Pesan;
import penpot.Model.Proyek;
import penpot.Controller.ControllerAdmin;
import penpot.Controller.ControllerDosen;
import penpot.Controller.ControllerMahasiswa;
import penpot.Controller.ControllerPekanKlmpk;
import penpot.Controller.ControllerPekanMhs;
import penpot.Controller.ControllerProyek;
import penpot.Controller.KelasController;
import penpot.Controller.kelolaBaru;
import penpot.Controller.prosesMasuk;
import penpot.Controller.prosesPesan;
import penpot.Model.Kelas;
import penpot.Model.pekanKlmpk;
import penpot.Model.pekanMhs;

/**
 *
 * @author haluan
 */
public class FormInduk extends javax.swing.JFrame {

    /**
     * Creates new form FormInduk
     */
    //Instansiasi Model
    private Pesan pesaN = new Pesan();
    private Mahasiswa mhs = new Mahasiswa();
    private Dosen dosenisme = new Dosen();
    private Admin adminisme = new Admin();
    Proyek proy = new Proyek();
    private static Proyek pro = new Proyek();
    //Record u/ tabel
    private List<Pesan> recordMasuk = new ArrayList<>();
    private List<Pesan> recordKeluar = new ArrayList<>();
    private List<Mahasiswa> recordSiswa = new ArrayList<>();
    private List<Dosen> recordDosen = new ArrayList<>();
    private List<Admin> recordadmin = new ArrayList<>();
    private List<Mahasiswa> recordTemanKelompok = new ArrayList<>();
    private List<pekanMhs> recordPekanMhs = new ArrayList<>();
    private List<pekanKlmpk> recordPekanKlpk = new ArrayList<>();
    private List<Proyek> recordProyekXX = new ArrayList<>();
    private List<Proyek> recordProyekC = new ArrayList<>();
    private List<Proyek> recordProyeks = new ArrayList<>();
    private List<Proyek> recordProyekI = new ArrayList<>();
    private List<Proyek> recordProyekII = new ArrayList<>();
    //Instansiasi Controller
    private ControllerMahasiswa cm = new ControllerMahasiswa();
    private ControllerDosen cd = new ControllerDosen();
    private ControllerAdmin ca = new ControllerAdmin();
    private prosesPesan prosespesan = new prosesPesan();
    private ControllerProyek cp = new ControllerProyek();
    private kelolaBaru kb = new kelolaBaru();
    private prosesMasuk posmas = new prosesMasuk();
    private ControllerPekanMhs cpm = new ControllerPekanMhs();
    private ControllerPekanKlmpk cpk = new ControllerPekanKlmpk();
    //variabel tambahan
    private int row;
    private static int seen;
    private static String onUsed = "", tempPengirim, tempPenerima;
    private static int sinyalHapus;
    private static String nim, nip;
    private String idProyek;
    private Thread t1;
    private int sinyalemen;
    private int sinyalemenDaftar;
    private boolean dosenExist = false;

    public FormInduk() {
        initComponents();
        this.peringatan1();
        this.setTitle("APPT");
        this.setSize(1350, 700);
        newMail.setSize(600, 400);
        bacaPsnMasukMhs.setSize(600, 400);
        editMahasiswa.setSize(400, 300);
        t1 = new indor();
        //tabelisasi
        jenisLogin.addActionListener(new combox());
        jenisLogin.setModel(new DefaultComboBoxModel(new String[]{"mahasiswa", "kaprodi", "dosen", "admin"}));

        //pekanMhs.addActionListener(new comboxNilMhs());
        pekanMhs.setModel(new DefaultComboBoxModel(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
        pekanMhs1.setModel(new DefaultComboBoxModel(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
        statusDaftar.addActionListener(new comboxDaftar());
        statusDaftar.setModel(new DefaultComboBoxModel(new String[]{"mahasiswa", "kaprodi", "dosen", "admin"}));
        jenisKelamin.setModel(new DefaultComboBoxModel(new String[]{"L", "P"}));
        tingkatMhs.setModel(new DefaultComboBoxModel(new String[]{"I", "II"}));
        t1.start();
    }

    void isiTabelMasuk(String status) throws SQLException {
        Object data[][] = new Object[recordMasuk.size()][4];
        int x = 0;
        for (Pesan pes : recordMasuk) {
            data[x][0] = pes.getTgl();
            data[x][3] = pes.getIsi();
            data[x][2] = pes.getPengirim();
            data[x][1] = pes.getJam();
            x++;
        }

        String judul[] = {"tanggal", "jam", "dari", "isi"};
        if (status.equals("siswa")) {
            pesanMasukSiswa.setModel(new DefaultTableModel(data, judul));
            jScrollPane2.setViewportView(pesanMasukSiswa);
            totalInMail.setText(prosespesan.jumlahMailMasuk(onUsed));
        } else {
            pesanMasukDosen.setModel(new DefaultTableModel(data, judul));
            jScrollPane4.setViewportView(pesanMasukDosen);
            totMailDsn.setText(prosespesan.jumlahMailMasuk(onUsed));
        }

    }

    void isiTabelKeluar(String status) throws SQLException {
        int x = 0;
        Object data[][] = new Object[recordKeluar.size()][4];
        for (Pesan pes : recordKeluar) {
            data[x][0] = pes.getTgl();
            data[x][3] = pes.getIsi();
            data[x][2] = pes.getPenerima();
            data[x][1] = pes.getJam();
            x++;
        }

        String judul[] = {"tanggal", "jam", "ke", "isi"};
        if (status.equals("siswa")) {
            pesanKeluarSiswa.setModel(new DefaultTableModel(data, judul));
            jScrollPane3.setViewportView(pesanKeluarSiswa);
            totalOutMail.setText(prosespesan.jumlahMailKeluar(onUsed));
        } else {
            pesanKeluarDosen.setModel(new DefaultTableModel(data, judul));
            jScrollPane5.setViewportView(pesanKeluarDosen);
            totOutMailDsn.setText(prosespesan.jumlahMailKeluar(onUsed));
        }
    }

    void isiTabelPenilaianKelompok(String nip) {
        try {
            int x = 0;
            recordProyekXX = cp.getAllDOsen(nip);
            Object data[][] = new Object[recordProyekXX.size()][3];
            for (Proyek p : recordProyekXX) {
                data[x][0] = p.getJudul();
                data[x][1] = p.getTingkat();
                data[x][2] = p.getTahunAkademik();
                x++;
            }
            String judul[] = {"Judul", "Tingkat", "Tahun Akademik"};
            penilaianKelompok.setModel(new DefaultTableModel(data, judul));
            jScrollPane30.setViewportView(penilaianKelompok);
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void isiTabeSiswa() {
        int x = 0;
        Object data[][] = new Object[recordSiswa.size()][6];
        for (Mahasiswa m : recordSiswa) {
            data[x][0] = m.getNim();
            data[x][1] = m.getNama();
            data[x][2] = m.getKelas();
            data[x][3] = m.getStatus();
            data[x][5] = m.getJenisKelamin();
            data[x][4] = m.getJabatan();
            x++;
        }
        String judul[] = {"nim", "nama", "kelas", "status", "jabatan", "kelamin"};
        tabelSiswaAdmin.setModel(new DefaultTableModel(data, judul));
        jScrollPane7.setViewportView(tabelSiswaAdmin);
    }

    private boolean CekKonfirmasi(String satu, String dua) {
        boolean temp = false;
        if (satu.length() > 6 && dua.length() > 6) {
            if (satu.equals(dua)) {
                temp = true;
            } else {
                JOptionPane.showMessageDialog(mailContent, "Validasi Gagal");
            }
        } else {
            JOptionPane.showMessageDialog(mailContent, "Panjang Karakter kurang dari 6");
        }
        return temp;
    }

    void isiTabelPekanMhs() {
        int x = 0;
        Object data[][] = new Object[recordPekanMhs.size()][2];
        for (pekanMhs p : recordPekanMhs) {
            data[x][0] = p.getPekan();
            data[x][1] = p.getNilai();
            x++;
        }
        String judul[] = {"pekan", "nilai"};
        tabPekanMhs.setModel(new DefaultTableModel(data, judul));
        jScrollPane14.setViewportView(tabPekanMhs);
    }

    void isiTabelPekanKlpk() {
        int x = 0;

        Object data[][] = new Object[recordPekanKlpk.size()][2];
        for (pekanKlmpk p : recordPekanKlpk) {
            data[x][0] = p.getPekan();
            data[x][1] = p.getNilai();
            x++;
        }
        String judul[] = {"pekan", "nilai kelompok"};
        tabPekanMhs1.setModel(new DefaultTableModel(data, judul));
        jScrollPane31.setViewportView(tabPekanMhs1);
    }

    void isiTabeldataNilaiMhs() {
        int x = 0;
        Object data[][] = new Object[recordPekanMhs.size()][2];
        for (pekanMhs p : recordPekanMhs) {
            data[x][0] = p.getPekan();
            data[x][1] = p.getNilai();
            x++;
        }
        String judul[] = {"pekan", "nilai"};
        dataNilaiMhs.setModel(new DefaultTableModel(data, judul));
        jScrollPane15.setViewportView(dataNilaiMhs);
    }

    void isiTabelProyekDos() {
        int g = 0;
        Object dataC[][] = new Object[recordProyeks.size()][7];
        for (Proyek p : recordProyeks) {
            dataC[g][1] = p.getJudul();
            dataC[g][2] = p.getTingkat();
            dataC[g][3] = p.getTahunAkademik();
            dataC[g][4] = p.getNama();
            dataC[g][5] = p.getStatus();
            dataC[g][0] = p.getIdPro();
            dataC[g][6] = p.getJumlah();
            g++;

        };
        String judul[] = {"id", "judul", "tingkat", "tahun akademik", "dosen pembimbing", "status", "jumlah"};
        lihatProyekAjuan.setModel(new DefaultTableModel(dataC, judul));

    }

    void isiTabelProyekI() {
        int y = 0;
        Object dataI[][] = new Object[recordProyekI.size()][7];
        for (Proyek p : recordProyekI) {
            dataI[y][1] = p.getJudul();
            dataI[y][2] = p.getTingkat();
            dataI[y][3] = p.getTahunAkademik();
            dataI[y][4] = p.getNama();
            dataI[y][5] = p.getStatus();
            dataI[y][0] = p.getIdPro();
            dataI[y][6] = p.getJumlah();
            y++;
        }
        String judul[] = {"id", "judul", "tingkat", "tahun akademik", "dosen pembimbing", "status", "jumlah"};
        tblPt1SetujuKapro.setModel(new DefaultTableModel(dataI, judul));
        tkt1admin.setModel(new DefaultTableModel(dataI, judul));
        jScrollPane27.setViewportView(tkt1admin);
        jScrollPane16.setViewportView(tblPt1SetujuKapro);

    }

    void isiTabelProyekII() {
        int z = 0;
        Object dataII[][] = new Object[recordProyekII.size()][7];
        for (Proyek p : recordProyekII) {
            dataII[z][1] = p.getJudul();
            dataII[z][2] = p.getTingkat();
            dataII[z][3] = p.getTahunAkademik();
            dataII[z][4] = p.getNama();
            dataII[z][5] = p.getStatus();
            dataII[z][0] = p.getIdPro();
            dataII[z][6] = p.getJumlah();
            z++;
        }
        String judul[] = {"id", "judul", "tingkat", "tahun akademik", "dosen pembimbing", "status", "jumlah"};

        tblPt2SetujuKapro.setModel(new DefaultTableModel(dataII, judul));
        jScrollPane17.setViewportView(tblPt2SetujuKapro);
        tkt2admin.setModel(new DefaultTableModel(dataII, judul));
        jScrollPane28.setViewportView(tkt2admin);

    }

    void isiTabelPilihProjMhs(String status) {
        int z = 0, y = 0;
        Object dataI[][] = new Object[recordProyekI.size()][7];
        Object dataII[][] = new Object[recordProyekII.size()][7];
        String judul[] = {"id", "judul", "tingkat", "tahun akademik", "dosen pembimbing", "status", "jumlah"};
        if (status.equals("I")) {

            for (Proyek p : recordProyekI) {
                dataI[y][1] = p.getJudul();
                dataI[y][2] = p.getTingkat();
                dataI[y][3] = p.getTahunAkademik();
                dataI[y][4] = p.getNama();
                dataI[y][5] = p.getStatus();
                dataI[y][0] = p.getIdPro();
                dataI[y][6] = p.getJumlah();
                y++;
            }
            pilihProjMhs.setModel(new DefaultTableModel(dataI, judul));
            jScrollPane18.setViewportView(pilihProjMhs);
        }
        if (status.equals("II")) {

            for (Proyek p : recordProyekII) {
                dataII[z][1] = p.getJudul();
                dataII[z][2] = p.getTingkat();
                dataII[z][3] = p.getTahunAkademik();
                dataII[z][4] = p.getNama();
                dataII[z][5] = p.getStatus();
                dataII[z][0] = p.getIdPro();
                dataII[z][6] = p.getJumlah();
                z++;
            }
            pilihProjMhs.setModel(new DefaultTableModel(dataII, judul));
            jScrollPane18.setViewportView(pilihProjMhs);
        }
    }

    void isiTabelProyek() {
        int x = 0;
        Object data[][] = new Object[recordProyekC.size()][7];
        for (Proyek p : recordProyekC) {
            data[x][1] = p.getJudul();
            data[x][2] = p.getTingkat();
            data[x][3] = p.getTahunAkademik();
            data[x][4] = p.getNama();
            data[x][5] = p.getStatus();
            data[x][0] = p.getIdPro();
            data[x][6] = p.getJumlah();

            x++;
        }
        String judul[] = {"id", "judul", "tingkat", "tahun akademik",
            "dosen pembimbing", "status", "jumlah"};


        tabelSiapSetuju.setModel(new DefaultTableModel(data, judul));
        jScrollPane12.setViewportView(tabelSiapSetuju);
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

    void isiTabelBimbingan() {
        int x = 0;
        Object data[][] = new Object[recordTemanKelompok.size()][6];
        for (Mahasiswa m : recordTemanKelompok) {
            data[x][0] = m.getJudul();
            data[x][1] = m.getNim();
            data[x][2] = m.getNama();
            data[x][3] = m.getKelas();
            data[x][4] = m.getStatus();
            data[x][5] = m.getJabatan();
            x++;
        }
        String judul[] = {"judul", "nim", "nama", "kelas", "status", "jabatan"};
        bimbinganDosen.setModel(new DefaultTableModel(data, judul));
        jScrollPane13.setViewportView(bimbinganDosen);
    }

    void isiTabelDosen() {
        int x = 0;
        Object data[][] = new Object[recordDosen.size()][2];
        for (Dosen d : recordDosen) {
            data[x][0] = d.getNip();
            data[x][1] = d.getNama();
            x++;
        }
        String judul[] = {"nip", "nama"};
        tabelDosenAdmin.setModel(new DefaultTableModel(data, judul));
        jScrollPane8.setViewportView(tabelDosenAdmin);
    }

    void isiTabeladmin() {
        try {
            int x = 0;
            recordadmin = ca.getAll();
            Object data[][] = new Object[recordadmin.size()][2];
            for (Admin d : recordadmin) {
                data[x][0] = d.getNip();
                data[x][1] = d.getNama();
                x++;
            }
            String judul[] = {"nip", "nama"};
            tabelAdminAdmin.setModel(new DefaultTableModel(data, judul));
            jScrollPane9.setViewportView(tabelAdminAdmin);
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadPesan(String status, int temp) throws SQLException {
        recordMasuk = prosespesan.getAllMasuk(onUsed, status, temp);
        recordKeluar = prosespesan.getAllKeluar(onUsed, status, temp);
        isiTabelMasuk(status);
        isiTabelKeluar(status);
    }

    private class comboxNilMhs0 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
            //To change body of generated methods, choose Tools | Templates.
        }
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
        if (jenisLogin.getSelectedIndex() == 0) {
            sinyalemen = 0;
            jLabel48.setText("nim");

        }
        if (jenisLogin.getSelectedIndex() == 1) {
            sinyalemen = 1;
            jLabel48.setText("nip");

        }
        if (jenisLogin.getSelectedIndex() == 2) {
            sinyalemen = 2;
            jLabel48.setText("nip");
        }
        if (jenisLogin.getSelectedIndex() == 3) {
            sinyalemen = 3;
            jLabel48.setText("nip");

        }

    }

    private void sinyalDaftar() {
        if (statusDaftar.getSelectedIndex() == 0) {
            sinyalemenDaftar = 0;//siswa
            idDaftar.setText("nim");
            kelasBaru.setText("kelas");
            tingkatMhs.show();
            jComboBox2.show();
            tingkatMhs.show();
            jLabel80.show();
        }
        if (statusDaftar.getSelectedIndex() == 1) {
            sinyalemenDaftar = 1;//kaprodi
            idDaftar.setText("nip");
            kelasBaru.setText("");
            tingkatMhs.hide();

            jLabel80.hide();
            jComboBox2.hide();
        }
        if (statusDaftar.getSelectedIndex() == 2) {
            sinyalemenDaftar = 2;//dosen
            idDaftar.setText("nip");
            kelasBaru.setText("");
            tingkatMhs.hide();
            jLabel80.hide();
            jComboBox2.hide();
        }
        if (statusDaftar.getSelectedIndex() == 3) {
            sinyalemenDaftar = 3;//admin
            idDaftar.setText("nip");
            kelasBaru.setText("");
            tingkatMhs.hide();
            jLabel80.hide();
            jComboBox2.hide();
        }
    }

    public String showTimeNow() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String hasil = sdf.format(cal.getTime());
        return hasil;
    }

    public String showDateNow(int s) {
        Calendar cal = Calendar.getInstance();
        String hasil = "";
        if (s == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyy");
            hasil = sdf.format(cal.getTime());
        }
        if (s == 1) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
            hasil = sdf.format(cal.getTime());
        }
        return hasil;
    }

    public String showyearNow() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy");
        String hasil = sdf.format(cal.getTime());
        return hasil;
    }

    public void update() {
        if (sinyalemen == 0) {
            tgl1.setText(this.showDateNow(0));
            waktu1.setText(this.showTimeNow() + " WIB");
        } else if (sinyalemen == 2) {
            tgl2.setText(this.showDateNow(0));
            waktu2.setText(this.showTimeNow() + " WIB");
        } else if (sinyalemen == 1) {
            tgl3.setText(this.showDateNow(0));
            waktu3.setText(this.showTimeNow() + " WIB");
        } else {
            tgl4.setText(this.showDateNow(0));
            waktu4.setText(this.showTimeNow() + " WIB");
        }
    }

    void peringatan1() {
        peringatanMasuk.hide();
    }

    void kosongAwal() {
        idMasuk.setText(null);
        passMasuk.setText(null);
    }

    void keluar() {

        CardLayout cad = (CardLayout) induk.getLayout();
        cad.show(induk, "login");
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
        nilaiMhs = new javax.swing.JFrame();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        pekanMhs = new javax.swing.JComboBox();
        nilaiPekanMhs = new javax.swing.JTextField();
        jScrollPane14 = new javax.swing.JScrollPane();
        tabPekanMhs = new javax.swing.JTable();
        inputNilaiMhsPkn = new javax.swing.JButton();
        namaMhsNilai = new javax.swing.JLabel();
        nilaiTotal = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        accProyek = new javax.swing.JFrame();
        jPanel31 = new javax.swing.JPanel();
        accProOk = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        accSetuju = new javax.swing.JButton();
        accTolak = new javax.swing.JButton();
        jScrollPane19 = new javax.swing.JScrollPane();
        pesanAcc = new javax.swing.JTextArea();
        jScrollPane23 = new javax.swing.JScrollPane();
        dscProjKapro = new javax.swing.JTextArea();
        jLabel58 = new javax.swing.JLabel();
        jButton29 = new javax.swing.JButton();
        statusPro = new javax.swing.JFrame();
        jPanel32 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jScrollPane20 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jLabel56 = new javax.swing.JLabel();
        jScrollPane24 = new javax.swing.JScrollPane();
        dscProDos = new javax.swing.JTextArea();
        jLabel59 = new javax.swing.JLabel();
        jButton30 = new javax.swing.JButton();
        pilihPro = new javax.swing.JFrame();
        jPanel33 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jScrollPane25 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        ambilProjek = new javax.swing.JButton();
        kelompokNilai = new javax.swing.JFrame();
        jPanel41 = new javax.swing.JPanel();
        pekanMhs1 = new javax.swing.JComboBox();
        namaMhsNilai1 = new javax.swing.JLabel();
        nilaiPekanMhs1 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        inputNilaiMhsPkn1 = new javax.swing.JButton();
        jScrollPane31 = new javax.swing.JScrollPane();
        tabPekanMhs1 = new javax.swing.JTable();
        nilaiTotal1 = new javax.swing.JLabel();
        induk = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jenisLogin = new javax.swing.JComboBox();
        idMasuk = new javax.swing.JTextField();
        ayoMasuk = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        passMasuk = new javax.swing.JPasswordField();
        peringatanMasuk = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
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
        jScrollPane15 = new javax.swing.JScrollPane();
        dataNilaiMhs = new javax.swing.JTable();
        projek = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        pilihProjMhs = new javax.swing.JTable();
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
        jScrollPane29 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel84 = new javax.swing.JLabel();
        jButton36 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton37 = new javax.swing.JButton();
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
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPasswordField2 = new javax.swing.JPasswordField();
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
        jScrollPane13 = new javax.swing.JScrollPane();
        bimbinganDosen = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        pesanMasukDosen = new javax.swing.JTable();
        totMailDsn = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        pesanKeluarDosen = new javax.swing.JTable();
        totOutMailDsn = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane26 = new javax.swing.JScrollPane();
        nilaiKelompok = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        jPasswordField3 = new javax.swing.JPasswordField();
        jLabel71 = new javax.swing.JLabel();
        jPasswordField4 = new javax.swing.JPasswordField();
        jButton31 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane30 = new javax.swing.JScrollPane();
        penilaianKelompok = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        ajukanJudul = new javax.swing.JTextField();
        ajukanJumlahKlpk = new javax.swing.JTextField();
        ajukanTingkat = new javax.swing.JComboBox();
        ajukanProyek = new javax.swing.JButton();
        batalAjukanProyek = new javax.swing.JButton();
        jScrollPane21 = new javax.swing.JScrollPane();
        lihatProyekAjuan = new javax.swing.JTable();
        jScrollPane22 = new javax.swing.JScrollPane();
        deskripsiProyekDos = new javax.swing.JTextArea();
        jLabel57 = new javax.swing.JLabel();
        kaprodi = new javax.swing.JPanel();
        navKap = new javax.swing.JPanel();
        jButton17 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        tgl3 = new javax.swing.JLabel();
        waktu3 = new javax.swing.JLabel();
        kontenKap = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tabelSiapSetuju = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        tblPt1SetujuKapro = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        tblPt2SetujuKapro = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jPasswordField5 = new javax.swing.JPasswordField();
        jPasswordField6 = new javax.swing.JPasswordField();
        jTextField8 = new javax.swing.JTextField();
        jButton33 = new javax.swing.JButton();
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
        jLabel76 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        jPasswordField7 = new javax.swing.JPasswordField();
        jLabel79 = new javax.swing.JLabel();
        jPasswordField8 = new javax.swing.JPasswordField();
        jButton34 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jTabbedPane6 = new javax.swing.JTabbedPane();
        jPanel35 = new javax.swing.JPanel();
        jScrollPane27 = new javax.swing.JScrollPane();
        tkt1admin = new javax.swing.JTable();
        jPanel40 = new javax.swing.JPanel();
        jScrollPane28 = new javax.swing.JScrollPane();
        tkt2admin = new javax.swing.JTable();
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
        tingkatMhs = new javax.swing.JComboBox();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jenisKelamin = new javax.swing.JComboBox();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        namaKelas = new javax.swing.JTextField();
        jButton35 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox();
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

        nilaiMhs.setResizable(false);

        jLabel4.setText("nilai");

        pekanMhs.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tabPekanMhs.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane14.setViewportView(tabPekanMhs);

        inputNilaiMhsPkn.setText("input");
        inputNilaiMhsPkn.setPreferredSize(new java.awt.Dimension(73, 23));
        inputNilaiMhsPkn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputNilaiMhsPknActionPerformed(evt);
            }
        });

        namaMhsNilai.setText("jLabel16");

        nilaiTotal.setText("jLabel64");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 749, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(pekanMhs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(60, 60, 60)
                                        .addComponent(namaMhsNilai))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(68, 68, 68)
                                        .addComponent(nilaiPekanMhs, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(inputNilaiMhsPkn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nilaiTotal)
                        .addGap(157, 157, 157))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pekanMhs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(namaMhsNilai))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nilaiPekanMhs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputNilaiMhsPkn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nilaiTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(107, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout nilaiMhsLayout = new javax.swing.GroupLayout(nilaiMhs.getContentPane());
        nilaiMhs.getContentPane().setLayout(nilaiMhsLayout);
        nilaiMhsLayout.setHorizontalGroup(
            nilaiMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 769, Short.MAX_VALUE)
            .addGroup(nilaiMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        nilaiMhsLayout.setVerticalGroup(
            nilaiMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
            .addGroup(nilaiMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        accProOk.setText("ok");
        accProOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accProOkActionPerformed(evt);
            }
        });

        jLabel17.setText("jLabel17");

        jLabel18.setText("jLabel18");

        jLabel37.setText("jLabel37");

        jLabel42.setText("jLabel42");

        accSetuju.setText("setuju");
        accSetuju.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accSetujuActionPerformed(evt);
            }
        });

        accTolak.setText("tolak");
        accTolak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accTolakActionPerformed(evt);
            }
        });

        pesanAcc.setColumns(20);
        pesanAcc.setRows(5);
        jScrollPane19.setViewportView(pesanAcc);

        dscProjKapro.setEditable(false);
        dscProjKapro.setColumns(20);
        dscProjKapro.setRows(5);
        jScrollPane23.setViewportView(dscProjKapro);

        jLabel58.setText("Deskripsi Proyek");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addComponent(accProOk)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addComponent(accSetuju)
                                .addGap(18, 18, 18)
                                .addComponent(accTolak))
                            .addComponent(jLabel17)
                            .addComponent(jLabel18)
                            .addComponent(jLabel37)
                            .addComponent(jLabel42))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane23)
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addComponent(jLabel58)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel58))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel42)
                        .addGap(24, 24, 24)
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(accSetuju)
                            .addComponent(accTolak)))
                    .addComponent(jScrollPane23))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(accProOk)
                .addContainerGap())
        );

        javax.swing.GroupLayout accProyekLayout = new javax.swing.GroupLayout(accProyek.getContentPane());
        accProyek.getContentPane().setLayout(accProyekLayout);
        accProyekLayout.setHorizontalGroup(
            accProyekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 632, Short.MAX_VALUE)
            .addGroup(accProyekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(accProyekLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        accProyekLayout.setVerticalGroup(
            accProyekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 401, Short.MAX_VALUE)
            .addGroup(accProyekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(accProyekLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jButton29.setText("jButton29");

        jLabel50.setText("jLabel50");

        jLabel51.setText("jLabel51");

        jLabel52.setText("jLabel52");

        jLabel53.setText("jLabel53");

        jLabel54.setText("jLabel54");

        jLabel55.setText("Pesan Kaprodi");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane20.setViewportView(jTextArea1);

        jButton2.setText("ok");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel56.setText("jLabel56");

        dscProDos.setColumns(20);
        dscProDos.setRows(5);
        jScrollPane24.setViewportView(dscProDos);

        jLabel59.setText("Deskripsi Proyek");

        jButton30.setText("update");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton30)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane20)
                    .addComponent(jScrollPane24, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addComponent(jLabel55)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel51)
                    .addComponent(jLabel52)
                    .addComponent(jLabel53)
                    .addComponent(jLabel54)
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addComponent(jLabel50)
                        .addGap(83, 83, 83)
                        .addComponent(jLabel56))
                    .addComponent(jLabel59))
                .addContainerGap(552, Short.MAX_VALUE))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(jLabel56))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel51)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel53)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel54)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jLabel59)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel55)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton30))
                .addContainerGap())
        );

        javax.swing.GroupLayout statusProLayout = new javax.swing.GroupLayout(statusPro.getContentPane());
        statusPro.getContentPane().setLayout(statusProLayout);
        statusProLayout.setHorizontalGroup(
            statusProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusProLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        statusProLayout.setVerticalGroup(
            statusProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusProLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel60.setText("jLabel60");

        jLabel61.setText("jLabel61");

        jLabel62.setText("jLabel62");

        jLabel63.setText("deskripsi");

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane25.setViewportView(jTextArea2);

        ambilProjek.setText("ambil");
        ambilProjek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ambilProjekActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane25, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel60)
                            .addComponent(jLabel61)
                            .addComponent(jLabel62)
                            .addComponent(jLabel63)
                            .addComponent(ambilProjek))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel60)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel61)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel62)
                .addGap(42, 42, 42)
                .addComponent(jLabel63)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ambilProjek)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pilihProLayout = new javax.swing.GroupLayout(pilihPro.getContentPane());
        pilihPro.getContentPane().setLayout(pilihProLayout);
        pilihProLayout.setHorizontalGroup(
            pilihProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pilihProLayout.setVerticalGroup(
            pilihProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pekanMhs1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        namaMhsNilai1.setText("jLabel16");

        jLabel38.setText("nilai");

        inputNilaiMhsPkn1.setText("input");
        inputNilaiMhsPkn1.setPreferredSize(new java.awt.Dimension(73, 23));
        inputNilaiMhsPkn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputNilaiMhsPkn1ActionPerformed(evt);
            }
        });

        tabPekanMhs1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane31.setViewportView(tabPekanMhs1);

        nilaiTotal1.setText("jLabel64");

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane31, javax.swing.GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE)
                            .addGroup(jPanel41Layout.createSequentialGroup()
                                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel41Layout.createSequentialGroup()
                                        .addComponent(pekanMhs1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(60, 60, 60)
                                        .addComponent(namaMhsNilai1))
                                    .addGroup(jPanel41Layout.createSequentialGroup()
                                        .addComponent(jLabel38)
                                        .addGap(68, 68, 68)
                                        .addComponent(nilaiPekanMhs1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addComponent(inputNilaiMhsPkn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nilaiTotal1)
                        .addGap(157, 157, 157))))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pekanMhs1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(namaMhsNilai1))
                .addGap(26, 26, 26)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nilaiPekanMhs1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38))
                .addGap(10, 10, 10)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputNilaiMhsPkn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nilaiTotal1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane31, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout kelompokNilaiLayout = new javax.swing.GroupLayout(kelompokNilai.getContentPane());
        kelompokNilai.getContentPane().setLayout(kelompokNilaiLayout);
        kelompokNilaiLayout.setHorizontalGroup(
            kelompokNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 774, Short.MAX_VALUE)
            .addGroup(kelompokNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(kelompokNilaiLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        kelompokNilaiLayout.setVerticalGroup(
            kelompokNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 329, Short.MAX_VALUE)
            .addGroup(kelompokNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(kelompokNilaiLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        induk.setLayout(new java.awt.CardLayout());

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));

        jPanel36.setBackground(new java.awt.Color(0, 0, 153));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/bulebelajar.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(jLabel1)
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                .addContainerGap(201, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(129, 129, 129))
        );

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));

        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/appt.png"))); // NOI18N
        jLabel47.setFocusable(false);
        jLabel47.setInheritsPopupMenu(false);
        jLabel47.setRequestFocusEnabled(false);
        jLabel47.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel47)
                .addGap(71, 71, 71))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 170, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel38.setBackground(new java.awt.Color(255, 255, 255));

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel48.setText("nim");

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel49.setText("password");

        jenisLogin.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jenisLogin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ayoMasuk.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ayoMasuk.setText("masuk");
        ayoMasuk.setPreferredSize(new java.awt.Dimension(111, 31));
        ayoMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ayoMasukActionPerformed(evt);
            }
        });

        jButton32.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton32.setText("keluar");
        jButton32.setPreferredSize(new java.awt.Dimension(111, 31));
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        peringatanMasuk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        peringatanMasuk.setForeground(new java.awt.Color(255, 0, 0));
        peringatanMasuk.setText("akses anda ditolak");

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(peringatanMasuk)
                    .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel38Layout.createSequentialGroup()
                            .addComponent(ayoMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel38Layout.createSequentialGroup()
                            .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel48)
                                .addComponent(jLabel49))
                            .addGap(30, 30, 30)
                            .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(idMasuk)
                                .addComponent(passMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jenisLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(peringatanMasuk)
                .addGap(18, 18, 18)
                .addComponent(jenisLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(idMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(passMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ayoMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(74, 74, 74))
        );

        jPanel39.setBackground(new java.awt.Color(255, 255, 255));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/itt.png"))); // NOI18N

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/IF.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addGap(37, 37, 37))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addGap(96, 96, 96))
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addComponent(jLabel20)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel38, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        induk.add(jPanel34, "login");

        navigasi.setBackground(new java.awt.Color(51, 0, 153));

        jButton6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/task_completed.png"))); // NOI18N
        jButton6.setText("data akademik");
        jButton6.setBorderPainted(false);
        jButton6.setContentAreaFilled(false);
        jButton6.setName(""); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/project-plan.png"))); // NOI18N
        jButton7.setText("proyek");
        jButton7.setBorderPainted(false);
        jButton7.setContentAreaFilled(false);
        jButton7.setName(""); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/1363865588_config-users.png"))); // NOI18N
        jButton8.setText("kelompok");
        jButton8.setBorderPainted(false);
        jButton8.setContentAreaFilled(false);
        jButton8.setName(""); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/load_upload.png"))); // NOI18N
        jButton9.setText("dokumen");
        jButton9.setBorderPainted(false);
        jButton9.setContentAreaFilled(false);
        jButton9.setName(""); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/indicator-messages-new.png"))); // NOI18N
        jButton10.setText("pesan");
        jButton10.setBorderPainted(false);
        jButton10.setContentAreaFilled(false);
        jButton10.setName(""); // NOI18N
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/keluar.png"))); // NOI18N
        jButton11.setText("keluar");
        jButton11.setBorderPainted(false);
        jButton11.setContentAreaFilled(false);
        jButton11.setName(""); // NOI18N
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

        jButton25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton25.setForeground(new java.awt.Color(255, 255, 255));
        jButton25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/configuration 1.png"))); // NOI18N
        jButton25.setText("setelan");
        jButton25.setBorderPainted(false);
        jButton25.setContentAreaFilled(false);
        jButton25.setName(""); // NOI18N
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
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(navigasiLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(navigasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tgl1)
                            .addComponent(waktu1))
                        .addGap(0, 0, Short.MAX_VALUE)))
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
        statistikNilaiMhs.setForeground(new java.awt.Color(0, 0, 204));
        statistikNilaiMhs.setText("statistik nilai");
        statistikNilaiMhs.setContentAreaFilled(false);
        statistikNilaiMhs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statistikNilaiMhsActionPerformed(evt);
            }
        });

        dataNilaiMhs.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane15.setViewportView(dataNilaiMhs);

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nilaiLayout.createSequentialGroup()
                .addGap(0, 290, Short.MAX_VALUE)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(230, 230, 230))
        );
        nilaiLayout.setVerticalGroup(
            nilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nilaiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(statistikNilaiMhs)
                .addGap(113, 113, 113)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(345, Short.MAX_VALUE))
        );

        konten.add(nilai, "nilai");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel10.setText("PROYEK");

        jLabel16.setText("* jumlah yang tertera merupakan jumlah yang masih tersedia untuk dimasuki sebagai anggota kelompok");

        pilihProjMhs.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane18.setViewportView(pilihProjMhs);

        javax.swing.GroupLayout projekLayout = new javax.swing.GroupLayout(projek);
        projek.setLayout(projekLayout);
        projekLayout.setHorizontalGroup(
            projekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane18, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(projekLayout.createSequentialGroup()
                .addGroup(projekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(projekLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16))
                    .addGroup(projekLayout.createSequentialGroup()
                        .addGap(447, 447, 447)
                        .addComponent(jLabel10)))
                .addContainerGap(492, Short.MAX_VALUE))
        );
        projekLayout.setVerticalGroup(
            projekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(projekLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel10)
                .addGap(120, 120, 120)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addComponent(jLabel16)
                .addGap(0, 69, Short.MAX_VALUE))
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
                .addGap(0, 372, Short.MAX_VALUE))
        );

        konten.add(kelompok, "kelompok");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel12.setText("DOKUMEN");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane29.setViewportView(jTable2);

        jLabel84.setText("upload");

        jButton36.setText("upload");
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });

        jButton37.setText("submit");
        jButton37.setEnabled(false);
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout uploadLayout = new javax.swing.GroupLayout(upload);
        upload.setLayout(uploadLayout);
        uploadLayout.setHorizontalGroup(
            uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uploadLayout.createSequentialGroup()
                .addGroup(uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(uploadLayout.createSequentialGroup()
                        .addGap(483, 483, 483)
                        .addComponent(jLabel12))
                    .addGroup(uploadLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addGroup(uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel84)
                            .addGroup(uploadLayout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton36))
                            .addComponent(jButton37))))
                .addContainerGap(353, Short.MAX_VALUE))
        );
        uploadLayout.setVerticalGroup(
            uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uploadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGroup(uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(uploadLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(uploadLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jLabel84)
                        .addGap(36, 36, 36)
                        .addGroup(uploadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton36))
                        .addGap(36, 36, 36)
                        .addComponent(jButton37)))
                .addContainerGap(184, Short.MAX_VALUE))
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
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(totalInMail)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(totalInMail)
                .addContainerGap(12, Short.MAX_VALUE))
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
                .addContainerGap(393, Short.MAX_VALUE)
                .addComponent(totalOutMail)
                .addGap(21, 21, 21))
            .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel22Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(42, Short.MAX_VALUE)))
        );

        tabMailMhs.addTab("keluar", jPanel22);

        pesanBaruMhs.setBackground(new java.awt.Color(0, 0, 204));
        pesanBaruMhs.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pesanBaruMhs.setForeground(new java.awt.Color(0, 0, 153));
        pesanBaruMhs.setText("new");
        pesanBaruMhs.setContentAreaFilled(false);
        pesanBaruMhs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pesanBaruMhsActionPerformed(evt);
            }
        });

        refreshPesanMhs.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        refreshPesanMhs.setForeground(new java.awt.Color(0, 0, 204));
        refreshPesanMhs.setText("refresh");
        refreshPesanMhs.setContentAreaFilled(false);
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
                .addComponent(tabMailMhs, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        konten.add(pesan, "pesan");

        jLabel41.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel41.setText("SETELAN");

        jLabel64.setText("Nama :");

        jLabel65.setText("NIM :");

        jLabel66.setText("Password :");

        jLabel67.setText("Konfirmasi Password :");

        jTextField3.setEditable(false);

        jTextField4.setEditable(false);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout setelanMhsLayout = new javax.swing.GroupLayout(setelanMhs);
        setelanMhs.setLayout(setelanMhsLayout);
        setelanMhsLayout.setHorizontalGroup(
            setelanMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, setelanMhsLayout.createSequentialGroup()
                .addContainerGap(475, Short.MAX_VALUE)
                .addComponent(jLabel41)
                .addGap(452, 452, 452))
            .addGroup(setelanMhsLayout.createSequentialGroup()
                .addGap(302, 302, 302)
                .addGroup(setelanMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(setelanMhsLayout.createSequentialGroup()
                        .addGroup(setelanMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel65)
                            .addComponent(jLabel64)
                            .addComponent(jLabel66))
                        .addGap(18, 18, 18)
                        .addGroup(setelanMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField4)
                            .addComponent(jTextField3)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)))
                    .addGroup(setelanMhsLayout.createSequentialGroup()
                        .addComponent(jLabel67)
                        .addGap(18, 18, 18)
                        .addGroup(setelanMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                            .addComponent(jPasswordField2))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        setelanMhsLayout.setVerticalGroup(
            setelanMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(setelanMhsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel41)
                .addGap(36, 36, 36)
                .addGroup(setelanMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(setelanMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel65)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(setelanMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(setelanMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel67)
                    .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(373, Short.MAX_VALUE))
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
                    .addComponent(konten, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        induk.add(menu, "menu");

        navDos.setBackground(new java.awt.Color(51, 0, 153));

        jButton12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/project-plan.png"))); // NOI18N
        jButton12.setText("proyek");
        jButton12.setContentAreaFilled(false);
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/tutorials.png"))); // NOI18N
        jButton13.setText("bimbingan");
        jButton13.setContentAreaFilled(false);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton14.setForeground(new java.awt.Color(255, 255, 255));
        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/indicator-messages-new.png"))); // NOI18N
        jButton14.setText("pesan");
        jButton14.setContentAreaFilled(false);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton15.setForeground(new java.awt.Color(255, 255, 255));
        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/notes.png"))); // NOI18N
        jButton15.setText("dokumen");
        jButton15.setContentAreaFilled(false);
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton16.setForeground(new java.awt.Color(255, 255, 255));
        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/configuration 1.png"))); // NOI18N
        jButton16.setText("setelan");
        jButton16.setContentAreaFilled(false);
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton18.setForeground(new java.awt.Color(255, 255, 255));
        jButton18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/keluar.png"))); // NOI18N
        jButton18.setText("keluar");
        jButton18.setContentAreaFilled(false);
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
                    .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(navDosLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(navDosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tgl2)
                            .addComponent(waktu2))))
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

        bimbinganDosen.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane13.setViewportView(bimbinganDosen);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 1024, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(451, 451, 451)
                        .addComponent(jLabel22)))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addGap(76, 76, 76)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 92, Short.MAX_VALUE))
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

        totMailDsn.setText("jLabel42");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 981, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(totMailDsn)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totMailDsn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        totOutMailDsn.setText("jLabel42");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 981, Short.MAX_VALUE)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(totOutMailDsn)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totOutMailDsn)
                .addContainerGap())
        );

        jTabbedPane2.addTab("outbox", jPanel16);

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(0, 0, 153));
        jButton5.setText("new");
        jButton5.setContentAreaFilled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton28.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton28.setForeground(new java.awt.Color(0, 0, 153));
        jButton28.setText("refresh");
        jButton28.setContentAreaFilled(false);
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
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
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

        nilaiKelompok.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane26.setViewportView(nilaiKelompok);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(521, Short.MAX_VALUE)
                .addComponent(jLabel24)
                .addGap(458, 458, 458))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 1081, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel24)
                .addGap(34, 34, 34)
                .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(114, Short.MAX_VALUE))
        );

        kontenDos.add(jPanel5, "penilaian");

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel25.setText("SETELAN");

        jTextField5.setEditable(false);

        jLabel68.setText("Nama :");

        jLabel69.setText("NIM :");

        jTextField6.setEditable(false);

        jLabel70.setText("Password :");

        jLabel71.setText("Konfirmasi Password :");

        jButton31.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton31.setText("save");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(390, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(469, 469, 469))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel69)
                                    .addComponent(jLabel68)
                                    .addComponent(jLabel70))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField5)
                                    .addComponent(jTextField6)
                                    .addComponent(jPasswordField3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel71)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPasswordField4, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(441, 441, 441))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addGap(44, 44, 44)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel68)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel69)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel70)
                    .addComponent(jPasswordField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel71)
                    .addComponent(jPasswordField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(331, Short.MAX_VALUE))
        );

        kontenDos.add(jPanel6, "setelan");

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel21.setText("PROYEK");

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        penilaianKelompok.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane30.setViewportView(penilaianKelompok);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane30, javax.swing.GroupLayout.DEFAULT_SIZE, 984, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane30, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("progres binaan", jPanel14);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("judul");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("tingkat");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("jumlah");

        ajukanTingkat.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ajukanTingkat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ajukanProyek.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ajukanProyek.setText("ajukan");
        ajukanProyek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajukanProyekActionPerformed(evt);
            }
        });

        batalAjukanProyek.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        batalAjukanProyek.setText("batal");
        batalAjukanProyek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batalAjukanProyekActionPerformed(evt);
            }
        });

        lihatProyekAjuan.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane21.setViewportView(lihatProyekAjuan);

        deskripsiProyekDos.setColumns(20);
        deskripsiProyekDos.setRows(5);
        jScrollPane22.setViewportView(deskripsiProyekDos);

        jLabel57.setText("deskripsi : ");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ajukanProyek)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14)
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8)
                                .addComponent(jLabel5)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ajukanTingkat, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(batalAjukanProyek)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(ajukanJudul, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ajukanJumlahKlpk, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel57)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(ajukanJudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(ajukanTingkat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(ajukanJumlahKlpk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ajukanProyek)
                            .addComponent(batalAjukanProyek))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel57)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        jTabbedPane1.addTab("mengajukan", jPanel13);

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
                        .addGap(0, 48, Short.MAX_VALUE)))
                .addContainerGap())
        );

        induk.add(dosen, "dosen");

        navKap.setBackground(new java.awt.Color(51, 0, 153));

        jButton17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton17.setForeground(new java.awt.Color(255, 255, 255));
        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/project-plan.png"))); // NOI18N
        jButton17.setText("proyek");
        jButton17.setContentAreaFilled(false);
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton19.setForeground(new java.awt.Color(255, 255, 255));
        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/configuration 1.png"))); // NOI18N
        jButton19.setText("setelan");
        jButton19.setContentAreaFilled(false);
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/keluar.png"))); // NOI18N
        jButton20.setText("keluar");
        jButton20.setContentAreaFilled(false);
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
                            .addComponent(jButton19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 413, Short.MAX_VALUE)
                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        kontenKap.setLayout(new java.awt.CardLayout());

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel40.setText("SELAMAT DATANG KAPRODI");

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addContainerGap(318, Short.MAX_VALUE)
                .addComponent(jLabel40)
                .addGap(288, 288, 288))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(214, 214, 214)
                .addComponent(jLabel40)
                .addContainerGap(374, Short.MAX_VALUE))
        );

        kontenKap.add(jPanel30, "card4");

        jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel26.setText("PROYEK");

        jTabbedPane3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tabelSiapSetuju.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane12.setViewportView(tabelSiapSetuju);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 992, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane3.addTab("menyetujui", jPanel17);

        jTabbedPane4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        tblPt1SetujuKapro.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane16.setViewportView(tblPt1SetujuKapro);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 967, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("tingkat I", jPanel19);

        tblPt2SetujuKapro.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane17.setViewportView(tblPt2SetujuKapro);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 967, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
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
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        kontenKap.add(jPanel7, "proyekKap");

        jLabel27.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel27.setText("SETELAN");

        jTextField7.setEditable(false);

        jLabel72.setText("Nama :");

        jLabel73.setText("NIM :");

        jLabel74.setText("Password :");

        jLabel75.setText("Konfirmasi Password :");

        jTextField8.setEditable(false);

        jButton33.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton33.setText("save");
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(486, Short.MAX_VALUE)
                .addComponent(jLabel27)
                .addGap(467, 467, 467))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(400, 400, 400)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel73)
                            .addComponent(jLabel72)
                            .addComponent(jLabel74))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField7)
                            .addComponent(jTextField8)
                            .addComponent(jPasswordField6, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel75)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPasswordField5, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel27)
                .addGap(28, 28, 28)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel72)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel73)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel74)
                    .addComponent(jPasswordField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel75)
                    .addComponent(jPasswordField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 356, Short.MAX_VALUE))
        );

        kontenKap.add(jPanel8, "setelanKap");

        javax.swing.GroupLayout kaprodiLayout = new javax.swing.GroupLayout(kaprodi);
        kaprodi.setLayout(kaprodiLayout);
        kaprodiLayout.setHorizontalGroup(
            kaprodiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kaprodiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(navKap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addGap(0, 50, Short.MAX_VALUE)))
                .addContainerGap())
        );

        induk.add(kaprodi, "kaprodi");

        navKap1.setBackground(new java.awt.Color(51, 0, 153));

        jButton21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton21.setForeground(new java.awt.Color(204, 204, 255));
        jButton21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/project-plan.png"))); // NOI18N
        jButton21.setText("proyek");
        jButton21.setContentAreaFilled(false);
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jButton22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton22.setForeground(new java.awt.Color(204, 204, 255));
        jButton22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/configuration 1.png"))); // NOI18N
        jButton22.setText("setelan");
        jButton22.setContentAreaFilled(false);
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jButton23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton23.setForeground(new java.awt.Color(204, 204, 255));
        jButton23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/keluar.png"))); // NOI18N
        jButton23.setText("keluar");
        jButton23.setContentAreaFilled(false);
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jButton24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton24.setForeground(new java.awt.Color(204, 204, 255));
        jButton24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/notes.png"))); // NOI18N
        jButton24.setText("dokumen");
        jButton24.setContentAreaFilled(false);
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

        jButton27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton27.setForeground(new java.awt.Color(204, 204, 255));
        jButton27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/1363852247_user-group-new.png"))); // NOI18N
        jButton27.setText("tambah pengguna");
        jButton27.setContentAreaFilled(false);
        jButton27.setPreferredSize(new java.awt.Dimension(75, 23));
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        daftarUser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        daftarUser.setForeground(new java.awt.Color(204, 204, 255));
        daftarUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/penpot/img/1363865588_config-users.png"))); // NOI18N
        daftarUser.setText("daftar pengguna");
        daftarUser.setContentAreaFilled(false);
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
                            .addComponent(jButton24, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 182, Short.MAX_VALUE)
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
                .addGap(0, 654, Short.MAX_VALUE))
        );

        kontenAd.add(jPanel10, "dokumenAd");

        jLabel30.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel30.setText("SETELAN");

        jLabel76.setText("Nama :");

        jTextField9.setEditable(false);

        jLabel77.setText("NIM :");

        jTextField10.setEditable(false);

        jLabel78.setText("Password :");

        jLabel79.setText("Konfirmasi Password :");

        jButton34.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton34.setText("save");
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(463, 463, 463)
                        .addComponent(jLabel30))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(320, 320, 320)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel77)
                                    .addComponent(jLabel76)
                                    .addComponent(jLabel78))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField9)
                                    .addComponent(jTextField10)
                                    .addComponent(jPasswordField7, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel79)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPasswordField8, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(501, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30)
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel76)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel77)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel78)
                    .addComponent(jPasswordField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel79)
                    .addComponent(jPasswordField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(405, Short.MAX_VALUE))
        );

        kontenAd.add(jPanel11, "setelanAd");

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel28.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel28.setText("PROYEK");

        tkt1admin.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane27.setViewportView(tkt1admin);

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane27, javax.swing.GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane27, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane6.addTab("TINGKAT I", jPanel35);

        tkt2admin.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane28.setViewportView(tkt2admin);

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane28, javax.swing.GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane28, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane6.addTab("TINGKAT II", jPanel40);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(443, 443, 443)
                        .addComponent(jLabel28))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(jTabbedPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 753, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(211, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addGap(62, 62, 62)
                .addComponent(jTabbedPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(291, Short.MAX_VALUE))
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

        tingkatMhs.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tingkatMhs.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel80.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel80.setText("tingkat");

        jLabel81.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel81.setText("jenis kelamin");

        jenisKelamin.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jenisKelamin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel82.setText("tambah kelas");

        jLabel83.setText("nama kelas");

        jButton35.setText("tambah");
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel80))
                                .addGap(18, 18, 18))
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel82)
                                    .addGroup(jPanel24Layout.createSequentialGroup()
                                        .addComponent(jLabel83)
                                        .addGap(18, 18, 18)
                                        .addComponent(namaKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton35))
                                .addGap(251, 251, 251)
                                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel39)
                                    .addComponent(namaDaftar)
                                    .addGroup(jPanel24Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(idDaftar)))
                                .addGap(26, 26, 26)))
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(statusDaftar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tingkatMhs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(idDaftarBaru, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                                    .addComponent(passwordDfatarBaru, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(namaDaftarBaru, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                                    .addComponent(daftarOlehAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(42, 42, 42)
                                .addComponent(jLabel81)
                                .addGap(18, 18, 18)
                                .addComponent(jenisKelamin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(205, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel39)
                            .addComponent(statusDaftar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(jLabel82)))
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(namaDaftar)
                            .addComponent(namaDaftarBaru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel81)
                            .addComponent(jenisKelamin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel83)
                            .addComponent(namaKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idDaftar)
                    .addComponent(idDaftarBaru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton35))
                .addGap(51, 51, 51)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(passwordDfatarBaru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kelasBaru)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tingkatMhs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel80))
                .addGap(31, 31, 31)
                .addComponent(daftarOlehAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(207, Short.MAX_VALUE))
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
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
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
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
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
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
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
                .addGap(0, 1098, Short.MAX_VALUE))
            .addGroup(adminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adminLayout.createSequentialGroup()
                    .addContainerGap(227, Short.MAX_VALUE)
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
        try {
            // TODO add your handling code here:
            recordPekanMhs = cpm.getAll(nim);
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
        isiTabeldataNilaiMhs();
        CardLayout cad = (CardLayout) konten.getLayout();
        cad.show(konten, "nilai");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

        CardLayout cad = (CardLayout) konten.getLayout();
        cad.show(konten, "projek");

        if (mhs.getIdProyek() == 0) {
            if (mhs.getStatus().equals("II")) {
                try {
                    recordProyekII = cp.getAllPTII();
                    isiTabelPilihProjMhs("II");
                } catch (SQLException ex) {
                    Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (mhs.getStatus().equals("I")) {
                try {
                    recordProyekI = cp.getAllPTI();
                    isiTabelPilihProjMhs("I");
                } catch (SQLException ex) {
                    Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        pilihProjMhs.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                row = pilihProjMhs.getSelectedRow();
                if (row != -1) {
                    if (mhs.getStatus().equals("II")) {
                        pro = recordProyekII.get(row);
                    } else if (mhs.getStatus().equals("I")) {
                        pro = recordProyekI.get(row);
                    }
                    jLabel60.setText("Judul         : " + pro.getJudul());
                    jLabel61.setText("Dosen Pembina : " + pro.getNama());
                    jLabel62.setText("Max. Anggota  : " + pro.getJumlah());
                    jTextArea2.setText(pro.getDeskripsi());
                    pilihPro.setSize(500, 500);
                    if (pro.getJumlah().equals("0")) {
                        JOptionPane.showMessageDialog(mailContent, "Kuota Habis");
                    } else {
                        pilihPro.show();
                    }
                }
            }
        });
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        tabelTemanKelompok.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                row = tabelTemanKelompok.getSelectedRow();
                if (row != -1) {
                }
            }
        });
        Proyek p = new Proyek();
        try {
            p = cm.getDataKelompok(nim);
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
        Dosen d = new Dosen();
        try {
            d = cm.getDataDepe(nim);
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
        idProyek = p.getIdPro();
        namaKelompokMhs.setText(p.getKelompok());
        namaDosenMhs.setText(d.getNama());
        CardLayout cad = (CardLayout) konten.getLayout();
        cad.show(konten, "kelompok");
        try {
            // TODO add your handling code here:
            recordTemanKelompok = cm.getKelompok(idProyek);
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
            recordMasuk = prosespesan.getAllMasuk(onUsed, "siswa", 1);
            recordKeluar = prosespesan.getAllKeluar(onUsed, "siswa", 1);
            isiTabelMasuk("siswa");
            isiTabelKeluar("siswa");
            CardLayout cad = (CardLayout) konten.getLayout();
            cad.show(konten, "pesan");
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        try {
            // TODO add your handling code here:
            jTextField3.setText("");
            jTextField4.setText("");
            recordPekanMhs = null;
            isiTabeldataNilaiMhs();
            recordProyekII = null;
            isiTabelPilihProjMhs("II");
            recordProyekI = null;
            isiTabelPilihProjMhs("I");
            namaKelompokMhs.setText("");
            namaDosenMhs.setText("");
            recordTemanKelompok = null;
            isiTabelTemanKelompok();
            recordMasuk = null;
            recordKeluar = null;
            isiTabelMasuk("siswa");
            isiTabelKeluar("siswa");
            jTextField4.setText("");
            jTextField3.setText("");
           
        } catch (SQLException ex) {
            
        } finally {
             kosongAwal();
            this.keluar();
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        isiTabelPenilaianKelompok(nip);
        isiTabelPekanKlpk();
        penilaianKelompok.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = penilaianKelompok.getSelectedRow();
                if (row != -1) {
                    proy = recordProyekXX.get(row);
                    kelompokNilai.setSize(500, 500);
                    namaMhsNilai1.setText(proy.getJudul());
                    try {
                        recordPekanKlpk = cpk.getAll(nip, proy.getIdPro());
                    } catch (SQLException ex) {
                        Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    isiTabelPekanKlpk();
                    System.out.println("" + proy.getIdPro() + "    " + nip);
                    kelompokNilai.show();
                }
            }
        });

        lihatProyekAjuan.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                row = lihatProyekAjuan.getSelectedRow();
                pro = recordProyeks.get(row);
                if (row != -1) {
                    statusPro.setSize(600, 600);
                    jLabel50.setText("Judul : " + pro.getJudul());
                    jLabel51.setText("Tingkat : " + pro.getTingkat());
                    jLabel52.setText("Tahun : " + pro.getTahunAkademik());
                    jLabel53.setText("Tgl pengajuan : " + pro.getAjukan());
                    jLabel56.setText("Status : " + pro.getStatus());
                    jLabel54.setText("Tgl distejui : " + pro.getSetuju());
                    jTextArea1.setText(pro.getPesan());
                    dscProDos.setText(pro.getDeskripsi());
                    statusPro.show();
                }
            }
        });
        try {
            recordProyeks = cp.getAllDOsen(nip);
            isiTabelProyekDos();
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
        ajukanTingkat.removeAllItems();
        ajukanTingkat.setModel(new DefaultComboBoxModel(new String[]{"I", "II"}));
        System.out.println("" + ajukanTingkat.getSelectedItem().toString());
        CardLayout cad = (CardLayout) kontenDos.getLayout();
        cad.show(kontenDos, "proyekDos");
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:

        try {
            recordTemanKelompok = cd.getAllBinaan(nip);

        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
        isiTabelBimbingan();
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
            ls.add((mhs.getNim() + ". " + mhs.getNama() + " --" + mhs.getJudul()));
        }
        for (String s : ls) {
            daftarBinaanDosen.addItem(s);
            System.out.println("" + s);
        }

        pesanMasukDosen.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int row = pesanMasukDosen.getSelectedRow();
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
                int row = pesanKeluarDosen.getSelectedRow();
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
            this.loadPesan("dosen", 0);
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
        jTextField5.setText(dosenisme.getNama());
        jTextField6.setText(dosenisme.getNip());
        CardLayout cad = (CardLayout) kontenDos.getLayout();
        cad.show(kontenDos, "setelan");
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        jTextField5.setText("");
        jTextField6.setText("");
        this.dosenExist = false;
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
        try {
            recordProyekC = cp.getAll();
            recordProyekI = cp.getAllPTI();
            recordProyekII = cp.getAllPTII();
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
        isiTabelProyek();
        isiTabelProyekI();
        isiTabelProyekII();
        tabelSiapSetuju.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                row = tabelSiapSetuju.getSelectedRow();
                if (row != -1) {
                    try {
                        recordProyekC = cp.getAll();
                    } catch (SQLException ex) {
                        Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    pro = recordProyekC.get(row);
                    jLabel17.setText("Judul : " + pro.getJudul());
                    jLabel18.setText("Dosen : " + pro.getNama());
                    jLabel37.setText("Tgl Pengajuan : " + pro.getAjukan());
                    jLabel42.setText("Jumlah Kelompok : " + pro.getJumlah());
                    pesanAcc.setText(pro.getPesan());
                    dscProjKapro.setText(pro.getDeskripsi());
                    accProyek.setSize(600, 600);
                    accProyek.show();
                    isiTabelProyek();

                }
            }
        });



    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
        jTextField7.setText(dosenisme.getNama());
        jTextField8.setText(dosenisme.getNip());
        CardLayout cad = (CardLayout) kontenKap.getLayout();
        cad.show(kontenKap, "setelanKap");
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        try {
            // TODO add your handling code here:
            CardLayout cad = (CardLayout) kontenAd.getLayout();
            cad.show(kontenAd, "proyekAd");
            recordProyekI = cp.getAllPTI();
            recordProyekII = cp.getAllPTII();
            isiTabelProyekI();
            isiTabelProyekII();
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // TODO add your handling code here:
        jTextField9.setText(adminisme.getNama());
        jTextField10.setText(adminisme.getNip());
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
        jTextField4.setText(mhs.getNama());
        jTextField3.setText(mhs.getNim());
        CardLayout cad = (CardLayout) konten.getLayout();
        cad.show(konten, "setelanMhs");
    }//GEN-LAST:event_jButton25ActionPerformed

    private void refreshPesanMhsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshPesanMhsActionPerformed
        try {
            // TODO add your handling code here:
            this.loadPesan("siswa", 1);
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
            this.loadPesan("siswa", 1);
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (mailContent.getText().length() != 0) {
            Pesan pesan = new Pesan();
            pesan.setIsi(mailContent.getText());
            String dosenPenerima = d.getNip();
            try {
                pesan.setTgl(this.showDateNow(0));
                pesan.setProto(this.showDateNow(1));
                pesan.setJam(this.showTimeNow());
                prosespesan.siswaKirimDosen(onUsed, dosenPenerima, pesan);
                mailContent.setText("");
                this.loadPesan("siswa", 1);
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
                    if (dosenExist == true) {
                        prosespesan.deletePsnMhsMsuk(pesan, 0);
                        this.loadPesan("dosen", 0);

                    } else {
                        prosespesan.deletePsnMhsMsuk(pesan, 1);
                    }

                    prosespesan.komit();
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
                    if (dosenExist == true) {
                        prosespesan.deletePsnMhsKlr(pesan, 0);
                        this.loadPesan("dosen", 0);

                    } else {
                        prosespesan.deletePsnMhsKlr(pesan, 1);
                    }
                    prosespesan.komit();
                } catch (SQLException ex) {
                    Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            bacaPsnMasukMhs.hide();
            try {
                this.loadPesan("siswa", 1);
            } catch (SQLException ex) {
                Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton26ActionPerformed

    private void statistikNilaiMhsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statistikNilaiMhsActionPerformed
        // TODO add your handling code here:
        BarChart piechart = new BarChart(recordPekanMhs);
        piechart.pack();
        piechart.show();
    }//GEN-LAST:event_statistikNilaiMhsActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // TODO add your handling code here:
        List<Kelas> lk = new ArrayList<>();
        List<String> ls = new ArrayList<>();
        KelasController kk = new KelasController();
        int i = 0;
        try {
            lk = kk.getAll();
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
        jComboBox2.removeAllItems();
        for (Kelas k : lk) {
            ls.add((k.getKelas()));
        }
        for (String s : ls) {
            jComboBox2.addItem(s);
            System.out.println("" + s);
        }
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
                    m.setKelas(jComboBox2.getSelectedItem().toString());
                    m.setStatus(tingkatMhs.getSelectedItem().toString());
                    m.setJenisKelamin(jenisKelamin.getSelectedItem().toString());
                    if (m.getNim() != null && m.getPassword() != null) {
                        cm.insert(m);
                    }
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

    }//GEN-LAST:event_daftarOlehAdminActionPerformed

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
            isiTabeladmin();
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
        newMail1.hide();
        newMail1.dispose();
    }//GEN-LAST:event_cancelMail1ActionPerformed

    private void sendMail1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMail1ActionPerformed
        // TODO add your handling code here:
        Mahasiswa m = new Mahasiswa();

        try {
            m = cd.getDataDepe(nim);
            loadPesan("siswa", 1);
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
                pesan.setTgl(this.showDateNow(0));
                pesan.setProto(this.showDateNow(1));
                pesan.setJam(this.showTimeNow());
                prosespesan.dosenKirimSiswa(onUsed, nimPenerima, pesan);
                mailContent1.setText("");
                newMail1.hide();
                newMail1.dispose();
                this.loadPesan("dosen", 0);
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
            this.loadPesan("dosen", 0);

        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton28ActionPerformed

    private void batalAjukanProyekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batalAjukanProyekActionPerformed
        // TODO add your handling code here:
        ajukanJudul.setText("");
        ajukanJumlahKlpk.setText("");
    }//GEN-LAST:event_batalAjukanProyekActionPerformed

    private void ajukanProyekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ajukanProyekActionPerformed
        // TODO add your handling code here:
        Proyek p = new Proyek();
        p.setJudul(ajukanJudul.getText());
        p.setTingkat(ajukanTingkat.getSelectedItem().toString());
        p.setTahunAkademik(showyearNow());
        p.setNip(nip);
        p.setJumlah(ajukanJumlahKlpk.getText());
        p.setAjukan((this.showDateNow(row) + " " + this.showTimeNow()));
        p.setKelompok("");
        p.setDeskripsi(deskripsiProyekDos.getText());
        ControllerProyek cp = new ControllerProyek();
        try {
            cp.insert(p);
            ajukanJudul.setText("");
            ajukanJumlahKlpk.setText("");
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ajukanProyekActionPerformed

    private void ayoMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ayoMasukActionPerformed
        // TODO add your handling code here:
        nim = idMasuk.getText();
        nip = idMasuk.getText();
        if (idMasuk.getText().length() != 0 && passMasuk.getText().length() != 0) {
            if (posmas.periksaMasuk(idMasuk.getText(), passMasuk.getText(), sinyalemen) == true) {
                peringatanMasuk.hide();
                CardLayout cad = (CardLayout) induk.getLayout();
                if (sinyalemen == 0) {
                    try {
                        // TODO add your handling code here:
                        recordPekanMhs = cpm.getAll(nim);
                        mhs = cm.getData(nim);

                    } catch (SQLException ex) {
                        Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    isiTabeldataNilaiMhs();
                    cad.show(induk, "menu");
                }
                if (sinyalemen == 1) {
                    kelolaBaru kb = new kelolaBaru();
                    try {
                        dosenisme = kb.getData(nip);
                    } catch (SQLException ex) {
                        Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    cad.show(induk, "kaprodi");
                }
                if (sinyalemen == 2) {
                    try {
                        bimbinganDosen.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                            public void valueChanged(ListSelectionEvent e) {
                                row = bimbinganDosen.getSelectedRow();
                                if (row != -1) {
                                    sinyalHapus = 1;
                                    jLabel15.setText("ke    : ");
                                    jLabel43.setText("Dosen Pembimbing");
                                    Mahasiswa m = recordTemanKelompok.get(row);
                                    mhs = m;
                                    try {
                                        recordPekanMhs = cpm.getAll(nip, m.getNim());
                                    } catch (SQLException ex) {
                                        Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    isiTabelPekanMhs();
                                    namaMhsNilai.setText(m.getNama());
                                    nilaiMhs.setSize(769, 380);
                                    double total = 0, count = 0;
                                    for (pekanMhs p : recordPekanMhs) {
                                        total += Integer.parseInt(p.getNilai());
                                        count++;
                                    }
                                    total /= count;
                                    nilaiTotal.setText("Nilai total = " + total);
                                    nilaiMhs.show();
                                }
                            }
                        });
                        recordTemanKelompok = cd.getAllBinaan(nip);
                    } catch (SQLException ex) {
                        Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        dosenisme = cd.getData(nip);
                    } catch (SQLException ex) {
                        Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    dosenExist = true;
                    isiTabelBimbingan();
                    cad.show(induk, "dosen");

                }
                if (sinyalemen == 3) {
                    try {
                        adminisme = ca.getData(nip);
                    } catch (SQLException ex) {
                        Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    cad.show(induk, "admin");
                }
                onUsed = idMasuk.getText();
            } else {
                peringatanMasuk.show();
            }
        }
    }//GEN-LAST:event_ayoMasukActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton32ActionPerformed

    private void inputNilaiMhsPknActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputNilaiMhsPknActionPerformed
        // TODO add your handling code here:
        if (nilaiPekanMhs.getText().length() > 0) {
            try {
                pekanMhs p = new pekanMhs();
                p.setNip(nip);
                if (Integer.parseInt(nilaiPekanMhs.getText()) < 100 && Integer.parseInt(nilaiPekanMhs.getText()) > 0) {
                    p.setNim(mhs.getNim());
                    p.setPekan(pekanMhs.getSelectedItem().toString());
                    p.setNilai(nilaiPekanMhs.getText());
                    ControllerPekanMhs cpm = new ControllerPekanMhs();
                    try {
                        cpm.insert(p);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(mailContent, "Akses ditolak");
                    }
                    try {
                        recordPekanMhs = cpm.getAll(nip, mhs.getNim());
                    } catch (SQLException ex) {
                        Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(mailContent, "Nilai diluar range");
                }
                isiTabelPekanMhs();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mailContent, "tolong angka");
            }
        }
    }//GEN-LAST:event_inputNilaiMhsPknActionPerformed

    private void accSetujuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accSetujuActionPerformed
        // TODO add your handling code here:
        pro.setStatus("yes");
        pro.setSetuju(showDateNow(0) + "" + showTimeNow());
        pro.setPesan(pesanAcc.getText());

    }//GEN-LAST:event_accSetujuActionPerformed

    private void accProOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accProOkActionPerformed
        // TODO add your handling code here:
        try {
            cp.update(pro);
            accProyek.hide();
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_accProOkActionPerformed

    private void accTolakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accTolakActionPerformed
        // TODO add your handling code here:
        pro.setStatus("no");
        pro.setSetuju("");
        pro.setPesan(pesanAcc.getText());
    }//GEN-LAST:event_accTolakActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        statusPro.hide();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        try {
            // TODO add your handling code here:
            pro.setDeskripsi(dscProDos.getText());
            cp.update(pro);
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton30ActionPerformed

    private void ambilProjekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ambilProjekActionPerformed
        // TODO add your handling code here:
        int temp = Integer.parseInt(pro.getJumlah());
        if (temp != 0) {
            try {
                temp -= 1;
                pro.setJumlah("" + temp);
                cp.updateJumlah(pro);
                mhs.setIdProyek(Integer.parseInt(pro.getIdPro()));
                mhs.setNim(nim);
                try {
                    cm.updateIdPro(mhs);
                } catch (SQLException ex) {
                    Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
                }
                recordProyekII = cp.getAllPTII();
                isiTabelPilihProjMhs("II");
                recordProyekI = cp.getAllPTI();
                isiTabelPilihProjMhs("I");
            } catch (SQLException ex) {
                Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_ambilProjekActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (this.CekKonfirmasi(jPasswordField1.getText(), jPasswordField2.getText()) == true) {
            try {
                mhs.setPassword(jPasswordField1.getText());
                cm.updatePassMhs(mhs);
            } catch (SQLException ex) {
                Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        // TODO add your handling code here:
        if (this.CekKonfirmasi(jPasswordField3.getText(), jPasswordField4.getText()) == true) {
            try {
                dosenisme.setPassword(jPasswordField3.getText());
                cd.updatePassDosen(dosenisme);
            } catch (SQLException ex) {
                Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        // TODO add your handling code here:
        if (this.CekKonfirmasi(jPasswordField6.getText(), jPasswordField5.getText()) == true) {
            try {
                dosenisme.setPassword(jPasswordField6.getText());
                System.out.println("" + dosenisme.getPassword());
                kb.updatePassKaprodi(dosenisme);
            } catch (SQLException ex) {
                Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton33ActionPerformed

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        // TODO add your handling code here:
        if (this.CekKonfirmasi(jPasswordField7.getText(), jPasswordField8.getText()) == true) {
            try {
                adminisme.setPassword(jPasswordField7.getText());
                ca.updatePassAdmin(adminisme);
            } catch (SQLException ex) {
                Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton34ActionPerformed

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        try {
            // TODO add your handling code here:
            Kelas k = new Kelas();
            k.setKelas(namaKelas.getText());
            KelasController kk = new KelasController();
            kk.insert(k);
        } catch (SQLException ex) {
            Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton35ActionPerformed

    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        // TODO add your handling code here:
        JFileChooser jfc = new JFileChooser();
        int a = jfc.showOpenDialog(null);
        if (a == 0) {
            File file = jfc.getSelectedFile();
            String dir = file.getAbsolutePath();
            jTextField1.setText(dir);
            if (jTextField1.getText() != null) {
                jButton37.setEnabled(true);
            }

        }
    }//GEN-LAST:event_jButton36ActionPerformed

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton37ActionPerformed

    private void inputNilaiMhsPkn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputNilaiMhsPkn1ActionPerformed
        // TODO add your handling code here:
        if (nilaiPekanMhs1.getText().length() > 0) {
            try {
                pekanKlmpk p = new pekanKlmpk();
                p.setNip(nip);
                if (Integer.parseInt(nilaiPekanMhs1.getText()) < 100 && Integer.parseInt(nilaiPekanMhs1.getText()) > 0) {
                    p.setIdProyek(Integer.parseInt(proy.getIdPro()));
                    p.setPekan(pekanMhs1.getSelectedItem().toString());
                    p.setNilai(nilaiPekanMhs1.getText());

                    try {

                        cpk.insert(p);

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(mailContent, "Akses ditolak");
                    }
                    try {
                        System.out.println("" + proy.getIdPro());
                        recordPekanKlpk = cpk.getAll(nip, proy.getIdPro());
                    } catch (SQLException ex) {
                        Logger.getLogger(FormInduk.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(mailContent, "Nilai diluar range");
                }
                isiTabelPekanKlpk();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mailContent, "tolong angka");
            }
        }
        nilaiPekanMhs1.setText("");
    }//GEN-LAST:event_inputNilaiMhsPkn1ActionPerformed

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
    private javax.swing.JButton accProOk;
    private javax.swing.JFrame accProyek;
    private javax.swing.JButton accSetuju;
    private javax.swing.JButton accTolak;
    private javax.swing.JPanel admin;
    private javax.swing.JTextField ajukanJudul;
    private javax.swing.JTextField ajukanJumlahKlpk;
    private javax.swing.JButton ajukanProyek;
    private javax.swing.JComboBox ajukanTingkat;
    private javax.swing.JButton ambilProjek;
    private javax.swing.JButton ayoMasuk;
    private javax.swing.JFrame bacaPsnMasukMhs;
    private javax.swing.JButton batalAjukanProyek;
    private javax.swing.JTable bimbinganDosen;
    private javax.swing.JButton cancelMail;
    private javax.swing.JButton cancelMail1;
    private javax.swing.JComboBox daftarBinaanDosen;
    private javax.swing.JButton daftarOlehAdmin;
    private javax.swing.JButton daftarUser;
    private javax.swing.JTable dataNilaiMhs;
    private javax.swing.JTextArea deskripsiProyekDos;
    private javax.swing.JPanel dosen;
    private javax.swing.JTextArea dscProDos;
    private javax.swing.JTextArea dscProjKapro;
    private javax.swing.JTextField editKelasAdmin;
    private javax.swing.JFrame editMahasiswa;
    private javax.swing.JTextField editNamaAdmin;
    private javax.swing.JTextField editNimAdmin;
    private javax.swing.JTextField editStatusAdmin;
    private javax.swing.JMenu help;
    private javax.swing.JLabel idDaftar;
    private javax.swing.JTextField idDaftarBaru;
    private javax.swing.JLabel idEditUserAdmin;
    private javax.swing.JTextField idMasuk;
    private javax.swing.JPanel induk;
    private javax.swing.JButton inputNilaiMhsPkn;
    private javax.swing.JButton inputNilaiMhsPkn1;
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
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
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
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JPasswordField jPasswordField3;
    private javax.swing.JPasswordField jPasswordField4;
    private javax.swing.JPasswordField jPasswordField5;
    private javax.swing.JPasswordField jPasswordField6;
    private javax.swing.JPasswordField jPasswordField7;
    private javax.swing.JPasswordField jPasswordField8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane28;
    private javax.swing.JScrollPane jScrollPane29;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane30;
    private javax.swing.JScrollPane jScrollPane31;
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
    private javax.swing.JTabbedPane jTabbedPane6;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JComboBox jenisKelamin;
    private javax.swing.JComboBox jenisLogin;
    private javax.swing.JPanel kaprodi;
    private javax.swing.JLabel kelasBaru;
    private javax.swing.JLabel kelasMhsAdmin;
    private javax.swing.JPanel kelompok;
    private javax.swing.JFrame kelompokNilai;
    private javax.swing.JPanel konten;
    private javax.swing.JPanel kontenAd;
    private javax.swing.JPanel kontenDos;
    private javax.swing.JPanel kontenKap;
    private javax.swing.JTable lihatProyekAjuan;
    private javax.swing.JTextArea mailContent;
    private javax.swing.JTextArea mailContent1;
    private javax.swing.JPanel menu;
    private javax.swing.JLabel namaDaftar;
    private javax.swing.JTextField namaDaftarBaru;
    private javax.swing.JLabel namaDosenMhs;
    private javax.swing.JLabel namaDosenSurat;
    private javax.swing.JTextField namaKelas;
    private javax.swing.JLabel namaKelompokMhs;
    private javax.swing.JLabel namaMhsNilai;
    private javax.swing.JLabel namaMhsNilai1;
    private javax.swing.JPanel navDos;
    private javax.swing.JPanel navKap;
    private javax.swing.JPanel navKap1;
    private javax.swing.JPanel navigasi;
    private javax.swing.JFrame newMail;
    private javax.swing.JFrame newMail1;
    public javax.swing.JPanel nilai;
    private javax.swing.JTable nilaiKelompok;
    private javax.swing.JFrame nilaiMhs;
    private javax.swing.JTextField nilaiPekanMhs;
    private javax.swing.JTextField nilaiPekanMhs1;
    private javax.swing.JLabel nilaiTotal;
    private javax.swing.JLabel nilaiTotal1;
    private javax.swing.JPasswordField passMasuk;
    private javax.swing.JTextField passwordDfatarBaru;
    private javax.swing.JComboBox pekanMhs;
    private javax.swing.JComboBox pekanMhs1;
    private javax.swing.JTable penilaianKelompok;
    private javax.swing.JLabel peringatanMasuk;
    private javax.swing.JPanel pesan;
    private javax.swing.JTextArea pesanAcc;
    private javax.swing.JToggleButton pesanBaruMhs;
    private javax.swing.JTable pesanKeluarDosen;
    private javax.swing.JTable pesanKeluarSiswa;
    private javax.swing.JTable pesanMasukDosen;
    private javax.swing.JTable pesanMasukSiswa;
    private javax.swing.JFrame pilihPro;
    private javax.swing.JTable pilihProjMhs;
    private javax.swing.JPanel projek;
    private javax.swing.JToggleButton refreshPesanMhs;
    private javax.swing.JButton sendMail;
    private javax.swing.JButton sendMail1;
    private javax.swing.JPanel setelanMhs;
    private javax.swing.JButton statistikNilaiMhs;
    private javax.swing.JComboBox statusDaftar;
    private javax.swing.JLabel statusMhsAdmin;
    private javax.swing.JFrame statusPro;
    private javax.swing.JTabbedPane tabMailMhs;
    private javax.swing.JTable tabPekanMhs;
    private javax.swing.JTable tabPekanMhs1;
    private javax.swing.JTable tabelAdminAdmin;
    private javax.swing.JTable tabelDosenAdmin;
    private javax.swing.JTable tabelSiapSetuju;
    private javax.swing.JTable tabelSiswaAdmin;
    private javax.swing.JTable tabelTemanKelompok;
    private javax.swing.JTable tblPt1SetujuKapro;
    private javax.swing.JTable tblPt2SetujuKapro;
    private javax.swing.JLabel tgl1;
    private javax.swing.JLabel tgl2;
    private javax.swing.JLabel tgl3;
    private javax.swing.JLabel tgl4;
    private javax.swing.JComboBox tingkatMhs;
    private javax.swing.JTable tkt1admin;
    private javax.swing.JTable tkt2admin;
    private javax.swing.JLabel totMailDsn;
    private javax.swing.JLabel totOutMailDsn;
    private javax.swing.JLabel totalInMail;
    private javax.swing.JLabel totalOutMail;
    private javax.swing.JLabel untuk;
    private javax.swing.JLabel untuk1;
    private javax.swing.JPanel upload;
    private javax.swing.JLabel waktu1;
    private javax.swing.JLabel waktu2;
    private javax.swing.JLabel waktu3;
    private javax.swing.JLabel waktu4;
    // End of variables declaration//GEN-END:variables
}
