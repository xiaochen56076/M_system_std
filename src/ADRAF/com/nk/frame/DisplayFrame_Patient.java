package ADRAF.com.nk.frame;

import ADRAF.com.nk.aimodel.AiChat;
import ADRAF.com.nk.bean.Medicine;
import ADRAF.com.nk.dao.MedicineDao;
import ADRAF.com.nk.datamodel.Mmodel;
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
    private JTextField daysField;
    private JTextArea symptomArea;
    private JButton btnSubmit;

    private JTable recordTable;
    private JScrollPane recordScrollPane;

    public DisplayFrame_Patient() {
        init();
        inittext();
        setVisible(true);
    }

    private void init() {
        setSize(1400, 800);
        setTitle("药物不良反应查询分析平台(患者模式)");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void inittext() {
        header = new JPanel();
        header.setBackground(new Color(70, 130, 180));
        header.setPreferredSize(new Dimension(0, 40));
        header.setLayout(new BorderLayout());

        title = new JLabel("患者工作台");
        title.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        title.setFont(new Font("null", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        btn_panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btn_panel.setOpaque(false);
        JLabel wl = new JLabel("欢迎，张三");
        wl.setFont(new Font("null", Font.PLAIN, 16));
        wl.setForeground(Color.WHITE);
        wl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        btn_panel.add(wl);

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

        JPanel querypage = new JPanel();
        querypage.setBackground(Color.white);
        querypage.setLayout(new BorderLayout());
        querypage.add(inittable(MedicineDao.getAllmedicine()));

        JPanel reportpage = initReportPanel();

        JPanel aipage = new JPanel();
        aipage.setBackground(Color.white);
        aipage.setLayout(new BorderLayout());
        aipage.add(initaichat());

        JPanel recordpage = initRecordPanel();

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

    // 按钮渲染（完全沿用 Visitor 风格）
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

    // ===================== AI自查（沿用 Visitor 的 initaichat 布局） =====================
    private JPanel initaichat() {
        JPanel query = new JPanel();
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

        JLabel disclaimer = new JLabel("[!] 仅供参考，不能替代医生诊断");
        disclaimer.setBounds(30, 620, 300, 25);
        disclaimer.setFont(font.ft);
        disclaimer.setForeground(Color.RED);

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
        query.add(disclaimer);
        return query;
    }

    // ===================== 不良反应上报 =====================
    private JPanel initReportPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("不良反应上报");
        titleLabel.setBounds(30, 20, 200, 30);
        titleLabel.setFont(new Font("null", Font.BOLD, 22));

        JLabel drugLabel = new JLabel("选择药品：");
        drugLabel.setBounds(30, 70, 100, 30);
        drugLabel.setFont(font.ft);

        drugCombo = new JComboBox<>();
        drugCombo.setBounds(130, 70, 200, 30);
        drugCombo.setFont(font.ft);
        List<Medicine> allMeds = MedicineDao.getAllmedicine();
        for (Medicine m : allMeds) {
            drugCombo.addItem(m.getName());
        }

        JLabel daysLabel = new JLabel("用药时长：");
        daysLabel.setBounds(360, 70, 100, 30);
        daysLabel.setFont(font.ft);

        daysField = new JTextField(10);
        daysField.setBounds(450, 70, 80, 30);
        daysField.setFont(font.ft);

        JLabel daysUnit = new JLabel("天");
        daysUnit.setBounds(535, 70, 30, 30);
        daysUnit.setFont(font.ft);

        JLabel symptomLabel = new JLabel("症状描述：");
        symptomLabel.setBounds(30, 120, 100, 30);
        symptomLabel.setFont(font.ft);

        symptomArea = new JTextArea();
        symptomArea.setBounds(30, 155, 600, 120);
        symptomArea.setFont(font.ft);
        symptomArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        symptomArea.setLineWrap(true);
        symptomArea.setWrapStyleWord(true);

        btnSubmit = new JButton("提交反馈");
        btnSubmit.setBounds(280, 300, 120, 40);
        btnSubmit.setFont(font.ft);
        btnSubmit.setBackground(new Color(60, 179, 113));
        btnSubmit.setForeground(Color.WHITE);

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String drug = (String) drugCombo.getSelectedItem();
                String days = daysField.getText().trim();
                String symptom = symptomArea.getText().trim();

                if (drug == null || days.isEmpty() || symptom.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "请完整填写信息");
                    return;
                }

                JOptionPane.showMessageDialog(null,
                        "提交成功，可在'我的记录'查看进度",
                        "提交成功", JOptionPane.INFORMATION_MESSAGE);
                daysField.setText("");
                symptomArea.setText("");
            }
        });

        panel.add(titleLabel);
        panel.add(drugLabel);
        panel.add(drugCombo);
        panel.add(daysLabel);
        panel.add(daysField);
        panel.add(daysUnit);
        panel.add(symptomLabel);
        panel.add(symptomArea);
        panel.add(btnSubmit);

        return panel;
    }

    // ===================== 我的记录 =====================
    private JPanel initRecordPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("我的记录");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 0));
        titleLabel.setFont(new Font("null", Font.BOLD, 22));
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"药品", "症状", "时间", "状态", "操作"};
        String[][] data = {
                {"阿莫西林", "皮疹", "01-15", "已通过", "查看详情"},
                {"头孢拉定", "腹泻", "01-16", "待审核", "查看详情"},
                {"布洛芬", "胃痛", "01-20", "已驳回", "查看详情"}
        };

        recordTable = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        recordTable.setRowHeight(35);
        recordTable.getTableHeader().setReorderingAllowed(false);
        recordTable.getTableHeader().setResizingAllowed(false);
        recordTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        recordTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        recordTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        recordTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        recordTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        recordTable.getColumnModel().getColumn(4).setCellRenderer(new BtnRenderer());

        recordTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = recordTable.columnAtPoint(e.getPoint());
                int row = recordTable.rowAtPoint(e.getPoint());
                if (col == 4) {
                    showRecordDetail(row);
                }
            }
        });

        recordScrollPane = new JScrollPane(recordTable);
        recordScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        recordScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(recordScrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void showRecordDetail(int row) {
        if (row < 0) return;
        JDialog dialog = new JDialog(this, "反馈详情", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.gridx = 0; gbc.gridy = 0;

        String drug = (String) recordTable.getValueAt(row, 0);
        String symptom = (String) recordTable.getValueAt(row, 1);
        String time = (String) recordTable.getValueAt(row, 2);
        String status = (String) recordTable.getValueAt(row, 3);

        panel.add(new JLabel("药品："), gbc); gbc.gridx = 1;
        panel.add(new JLabel(drug), gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("症状："), gbc); gbc.gridx = 1;
        panel.add(new JLabel(symptom), gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("时间："), gbc); gbc.gridx = 1;
        panel.add(new JLabel(time), gbc);
        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("状态："), gbc); gbc.gridx = 1;
        JLabel statusLabel = new JLabel(status);
        if ("已通过".equals(status)) {
            statusLabel.setForeground(new Color(60, 179, 113));
        } else if ("待审核".equals(status)) {
            statusLabel.setForeground(new Color(218, 165, 32));
        } else {
            statusLabel.setForeground(Color.RED);
        }
        panel.add(statusLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(new JLabel("审核意见："), gbc); gbc.gridy = 5;

        JTextArea opinionArea = new JTextArea("暂无审核意见");
        if ("已通过".equals(status)) {
            opinionArea.setText("审核通过，无严重不良反应，建议继续观察。");
        } else if ("已驳回".equals(status)) {
            opinionArea.setText("该症状与药物关联性较低，建议进一步检查。");
        }
        opinionArea.setEditable(false);
        opinionArea.setLineWrap(true);
        opinionArea.setWrapStyleWord(true);
        opinionArea.setBackground(new Color(245, 245, 245));
        JScrollPane osp = new JScrollPane(opinionArea);
        osp.setPreferredSize(new Dimension(300, 80));
        panel.add(osp, gbc);

        gbc.gridy = 6; gbc.anchor = GridBagConstraints.CENTER;
        JButton btnClose = new JButton("关闭");
        btnClose.addActionListener(ev -> dialog.dispose());
        panel.add(btnClose, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        new DisplayFrame_Patient();
    }
}
