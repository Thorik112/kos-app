/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;

public class f_pembayaran_user extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(f_pembayaran_user.class.getName());

    private int idBooking;
    private JLabel lblInfo, lblPreview;
    private JComboBox<String> metode;
    private File selectedFile;
    
    public f_pembayaran_user(int idBooking) {
        //initComponents();
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        this.idBooking = idBooking;

        setTitle("Pembayaran");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initUI();
        loadDetail();
    }
    
    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        lblInfo = new JLabel("Detail Booking");

        metode = new JComboBox<>(new String[]{
            "Transfer BCA", "Transfer BRI", "Dana", "OVO"
        });

        JButton btnPilih = new JButton("Pilih Bukti");
        JButton btnUpload = new JButton("Bayar");

        lblPreview = new JLabel("Preview", JLabel.CENTER);
        lblPreview.setPreferredSize(new Dimension(300,200));

        btnPilih.addActionListener(e -> pilihFile());
        btnUpload.addActionListener(e -> upload());

        panel.add(lblInfo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Metode Pembayaran"));
        panel.add(metode);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblPreview);
        panel.add(btnPilih);
        panel.add(btnUpload);

        add(panel);
    }
    
    private void loadDetail() {
        try {
            Connection conn = koneksi.connect();

            String sql = "SELECT total_harga FROM booking WHERE id_booking=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idBooking);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                double total = rs.getDouble("total_harga");

                lblInfo.setText("Total Bayar: Rp " + total);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    private void pilihFile() {
        JFileChooser chooser = new JFileChooser();

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();

            ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
            Image img = icon.getImage().getScaledInstance(300,200,Image.SCALE_SMOOTH);

            lblPreview.setIcon(new ImageIcon(img));
        }
    }
    
    private void upload() {
        try {
            if (selectedFile == null) {
                JOptionPane.showMessageDialog(this, "Pilih bukti dulu!");
                return;
            }

            Connection conn = koneksi.connect();

            // ambil total dari booking
            String q = "SELECT total_harga FROM booking WHERE id_booking=?";
            PreparedStatement pstQ = conn.prepareStatement(q);
            pstQ.setInt(1, idBooking);

            ResultSet rs = pstQ.executeQuery();

            double total = 0;
            if (rs.next()) {
                total = rs.getDouble("total_harga");
            }

            // simpan file
            String fileName = System.currentTimeMillis() + "_" + selectedFile.getName(); // biar unik
            File dest = new File("src/images/" + fileName);
            Files.copy(selectedFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // insert pembayaran
            String sql = "INSERT INTO pembayaran " +
                         "(id_booking, metode_pembayaran, total_bayar, tanggal_bayar, bukti_pembayaran, status_pembayaran) " +
                         "VALUES (?,?,?,NOW(),?,'pending')";

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setInt(1, idBooking);
            pst.setString(2, metode.getSelectedItem().toString());
            pst.setDouble(3, total);
            pst.setString(4, fileName);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Pembayaran berhasil dikirim!");
            
            new f_riwayat_booking().setVisible(true);
            dispose();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
