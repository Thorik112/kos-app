/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.sql.*;

public class home_user extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(home_user.class.getName());
 
        // ===== DESIGN TOKENS =====
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
    private static final Color C_BADGE_BG         = new Color(15, 25, 35, 210);
 
    private static final Font FONT_TITLE  = new Font("Georgia",  Font.BOLD,  14);
    private static final Font FONT_BODY   = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_SMALL  = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font FONT_BOLD   = new Font("Segoe UI", Font.BOLD,  13);
 
    // Image height constant — used in both imagePanel preferredSize and badge y-position
    private static final int IMG_H = 160;
    
    public home_user() {
        setTitle("KosApp \u2014 Home");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        getContentPane().setBackground(C_BG_PAGE);
        initUI();
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
        logoPanel.add(lKos);
        logoPanel.add(lApp);
        sidebar.add(logoPanel);
        sidebar.add(hRule());
 
        // Nav
        JPanel nav = new JPanel();
        nav.setOpaque(false);
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        nav.setBorder(new EmptyBorder(16, 0, 0, 0));
        nav.setAlignmentX(LEFT_ALIGNMENT);
        navLabel(nav, "NAVIGASI");
 
        JButton btnDash  = sidebarBtn("Dashboard",       "\u2302", true);
        JButton btnCari  = sidebarBtn("Cari Kos",        "\u25A2", false);
        JButton btnRiwayat  = sidebarBtn("Riwayat Booking", "\u2630", false);
        JButton btnNotif = sidebarBtn("Notifikasi",      "\u25CE", false);
 
        btnDash.addActionListener(e    -> { new home_user().setVisible(true); dispose(); });
        btnCari.addActionListener(e    -> { new cari_kos().setVisible(true);  dispose(); });
        btnRiwayat.addActionListener(e -> new f_riwayat_booking().setVisible(true));
        btnNotif.addActionListener(e   -> new f_notifikasi_user().setVisible(true));
 
        nav.add(btnDash);  nav.add(Box.createVerticalStrut(2));
        nav.add(btnCari);  nav.add(Box.createVerticalStrut(2));
        nav.add(btnRiwayat); nav.add(Box.createVerticalStrut(2));
        nav.add(btnNotif);
        sidebar.add(nav);
        sidebar.add(Box.createVerticalGlue());
 
        // User card
        sidebar.add(sidebarUserCard());
 
        // Logout
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
 
        // Truncate username if too long
        String displayName = session.username != null ? session.username : "User";
        if (displayName.length() > 14) displayName = displayName.substring(0, 13) + "…";
        JLabel uname = new JLabel(displayName);
        uname.setFont(FONT_BOLD);
        uname.setForeground(Color.WHITE);
 
        JLabel role = new JLabel("Penyewa");
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
        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Georgia", Font.BOLD, 20));
        title.setForeground(C_TEXT_PRIM);
        JLabel crumb = new JLabel("  /  Beranda");
        crumb.setFont(FONT_BODY);
        crumb.setForeground(C_TEXT_SEC);
        left.add(title); left.add(crumb);
 
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 12));
        right.setOpaque(false);
        JLabel greeting = new JLabel("Selamat Datang, "
            + (session.username != null ? session.username : "User"));
        greeting.setFont(FONT_BODY);
        greeting.setForeground(C_TEXT_SEC);
        JLabel avatarLbl = headerAvatar();
        avatarLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        avatarLbl.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { new profile_user().setVisible(true); }
        });
        right.add(greeting); right.add(avatarLbl);
 
        header.add(left,  BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);
        return header;
    }
 
    private JLabel headerAvatar() {
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
                        ? String.valueOf(session.username.charAt(0)).toUpperCase() : "U";
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
    // CONTENT
    // ===================================================================
    private JScrollPane buildContent() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(C_BG_PAGE);
        wrapper.setBorder(new EmptyBorder(32, 32, 32, 32));
        wrapper.add(sectionHeader(), BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0, 3, 18, 18));
        grid.setBackground(C_BG_PAGE);
        loadData(grid);
        wrapper.add(grid, BorderLayout.CENTER);
 
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
        p.setBorder(new EmptyBorder(0, 0, 20, 0));
 
        JLabel t = new JLabel("Kos Tersedia");
        t.setFont(new Font("Georgia", Font.BOLD, 22));
        t.setForeground(C_TEXT_PRIM);
        t.setAlignmentX(LEFT_ALIGNMENT);
 
        JLabel s = new JLabel("Pilihan hunian terbaik untuk Anda");
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
    private void loadData(JPanel content) {
        try {
            Connection conn = koneksi.connect();
            String sql = """
                SELECT kos.*, foto_kos.nama_file
                FROM kos
                LEFT JOIN foto_kos ON kos.id_kos = foto_kos.id_kos
                  AND foto_kos.created_at = (
                      SELECT MAX(created_at) FROM foto_kos f2
                      WHERE f2.id_kos = kos.id_kos
                  )
                LIMIT 6
                """;
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int    id     = rs.getInt("id_kos");
                String nama   = rs.getString("nama_kos");
                String harga  = rs.getString("harga");
                String kota   = rs.getString("kota");
                String alamat = rs.getString("alamat");
                String foto   = rs.getString("nama_file");
                if (foto == null) foto = "default.jpg";
                content.add(createCard(id, nama, harga, kota, alamat, foto));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
 
    private static final int CARD_BODY_H   = 90;   // name + loc + price
    private static final int CARD_FOOTER_H = 52;   // button area
    private static final int CARD_TOTAL_H  = IMG_H + CARD_BODY_H + CARD_FOOTER_H;
 
    private JPanel createCard(int id, String nama, String harga,
                              String kota, String alamat, String fotoFile) {
 
        // Shadow wrapper — gives a few extra pixels on bottom-right for shadow room
        JPanel wrap = new JPanel(new BorderLayout()) {
            @Override public boolean isOpaque() { return false; }
        };
        wrap.setOpaque(false);
        wrap.setBorder(new EmptyBorder(0, 0, 8, 8));
        // FIX: set a fixed preferred height so GridLayout doesn't stretch
        wrap.setPreferredSize(new Dimension(0, 400));
 
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
 
        // ── IMAGE ────────────────────────────────────────────────────────────
        // Pre-load and scale image once — not in paintComponent (causes flickering)
        Image scaledImg = null;
        try {
            String path = "E:\\NetBeansProjects\\Kos_TA\\src\\images\\" + fotoFile;
            scaledImg = new ImageIcon(path).getImage()
                .getScaledInstance(600, IMG_H, Image.SCALE_SMOOTH);
        } catch (Exception ignored) {}
        final Image finalImg = scaledImg;
 
        // We track panel width dynamically for scaling
        JPanel imgPanel = new JPanel(null) {   // null layout for badge overlay
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_RENDERING,   RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 
                // Round top corners only
                g2.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight() + 14, 14, 14));
 
                if (finalImg != null) {
                    g2.drawImage(finalImg, 0, 0, getWidth(), getHeight(), null);
                } else {
                    g2.setColor(new Color(0xDC, 0xD9, 0xD3));
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.setFont(FONT_BODY);
                    g2.setColor(C_TEXT_SEC);
                    String msg = "No Image";
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(msg,
                        (getWidth() - fm.stringWidth(msg)) / 2,
                        getHeight() / 2 + fm.getAscent() / 2);
                }
 
                // Gradient overlay at bottom of image
                g2.setClip(null);
                GradientPaint gp = new GradientPaint(
                    0, getHeight() - 55, new Color(0, 0, 0, 0),
                    0, getHeight(),      new Color(0, 0, 0, 100));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
 
                // Paint badge child on top
                super.paintChildren(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        imgPanel.setPreferredSize(new Dimension(0, IMG_H));

        JLabel badgeTxt = new JLabel(" " + kota + " ");
        badgeTxt.setFont(new Font("Segoe UI", Font.BOLD, 10));
        badgeTxt.setForeground(C_ACCENT);
 
        JPanel badge = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(C_BADGE_BG);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        badge.add(badgeTxt);
        badge.setBorder(new EmptyBorder(3, 6, 3, 6));
 
        // Use preferred size of text to size the badge properly
        Dimension badgePref = badge.getPreferredSize();
        int bw = Math.max(badgePref.width + 16, 70);
        // FIX: y position anchored relative to IMG_H — no more hardcoded magic number
        badge.setBounds(10, IMG_H - 28, bw, 22);
        imgPanel.add(badge);
 
        // Reposition badge when imgPanel is resized (e.g. window resize)
        imgPanel.addComponentListener(new ComponentAdapter() {
            @Override public void componentResized(ComponentEvent e) {
                badge.setBounds(10, imgPanel.getHeight() - 28, bw, 22);
            }
        });
 
        // ── BODY ─────────────────────────────────────────────────────────────
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setOpaque(false);
        // FIX: fixed height so the card body doesn't expand and create empty space
        body.setBorder(new EmptyBorder(12, 16, 0, 16));
        body.setPreferredSize(new Dimension(0, CARD_BODY_H));
 
        JLabel nameLabel = new JLabel(truncate(nama, 28));
        nameLabel.setFont(FONT_TITLE);
        nameLabel.setForeground(C_TEXT_PRIM);
        nameLabel.setAlignmentX(LEFT_ALIGNMENT);
 
        JLabel locLabel = new JLabel("\u25CF  " + truncate(alamat, 32));
        locLabel.setFont(FONT_SMALL);
        locLabel.setForeground(C_TEXT_SEC);
        locLabel.setAlignmentX(LEFT_ALIGNMENT);
 
        JPanel priceRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        priceRow.setOpaque(false);
        priceRow.setAlignmentX(LEFT_ALIGNMENT);
 
        JLabel priceLabel = new JLabel("Rp " + formatHarga(harga));
        priceLabel.setFont(new Font("Georgia", Font.BOLD, 15));
        priceLabel.setForeground(C_TEXT_PRIM);
 
        JLabel perLabel = new JLabel(" /bln");
        perLabel.setFont(FONT_SMALL);
        perLabel.setForeground(C_TEXT_SEC);
 
        priceRow.add(priceLabel);
        priceRow.add(perLabel);
 
        body.add(nameLabel);
        body.add(Box.createVerticalStrut(4));
        body.add(locLabel);
        body.add(Box.createVerticalStrut(8));
        body.add(priceRow);
 
        // ── FOOTER ───────────────────────────────────────────────────────────
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(10, 14, 14, 14));
        footer.setPreferredSize(new Dimension(0, CARD_FOOTER_H));
 
        JButton btn = detailButton();
        btn.addActionListener(e -> new detail_kos(id).setVisible(true));
        footer.add(btn, BorderLayout.CENTER);
 
        // Assemble
        card.add(imgPanel, BorderLayout.NORTH);
        card.add(body,     BorderLayout.CENTER);
        card.add(footer,   BorderLayout.SOUTH);
 
        wrap.add(card);
        return wrap;
    }
 
    private JButton detailButton() {
        JButton btn = new JButton("Lihat Detail") {
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
 
    // ===================================================================
    // UTILITIES
    // ===================================================================
    private String formatHarga(String raw) {
        try {
            long val = Long.parseLong(raw.replaceAll("[^0-9]", ""));
            return String.format("%,d", val).replace(',', '.');
        } catch (Exception e) { return raw; }
    }
 
    /** Truncate text with ellipsis if too long */
    private String truncate(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, max - 1) + "\u2026";
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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String[] args) {
        //session.username = "User"; // dummy dulu

        new home_user().setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
