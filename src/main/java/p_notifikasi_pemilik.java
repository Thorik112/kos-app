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

public class p_notifikasi_pemilik extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(p_notifikasi_pemilik.class.getName());

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
    private static final Color C_TEXT_PRIM        = new Color(0x1A, 0x1A, 0x1A);
    private static final Color C_TEXT_SEC         = new Color(0x77, 0x77, 0x77);
    private static final Color C_TEXT_SIDEBAR     = new Color(0xB8, 0xC4, 0xCC);
    private static final Color C_BORDER           = new Color(0xE8, 0xE5, 0xDF);
    private static final Color C_HEADER_BG        = new Color(0xFF, 0xFF, 0xFF);
    private static final Color C_SIDEBAR_DIV      = new Color(0x2A, 0x3A, 0x4A);
    private static final Color C_ACTIVE_BG        = new Color(200, 169, 110, 35);
    private static final Color C_GREEN            = new Color(0x2E, 0xA0, 0x6F);
    private static final Color C_GREEN_BG         = new Color(0xE8, 0xF7, 0xF1);
    private static final Color C_GREEN_TEXT       = new Color(0x1A, 0x7A, 0x50);
    private static final Color C_ORANGE           = new Color(0xE0, 0x7D, 0x1A);
    private static final Color C_ORANGE_BG        = new Color(0xFD, 0xF3, 0xE3);
    private static final Color C_ORANGE_TEXT      = new Color(0xA0, 0x58, 0x0C);
    private static final Color C_RED              = new Color(0xDC, 0x35, 0x45);
    private static final Color C_RED_BG           = new Color(0xFD, 0xEE, 0xEE);
    private static final Color C_RED_TEXT         = new Color(0xA0, 0x20, 0x20);
 
    private static final Font FONT_BODY  = new Font("Segoe UI", Font.PLAIN,  13);
    private static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN,  11);
    private static final Font FONT_BOLD  = new Font("Segoe UI", Font.BOLD,   13);
    private static final Font FONT_H1    = new Font("Georgia",  Font.BOLD,   22);

       
    public p_notifikasi_pemilik() {
        
        setTitle("KosApp \u2014 Notifikasi Booking");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        getContentPane().setBackground(C_BG_PAGE);
        setLayout(new BorderLayout());
 
        initUI();
        loadData();
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
 
        JButton btnDash   = sidebarBtn("Dashboard",  "\u2302", false);
        JButton btnKelola = sidebarBtn("Kelola Kos",  "\u25A1", false);
        JButton btnBayar  = sidebarBtn("Pembayaran",  "\u25CE", false);
        JButton btnNotif  = sidebarBtn("Notifikasi",  "\u25CF", true);
 
        btnDash.addActionListener(e   -> { new p_dashboard_pemilik().setVisible(true); dispose(); });
        btnKelola.addActionListener(e -> { new p_kelola_kos().setVisible(true); dispose(); });
        btnBayar.addActionListener(e  -> { new p_pembayaran_pemilik().setVisible(true); dispose(); });
        btnNotif.addActionListener(e  -> { /* already here */ });
 
        nav.add(btnDash);   nav.add(Box.createVerticalStrut(2));
        nav.add(btnKelola); nav.add(Box.createVerticalStrut(2));
        nav.add(btnBayar);  nav.add(Box.createVerticalStrut(2));
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
                    ? String.valueOf(session.username.charAt(0)).toUpperCase() : "P";
                g2.setFont(new Font("Georgia", Font.BOLD, 14));
                g2.setColor(C_BG_SIDEBAR);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(init, (getWidth() - fm.stringWidth(init)) / 2,
                    (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        avatar.setPreferredSize(new Dimension(38, 38));
 
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
        String dn = session.username != null ? session.username : "Pemilik";
        if (dn.length() > 14) dn = dn.substring(0, 13) + "\u2026";
        JLabel uname = new JLabel(dn); uname.setFont(FONT_BOLD); uname.setForeground(Color.WHITE);
        JLabel role  = new JLabel("Pemilik Kos"); role.setFont(FONT_SMALL); role.setForeground(new Color(0x88, 0xA0, 0xB0));
        info.add(uname); info.add(role);
        card.add(avatar, BorderLayout.WEST);
        card.add(info,   BorderLayout.CENTER);
        return card;
    }
 
    private Component hRule() {
        JPanel p = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(C_SIDEBAR_DIV); g.fillRect(26, 0, getWidth() - 52, 1);
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
                    g2.setColor(C_ACTIVE_BG); g2.fillRoundRect(mx, my, bw, bh, 8, 8);
                    g2.setColor(C_ACCENT);    g2.fillRoundRect(0, 10, 4, bh - 8, 3, 3);
                } else if (hov) {
                    g2.setColor(C_BG_SIDEBAR_HOVER); g2.fillRoundRect(mx, my, bw, bh, 8, 8);
                }
                g2.dispose(); super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        btn.setFont(active ? FONT_BOLD : FONT_BODY);
        btn.setForeground(active ? Color.WHITE : C_TEXT_SIDEBAR);
        btn.setContentAreaFilled(false); btn.setBorderPainted(false); btn.setFocusPainted(false);
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
                g.setColor(C_BORDER); g.fillRect(0, getHeight() - 1, getWidth(), 1);
            }
        };
        header.setBackground(C_HEADER_BG);
        header.setBorder(new EmptyBorder(0, 32, 0, 32));
        header.setPreferredSize(new Dimension(0, 68));
 
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 16));
        left.setOpaque(false);
        JLabel title = new JLabel("Notifikasi");
        title.setFont(new Font("Georgia", Font.BOLD, 20)); title.setForeground(C_TEXT_PRIM);
        JLabel crumb = new JLabel("  /  Owner Panel");
        crumb.setFont(FONT_BODY); crumb.setForeground(C_TEXT_SEC);
        left.add(title); left.add(crumb);
 
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 12));
        right.setOpaque(false);
        JLabel greeting = new JLabel("Selamat Datang, " + (session.username != null ? session.username : "Pemilik"));
        greeting.setFont(FONT_BODY); greeting.setForeground(C_TEXT_SEC);
        JLabel av = buildHeaderAvatar();
        av.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        av.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { new profile_user().setVisible(true); }
        });
        right.add(greeting); right.add(av);
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
                    g2.drawImage(img, 1, 1, d, d, null); g2.setClip(null);
                } else {
                    g2.setColor(C_ACCENT); g2.fillOval(1, 1, d, d);
                    String init = session.username != null && !session.username.isEmpty()
                        ? String.valueOf(session.username.charAt(0)).toUpperCase() : "P";
                    g2.setFont(new Font("Georgia", Font.BOLD, 16)); g2.setColor(Color.WHITE);
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(init, 1 + (d - fm.stringWidth(init)) / 2,
                        1 + (d + fm.getAscent() - fm.getDescent()) / 2);
                }
                g2.setColor(C_ACCENT); g2.setStroke(new BasicStroke(2f)); g2.drawOval(1, 1, d, d);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        lbl.setPreferredSize(new Dimension(42, 42));
        return lbl;
    }
 
    // ===================================================================
    // CONTENT
    // ===================================================================
    private JScrollPane buildContent() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(C_BG_PAGE);
        wrapper.setBorder(new EmptyBorder(28, 32, 32, 32));
        wrapper.add(buildSectionHeader(), BorderLayout.NORTH);
 
        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(C_BG_PAGE);
        content.setAlignmentX(LEFT_ALIGNMENT);
        wrapper.add(content, BorderLayout.CENTER);
 
        JScrollPane scroll = new JScrollPane(wrapper);
        scroll.setBorder(null);
        scroll.setBackground(C_BG_PAGE);
        scroll.getViewport().setBackground(C_BG_PAGE);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }
 
    private JPanel buildSectionHeader() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(0, 0, 22, 0));
 
        JLabel t = new JLabel("Notifikasi Booking");
        t.setFont(FONT_H1); t.setForeground(C_TEXT_PRIM); t.setAlignmentX(LEFT_ALIGNMENT);
        JLabel s = new JLabel("Terima atau tolak permintaan booking dari penyewa");
        s.setFont(FONT_BODY); s.setForeground(C_TEXT_SEC); s.setAlignmentX(LEFT_ALIGNMENT);
 
        JPanel deco = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(C_ACCENT); g2.fillRect(0, 1, 38, 3);
                g2.setColor(C_BORDER); g2.fillRect(42, 2, getWidth() - 42, 1);
                g2.dispose();
            }
        };
        deco.setOpaque(false); deco.setAlignmentX(LEFT_ALIGNMENT);
        deco.setMaximumSize(new Dimension(Integer.MAX_VALUE, 6));
        deco.setPreferredSize(new Dimension(0, 6));
 
        p.add(t); p.add(Box.createVerticalStrut(5));
        p.add(s); p.add(Box.createVerticalStrut(12)); p.add(deco);
        return p;
    }
 
    // ===================================================================
    // NOTIF CARD
    // ===================================================================
    private JPanel createNotifCard(int idBooking, String user, String kamar, String kos, String status) {
        Color barColor; Color badgeBg; Color badgeFg; String badgeText;
        switch (status.toLowerCase()) {
            case "confirmed":
                barColor = C_GREEN; badgeBg = C_GREEN_BG; badgeFg = C_GREEN_TEXT; badgeText = "CONFIRMED"; break;
            case "cancelled":
                barColor = C_RED;   badgeBg = C_RED_BG;   badgeFg = C_RED_TEXT;   badgeText = "CANCELLED"; break;
            default:
                barColor = C_ORANGE; badgeBg = C_ORANGE_BG; badgeFg = C_ORANGE_TEXT; badgeText = "PENDING"; break;
        }
 
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(0, 0, 12, 0));
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        wrapper.setAlignmentX(LEFT_ALIGNMENT);
 
        final Color fBar = barColor;
        JPanel card = new JPanel(new BorderLayout(14, 0)) {
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
        card.setBorder(new EmptyBorder(16, 22, 16, 20));
 
        // Icon badge
        JPanel iconBadge = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0xF0, 0xEE, 0xE8));
                g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
                g2.setColor(C_ACCENT); g2.setStroke(new BasicStroke(1.5f));
                g2.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
                g2.dispose(); super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        iconBadge.setPreferredSize(new Dimension(44, 44));
        iconBadge.setLayout(new BorderLayout());
        JLabel iconLbl = new JLabel("\u25A1", SwingConstants.CENTER);
        iconLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        iconLbl.setForeground(C_ACCENT);
        iconBadge.add(iconLbl);
 
        // Info
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
 
        JLabel lblUser = new JLabel((user != null ? user : "-") + " memesan kamar");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUser.setForeground(C_TEXT_PRIM);
        lblUser.setAlignmentX(LEFT_ALIGNMENT);
 
        JLabel lblDetail = new JLabel("Kamar " + kamar + "  \u2022  " + kos);
        lblDetail.setFont(FONT_SMALL);
        lblDetail.setForeground(C_TEXT_SEC);
        lblDetail.setAlignmentX(LEFT_ALIGNMENT);
 
        info.add(lblUser); info.add(Box.createVerticalStrut(5)); info.add(lblDetail);
 
        // Right
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
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setForeground(fBadgeFg);
        badge.setBorder(new EmptyBorder(5, 14, 5, 14));
        badge.setAlignmentX(RIGHT_ALIGNMENT);
 
        boolean isPending = status.equalsIgnoreCase("pending");
        JButton btnTerima = darkButton("Terima");
        JButton btnTolak  = dangerButton("Tolak");
        btnTerima.setEnabled(isPending);
        btnTolak.setEnabled(isPending);
 
        btnTerima.addActionListener(e -> updateStatus(idBooking, "confirmed"));
        btnTolak.addActionListener(e  -> updateStatus(idBooking, "cancelled"));
 
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        btnRow.setOpaque(false);
        btnRow.add(btnTerima); btnRow.add(btnTolak);
 
        right.add(badge); right.add(Box.createVerticalStrut(10)); right.add(btnRow);
 
        card.add(iconBadge, BorderLayout.WEST);
        card.add(info,      BorderLayout.CENTER);
        card.add(right,     BorderLayout.EAST);
 
        wrapper.add(card);
        return wrapper;
    }
 
    // ===================================================================
    // BUTTONS
    // ===================================================================
    private JButton darkButton(String text) {
        JButton btn = new JButton(text) {
            private boolean hov = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                public void mouseExited (MouseEvent e) { hov = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(isEnabled() ? (hov ? C_ACCENT_DARK : C_BG_SIDEBAR) : new Color(0xCC, 0xCC, 0xCC));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose(); super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        btn.setFont(FONT_BOLD); btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false); btn.setBorderPainted(false); btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(7, 14, 7, 14));
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
                if (isEnabled()) {
                    g2.setColor(hov ? C_RED : C_RED_BG);
                } else {
                    g2.setColor(new Color(0xCC, 0xCC, 0xCC));
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose(); super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        btn.setFont(FONT_BOLD); btn.setForeground(C_RED_TEXT);
        btn.setContentAreaFilled(false); btn.setBorderPainted(false); btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(7, 14, 7, 14));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
 
    // ===================================================================
    // LOAD DATA
    // ===================================================================
    private void loadData() {
        content.removeAll();
        try {
            Connection conn = koneksi.connect();
            String sql =
                "SELECT b.id_booking, u.nama, k.nomor_kamar, kos.nama_kos, b.status_booking " +
                "FROM booking b " +
                "JOIN user u   ON b.id_user  = u.id_user " +
                "JOIN kamar k  ON b.id_kamar = k.id_kamar " +
                "JOIN kos      ON k.id_kos   = kos.id_kos " +
                "WHERE kos.id_user = ? " +
                "ORDER BY b.id_booking DESC";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, session.id_user);
            ResultSet rs = pst.executeQuery();
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                content.add(createNotifCard(
                    rs.getInt("id_booking"),
                    rs.getString("nama"),
                    rs.getString("nomor_kamar"),
                    rs.getString("nama_kos"),
                    rs.getString("status_booking")
                ));
            }
            if (!hasData) {
                JLabel empty = new JLabel("Belum ada notifikasi booking.");
                empty.setFont(FONT_BODY); empty.setForeground(C_TEXT_SEC);
                empty.setAlignmentX(LEFT_ALIGNMENT);
                content.add(empty);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        content.add(Box.createVerticalGlue());
        content.revalidate(); content.repaint();
    }
 
    private void updateStatus(int idBooking, String status) {
        try {
            Connection conn = koneksi.connect();
            String sql = "UPDATE booking SET status_booking=? WHERE id_booking=? AND status_booking='pending'";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, status); pst.setInt(2, idBooking);
            int rows = pst.executeUpdate();
            JOptionPane.showMessageDialog(this, rows > 0 ? "Status berhasil diperbarui." : "Sudah diproses sebelumnya.");
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
 
    private void logout() {
        session.username = null; session.email = null; session.role = null;
        session.foto = null; session.id_user = 0;
        new f_login().setVisible(true); this.dispose();
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
        java.awt.EventQueue.invokeLater(() -> new p_notifikasi_pemilik().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
