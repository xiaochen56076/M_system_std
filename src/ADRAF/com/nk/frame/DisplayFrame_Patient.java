package ADRAF.com.nk.frame;

import ADRAF.com.nk.aimodel.AiChat;
import ADRAF.com.nk.bean.Medicine;
import ADRAF.com.nk.bean.Records;
import ADRAF.com.nk.dao.MedicineDao;
import ADRAF.com.nk.dao.RecordDao;
import ADRAF.com.nk.datamodel.Mmodel;
import ADRAF.com.nk.datamodel.Rmodel;
import ADRAF.com.nk.tool.MmDialog;
import ADRAF.com.nk.tool.RecDialog;
import ADRAF.com.nk.tool.UserStateTool;
import ADRAF.com.nk.tool.font;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class DisplayFrame_Patient extends JFrame {

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

    private JTextArea usertext;
    private JTextArea aitext;
    private JButton btn_ok;
    private JScrollPane aijsp;
    private JButton btn_reset;

    private JComboBox<String> drugCombo;
    private JTextField daystext;
    private JTextArea symptomtext;
    private JButton btnSubmit;
    private JTextField mSearch;
    private DefaultListModel<String> mModel;
    private JList<String> mList;

    private JTable recordTable;
    private JScrollPane recordjsp;

    public DisplayFrame_Patient() {
        init();
        inittext();
        setVisible(true);
    }

    private void init() {
        setSize(1400, 800);
        setTitle("药物不良反应查询反馈平台(患者模式)");
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
        JLabel wl = new JLabel("欢迎，" + UserStateTool.getUsername());
        wl.setFont(new Font("null", Font.PLAIN, 16));
        wl.setForeground(Color.WHITE);
        wl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        btn_panel.add(wl);

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
        JMenuItem pwdItem = new JMenuItem("修改密码");
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

        pwdItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("test");
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
        JButton btn2 = new JButton("不良反应上报");
        JButton btn3 = new JButton("AI自查");
        JButton btn4 = new JButton("我的记录");

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

        //查询药品
        JPanel querypage = new JPanel();
        querypage.setBackground(Color.white);
        querypage.setLayout(new BorderLayout());
        querypage.add(inittable(MedicineDao.getAllmedicine()));
        //上报
        JPanel reportpage = initReportPanel();
        reportpage.setBackground(Color.WHITE);
        reportpage.setLayout(new BorderLayout());
        //ai咨询
        JPanel aipage = new JPanel();
        aipage.setLayout(new BorderLayout());
        aipage.add(initaichat());
        //记录
        JPanel recordpage = new JPanel();
        recordpage.setLayout(new BorderLayout());
        recordpage.add(initrecordpanel(RecordDao.getMyRecords()));
        recordpage.setBackground(Color.WHITE);


        cardpaanel.add(querypage, "query");
        cardpaanel.add(reportpage, "report");
        cardpaanel.add(aipage, "queryai");
        cardpaanel.add(recordpage, "record");

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
                cardLayout.show(cardpaanel, "report");
            }
        });
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardpaanel, "queryai");
            }
        });
        btn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardpaanel, "record");
                refreshRecordTable(RecordDao.getMyRecords());
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

    //初始化药物表格
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

    //设置药品表格
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


    private JPanel initaichat() {
        JPanel query = new JPanel();
        query.setBackground(Color.WHITE);
        query.setLayout(null);

        JLabel labelTip = new JLabel("请描述你的症状，AI将分析可能相关的药物不良反应：");
        labelTip.setBounds(30, 20, 540, 30);
        labelTip.setFont(font.ft);

        usertext = new JTextArea();
        usertext.setBounds(30, 50, 800, 80);
        usertext.setFont(font.ft);
        usertext.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        btn_ok = new JButton("开始分析");
        btn_ok.setBounds(240, 140, 120, 35);
        btn_ok.setFont(font.ft);

        btn_reset = new JButton("清空重填");
        btn_reset.setBounds(500, 140, 120, 35);
        btn_reset.setFont(font.ft);

        JLabel labelResult = new JLabel("分析结果：");
        labelResult.setBounds(30, 185, 100, 30);
        labelResult.setFont(font.ft);

        aitext = new JTextArea();
        aitext.setFont(font.ft);
        aitext.setLineWrap(true);
        aitext.setWrapStyleWord(true);
        aitext.setEditable(false);

        aijsp = new JScrollPane(aitext);
        aijsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        aijsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        aijsp.setBounds(30, 215, 800, 400);


        btn_reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aitext.setText("");
                usertext.setText("");
            }
        });

        btn_ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String content = usertext.getText().trim();
                    if (content.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "请输入症状描述");
                        return;
                    }
                    String word = usertext.getText().trim();
                    SwingWorker<String, Object> worker = new SwingWorker<>() {
                        @Override
                        protected String doInBackground() throws Exception {
                            return AiChat.callai(word);
                        }

                        @Override
                        protected void done() {
                            try {
                                aitext.setText(get());
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    };
                    worker.execute();
                    aitext.setText("正在分析，请稍候...");

                    new Timer(1000, evt -> {
                        ((Timer) evt.getSource()).stop();
                    }).start();
                } catch (Exception ex) {
                    throw new RuntimeException(ex.getMessage());
                }
            }
        });

        query.add(labelTip);
        query.add(usertext);
        query.add(btn_ok);
        query.add(btn_reset);
        query.add(labelResult);
        query.add(aijsp);
        return query;
    }

    //上报部分
    private JPanel initReportPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("不良反应上报 ");
        titleLabel.setBounds(30, 20, 200, 30);
        titleLabel.setFont(font.ft);

        JLabel drugLabel = new JLabel("选择药品：");
        drugLabel.setBounds(30, 70, 100, 30);
        drugLabel.setFont(font.ft);

        //搜索选择
        mSearch = new JTextField(20);
        mSearch.setBounds(130, 70, 200, 30);
        mSearch.setFont(font.ft);


        mModel = new DefaultListModel<>();
        mList = new JList<>(mModel);
        JScrollPane mJsp = new JScrollPane(mList);
        mJsp.setBounds(130, 100, 200, 150);
        mJsp.setVisible(false);

        mSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                wordList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                wordList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                wordList();
            }


            public void wordList() {
                String word = mSearch.getText().trim();
                if (word.isEmpty()) {
                    mJsp.setVisible(false);
                    return;
                }
                mModel.clear();
                List<Medicine> result = MedicineDao.seacrchMedicine(word);
                if (result.isEmpty()) {
                    mModel.addElement("暂无匹配药品");
                } else {
                    for (Medicine m : result) {
                        mModel.addElement(m.getName());
                    }
                }
                mJsp.setVisible(true);
            }
        });

        mList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String selected = mList.getSelectedValue();
                if (selected != null && !selected.equals("暂无匹配药品")) {
                    mSearch.setText(selected);
                    mJsp.setVisible(false);
                }
            }
        });


        JLabel daysLabel = new JLabel("用药时长：");
        daysLabel.setBounds(360, 70, 100, 30);
        daysLabel.setFont(font.ft);

        daystext = new JTextField(10);
        daystext.setBounds(450, 70, 80, 30);
        daystext.setFont(font.ft);

        JLabel daysUnit = new JLabel("天");
        daysUnit.setBounds(535, 70, 30, 30);
        daysUnit.setFont(font.ft);

        JLabel symptomLabel = new JLabel("症状描述：");
        symptomLabel.setBounds(30, 120, 100, 30);
        symptomLabel.setFont(font.ft);

        symptomtext = new JTextArea();
        symptomtext.setBounds(30, 155, 600, 120);
        symptomtext.setFont(font.ft);
        symptomtext.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        symptomtext.setLineWrap(true);
        symptomtext.setWrapStyleWord(true);

        btnSubmit = new JButton("提交反馈");
        btnSubmit.setBounds(280, 300, 120, 40);
        btnSubmit.setFont(font.ft);

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String drug = mSearch.getText().trim();
                String days = daystext.getText().trim();
                String symptom = symptomtext.getText().trim();

                if (drug.isEmpty() || days.isEmpty() || symptom.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "请完整填写信息");
                    return;
                }

                Records r = new Records(drug, symptom, days);
                RecordDao.insertMyrecord(r);

                JOptionPane.showMessageDialog(null, "提交成功，可在'我的记录'查看进度", "提交成功", JOptionPane.INFORMATION_MESSAGE);
                daystext.setText("");
                symptomtext.setText("");
                mSearch.setText("");
            }
        });

        panel.add(titleLabel);
        panel.add(drugLabel);
        panel.add(mSearch);
        panel.add(mJsp);
        panel.add(daysLabel);
        panel.add(daystext);
        panel.add(daysUnit);
        panel.add(symptomLabel);
        panel.add(symptomtext);
        panel.add(btnSubmit);

        return panel;
    }

    private void refreshRecordTable(List<Records> list) {
        recordTable.setModel(new Rmodel(list));
        recordTable();
    }

    //初始化记录
    private JPanel initrecordpanel(List<Records> word) {

        JLabel titleLabel = new JLabel("我的记录");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 0));
        titleLabel.setFont(font.ft);


        recordTable = new JTable(new Rmodel(word));


        recordTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = recordTable.columnAtPoint(e.getPoint());
                int row = recordTable.rowAtPoint(e.getPoint());
                if (col == 5) {
                    Records r = ((Rmodel) recordTable.getModel()).getRecordrow(row);
                    new RecDialog(r);
                }
            }
        });

        recordjsp = new JScrollPane(recordTable);
        recordjsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        recordjsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel rpanel = new JPanel(new BorderLayout());
        rpanel.setBackground(Color.WHITE);
        rpanel.add(titleLabel, BorderLayout.NORTH);
        rpanel.add(recordjsp, BorderLayout.CENTER);
        return rpanel;
    }

    //设置记录表格
    private void recordTable() {
        recordTable.setRowHeight(35);
        recordTable.getTableHeader().setReorderingAllowed(false);
        recordTable.getTableHeader().setResizingAllowed(false);
        recordTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        recordTable.getColumnModel().getColumn(1).setPreferredWidth(250);
        recordTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        recordTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        recordTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        recordTable.getColumnModel().getColumn(5).setCellRenderer(new BtnRenderer());
    }

    public static void main(String[] args) {
        new DisplayFrame_Patient();
    }
}
