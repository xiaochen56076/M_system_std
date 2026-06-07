package ADRAF.com.nk.frame;

// @Author：nskdf
// @Time：2026-06-05-21-38
// @Project：ADARF_P

import ADRAF.com.nk.aimodel.AiChat;
import ADRAF.com.nk.bean.Medicine;
import ADRAF.com.nk.dao.MedicineDao;
import ADRAF.com.nk.datamodel.Mmodel;
import ADRAF.com.nk.tool.font;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
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
    private List<Medicine> currentMedicineList;

    public DisplayFrame_Patient() {
        init();
        inittext();
        setVisible(true);
    }

    private void init() {
        setSize(1400, 800);
        setTitle("药物不良反应查询分析平台");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void inittext() {
        header = new JPanel();
        header.setBackground(new Color(70, 130, 180));
        header.setPreferredSize(new Dimension(0, 40));
        header.setLayout(new BorderLayout());

        title = new JLabel("患者");
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

        JPanel qp = new JPanel();
        qp.setBackground(Color.white);
        qp.setLayout(new BorderLayout());
        currentMedicineList = MedicineDao.getAllmedicine();
        qp.add(inittable(currentMedicineList));

        JPanel rp = initReportPanel();
        JPanel ap = new JPanel();
        ap.setBackground(Color.white);
        ap.setLayout(new BorderLayout());
        ap.add(initaichat());
        JPanel rcp = initRecordPanel();

        cardpaanel.add(qp, "query");
        cardpaanel.add(rp, "report");
        cardpaanel.add(ap, "queryai");
        cardpaanel.add(rcp, "record");

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftpanel, cardpaanel);
        sp.setDividerLocation(180);
        sp.setDividerSize(2);
        sp.setEnabled(false);

        btn1.addActionListener(e -> cardLayout.show(cardpaanel, "query"));
        btn2.addActionListener(e -> cardLayout.show(cardpaanel, "report"));
        btn3.addActionListener(e -> cardLayout.show(cardpaanel, "queryai"));
        btn4.addActionListener(e -> cardLayout.show(cardpaanel, "record"));
        return sp;
    }

    private void initsearch() {
        search_area = new JPanel();
        jTextField = new JTextField(30);
        jTextField.setPreferredSize(new Dimension(80, 28));
        btn_search = new JButton("查询");
        btn_search.setPreferredSize(new Dimension(80, 28));
        btn_search.addActionListener(e -> {
            String w = jTextField.getText().trim();
            List<Medicine> list = w.isEmpty() ? MedicineDao.getAllmedicine() : MedicineDao.seacrchMedicine(w);
            refreshTable(list);
        });
        search_area.add(jTextField);
        search_area.add(btn_search);
    }

    private void refreshTable(List<Medicine> list) {
        currentMedicineList = list;
        resultTable.setModel(new Mmodel(list));
        // 保持按钮列
        if (resultTable.getColumnCount() == 5) {
            resultTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer("查看详情"));
            resultTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor("查看详情", e -> {
                int row = resultTable.getSelectedRow();
                if (row >= 0 && row < list.size()) showDrugDetailDialog(list.get(row));
            }));
        }
    }

    private JPanel inittable(List<Medicine> word) {
        tableModel = new Mmodel(word);
        resultTable = new JTable(tableModel);

        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(SwingConstants.CENTER);
        resultTable.setRowHeight(40);
        resultTable.getTableHeader().setReorderingAllowed(false);
        resultTable.getTableHeader().setResizingAllowed(false);
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(45);
        resultTable.getColumnModel().getColumn(0).setCellRenderer(r);
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        resultTable.getColumnModel().getColumn(1).setCellRenderer(r);
        resultTable.getColumnModel().getColumn(2).setPreferredWidth(250);
        resultTable.getColumnModel().getColumn(2).setCellRenderer(r);
        resultTable.getColumnModel().getColumn(3).setPreferredWidth(200);
        resultTable.getColumnModel().getColumn(4).setPreferredWidth(100);

        // 操作列使用按钮
        List<Medicine> listRef = word;
        resultTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer("查看详情"));
        resultTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor("查看详情", e -> {
            int row = resultTable.getSelectedRow();
            if (row >= 0 && row < listRef.size()) showDrugDetailDialog(listRef.get(row));
        }));

        scrollPane = new JScrollPane(resultTable);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel cp = new JPanel(new BorderLayout());
        cp.add(search_area, BorderLayout.NORTH);
        cp.add(scrollPane, BorderLayout.CENTER);
        return cp;
    }

    //按钮渲染器（负责按钮的显示）
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer(String text) {
            setText(text);
            setOpaque(true);
            setFont(new Font("null", Font.PLAIN, 13));
            setBackground(new Color(70, 130, 180));
            setForeground(Color.WHITE);
            setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends AbstractCellEditor implements javax.swing.table.TableCellEditor {
        private JButton button;
        private String label;

        public ButtonEditor(String text, ActionListener action) {
            button = new JButton(text);
            button.setFont(new Font("null", Font.PLAIN, 13));
            button.setBackground(new Color(70, 130, 180));
            button.setForeground(Color.WHITE);
            button.addActionListener(e -> {
                action.actionPerformed(e);
                fireEditingStopped();
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            return button;
        }

        public Object getCellEditorValue() {
            return label;
        }
    }

    private void showDrugDetailDialog(Medicine m) {
        JDialog d = new JDialog(this, "药品详情 - " + m.getName(), true);
        d.setSize(400, 280);
        d.setLocationRelativeTo(this);
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(5, 10, 5, 10);
        g.gridx = 0;
        g.gridy = 0;
        p.add(new JLabel("药品名："), g);
        g.gridx = 1;
        p.add(new JLabel(m.getName()), g);
        g.gridx = 0;
        g.gridy = 1;
        p.add(new JLabel("编码："), g);
        g.gridx = 1;
        p.add(new JLabel(m.getId()), g);
        g.gridx = 0;
        g.gridy = 2;
        p.add(new JLabel("常见不良反应："), g);
        g.gridx = 1;
        p.add(new JLabel(m.getAdverseReaction()), g);
        g.gridx = 0;
        g.gridy = 3;
        p.add(new JLabel("禁忌："), g);
        g.gridx = 1;
        JTextArea ta = new JTextArea(m.getContraindication());
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBackground(new Color(255, 240, 240));
        ta.setFont(font.ft);
        JScrollPane sp = new JScrollPane(ta);
        sp.setPreferredSize(new Dimension(250, 60));
        p.add(sp, g);
        g.gridx = 0;
        g.gridy = 4;
        g.gridwidth = 2;
        g.anchor = GridBagConstraints.CENTER;
        JButton bc = new JButton("关闭");
        bc.addActionListener(ev -> d.dispose());
        p.add(bc, g);
        d.add(p);
        d.setVisible(true);
    }

    private JPanel initaichat() {
        JPanel q = new JPanel();
        q.setLayout(null);
        JLabel lt = new JLabel("请描述你的症状，AI将分析可能相关的药物不良反应：");
        lt.setBounds(30, 20, 540, 30);
        lt.setFont(font.ft);
        usertext = new JTextArea();
        usertext.setBounds(30, 50, 800, 80);
        usertext.setFont(font.ft);
        usertext.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        btn_ok = new JButton("开始分析");
        btn_ok.setBounds(240, 140, 130, 35);
        btn_ok.setFont(font.ft);
        btn_reset = new JButton("清空重填");
        btn_reset.setBounds(500, 140, 130, 35);
        btn_reset.setFont(font.ft);
        JLabel lr = new JLabel("分析结果：");
        lr.setBounds(30, 185, 100, 30);
        lr.setFont(font.ft);
        aitext = new JTextArea();
        aitext.setFont(font.ft);
        aitext.setLineWrap(true);
        aitext.setWrapStyleWord(true);
        aitext.setEditable(false);
        aijsp = new JScrollPane(aitext);
        aijsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        aijsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aijsp.setBounds(30, 215, 800, 400);
        JLabel disc = new JLabel("[!] 仅供参考，不能替代医生诊断");
        disc.setBounds(30, 620, 300, 25);
        disc.setFont(font.ft);
        disc.setForeground(Color.RED);
        btn_reset.addActionListener(ev -> {
            aitext.setText("");
            usertext.setText("");
        });
        btn_ok.addActionListener(ev -> {
            try {
                String s = usertext.getText().trim();
                if (s.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "请输入症状描述");
                    return;
                }
                SwingWorker<String, Object> wk = new SwingWorker<>() {
                    protected String doInBackground() throws Exception {
                        return AiChat.callai(s);
                    }

                    protected void done() {
                        try {
                            aitext.setText(get());
                        } catch (Exception ex) {
                            aitext.setText("分析出错：" + ex.getMessage());
                        }
                    }
                };
                wk.execute();
                aitext.setText("正在分析，请稍候...");
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        });
        q.add(lt);
        q.add(usertext);
        q.add(btn_ok);
        q.add(btn_reset);
        q.add(lr);
        q.add(aijsp);
        q.add(disc);
        return q;
    }

    private JPanel initReportPanel() {
        JPanel p = new JPanel(null);
        p.setBackground(Color.WHITE);
        JLabel tl = new JLabel("不良反应上报");
        tl.setBounds(30, 20, 200, 30);
        tl.setFont(new Font("null", Font.BOLD, 22));
        JLabel dl = new JLabel("选择药品：");
        dl.setBounds(30, 70, 100, 30);
        dl.setFont(font.ft);
        drugCombo = new JComboBox<>();
        drugCombo.setBounds(130, 70, 200, 30);
        drugCombo.setFont(font.ft);
        for (Medicine m : MedicineDao.getAllmedicine()) drugCombo.addItem(m.getName());
        JLabel dal = new JLabel("用药时长：");
        dal.setBounds(360, 70, 100, 30);
        dal.setFont(font.ft);
        daysField = new JTextField(10);
        daysField.setBounds(450, 70, 80, 30);
        daysField.setFont(font.ft);
        JLabel du = new JLabel("天");
        du.setBounds(535, 70, 30, 30);
        du.setFont(font.ft);
        JLabel sl = new JLabel("症状描述：");
        sl.setBounds(30, 120, 100, 30);
        sl.setFont(font.ft);
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
        btnSubmit.addActionListener(ev -> {
            String dr = (String) drugCombo.getSelectedItem();
            String da = daysField.getText().trim();
            String sy = symptomArea.getText().trim();
            if (dr == null || da.isEmpty() || sy.isEmpty()) {
                JOptionPane.showMessageDialog(null, "请完整填写信息");
                return;
            }
            JOptionPane.showMessageDialog(null, "提交成功，可在'我的记录'查看进度",
                    "提交成功", JOptionPane.INFORMATION_MESSAGE);
            daysField.setText("");
            symptomArea.setText("");
        });
        p.add(tl);
        p.add(dl);
        p.add(drugCombo);
        p.add(dal);
        p.add(daysField);
        p.add(du);
        p.add(sl);
        p.add(symptomArea);
        p.add(btnSubmit);
        return p;
    }

    private JPanel initRecordPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        JLabel tl = new JLabel("我的记录");
        tl.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 0));
        tl.setFont(new Font("null", Font.BOLD, 22));
        p.add(tl, BorderLayout.NORTH);

        String[] cols = {"药品", "症状", "时间", "状态", "操作"};
        String[][] data = {
                {"阿莫西林", "皮疹", "01-15", "已通过", "查看详情"},
                {"头孢拉定", "腹泻", "01-16", "待审核", "查看详情"},
                {"布洛芬", "胃痛", "01-20", "已驳回", "查看详情"}
        };

        recordTable = new JTable(data, cols) {
            public boolean isCellEditable(int r, int c) {
                return c == 4;
            }
        };

        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(SwingConstants.CENTER);
        recordTable.setRowHeight(40);
        recordTable.getTableHeader().setReorderingAllowed(false);
        recordTable.getTableHeader().setResizingAllowed(false);
        recordTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        recordTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        recordTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        recordTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        recordTable.getColumnModel().getColumn(4).setPreferredWidth(100);

        // 操作列按钮
        recordTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer("查看详情"));
        recordTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor("查看详情", e -> {
            int row = recordTable.getSelectedRow();
            if (row >= 0) showRecordDetailDialog(row);
        }));

        recordScrollPane = new JScrollPane(recordTable);
        recordScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        recordScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        p.add(recordScrollPane, BorderLayout.CENTER);
        return p;
    }

    private void showRecordDetailDialog(int row) {
        JDialog d = new JDialog(this, "反馈详情", true);
        d.setSize(400, 300);
        d.setLocationRelativeTo(this);
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(5, 10, 5, 10);
        g.gridx = 0;
        g.gridy = 0;
        String drug = (String) recordTable.getValueAt(row, 0);
        String sym = (String) recordTable.getValueAt(row, 1);
        String tim = (String) recordTable.getValueAt(row, 2);
        String sta = (String) recordTable.getValueAt(row, 3);
        p.add(new JLabel("药品："), g);
        g.gridx = 1;
        p.add(new JLabel(drug), g);
        g.gridx = 0;
        g.gridy = 1;
        p.add(new JLabel("症状："), g);
        g.gridx = 1;
        p.add(new JLabel(sym), g);
        g.gridx = 0;
        g.gridy = 2;
        p.add(new JLabel("时间："), g);
        g.gridx = 1;
        p.add(new JLabel(tim), g);
        g.gridx = 0;
        g.gridy = 3;
        p.add(new JLabel("状态："), g);
        g.gridx = 1;
        JLabel sl = new JLabel(sta);
        if ("已通过".equals(sta)) sl.setForeground(new Color(60, 179, 113));
        else if ("待审核".equals(sta)) sl.setForeground(new Color(218, 165, 32));
        else sl.setForeground(Color.RED);
        p.add(sl, g);
        g.gridx = 0;
        g.gridy = 4;
        g.gridwidth = 2;
        p.add(new JLabel("审核意见："), g);
        g.gridy = 5;
        JTextArea oa = new JTextArea("暂无审核意见");
        if ("已通过".equals(sta)) oa.setText("审核通过，无严重不良反应，建议继续观察。");
        else if ("已驳回".equals(sta)) oa.setText("该症状与药物关联性较低，建议进一步检查。");
        oa.setEditable(false);
        oa.setLineWrap(true);
        oa.setWrapStyleWord(true);
        oa.setBackground(new Color(245, 245, 245));
        JScrollPane osp = new JScrollPane(oa);
        osp.setPreferredSize(new Dimension(300, 80));
        p.add(osp, g);
        g.gridy = 6;
        g.anchor = GridBagConstraints.CENTER;
        JButton bc = new JButton("关闭");
        bc.addActionListener(ev -> d.dispose());
        p.add(bc, g);
        d.add(p);
        d.setVisible(true);
    }

    public static void main(String[] args) {
        new DisplayFrame_Patient();
    }
}