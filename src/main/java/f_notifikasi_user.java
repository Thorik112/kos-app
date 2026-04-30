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

public class f_notifikasi_user extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(f_notifikasi_user.class.getName());

    // ===== DESIGN TOKENS (identik dengan semua halaman lain) =====
    private static final Color C_BG_PAGE          = new Color(0xF5, 0xF4, 0xF0);
    private static final Color C_BG_CARD          = new Color(0xFF, 0xFF, 0xFF);
    private static final Color C_BG_SIDEBAR       = new Color(0x0F, 0x19, 0x23);
    private static final Color C_BG_SIDEBAR_HOVER = new Color(0x1E, 0x2D, 0x3D);
    private static final Color C_ACCENT           = new Color(0xC8, 0xA9, 0x6E);
    private static final Color C_TEXT_PRIM        = new Color(0x1A, 0x1A, 0x1A);
    private static final Color C_TEXT_SEC         = new Color(0x77, 0x77, 0x77);
    private static final Color C_TEXT_SIDEBAR     = new Color(0xB8, 0xC4, 0xCC);
    private static final Color C_BORDER           = new Color(0xE8, 0xE5, 0xDF);
    private static final Color C_HEADER_BG        = new Color(0xFF, 0xFF, 0xFF);
    private static final Color C_SIDEBAR_DIV      = new Color(0x2A, 0x3A, 0x4A);
    private static final Color C_ACTIVE_BG        = new Color(200, 169, 110, 35);
 
    // Notif status colors
    private static final Color C_CONFIRM_FG  = new Color(0x2E, 0x7D, 0x52);
    private static final Color C_CONFIRM_BG  = new Color(0xD1, 0xEC, 0xDF);
    private static final Color C_CONFIRM_BAR = new Color(0x2E, 0x7D, 0x52);
    private static final Color C_CANCEL_FG   = new Color(0xB0, 0x30, 0x30);
    private static final Color C_CANCEL_BG   = new Color(0xF8, 0xD7, 0xD7);
    private static final Color C_CANCEL_BAR  = new Color(0xB0, 0x30, 0x30);
 
    private static final Font FONT_BODY  = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font FONT_BOLD  = new Font("Segoe UI", Font.BOLD,  13);
 
    private JPanel contentPanel;
    private Timer  autoRefreshTimer;
    
    public f_notifikasi_user() {
        setTitle("KosApp \u2014 Notifikasi");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        getContentPane().setBackground(C_BG_PAGE);
 
        initUI();
        loadData();
 
        // Auto-refresh every 5 seconds
        autoRefreshTimer = new Timer(5000, e -> loadData());
        autoRefreshTimer.start();
 
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (autoRefreshTimer != null) autoRefreshTimer.stop();
            }
        });
    }
    
    private void initUI() {
        setLayout(new BorderLayout());
        add(buildSidebar(), BorderLayout.WEST);
 
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(C_BG_PAGE);
        main.add(buildHeader(),  BorderLayout.NORTH);
        main.add(buildContent(), BorderLayout.CENTER);
        add(main, BorderLayout.CENTER);
    }
 
    // ===================================================================
    // SIDEBAR  ("Notifikasi" active)
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
 
        // Logo
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
 
        // Nav
        JPanel nav = new JPanel();
        nav.setOpaque(false);
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        nav.setBorder(new EmptyBorder(16, 0, 0, 0));
        nav.setAlignmentX(LEFT_ALIGNMENT);
        navLabel(nav, "NAVIGASI");
 
        JButton btnDash    = sidebarBtn("Dashboard",       "\u2302", false);
        JButton btnCari    = sidebarBtn("Cari Kos",        "\u25A2", false);
        JButton btnRiwayat = sidebarBtn("Riwayat Booking", "\u2630", false);
        JButton btnNotif   = sidebarBtn("Notifikasi",      "\u25CE", true);   // ACTIVE
 
        btnDash.addActionListener(e    -> { new home_user().setVisible(true);       dispose(); });
        btnCari.addActionListener(e    -> { new cari_kos().setVisible(true);        dispose(); });
        btnRiwayat.addActionListener(e -> { new f_riwayat_booking().setVisible(true); dispose(); });
        btnNotif.addActionListener(e   -> { /* already here */ });
 
        nav.add(btnDash);    nav.add(Box.createVerticalStrut(2));
        nav.add(btnCari);    nav.add(Box.createVerticalStrut(2));
        nav.add(btnRiwayat); nav.add(Box.createVerticalStrut(2));
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
        if (displayName.length() > 14) displayName = displayName.substring(0, 13) + "\u2026";
        JLabel uname = new JLabel(displayName);
        uname.setFont(FONT_BOLD); uname.setForeground(Color.WHITE);
        JLabel role = new JLabel("Penyewa");
        role.setFont(FONT_SMALL); role.setForeground(new Color(0x88, 0xA0, 0xB0));
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
        JLabel pageTitle = new JLabel("Notifikasi");
        pageTitle.setFont(new Font("Georgia", Font.BOLD, 20));
        pageTitle.setForeground(C_TEXT_PRIM);
        JLabel crumb = new JLabel("  /  Pembaruan Status");
        crumb.setFont(FONT_BODY);
        crumb.setForeground(C_TEXT_SEC);
        left.add(pageTitle); left.add(crumb);
 
        // Right: refresh indicator
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 12));
        right.setOpaque(false);
 
        JLabel dot = new JLabel("\u25CF");    // live dot
        dot.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        dot.setForeground(C_CONFIRM_FG);
 
        JLabel liveLabel = new JLabel("Auto-refresh aktif");
        liveLabel.setFont(FONT_SMALL);
        liveLabel.setForeground(C_TEXT_SEC);
 
        JLabel greeting = new JLabel("Hi, " + (session.username != null ? session.username : "User"));
        greeting.setFont(FONT_BODY);
        greeting.setForeground(C_TEXT_SEC);
 
        right.add(dot);
        right.add(liveLabel);
        right.add(Box.createHorizontalStrut(16));
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
        wrapper.setBorder(new EmptyBorder(28, 32, 32, 32));
        wrapper.add(buildSectionHeader(), BorderLayout.NORTH);
 
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(C_BG_PAGE);
        wrapper.add(contentPanel, BorderLayout.CENTER);
 
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
        t.setFont(new Font("Georgia", Font.BOLD, 22));
        t.setForeground(C_TEXT_PRIM);
        t.setAlignmentX(LEFT_ALIGNMENT);
 
        JLabel s = new JLabel("Status terbaru dari booking Anda");
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
    // LOAD DATA
    // ===================================================================
    private void loadData() {
        contentPanel.removeAll();
 
        try {
            Connection conn = koneksi.connect();
            String sql =
                "SELECT b.id_booking, k.nomor_kamar, kos.nama_kos, b.status_booking " +
                "FROM booking b " +
                "JOIN kamar k ON b.id_kamar = k.id_kamar " +
                "JOIN kos ON k.id_kos = kos.id_kos " +
                "WHERE b.id_user = ? AND b.status_booking != 'pending' " +
                "ORDER BY b.id_booking DESC";
 
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, session.id_user);
            ResultSet rs = pst.executeQuery();
 
            boolean any = false;
            while (rs.next()) {
                any = true;
                contentPanel.add(createNotifCard(
                    rs.getInt("id_booking"),
                    rs.getString("nomor_kamar"),
                    rs.getString("nama_kos"),
                    rs.getString("status_booking")));
                contentPanel.add(Box.createVerticalStrut(10));
            }
            if (!any) contentPanel.add(emptyState());
 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
 
        contentPanel.revalidate();
        contentPanel.repaint();
    }
 
    // ===================================================================
    // NOTIFIKASI CARD
    // ===================================================================
    private JPanel createNotifCard(int id, String kamar, String kos, String status) {
        boolean isConfirm = "confirmed".equalsIgnoreCase(status);
 
        Color barColor = isConfirm ? C_CONFIRM_BAR : C_CANCEL_BAR;
        Color bgColor  = isConfirm ? C_CONFIRM_BG  : C_CANCEL_BG;
        Color fgColor  = isConfirm ? C_CONFIRM_FG  : C_CANCEL_FG;
        String iconStr = isConfirm ? "\u2713" : "\u2715";   // ✓ or ✕
        String msgText = isConfirm
            ? "Booking kamar <b>" + kamar + "</b> di <b>" + kos + "</b> telah <b>dikonfirmasi</b>"
            : "Booking kamar <b>" + kamar + "</b> di <b>" + kos + "</b> telah <b>dibatalkan</b>";
        String subText = isConfirm
            ? "Selamat! Silakan lanjutkan ke pembayaran."
            : "Mohon maaf, booking Anda tidak dapat diproses.";
 
        // Shadow wrapper
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);
        wrap.setAlignmentX(LEFT_ALIGNMENT);
        wrap.setBorder(new EmptyBorder(0, 0, 4, 6));
 
        // Card
        JPanel card = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // subtle shadow
                for (int i = 3; i >= 1; i--) {
                    g2.setColor(new Color(0, 0, 0, 4));
                    g2.fillRoundRect(i, i + 1, getWidth() - i * 2, getHeight() - i * 2, 14, 14);
                }
                // card bg — tinted by status
                g2.setColor(C_BG_CARD);
                g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 14, 14);
                // status-colored left tint strip
                g2.setColor(new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), 80));
                g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 14, 14);
                // border
                g2.setColor(new Color(barColor.getRed(), barColor.getGreen(), barColor.getBlue(), 60));
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 14, 14);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 82));
 
        // ── LEFT: accent bar ──────────────────────────────────────────
        JPanel accentBar = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(barColor);
                g2.fillRoundRect(0, 4, getWidth() + 8, getHeight() - 8, 14, 14);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        accentBar.setPreferredSize(new Dimension(5, 0));
 
        // ── ICON circle ───────────────────────────────────────────────
        final Color finalFg  = fgColor;
        final Color finalBar = barColor;
        final String finalIcon = iconStr;
 
        JPanel iconCircle = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // circle bg
                g2.setColor(new Color(finalBar.getRed(), finalBar.getGreen(), finalBar.getBlue(), 22));
                g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
                // icon
                g2.setColor(finalFg);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(finalIcon,
                    (getWidth()  - fm.stringWidth(finalIcon)) / 2,
                    (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        iconCircle.setPreferredSize(new Dimension(40, 40));
 
        JPanel iconWrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        iconWrap.setOpaque(false);
        iconWrap.setBorder(new EmptyBorder(20, 16, 20, 8));
        iconWrap.add(iconCircle);
 
        // ── TEXT section ──────────────────────────────────────────────
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.setBorder(new EmptyBorder(16, 4, 16, 16));
 
        JLabel msgLabel = new JLabel("<html>" + msgText + "</html>");
        msgLabel.setFont(FONT_BODY);
        msgLabel.setForeground(C_TEXT_PRIM);
        msgLabel.setAlignmentX(LEFT_ALIGNMENT);
 
        JLabel subLabel = new JLabel(subText);
        subLabel.setFont(FONT_SMALL);
        subLabel.setForeground(C_TEXT_SEC);
        subLabel.setAlignmentX(LEFT_ALIGNMENT);
 
        textPanel.add(msgLabel);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(subLabel);
 
        // ── RIGHT: booking ID chip ────────────────────────────────────
        JLabel idChip = new JLabel(" #" + id + " ") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0xF0, 0xEE, 0xE8));
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        idChip.setFont(FONT_SMALL);
        idChip.setForeground(C_TEXT_SEC);
        idChip.setBorder(new EmptyBorder(3, 10, 3, 10));
 
        JPanel rightWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightWrap.setOpaque(false);
        rightWrap.setBorder(new EmptyBorder(0, 0, 0, 14));
        rightWrap.add(idChip);
 
        // ── ASSEMBLE ──────────────────────────────────────────────────
        JPanel leftSection = new JPanel(new BorderLayout());
        leftSection.setOpaque(false);
        leftSection.add(accentBar, BorderLayout.WEST);
        leftSection.add(iconWrap,  BorderLayout.CENTER);
 
        card.add(leftSection, BorderLayout.WEST);
        card.add(textPanel,   BorderLayout.CENTER);
        card.add(rightWrap,   BorderLayout.EAST);
 
        wrap.add(card);
        return wrap;
    }
 
    // ===================================================================
    // EMPTY STATE
    // ===================================================================
    private JPanel emptyState() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(80, 0, 0, 0));
        p.setAlignmentX(LEFT_ALIGNMENT);
 
        // Bell icon placeholder
        JLabel icon = new JLabel("\u25CE");
        icon.setFont(new Font("Georgia", Font.PLAIN, 52));
        icon.setForeground(new Color(0xCC, 0xC8, 0xC0));
        icon.setAlignmentX(CENTER_ALIGNMENT);
 
        JLabel msg = new JLabel("Belum ada notifikasi");
        msg.setFont(new Font("Georgia", Font.BOLD, 17));
        msg.setForeground(new Color(0xBB, 0xB8, 0xB0));
        msg.setAlignmentX(CENTER_ALIGNMENT);
 
        JLabel sub = new JLabel("Notifikasi akan muncul setelah booking dikonfirmasi");
        sub.setFont(FONT_BODY);
        sub.setForeground(new Color(0xCC, 0xC8, 0xC0));
        sub.setAlignmentX(CENTER_ALIGNMENT);
 
        p.add(icon);
        p.add(Box.createVerticalStrut(14));
        p.add(msg);
        p.add(Box.createVerticalStrut(6));
        p.add(sub);
        return p;
    }
 
    // ===================================================================
    // UTILITIES
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
        java.awt.EventQueue.invokeLater(() -> new f_notifikasi_user().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
