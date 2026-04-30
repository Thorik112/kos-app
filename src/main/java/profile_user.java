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


public class profile_user extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(profile_user.class.getName());

    private static final Color C_BG_PAGE          = new Color(0xF5, 0xF4, 0xF0);
    private static final Color C_BG_CARD          = new Color(0xFF, 0xFF, 0xFF);
    private static final Color C_BG_SIDEBAR       = new Color(0x0F, 0x19, 0x23);
    private static final Color C_BG_SIDEBAR_HOVER = new Color(0x1E, 0x2D, 0x3D);
    private static final Color C_ACCENT           = new Color(0xC8, 0xA9, 0x6E);
    private static final Color C_ACCENT_DARK      = new Color(0xA8, 0x87, 0x3E);
    private static final Color C_TEXT_PRIM        = new Color(0x1A, 0x1A, 0x1A);
    private static final Color C_TEXT_SEC         = new Color(0x77, 0x77, 0x77);
    private static final Color C_TEXT_SIDEBAR     = new Color(0xB8, 0xC4, 0xCC);
    private static final Color C_BORDER           = new Color(0xE8, 0xE5, 0xDF);
    private static final Color C_HEADER_BG        = new Color(0xFF, 0xFF, 0xFF);
    private static final Color C_SIDEBAR_DIV      = new Color(0x2A, 0x3A, 0x4A);
    private static final Color C_ACTIVE_BG        = new Color(200, 169, 110, 35);
    private static final Color C_INPUT_FOCUS      = new Color(0xC8, 0xA9, 0x6E, 80);
 
    private static final Font FONT_TITLE  = new Font("Georgia",  Font.BOLD,  14);
    private static final Font FONT_BODY   = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_SMALL  = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font FONT_BOLD   = new Font("Segoe UI", Font.BOLD,  13);
 
    private JTextField txtNama, txtEmail, txtTelp, txtPekerjaan;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbJK;
    private JComboBox<String> cmbRole;
    private JLabel lblFoto;
    private File selectedFile;
    
    public profile_user() {
//        initComponents();
        
        setTitle("KosApp — Edit Profil");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(C_BG_PAGE);
 
        initUI();
        loadData();
    }
    
    private void initUI() {
        setLayout(new BorderLayout());
        add(buildSidebar(), BorderLayout.WEST);
 
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(C_BG_PAGE);
        main.add(buildHeader(), BorderLayout.NORTH);
        main.add(buildContent(), BorderLayout.CENTER);
        add(main, BorderLayout.CENTER);
    }
 
    // ===================================================================
    // SIDEBAR
    // ===================================================================
    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(C_BG_SIDEBAR);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(C_SIDEBAR_DIV);
                g2.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
                g2.dispose();
            }
        };
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setOpaque(false);
 
        JPanel logoPanel = new JPanel();
        logoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoPanel.setOpaque(false);
        logoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 74));
        logoPanel.setBorder(new EmptyBorder(22, 26, 22, 26));
        JLabel lKos = new JLabel("KOS");
        lKos.setFont(new Font("Georgia", Font.BOLD, 22));
        lKos.setForeground(Color.WHITE);
        JLabel lApp = new JLabel(" APP");
        lApp.setFont(new Font("Georgia", Font.PLAIN, 22));
        lApp.setForeground(C_ACCENT);
        logoPanel.add(lKos); logoPanel.add(lApp);
        sidebar.add(logoPanel);
        sidebar.add(hRule());
 
        JPanel nav = new JPanel();
        nav.setOpaque(false);
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        nav.setBorder(new EmptyBorder(16, 0, 0, 0));
        nav.setAlignmentX(LEFT_ALIGNMENT);
        navLabel(nav, "NAVIGASI");
 
        JButton btnDash    = sidebarBtn("Dashboard",       "\u2302", false);
        JButton btnCari    = sidebarBtn("Cari Kos",        "\u25A2", false);
        JButton btnRiwayat = sidebarBtn("Riwayat Booking", "\u2630", false);
        JButton btnNotif   = sidebarBtn("Notifikasi",      "\u25CE", false);
 
        btnDash.addActionListener(e    -> { new home_user().setVisible(true); dispose(); });
        btnCari.addActionListener(e    -> { new cari_kos().setVisible(true);  dispose(); });
        btnRiwayat.addActionListener(e -> new f_riwayat_booking().setVisible(true));
        btnNotif.addActionListener(e   -> new f_notifikasi_user().setVisible(true));
 
        nav.add(btnDash);     nav.add(Box.createVerticalStrut(2));
        nav.add(btnCari);     nav.add(Box.createVerticalStrut(2));
        nav.add(btnRiwayat);  nav.add(Box.createVerticalStrut(2));
        nav.add(btnNotif);
        sidebar.add(nav);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(sidebarUserCard());
 
        JButton btnLogout = sidebarBtn("Keluar", "\u2192", false);
        btnLogout.setForeground(new Color(0xFF, 0x78, 0x78));
        btnLogout.addActionListener(e -> logout());
        sidebar.add(btnLogout);
        sidebar.add(Box.createVerticalStrut(20));
        return sidebar;
    }
 
    private JPanel sidebarUserCard() {
        JPanel card = new JPanel(new BorderLayout(12, 0));
        card.setOpaque(false);
        card.setBorder(new CompoundBorder(
            new MatteBorder(1, 0, 1, 0, C_SIDEBAR_DIV),
            new EmptyBorder(14, 22, 14, 22)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        card.setAlignmentX(LEFT_ALIGNMENT);
 
        JPanel avatar = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(C_ACCENT);
                g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
                String init = session.username != null && !session.username.isEmpty()
                    ? String.valueOf(session.username.charAt(0)).toUpperCase() : "U";
                g2.setFont(new Font("Georgia", Font.BOLD, 14));
                g2.setColor(C_BG_SIDEBAR);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(init,
                    (getWidth()  - fm.stringWidth(init)) / 2,
                    (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        avatar.setPreferredSize(new Dimension(38, 38));
 
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
        String displayName = session.username != null ? session.username : "User";
        if (displayName.length() > 14) displayName = displayName.substring(0, 13) + "…";
        JLabel uname = new JLabel(displayName);
        uname.setFont(FONT_BOLD);
        uname.setForeground(Color.WHITE);
        JLabel role = new JLabel("Penyewa");
        role.setFont(FONT_SMALL);
        role.setForeground(new Color(0x88, 0xA0, 0xB0));
        info.add(uname); info.add(role);
        card.add(avatar, BorderLayout.WEST);
        card.add(info,   BorderLayout.CENTER);
        return card;
    }
 
    private Component hRule() {
        JPanel p = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(C_SIDEBAR_DIV);
                g.fillRect(26, 0, getWidth() - 52, 1);
            }
        };
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        p.setPreferredSize(new Dimension(0, 1));
        return p;
    }
 
    private void navLabel(JPanel p, String t) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.BOLD, 10));
        l.setForeground(new Color(0x44, 0x55, 0x66));
        l.setBorder(new EmptyBorder(0, 28, 8, 0));
        l.setAlignmentX(LEFT_ALIGNMENT);
        p.add(l);
    }
 
    private JButton sidebarBtn(String label, String icon, boolean active) {
        JButton btn = new JButton(icon + "   " + label) {
            private boolean hov = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                public void mouseExited (MouseEvent e) { hov = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int mx = 14, my = 2, bw = getWidth() - mx * 2, bh = getHeight() - my * 2;
                if (active) {
                    g2.setColor(C_ACTIVE_BG);
                    g2.fillRoundRect(mx, my, bw, bh, 8, 8);
                    g2.setColor(C_ACCENT);
                    g2.fillRoundRect(0, 10, 4, bh - 8, 3, 3);
                } else if (hov) {
                    g2.setColor(C_BG_SIDEBAR_HOVER);
                    g2.fillRoundRect(mx, my, bw, bh, 8, 8);
                }
                g2.dispose();
                super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        btn.setFont(active ? FONT_BOLD : FONT_BODY);
        btn.setForeground(active ? Color.WHITE : C_TEXT_SIDEBAR);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(10, 28, 10, 20));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
 
    // ===================================================================
    // HEADER
    // ===================================================================
    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(C_BORDER);
                g.fillRect(0, getHeight() - 1, getWidth(), 1);
            }
        };
        header.setBackground(C_HEADER_BG);
        header.setBorder(new EmptyBorder(0, 32, 0, 32));
        header.setPreferredSize(new Dimension(0, 68));
 
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 16));
        left.setOpaque(false);
        JLabel title = new JLabel("Edit Profil");
        title.setFont(new Font("Georgia", Font.BOLD, 20));
        title.setForeground(C_TEXT_PRIM);
        JLabel crumb = new JLabel("  /  Akun Saya");
        crumb.setFont(FONT_BODY);
        crumb.setForeground(C_TEXT_SEC);
        left.add(title); left.add(crumb);
 
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 12));
        right.setOpaque(false);
        JLabel greeting = new JLabel("Selamat Datang, "
            + (session.username != null ? session.username : "User"));
        greeting.setFont(FONT_BODY);
        greeting.setForeground(C_TEXT_SEC);
        right.add(greeting);
 
        header.add(left,  BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);
        return header;
    }
 
    // ===================================================================
    // CONTENT
    // ===================================================================
    private JScrollPane buildContent() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(C_BG_PAGE);
        wrapper.setBorder(new EmptyBorder(32, 32, 32, 32));
        wrapper.add(sectionHeader(), BorderLayout.NORTH);
 
        // Two-column layout: avatar left, form right
        JPanel body = new JPanel(new BorderLayout(24, 0));
        body.setOpaque(false);
        body.add(buildAvatarPanel(), BorderLayout.WEST);
        body.add(buildFormCard(),    BorderLayout.CENTER);
        wrapper.add(body, BorderLayout.CENTER);
 
        JScrollPane scroll = new JScrollPane(wrapper);
        scroll.setBorder(null);
        scroll.setBackground(C_BG_PAGE);
        scroll.getViewport().setBackground(C_BG_PAGE);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }
 
    private JPanel sectionHeader() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(0, 0, 24, 0));
 
        JLabel t = new JLabel("Pengaturan Profil");
        t.setFont(new Font("Georgia", Font.BOLD, 22));
        t.setForeground(C_TEXT_PRIM);
        t.setAlignmentX(LEFT_ALIGNMENT);
 
        JLabel s = new JLabel("Kelola informasi pribadi dan keamanan akun Anda");
        s.setFont(FONT_BODY);
        s.setForeground(C_TEXT_SEC);
        s.setAlignmentX(LEFT_ALIGNMENT);
 
        JPanel deco = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(C_ACCENT);
                g2.fillRect(0, 1, 38, 3);
                g2.setColor(C_BORDER);
                g2.fillRect(42, 2, getWidth() - 42, 1);
                g2.dispose();
            }
        };
        deco.setOpaque(false);
        deco.setAlignmentX(LEFT_ALIGNMENT);
        deco.setMaximumSize(new Dimension(Integer.MAX_VALUE, 6));
        deco.setPreferredSize(new Dimension(0, 6));
 
        p.add(t);
        p.add(Box.createVerticalStrut(5));
        p.add(s);
        p.add(Box.createVerticalStrut(13));
        p.add(deco);
        return p;
    }
 
    // ===================================================================
    // AVATAR PANEL (left column)
    // ===================================================================
    private JPanel buildAvatarPanel() {
        JPanel outer = new JPanel();
        outer.setLayout(new BoxLayout(outer, BoxLayout.Y_AXIS));
        outer.setOpaque(false);
        outer.setPreferredSize(new Dimension(220, 0));
 
        JPanel card = roundedCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(28, 20, 28, 20));
 
        // Avatar circle display
        lblFoto = new JLabel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                int d = Math.min(getWidth(), getHeight()) - 4;
                int x = (getWidth()  - d) / 2;
                int y = (getHeight() - d) / 2;
                if (getIcon() != null) {
                    g2.setClip(new Ellipse2D.Float(x, y, d, d));
                    g2.drawImage(((ImageIcon) getIcon()).getImage(), x, y, d, d, null);
                    g2.setClip(null);
                } else {
                    // Placeholder gradient circle
                    g2.setColor(new Color(0xDC, 0xD9, 0xD3));
                    g2.fillOval(x, y, d, d);
                    g2.setFont(new Font("Georgia", Font.BOLD, 36));
                    g2.setColor(C_TEXT_SEC);
                    String init = session.username != null && !session.username.isEmpty()
                        ? String.valueOf(session.username.charAt(0)).toUpperCase() : "?";
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(init,
                        x + (d - fm.stringWidth(init)) / 2,
                        y + (d + fm.getAscent() - fm.getDescent()) / 2);
                }
                // Gold ring
                g2.setColor(C_ACCENT);
                g2.setStroke(new BasicStroke(2.5f));
                g2.drawOval(x, y, d, d);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        lblFoto.setPreferredSize(new Dimension(150, 150));
        lblFoto.setMaximumSize(new Dimension(150, 150));
        lblFoto.setAlignmentX(CENTER_ALIGNMENT);
 
        JButton btnPilih = new JButton("Ubah Foto") {
            private boolean hov = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                public void mouseExited (MouseEvent e) { hov = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? C_ACCENT : C_BORDER);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                g2.setColor(hov ? Color.WHITE : C_TEXT_PRIM);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                    (getWidth() - fm.stringWidth(getText())) / 2,
                    (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        btnPilih.setFont(FONT_BOLD);
        btnPilih.setContentAreaFilled(false);
        btnPilih.setBorderPainted(false);
        btnPilih.setFocusPainted(false);
        btnPilih.setPreferredSize(new Dimension(160, 36));
        btnPilih.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        btnPilih.setAlignmentX(CENTER_ALIGNMENT);
        btnPilih.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnPilih.addActionListener(e -> pilihFoto());
 
        // Username label under avatar
        JLabel nameLbl = new JLabel(session.username != null ? session.username : "User");
        nameLbl.setFont(new Font("Georgia", Font.BOLD, 15));
        nameLbl.setForeground(C_TEXT_PRIM);
        nameLbl.setAlignmentX(CENTER_ALIGNMENT);
 
        JLabel roleLbl = new JLabel("Penyewa");
        roleLbl.setFont(FONT_SMALL);
        roleLbl.setForeground(C_TEXT_SEC);
        roleLbl.setAlignmentX(CENTER_ALIGNMENT);
 
        card.add(lblFoto);
        card.add(Box.createVerticalStrut(16));
        card.add(nameLbl);
        card.add(Box.createVerticalStrut(3));
        card.add(roleLbl);
        card.add(Box.createVerticalStrut(16));
        card.add(btnPilih);
 
        outer.add(card);
        return outer;
    }
 
    // ===================================================================
    // FORM CARD (right column)
    // ===================================================================
    private JPanel buildFormCard() {
        JPanel card = roundedCard();
        card.setLayout(new BorderLayout());
 
        // Form body in scroll
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        form.setBorder(new EmptyBorder(24, 28, 24, 28));
 
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;
        g.gridx = 0;
        g.gridy = 0;
        g.insets = new Insets(0, 0, 16, 0);
 
        txtNama      = styledField();
        txtEmail     = styledField();
        txtPassword  = new JPasswordField();
        applyFieldStyle(txtPassword);
        txtTelp      = styledField();
        txtPekerjaan = styledField();
        cmbJK        = styledCombo(new String[]{"L", "P"});
        cmbRole      = styledCombo(new String[]{"pengguna", "pemilik_kos"});
 
        // 2-column grid using nested panels
        form.add(formRow("Nama Lengkap", txtNama, "No. Telepon", txtTelp), g); g.gridy++;
        form.add(formRow("Email", txtEmail, "Pekerjaan", txtPekerjaan), g); g.gridy++;
        form.add(formRow("Password Baru", txtPassword, "Jenis Kelamin", cmbJK), g); g.gridy++;
        form.add(formRow("Role", cmbRole, null, null), g); g.gridy++;
 
        // Spacer
        g.weighty = 1;
        form.add(Box.createVerticalGlue(), g);
 
        card.add(form, BorderLayout.CENTER);
 
        // Footer with save button
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)) {
            @Override protected void paintComponent(Graphics g2) {
                super.paintComponent(g2);
                g2.setColor(C_BORDER);
                g2.fillRect(0, 0, getWidth(), 1);
            }
        };
        footer.setBackground(C_BG_CARD);
        footer.setBorder(new EmptyBorder(14, 24, 14, 24));
 
        JButton btnCancel = new JButton("Batal") {
            private boolean hov = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                public void mouseExited (MouseEvent e) { hov = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g2) {
                Graphics2D g3 = (Graphics2D) g2.create();
                g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g3.setColor(hov ? C_BORDER : Color.WHITE);
                g3.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                g3.setColor(C_BORDER);
                g3.setStroke(new BasicStroke(1f));
                g3.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                g3.dispose();
                super.paintComponent(g2);
            }
            @Override public boolean isOpaque() { return false; }
        };
        btnCancel.setFont(FONT_BODY);
        btnCancel.setForeground(C_TEXT_SEC);
        btnCancel.setContentAreaFilled(false);
        btnCancel.setBorderPainted(false);
        btnCancel.setFocusPainted(false);
        btnCancel.setBorder(new EmptyBorder(9, 22, 9, 22));
        btnCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCancel.addActionListener(e -> dispose());
 
        JButton btnSave = new JButton("Simpan Perubahan") {
            private boolean hov = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                public void mouseExited (MouseEvent e) { hov = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g2) {
                Graphics2D g3 = (Graphics2D) g2.create();
                g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g3.setColor(hov ? C_ACCENT_DARK : C_BG_SIDEBAR);
                g3.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                g3.dispose();
                super.paintComponent(g2);
            }
            @Override public boolean isOpaque() { return false; }
        };
        btnSave.setFont(FONT_BOLD);
        btnSave.setForeground(Color.WHITE);
        btnSave.setContentAreaFilled(false);
        btnSave.setBorderPainted(false);
        btnSave.setFocusPainted(false);
        btnSave.setBorder(new EmptyBorder(9, 24, 9, 24));
        btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSave.addActionListener(e -> simpan());
 
        footer.add(btnCancel);
        footer.add(Box.createHorizontalStrut(10));
        footer.add(btnSave);
        card.add(footer, BorderLayout.SOUTH);
        return card;
    }
 
    /** Two-column form row — pass null for right side to span full width */
    private JPanel formRow(String leftLabel, JComponent leftField,
                           String rightLabel, JComponent rightField) {
        JPanel row = new JPanel(new GridLayout(1, rightLabel != null ? 2 : 1, 20, 0));
        row.setOpaque(false);
        row.add(labeledField(leftLabel, leftField));
        if (rightLabel != null) row.add(labeledField(rightLabel, rightField));
        return row;
    }
 
    private JPanel labeledField(String label, JComponent field) {
        JPanel p = new JPanel(new BorderLayout(0, 6));
        p.setOpaque(false);
 
        JLabel lbl = new JLabel(label.toUpperCase());
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lbl.setForeground(C_ACCENT);
 
        p.add(lbl, BorderLayout.NORTH);
        p.add(field, BorderLayout.CENTER);
        return p;
    }
 
    // ===================================================================
    // DATA
    // ===================================================================
    private void loadData() {
        try {
            Connection conn = koneksi.connect();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM user WHERE id_user=?");
            pst.setInt(1, session.id_user);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                txtNama.setText(rs.getString("nama"));
                txtEmail.setText(rs.getString("email"));
                txtTelp.setText(rs.getString("no_telp"));
                txtPekerjaan.setText(rs.getString("pekerjaan"));
                cmbJK.setSelectedItem(rs.getString("jenis_kelamin"));
                cmbRole.setSelectedItem(rs.getString("role"));
                String foto = rs.getString("foto_profil");
                if (foto != null && !foto.isEmpty()) {
                    Image img = new ImageIcon("E:\\NetBeansProjects\\Kos_TA\\src\\images\\" + foto)
                        .getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    lblFoto.setIcon(new ImageIcon(img));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
 
    private void pilihFoto() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
        selectedFile = chooser.getSelectedFile();
        String fn = selectedFile.getName().toLowerCase();
        if (!fn.endsWith(".jpg") && !fn.endsWith(".jpeg") && !fn.endsWith(".png")) {
            JOptionPane.showMessageDialog(this, "File harus berupa gambar (JPG/PNG)");
            selectedFile = null; return;
        }
        if (selectedFile.length() > 2 * 1024 * 1024) {
            JOptionPane.showMessageDialog(this, "Ukuran gambar maksimal 2MB");
            selectedFile = null; return;
        }
        Image img = new ImageIcon(selectedFile.getAbsolutePath())
            .getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        lblFoto.setIcon(new ImageIcon(img));
        lblFoto.repaint();
    }
 
    private void simpan() {
        try {
            if (txtNama.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong!");
                return;
            }
            if (selectedFile != null && !selectedFile.exists()) {
                JOptionPane.showMessageDialog(this, "File foto tidak ditemukan!");
                return;
            }
 
            Connection conn = koneksi.connect();
            String fileName = null;
            if (selectedFile != null) {
                fileName = System.currentTimeMillis() + "_" + selectedFile.getName();
                File dest = new File("E:\\NetBeansProjects\\Kos_TA\\src\\images\\" + fileName);
                Files.copy(selectedFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
 
            String passwordBaru = new String(txtPassword.getPassword());
            boolean ubahPassword = !passwordBaru.isEmpty();
 
            String sql;
            if (ubahPassword && fileName != null) {
                sql = "UPDATE user SET nama=?,email=?,password=?,no_telp=?,jenis_kelamin=?,pekerjaan=?,role=?,foto_profil=? WHERE id_user=?";
            } else if (ubahPassword) {
                sql = "UPDATE user SET nama=?,email=?,password=?,no_telp=?,jenis_kelamin=?,pekerjaan=?,role=? WHERE id_user=?";
            } else if (fileName != null) {
                sql = "UPDATE user SET nama=?,email=?,no_telp=?,jenis_kelamin=?,pekerjaan=?,role=?,foto_profil=? WHERE id_user=?";
            } else {
                sql = "UPDATE user SET nama=?,email=?,no_telp=?,jenis_kelamin=?,pekerjaan=?,role=? WHERE id_user=?";
            }
 
            PreparedStatement pst = conn.prepareStatement(sql);
            int i = 1;
            pst.setString(i++, txtNama.getText());
            pst.setString(i++, txtEmail.getText());
            if (ubahPassword) pst.setString(i++, hashPassword(passwordBaru));
            pst.setString(i++, txtTelp.getText());
            pst.setString(i++, cmbJK.getSelectedItem().toString());
            pst.setString(i++, txtPekerjaan.getText());
            pst.setString(i++, cmbRole.getSelectedItem().toString());
            if (fileName != null) pst.setString(i++, fileName);
            pst.setInt(i, session.id_user);
            pst.executeUpdate();
 
            // Update session
            session.username = txtNama.getText();
            session.email    = txtEmail.getText();
            session.role     = cmbRole.getSelectedItem().toString();
            if (fileName != null) session.foto = fileName;
 
            int confirm = JOptionPane.showConfirmDialog(this,
                "Profil berhasil disimpan!\nLogout sekarang untuk menerapkan perubahan?",
                "Simpan Berhasil", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                logout();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
 
    private void logout() {
        session.username = null; session.email = null;
        session.role = null; session.foto = null; session.id_user = 0;
        for (Frame frame : Frame.getFrames()) frame.dispose();
        new f_login().setVisible(true);
    }
 
    // ===================================================================
    // HELPERS
    // ===================================================================
    private JPanel roundedCard() {
        return new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(C_BG_CARD);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
                g2.setColor(C_BORDER);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
    }
 
    private JTextField styledField() {
        JTextField f = new JTextField();
        applyFieldStyle(f);
        return f;
    }
 
    private void applyFieldStyle(JComponent f) {
        f.setFont(FONT_BODY);
        f.setForeground(C_TEXT_PRIM);
        f.setBackground(Color.WHITE);
        f.setBorder(new CompoundBorder(
            new LineBorder(C_BORDER, 1, true),
            new EmptyBorder(8, 12, 8, 12)));
        f.setPreferredSize(new Dimension(0, 38));
        // Gold focus border via FocusListener
        f.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                f.setBorder(new CompoundBorder(
                    new LineBorder(C_ACCENT, 1, true),
                    new EmptyBorder(8, 12, 8, 12)));
            }
            @Override public void focusLost(FocusEvent e) {
                f.setBorder(new CompoundBorder(
                    new LineBorder(C_BORDER, 1, true),
                    new EmptyBorder(8, 12, 8, 12)));
            }
        });
    }
 
    private JComboBox<String> styledCombo(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(FONT_BODY);
        cb.setForeground(C_TEXT_PRIM);
        cb.setBackground(Color.WHITE);
        cb.setPreferredSize(new Dimension(0, 38));
        cb.setBorder(new CompoundBorder(
            new LineBorder(C_BORDER, 1, true),
            new EmptyBorder(4, 8, 4, 8)));
        return cb;
    }
 
    public String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                String h = Integer.toHexString(0xff & b);
                if (h.length() == 1) hex.append('0');
                hex.append(h);
            }
            return hex.toString();
        } catch (Exception e) { return null; }
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
        java.awt.EventQueue.invokeLater(() -> new profile_user().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
