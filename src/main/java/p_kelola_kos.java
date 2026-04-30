/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.sql.*;

public class p_kelola_kos extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(p_kelola_kos.class.getName());

    private JTable table;
    private DefaultTableModel model;
 
    // ===================================================================
    // DESIGN TOKENS  (identik dengan p_dashboard_pemilik)
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
    private static final Color C_TABLE_HEADER_BG  = new Color(0x0F, 0x19, 0x23);
    private static final Color C_TABLE_STRIPE     = new Color(0xFB, 0xFA, 0xF7);
    private static final Color C_TABLE_SEL        = new Color(0xF0, 0xE8, 0xD8);
    private static final Color C_TABLE_SEL_TEXT   = new Color(0x1A, 0x1A, 0x1A);
    private static final Color C_BTN_DANGER       = new Color(0xC0, 0x3A, 0x2B);
    private static final Color C_BTN_DANGER_DARK  = new Color(0x96, 0x2D, 0x22);
    private static final Color C_BTN_NEUTRAL      = new Color(0x3A, 0x4A, 0x5A);
    private static final Color C_BTN_NEUTRAL_DARK = new Color(0x2A, 0x38, 0x48);
 
    private static final Font FONT_BODY    = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_SMALL   = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font FONT_BOLD    = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FONT_H1      = new Font("Georgia",  Font.BOLD,  22);
    private static final Font FONT_TABLE   = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_TH      = new Font("Segoe UI", Font.BOLD,  12);
    
    public p_kelola_kos() {
//        initComponents();
        
        setTitle("KosApp \u2014 Kelola Kos");
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
    // SIDEBAR  (copy-consistent dengan dashboard)
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
 
        JButton btnDash   = sidebarBtn("Dashboard",  "\u2302", false);
        JButton btnKelola = sidebarBtn("Kelola Kos",  "\u25A1", true);
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
        JLabel title = new JLabel("Kelola Kos");
        title.setFont(new Font("Georgia", Font.BOLD, 20));
        title.setForeground(C_TEXT_PRIM);
        JLabel crumb = new JLabel("  /  Manajemen Properti");
        crumb.setFont(FONT_BODY);
        crumb.setForeground(C_TEXT_SEC);
        left.add(title); left.add(crumb);
 
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 12));
        right.setOpaque(false);
        JLabel greeting = new JLabel(session.username != null ? session.username : "Pemilik");
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
    // CONTENT
    // ===================================================================
    private JPanel buildContent() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(C_BG_PAGE);
        wrapper.setBorder(new EmptyBorder(28, 32, 32, 32));
 
        // Section header
        wrapper.add(buildSectionHeader(), BorderLayout.NORTH);
 
        // Card
        JPanel card = buildTableCard();
        wrapper.add(card, BorderLayout.CENTER);
 
        return wrapper;
    }
 
    private JPanel buildSectionHeader() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(0, 0, 20, 0));
 
        JLabel t = new JLabel("Daftar Kos Anda");
        t.setFont(FONT_H1);
        t.setForeground(C_TEXT_PRIM);
        t.setAlignmentX(LEFT_ALIGNMENT);
 
        JLabel s = new JLabel("Tambah, edit, atau hapus properti kos yang Anda kelola");
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
 
    // ===================================================================
    // TABLE CARD
    // ===================================================================
    private JPanel buildTableCard() {
        JPanel card = new JPanel(new BorderLayout(0, 0)) {
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
 
        // ── Toolbar (top of card) ─────────────────────────────────────
        JPanel toolbar = buildToolbar();
        card.add(toolbar, BorderLayout.NORTH);
 
        // ── Table ─────────────────────────────────────────────────────
        model = new DefaultTableModel() {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        String[] cols = {"ID", "Nama Kos", "Deskripsi", "Kota", "Alamat", "Tipe", "Harga", "Aturan"};
        for (String col : cols) model.addColumn(col);
 
        table = new JTable(model) {
            @Override public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (isRowSelected(row)) {
                    c.setBackground(C_TABLE_SEL);
                    c.setForeground(C_TABLE_SEL_TEXT);
                } else {
                    c.setBackground(row % 2 == 0 ? C_BG_CARD : C_TABLE_STRIPE);
                    c.setForeground(C_TEXT_PRIM);
                }
                if (c instanceof JLabel) ((JLabel) c).setBorder(new EmptyBorder(0, 12, 0, 12));
                return c;
            }
        };
        table.setFont(FONT_TABLE);
        table.setRowHeight(36);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFocusable(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 
        // Column widths
        int[] widths = {40, 160, 200, 90, 180, 70, 120, 180};
        for (int i = 0; i < widths.length; i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
 
        // Hide ID column
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);
 
        // Header style
        JTableHeader th = table.getTableHeader();
        th.setFont(FONT_TH);
        th.setBackground(C_TABLE_HEADER_BG);
        th.setForeground(C_ACCENT);
        th.setPreferredSize(new Dimension(0, 40));
        th.setBorder(new MatteBorder(0, 0, 1, 0, C_SIDEBAR_DIV));
        th.setReorderingAllowed(false);
        th.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setHorizontalAlignment(SwingConstants.LEFT);
            }
            @Override public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                setBackground(C_TABLE_HEADER_BG);
                setForeground(C_ACCENT);
                setFont(FONT_TH);
                setBorder(new EmptyBorder(0, 12, 0, 12));
                return this;
            }
        });
 
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new MatteBorder(1, 0, 0, 0, C_BORDER));
        scroll.setBackground(C_BG_CARD);
        scroll.getViewport().setBackground(C_BG_CARD);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
 
        card.add(scroll, BorderLayout.CENTER);
 
        // ── Footer (action buttons) ───────────────────────────────────
        JPanel footer = buildFooter();
        card.add(footer, BorderLayout.SOUTH);
 
        return card;
    }
 
    // ── Toolbar: title + search hint ─────────────────────────────────
    private JPanel buildToolbar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setOpaque(false);
        bar.setBorder(new EmptyBorder(16, 20, 14, 20));
 
        JLabel lbl = new JLabel("Data Kos");
        lbl.setFont(new Font("Georgia", Font.BOLD, 15));
        lbl.setForeground(C_TEXT_PRIM);
 
        JLabel hint = new JLabel("Klik baris untuk memilih");
        hint.setFont(FONT_SMALL);
        hint.setForeground(C_TEXT_SEC);
 
        bar.add(lbl,  BorderLayout.WEST);
        bar.add(hint, BorderLayout.EAST);
 
        // Bottom divider
        JPanel divider = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(C_BORDER);
                g.fillRect(0, 0, getWidth(), 1);
            }
        };
        divider.setOpaque(false);
        divider.setPreferredSize(new Dimension(0, 1));
 
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);
        wrap.add(bar,     BorderLayout.CENTER);
        wrap.add(divider, BorderLayout.SOUTH);
        return wrap;
    }
 
    // ── Footer: 4 action buttons ──────────────────────────────────────
    private JPanel buildFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.setBorder(new CompoundBorder(
            new MatteBorder(1, 0, 0, 0, C_BORDER),
            new EmptyBorder(14, 20, 14, 20)
        ));
 
        // Left group: Tambah
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        left.setOpaque(false);
        JButton btnTambah = accentButton("+ Tambah Kos");
        btnTambah.addActionListener(e -> { new form_kos().setVisible(true); });
        left.add(btnTambah);
 
        // Right group: Edit | Upload Foto | Hapus
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);
 
        JButton btnFoto  = neutralButton("\u2191  Upload Foto");
        JButton btnEdit  = neutralButton("\u270E  Edit");
        JButton btnHapus = dangerButton("\u2715  Hapus");
 
        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu!"); return; }
            int idKos = (int) model.getValueAt(row, 0);
            new form_kos(idKos).setVisible(true);
        });
 
        btnHapus.addActionListener(e -> hapusKos());
 
        btnFoto.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "Pilih kos terlebih dahulu!"); return; }
            int idKos = (int) model.getValueAt(row, 0);
            new p_upload_foto(idKos).setVisible(true);
        });
 
        right.add(btnFoto);
        right.add(btnEdit);
        right.add(btnHapus);
 
        footer.add(left,  BorderLayout.WEST);
        footer.add(right, BorderLayout.EAST);
        return footer;
    }
 
    // ===================================================================
    // BUTTONS
    // ===================================================================
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
        btn.setBorder(new EmptyBorder(9, 20, 9, 20));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
 
    private JButton neutralButton(String text) {
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
                g2.setColor(hov ? C_BTN_NEUTRAL_DARK : C_BTN_NEUTRAL);
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
        btn.setBorder(new EmptyBorder(9, 18, 9, 18));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
 
    private JButton dangerButton(String text) {
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
                g2.setColor(hov ? C_BTN_DANGER_DARK : C_BTN_DANGER);
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
        btn.setBorder(new EmptyBorder(9, 18, 9, 18));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
 
    // ===================================================================
    // LOAD DATA
    // ===================================================================
    private void loadData() {
        model.setRowCount(0);
        try {
            Connection conn = koneksi.connect();
            PreparedStatement pst = conn.prepareStatement(
                    "SELECT * FROM kos WHERE id_user=?");
            pst.setInt(1, session.id_user);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_kos"),
                    rs.getString("nama_kos"),
                    rs.getString("deskripsi"),
                    rs.getString("kota"),
                    rs.getString("alamat"),
                    rs.getString("tipe_kos"),
                    formatRupiah(rs.getDouble("harga")),
                    rs.getString("aturan_kos")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
 
    // ===================================================================
    // HAPUS KOS
    // ===================================================================
    private void hapusKos() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu!"); return; }
        int id = (int) model.getValueAt(row, 0);
 
        // Custom confirm dialog
        int confirm = JOptionPane.showConfirmDialog(this,
            "Yakin ingin menghapus kos ini?\nTindakan tidak bisa dibatalkan.",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
 
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection conn = koneksi.connect();
                PreparedStatement pst = conn.prepareStatement("DELETE FROM kos WHERE id_kos=?");
                pst.setInt(1, id);
                pst.executeUpdate();
                loadData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }
 
    // ===================================================================
    // UTILITIES
    // ===================================================================
    private String formatRupiah(double angka) {
        java.text.NumberFormat fmt = java.text.NumberFormat.getCurrencyInstance(
                new java.util.Locale("id", "ID"));
        return fmt.format(angka);
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
        java.awt.EventQueue.invokeLater(() -> new p_kelola_kos().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
