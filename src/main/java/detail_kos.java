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
import java.util.ArrayList;

public class detail_kos extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(detail_kos.class.getName());

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
    private static final Color C_STATUS_AVAIL     = new Color(0x2E, 0x7D, 0x32);
    private static final Color C_STATUS_FULL      = new Color(0xC6, 0x28, 0x28);
 
    private static final Font FONT_TITLE  = new Font("Georgia",  Font.BOLD,  14);
    private static final Font FONT_BODY   = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_SMALL  = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font FONT_BOLD   = new Font("Segoe UI", Font.BOLD,  13);
 
    private int idKos;
    private JLabel lblNama, lblAlamat, lblImage;
    private JPanel kamarPanel;
    private ArrayList<String> listFoto = new ArrayList<>();
    private int indexFoto = 0;
    
    public detail_kos(int idKos) {
        //initComponents();
        
        this.idKos = idKos;
        setTitle("KosApp — Detail Kos");
        setSize(960, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        getContentPane().setBackground(C_BG_PAGE);
 
        initUI();
        loadData();
        loadFoto();
        loadKamar();
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
 
        JButton btnDash     = sidebarBtn("Dashboard",        "\u2302", false);
        JButton btnCari     = sidebarBtn("Cari Kos",         "\u25A2", false);
        JButton btnRiwayat  = sidebarBtn("Riwayat Booking",  "\u2630", false);
        JButton btnNotif    = sidebarBtn("Notifikasi",       "\u25CE", false);
 
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
        JLabel title = new JLabel("Detail Kos");
        title.setFont(new Font("Georgia", Font.BOLD, 20));
        title.setForeground(C_TEXT_PRIM);
        JLabel crumb = new JLabel("  /  Info Properti");
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
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(C_BG_PAGE);
        wrapper.setBorder(new EmptyBorder(32, 32, 32, 32));
 
        // Section header
        wrapper.add(sectionHeader("Informasi Kos", "Foto dan detail lengkap properti"));
        wrapper.add(Box.createVerticalStrut(20));
 
        // Top: image gallery + kos info side by side
        JPanel topRow = new JPanel(new BorderLayout(20, 0));
        topRow.setOpaque(false);
        topRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
 
        // Gallery panel
        JPanel galleryCard = buildGalleryCard();
        topRow.add(galleryCard, BorderLayout.CENTER);
 
        // Kos info card
        JPanel infoCard = buildInfoCard();
        infoCard.setPreferredSize(new Dimension(280, 0));
        topRow.add(infoCard, BorderLayout.EAST);
 
        wrapper.add(topRow);
        wrapper.add(Box.createVerticalStrut(28));
        wrapper.add(sectionHeader("Daftar Kamar", "Pilih kamar yang tersedia"));
        wrapper.add(Box.createVerticalStrut(14));
 
        kamarPanel = new JPanel();
        kamarPanel.setLayout(new BoxLayout(kamarPanel, BoxLayout.Y_AXIS));
        kamarPanel.setOpaque(false);
        kamarPanel.setAlignmentX(LEFT_ALIGNMENT);
        wrapper.add(kamarPanel);
 
        JScrollPane scroll = new JScrollPane(wrapper);
        scroll.setBorder(null);
        scroll.setBackground(C_BG_PAGE);
        scroll.getViewport().setBackground(C_BG_PAGE);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }
 
    private JPanel buildGalleryCard() {
        JPanel card = roundedCard();
        card.setLayout(new BorderLayout());
 
        // Image display
        lblImage = new JLabel() {
            @Override protected void paintComponent(Graphics g) {
                if (getIcon() == null) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(new Color(0xDC, 0xD9, 0xD3));
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.setFont(FONT_BODY);
                    g2.setColor(C_TEXT_SEC);
                    String msg = "Memuat foto…";
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(msg,
                        (getWidth() - fm.stringWidth(msg)) / 2,
                        getHeight() / 2);
                    g2.dispose();
                } else {
                    super.paintComponent(g);
                }
            }
        };
        lblImage.setHorizontalAlignment(JLabel.CENTER);
        lblImage.setPreferredSize(new Dimension(0, 240));
        lblImage.setOpaque(false);
 
        // Nav buttons
        JButton prev = navPhotoBtn("\u2039");
        JButton next = navPhotoBtn("\u203A");
        prev.addActionListener(e -> prevFoto());
        next.addActionListener(e -> nextFoto());
 
        JPanel imgWrapper = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0xDC, 0xD9, 0xD3));
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        imgWrapper.setBorder(new EmptyBorder(0, 0, 0, 0));
        imgWrapper.add(prev, BorderLayout.WEST);
        imgWrapper.add(lblImage, BorderLayout.CENTER);
        imgWrapper.add(next, BorderLayout.EAST);
 
        card.add(imgWrapper, BorderLayout.CENTER);
        card.setBorder(new EmptyBorder(16, 16, 16, 16));
        return card;
    }
 
    private JButton navPhotoBtn(String symbol) {
        JButton btn = new JButton(symbol) {
            private boolean hov = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                public void mouseExited (MouseEvent e) { hov = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov
                    ? new Color(0, 0, 0, 100)
                    : new Color(0, 0, 0, 55));
                g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        btn.setFont(new Font("Georgia", Font.BOLD, 22));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(44, 0));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
 
    private JPanel buildInfoCard() {
        JPanel card = roundedCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(20, 20, 20, 20));
 
        JLabel tagNama = fieldTag("Nama Kos");
        lblNama = new JLabel("—");
        lblNama.setFont(new Font("Georgia", Font.BOLD, 16));
        lblNama.setForeground(C_TEXT_PRIM);
        lblNama.setAlignmentX(LEFT_ALIGNMENT);
 
        JLabel tagAlamat = fieldTag("Lokasi");
        lblAlamat = new JLabel("—");
        lblAlamat.setFont(FONT_BODY);
        lblAlamat.setForeground(C_TEXT_SEC);
        lblAlamat.setAlignmentX(LEFT_ALIGNMENT);
 
        // Thin gold divider
        JPanel div = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(C_ACCENT);
                g.fillRect(0, 0, 36, 2);
                g.setColor(C_BORDER);
                g.fillRect(40, 1, getWidth() - 40, 1);
            }
        };
        div.setOpaque(false);
        div.setMaximumSize(new Dimension(Integer.MAX_VALUE, 4));
        div.setPreferredSize(new Dimension(0, 4));
        div.setAlignmentX(LEFT_ALIGNMENT);
 
        card.add(tagNama);
        card.add(Box.createVerticalStrut(4));
        card.add(lblNama);
        card.add(Box.createVerticalStrut(14));
        card.add(div);
        card.add(Box.createVerticalStrut(14));
        card.add(tagAlamat);
        card.add(Box.createVerticalStrut(4));
        card.add(lblAlamat);
        card.add(Box.createVerticalGlue());
        return card;
    }
 
    // ===================================================================
    // KAMAR CARD
    // ===================================================================
    private JPanel createKamarCard(int idKamar, String nomor, String status, String desk, String foto) {
        // Outer shadow wrapper
        JPanel wrap = new JPanel(new BorderLayout()) {
            @Override public boolean isOpaque() { return false; }
        };
        wrap.setOpaque(false);
        wrap.setBorder(new EmptyBorder(0, 0, 12, 6));
        wrap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 148));
        wrap.setAlignmentX(LEFT_ALIGNMENT);
 
        JPanel card = new JPanel(new BorderLayout(14, 0)) {
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
                    for (int i = 3; i >= 1; i--) {
                        g2.setColor(new Color(0, 0, 0, 5));
                        g2.fillRoundRect(i, i + 1, w, h, 12, 12);
                    }
                }
                g2.setColor(C_BG_CARD);
                g2.fillRoundRect(0, 0, w, h, 12, 12);
                g2.setColor(hov ? C_ACCENT : C_BORDER);
                g2.setStroke(new BasicStroke(hov ? 1.5f : 1f));
                g2.drawRoundRect(0, 0, w, h, 12, 12);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        card.setBorder(new EmptyBorder(14, 14, 14, 14));
 
        // ── FOTO ──────────────────────────────────────────────────────────────
        String imgPath = (foto != null && !foto.isEmpty())
            ? "E:\\NetBeansProjects\\Kos_TA\\src\\images\\" + foto
            : "E:\\NetBeansProjects\\Kos_TA\\src\\images\\default_kos.jpg";
 
        JPanel imgPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                try {
                    Image img = new ImageIcon(imgPath).getImage()
                        .getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                    g2.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                    g2.drawImage(img, 0, 0, null);
                } catch (Exception ignored) {
                    g2.setColor(C_BORDER);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                }
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        imgPanel.setPreferredSize(new Dimension(110, 88));
 
        // ── INFO ──────────────────────────────────────────────────────────────
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
 
        JLabel nomorLbl = new JLabel("Kamar " + nomor);
        nomorLbl.setFont(FONT_TITLE);
        nomorLbl.setForeground(C_TEXT_PRIM);
        nomorLbl.setAlignmentX(LEFT_ALIGNMENT);
 
        // Status badge
        boolean tersedia = !status.equalsIgnoreCase("penuh");
        JPanel statusBadge = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = tersedia
                    ? new Color(0x2E, 0x7D, 0x32, 30)
                    : new Color(0xC6, 0x28, 0x28, 30);
                Color border = tersedia ? C_STATUS_AVAIL : C_STATUS_FULL;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.setColor(border);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }
            @Override public boolean isOpaque() { return false; }
        };
        statusBadge.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 2));
        JLabel statusTxt = new JLabel(tersedia ? "● Tersedia" : "● Penuh");
        statusTxt.setFont(FONT_SMALL);
        statusTxt.setForeground(tersedia ? C_STATUS_AVAIL : C_STATUS_FULL);
        statusBadge.add(statusTxt);
        Dimension bd = statusBadge.getPreferredSize();
        statusBadge.setMaximumSize(new Dimension(bd.width + 16, 22));
        statusBadge.setAlignmentX(LEFT_ALIGNMENT);
 
        JTextArea deskArea = new JTextArea(desk != null ? desk : "—");
        deskArea.setLineWrap(true);
        deskArea.setWrapStyleWord(true);
        deskArea.setEditable(false);
        deskArea.setOpaque(false);
        deskArea.setFont(FONT_SMALL);
        deskArea.setForeground(C_TEXT_SEC);
        deskArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        deskArea.setAlignmentX(LEFT_ALIGNMENT);
 
        info.add(nomorLbl);
        info.add(Box.createVerticalStrut(5));
        info.add(statusBadge);
        info.add(Box.createVerticalStrut(6));
        info.add(deskArea);
 
        // ── BUTTON ───────────────────────────────────────────────────────────
        JPanel btnPanel = new JPanel(new BorderLayout());
        btnPanel.setOpaque(false);
        btnPanel.setPreferredSize(new Dimension(120, 0));
 
        JButton btn = new JButton(tersedia ? "Booking" : "Penuh") {
            private boolean hov = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { if (isEnabled()) { hov = true;  repaint(); } }
                public void mouseExited (MouseEvent e) { hov = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (!isEnabled()) {
                    g2.setColor(C_BORDER);
                } else {
                    g2.setColor(hov ? C_ACCENT_DARK : C_BG_SIDEBAR);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        btn.setFont(FONT_BOLD);
        btn.setForeground(tersedia ? Color.WHITE : C_TEXT_SEC);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setEnabled(tersedia);
        btn.setCursor(tersedia ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : Cursor.getDefaultCursor());
        btn.setBorder(new EmptyBorder(8, 16, 8, 16));
        if (tersedia) btn.addActionListener(e -> bookingKamar(idKamar));
 
        btnPanel.add(btn, BorderLayout.CENTER);
 
        card.add(imgPanel,  BorderLayout.WEST);
        card.add(info,      BorderLayout.CENTER);
        card.add(btnPanel,  BorderLayout.EAST);
        wrap.add(card);
        return wrap;
    }
 
    // ===================================================================
    // DATA LOADING
    // ===================================================================
    private void loadData() {
        try {
            Connection conn = koneksi.connect();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM kos WHERE id_kos=?");
            pst.setInt(1, idKos);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                lblNama.setText(rs.getString("nama_kos"));
                lblAlamat.setText(rs.getString("kota") + " — " + rs.getString("alamat"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
 
    private void loadFoto() {
        try {
            Connection conn = koneksi.connect();
            PreparedStatement pst = conn.prepareStatement(
                "SELECT nama_file FROM foto_kos WHERE id_kos=?");
            pst.setInt(1, idKos);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) listFoto.add(rs.getString("nama_file"));
            if (!listFoto.isEmpty()) tampilFoto();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
 
    private void tampilFoto() {
        try {
            if (listFoto.isEmpty()) { lblImage.setText("Tidak ada foto"); return; }
            String path = "E:\\NetBeansProjects\\Kos_TA\\src\\images\\" + listFoto.get(indexFoto);
            Image img = new ImageIcon(path).getImage().getScaledInstance(700, 240, Image.SCALE_SMOOTH);
            lblImage.setIcon(new ImageIcon(img));
            lblImage.setText("");
        } catch (Exception e) { lblImage.setText("No Image"); }
    }
 
    private void nextFoto() {
        if (indexFoto < listFoto.size() - 1) { indexFoto++; tampilFoto(); }
    }
 
    private void prevFoto() {
        if (indexFoto > 0) { indexFoto--; tampilFoto(); }
    }
 
    private void loadKamar() {
        kamarPanel.removeAll();
        try {
            Connection conn = koneksi.connect();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM kamar WHERE id_kos=?");
            pst.setInt(1, idKos);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                kamarPanel.add(createKamarCard(
                    rs.getInt("id_kamar"),
                    rs.getString("nomor_kamar"),
                    rs.getString("status_kamar"),
                    rs.getString("deskripsi"),
                    rs.getString("foto")));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        kamarPanel.revalidate();
        kamarPanel.repaint();
    }
 
    private void bookingKamar(int idKamar) {
        try {
            JTextField tglMasuk = styledTextField();
            JTextField durasi   = styledTextField();
 
            JPanel form = new JPanel();
            form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
            form.setBackground(C_BG_CARD);
            form.setBorder(new EmptyBorder(10, 10, 10, 10));
            form.add(fieldTag("Tanggal Masuk (YYYY-MM-DD)"));
            form.add(Box.createVerticalStrut(4));
            form.add(tglMasuk);
            form.add(Box.createVerticalStrut(10));
            form.add(fieldTag("Durasi Sewa (bulan)"));
            form.add(Box.createVerticalStrut(4));
            form.add(durasi);
 
            int result = JOptionPane.showConfirmDialog(
                this, form, "Form Booking", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION) return;
 
            String tanggalMasuk = tglMasuk.getText().trim();
            if (tanggalMasuk.isEmpty() || durasi.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Data harus diisi!");
                return;
            }
            int durasiSewa = Integer.parseInt(durasi.getText().trim());
 
            Connection conn = koneksi.connect();
            PreparedStatement pstH = conn.prepareStatement(
                "SELECT k.harga FROM kamar km JOIN kos k ON km.id_kos=k.id_kos WHERE km.id_kamar=?");
            pstH.setInt(1, idKamar);
            ResultSet rs = pstH.executeQuery();
            double harga = rs.next() ? rs.getDouble("harga") : 0;
            double total = harga * durasiSewa;
 
            PreparedStatement pst = conn.prepareStatement(
                "INSERT INTO booking(id_user,id_kamar,tanggal_masuk,durasi_sewa,total_harga,tanggal_booking,status_booking) "
                + "VALUES(?,?,?,?,?,NOW(),'pending')");
            pst.setInt(1, session.id_user);
            pst.setInt(2, idKamar);
            pst.setString(3, tanggalMasuk);
            pst.setInt(4, durasiSewa);
            pst.setDouble(5, total);
            pst.executeUpdate();
 
            JOptionPane.showMessageDialog(this,
                "Booking berhasil!\nTotal bayar: Rp " + String.format("%,.0f", total));
            loadKamar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
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
 
    private JLabel fieldTag(String text) {
        JLabel l = new JLabel(text.toUpperCase());
        l.setFont(new Font("Segoe UI", Font.BOLD, 10));
        l.setForeground(C_ACCENT);
        l.setAlignmentX(LEFT_ALIGNMENT);
        return l;
    }
 
    private JTextField styledTextField() {
        JTextField f = new JTextField();
        f.setFont(FONT_BODY);
        f.setForeground(C_TEXT_PRIM);
        f.setBorder(new CompoundBorder(
            new LineBorder(C_BORDER, 1, true),
            new EmptyBorder(6, 10, 6, 10)));
        f.setPreferredSize(new Dimension(260, 34));
        return f;
    }
 
    private JPanel sectionHeader(String titleText, String sub) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(0, 0, 6, 0));
        p.setAlignmentX(LEFT_ALIGNMENT);
 
        JLabel t = new JLabel(titleText);
        t.setFont(new Font("Georgia", Font.BOLD, 18));
        t.setForeground(C_TEXT_PRIM);
        t.setAlignmentX(LEFT_ALIGNMENT);
 
        JLabel s = new JLabel(sub);
        s.setFont(FONT_BODY);
        s.setForeground(C_TEXT_SEC);
        s.setAlignmentX(LEFT_ALIGNMENT);
 
        JPanel deco = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(C_ACCENT);
                g2.fillRect(0, 1, 36, 3);
                g2.setColor(C_BORDER);
                g2.fillRect(40, 2, getWidth() - 40, 1);
                g2.dispose();
            }
        };
        deco.setOpaque(false);
        deco.setAlignmentX(LEFT_ALIGNMENT);
        deco.setMaximumSize(new Dimension(Integer.MAX_VALUE, 6));
        deco.setPreferredSize(new Dimension(0, 6));
 
        p.add(t);
        p.add(Box.createVerticalStrut(4));
        p.add(s);
        p.add(Box.createVerticalStrut(10));
        p.add(deco);
        return p;
    }
 
    private void logout() {
        session.username = null; session.email = null;
        session.role = null; session.foto = null; session.id_user = 0;
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
//        java.awt.EventQueue.invokeLater(() -> new detail_kos(idKos).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
