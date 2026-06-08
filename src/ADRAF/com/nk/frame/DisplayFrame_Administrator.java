package ADRAF.com.nk.frame;

import ADRAF.com.nk.bean.Medicine;
import ADRAF.com.nk.dao.MedicineDao;
import ADRAF.com.nk.datamodel.Mmodel;
import ADRAF.com.nk.tool.MmDialog;
import ADRAF.com.nk.tool.font;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class DisplayFrame_Administrator extends JFrame {

    private JTextField jTextField;
    private JLabel title;
    private JPanel header;
    private JPanel search_area;
    private JButton btn_search;
    private JScrollPane scrollPane;
    private AbstractTableModel tableModel;
    private JTable resultTable;
    private JPanel btn_panel;
    private JPanel leftpanel;
    private CardLayout cardLayout;
    private JPanel cardpaanel;

    private JTable mgmtTable;
    private JScrollPane mgmtScrollPane;
    private JButton btnAddDrug;

    private JTable userTable;
    private JScrollPane userScrollPane;
    private JButton btnAddDoctor;
    private String[][] userData = {
            {"张三", "患者", "正常"},
            {"李四", "患者", "正常"},
            {"王五", "患者", "禁用"},
            {"李医生", "医护", "正常"}
    };

    private JTable feedbackTable;
    private JScrollPane feedbackScrollPane;
    private String[][] feedbackData = {
            {"阿莫西林", "皮疹", "张三", "01-15", "已通过"},
            {"布洛芬", "胃痛", "李四", "01-16", "待审核"},
            {"头孢拉定", "腹泻", "王五", "01-18", "已通过"},
            {"阿司匹林", "出血", "赵六", "01-20", "待审核"}
    };

    public DisplayFrame_Administrator() {
        init();
        inittext();
        setVisible(true);
    }

    private void init() {
        setSize(1400, 800);
        setTitle("药物不良反应查询分析平台(管理员模式)");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void inittext() {
        header = new JPanel();
        header.setBackground(new Color(70, 130, 180));
        header.setPreferredSize(new Dimension(0, 40));
        header.setLayout(new BorderLayout());

        title = new JLabel("药物不良反应查询反馈平台");
        title.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        title.setFont(new Font("null", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        btn_panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btn_panel.setOpaque(false);
        JLabel adminLabel = new JLabel("admin");
        adminLabel.setFont(new Font("null", Font.PLAIN, 16));
        adminLabel.setForeground(Color.WHITE);
        adminLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        btn_panel.add(adminLabel);

        header.add(btn_panel, BorderLayout.EAST);
        header.add(title, BorderLayout.WEST);

        initsearch();
        add(initlrpanel());
        add(header, BorderLayout.NORTH);
    }

    private JSplitPane initlrpanel() {
        leftpanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        leftpanel.setBackground(Color.WHITE);

        JButton btn1 = new JButton("药品查询");
        JButton btn2 = new JButton("药品管理");
        JButton btn3 = new JButton("用户管理");
        JButton btn4 = new JButton("反馈管理");

        btn1.setPreferredSize(new Dimension(180, 60));
        btn2.setPreferredSize(new Dimension(180, 60));
        btn3.setPreferredSize(new Dimension(180, 60));
        btn4.setPreferredSize(new Dimension(180, 60));

        leftpanel.add(btn1);
        leftpanel.add(btn2);
        leftpanel.add(btn3);
        leftpanel.add(btn4);

        cardLayout = new CardLayout();
        cardpaanel = new JPanel(cardLayout);

        JPanel querypage = new JPanel();
        querypage.setBackground(Color.white);
        querypage.setLayout(new BorderLayout());
        querypage.add(inittable(MedicineDao.getAllmedicine()));

        JPanel mgmtpage = initDrugMgmtPanel();
        JPanel userpage = initUserMgmtPanel();
        JPanel feedbackpage = initFeedbackMgmtPanel();

        cardpaanel.add(querypage, "query");
        cardpaanel.add(mgmtpage, "mgmt");
        cardpaanel.add(userpage, "user");
        cardpaanel.add(feedbackpage, "feedback");

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftpanel, cardpaanel);
        splitPane.setDividerLocation(180);
        splitPane.setDividerSize(2);
        splitPane.setEnabled(false);

        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardpaanel, "query");
            }
        });
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardpaanel, "mgmt");
            }
        });
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardpaanel, "user");
            }
        });
        btn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardpaanel, "feedback");
            }
        });

        return splitPane;
    }

    private void initsearch() {
        search_area = new JPanel();
        jTextField = new JTextField(30);
        jTextField.setPreferredSize(new Dimension(80, 28));
        btn_search = new JButton("查询");
        btn_search.setPreferredSize(new Dimension(80, 28));
        btn_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String word = jTextField.getText().trim();
                refreshTable(MedicineDao.seacrchMedicine(word));
                if (word.isEmpty()) {
                    refreshTable(MedicineDao.getAllmedicine());
                }
            }
        });
        search_area.add(jTextField);
        search_area.add(btn_search);
    }

    private void refreshTable(List<Medicine> list) {
        resultTable.setModel(new Mmodel(list));
        table();
    }

    private JPanel inittable(List<Medicine> word) {
        tableModel = new Mmodel(word);
        resultTable = new JTable(tableModel);
        table();

        resultTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = resultTable.columnAtPoint(e.getPoint());
                int row = resultTable.rowAtPoint(e.getPoint());
                if (col == 4) {
                    Medicine m = ((Mmodel) resultTable.getModel()).getMedicinerow(row);
                    new MmDialog(m);
                }
            }
        });

        scrollPane = new JScrollPane(resultTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(search_area, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        return centerPanel;
    }

    private void table() {
        resultTable.setRowHeight(35);
        resultTable.getTableHeader().setReorderingAllowed(false);
        resultTable.getTableHeader().setResizingAllowed(false);
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(45);
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        resultTable.getColumnModel().getColumn(2).setPreferredWidth(250);
        resultTable.getColumnModel().getColumn(3).setPreferredWidth(200);
        resultTable.getColumnModel().getColumn(4).setCellRenderer(new BtnRenderer());
    }

    public class BtnRenderer extends JButton implements TableCellRenderer {
        public BtnRenderer() {
            setOpaque(true);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int col) {
            setText(value == null ? "" : value.toString());
            if ("".equals(value)) {
                setText("查看详情");
            }
            return this;
        }
    }

    // ===================== 药品管理 =====================
    private JPanel initDrugMgmtPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("药品管理");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 0));
        titleLabel.setFont(new Font("null", Font.BOLD, 22));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topPanel.setBackground(Color.WHITE);

        btnAddDrug = new JButton("+ 新增药品");
        btnAddDrug.setPreferredSize(new Dimension(120, 35));
        btnAddDrug.setBackground(new Color(60, 179, 113));
        btnAddDrug.setForeground(Color.WHITE);
        btnAddDrug.setFont(font.ft);
        btnAddDrug.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDrugEditDialog(null);
            }
        });
        topPanel.add(btnAddDrug);

        List<Medicine> allMeds = MedicineDao.getAllmedicine();
        String[] mgmtColumns = {"药品名", "常见不良反应", "禁忌", "操作"};
        Object[][] mgmtData = new Object[allMeds.size()][4];
        for (int i = 0; i < allMeds.size(); i++) {
            Medicine m = allMeds.get(i);
            mgmtData[i][0] = m.getName();
            mgmtData[i][1] = m.getAdverseReaction();
            mgmtData[i][2] = m.getContraindication();
            mgmtData[i][3] = "修改/删除";
        }

        mgmtTable = new JTable(mgmtData, mgmtColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        mgmtTable.setRowHeight(35);
        mgmtTable.getTableHeader().setReorderingAllowed(false);
        mgmtTable.getTableHeader().setResizingAllowed(false);
        mgmtTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        mgmtTable.getColumnModel().getColumn(1).setPreferredWidth(250);
        mgmtTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        mgmtTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        mgmtTable.getColumnModel().getColumn(3).setCellRenderer(new BtnRenderer());

        List<Medicine> medRef = allMeds;
        mgmtTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = mgmtTable.columnAtPoint(e.getPoint());
                int row = mgmtTable.rowAtPoint(e.getPoint());
                if (col == 3) {
                    String[] options = {"修改", "删除"};
                    int choice = JOptionPane.showOptionDialog(panel,
                            "药品：" + mgmtTable.getValueAt(row, 0),
                            "操作", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (choice == 0) {
                        showDrugEditDialog(medRef.get(row));
                    } else if (choice == 1) {
                        int confirm = JOptionPane.showConfirmDialog(panel,
                                "确定要删除「" + mgmtTable.getValueAt(row, 0) + "」吗？",
                                "确认删除", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            JOptionPane.showMessageDialog(panel, "已删除：" + mgmtTable.getValueAt(row, 0));
                        }
                    }
                }
            }
        });

        mgmtScrollPane = new JScrollPane(mgmtTable);
        mgmtScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mgmtScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(mgmtScrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void showDrugEditDialog(Medicine medicine) {
        boolean isNew = (medicine == null);
        JDialog dialog = new JDialog(this, isNew ? "新增药品" : "修改药品", true);
        dialog.setSize(450, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("药品名："), gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(20);
        if (!isNew) nameField.setText(medicine.getName());
        panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("常见不良反应："), gbc);
        gbc.gridx = 1;
        JTextField adverseField = new JTextField(20);
        if (!isNew) adverseField.setText(medicine.getAdverseReaction());
        panel.add(adverseField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("禁忌："), gbc);
        gbc.gridx = 1;
        JTextArea contraArea = new JTextArea(3, 20);
        contraArea.setLineWrap(true);
        contraArea.setWrapStyleWord(true);
        if (!isNew) contraArea.setText(medicine.getContraindication());
        JScrollPane csp = new JScrollPane(contraArea);
        csp.setPreferredSize(new Dimension(250, 60));
        panel.add(csp, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel btnPanel = new JPanel(new FlowLayout());

        JButton btnSave = new JButton("保存");
        btnSave.setBackground(new Color(60, 179, 113));
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "药品名不能为空");
                    return;
                }
                JOptionPane.showMessageDialog(dialog,
                        isNew ? "新增药品成功" : "修改药品成功");
                dialog.dispose();
            }
        });

        JButton btnCancel = new JButton("取消");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        panel.add(btnPanel, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    // ===================== 用户管理 =====================
    private JPanel initUserMgmtPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("用户管理");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 0));
        titleLabel.setFont(new Font("null", Font.BOLD, 22));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topPanel.setBackground(Color.WHITE);

        btnAddDoctor = new JButton("+ 新增医护账号");
        btnAddDoctor.setPreferredSize(new Dimension(150, 35));
        btnAddDoctor.setBackground(new Color(70, 130, 180));
        btnAddDoctor.setForeground(Color.WHITE);
        btnAddDoctor.setFont(font.ft);
        btnAddDoctor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog(DisplayFrame_Administrator.this, "新增医护账号", true);
                dialog.setSize(350, 200);
                dialog.setLocationRelativeTo(DisplayFrame_Administrator.this);

                JPanel dPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(5, 10, 5, 10);

                gbc.gridx = 0; gbc.gridy = 0;
                dPanel.add(new JLabel("用户名："), gbc);
                gbc.gridx = 1;
                JTextField userField = new JTextField(15);
                dPanel.add(userField, gbc);

                gbc.gridx = 0; gbc.gridy = 1;
                dPanel.add(new JLabel("密码："), gbc);
                gbc.gridx = 1;
                JPasswordField passField = new JPasswordField(15);
                dPanel.add(passField, gbc);

                gbc.gridx = 0; gbc.gridy = 2;
                gbc.gridwidth = 2;
                gbc.anchor = GridBagConstraints.CENTER;
                JButton btnCreate = new JButton("创建");
                btnCreate.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ev) {
                        JOptionPane.showMessageDialog(dialog, "医护账号创建成功");
                        dialog.dispose();
                    }
                });
                dPanel.add(btnCreate, gbc);

                dialog.add(dPanel);
                dialog.setVisible(true);
            }
        });

        topPanel.add(btnAddDoctor);

        String[] userColumns = {"用户名", "角色", "状态", "操作"};
        userTable = new JTable(userData, userColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        userTable.setRowHeight(35);
        userTable.getTableHeader().setReorderingAllowed(false);
        userTable.getTableHeader().setResizingAllowed(false);
        userTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        userTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        userTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        userTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        userTable.getColumnModel().getColumn(3).setCellRenderer(new BtnRenderer());

        userTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = userTable.columnAtPoint(e.getPoint());
                int row = userTable.rowAtPoint(e.getPoint());
                if (col == 3) {
                    String username = userData[row][0];
                    String currentStatus = userData[row][2];
                    String[] options;
                    if ("正常".equals(currentStatus)) {
                        options = new String[]{"禁用", "删除"};
                    } else {
                        options = new String[]{"启用", "删除"};
                    }
                    int choice = JOptionPane.showOptionDialog(panel,
                            "用户：" + username,
                            "操作", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (choice >= 0) {
                        if ("删除".equals(options[choice])) {
                            int confirm = JOptionPane.showConfirmDialog(panel,
                                    "确定要删除用户「" + username + "」吗？",
                                    "确认删除", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                JOptionPane.showMessageDialog(panel, "已删除用户：" + username);
                            }
                        } else {
                            String newStatus = "禁用".equals(options[choice]) ? "禁用" : "正常";
                            userData[row][2] = newStatus;
                            JOptionPane.showMessageDialog(panel, "用户「" + username + "」已" + newStatus);
                            refreshUserTable();
                        }
                    }
                }
            }
        });

        userScrollPane = new JScrollPane(userTable);
        userScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        userScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(userScrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void refreshUserTable() {
        String[] userColumns = {"用户名", "角色", "状态", "操作"};
        userTable.setModel(new javax.swing.table.DefaultTableModel(userData, userColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < userTable.getColumnCount(); i++) {
            userTable.getColumnModel().getColumn(i).setCellRenderer(r);
        }
        userTable.getColumnModel().getColumn(3).setCellRenderer(new BtnRenderer());
    }

    // ===================== 反馈管理 =====================
    private JPanel initFeedbackMgmtPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("反馈管理");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 0));
        titleLabel.setFont(new Font("null", Font.BOLD, 22));
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] fbColumns = {"药品", "症状", "患者", "时间", "状态", "操作"};
        feedbackTable = new JTable(feedbackData, fbColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        feedbackTable.setRowHeight(35);
        feedbackTable.getTableHeader().setReorderingAllowed(false);
        feedbackTable.getTableHeader().setResizingAllowed(false);
        feedbackTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        feedbackTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        feedbackTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        feedbackTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        feedbackTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        feedbackTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        feedbackTable.getColumnModel().getColumn(5).setCellRenderer(new BtnRenderer());

        feedbackTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = feedbackTable.columnAtPoint(e.getPoint());
                int row = feedbackTable.rowAtPoint(e.getPoint());
                if (col == 5) {
                    int confirm = JOptionPane.showConfirmDialog(panel,
                            "确定要删除这条反馈记录吗？药品：" + feedbackData[row][0],
                            "确认删除", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(panel, "已删除反馈记录");
                    }
                }
            }
        });

        feedbackScrollPane = new JScrollPane(feedbackTable);
        feedbackScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        feedbackScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(feedbackScrollPane, BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        new DisplayFrame_Administrator();
    }
}
