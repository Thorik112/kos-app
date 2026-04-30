/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.sql.*;

public class cari_kos extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(cari_kos.class.getName());

    // ===== DESIGN TOKENS (identik dengan home_user) =====
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
 
    private static final int IMG_H        = 160;
    private static final int CARD_BODY_H  = 90;
    private static final int CARD_FOOT_H  = 52;
 
    // ===== STATE =====
    private JPanel      contentGrid;
    private JTextField  searchField;
    private JSlider     sliderMin, sliderMax;
    private JLabel      lblRange;
    
    public cari_kos() {
       // initComponents();
        
        setTitle("KosApp \u2014 Cari Kos");
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
 
        // load all data on start
        loadData("", 0, 5_000_000);
    }
 
    // ===================================================================
    // SIDEBAR  (identik dengan home_user, hanya "Cari Kos" yang active)
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
 
        JButton btnDash     = sidebarBtn("Dashboard",       "\u2302", false);
        JButton btnCari     = sidebarBtn("Cari Kos",        "\u25A2", true);   // ACTIVE
        JButton btnRiwayat  = sidebarBtn("Riwayat Booking", "\u2630", false);
        JButton btnNotif    = sidebarBtn("Notifikasi",      "\u25CE", false);
 
        btnDash.addActionListener(e    -> { new home_user().setVisible(true); dispose(); });
        btnCari.addActionListener(e    -> { /* already here */ });
        btnRiwayat.addActionListener(e -> new f_riwayat_booking().setVisible(true));
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
    // HEADER  — berisi search bar + range slider harga
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
 
        // Left — breadcrumb
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 16));
        left.setOpaque(false);
        JLabel pageTitle = new JLabel("Cari Kos");
        pageTitle.setFont(new Font("Georgia", Font.BOLD, 20));
        pageTitle.setForeground(C_TEXT_PRIM);
        JLabel crumb = new JLabel("  /  Temukan Hunian");
        crumb.setFont(FONT_BODY);
        crumb.setForeground(C_TEXT_SEC);
        left.add(pageTitle); left.add(crumb);
 
        // Right — search box + button
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 12));
        right.setOpaque(false);
 
        searchField = buildSearchField();
 
        JButton btnCari = buildSearchButton();
        btnCari.addActionListener(e -> triggerSearch());
        // search on Enter
        searchField.addActionListener(e -> triggerSearch());
 
        right.add(searchField);
        right.add(btnCari);
 
        header.add(left,  BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);
        return header;
    }
 
    /** Rounded search field */
    private JTextField buildSearchField() {
        JTextField tf = new JTextField(22) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0xF5, 0xF4, 0xF0));
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 24, 24);
                g2.setColor(C_BORDER);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 24, 24);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        tf.setFont(FONT_BODY);
        tf.setForeground(C_TEXT_PRIM);
        tf.setOpaque(false);
        tf.setBorder(new EmptyBorder(6, 14, 6, 14));
        tf.setToolTipText("Cari nama kos atau kota...");
        tf.setPreferredSize(new Dimension(260, 36));
        return tf;
    }
 
    private JButton buildSearchButton() {
        JButton btn = new JButton("\uD83D\uDD0D  Cari") {
            private boolean hov = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                public void mouseExited (MouseEvent e) { hov = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? C_ACCENT_DARK : C_ACCENT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
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
        btn.setBorder(new EmptyBorder(6, 20, 6, 20));
        btn.setPreferredSize(new Dimension(110, 36));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
 
    // ===================================================================
    // CONTENT  — filter panel (kiri) + card grid (kanan)
    // ===================================================================
    private JPanel buildContent() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(C_BG_PAGE);
 
        content.add(buildFilterPanel(), BorderLayout.WEST);
 
        // Card grid inside scroll
        contentGrid = new JPanel(new GridLayout(0, 3, 18, 18));
        contentGrid.setBackground(C_BG_PAGE);
 
        JPanel gridWrapper = new JPanel(new BorderLayout());
        gridWrapper.setBackground(C_BG_PAGE);
        gridWrapper.setBorder(new EmptyBorder(24, 20, 28, 28));
        gridWrapper.add(contentGrid, BorderLayout.CENTER);
 
        JScrollPane scroll = new JScrollPane(gridWrapper);
        scroll.setBorder(null);
        scroll.setBackground(C_BG_PAGE);
        scroll.getViewport().setBackground(C_BG_PAGE);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        content.add(scroll, BorderLayout.CENTER);
 
        return content;
    }
 
    // ===================================================================
    // FILTER PANEL  — sidebar kanan berisi slider harga
    // ===================================================================
    private JPanel buildFilterPanel() {
        JPanel panel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // right-edge divider
                g.setColor(C_BORDER);
                g.fillRect(getWidth() - 1, 0, 1, getHeight());
            }
        };
        panel.setBackground(C_BG_CARD);
        panel.setPreferredSize(new Dimension(230, 0));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(28, 22, 28, 22));
 
        // Section title
        JLabel filterTitle = new JLabel("Filter");
        filterTitle.setFont(new Font("Georgia", Font.BOLD, 16));
        filterTitle.setForeground(C_TEXT_PRIM);
        filterTitle.setAlignmentX(LEFT_ALIGNMENT);
 
        JPanel deco = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(C_ACCENT);
                g2.fillRect(0, 1, 28, 3);
                g2.setColor(C_BORDER);
                g2.fillRect(32, 2, getWidth() - 32, 1);
                g2.dispose();
            }
        };
        deco.setOpaque(false);
        deco.setMaximumSize(new Dimension(Integer.MAX_VALUE, 6));
        deco.setPreferredSize(new Dimension(0, 6));
        deco.setAlignmentX(LEFT_ALIGNMENT);
 
        panel.add(filterTitle);
        panel.add(Box.createVerticalStrut(10));
        panel.add(deco);
        panel.add(Box.createVerticalStrut(24));
 
        // ── HARGA SECTION ──
        JLabel hargaLabel = new JLabel("Rentang Harga");
        hargaLabel.setFont(FONT_BOLD);
        hargaLabel.setForeground(C_TEXT_PRIM);
        hargaLabel.setAlignmentX(LEFT_ALIGNMENT);
 
        // Range display label
        lblRange = new JLabel();
        lblRange.setFont(new Font("Georgia", Font.BOLD, 13));
        lblRange.setForeground(C_ACCENT);
        lblRange.setAlignmentX(LEFT_ALIGNMENT);
 
        // Sliders
        sliderMin = buildSlider(0, 5_000_000, 0);
        sliderMax = buildSlider(0, 5_000_000, 5_000_000);
 
        updateRangeLabel();
 
        sliderMin.addChangeListener(e -> {
            if (sliderMin.getValue() > sliderMax.getValue())
                sliderMin.setValue(sliderMax.getValue());
            updateRangeLabel();
            if (!sliderMin.getValueIsAdjusting()) triggerSearch();
        });
        sliderMax.addChangeListener(e -> {
            if (sliderMax.getValue() < sliderMin.getValue())
                sliderMax.setValue(sliderMin.getValue());
            updateRangeLabel();
            if (!sliderMax.getValueIsAdjusting()) triggerSearch();
        });
 
        JLabel minLabel = filterSubLabel("Minimum");
        JLabel maxLabel = filterSubLabel("Maksimum");
 
        // Reset button
        JButton btnReset = buildResetButton();
        btnReset.addActionListener(e -> {
            sliderMin.setValue(0);
            sliderMax.setValue(5_000_000);
            searchField.setText("");
            triggerSearch();
        });
 
        panel.add(hargaLabel);
        panel.add(Box.createVerticalStrut(12));
        panel.add(lblRange);
        panel.add(Box.createVerticalStrut(16));
        panel.add(minLabel);
        panel.add(Box.createVerticalStrut(4));
        panel.add(sliderMin);
        panel.add(Box.createVerticalStrut(10));
        panel.add(maxLabel);
        panel.add(Box.createVerticalStrut(4));
        panel.add(sliderMax);
        panel.add(Box.createVerticalStrut(24));
        panel.add(buildHRule());
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnReset);
        panel.add(Box.createVerticalGlue());
 
        return panel;
    }
 
    private JSlider buildSlider(int min, int max, int value) {
        JSlider s = new JSlider(min, max, value);
        s.setOpaque(false);
        s.setAlignmentX(LEFT_ALIGNMENT);
        s.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        s.setPreferredSize(new Dimension(0, 36));
        s.setForeground(C_ACCENT);
        UIManager.put("Slider.thumb", C_ACCENT);
        return s;
    }
 
    private JLabel filterSubLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_SMALL);
        l.setForeground(C_TEXT_SEC);
        l.setAlignmentX(LEFT_ALIGNMENT);
        return l;
    }
 
    private JPanel buildHRule() {
        JPanel r = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(C_BORDER);
                g.fillRect(0, 0, getWidth(), 1);
            }
        };
        r.setOpaque(false);
        r.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        r.setPreferredSize(new Dimension(0, 1));
        r.setAlignmentX(LEFT_ALIGNMENT);
        return r;
    }
 
    private JButton buildResetButton() {
        JButton btn = new JButton("Reset Filter") {
            private boolean hov = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                public void mouseExited (MouseEvent e) { hov = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (hov) {
                    g2.setColor(new Color(0xF5, 0xF4, 0xF0));
                } else {
                    g2.setColor(C_BG_CARD);
                }
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                g2.setColor(C_ACCENT);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        btn.setFont(FONT_BODY);
        btn.setForeground(C_ACCENT);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(9, 0, 9, 0));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
 
    // ===================================================================
    // LOAD DATA
    // ===================================================================
    private void triggerSearch() {
        loadData(searchField.getText().trim(), sliderMin.getValue(), sliderMax.getValue());
    }
 
    private void loadData(String keyword, int min, int max) {
        contentGrid.removeAll();
 
        try {
            Connection conn = koneksi.connect();
            String sql = "SELECT kos.*, MIN(foto_kos.nama_file) AS nama_file "
                       + "FROM kos "
                       + "LEFT JOIN foto_kos ON kos.id_kos = foto_kos.id_kos "
                       + "WHERE (nama_kos LIKE ? OR kota LIKE ?) "
                       + "AND harga >= ? AND harga <= ? "
                       + "GROUP BY kos.id_kos";
 
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, "%" + keyword + "%");
            pst.setString(2, "%" + keyword + "%");
            pst.setInt(3, min);
            pst.setInt(4, max);
 
            ResultSet rs = pst.executeQuery();
            boolean any = false;
            while (rs.next()) {
                any = true;
                contentGrid.add(createCard(
                    rs.getInt("id_kos"),
                    rs.getString("nama_kos"),
                    rs.getString("harga"),
                    rs.getString("kota"),
                    rs.getString("alamat"),
                    rs.getString("nama_file")));
            }
            if (!any) {
                contentGrid.add(emptyState());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
 
        contentGrid.revalidate();
        contentGrid.repaint();
    }
 
    /** Empty state panel shown when no results */
    private JPanel emptyState() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(60, 0, 0, 0));
 
        JLabel icon = new JLabel("\u2302");
        icon.setFont(new Font("Georgia", Font.PLAIN, 48));
        icon.setForeground(new Color(0xCC, 0xC8, 0xC0));
        icon.setAlignmentX(CENTER_ALIGNMENT);
 
        JLabel msg = new JLabel("Tidak ada kos ditemukan");
        msg.setFont(new Font("Georgia", Font.BOLD, 16));
        msg.setForeground(new Color(0xBB, 0xB8, 0xB0));
        msg.setAlignmentX(CENTER_ALIGNMENT);
 
        JLabel sub = new JLabel("Coba ubah kata kunci atau filter harga");
        sub.setFont(FONT_BODY);
        sub.setForeground(new Color(0xCC, 0xC8, 0xC0));
        sub.setAlignmentX(CENTER_ALIGNMENT);
 
        p.add(icon);
        p.add(Box.createVerticalStrut(12));
        p.add(msg);
        p.add(Box.createVerticalStrut(6));
        p.add(sub);
        return p;
    }
 
    // ===================================================================
    // CARD  (sama persis dengan home_user)
    // ===================================================================
    private JPanel createCard(int id, String nama, String harga,
                              String kota, String alamat, String fotoFile) {
 
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);
        wrap.setBorder(new EmptyBorder(0, 0, 8, 8));
        wrap.setPreferredSize(new Dimension(0, IMG_H + CARD_BODY_H + CARD_FOOT_H + 16));
 
        JPanel card = new JPanel(new BorderLayout()) {
            private boolean hov = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                public void mouseExited (MouseEvent e) { hov = false; repaint(); }
            }); }
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
 
        // Image — pre-scale once
        if (fotoFile == null) fotoFile = "default.jpg";
        Image scaledImg = null;
        try {
            String path = "E:\\NetBeansProjects\\Kos_TA\\src\\images\\" + fotoFile;
            scaledImg = new ImageIcon(path).getImage()
                .getScaledInstance(600, IMG_H, Image.SCALE_SMOOTH);
        } catch (Exception ignored) {}
        final Image finalImg = scaledImg;
 
        JPanel imgPanel = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_RENDERING,   RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
                g2.setClip(null);
                GradientPaint gp = new GradientPaint(
                    0, getHeight() - 55, new Color(0, 0, 0, 0),
                    0, getHeight(),      new Color(0, 0, 0, 100));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintChildren(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        imgPanel.setPreferredSize(new Dimension(0, IMG_H));
 
        // Badge
        JLabel badgeTxt = new JLabel(" " + (kota != null ? kota : "") + " ");
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
        badge.setBorder(new EmptyBorder(3, 8, 3, 8));
        int bw = Math.max(badge.getPreferredSize().width + 16, 70);
        badge.setBounds(10, IMG_H - 28, bw, 22);
        imgPanel.add(badge);
        imgPanel.addComponentListener(new ComponentAdapter() {
            @Override public void componentResized(ComponentEvent e) {
                badge.setBounds(10, imgPanel.getHeight() - 28, bw, 22);
            }
        });
 
        // Body
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setOpaque(false);
        body.setBorder(new EmptyBorder(12, 16, 0, 16));
        body.setPreferredSize(new Dimension(0, CARD_BODY_H));
 
        JLabel nameLabel = new JLabel(truncate(nama, 28));
        nameLabel.setFont(FONT_TITLE);
        nameLabel.setForeground(C_TEXT_PRIM);
        nameLabel.setAlignmentX(LEFT_ALIGNMENT);
 
        JLabel locLabel = new JLabel("\u25CF  " + truncate(alamat != null ? alamat : "", 32));
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
        priceRow.add(priceLabel); priceRow.add(perLabel);
 
        body.add(nameLabel);
        body.add(Box.createVerticalStrut(4));
        body.add(locLabel);
        body.add(Box.createVerticalStrut(8));
        body.add(priceRow);
 
        // Footer
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(10, 14, 14, 14));
        footer.setPreferredSize(new Dimension(0, CARD_FOOT_H));
 
        JButton detailBtn = detailButton();
        detailBtn.addActionListener(e -> new detail_kos(id).setVisible(true));
        footer.add(detailBtn, BorderLayout.CENTER);
 
        card.add(imgPanel, BorderLayout.NORTH);
        card.add(body,     BorderLayout.CENTER);
        card.add(footer,   BorderLayout.SOUTH);
 
        wrap.add(card);
        return wrap;
    }
 
    private JButton detailButton() {
        JButton btn = new JButton("Lihat Detail") {
            private boolean hov = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                public void mouseExited (MouseEvent e) { hov = false; repaint(); }
            }); }
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
    private void updateRangeLabel() {
        int min = sliderMin.getValue();
        int max = sliderMax.getValue();
        lblRange.setText("Rp " + formatHarga(String.valueOf(min))
            + " \u2014 Rp " + formatHarga(String.valueOf(max)));
    }
 
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
 
    private void logout() {
        session.username = null;
        session.email    = null;
        session.role     = null;
        session.foto     = null;
        session.id_user  = 0;
        new f_login().setVisible(true);
        dispose();
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
        java.awt.EventQueue.invokeLater(() -> new cari_kos().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
