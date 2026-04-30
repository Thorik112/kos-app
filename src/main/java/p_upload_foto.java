/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;

public class p_upload_foto extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(p_upload_foto.class.getName());

    private int idKos;
    private JLabel lblPreview;
    private File selectedFile;
    private JTable table;
    private DefaultTableModel model;
 
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
    private static final Color C_RED        = new Color(0xDC, 0x35, 0x45);
    private static final Color C_RED_BG     = new Color(0xFD, 0xEE, 0xEE);
    private static final Color C_RED_TEXT   = new Color(0xA0, 0x20, 0x20);
 
    private static final Font FONT_BODY  = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font FONT_BOLD  = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FONT_H2    = new Font("Georgia",  Font.BOLD,  18);
    
    public p_upload_foto(int idKos) {
        //initComponents();
        
        this.idKos = idKos;
        setTitle("KosApp \u2014 Upload Foto Kos");
        setSize(500, 580);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(C_BG_PAGE);
        setLayout(new BorderLayout());
 
        initUI();
        loadFotoList();
    }
    
    private void initUI() {
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
        card.setBorder(new EmptyBorder(24, 28, 24, 28));
 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1;
        gbc.insets = new Insets(0, 0, 16, 0);
 
        // Title + deco
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
 
        JLabel title = new JLabel("Upload Foto Kos");
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
 
        titlePanel.add(title); titlePanel.add(Box.createVerticalStrut(8)); titlePanel.add(deco);
        card.add(titlePanel, gbc); gbc.gridy++;
 
        // Preview box
        lblPreview = new JLabel("Pilih gambar untuk preview", SwingConstants.CENTER) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(C_INPUT_BG); g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2.setColor(C_BORDER);
                float[] dash = {5f};
                g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5f, dash, 0f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2.dispose(); super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        lblPreview.setFont(FONT_SMALL); lblPreview.setForeground(C_TEXT_SEC);
        lblPreview.setPreferredSize(new Dimension(0, 160));
        gbc.insets = new Insets(0, 0, 10, 0);
        card.add(lblPreview, gbc); gbc.gridy++;
 
        // Pilih button
        JButton btnPilih = ghostButton("Pilih Gambar");
        btnPilih.addActionListener(e -> pilihGambar());
        gbc.insets = new Insets(0, 0, 14, 0);
        card.add(btnPilih, gbc); gbc.gridy++;
 
        // Table label
        JLabel tblLabel = new JLabel("Foto Tersimpan");
        tblLabel.setFont(FONT_BOLD); tblLabel.setForeground(C_TEXT_PRIM);
        gbc.insets = new Insets(0, 0, 6, 0);
        card.add(tblLabel, gbc); gbc.gridy++;
 
        // Table
        model = new DefaultTableModel(new String[]{"ID", "Nama File"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setFont(FONT_BODY); table.setRowHeight(28);
        table.setGridColor(C_BORDER); table.setBackground(C_BG_CARD);
        table.setSelectionBackground(new Color(0xC8, 0xA9, 0x6E, 40));
        table.setSelectionForeground(C_TEXT_PRIM);
        table.getTableHeader().setFont(FONT_BOLD);
        table.getTableHeader().setBackground(C_INPUT_BG);
        table.getTableHeader().setForeground(C_TEXT_SEC);
        table.getTableHeader().setBorder(new MatteBorder(0, 0, 1, 0, C_BORDER));
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 0));
 
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                String file = model.getValueAt(row, 1).toString();
                try {
                    Image img = new ImageIcon("E:\\NetBeansProjects\\Kos_TA\\src\\images\\" + file)
                        .getImage().getScaledInstance(440, 155, Image.SCALE_SMOOTH);
                    lblPreview.setIcon(new ImageIcon(img)); lblPreview.setText("");
                } catch (Exception ignored) {}
            }
        });
 
        JScrollPane scrollTable = new JScrollPane(table);
        scrollTable.setBorder(new LineBorder(C_BORDER, 1, true));
        scrollTable.setBackground(C_BG_CARD);
        scrollTable.getViewport().setBackground(C_BG_CARD);
        scrollTable.setPreferredSize(new Dimension(0, 130));
        gbc.insets = new Insets(0, 0, 14, 0);
        card.add(scrollTable, gbc); gbc.gridy++;
 
        // Upload + Hapus buttons
        JPanel btnRow = new JPanel(new GridLayout(1, 2, 10, 0));
        btnRow.setOpaque(false);
        JButton btnUpload = accentButton("Upload Foto");
        JButton btnHapus  = dangerButton("Hapus Foto");
        btnUpload.addActionListener(e -> upload());
        btnHapus.addActionListener(e  -> hapusFoto());
        btnRow.add(btnUpload); btnRow.add(btnHapus);
        gbc.insets = new Insets(0, 0, 0, 0);
        card.add(btnRow, gbc);
 
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
    // STYLED BUTTONS
    // ===================================================================
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
        btn.setFont(FONT_BOLD); btn.setForeground(C_BG_DARK);
        btn.setContentAreaFilled(false); btn.setBorderPainted(false); btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(9, 16, 9, 16));
        btn.setPreferredSize(new Dimension(0, 38));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
 
    private JButton ghostButton(String text) {
        JButton btn = new JButton(text) {
            private boolean hov = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                public void mouseExited (MouseEvent e) { hov = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? new Color(0xE0, 0xDD, 0xD5) : new Color(0xF0, 0xEE, 0xE8));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose(); super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        btn.setFont(FONT_BOLD); btn.setForeground(C_TEXT_PRIM);
        btn.setContentAreaFilled(false); btn.setBorderPainted(false); btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 16, 8, 16));
        btn.setPreferredSize(new Dimension(0, 36));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
 
    private JButton dangerButton(String text) {
        JButton btn = new JButton(text) {
            private boolean hov = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                public void mouseExited (MouseEvent e) { hov = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? C_RED : C_RED_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose(); super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        btn.setFont(FONT_BOLD); btn.setForeground(C_RED_TEXT);
        btn.setContentAreaFilled(false); btn.setBorderPainted(false); btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(9, 16, 9, 16));
        btn.setPreferredSize(new Dimension(0, 38));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
 
    // ===================================================================
    // DATA OPERATIONS (unchanged logic)
    // ===================================================================
    private void pilihGambar() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            Image img = new ImageIcon(selectedFile.getAbsolutePath())
                .getImage().getScaledInstance(440, 155, Image.SCALE_SMOOTH);
            lblPreview.setIcon(new ImageIcon(img)); lblPreview.setText("");
        }
    }
 
    private void upload() {
        try {
            if (selectedFile == null) { JOptionPane.showMessageDialog(this, "Pilih gambar terlebih dahulu!"); return; }
            String fileName = selectedFile.getName();
            Files.copy(selectedFile.toPath(),
                new File("E:\\NetBeansProjects\\Kos_TA\\src\\images\\" + fileName).toPath(),
                StandardCopyOption.REPLACE_EXISTING);
            Connection conn = koneksi.connect();
            PreparedStatement pst = conn.prepareStatement("INSERT INTO foto_kos(id_kos, nama_file) VALUES(?,?)");
            pst.setInt(1, idKos); pst.setString(2, fileName);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Upload berhasil!");
            loadFotoList();
        } catch (Exception e) { JOptionPane.showMessageDialog(this, e.getMessage()); }
    }
 
    private void loadFotoList() {
        model.setRowCount(0);
        try {
            Connection conn = koneksi.connect();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM foto_kos WHERE id_kos=?");
            pst.setInt(1, idKos);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id_foto"), rs.getString("nama_file")});
            }
            int rowCount = table.getRowCount();
            int h = rowCount * table.getRowHeight() + table.getTableHeader().getHeight();
            table.setPreferredScrollableViewportSize(new Dimension(400, Math.min(h, 200)));
        } catch (Exception e) { JOptionPane.showMessageDialog(this, e.getMessage()); }
    }
 
    private void hapusFoto() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Pilih foto terlebih dahulu!"); return; }
 
        int idFoto    = (int) model.getValueAt(row, 0);
        String nama   = model.getValueAt(row, 1).toString();
 
        if (JOptionPane.showConfirmDialog(this, "Yakin hapus foto ini?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                Connection conn = koneksi.connect();
                PreparedStatement pstCek = conn.prepareStatement("SELECT COUNT(*) FROM foto_kos WHERE nama_file=?");
                pstCek.setString(1, nama);
                ResultSet rs = pstCek.executeQuery();
                boolean bolehHapusFile = rs.next() && rs.getInt(1) <= 1;
 
                PreparedStatement pstDel = conn.prepareStatement("DELETE FROM foto_kos WHERE id_foto=?");
                pstDel.setInt(1, idFoto); pstDel.executeUpdate();
 
                if (bolehHapusFile) {
                    File f = new File("E:\\NetBeansProjects\\Kos_TA\\src\\images\\" + nama);
                    if (f.exists()) f.delete();
                }
                JOptionPane.showMessageDialog(this, "Foto berhasil dihapus!");
                loadFotoList();
            } catch (Exception e) { JOptionPane.showMessageDialog(this, e.getMessage()); }
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
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
