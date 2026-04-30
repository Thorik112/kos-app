/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;

public class p_kelola_kamar extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(p_kelola_kamar.class.getName());

    private int idKos;
    private int idEdit = -1;
 
    private JTextField txtNomor;
    private JTextArea  txtDeskripsi;
    private JComboBox<String> cmbStatus;
    private String fotoPath = "";
    private JCheckBox cbAC, cbWifi, cbLemari, cbKM;
    private JLabel lblPreview;
    private JPanel panelList;
 
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
    private static final Color C_GREEN      = new Color(0x2E, 0xA0, 0x6F);
    private static final Color C_GREEN_BG   = new Color(0xE8, 0xF7, 0xF1);
    private static final Color C_GREEN_TEXT = new Color(0x1A, 0x7A, 0x50);
    private static final Color C_RED        = new Color(0xDC, 0x35, 0x45);
    private static final Color C_RED_BG     = new Color(0xFD, 0xEE, 0xEE);
    private static final Color C_RED_TEXT   = new Color(0xA0, 0x20, 0x20);
    private static final Color C_INPUT_BG   = new Color(0xFA, 0xF9, 0xF6);
 
    private static final Font FONT_BODY  = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font FONT_BOLD  = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FONT_H2    = new Font("Georgia",  Font.BOLD,  18);
    private static final Font FONT_H3    = new Font("Georgia",  Font.BOLD,  15);
    
    public p_kelola_kamar(int idKos) {
//        initComponents();
        
        this.idKos = idKos;
        setTitle("KosApp \u2014 Kelola Kamar");
        setSize(560, 860);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(C_BG_PAGE);
        setLayout(new BorderLayout());
 
        initUI();
        loadKamar();
    }
    
    private void initUI() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(C_BG_PAGE);
 
        wrapper.add(buildFormCard(), BorderLayout.NORTH);
 
        panelList = new JPanel();
        panelList.setLayout(new BoxLayout(panelList, BoxLayout.Y_AXIS));
        panelList.setBackground(C_BG_PAGE);
        panelList.setBorder(new EmptyBorder(0, 20, 20, 20));
 
        JScrollPane scrollList = new JScrollPane(panelList);
        scrollList.setBorder(null);
        scrollList.setBackground(C_BG_PAGE);
        scrollList.getViewport().setBackground(C_BG_PAGE);
        scrollList.getVerticalScrollBar().setUnitIncrement(16);
        wrapper.add(scrollList, BorderLayout.CENTER);
 
        add(wrapper);
    }
 
    private JPanel buildFormCard() {
        JPanel outer = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(C_BG_CARD);
                g2.fillRoundRect(20, 20, getWidth() - 40, getHeight() - 20, 16, 16);
                g2.setColor(C_BORDER);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(20, 20, getWidth() - 40, getHeight() - 20, 16, 16);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        outer.setBackground(C_BG_PAGE);
        outer.setLayout(new BorderLayout());
        outer.setBorder(new EmptyBorder(20, 40, 20, 40));
 
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        form.setBorder(new EmptyBorder(10, 0, 10, 0));
 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 4, 0);
        gbc.gridwidth = 2; gbc.weightx = 1;
 
        // Title + deco
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(new EmptyBorder(0, 0, 14, 0));
 
        JLabel title = new JLabel("Tambah / Edit Kamar");
        title.setFont(FONT_H2); title.setForeground(C_TEXT_PRIM);
        title.setAlignmentX(LEFT_ALIGNMENT);
 
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
        form.add(titlePanel, gbc); gbc.gridy++;
 
        // Nomor Kamar
        gbc.gridwidth = 1; gbc.weightx = 0.3;
        form.add(fieldLabel("Nomor Kamar"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7; gbc.insets = new Insets(0, 8, 4, 0);
        txtNomor = styledTextField();
        form.add(txtNomor, gbc);
 
        gbc.gridx = 0; gbc.gridy++; gbc.weightx = 0.3; gbc.insets = new Insets(0, 0, 4, 0);
        form.add(fieldLabel("Status"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7; gbc.insets = new Insets(0, 8, 4, 0);
        cmbStatus = styledComboBox(new String[]{"tersedia", "penuh"});
        cmbStatus.setEnabled(false);
        form.add(cmbStatus, gbc);
 
        gbc.gridx = 0; gbc.gridy++; gbc.weightx = 0.3; gbc.insets = new Insets(0, 0, 4, 0);
        form.add(fieldLabel("Deskripsi"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7; gbc.insets = new Insets(0, 8, 4, 0);
        txtDeskripsi = new JTextArea(3, 0);
        txtDeskripsi.setFont(FONT_BODY); txtDeskripsi.setLineWrap(true); txtDeskripsi.setWrapStyleWord(true);
        txtDeskripsi.setBackground(C_INPUT_BG); txtDeskripsi.setForeground(C_TEXT_PRIM);
        txtDeskripsi.setBorder(new CompoundBorder(
            new LineBorder(C_BORDER, 1, true), new EmptyBorder(6, 10, 6, 10)));
        form.add(new JScrollPane(txtDeskripsi), gbc);
 
        // Foto
        gbc.gridx = 0; gbc.gridy++; gbc.weightx = 0.3; gbc.insets = new Insets(0, 0, 4, 0);
        form.add(fieldLabel("Foto"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7; gbc.insets = new Insets(0, 8, 4, 0);
 
        JPanel fotoPanel = new JPanel(new BorderLayout(0, 6));
        fotoPanel.setOpaque(false);
 
        lblPreview = new JLabel("Klik untuk pilih foto", SwingConstants.CENTER) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(C_INPUT_BG); g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.setColor(C_BORDER);
                float[] dash = {4f}; g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 4f, dash, 0f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.dispose(); super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        lblPreview.setFont(FONT_SMALL); lblPreview.setForeground(C_TEXT_SEC);
        lblPreview.setPreferredSize(new Dimension(0, 100));
 
        JButton btnFoto = ghostButton("Pilih Foto");
        btnFoto.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                fotoPath = fc.getSelectedFile().getAbsolutePath();
                Image img = new ImageIcon(fotoPath).getImage().getScaledInstance(300, 100, Image.SCALE_SMOOTH);
                lblPreview.setIcon(new ImageIcon(img)); lblPreview.setText("");
            }
        });
        fotoPanel.add(lblPreview, BorderLayout.CENTER);
        fotoPanel.add(btnFoto, BorderLayout.SOUTH);
        form.add(fotoPanel, gbc);
 
        // Fasilitas
        gbc.gridx = 0; gbc.gridy++; gbc.weightx = 0.3; gbc.insets = new Insets(0, 0, 4, 0);
        form.add(fieldLabel("Fasilitas"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7; gbc.insets = new Insets(0, 8, 4, 0);
 
        cbAC     = styledCheckBox("AC");
        cbWifi   = styledCheckBox("WiFi");
        cbLemari = styledCheckBox("Lemari");
        cbKM     = styledCheckBox("KM Dalam");
 
        JPanel cbPanel = new JPanel(new GridLayout(2, 2, 8, 4));
        cbPanel.setOpaque(false);
        cbPanel.add(cbAC); cbPanel.add(cbWifi); cbPanel.add(cbLemari); cbPanel.add(cbKM);
        form.add(cbPanel, gbc);
 
        // Simpan button
        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        gbc.insets = new Insets(14, 0, 0, 0);
        JButton btnSimpan = accentButton(idEdit == -1 ? "Simpan Kamar" : "Update Kamar");
        btnSimpan.addActionListener(e -> {
            if (idEdit == -1) tambahKamar(); else updateKamar();
        });
        form.add(btnSimpan, gbc);
 
        outer.add(form);
        return outer;
    }
 
    // ===================================================================
    // KAMAR CARD
    // ===================================================================
    private JPanel createCard(int id, String nomor, String status, String desk, String foto) {
        boolean tersedia = status.equalsIgnoreCase("tersedia");
        Color barColor  = tersedia ? C_GREEN  : C_RED;
        Color badgeBg   = tersedia ? C_GREEN_BG  : C_RED_BG;
        Color badgeFg   = tersedia ? C_GREEN_TEXT : C_RED_TEXT;
        String badgeText = tersedia ? "TERSEDIA" : "PENUH";
 
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(0, 0, 10, 0));
 
        final Color fBar = barColor;
        JPanel card = new JPanel(new BorderLayout(12, 0)) {
            private boolean hov = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                public void mouseExited (MouseEvent e) { hov = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth() - 1, h = getHeight() - 1;
                g2.setColor(C_BG_CARD); g2.fillRoundRect(0, 0, w, h, 14, 14);
                g2.setColor(hov ? C_ACCENT : C_BORDER);
                g2.setStroke(new BasicStroke(hov ? 1.5f : 1f));
                g2.drawRoundRect(0, 0, w, h, 14, 14);
                g2.setColor(fBar); g2.setStroke(new BasicStroke(1f));
                g2.fillRoundRect(0, 16, 5, h - 32, 3, 3);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        card.setBorder(new EmptyBorder(14, 20, 14, 16));
 
        // Photo thumbnail
        JLabel lblFoto = new JLabel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0xF0, 0xEE, 0xE8));
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.dispose(); super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        lblFoto.setPreferredSize(new Dimension(90, 75));
        lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
 
        String imgPath = (foto != null && !foto.isEmpty())
            ? "E:\\NetBeansProjects\\Kos_TA\\src\\images\\" + foto
            : "E:\\NetBeansProjects\\Kos_TA\\src\\images\\profile.png";
        try {
            Image img = new ImageIcon(imgPath).getImage().getScaledInstance(90, 75, Image.SCALE_SMOOTH);
            lblFoto.setIcon(new ImageIcon(img));
        } catch (Exception ignored) {}
 
        // Info
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
 
        JLabel lblJudul = new JLabel("Kamar " + nomor);
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblJudul.setForeground(C_TEXT_PRIM);
        lblJudul.setAlignmentX(LEFT_ALIGNMENT);
 
        if (desk != null && !desk.isEmpty()) {
            JLabel lblDesk = new JLabel(desk.length() > 50 ? desk.substring(0, 49) + "\u2026" : desk);
            lblDesk.setFont(FONT_SMALL); lblDesk.setForeground(C_TEXT_SEC);
            lblDesk.setAlignmentX(LEFT_ALIGNMENT);
            info.add(lblJudul); info.add(Box.createVerticalStrut(4)); info.add(lblDesk);
        } else {
            info.add(lblJudul);
        }
 
        JLabel lblFas = new JLabel("Memuat fasilitas...");
        lblFas.setFont(FONT_SMALL); lblFas.setForeground(C_TEXT_SEC);
        lblFas.setAlignmentX(LEFT_ALIGNMENT);
        loadFasilitasLabel(id, lblFas);
        info.add(Box.createVerticalStrut(4)); info.add(lblFas);
 
        // Right: badge + buttons
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setOpaque(false);
 
        final Color fBadgeBg = badgeBg; final Color fBadgeFg = badgeFg;
        JLabel badge = new JLabel(badgeText, SwingConstants.CENTER) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(fBadgeBg); g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose(); super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        badge.setFont(new Font("Segoe UI", Font.BOLD, 10));
        badge.setForeground(fBadgeFg);
        badge.setBorder(new EmptyBorder(4, 10, 4, 10));
        badge.setAlignmentX(RIGHT_ALIGNMENT);
 
        JButton btnEdit  = ghostButton("Edit");
        JButton btnHapus = dangerButton("Hapus");
        btnEdit.addActionListener(e  -> isiForm(nomor, status, desk, id));
        btnHapus.addActionListener(e -> hapusKamar(id));
 
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        btnRow.setOpaque(false);
        btnRow.add(btnEdit); btnRow.add(btnHapus);
 
        right.add(badge); right.add(Box.createVerticalGlue()); right.add(btnRow);
 
        card.add(lblFoto, BorderLayout.WEST);
        card.add(info,    BorderLayout.CENTER);
        card.add(right,   BorderLayout.EAST);
 
        wrapper.add(card);
        return wrapper;
    }
 
    // ===================================================================
    // STYLED COMPONENTS
    // ===================================================================
    private JLabel fieldLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_BOLD); l.setForeground(C_TEXT_PRIM);
        return l;
    }
 
    private JTextField styledTextField() {
        JTextField tf = new JTextField();
        tf.setFont(FONT_BODY); tf.setBackground(C_INPUT_BG); tf.setForeground(C_TEXT_PRIM);
        tf.setBorder(new CompoundBorder(new LineBorder(C_BORDER, 1, true), new EmptyBorder(6, 10, 6, 10)));
        tf.setPreferredSize(new Dimension(0, 34));
        return tf;
    }
 
    private JComboBox<String> styledComboBox(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(FONT_BODY); cb.setBackground(C_INPUT_BG); cb.setForeground(C_TEXT_PRIM);
        cb.setBorder(new LineBorder(C_BORDER, 1, true));
        cb.setPreferredSize(new Dimension(0, 34));
        return cb;
    }
 
    private JCheckBox styledCheckBox(String text) {
        JCheckBox cb = new JCheckBox(text);
        cb.setFont(FONT_BODY); cb.setForeground(C_TEXT_PRIM); cb.setOpaque(false);
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
        btn.setFont(FONT_BOLD); btn.setForeground(C_BG_DARK);
        btn.setContentAreaFilled(false); btn.setBorderPainted(false); btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(9, 20, 9, 20));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
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
        btn.setBorder(new EmptyBorder(6, 12, 6, 12));
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
        btn.setBorder(new EmptyBorder(6, 12, 6, 12));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
 
    // ===================================================================
    // DATA OPERATIONS (unchanged logic)
    // ===================================================================
    private void loadKamar() {
        panelList.removeAll();
        JLabel secLabel = new JLabel("Daftar Kamar");
        secLabel.setFont(FONT_H3); secLabel.setForeground(C_TEXT_PRIM);
        secLabel.setAlignmentX(LEFT_ALIGNMENT);
        secLabel.setBorder(new EmptyBorder(14, 0, 10, 0));
        panelList.add(secLabel);
 
        try {
            Connection conn = koneksi.connect();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM kamar WHERE id_kos=?");
            pst.setInt(1, idKos);
            ResultSet rs = pst.executeQuery();
            boolean any = false;
            while (rs.next()) {
                any = true;
                panelList.add(createCard(
                    rs.getInt("id_kamar"), rs.getString("nomor_kamar"),
                    rs.getString("status_kamar"), rs.getString("deskripsi"), rs.getString("foto")));
            }
            if (!any) {
                JLabel empty = new JLabel("Belum ada kamar. Tambahkan kamar di atas.");
                empty.setFont(FONT_BODY); empty.setForeground(C_TEXT_SEC);
                panelList.add(empty);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        panelList.revalidate(); panelList.repaint();
    }
 
    private void tambahKamar() {
        try {
            String nomor = txtNomor.getText().trim();
            if (nomor.isEmpty()) { JOptionPane.showMessageDialog(this, "Nomor kamar tidak boleh kosong!"); return; }
            if (isNomorKamarExist(nomor)) { JOptionPane.showMessageDialog(this, "Nomor kamar sudah ada!"); return; }
 
            Connection conn = koneksi.connect();
            String namaFile = "";
            if (!fotoPath.isEmpty()) {
                namaFile = new File(fotoPath).getName();
                Files.copy(new File(fotoPath).toPath(),
                    new File("E:\\NetBeansProjects\\Kos_TA\\src\\images\\" + namaFile).toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
            }
 
            PreparedStatement pst = conn.prepareStatement(
                "INSERT INTO kamar (id_kos, nomor_kamar, status_kamar, deskripsi, foto) VALUES (?,?,?,?,?)");
            pst.setInt(1, idKos); pst.setString(2, nomor);
            pst.setString(3, cmbStatus.getSelectedItem().toString());
            pst.setString(4, txtDeskripsi.getText()); pst.setString(5, namaFile);
            pst.executeUpdate();
 
            ResultSet rs = conn.createStatement().executeQuery("SELECT MAX(id_kamar) as id FROM kamar");
            if (rs.next()) simpanFasilitas(rs.getInt("id"));
 
            JOptionPane.showMessageDialog(this, "Kamar berhasil ditambahkan!");
            clearForm(); loadKamar();
        } catch (Exception e) { JOptionPane.showMessageDialog(this, e.getMessage()); }
    }
 
    private void isiForm(String nomor, String status, String desk, int id) {
        txtNomor.setText(nomor); txtDeskripsi.setText(desk);
        cmbStatus.setSelectedItem(status); idEdit = id;
        loadFasilitas(id);
    }
 
    private void loadFasilitas(int idKamar) {
        try {
            cbAC.setSelected(false); cbWifi.setSelected(false);
            cbLemari.setSelected(false); cbKM.setSelected(false);
            Connection conn = koneksi.connect();
            PreparedStatement pst = conn.prepareStatement("SELECT fasilitas FROM fasilitas WHERE id_kamar=?");
            pst.setInt(1, idKamar);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String f = rs.getString("fasilitas");
                if (f.equals("AC"))       cbAC.setSelected(true);
                if (f.equals("WiFi"))     cbWifi.setSelected(true);
                if (f.equals("Lemari"))   cbLemari.setSelected(true);
                if (f.equals("KM Dalam")) cbKM.setSelected(true);
            }
        } catch (Exception e) { JOptionPane.showMessageDialog(this, e.getMessage()); }
    }
 
    private void updateKamar() {
        try {
            String nomor = txtNomor.getText().trim();
            if (nomor.isEmpty()) { JOptionPane.showMessageDialog(this, "Nomor kamar tidak boleh kosong!"); return; }
 
            Connection conn = koneksi.connect();
            PreparedStatement pstCek = conn.prepareStatement(
                "SELECT * FROM kamar WHERE id_kos=? AND nomor_kamar=? AND id_kamar!=?");
            pstCek.setInt(1, idKos); pstCek.setString(2, nomor); pstCek.setInt(3, idEdit);
            if (pstCek.executeQuery().next()) { JOptionPane.showMessageDialog(this, "Nomor kamar sudah dipakai!"); return; }
 
            String namaFile = "";
            if (!fotoPath.isEmpty()) {
                namaFile = new File(fotoPath).getName();
                Files.copy(new File(fotoPath).toPath(),
                    new File("E:\\NetBeansProjects\\Kos_TA\\src\\images\\" + namaFile).toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
            } else {
                PreparedStatement pstF = conn.prepareStatement("SELECT foto FROM kamar WHERE id_kamar=?");
                pstF.setInt(1, idEdit);
                ResultSet rf = pstF.executeQuery();
                if (rf.next()) namaFile = rf.getString("foto");
            }
 
            PreparedStatement pst = conn.prepareStatement(
                "UPDATE kamar SET nomor_kamar=?, status_kamar=?, deskripsi=?, foto=? WHERE id_kamar=?");
            pst.setString(1, nomor); pst.setString(2, cmbStatus.getSelectedItem().toString());
            pst.setString(3, txtDeskripsi.getText()); pst.setString(4, namaFile); pst.setInt(5, idEdit);
            pst.executeUpdate();
 
            PreparedStatement pstDel = conn.prepareStatement("DELETE FROM fasilitas WHERE id_kamar=?");
            pstDel.setInt(1, idEdit); pstDel.executeUpdate();
            simpanFasilitas(idEdit);
 
            JOptionPane.showMessageDialog(this, "Kamar berhasil diupdate!");
            clearForm(); loadKamar();
        } catch (Exception e) { JOptionPane.showMessageDialog(this, e.getMessage()); }
    }
 
    private void hapusKamar(int id) {
        if (JOptionPane.showConfirmDialog(this, "Yakin hapus kamar ini?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                Connection conn = koneksi.connect();
                PreparedStatement pst = conn.prepareStatement("DELETE FROM kamar WHERE id_kamar=?");
                pst.setInt(1, id); pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Berhasil dihapus!");
                loadKamar();
            } catch (Exception e) { JOptionPane.showMessageDialog(this, e.getMessage()); }
        }
    }
 
    private void clearForm() {
        txtNomor.setText(""); txtDeskripsi.setText("");
        cmbStatus.setSelectedIndex(0); fotoPath = "";
        cbAC.setSelected(false); cbWifi.setSelected(false);
        cbLemari.setSelected(false); cbKM.setSelected(false);
        idEdit = -1; lblPreview.setIcon(null); lblPreview.setText("Klik untuk pilih foto");
    }
 
    private boolean isNomorKamarExist(String nomor) {
        try {
            Connection conn = koneksi.connect();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM kamar WHERE id_kos=? AND nomor_kamar=?");
            pst.setInt(1, idKos); pst.setString(2, nomor);
            return pst.executeQuery().next();
        } catch (Exception e) { JOptionPane.showMessageDialog(this, e.getMessage()); }
        return false;
    }
 
    private void simpanFasilitas(int idKamar) {
        try {
            Connection conn = koneksi.connect();
            PreparedStatement pst = conn.prepareStatement("INSERT INTO fasilitas (id_kamar, fasilitas) VALUES (?,?)");
            String[] items = {"AC", "WiFi", "Lemari", "KM Dalam"};
            JCheckBox[] cbs = {cbAC, cbWifi, cbLemari, cbKM};
            for (int i = 0; i < cbs.length; i++) {
                if (cbs[i].isSelected()) { pst.setInt(1, idKamar); pst.setString(2, items[i]); pst.executeUpdate(); }
            }
        } catch (Exception e) { JOptionPane.showMessageDialog(this, e.getMessage()); }
    }
 
    private void loadFasilitasLabel(int idKamar, JLabel label) {
        try {
            Connection conn = koneksi.connect();
            PreparedStatement pst = conn.prepareStatement("SELECT fasilitas FROM fasilitas WHERE id_kamar=?");
            pst.setInt(1, idKamar);
            ResultSet rs = pst.executeQuery();
            StringBuilder sb = new StringBuilder("Fasilitas: ");
            while (rs.next()) sb.append(rs.getString("fasilitas")).append(", ");
            label.setText(sb.toString().equals("Fasilitas: ") ? "Fasilitas: -"
                : sb.substring(0, sb.length() - 2));
        } catch (Exception e) { label.setText("Fasilitas: -"); }
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
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
//            logger.log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(() -> new p_kelola_kamar().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
