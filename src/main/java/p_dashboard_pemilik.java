/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.sql.*;

public class p_dashboard_pemilik extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(p_dashboard_pemilik.class.getName());

    // ===== INSTANCE FIELDS =====
    private JLabel lblTotalKos, lblTotalKamar, lblTersedia, lblPendapatan;
    private JPanel content;
 
    // ===================================================================
    // DESIGN TOKENS
    // ===================================================================
    private static final Color C_BG_PAGE          = new Color(0xF5, 0xF4, 0xF0);
    private static final Color C_BG_CARD          = new Color(0xFF, 0xFF, 0xFF);
    private static final Color C_BG_SIDEBAR       = new Color(0x0F, 0x19, 0x23);
    private static final Color C_BG_SIDEBAR_HOVER = new Color(0x1E, 0x2D, 0x3D);
    private static final Color C_ACCENT           = new Color(0xC8, 0xA9, 0x6E);
    private static final Color C_ACCENT_DARK      = new Color(0xA8, 0x87, 0x3E);
    private static final Color C_ACCENT_LIGHT     = new Color(0xF5, 0xEE, 0xDC);
    private static final Color C_TEXT_PRIM        = new Color(0x1A, 0x1A, 0x1A);
    private static final Color C_TEXT_SEC         = new Color(0x77, 0x77, 0x77);
    private static final Color C_TEXT_SIDEBAR     = new Color(0xB8, 0xC4, 0xCC);
    private static final Color C_BORDER           = new Color(0xE8, 0xE5, 0xDF);
    private static final Color C_HEADER_BG        = new Color(0xFF, 0xFF, 0xFF);
    private static final Color C_SIDEBAR_DIV      = new Color(0x2A, 0x3A, 0x4A);
    private static final Color C_ACTIVE_BG        = new Color(200, 169, 110, 35);
    private static final Color C_GREEN            = new Color(0x2E, 0xA0, 0x6F);
    private static final Color C_GREEN_BG         = new Color(0xE8, 0xF7, 0xF1);
    private static final Color C_BLUE             = new Color(0x2D, 0x7D, 0xD9);
    private static final Color C_BLUE_BG          = new Color(0xE8, 0xF2, 0xFB);
    private static final Color C_ORANGE           = new Color(0xE0, 0x7D, 0x1A);
    private static final Color C_ORANGE_BG        = new Color(0xFD, 0xF3, 0xE3);
 
    private static final Font FONT_BODY    = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_SMALL   = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font FONT_BOLD    = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FONT_H1      = new Font("Georgia",  Font.BOLD,  22);
    private static final Font FONT_STAT    = new Font("Georgia",  Font.BOLD,  28);
    
    public p_dashboard_pemilik() {
        setTitle("KosApp \u2014 Owner Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        getContentPane().setBackground(C_BG_PAGE);
        setLayout(new BorderLayout());
 
        initUI();
        loadStatistik();
        loadKos();
    }
    
    private void initUI() {
        add(buildSidebar(), BorderLayout.WEST);
 
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(C_BG_PAGE);
        main.add(buildHeader(),  BorderLayout.NORTH);
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
 
        // ── Logo ──────────────────────────────────────────────────────
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
        logoPanel.add(lKos);
        logoPanel.add(lApp);
        sidebar.add(logoPanel);
        sidebar.add(hRule());
 
        // ── Nav ───────────────────────────────────────────────────────
        JPanel nav = new JPanel();
        nav.setOpaque(false);
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        nav.setBorder(new EmptyBorder(16, 0, 0, 0));
        nav.setAlignmentX(LEFT_ALIGNMENT);
        navLabel(nav, "NAVIGASI");
 
        JButton btnDash   = sidebarBtn("Dashboard",  "\u2302", true);
        JButton btnKelola = sidebarBtn("Kelola Kos",  "\u25A1", false);
        JButton btnBayar  = sidebarBtn("Pembayaran",  "\u25CE", false);
        JButton btnNotif  = sidebarBtn("Notifikasi",  "\u25CF", false);
 
        btnDash.addActionListener(e   -> { new p_dashboard_pemilik().setVisible(true); dispose(); });
        btnKelola.addActionListener(e -> { new p_kelola_kos().setVisible(true); dispose(); });
        btnBayar.addActionListener(e  -> { new p_pembayaran_pemilik().setVisible(true); dispose(); });
        btnNotif.addActionListener(e  -> new p_notifikasi_pemilik().setVisible(true));
 
        nav.add(btnDash);   nav.add(Box.createVerticalStrut(2));
        nav.add(btnKelola); nav.add(Box.createVerticalStrut(2));
        nav.add(btnBayar);  nav.add(Box.createVerticalStrut(2));
        nav.add(btnNotif);
        sidebar.add(nav);
        sidebar.add(Box.createVerticalGlue());
 
        // ── User card ─────────────────────────────────────────────────
        sidebar.add(sidebarUserCard());
 
        // ── Logout ────────────────────────────────────────────────────
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
                    ? String.valueOf(session.username.charAt(0)).toUpperCase() : "P";
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
 
        String displayName = session.username != null ? session.username : "Pemilik";
        if (displayName.length() > 14) displayName = displayName.substring(0, 13) + "\u2026";
        JLabel uname = new JLabel(displayName);
        uname.setFont(FONT_BOLD);
        uname.setForeground(Color.WHITE);
 
        JLabel role = new JLabel("Pemilik Kos");
        role.setFont(FONT_SMALL);
        role.setForeground(new Color(0x88, 0xA0, 0xB0));
 
        info.add(uname);
        info.add(role);
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
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hov = false; repaint(); }
                });
            }
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
        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Georgia", Font.BOLD, 20));
        title.setForeground(C_TEXT_PRIM);
        JLabel crumb = new JLabel("  /  Owner Panel");
        crumb.setFont(FONT_BODY);
        crumb.setForeground(C_TEXT_SEC);
        left.add(title); left.add(crumb);
 
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 12));
        right.setOpaque(false);
        JLabel greeting = new JLabel("Selamat Datang, "
            + (session.username != null ? session.username : "Pemilik"));
        greeting.setFont(FONT_BODY);
        greeting.setForeground(C_TEXT_SEC);
        JLabel avatarLbl = buildHeaderAvatar();
        avatarLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        avatarLbl.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { new profile_user().setVisible(true); }
        });
        right.add(greeting); right.add(avatarLbl);
 
        header.add(left,  BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);
        return header;
    }
 
    private JLabel buildHeaderAvatar() {
        JLabel lbl = new JLabel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                int d = Math.min(getWidth(), getHeight()) - 2;
                if (session.foto != null && !session.foto.isEmpty()) {
                    Image img = new ImageIcon("E:\\NetBeansProjects\\Kos_TA\\src\\images\\" + session.foto).getImage();
                    g2.setClip(new Ellipse2D.Float(1, 1, d, d));
                    g2.drawImage(img, 1, 1, d, d, null);
                    g2.setClip(null);
                } else {
                    g2.setColor(C_ACCENT);
                    g2.fillOval(1, 1, d, d);
                    String init = session.username != null && !session.username.isEmpty()
                        ? String.valueOf(session.username.charAt(0)).toUpperCase() : "P";
                    g2.setFont(new Font("Georgia", Font.BOLD, 16));
                    g2.setColor(Color.WHITE);
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(init,
                        1 + (d - fm.stringWidth(init)) / 2,
                        1 + (d + fm.getAscent() - fm.getDescent()) / 2);
                }
                g2.setColor(C_ACCENT);
                g2.setStroke(new BasicStroke(2f));
                g2.drawOval(1, 1, d, d);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        lbl.setPreferredSize(new Dimension(42, 42));
        return lbl;
    }
 
    // ===================================================================
    // CONTENT (scrollable body)
    // ===================================================================
    private JScrollPane buildContent() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(C_BG_PAGE);
        wrapper.setBorder(new EmptyBorder(28, 32, 32, 32));
 
        // Section header
        wrapper.add(buildSectionHeader(), BorderLayout.NORTH);
 
        // Body: stat row + kos section label + kos grid
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(C_BG_PAGE);
 
        body.add(buildStatRow());
        body.add(Box.createVerticalStrut(28));
        body.add(buildKosSectionLabel());
        body.add(Box.createVerticalStrut(14));
 
        content = new JPanel(new GridLayout(0, 3, 18, 18));
        content.setBackground(C_BG_PAGE);
        content.setAlignmentX(LEFT_ALIGNMENT);
        body.add(content);
 
        wrapper.add(body, BorderLayout.CENTER);
 
        JScrollPane scroll = new JScrollPane(wrapper);
        scroll.setBorder(null);
        scroll.setBackground(C_BG_PAGE);
        scroll.getViewport().setBackground(C_BG_PAGE);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }
 
    // ── Page section header ───────────────────────────────────────────
    private JPanel buildSectionHeader() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(0, 0, 22, 0));
 
        JLabel t = new JLabel("Ringkasan Properti");
        t.setFont(FONT_H1);
        t.setForeground(C_TEXT_PRIM);
        t.setAlignmentX(LEFT_ALIGNMENT);
 
        JLabel s = new JLabel("Pantau statistik dan daftar kos Anda");
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
        p.add(Box.createVerticalStrut(12));
        p.add(deco);
        return p;
    }
 
    // ── Stat row (4 cards) ────────────────────────────────────────────
    private JPanel buildStatRow() {
        JPanel row = new JPanel(new GridLayout(1, 4, 16, 0));
        row.setBackground(C_BG_PAGE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        row.setAlignmentX(LEFT_ALIGNMENT);
 
        lblTotalKos   = new JLabel("0");
        lblTotalKamar = new JLabel("0");
        lblTersedia   = new JLabel("0");
        lblPendapatan = new JLabel("-");
 
        row.add(createStatCard("Total Kos",   lblTotalKos,   "\u2302", C_ACCENT,  C_ACCENT_LIGHT));
        row.add(createStatCard("Total Kamar", lblTotalKamar, "\u25A1", C_BLUE,    C_BLUE_BG));
        row.add(createStatCard("Tersedia",    lblTersedia,   "\u25CE", C_GREEN,   C_GREEN_BG));
        row.add(createStatCard("Pendapatan",  lblPendapatan, "\u25B2", C_ORANGE,  C_ORANGE_BG));
        return row;
    }
 
    private JPanel createStatCard(String titleText, JLabel valueLabel,
                                  String iconChar, Color accentClr, Color bgClr) {
        JPanel card = new JPanel(new BorderLayout()) {
            private boolean hov = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hov = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(C_BG_CARD);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
                g2.setColor(hov ? accentClr : C_BORDER);
                g2.setStroke(new BasicStroke(hov ? 1.5f : 1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
                // Left accent bar
                g2.setColor(accentClr);
                g2.setStroke(new BasicStroke(1f));
                g2.fillRoundRect(0, 18, 4, getHeight() - 36, 3, 3);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        card.setBorder(new EmptyBorder(16, 20, 16, 16));
 
        // Icon badge top-right
        JLabel iconLbl = new JLabel(iconChar) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgClr);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        iconLbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        iconLbl.setForeground(accentClr);
        iconLbl.setHorizontalAlignment(SwingConstants.CENTER);
        iconLbl.setPreferredSize(new Dimension(34, 34));
 
        JPanel iconWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        iconWrap.setOpaque(false);
        iconWrap.add(iconLbl);
 
        // Text
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
 
        JLabel lblTitle = new JLabel(titleText);
        lblTitle.setFont(FONT_SMALL);
        lblTitle.setForeground(C_TEXT_SEC);
        lblTitle.setAlignmentX(LEFT_ALIGNMENT);
 
        valueLabel.setFont(FONT_STAT);
        valueLabel.setForeground(C_TEXT_PRIM);
        valueLabel.setAlignmentX(LEFT_ALIGNMENT);
 
        textPanel.add(lblTitle);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(valueLabel);
 
        card.add(iconWrap,  BorderLayout.NORTH);
        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }
 
    // ── Kos section label with action button ─────────────────────────
    private JPanel buildKosSectionLabel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        p.setAlignmentX(LEFT_ALIGNMENT);
 
        JLabel lbl = new JLabel("Daftar Kos Saya");
        lbl.setFont(new Font("Georgia", Font.BOLD, 16));
        lbl.setForeground(C_TEXT_PRIM);
 
        JButton btnTambah = accentButton("+ Tambah Kos");
        btnTambah.addActionListener(e -> new p_kelola_kos().setVisible(true));
 
        p.add(lbl,       BorderLayout.WEST);
        p.add(btnTambah, BorderLayout.EAST);
        return p;
    }
 
    // ===================================================================
    // KOS CARD (modern, dark top bar + gold dot pattern)
    // ===================================================================
    private JPanel createKosCard(int idKos, String nama, String kota) {
        JPanel wrap = new JPanel(new BorderLayout()) {
            @Override public boolean isOpaque() { return false; }
        };
        wrap.setOpaque(false);
        wrap.setBorder(new EmptyBorder(0, 0, 8, 8));
        wrap.setPreferredSize(new Dimension(0, 190));
 
        JPanel card = new JPanel(new BorderLayout()) {
            private boolean hov = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hov = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth() - 5, h = getHeight() - 5;
                if (hov) {
                    for (int i = 4; i >= 1; i--) {
                        g2.setColor(new Color(0, 0, 0, 5));
                        g2.fillRoundRect(i, i + 1, w, h, 14, 14);
                    }
                }
                g2.setColor(C_BG_CARD);
                g2.fillRoundRect(0, 0, w, h, 14, 14);
                g2.setColor(hov ? C_ACCENT : C_BORDER);
                g2.setStroke(new BasicStroke(hov ? 1.5f : 1f));
                g2.drawRoundRect(0, 0, w, h, 14, 14);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
 
        // ── Dark top bar with dot pattern ─────────────────────────────
        JPanel topBar = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Round only top corners
                g2.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight() + 14, 14, 14));
                g2.setColor(C_BG_SIDEBAR);
                g2.fillRect(0, 0, getWidth(), getHeight());
                // Subtle gold dot pattern
                g2.setColor(new Color(200, 169, 110, 40));
                for (int x = 14; x < getWidth(); x += 20)
                    for (int y = 8; y < getHeight(); y += 12)
                        g2.fillOval(x, y, 3, 3);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        topBar.setPreferredSize(new Dimension(0, 56));
        topBar.setBorder(new EmptyBorder(10, 16, 10, 16));
 
        JLabel nameLbl = new JLabel(truncate(nama, 22));
        nameLbl.setFont(new Font("Georgia", Font.BOLD, 13));
        nameLbl.setForeground(Color.WHITE);
 
        // City badge
        JLabel cityLbl = new JLabel(kota);
        cityLbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        cityLbl.setForeground(C_ACCENT);
        JPanel cityBadge = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(15, 25, 35, 200));
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        cityBadge.add(cityLbl);
        cityBadge.setBorder(new EmptyBorder(3, 8, 3, 8));
 
        topBar.add(nameLbl,   BorderLayout.CENTER);
        topBar.add(cityBadge, BorderLayout.EAST);
 
        // ── Body ─────────────────────────────────────────────────────
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setOpaque(false);
        body.setBorder(new EmptyBorder(12, 16, 0, 16));
 
        JLabel namaRow = new JLabel("\u2302  " + truncate(nama, 26));
        namaRow.setFont(new Font("Segoe UI", Font.BOLD, 13));
        namaRow.setForeground(C_TEXT_PRIM);
        namaRow.setAlignmentX(LEFT_ALIGNMENT);
 
        JLabel kotaRow = new JLabel("\u25CF  " + kota);
        kotaRow.setFont(FONT_SMALL);
        kotaRow.setForeground(C_TEXT_SEC);
        kotaRow.setAlignmentX(LEFT_ALIGNMENT);
 
        body.add(namaRow);
        body.add(Box.createVerticalStrut(5));
        body.add(kotaRow);
 
        // ── Footer ───────────────────────────────────────────────────
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(10, 14, 14, 14));
 
        JButton btnKelola = darkButton("Kelola Kamar");
        btnKelola.addActionListener(e -> new p_kelola_kamar(idKos).setVisible(true));
        footer.add(btnKelola, BorderLayout.CENTER);
 
        card.add(topBar, BorderLayout.NORTH);
        card.add(body,   BorderLayout.CENTER);
        card.add(footer, BorderLayout.SOUTH);
 
        wrap.add(card);
        return wrap;
    }
 
    // ===================================================================
    // BUTTONS
    // ===================================================================
    private JButton darkButton(String text) {
        JButton btn = new JButton(text) {
            private boolean hov = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hov = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? C_ACCENT_DARK : C_BG_SIDEBAR);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        btn.setFont(FONT_BOLD);
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 0, 8, 0));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
 
    private JButton accentButton(String text) {
        JButton btn = new JButton(text) {
            private boolean hov = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hov = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? C_ACCENT_DARK : C_ACCENT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        btn.setFont(FONT_BOLD);
        btn.setForeground(C_BG_SIDEBAR);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(7, 18, 7, 18));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
 
    // ===================================================================
    // LOAD DATA
    // ===================================================================
    private void loadStatistik() {
        try {
            Connection conn = koneksi.connect();
 
            PreparedStatement pst1 = conn.prepareStatement(
                    "SELECT COUNT(*) FROM kos WHERE id_user=?");
            pst1.setInt(1, session.id_user);
            ResultSet rs1 = pst1.executeQuery();
            if (rs1.next()) lblTotalKos.setText(String.valueOf(rs1.getInt(1)));
 
            PreparedStatement pst2 = conn.prepareStatement(
                    "SELECT COUNT(*) FROM kamar k JOIN kos ON k.id_kos=kos.id_kos WHERE kos.id_user=?");
            pst2.setInt(1, session.id_user);
            ResultSet rs2 = pst2.executeQuery();
            if (rs2.next()) lblTotalKamar.setText(String.valueOf(rs2.getInt(1)));
 
            PreparedStatement pst3 = conn.prepareStatement(
                    "SELECT COUNT(*) FROM kamar k JOIN kos ON k.id_kos=kos.id_kos " +
                    "WHERE kos.id_user=? AND status_kamar='tersedia'");
            pst3.setInt(1, session.id_user);
            ResultSet rs3 = pst3.executeQuery();
            if (rs3.next()) lblTersedia.setText(String.valueOf(rs3.getInt(1)));
 
            // Total pendapatan dari pembayaran lunas
            PreparedStatement pst4 = conn.prepareStatement(
                    "SELECT COALESCE(SUM(p.total_bayar), 0) FROM pembayaran p " +
                    "JOIN booking b ON p.id_booking = b.id_booking " +
                    "JOIN kamar k   ON b.id_kamar   = k.id_kamar " +
                    "JOIN kos       ON k.id_kos      = kos.id_kos " +
                    "WHERE kos.id_user=? AND p.status_pembayaran='lunas'");
            pst4.setInt(1, session.id_user);
            ResultSet rs4 = pst4.executeQuery();
            if (rs4.next()) {
                long total = rs4.getLong(1);
                lblPendapatan.setText("Rp " + formatHarga(String.valueOf(total)));
                lblPendapatan.setFont(new Font("Georgia", Font.BOLD, total > 0 ? 18 : 28));
            }
 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
 
    private void loadKos() {
        content.removeAll();
        try {
            Connection conn = koneksi.connect();
            PreparedStatement pst = conn.prepareStatement(
                    "SELECT * FROM kos WHERE id_user=?");
            pst.setInt(1, session.id_user);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                content.add(createKosCard(
                        rs.getInt("id_kos"),
                        rs.getString("nama_kos"),
                        rs.getString("kota")
                ));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        content.revalidate();
        content.repaint();
    }
 
    // ===================================================================
    // UTILITIES
    // ===================================================================
    private String formatHarga(String raw) {
        try {
            long val = Long.parseLong(raw.replaceAll("[^0-9]", ""));
            return String.format("%,d", val).replace(',', '.');
        } catch (Exception e) { return raw; }
    }
 
    private String truncate(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, max - 1) + "\u2026";
    }
 
    // ===================================================================
    // LOGOUT
    // ===================================================================
    private void logout() {
        session.username = null;
        session.email    = null;
        session.role     = null;
        session.foto     = null;
        session.id_user  = 0;
        new f_login().setVisible(true);
        this.dispose();
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
        java.awt.EventQueue.invokeLater(() -> new p_dashboard_pemilik().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
