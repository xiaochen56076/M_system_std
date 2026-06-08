package ADRAF.com.nk.frame;

// @Author：nskdf
// @Time：2026-06-05-16-45
// @Project：ADARF_P

import ADRAF.com.nk.aimodel.AiChat;
import ADRAF.com.nk.bean.Medicine;
import ADRAF.com.nk.dao.MedicineDao;
import ADRAF.com.nk.datamodel.Mmodel;
import ADRAF.com.nk.tool.MmDialog;
import ADRAF.com.nk.tool.WindowTool;
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

public class DisplayFrame_Visitor extends JFrame {

    private JTextField jTextField;
    private JLabel title;
    private JPanel header; //设置窗口中头部部分的容器
    private JPanel search_area;
    private JButton btn_search;
    private JScrollPane scrollPane; //创建滚动区域
    private AbstractTableModel tableModel; //创建表格数据模型
    private JTable resultTable;
    private JButton btn_up;
    private JButton btn_in;
    private JPanel btn_panel;
    private JPanel leftpanel;
    private CardLayout cardLayout;
    private JPanel cardpaanel;


    private JTextArea usertext;
    private JTextArea aitext;
    private JButton btn_ok;
    private JScrollPane aijsp;
    private JButton btn_reset;

    public DisplayFrame_Visitor() throws HeadlessException {
        init();
        inittext();

        this.setVisible(true);
    }

    private void init() {
        this.setSize(1400, 800);
        this.setTitle("药物不良反应查询反馈平台(访客模式)");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口布局为BorderLayout
        this.setLayout(new BorderLayout());
    }

    //界面整合部分
    private void inittext() {
//        上层部分
        header = new JPanel();
        header.setBackground(new Color(70, 130, 180));
        header.setPreferredSize(new Dimension(0, 40));
        header.setLayout(new BorderLayout());

        title = new JLabel("药物不良反应查询反馈平台(更多功能请登录)");
        title.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        title.setFont(new Font("null", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        btn_panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btn_panel.setOpaque(false);//禁用背景绘制
        btn_up = new JButton("登录");
        btn_in = new JButton("注册");

        btn_up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginFrame_Sec lf = new LoginFrame_Sec();
                WindowTool.setJF(lf);
            }
        });


        DisplayFrame_Visitor temp = this;
        btn_in.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterFrame rf = new RegisterFrame();
                WindowTool.setJF(temp);
                dispose();
            }
        });


        btn_panel.add(btn_up);
        btn_panel.add(btn_in);

        header.add(btn_panel, BorderLayout.EAST);
        header.add(title, BorderLayout.WEST);


        initsearch();
        this.add(initlrpanel());
        this.add(header, BorderLayout.NORTH);
    }

    //左右排版部分
    private JSplitPane initlrpanel() {
        leftpanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        leftpanel.setBackground(Color.WHITE);
        JButton btn1 = new JButton("药物查询");
        JButton btn2 = new JButton("症状自查");
        btn1.setPreferredSize(new Dimension(180,  60));
        btn2.setPreferredSize(new Dimension(180,  60));
        leftpanel.add(btn1);
        leftpanel.add(btn2);

        cardLayout = new CardLayout();//卡片管理器
        cardpaanel = new JPanel(cardLayout);


        JPanel qureypage = new JPanel();
        qureypage.setBackground(Color.white);
        qureypage.setLayout(new BorderLayout());
        qureypage.add(inittable(MedicineDao.getAllmedicine()));

        JPanel qureypage1 = new JPanel();
        qureypage1.setBackground(Color.white);
        qureypage1.setLayout(new BorderLayout());
        qureypage1.add(initaichat());

        cardpaanel.add(qureypage, "query");
        cardpaanel.add(qureypage1, "queryai");

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
                cardLayout.show(cardpaanel, "queryai");
            }
        });
        return splitPane;
    }





    //搜索框部分
    private void initsearch() {
        search_area = new JPanel();
        jTextField = new JTextField(30);
        jTextField.setPreferredSize(new Dimension(80, 28));
        btn_search = new JButton("搜索");
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

    //用于刷新数据模型
    private void refreshTable(List<Medicine> list) {
        resultTable.setModel(new Mmodel(list));
        table();
    }

    //表单部分，便于后头复用
    private JPanel inittable(List<Medicine> word) {
        tableModel = new Mmodel(word);
        resultTable = new JTable(tableModel);
        table();


        resultTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = resultTable.columnAtPoint(e.getPoint());
                int row = resultTable.rowAtPoint(e.getPoint());
                System.out.println(row);
                if (col == 4) {
                    Medicine m = ((Mmodel)resultTable.getModel()).getMedicinerow(row);
                    new MmDialog(m);
                }
            }
        });

        scrollPane = new JScrollPane(resultTable);
        //总是显示垂直滚动条(默认)
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //水平滚动条不显示
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel mPanel = new JPanel(new BorderLayout());
        mPanel.add(search_area, BorderLayout.NORTH);
        mPanel.add(scrollPane, BorderLayout.CENTER);
        return mPanel;
    }

    private void table() {
        resultTable.setRowHeight(35);
        resultTable.getTableHeader().setReorderingAllowed(false);//禁用表头的拖动
        resultTable.getTableHeader().setResizingAllowed(false);//禁用表头的的宽度可随意拖动
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(45);
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        resultTable.getColumnModel().getColumn(2).setPreferredWidth(250);
        resultTable.getColumnModel().getColumn(3).setPreferredWidth(200);
        resultTable.getColumnModel().getColumn(4).setCellRenderer(new BtnRenderer());
    }



    //自查部分(AI)
    private JPanel initaichat() {

        JPanel query = new JPanel();
        query.setLayout(null);

        JLabel labelTip = new JLabel("请描述您不适的症状，AI帮您分析可能关联的药物：");
        labelTip.setBounds(30, 20, 540, 30);
        labelTip.setFont(ADRAF.com.nk.tool.font.ft);


        usertext = new JTextArea();
        usertext.setBounds(30, 50, 800, 80);
        usertext.setFont(font.ft);
        usertext.setBorder(BorderFactory.createLineBorder(Color.GRAY));


        btn_ok = new JButton("开始分析");
        btn_ok.setBounds(240, 140, 120, 35);
        btn_ok.setFont(font.ft);

        btn_reset = new JButton("清空内容");
        btn_reset.setBounds(500, 140, 120, 35);
        btn_reset.setFont(font.ft);


        JLabel labelResult = new JLabel("分析结果：");
        labelResult.setBounds(30, 185, 100, 30);
        labelResult.setFont(font.ft);


        aitext = new JTextArea();
        aitext.setFont(font.ft);
        aitext.setLineWrap(true);
        aitext.setWrapStyleWord(true);
        aitext.setEditable(false); // 禁止修改

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
                        JOptionPane.showMessageDialog(null, "请输入症状！");
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

    //按钮渲染部分（不过只是渲染，不能用哈）
    public class BtnRenderer extends JButton implements TableCellRenderer {
        public BtnRenderer() {
            setOpaque(true);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                setText("查看详情");
            return this;
        }
    }

    public static void main(String[] args) {
        new DisplayFrame_Visitor();
    }
}
