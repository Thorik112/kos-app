/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class form_kos extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(form_kos.class.getName());

    private JTextField txtNama, txtDeskripsi, txtKota, txtAlamat, txtHarga, txtAturan;
    private JComboBox<String> cmbTipe;
    private int idKos = 0;
 
    // ===================================================================
    // DESIGN TOKENS
    // ===================================================================
    private static final Color C_BG_PAGE    = new Color(0xF5, 0xF4, 0xF0);
    private static final Color C_BG_CARD    = new Color(0xFF, 0xFF, 0xFF);
    private static final Color C_ACCENT     = new Color(0xC8, 0xA9, 0x6E);
    private static final Color C_ACCENT_DARK= new Color(0xA8, 0x87, 0x3E);
    private static final Color C_BG_DARK    = new Color(0x0F, 0x19, 0x23);
    private static final Color C_TEXT_PRIM  = new Color(0x1A, 0x1A, 0x1A);
    private static final Color C_TEXT_SEC   = new Color(0x77, 0x77, 0x77);
    private static final Color C_BORDER     = new Color(0xE8, 0xE5, 0xDF);
    private static final Color C_INPUT_BG   = new Color(0xFA, 0xF9, 0xF6);
 
    private static final Font FONT_BODY  = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_BOLD  = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FONT_H2    = new Font("Georgia",  Font.BOLD,  20);
    
    public form_kos() {
//        initComponents();
        setTitle("KosApp \u2014 Tambah Kos");
        setResizable(false);
        setLocationRelativeTo(null);
        init();
    }
    
    public form_kos(int idKos) {
        this.idKos = idKos;
        setTitle("KosApp \u2014 Edit Kos");
        setResizable(false);
        setLocationRelativeTo(null);
        init();
        loadData();
    }
    
    private void init() {
        setSize(500, 640);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(C_BG_PAGE);
 
        txtNama      = styledTextField();
        txtDeskripsi = styledTextField();
        txtKota      = styledTextField();
        txtAlamat    = styledTextField();
        txtHarga     = styledTextField();
        txtAturan    = styledTextField();
        cmbTipe      = styledComboBox(new String[]{"bebas", "muslim", "muslimah"});
 
        JPanel card = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(C_BG_CARD);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2.setColor(C_BORDER); g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        card.setBorder(new EmptyBorder(28, 32, 28, 32));
 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1;
        gbc.insets = new Insets(0, 0, 16, 0);
 
        // Title + deco
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
 
        JLabel title = new JLabel(idKos == 0 ? "Tambah Kos Baru" : "Edit Data Kos");
        title.setFont(FONT_H2); title.setForeground(C_TEXT_PRIM); title.setAlignmentX(LEFT_ALIGNMENT);
 
        JPanel deco = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(C_ACCENT); g2.fillRect(0, 1, 32, 3);
                g2.setColor(C_BORDER); g2.fillRect(36, 2, getWidth() - 36, 1);
                g2.dispose();
            }
        };
        deco.setOpaque(false); deco.setAlignmentX(LEFT_ALIGNMENT);
        deco.setMaximumSize(new Dimension(Integer.MAX_VALUE, 6));
        deco.setPreferredSize(new Dimension(0, 6));
 
        titlePanel.add(title); titlePanel.add(Box.createVerticalStrut(10)); titlePanel.add(deco);
        card.add(titlePanel, gbc); gbc.gridy++;
 
        String[][] fields = {
            {"Nama Kos", "nama"}, {"Deskripsi", "deskripsi"}, {"Kota", "kota"},
            {"Alamat", "alamat"}, {"Harga (Rp)", "harga"}, {"Aturan Kos", "aturan"}
        };
        JComponent[] comps = {txtNama, txtDeskripsi, txtKota, txtAlamat, txtHarga, txtAturan};
 
        for (int i = 0; i < comps.length; i++) {
            gbc.insets = new Insets(0, 0, i == comps.length - 1 ? 0 : 12, 0);
            card.add(createFieldPanel(fields[i][0], comps[i]), gbc); gbc.gridy++;
        }
 
        gbc.insets = new Insets(0, 0, 12, 0);
        card.add(createFieldPanel("Tipe Kos", cmbTipe), gbc); gbc.gridy++;
 
        gbc.insets = new Insets(18, 0, 0, 0);
        JButton btnSimpan = accentButton(idKos == 0 ? "Simpan Kos" : "Update Kos");
        btnSimpan.addActionListener(e -> simpan());
        card.add(btnSimpan, gbc);
 
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(C_BG_PAGE);
        wrapper.setBorder(new EmptyBorder(24, 24, 24, 24));
        wrapper.add(card, new GridBagConstraints());
 
        JScrollPane scroll = new JScrollPane(wrapper);
        scroll.setBorder(null);
        scroll.setBackground(C_BG_PAGE);
        scroll.getViewport().setBackground(C_BG_PAGE);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }
 
    // ===================================================================
    // STYLED COMPONENTS
    // ===================================================================
    private JPanel createFieldPanel(String label, JComponent field) {
        JPanel p = new JPanel(new BorderLayout(0, 5));
        p.setOpaque(false);
        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_BOLD); lbl.setForeground(C_TEXT_PRIM);
        p.add(lbl, BorderLayout.NORTH);
        p.add(field, BorderLayout.CENTER);
        return p;
    }
 
    private JTextField styledTextField() {
        JTextField tf = new JTextField();
        tf.setFont(FONT_BODY); tf.setBackground(C_INPUT_BG); tf.setForeground(C_TEXT_PRIM);
        tf.setBorder(new CompoundBorder(new LineBorder(C_BORDER, 1, true), new EmptyBorder(7, 10, 7, 10)));
        tf.setPreferredSize(new Dimension(0, 36));
        return tf;
    }
 
    private JComboBox<String> styledComboBox(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(FONT_BODY); cb.setBackground(C_INPUT_BG); cb.setForeground(C_TEXT_PRIM);
        cb.setBorder(new LineBorder(C_BORDER, 1, true));
        cb.setPreferredSize(new Dimension(0, 36));
        return cb;
    }
 
    private JButton accentButton(String text) {
        JButton btn = new JButton(text) {
            private boolean hov = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                public void mouseExited (MouseEvent e) { hov = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? C_ACCENT_DARK : C_ACCENT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose(); super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(C_BG_DARK);
        btn.setContentAreaFilled(false); btn.setBorderPainted(false); btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        return btn;
    }
 
    // ===================================================================
    // DATA
    // ===================================================================
    private void loadData() {
        try {
            Connection conn = koneksi.connect();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM kos WHERE id_kos=?");
            pst.setInt(1, idKos);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                txtNama.setText(rs.getString("nama_kos"));
                txtDeskripsi.setText(rs.getString("deskripsi"));
                txtKota.setText(rs.getString("kota"));
                txtAlamat.setText(rs.getString("alamat"));
                txtHarga.setText(String.valueOf(rs.getDouble("harga")));
                txtAturan.setText(rs.getString("aturan_kos"));
                cmbTipe.setSelectedItem(rs.getString("tipe_kos"));
            }
        } catch (Exception e) { JOptionPane.showMessageDialog(this, e.getMessage()); }
    }
 
    private void simpan() {
        try {
            if (txtNama.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong");
                return;
            }
            Connection conn = koneksi.connect();
            String sql = idKos == 0
                ? "INSERT INTO kos(nama_kos, deskripsi, kota, alamat, tipe_kos, harga, aturan_kos, id_user) VALUES(?,?,?,?,?,?,?,?)"
                : "UPDATE kos SET nama_kos=?, deskripsi=?, kota=?, alamat=?, tipe_kos=?, harga=?, aturan_kos=? WHERE id_kos=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtNama.getText()); pst.setString(2, txtDeskripsi.getText());
            pst.setString(3, txtKota.getText());  pst.setString(4, txtAlamat.getText());
            pst.setString(5, cmbTipe.getSelectedItem().toString());
            pst.setDouble(6, Double.parseDouble(txtHarga.getText()));
            pst.setString(7, txtAturan.getText());
            pst.setInt(8, idKos == 0 ? session.id_user : idKos);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Berhasil disimpan!");
            dispose();
        } catch (Exception e) { JOptionPane.showMessageDialog(this, e.getMessage()); }
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
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) { javax.swing.UIManager.setLookAndFeel(info.getClassName()); break; }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new form_kos().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
