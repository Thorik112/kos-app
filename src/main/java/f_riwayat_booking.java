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

public class f_riwayat_booking extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(f_riwayat_booking.class.getName());

    // ===== DESIGN TOKENS (identik dengan home_user & cari_kos) =====
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
 
    // Status badge colors — refined from original
    private static final Color C_STATUS_PENDING   = new Color(0xD4, 0x97, 0x20);  // amber
    private static final Color C_STATUS_CONFIRM   = new Color(0x2E, 0x7D, 0x52);  // forest green
    private static final Color C_STATUS_CANCEL    = new Color(0xB0, 0x30, 0x30);  // deep red
    private static final Color C_STATUS_PENDING_BG = new Color(0xFF, 0xF3, 0xCD);
    private static final Color C_STATUS_CONFIRM_BG = new Color(0xD1, 0xEC, 0xDF);
    private static final Color C_STATUS_CANCEL_BG  = new Color(0xF8, 0xD7, 0xD7);
 
    private static final Font FONT_DISPLAY = new Font("Georgia",  Font.BOLD,  18);
    private static final Font FONT_TITLE   = new Font("Georgia",  Font.BOLD,  15);
    private static final Font FONT_BODY    = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_SMALL   = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font FONT_BOLD    = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FONT_MONO    = new Font("Consolas", Font.PLAIN, 12);
 
    private JPanel contentPanel;
    
    public f_riwayat_booking() {
        
        setTitle("KosApp \u2014 Riwayat Booking");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        getContentPane().setBackground(C_BG_PAGE);
        initUI();
        loadData();
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
    // SIDEBAR  ("Riwayat Booking" active)
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
        JButton btnRiwayat = sidebarBtn("Riwayat Booking", "\u2630", true);   // ACTIVE
        JButton btnNotif   = sidebarBtn("Notifikasi",      "\u25CE", false);
 
        btnDash.addActionListener(e    -> { new home_user().setVisible(true); dispose(); });
        btnCari.addActionListener(e    -> { new cari_kos().setVisible(true);  dispose(); });
        btnRiwayat.addActionListener(e -> { /* already here */ });
        btnNotif.addActionListener(e   -> new f_notifikasi_user().setVisible(true));
 
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
        JLabel pageTitle = new JLabel("Riwayat Booking");
        pageTitle.setFont(new Font("Georgia", Font.BOLD, 20));
        pageTitle.setForeground(C_TEXT_PRIM);
        JLabel crumb = new JLabel("  /  Histori Sewa");
        crumb.setFont(FONT_BODY);
        crumb.setForeground(C_TEXT_SEC);
        left.add(pageTitle); left.add(crumb);
 
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 12));
        right.setOpaque(false);
        JLabel greeting = new JLabel("Hi, " + (session.username != null ? session.username : "User"));
        greeting.setFont(FONT_BODY);
        greeting.setForeground(C_TEXT_SEC);
        right.add(greeting);
 
        header.add(left,  BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);
        return header;
    }
 
    // ===================================================================
    // CONTENT AREA
    // ===================================================================
    private JScrollPane buildContent() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(C_BG_PAGE);
        wrapper.setBorder(new EmptyBorder(28, 32, 32, 32));
 
        // Section header
        JPanel sectionHead = buildSectionHeader();
        wrapper.add(sectionHead, BorderLayout.NORTH);
 
        // Booking list (BoxLayout vertical)
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
 
        JLabel t = new JLabel("Daftar Booking");
        t.setFont(new Font("Georgia", Font.BOLD, 22));
        t.setForeground(C_TEXT_PRIM);
        t.setAlignmentX(LEFT_ALIGNMENT);
 
        JLabel s = new JLabel("Semua histori pemesanan Anda");
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
                "SELECT b.id_booking, k.nomor_kamar, kos.nama_kos, " +
                "b.tanggal_masuk, b.durasi_sewa, b.total_harga, b.status_booking " +
                "FROM booking b " +
                "JOIN kamar k ON b.id_kamar = k.id_kamar " +
                "JOIN kos ON k.id_kos = kos.id_kos " +
                "WHERE b.id_user = ? " +
                "ORDER BY b.id_booking DESC";
 
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, session.id_user);
            ResultSet rs = pst.executeQuery();
 
            boolean any = false;
            while (rs.next()) {
                any = true;
                contentPanel.add(createCard(
                    rs.getInt("id_booking"),
                    rs.getString("nomor_kamar"),
                    rs.getString("nama_kos"),
                    rs.getString("tanggal_masuk"),
                    rs.getInt("durasi_sewa"),
                    rs.getDouble("total_harga"),
                    rs.getString("status_booking")));
                contentPanel.add(Box.createVerticalStrut(12));
            }
            if (!any) contentPanel.add(emptyState());
 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
 
        contentPanel.revalidate();
        contentPanel.repaint();
    }
 
    // ===================================================================
    // BOOKING CARD
    // ===================================================================
    private JPanel createCard(int idBooking, String kamar, String namaKos,
                              String tanggal, int durasi, double total, String status) {
 
        // Outer card with custom rounded painting
        JPanel card = new JPanel(new BorderLayout(0, 0)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // subtle shadow
                for (int i = 3; i >= 1; i--) {
                    g2.setColor(new Color(0, 0, 0, 4));
                    g2.fillRoundRect(i, i + 1, getWidth() - i * 2, getHeight() - i * 2, 14, 14);
                }
                g2.setColor(C_BG_CARD);
                g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 14, 14);
                g2.setColor(C_BORDER);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 14, 14);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));
        card.setAlignmentX(LEFT_ALIGNMENT);
 
        // ── LEFT: accent bar + info ─────────────────────────────────────
        // Colored left accent bar based on status
        JPanel accentBar = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color barColor = switch (status) {
                    case "confirmed"  -> C_STATUS_CONFIRM;
                    case "cancelled"  -> C_STATUS_CANCEL;
                    default           -> C_STATUS_PENDING;
                };
                g2.setColor(barColor);
                // only round left corners to match card
                g2.fillRoundRect(0, 4, getWidth() + 8, getHeight() - 8, 14, 14);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        accentBar.setPreferredSize(new Dimension(6, 0));
 
        // Main info section
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(new EmptyBorder(16, 18, 16, 16));
 
        // Kos name + booking ID row
        JPanel nameRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        nameRow.setOpaque(false);
        nameRow.setAlignmentX(LEFT_ALIGNMENT);
 
        JLabel lblKos = new JLabel(namaKos);
        lblKos.setFont(FONT_TITLE);
        lblKos.setForeground(C_TEXT_PRIM);
 
        JLabel lblId = new JLabel("  #" + idBooking);
        lblId.setFont(FONT_SMALL);
        lblId.setForeground(C_TEXT_SEC);
 
        nameRow.add(lblKos);
        nameRow.add(lblId);
 
        // Detail row — kamar · tanggal · durasi
        JPanel detailRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        detailRow.setOpaque(false);
        detailRow.setAlignmentX(LEFT_ALIGNMENT);
 
        detailRow.add(metaChip("\uD83D\uDECF  Kamar " + kamar));
        detailRow.add(Box.createHorizontalStrut(8));
        detailRow.add(metaChip("\uD83D\uDCC5  " + tanggal));
        detailRow.add(Box.createHorizontalStrut(8));
        detailRow.add(metaChip("\u23F1  " + durasi + " bulan"));
 
        // Price row
        JPanel priceRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        priceRow.setOpaque(false);
        priceRow.setAlignmentX(LEFT_ALIGNMENT);
 
        JLabel lblTotal = new JLabel("Total  ");
        lblTotal.setFont(FONT_SMALL);
        lblTotal.setForeground(C_TEXT_SEC);
 
        JLabel lblHarga = new JLabel("Rp " + formatHarga(total));
        lblHarga.setFont(new Font("Georgia", Font.BOLD, 15));
        lblHarga.setForeground(C_TEXT_PRIM);
 
        priceRow.add(lblTotal);
        priceRow.add(lblHarga);
 
        infoPanel.add(nameRow);
        infoPanel.add(Box.createVerticalStrut(6));
        infoPanel.add(detailRow);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(priceRow);
 
        // ── RIGHT: status badge + action button ────────────────────────
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        actionPanel.setOpaque(false);
        actionPanel.setBorder(new EmptyBorder(16, 0, 16, 20));
 
        JPanel badge = statusBadge(status);
        badge.setAlignmentX(RIGHT_ALIGNMENT);
        actionPanel.add(badge);
        actionPanel.add(Box.createVerticalStrut(10));
 
        // Action button based on status
        if ("pending".equals(status)) {
            JButton btnBatal = dangerButton("Batalkan");
            btnBatal.setAlignmentX(RIGHT_ALIGNMENT);
            btnBatal.addActionListener(e -> batalBooking(idBooking));
            actionPanel.add(btnBatal);
 
        } else if ("confirmed".equals(status)) {
            String statusBayar = getStatusPembayaran(idBooking);
 
            if (statusBayar == null) {
                JButton btnBayar = primaryButton("Bayar Sekarang");
                btnBayar.setAlignmentX(RIGHT_ALIGNMENT);
                btnBayar.addActionListener(e -> new f_pembayaran_user(idBooking).setVisible(true));
                actionPanel.add(btnBayar);
            } else {
                JLabel lblBayar = paymentStatusLabel(statusBayar);
                lblBayar.setAlignmentX(RIGHT_ALIGNMENT);
                actionPanel.add(lblBayar);
            }
        }
 
        // Assemble card
        JPanel leftSection = new JPanel(new BorderLayout());
        leftSection.setOpaque(false);
        leftSection.add(accentBar,  BorderLayout.WEST);
        leftSection.add(infoPanel,  BorderLayout.CENTER);
 
        card.add(leftSection,  BorderLayout.CENTER);
        card.add(actionPanel,  BorderLayout.EAST);
 
        // Wrapper with bottom margin
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);
        wrap.setAlignmentX(LEFT_ALIGNMENT);
        wrap.setBorder(new EmptyBorder(0, 0, 4, 6)); // room for shadow
        wrap.add(card);
        return wrap;
    }
 
    /** Small inline chip — kamar / tanggal / durasi */
    private JLabel metaChip(String text) {
        JLabel lbl = new JLabel(text) {
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
        lbl.setFont(FONT_SMALL);
        lbl.setForeground(C_TEXT_SEC);
        lbl.setBorder(new EmptyBorder(3, 10, 3, 10));
        return lbl;
    }
 
    /** Coloured status badge panel */
    private JPanel statusBadge(String status) {
        Color bg, fg;
        String label;
        switch (status) {
            case "confirmed" -> { bg = C_STATUS_CONFIRM_BG; fg = C_STATUS_CONFIRM; label = "Dikonfirmasi"; }
            case "cancelled" -> { bg = C_STATUS_CANCEL_BG;  fg = C_STATUS_CANCEL;  label = "Dibatalkan";   }
            default          -> { bg = C_STATUS_PENDING_BG; fg = C_STATUS_PENDING; label = "Menunggu";     }
        }
        final Color finalBg = bg;
        final Color finalFg = fg;
 
        JLabel lbl = new JLabel(label, SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lbl.setForeground(finalFg);
 
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(finalBg);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.setColor(finalFg);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        panel.add(lbl);
        panel.setBorder(new EmptyBorder(4, 14, 4, 14));
        return panel;
    }
 
    /** Label for payment status (menunggu / lunas / gagal) */
    private JLabel paymentStatusLabel(String statusBayar) {
        String text;
        Color fg;
        switch (statusBayar) {
            case "lunas"     -> { text = "\u2714  Lunas";         fg = C_STATUS_CONFIRM; }
            case "menunggu"  -> { text = "\u23F3  Menunggu";      fg = C_STATUS_PENDING; }
            default          -> { text = "\u2716  Ditolak";       fg = C_STATUS_CANCEL;  }
        }
        JLabel lbl = new JLabel(text, SwingConstants.RIGHT);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lbl.setForeground(fg);
        return lbl;
    }
 
    /** Gold primary button (Bayar Sekarang) */
    private JButton primaryButton(String text) {
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
        btn.setBorder(new EmptyBorder(8, 18, 8, 18));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
 
    /** Red danger button (Batalkan) */
    private JButton dangerButton(String text) {
        final Color C_DANGER     = new Color(0xB0, 0x30, 0x30);
        final Color C_DANGER_HOV = new Color(0x8C, 0x20, 0x20);
        final Color C_DANGER_BG  = new Color(0xF8, 0xD7, 0xD7);
 
        JButton btn = new JButton(text) {
            private boolean hov = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                public void mouseExited (MouseEvent e) { hov = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? C_DANGER_HOV : C_DANGER_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(C_DANGER);
                g2.setStroke(new BasicStroke(1.2f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        btn.setFont(FONT_BOLD);
        btn.setForeground(C_DANGER);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 18, 8, 18));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
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
 
        JLabel icon = new JLabel("\u2630");
        icon.setFont(new Font("Georgia", Font.PLAIN, 52));
        icon.setForeground(new Color(0xCC, 0xC8, 0xC0));
        icon.setAlignmentX(CENTER_ALIGNMENT);
 
        JLabel msg = new JLabel("Belum ada riwayat booking");
        msg.setFont(new Font("Georgia", Font.BOLD, 17));
        msg.setForeground(new Color(0xBB, 0xB8, 0xB0));
        msg.setAlignmentX(CENTER_ALIGNMENT);
 
        JLabel sub = new JLabel("Yuk temukan kos impian Anda sekarang");
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
    // ACTIONS
    // ===================================================================
    private void batalBooking(int idBooking) {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Yakin ingin membatalkan booking ini?", "Konfirmasi Pembatalan",
            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
 
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection conn = koneksi.connect();
                PreparedStatement pst = conn.prepareStatement(
                    "UPDATE booking SET status_booking='cancelled' WHERE id_booking=?");
                pst.setInt(1, idBooking);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Booking berhasil dibatalkan.");
                loadData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }
 
    private String getStatusPembayaran(int idBooking) {
        try {
            Connection conn = koneksi.connect();
            PreparedStatement pst = conn.prepareStatement(
                "SELECT status_pembayaran FROM pembayaran " +
                "WHERE id_booking=? ORDER BY id_pembayaran DESC LIMIT 1");
            pst.setInt(1, idBooking);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) return rs.getString("status_pembayaran");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 
    // ===================================================================
    // UTILITIES
    // ===================================================================
    private String formatHarga(double val) {
        return String.format("%,.0f", val).replace(',', '.');
    }
 
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
        java.awt.EventQueue.invokeLater(() -> new f_riwayat_booking().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
