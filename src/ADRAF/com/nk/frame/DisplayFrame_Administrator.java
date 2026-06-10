package ADRAF.com.nk.frame;

import ADRAF.com.nk.bean.Medicine;
import ADRAF.com.nk.bean.User;
import ADRAF.com.nk.dao.MedicineDao;
import ADRAF.com.nk.dao.UserDao;
import ADRAF.com.nk.datamodel.*;
import ADRAF.com.nk.tool.AddMmDialog;
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
    private JScrollPane memScrollPane;
    private JButton btnAddDrug;

    private JButton btnAddDoctor;

    private JTable feedbackTable;
    private JScrollPane feedbackScrollPane;
    private String[][] feedbackData = {
            {"阿莫西林", "皮疹", "患者", "01-15", "未通过", ""},
            {"布洛芬", "胃痛", "患者", "01-16", "已通过", ""},
            {"头孢拉定", "腹泻", "患者", "01-18", "未通过", ""},
            {"阿司匹林", "呕血", "患者", "01-20", "已通过", ""}
    };

    public DisplayFrame_Administrator() {
        init();
        inittext();
        setVisible(true);
    }

    private void init() {
        setSize(1400, 800);
        setTitle("药物不良反应咨询平台(管理员模式)");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void inittext() {
        header = new JPanel();
        header.setBackground(new Color(70, 130, 180));
        header.setPreferredSize(new Dimension(0, 40));
        header.setLayout(new BorderLayout());

        title = new JLabel("药物不良反应咨询平台");
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

        btnAddDrug = new JButton("添加药品");
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




    private JPanel initUsermTable(){
        JPanel panel = new JPanel(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("患者管理", initPatientTable(UserDao.));
        tabbedPane.add("医生管理", initDoctorTable(UserDao.));
        tabbedPane.add("管理员管理", initAdminTable(UserDao.));
        panel.add(tabbedPane);

        return panel;
    }




    private JPanel initDoctorTable(List<User> list) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topPanel.setBackground(Color.WHITE);

        btnAddDoctor = new JButton("添加医生账号");
        btnAddDoctor.setFont(font.ft);

        topPanel.add(btnAddDoctor);

        JTable userTable = new JTable(new Dmodel(list));

        userTable.setRowHeight(35);
        userTable.getTableHeader().setReorderingAllowed(false);
        userTable.getTableHeader().setResizingAllowed(false);
        userTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        userTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        userTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        userTable.getColumnModel().getColumn(3).setCellRenderer(new BtnRenderer());
        userTable.getColumnModel().getColumn(4).setCellRenderer(new BtnRenderer());
        userTable.getColumnModel().getColumn(5).setCellRenderer(new BtnRenderer());

        userTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = userTable.columnAtPoint(e.getPoint());
                int row = userTable.rowAtPoint(e.getPoint());
                Dmodel dm = (Dmodel)userTable.getModel();
                User u = dm.getUserrow(row);
                if (col == 3) {
                    showDoctorEditDialog(u, dm);
                } else if (col == 4) {
                    JOptionPane.showMessageDialog(null, "重置密码:" + u.getName());
                } else if (col == 5) {
                    String newStatus = "启用".equals(u.getStatus()) ? "禁用" : "启用";
                    u.setStatus(newStatus);
                    JOptionPane.showMessageDialog(null, "医生:" + u.getName() + " 账号已" + newStatus);
                    dm.fireTableDataChanged();
                }
            }
        });

        JScrollPane userScrollPane = new JScrollPane(userTable);
        userScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        userScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(userScrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void showDoctorEditDialog(User u, AbstractTableModel model) {
        JDialog dialog = new JDialog(this, "编辑资料 - " + u.getName(), true);
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);

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
                u.setName(nameField.getText().trim());
                u.setPwd(pwdField.getText().trim());
                JOptionPane.showMessageDialog(dialog, "保存成功");
                dialog.dispose();
                model.fireTableDataChanged();
            }
        });
        dialog.add(saveBtn);

        dialog.setVisible(true);
    }




    private JPanel initPatientTable(List<User> list) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JTable patientTable = new JTable(new Umodel(list));
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

        patientTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = patientTable.columnAtPoint(e.getPoint());
                int row = patientTable.rowAtPoint(e.getPoint());
                Umodel um = (Umodel)patientTable.getModel();
                User u = um.getUserrow(row);
                if (col == 4) {
                    showPatientDetail(u.getName());
                } else if (col == 5) {
                    JOptionPane.showMessageDialog(null, "患者:" + u.getName() + " 账号已禁用");
                } else if (col == 6) {
                    int confirm = JOptionPane.showConfirmDialog(patientTable,
                            "确定要删除患者 " + u.getName() + " 吗？",
                            "确认删除", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
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

    private void showPatientDetail(String username) {
        JDialog dialog = new JDialog(this, "患者详情 - " + username, true);
        dialog.setSize(500, 300);
        dialog.setLocationRelativeTo(this);

        String[][] data = {};
        String[] columns = {"药品", "症状", "时间", "状态"};
        JTable table = new JTable(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setRowHeight(30);
        table.getTableHeader().setReorderingAllowed(false);

        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        content.add(new JLabel("反馈记录", SwingConstants.LEFT), BorderLayout.NORTH);
        content.add(new JScrollPane(table), BorderLayout.CENTER);

        dialog.add(content);
        dialog.setVisible(true);
    }




    private JPanel initAdminTable(List<User> list) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JTable adminTable = new JTable(new Amodel(list));
        adminTable.setRowHeight(35);
        adminTable.getTableHeader().setReorderingAllowed(false);
        adminTable.getTableHeader().setResizingAllowed(false);
        adminTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        adminTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        adminTable.getColumnModel().getColumn(2).setCellRenderer(new BtnRenderer());
        adminTable.getColumnModel().getColumn(3).setCellRenderer(new BtnRenderer());

        adminTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = adminTable.columnAtPoint(e.getPoint());
                int row = adminTable.rowAtPoint(e.getPoint());
                Amodel am = (Amodel)adminTable.getModel();
                User u = am.getUserrow(row);
                if (col == 2) {
                    JOptionPane.showMessageDialog(null, "管理员:" + u.getName() + " 账号已禁用");
                } else if (col == 3) {
                    int confirm = JOptionPane.showConfirmDialog(adminTable,
                            "确定要删除管理员 " + u.getName() + " 吗？",
                            "确认删除", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(null, "已删除管理员：" + u.getName());
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(adminTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }




    private JPanel initFeedbackmTable() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("反馈管理");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 0));
        titleLabel.setFont(new Font("null", Font.BOLD, 22));
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] fbColumns = {"药品", "症状", "用户", "时间", "状态", "操作"};
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
                            "确定要删除该反馈记录？药品：" + feedbackData[row][0],
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
