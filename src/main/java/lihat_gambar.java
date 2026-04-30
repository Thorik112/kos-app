/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class lihat_gambar extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(lihat_gambar.class.getName());

    private static final Color C_BG_PAGE  = new Color(0x1A, 0x1A, 0x1A);
    private static final Color C_BG_CARD  = new Color(0xFF, 0xFF, 0xFF);
    private static final Color C_ACCENT   = new Color(0xC8, 0xA9, 0x6E);
    private static final Color C_ACCENT_D = new Color(0xA8, 0x87, 0x3E);
    private static final Color C_BG_DARK  = new Color(0x0F, 0x19, 0x23);
    private static final Color C_TEXT_SEC = new Color(0xAA, 0xAA, 0xAA);
    private static final Color C_BORDER   = new Color(0x33, 0x33, 0x33);
 
    private static final Font FONT_BOLD  = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 12);
    
    public lihat_gambar(String path) {
        setTitle("KosApp \u2014 Bukti Pembayaran");
        setSize(640, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(C_BG_PAGE);
        setLayout(new BorderLayout());
 
        // Header bar
        JPanel header = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(C_BORDER); g.fillRect(0, getHeight() - 1, getWidth(), 1);
            }
        };
        header.setBackground(C_BG_DARK);
        header.setPreferredSize(new Dimension(0, 56));
        header.setBorder(new EmptyBorder(0, 24, 0, 24));
 
        JPanel logoRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        logoRow.setOpaque(false);
        JLabel lKos = new JLabel("KOS"); lKos.setFont(new Font("Georgia", Font.BOLD, 18)); lKos.setForeground(Color.WHITE);
        JLabel lApp = new JLabel(" APP"); lApp.setFont(new Font("Georgia", Font.PLAIN, 18)); lApp.setForeground(C_ACCENT);
        logoRow.add(lKos); logoRow.add(lApp);
 
        JLabel subLbl = new JLabel("Bukti Pembayaran");
        subLbl.setFont(FONT_SMALL); subLbl.setForeground(C_TEXT_SEC);
        JPanel subWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 18));
        subWrap.setOpaque(false); subWrap.add(subLbl);
 
        header.add(logoRow, BorderLayout.WEST);
        header.add(subWrap, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);
 
        // Image area
        JLabel imgLabel = new JLabel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(C_BG_PAGE); g2.fillRect(0, 0, getWidth(), getHeight());
                if (getIcon() == null) {
                    g2.setFont(FONT_SMALL); g2.setColor(C_TEXT_SEC);
                    String msg = "Gambar tidak ditemukan";
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(msg, (getWidth() - fm.stringWidth(msg)) / 2, getHeight() / 2);
                }
                g2.dispose(); super.paintComponent(g);
            }
        };
        imgLabel.setHorizontalAlignment(JLabel.CENTER);
 
        try {
            System.out.println("LOAD GAMBAR: " + path);
            ImageIcon icon = new ImageIcon(path);
            if (icon.getIconWidth() == -1) throw new Exception("Gambar tidak ditemukan");
            Image img = icon.getImage().getScaledInstance(560, 420, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            imgLabel.setText(null);
        }
 
        JScrollPane scroll = new JScrollPane(imgLabel);
        scroll.setBorder(null);
        scroll.setBackground(C_BG_PAGE);
        scroll.getViewport().setBackground(C_BG_PAGE);
        add(scroll, BorderLayout.CENTER);
 
        // Footer with close button
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 14)) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(C_BORDER); g.fillRect(0, 0, getWidth(), 1);
            }
        };
        footer.setBackground(C_BG_DARK);
        footer.setPreferredSize(new Dimension(0, 60));
 
        JButton btnTutup = new JButton("Tutup") {
            private boolean hov = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                public void mouseExited (MouseEvent e) { hov = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? C_ACCENT_D : C_ACCENT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose(); super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        btnTutup.setFont(FONT_BOLD); btnTutup.setForeground(C_BG_DARK);
        btnTutup.setContentAreaFilled(false); btnTutup.setBorderPainted(false); btnTutup.setFocusPainted(false);
        btnTutup.setBorder(new EmptyBorder(8, 32, 8, 32));
        btnTutup.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnTutup.addActionListener(e -> dispose());
        footer.add(btnTutup);
        add(footer, BorderLayout.SOUTH);
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
