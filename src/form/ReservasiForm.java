/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package form;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.Koneksi;
import java.util.Date;
/**
 *
 * @author Lenovo
 */
public class ReservasiForm extends javax.swing.JFrame {
    private String idReservasi;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ReservasiForm.class.getName());

    /**
     * Creates new form ReservasiForm
     */
    public ReservasiForm() {
    initComponents();

    setLocationRelativeTo(null);
    setResizable(false);

    tblReservasi.setRowHeight(25);

    tampilData();
    loadTamu();
    loadKamar();
}
    
    private void loadTamu() {

    try {

        Connection conn = Koneksi.getKoneksi();

        String sql = "SELECT * FROM tamu";

        PreparedStatement pst = conn.prepareStatement(sql);

        ResultSet rs = pst.executeQuery();

        cmbTamu.removeAllItems();

        while (rs.next()) {
            cmbTamu.addItem(rs.getString("id_tamu") + " - " + rs.getString("nama"));
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }

}
    
    private void loadKamar() {

    try {

        Connection conn = Koneksi.getKoneksi();

        String sql = "SELECT * FROM kamar WHERE status='Kosong'";

        PreparedStatement pst = conn.prepareStatement(sql);

        ResultSet rs = pst.executeQuery();

        cmbKamar.removeAllItems();

        while (rs.next()) {
            cmbKamar.addItem(rs.getString("id_kamar") + " - " + rs.getString("nomor_kamar"));
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }

}

    private void tampilData() {

    DefaultTableModel model = new DefaultTableModel();

    model.addColumn("ID");
    model.addColumn("ID Tamu");
    model.addColumn("ID Kamar");
    model.addColumn("Check In");
    model.addColumn("Check Out");
    model.addColumn("Lama");
    model.addColumn("Total");
    model.addColumn("Status");

    try {

        Connection conn = Koneksi.getKoneksi();

        String sql = "SELECT r.*, t.nama, k.nomor_kamar "
           + "FROM reservasi r "
           + "JOIN tamu t ON r.id_tamu = t.id_tamu "
           + "JOIN kamar k ON r.id_kamar = k.id_kamar";

        PreparedStatement pst = conn.prepareStatement(sql);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {

            model.addRow(new Object[]{
    rs.getString("id_reservasi"),
    rs.getString("id_tamu") + " - " + rs.getString("nama"),
    rs.getString("id_kamar") + " - " + rs.getString("nomor_kamar"),
                rs.getString("checkin"),
                rs.getString("checkout"),
                rs.getString("lama_menginap"),
                rs.getString("total_bayar"),
                rs.getString("status")
            });

        }

        tblReservasi.setModel(model);

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this, e.getMessage());

    }

}
    private void resetForm() {

    cmbTamu.setSelectedIndex(0);
    cmbKamar.setSelectedIndex(0);

    dcCheckIn.setDate(null);
    dcCheckOut.setDate(null);

    txtLama.setText("");
    txtTotal.setText("");

    cmbStatus.setSelectedIndex(0);

    txtCari.setText("");

    tampilData();
}
    private long hitungLamaMenginap() {

    Date checkIn = dcCheckIn.getDate();
    Date checkOut = dcCheckOut.getDate();

    if (checkIn == null || checkOut == null) {
        return 0;
    }

    long selisih = checkOut.getTime() - checkIn.getTime();

    return selisih / (1000 * 60 * 60 * 24);
}
    private int hitungTotalBayar() {

    int harga = 0;

    try {

        Connection conn = Koneksi.getKoneksi();

        String idKamar = cmbKamar.getSelectedItem().toString().split(" - ")[0];

        String sql = "SELECT harga FROM kamar WHERE id_kamar=?";

        PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, idKamar);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {

            harga = rs.getInt("harga");

        }

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this, e.getMessage());

    }

    return (int) hitungLamaMenginap() * harga;

}
    private void cariData() {

    DefaultTableModel model = new DefaultTableModel();

    model.addColumn("ID");
    model.addColumn("ID Tamu");
    model.addColumn("ID Kamar");
    model.addColumn("Check In");
    model.addColumn("Check Out");
    model.addColumn("Lama");
    model.addColumn("Total");
    model.addColumn("Status");

    try {

        Connection conn = Koneksi.getKoneksi();

        String sql = "SELECT * FROM reservasi WHERE "
                + "id_tamu LIKE ? OR "
                + "id_kamar LIKE ? OR "
                + "status LIKE ?";

        PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, "%" + txtCari.getText() + "%");
        pst.setString(2, "%" + txtCari.getText() + "%");
        pst.setString(3, "%" + txtCari.getText() + "%");

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {

            model.addRow(new Object[]{
                rs.getString("id_reservasi"),
                rs.getString("id_tamu"),
                rs.getString("id_kamar"),
                rs.getString("checkin"),
                rs.getString("checkout"),
                rs.getString("lama_menginap"),
                rs.getString("total_bayar"),
                rs.getString("status")
            });

        }

        tblReservasi.setModel(model);

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this, e.getMessage());

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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmbTamu = new javax.swing.JComboBox<>();
        cmbKamar = new javax.swing.JComboBox<>();
        dcCheckIn = new com.toedter.calendar.JDateChooser();
        dcCheckOut = new com.toedter.calendar.JDateChooser();
        txtLama = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        btnTambah = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblReservasi = new javax.swing.JTable();
        btnKembali = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("DATA RESERVASI");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Tamu :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Check In :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Kamar :");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Lama Inap :");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Check Out :");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Total Bayar :");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Status :");

        cmbTamu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbTamu.addActionListener(this::cmbTamuActionPerformed);

        cmbKamar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        dcCheckOut.addPropertyChangeListener(this::dcCheckOutPropertyChange);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Hari");

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Check In", "Check Out" }));

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(this::btnTambahActionPerformed);

        btnUbah.setText("Ubah");
        btnUbah.addActionListener(this::btnUbahActionPerformed);

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(this::btnHapusActionPerformed);

        btnReset.setText("Reset");
        btnReset.addActionListener(this::btnResetActionPerformed);

        jLabel10.setText("Cari :");

        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
        });

        tblReservasi.setModel(new javax.swing.table.DefaultTableModel(
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
        tblReservasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblReservasiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblReservasi);

        btnKembali.setText("Kembali");
        btnKembali.addActionListener(this::btnKembaliActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addComponent(btnTambah)
                        .addGap(52, 52, 52)
                        .addComponent(btnUbah)
                        .addGap(51, 51, 51)
                        .addComponent(btnHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnReset)
                        .addGap(37, 37, 37)
                        .addComponent(btnKembali)
                        .addGap(67, 67, 67))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7))
                                .addGap(60, 60, 60)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dcCheckOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtLama)
                                    .addComponent(txtTotal)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(cmbTamu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cmbKamar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(dcCheckIn, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE))
                                    .addComponent(cmbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCari)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 663, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(184, 184, 184))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(448, 448, 448))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(cmbTamu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(cmbKamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3))
                            .addComponent(dcCheckIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6))
                    .addComponent(dcCheckOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnUbah)
                    .addComponent(btnHapus)
                    .addComponent(btnReset)
                    .addComponent(btnKembali))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dcCheckOutPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dcCheckOutPropertyChange
if (dcCheckIn.getDate() != null && dcCheckOut.getDate() != null) {

        txtLama.setText(String.valueOf(hitungLamaMenginap()));
        txtTotal.setText(String.valueOf(hitungTotalBayar()));

    }
     
// TODO add your handling code here:
    }//GEN-LAST:event_dcCheckOutPropertyChange

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
try {

        Connection conn = Koneksi.getKoneksi();

        String idTamu = cmbTamu.getSelectedItem().toString().split(" - ")[0];
        String idKamar = cmbKamar.getSelectedItem().toString().split(" - ")[0];

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

        String checkIn = sdf.format(dcCheckIn.getDate());
        String checkOut = sdf.format(dcCheckOut.getDate());

        String sql = "INSERT INTO reservasi(id_tamu, id_kamar, checkin, checkout, lama_menginap, total_bayar, status) VALUES (?,?,?,?,?,?,?)";

        PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, idTamu);
        pst.setString(2, idKamar);
        pst.setString(3, checkIn);
        pst.setString(4, checkOut);
        pst.setLong(5, hitungLamaMenginap());
        pst.setInt(6, hitungTotalBayar());
        pst.setString(7, cmbStatus.getSelectedItem().toString());

        pst.executeUpdate();

        // Mengubah status kamar menjadi Terisi
        String sql2 = "UPDATE kamar SET status='Terisi' WHERE id_kamar=?";

        PreparedStatement pst2 = conn.prepareStatement(sql2);

        pst2.setString(1, idKamar);

        pst2.executeUpdate();

        JOptionPane.showMessageDialog(this, "Reservasi berhasil disimpan");

        tampilData();
        loadKamar();
        resetForm();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this, e.getMessage());

    }        // TODO add your handling code here:
    }//GEN-LAST:event_btnTambahActionPerformed

    private void tblReservasiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblReservasiMouseClicked
int baris = tblReservasi.getSelectedRow();

    idReservasi = tblReservasi.getValueAt(baris, 0).toString();

    // Pilih ComboBox Tamu berdasarkan ID
    String tamu = tblReservasi.getValueAt(baris, 1).toString();
cmbTamu.setSelectedItem(tamu);
    

    // Pilih ComboBox Kamar berdasarkan ID
    String kamar = tblReservasi.getValueAt(baris, 2).toString();
cmbKamar.setSelectedItem(kamar);
    

    try {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

        dcCheckIn.setDate(sdf.parse(tblReservasi.getValueAt(baris, 3).toString()));
        dcCheckOut.setDate(sdf.parse(tblReservasi.getValueAt(baris, 4).toString()));

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }

    txtLama.setText(tblReservasi.getValueAt(baris, 5).toString());
    txtTotal.setText(tblReservasi.getValueAt(baris, 6).toString());

    cmbStatus.setSelectedItem(tblReservasi.getValueAt(baris, 7).toString());     
// TODO add your handling code here:
    }//GEN-LAST:event_tblReservasiMouseClicked

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
try {

        Connection conn = Koneksi.getKoneksi();

        String idTamu = cmbTamu.getSelectedItem().toString().split(" - ")[0];
        String idKamar = cmbKamar.getSelectedItem().toString().split(" - ")[0];

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

        String checkIn = sdf.format(dcCheckIn.getDate());
        String checkOut = sdf.format(dcCheckOut.getDate());

        String sql = "UPDATE reservasi SET id_tamu=?, id_kamar=?, checkin=?, checkout=?, lama_menginap=?, total_bayar=?, status=? WHERE id_reservasi=?";

        PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, idTamu);
        pst.setString(2, idKamar);
        pst.setString(3, checkIn);
        pst.setString(4, checkOut);
        pst.setLong(5, hitungLamaMenginap());
        pst.setInt(6, hitungTotalBayar());
        pst.setString(7, cmbStatus.getSelectedItem().toString());
        pst.setString(8, idReservasi);

        pst.executeUpdate();

        JOptionPane.showMessageDialog(this, "Data reservasi berhasil diubah");

        tampilData();
        resetForm();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this, e.getMessage());

    }        // TODO add your handling code here:
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
 try {

        Connection conn = Koneksi.getKoneksi();

        // Ambil id kamar dari reservasi yang dipilih
        String sqlCari = "SELECT id_kamar FROM reservasi WHERE id_reservasi=?";

        PreparedStatement pstCari = conn.prepareStatement(sqlCari);
        pstCari.setString(1, idReservasi);

        ResultSet rs = pstCari.executeQuery();

        String idKamar = "";

        if (rs.next()) {
            idKamar = rs.getString("id_kamar");
        }

        // Hapus reservasi
        String sql = "DELETE FROM reservasi WHERE id_reservasi=?";

        PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, idReservasi);

        pst.executeUpdate();

        // Ubah status kamar menjadi Kosong lagi
        String sqlUpdate = "UPDATE kamar SET status='Kosong' WHERE id_kamar=?";

        PreparedStatement pstUpdate = conn.prepareStatement(sqlUpdate);

        pstUpdate.setString(1, idKamar);

        pstUpdate.executeUpdate();

        JOptionPane.showMessageDialog(this, "Reservasi berhasil dihapus");

        tampilData();
        loadKamar();
        resetForm();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this, e.getMessage());

    }        // TODO add your handling code here:
    }//GEN-LAST:event_btnHapusActionPerformed

    private void cmbTamuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTamuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTamuActionPerformed

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
cariData();        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariKeyReleased

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
resetForm();
tampilData();        // TODO add your handling code here:
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
new DashboardForm().setVisible(true);
    this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_btnKembaliActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new ReservasiForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKembali;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUbah;
    private javax.swing.JComboBox<String> cmbKamar;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JComboBox<String> cmbTamu;
    private com.toedter.calendar.JDateChooser dcCheckIn;
    private com.toedter.calendar.JDateChooser dcCheckOut;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblReservasi;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtLama;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}