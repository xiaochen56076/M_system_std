package ADRAF.com.nk.frame;

import ADRAF.com.nk.bean.Medicine;
import ADRAF.com.nk.bean.Records;
import ADRAF.com.nk.bean.User;
import ADRAF.com.nk.dao.MedicineDao;
import ADRAF.com.nk.dao.RecordDao;
import ADRAF.com.nk.dao.UserDao;
import ADRAF.com.nk.datamodel.*;
import ADRAF.com.nk.tool.MmDialog;
import ADRAF.com.nk.tool.font;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
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
    private JTable patientTable;
    private JTable doctorTable;
    private JScrollPane memScrollPane;
    private JButton btnAddDrug;


    private JButton btnAddDoctor;
    private JButton btnAddAdmin;
    private JTable adminTable;
    private JTable feedbackTable;
    private JScrollPane feedbackScrollPane;

    public DisplayFrame_Administrator() {
        init();
        inittext();
        setVisible(true);
    }

    private void init() {
        setSize(1400, 800);
        setTitle("药物不良反应反馈平台(管理员模式)");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void inittext() {
        header = new JPanel();
        header.setBackground(new Color(70, 130, 180));
        header.setPreferredSize(new Dimension(0, 40));
        header.setLayout(new BorderLayout());

        title = new JLabel("药物不良反应反馈平台");
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

        initmenu();

        initsearch();
        add(initlrpanel());
        add(header, BorderLayout.NORTH);
    }

    private void initmenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu sysMenu = new JMenu("账户");
        JMenuItem logoutItem = new JMenuItem("退出登录");
        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "确认退出登录？", "退出", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new LoginFrame_Fir();
                    dispose();
                }
            }
        });
        sysMenu.add(logoutItem);
        menuBar.add(sysMenu);
        setJMenuBar(menuBar);
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
                if (word.isEmpty()) {
                    refreshTable(MedicineDao.getAllmedicine());
                } else {
                    refreshTable(MedicineDao.seacrchMedicine(word));
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

        btnAddDrug = new JButton("添加药品");
        btnAddDrug.setForeground(Color.BLACK);
        btnAddDrug.setFont(font.ft);
        btnAddDrug.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddMedicineDialog();
            }
        });
        topPanel.add(btnAddDrug);


        memTable = new JTable(new AMmodel(MedicineDao.getAllmedicine()));
        memstyle();

        memTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = memTable.columnAtPoint(e.getPoint());
                int row = memTable.rowAtPoint(e.getPoint());
                Medicine m = ((AMmodel) memTable.getModel()).getAMecordrow(row);
                if (col == 4) {
                    showEditMedicineDialog(m);
                } else if (col == 5) {
                    int confirm = JOptionPane.showConfirmDialog(patientTable, "确定要删除药品 " + m.getName() + " 吗？", "确认删除", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        MedicineDao.deleteMedicine(m);
                        refreshmemTable(MedicineDao.getAllmedicine());
                        JOptionPane.showMessageDialog(null, "删除成功");
                    }
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

    private void refreshmemTable(List<Medicine> list) {
        memTable.setModel(new AMmodel(list));
        memstyle();
    }

    private void showEditMedicineDialog(Medicine m) {
        JDialog dialog = new JDialog(this, "编辑药品", true);
        dialog.setSize(450, 320);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);
        dialog.setResizable(false);

        JLabel nameLabel = new JLabel("药品名称：");
        nameLabel.setBounds(20, 20, 80, 25);
        dialog.add(nameLabel);

        JTextField nameField = new JTextField(m.getName());
        nameField.setBounds(110, 20, 300, 25);
        dialog.add(nameField);

        JLabel adverseLabel = new JLabel("不良反应：");
        adverseLabel.setBounds(20, 60, 80, 25);
        dialog.add(adverseLabel);

        JTextArea adverseArea = new JTextArea(m.getAdverseReaction());
        adverseArea.setLineWrap(true);
        adverseArea.setWrapStyleWord(true);
        JScrollPane adverseScroll = new JScrollPane(adverseArea);
        adverseScroll.setBounds(110, 60, 300, 60);
        dialog.add(adverseScroll);

        JLabel contraLabel = new JLabel("禁忌：");
        contraLabel.setBounds(20, 135, 80, 25);
        dialog.add(contraLabel);

        JTextArea contraArea = new JTextArea(m.getContraindication());
        contraArea.setLineWrap(true);
        contraArea.setWrapStyleWord(true);
        JScrollPane contraScroll = new JScrollPane(contraArea);
        contraScroll.setBounds(110, 135, 300, 60);
        dialog.add(contraScroll);

        JButton saveBtn = new JButton("保存");
        saveBtn.setBounds(150, 230, 80, 30);
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m.setName(nameField.getText().trim());
                m.setAdverseReaction(adverseArea.getText().trim());
                m.setContraindication(contraArea.getText().trim());
                MedicineDao.updateMedicine(m);
                refreshmemTable(MedicineDao.getAllmedicine());
                JOptionPane.showMessageDialog(dialog, "修改成功");
                dialog.dispose();
            }
        });
        dialog.add(saveBtn);

        JButton cancelBtn = new JButton("取消");
        cancelBtn.setBounds(250, 230, 80, 30);
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.add(cancelBtn);

        dialog.setVisible(true);
    }

    private void showAddMedicineDialog() {
        JDialog dialog = new JDialog(this, "添加药品", true);
        dialog.setSize(450, 320);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);
        dialog.setResizable(false);

        JLabel nameLabel = new JLabel("药品名称：");
        nameLabel.setBounds(20, 20, 80, 25);
        dialog.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(110, 20, 300, 25);
        dialog.add(nameField);

        JLabel codeLabel = new JLabel("国字号：");
        codeLabel.setBounds(20, 60, 80, 25);
        dialog.add(codeLabel);

        JTextField codeField = new JTextField();
        codeField.setBounds(110, 60, 300, 25);
        dialog.add(codeField);

        JLabel adverseLabel = new JLabel("不良反应：");
        adverseLabel.setBounds(20, 100, 80, 25);
        dialog.add(adverseLabel);

        JTextArea adverseArea = new JTextArea();
        adverseArea.setLineWrap(true);
        adverseArea.setWrapStyleWord(true);
        JScrollPane adverseScroll = new JScrollPane(adverseArea);
        adverseScroll.setBounds(110, 100, 300, 60);
        dialog.add(adverseScroll);

        JLabel contraLabel = new JLabel("禁忌：");
        contraLabel.setBounds(20, 175, 80, 25);
        dialog.add(contraLabel);

        JTextArea contraArea = new JTextArea();
        contraArea.setLineWrap(true);
        contraArea.setWrapStyleWord(true);
        JScrollPane contraScroll = new JScrollPane(contraArea);
        contraScroll.setBounds(110, 175, 300, 60);
        dialog.add(contraScroll);

        JButton saveBtn = new JButton("添加");
        saveBtn.setBounds(150, 250, 80, 30);
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String code = codeField.getText().trim();
                String adverse = adverseArea.getText().trim();
                String contra = contraArea.getText().trim();

                if (name.isEmpty() || code.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "药品名称和国字号不能为空");
                    return;
                }

                Medicine m = new Medicine(code, name, adverse, contra);
                if (MedicineDao.insertMedicine(m)) {
                    JOptionPane.showMessageDialog(dialog, "添加成功");
                    refreshmemTable(MedicineDao.getAllmedicine());
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "添加失败，请检查国字号是否重复");
                }
            }
        });
        dialog.add(saveBtn);

        JButton cancelBtn = new JButton("取消");
        cancelBtn.setBounds(250, 250, 80, 30);
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.add(cancelBtn);

        dialog.setVisible(true);
    }


    //标签页部分
    private JPanel initUsermTable() {
        JPanel panel = new JPanel(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("患者管理", initPatientTable(UserDao.getPuser()));
        tabbedPane.add("医生管理", initDoctorTable(UserDao.getDuser()));
        tabbedPane.add("管理员管理", initAdminTable(UserDao.getAuser()));
        panel.add(tabbedPane);

        return panel;
    }


    //医生部分
    private JPanel initDoctorTable(List<User> list) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topPanel.setBackground(Color.WHITE);

        btnAddDoctor = new JButton("添加医生账号");
        btnAddDoctor.setFont(font.ft);

        topPanel.add(btnAddDoctor);

        doctorTable = new JTable(new ADPmodel(list));
        doctorstyle();


        doctorTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = doctorTable.columnAtPoint(e.getPoint());
                int row = doctorTable.rowAtPoint(e.getPoint());
                ADPmodel dm = (ADPmodel) doctorTable.getModel();
                User u = dm.getUserrow(row);
                if (col == 3) {
                    showDoctorEditDialog(u, dm);
                } else if (col == 4) {
                    JOptionPane.showMessageDialog(null, "重置密码为123456");
                    u.setPwd("123456");
                    UserDao.resetPwd(u, dm);
                    dm.fireTableDataChanged();
                } else if (col == 5) {
                    String newStatus = "启用".equals(u.getStatus()) ? "禁用" : "启用";
                    u.setStatus(newStatus);
                    JOptionPane.showMessageDialog(null, "医生:" + u.getName() + " 账号已" + newStatus);
                    UserDao.setStatus(u);
                }
                dm.fireTableDataChanged();
            }
        });


        btnAddDoctor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddDoctorDialog();
            }
        });

        JScrollPane userScrollPane = new JScrollPane(doctorTable);
        userScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        userScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(userScrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void doctorstyle() {
        doctorTable.setRowHeight(35);
        doctorTable.getTableHeader().setReorderingAllowed(false);
        doctorTable.getTableHeader().setResizingAllowed(false);
        doctorTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        doctorTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        doctorTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        doctorTable.getColumnModel().getColumn(3).setCellRenderer(new BtnRenderer());
        doctorTable.getColumnModel().getColumn(4).setCellRenderer(new BtnRenderer());
        doctorTable.getColumnModel().getColumn(5).setCellRenderer(new BtnRenderer());
    }

    private void showDoctorEditDialog(User u, AbstractTableModel model) {
        JDialog dialog = new JDialog(this, "编辑资料 - " + u.getName(), true);
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);

        String oldname = u.getName();
        JLabel nameLabel = new JLabel("姓名：");
        nameLabel.setBounds(30, 30, 50, 25);
        dialog.add(nameLabel);

        JTextField nameField = new JTextField(u.getName());
        nameField.setBounds(90, 30, 200, 25);
        dialog.add(nameField);

        JLabel pwdLabel = new JLabel("密码：");
        pwdLabel.setBounds(30, 70, 50, 25);
        dialog.add(pwdLabel);

        JTextField pwdField = new JTextField(u.getPwd());
        pwdField.setBounds(90, 70, 200, 25);
        dialog.add(pwdField);

        JButton saveBtn = new JButton("保存");
        saveBtn.setBounds(130, 120, 80, 30);
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = nameField.getText().trim();
                String newPwd = pwdField.getText().trim();
                if (newName.isEmpty() || newPwd.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "用户名和密码不能为空");
                    return;
                }
                if (!newName.equals(oldname) && UserDao.isUsernameExists(newName)) {
                    JOptionPane.showMessageDialog(dialog, "该用户名已存在");
                    return;
                }
                u.setName(newName);
                u.setPwd(newPwd);
                if (UserDao.updateUser(oldname, u)) {
                    JOptionPane.showMessageDialog(dialog, "保存成功");
                    dialog.dispose();
                    model.fireTableDataChanged();
                }
            }
        });
        dialog.add(saveBtn);

        dialog.setVisible(true);
    }

    private void showAddDoctorDialog() {
        JDialog dialog = new JDialog(this, "添加医生账号", true);
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);
        dialog.setResizable(false);

        JLabel userLabel = new JLabel("用户名：");
        userLabel.setBounds(30, 30, 60, 25);
        dialog.add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(100, 30, 200, 25);
        dialog.add(userField);

        JLabel pwdLabel = new JLabel("密码：");
        pwdLabel.setBounds(30, 70, 60, 25);
        dialog.add(pwdLabel);

        JPasswordField pwdField = new JPasswordField();
        pwdField.setBounds(100, 70, 200, 25);
        dialog.add(pwdField);

        JButton saveBtn = new JButton("添加");
        saveBtn.setBounds(130, 120, 80, 30);
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = userField.getText().trim();
                String pwd = new String(pwdField.getPassword()).trim();

                User newUser = new User();
                newUser.setName(name);
                newUser.setPwd(pwd);
                newUser.setRole("2");
                if (UserDao.adminInsertUser(newUser)) {
                    refreshDoctorTable();
                    JOptionPane.showMessageDialog(null, "添加成功");
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "添加失败，请重试");
                }
                refreshDoctorTable();
            }
        });
        dialog.add(saveBtn);

        JButton cancelBtn = new JButton("取消");
        cancelBtn.setBounds(220, 120, 80, 30);
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.add(cancelBtn);

        dialog.setVisible(true);
    }

    private void refreshDoctorTable() {
        doctorTable.setModel(new ADPmodel(UserDao.getDuser()));
        doctorstyle();
    }


    //患者部分
    private JPanel initPatientTable(List<User> list) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        patientTable = new JTable(new Pmodel(list));

        patientstyle();


        patientTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = patientTable.columnAtPoint(e.getPoint());
                int row = patientTable.rowAtPoint(e.getPoint());
                Pmodel um = (Pmodel) patientTable.getModel();
                User u = um.getUserrow(row);
                if (col == 4) {
                    showPatientEditDialog(u, um);
                } else if (col == 5) {
                    String newStatus = "启用".equals(u.getStatus()) ? "禁用" : "启用";
                    u.setStatus(newStatus);
                    JOptionPane.showMessageDialog(null, "患者:" + u.getName() + " 账号已" + newStatus);
                    UserDao.setStatus(u);
                    um.fireTableDataChanged();

                } else if (col == 6) {
                    int confirm = JOptionPane.showConfirmDialog(patientTable, "确定要删除患者 " + u.getName() + " 吗？", "确认删除", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        UserDao.deleteUser(u);
                        refreshpatienttable(UserDao.getPuser());
                        JOptionPane.showMessageDialog(null, "已删除患者：" + u.getName());
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

    private void refreshpatienttable(List<User> list) {
        patientTable.setModel(new Pmodel(list));
        patientstyle();
    }

    private void patientstyle() {
        patientTable.setRowHeight(35);
        patientTable.getTableHeader().setReorderingAllowed(false);
        patientTable.getTableHeader().setResizingAllowed(false);
        patientTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        patientTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        patientTable.getColumnModel().getColumn(2).setPreferredWidth(60);
        patientTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        patientTable.getColumnModel().getColumn(4).setCellRenderer(new BtnRenderer());
        patientTable.getColumnModel().getColumn(5).setCellRenderer(new BtnRenderer());
        patientTable.getColumnModel().getColumn(6).setCellRenderer(new BtnRenderer());
    }

    private void showPatientEditDialog(User u, AbstractTableModel model) {
        JDialog dialog = new JDialog(this, "编辑患者 - " + u.getName(), true);
        dialog.setSize(350, 220);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);
        dialog.setResizable(false);

        String oldname = u.getName();

        JLabel nameLabel = new JLabel("姓名：");
        nameLabel.setBounds(30, 30, 50, 25);
        dialog.add(nameLabel);

        JTextField nameField = new JTextField(u.getName());
        nameField.setBounds(90, 30, 200, 25);
        dialog.add(nameField);

        JLabel pwdLabel = new JLabel("密码：");
        pwdLabel.setBounds(30, 70, 50, 25);
        dialog.add(pwdLabel);

        JTextField pwdField = new JTextField(u.getPwd());
        pwdField.setBounds(90, 70, 200, 25);
        dialog.add(pwdField);

        JButton saveBtn = new JButton("保存");
        saveBtn.setBounds(130, 120, 80, 30);
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = nameField.getText().trim();
                String newPwd = pwdField.getText().trim();
                if (newName.isEmpty() || newPwd.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "用户名和密码不能为空");
                    return;
                }
                if (!newName.equals(oldname) && UserDao.isUsernameExists(newName)) {
                    JOptionPane.showMessageDialog(dialog, "该用户名已存在");
                    return;
                }
                u.setName(newName);
                u.setPwd(newPwd);
                if (UserDao.updateUser(oldname, u)) {
                    JOptionPane.showMessageDialog(dialog, "修改成功");
                    dialog.dispose();
                    model.fireTableDataChanged();
                }
            }
        });
        dialog.add(saveBtn);

        JButton cancelBtn = new JButton("取消");
        cancelBtn.setBounds(220, 120, 80, 30);
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.add(cancelBtn);

        dialog.setVisible(true);
    }


    //管理员部分
    private JPanel initAdminTable(List<User> list) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topPanel.setBackground(Color.WHITE);

        btnAddAdmin = new JButton("添加管理员账号");
        btnAddAdmin.setFont(font.ft);
        topPanel.add(btnAddAdmin);

        adminTable = new JTable(new AUmodel(list));
        adminstyle();

        adminTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = adminTable.columnAtPoint(e.getPoint());
                int row = adminTable.rowAtPoint(e.getPoint());
                AUmodel am = (AUmodel) adminTable.getModel();
                User u = am.getUserrow(row);
                if (col == 3) {
                    String newStatus = "禁用".equals(u.getStatus()) ? "启用" : "禁用";
                    u.setStatus(newStatus);
                    JOptionPane.showMessageDialog(null, "管理员:" + u.getName() + " 账号已" + newStatus);
                    UserDao.setStatus(u);
                    am.fireTableDataChanged();
                } else if (col == 4) {
                    int confirm = JOptionPane.showConfirmDialog(adminTable, "确定要删除管理员 " + u.getName() + " 吗？", "确认删除", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        if ("4".equals(u.getRole())) {
                            JOptionPane.showMessageDialog(null, "超级管理员不能删除");
                            return;
                        }
                        UserDao.deleteUser(u);
                        refreshAdminTable(UserDao.getAuser());
                        JOptionPane.showMessageDialog(null, "已删除管理员：" + u.getName());
                        am.fireTableDataChanged();
                    }
                }
            }
        });

        btnAddAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddAdminDialog();
            }
        });


        JScrollPane scrollPane = new JScrollPane(adminTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(topPanel, BorderLayout.NORTH);
        return panel;
    }

    private void refreshAdminTable(List<User> list) {
        adminTable.setModel(new AUmodel(list));
        adminstyle();
    }

    private void adminstyle() {
        adminTable.setRowHeight(35);
        adminTable.getTableHeader().setReorderingAllowed(false);
        adminTable.getTableHeader().setResizingAllowed(false);
        adminTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        adminTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        adminTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        adminTable.getColumnModel().getColumn(3).setCellRenderer(new BtnRenderer());
        adminTable.getColumnModel().getColumn(4).setCellRenderer(new BtnRenderer());
    }

    private void showAddAdminDialog() {
        JDialog dialog = new JDialog(this, "添加管理员账号", true);
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);
        dialog.setResizable(false);

        JLabel userLabel = new JLabel("用户名：");
        userLabel.setBounds(30, 30, 60, 25);
        dialog.add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(100, 30, 200, 25);
        dialog.add(userField);

        JLabel pwdLabel = new JLabel("密码：");
        pwdLabel.setBounds(30, 70, 60, 25);
        dialog.add(pwdLabel);

        JPasswordField pwdField = new JPasswordField();
        pwdField.setBounds(100, 70, 200, 25);
        dialog.add(pwdField);

        JButton saveBtn = new JButton("添加");
        saveBtn.setBounds(130, 120, 80, 30);
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = userField.getText().trim();
                String pwd = new String(pwdField.getPassword()).trim();
                if (name.isEmpty() || pwd.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "用户名和密码不能为空");
                    return;
                }
                if (UserDao.isUsernameExists(name)) {
                    JOptionPane.showMessageDialog(dialog, "该用户名已存在");
                    return;
                }
                User newUser = new User();
                newUser.setName(name);
                newUser.setPwd(pwd);
                newUser.setRole("3");
                if (UserDao.adminInsertUser(newUser)) {
                    refreshDoctorTable();
                    JOptionPane.showMessageDialog(null, "添加成功");
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "添加失败，请重试");
                }
                refreshAdminTable(UserDao.getAuser());
            }
        });
        dialog.add(saveBtn);

        JButton cancelBtn = new JButton("取消");
        cancelBtn.setBounds(220, 120, 80, 30);
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });


        dialog.add(cancelBtn);
        dialog.setVisible(true);
    }


    //审核界面
    private JPanel initFeedbackmTable() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("反馈管理");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 0));
        titleLabel.setFont(new Font("null", Font.BOLD, 22));
        panel.add(titleLabel, BorderLayout.NORTH);

        feedbackTable = new JTable(new ARmodel(RecordDao.getAllRecords()));
        feedbackstyle();


        feedbackTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = feedbackTable.columnAtPoint(e.getPoint());
                int row = feedbackTable.rowAtPoint(e.getPoint());
                ARmodel rm = (ARmodel) feedbackTable.getModel();
                Records r = rm.getARecordrow(row);
                if (col == 7) {
                    showFeedbackDetailDialog(r);
                }
            }
        });

        feedbackScrollPane = new JScrollPane(feedbackTable);
        feedbackScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        feedbackScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(feedbackScrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void feedbackstyle() {
        feedbackTable.setRowHeight(35);
        feedbackTable.getTableHeader().setReorderingAllowed(false);
        feedbackTable.getTableHeader().setResizingAllowed(false);
        feedbackTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        feedbackTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        feedbackTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        feedbackTable.getColumnModel().getColumn(3).setPreferredWidth(130);
        feedbackTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        feedbackTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        feedbackTable.getColumnModel().getColumn(6).setPreferredWidth(80);
        feedbackTable.getColumnModel().getColumn(7).setCellRenderer(new BtnRenderer());
    }

    private void showFeedbackDetailDialog(Records r) {
        JDialog dialog = new JDialog(this, "反馈详情", true);
        dialog.setSize(500, 480);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);
        dialog.setResizable(false);

        JLabel drugLabel = new JLabel("药品名称：");
        drugLabel.setBounds(20, 20, 80, 25);
        dialog.add(drugLabel);
        JLabel drugVal = new JLabel(r.getMeName());
        drugVal.setBounds(110, 20, 350, 25);
        drugVal.setFont(new Font("null", Font.BOLD, 14));
        dialog.add(drugVal);

        JLabel symLabel = new JLabel("不良反应：");
        symLabel.setBounds(20, 55, 80, 25);
        dialog.add(symLabel);
        JTextArea symArea = new JTextArea(r.getSymptom());
        symArea.setLineWrap(true);
        symArea.setWrapStyleWord(true);
        symArea.setEditable(false);
        JScrollPane symScroll = new JScrollPane(symArea);
        symScroll.setBounds(110, 55, 350, 50);
        dialog.add(symScroll);

        JLabel timeLabel = new JLabel("用药时间：");
        timeLabel.setBounds(20, 115, 80, 25);
        dialog.add(timeLabel);
        JLabel timeVal = new JLabel(r.getDays() + "天");
        timeVal.setBounds(110, 115, 350, 25);
        dialog.add(timeVal);

        JLabel reportLabel = new JLabel("上报时间：");
        reportLabel.setBounds(20, 145, 80, 25);
        dialog.add(reportLabel);
        JLabel reportVal = new JLabel(r.getReportTime());
        reportVal.setBounds(110, 145, 350, 25);
        dialog.add(reportVal);

        JLabel userLabel = new JLabel("上报用户：");
        userLabel.setBounds(20, 175, 80, 25);
        dialog.add(userLabel);
        JLabel userVal = new JLabel(r.getUsername() != null ? r.getUsername() : "未知");
        userVal.setBounds(110, 175, 350, 25);
        dialog.add(userVal);

        JLabel statusLabel = new JLabel("审核状态：");
        statusLabel.setBounds(20, 205, 80, 25);
        dialog.add(statusLabel);
        JLabel statusVal = new JLabel(r.getStatus() != null ? r.getStatus() : "待审核");
        statusVal.setBounds(110, 205, 350, 25);
        statusVal.setForeground(new Color(200, 100, 0));
        statusVal.setFont(new Font("null", Font.BOLD, 14));
        dialog.add(statusVal);

        JLabel docLabel = new JLabel("审核医生：");
        docLabel.setBounds(20, 235, 80, 25);
        dialog.add(docLabel);
        JLabel docVal = new JLabel(r.getDocname() != null ? r.getDocname() : "无");
        docVal.setBounds(110, 235, 350, 25);
        dialog.add(docVal);

        JLabel opinionLabel = new JLabel("审核意见：");
        opinionLabel.setBounds(20, 265, 80, 25);
        dialog.add(opinionLabel);
        JTextArea opinionArea = new JTextArea(r.getDoctorOpinion() != null ? r.getDoctorOpinion() : "无");
        opinionArea.setLineWrap(true);
        opinionArea.setWrapStyleWord(true);
        opinionArea.setEditable(false);
        JScrollPane opinionScroll = new JScrollPane(opinionArea);
        opinionScroll.setBounds(110, 265, 350, 55);
        dialog.add(opinionScroll);

        JButton closeBtn = new JButton("关闭");
        closeBtn.setBounds(210, 335, 80, 30);
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.add(closeBtn);

        dialog.setVisible(true);
    }


    public static void main(String[] args) {
        new DisplayFrame_Administrator();
    }
}
