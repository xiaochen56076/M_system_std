package ADRAF.com.nk.demo;

import ADRAF.com.nk.bean.Medicine;
import ADRAF.com.nk.dao.MedicineDao;
import ADRAF.com.nk.datamodel.DMmodel;
import ADRAF.com.nk.datamodel.DRmodel;
import ADRAF.com.nk.datamodel.Mmodel;
import ADRAF.com.nk.tool.AddMmDialog;
import ADRAF.com.nk.tool.MmDialog;
import ADRAF.com.nk.tool.font;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
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

    private JTable memTable;
    private JScrollPane memScrollPane;
    private JButton btnAddDrug;

    // 患者管理
    private String[][] patientData = {
            {"患者1", "张患者", "138xxxx1234", "2025-01-10", "正常", ""},
            {"患者2", "李患者", "139xxxx5678", "2025-02-15", "正常", ""},
            {"患者3", "王患者", "137xxxx9012", "2025-03-20", "禁用", ""}
    };
    private JTable patientTable;
    // 医生管理
    private String[][] doctorData = {
            {"张医生", "张大明", "内科", "主任医师", "正常", ""},
            {"李医生", "李小华", "外科", "副主任医师", "正常", ""},
            {"王医生", "王小丽", "儿科", "主治医师", "禁用", ""
            }
    };
    private JTable doctorTable;
    // 管理员管理
    private String[][] adminData = {
            {"admin", "2025-06-10 09:30", "正常", ""},
            {"root", "2025-06-09 15:20", "正常", ""}
    };
    private JTable adminTable;

    private JTable feedbackTable;
    private JScrollPane feedbackScrollPane;
    private String[][] feedbackData = {
            {"阿莫西林", "皮疹", "张三", "01-15", "已通过", ""},
            {"布洛芬", "胃痛", "李四", "01-16", "待审核", ""},
            {"头孢拉定", "腹泻", "王五", "01-18", "已通过", ""},
            {"阿司匹林", "出血", "赵六", "01-20", "待审核", ""}
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

        JPanel mgmtpage = initMemTable();
        JPanel userpage = initUsermTable();
        JPanel feedbackpage = initFeedbackmTable();

        cardpaanel.add(querypage, "query");
        cardpaanel.add(mgmtpage, "mem");
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
                cardLayout.show(cardpaanel, "mem");
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




    private JPanel initMemTable() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("药品管理");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 0));
        titleLabel.setFont(new Font("null", Font.BOLD, 22));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topPanel.setBackground(Color.WHITE);

        btnAddDrug = new JButton("新增药品");
        btnAddDrug.setForeground(Color.BLACK);
        btnAddDrug.setFont(font.ft);
        btnAddDrug.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddMmDialog();
            }
        });
        topPanel.add(btnAddDrug);


        memTable = new JTable(new DMmodel(MedicineDao.getAllmedicine()));
        memstyle();

        memTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = memTable.columnAtPoint(e.getPoint());
                int row = memTable.rowAtPoint(e.getPoint());
                if (col == 4) {
                    System.out.println("test");
                }
                else if(col == 5){
                    System.out.println("test1");
                }
            }
        });

        memScrollPane = new JScrollPane(memTable);
        memScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        memScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(memScrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void memstyle() {
        memTable.setRowHeight(35);
        memTable.getTableHeader().setReorderingAllowed(false);
        memTable.getTableHeader().setResizingAllowed(false);
        memTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        memTable.getColumnModel().getColumn(1).setPreferredWidth(250);
        memTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        memTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        memTable.getColumnModel().getColumn(4).setCellRenderer(new BtnRenderer());
        memTable.getColumnModel().getColumn(5).setCellRenderer(new BtnRenderer());
    }

    private void refreshmemTable(){
        memTable.setModel(new Mmodel(MedicineDao.getAllmedicine()));
        memstyle();
    }






    private JPanel initUsermTable() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("用户管理");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 0));
        titleLabel.setFont(new Font("null", Font.BOLD, 22));
        panel.add(titleLabel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("患者管理", initPatientTab());
        tabbedPane.addTab("医生管理", initDoctorTab());
        tabbedPane.addTab("管理员管理", initAdminTab());

        panel.add(tabbedPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel initPatientTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        String[] columns = {"用户名", "姓名", "联系方式", "注册时间", "状态", "操作"};
        patientTable = new JTable(patientData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        styleUserTable(patientTable, new int[]{120, 100, 150, 120, 80, 80}, 5);

        patientTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = patientTable.columnAtPoint(e.getPoint());
                int row = patientTable.rowAtPoint(e.getPoint());
                if (col == 5) {
                    String username = patientData[row][0];
                    String currentStatus = patientData[row][4];
                    String[] options;
                    if ("正常".equals(currentStatus)) {
                        options = new String[]{"查看详情", "禁用", "删除"};
                    } else {
                        options = new String[]{"查看详情", "启用", "删除"};
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
                        } else if ("查看详情".equals(options[choice])) {
                            JOptionPane.showMessageDialog(panel,
                                    "用户名：" + username + "\n姓名：" + patientData[row][1] +
                                    "\n联系方式：" + patientData[row][2] + "\n注册时间：" + patientData[row][3] +
                                    "\n状态：" + currentStatus);
                        } else {
                            String newStatus = "禁用".equals(options[choice]) ? "禁用" : "正常";
                            patientData[row][4] = newStatus;
                            JOptionPane.showMessageDialog(panel, "用户「" + username + "」已" + newStatus);
                            refreshPatientTable();
                        }
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(patientTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel initDoctorTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topPanel.setBackground(Color.WHITE);
        JButton btnAddDoctor = new JButton("+ 新增医生账号");
        btnAddDoctor.setPreferredSize(new Dimension(150, 35));
        btnAddDoctor.setBackground(new Color(70, 130, 180));
        btnAddDoctor.setForeground(Color.WHITE);
        btnAddDoctor.setFont(font.ft);
        btnAddDoctor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog(DisplayFrame_Administrator.this, "新增医生账号", true);
                dialog.setSize(380, 280);
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
                dPanel.add(new JLabel("姓名："), gbc);
                gbc.gridx = 1;
                JTextField nameField = new JTextField(15);
                dPanel.add(nameField, gbc);

                gbc.gridx = 0; gbc.gridy = 3;
                dPanel.add(new JLabel("科室："), gbc);
                gbc.gridx = 1;
                JTextField deptField = new JTextField(15);
                dPanel.add(deptField, gbc);

                gbc.gridx = 0; gbc.gridy = 4;
                gbc.gridwidth = 2;
                gbc.anchor = GridBagConstraints.CENTER;
                JButton btnCreate = new JButton("创建");
                btnCreate.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ev) {
                        JOptionPane.showMessageDialog(dialog, "医生账号创建成功");
                        dialog.dispose();
                    }
                });
                dPanel.add(btnCreate, gbc);

                dialog.add(dPanel);
                dialog.setVisible(true);
            }
        });
        topPanel.add(btnAddDoctor);

        String[] columns = {"用户名", "姓名", "科室", "职称", "状态", "操作"};
        doctorTable = new JTable(doctorData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        styleUserTable(doctorTable, new int[]{100, 100, 120, 130, 80, 80}, 5);

        doctorTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = doctorTable.columnAtPoint(e.getPoint());
                int row = doctorTable.rowAtPoint(e.getPoint());
                if (col == 5) {
                    String username = doctorData[row][0];
                    String currentStatus = doctorData[row][4];
                    String[] options;
                    if ("正常".equals(currentStatus)) {
                        options = new String[]{"编辑资料", "重置密码", "禁用"};
                    } else {
                        options = new String[]{"编辑资料", "重置密码", "启用"};
                    }
                    int choice = JOptionPane.showOptionDialog(panel,
                            "医生：" + username,
                            "操作", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (choice >= 0) {
                        if ("禁用".equals(options[choice])) {
                            doctorData[row][4] = "禁用";
                            JOptionPane.showMessageDialog(panel, "医生「" + username + "」已禁用");
                            refreshDoctorTable();
                        } else if ("启用".equals(options[choice])) {
                            doctorData[row][4] = "正常";
                            JOptionPane.showMessageDialog(panel, "医生「" + username + "」已启用");
                            refreshDoctorTable();
                        } else if ("重置密码".equals(options[choice])) {
                            JOptionPane.showMessageDialog(panel, "已重置医生「" + username + "」的密码");
                        } else if ("编辑资料".equals(options[choice])) {
                            JOptionPane.showMessageDialog(panel,
                                    "编辑医生资料：\n用户名：" + username + "\n姓名：" + doctorData[row][1] +
                                    "\n科室：" + doctorData[row][2] + "\n职称：" + doctorData[row][3]);
                        }
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(doctorTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel initAdminTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topPanel.setBackground(Color.WHITE);
        JButton btnAddAdmin = new JButton("+ 新增管理员账号");
        btnAddAdmin.setPreferredSize(new Dimension(160, 35));
        btnAddAdmin.setBackground(new Color(70, 130, 180));
        btnAddAdmin.setForeground(Color.WHITE);
        btnAddAdmin.setFont(font.ft);
        btnAddAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog(DisplayFrame_Administrator.this, "新增管理员账号", true);
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
                        JOptionPane.showMessageDialog(dialog, "管理员账号创建成功");
                        dialog.dispose();
                    }
                });
                dPanel.add(btnCreate, gbc);

                dialog.add(dPanel);
                dialog.setVisible(true);
            }
        });
        topPanel.add(btnAddAdmin);

        String[] columns = {"用户名", "最后登录时间", "状态", "操作"};
        adminTable = new JTable(adminData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        styleUserTable(adminTable, new int[]{120, 180, 80, 80}, 3);

        adminTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = adminTable.columnAtPoint(e.getPoint());
                int row = adminTable.rowAtPoint(e.getPoint());
                if (col == 3) {
                    String username = adminData[row][0];
                    String currentStatus = adminData[row][2];
                    if ("admin".equals(username)) {
                        JOptionPane.showMessageDialog(panel, "不能对超级管理员执行操作");
                        return;
                    }
                    String[] options;
                    if ("正常".equals(currentStatus)) {
                        options = new String[]{"禁用", "删除"};
                    } else {
                        options = new String[]{"启用", "删除"};
                    }
                    int choice = JOptionPane.showOptionDialog(panel,
                            "管理员：" + username,
                            "操作", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (choice >= 0) {
                        if ("删除".equals(options[choice])) {
                            int confirm = JOptionPane.showConfirmDialog(panel,
                                    "确定要删除管理员「" + username + "」吗？",
                                    "确认删除", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                JOptionPane.showMessageDialog(panel, "已删除管理员：" + username);
                            }
                        } else {
                            String newStatus = "禁用".equals(options[choice]) ? "禁用" : "正常";
                            adminData[row][2] = newStatus;
                            JOptionPane.showMessageDialog(panel, "管理员「" + username + "」已" + newStatus);
                            refreshAdminTable();
                        }
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(adminTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void styleUserTable(JTable table, int[] colWidths, int actionCol) {
        table.setRowHeight(35);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        for (int i = 0; i < colWidths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(colWidths[i]);
        }
        table.getColumnModel().getColumn(actionCol).setCellRenderer(new BtnRenderer());
    }

    private void refreshPatientTable() {
        String[] columns = {"用户名", "姓名", "联系方式", "注册时间", "状态", "操作"};
        patientTable.setModel(new DefaultTableModel(patientData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        });
        styleUserTable(patientTable, new int[]{120, 100, 150, 120, 80, 80}, 5);
    }

    private void refreshDoctorTable() {
        String[] columns = {"用户名", "姓名", "科室", "职称", "状态", "操作"};
        doctorTable.setModel(new DefaultTableModel(doctorData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        });
        styleUserTable(doctorTable, new int[]{100, 100, 120, 130, 80, 80}, 5);
    }

    private void refreshAdminTable() {
        String[] columns = {"用户名", "最后登录时间", "状态", "操作"};
        adminTable.setModel(new DefaultTableModel(adminData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        });
        styleUserTable(adminTable, new int[]{120, 180, 80, 80}, 3);
    }


    private JPanel initFeedbackmTable() {
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
