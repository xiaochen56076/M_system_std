package ADRAF.com.nk.frame;

// @Author：nskdf
// @Time：2026-06-05-16-45
// @Project：ADARF_P

import ADRAF.com.nk.bean.Medicine;
import ADRAF.com.nk.dao.MedicineDao;
import ADRAF.com.nk.datamodel.Mmodel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainFrame_Visitor extends JFrame {

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


    public MainFrame_Visitor() throws HeadlessException {
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
    private void inittext(){
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
        btn_panel.add(btn_up);
        btn_panel.add(btn_in);

        header.add(btn_panel, BorderLayout.EAST);
        header.add(title, BorderLayout.WEST);

        initsearch();

        this.add(initlrpanel());
        this.add(header,BorderLayout.NORTH);
    }


    //左右排版部分
    private JSplitPane initlrpanel(){
        leftpanel = new JPanel();
        leftpanel.setBackground(Color.DARK_GRAY);
        leftpanel.setLayout(new GridLayout(2, 1));
        leftpanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        JButton btn1 = new JButton("药物查询");
        JButton btn2 = new JButton("症状自查");
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
        qureypage1.add(new JLabel("症状查询"));

        cardpaanel.add(qureypage, "query");
        cardpaanel.add(qureypage1, "query1");

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
                cardLayout.show(cardpaanel, "query1");
            }
        });
        return splitPane;
    }

    //搜索框部分
    private void initsearch(){
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

                if (word.isEmpty()){
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
    }

    //表单部分，便于复用
    private JPanel inittable(List<Medicine> word){
        tableModel = new Mmodel(word);
        resultTable = new JTable(tableModel);
        //设置宽度
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(SwingConstants.CENTER);
        resultTable.setRowHeight(35);
        resultTable.getTableHeader().setReorderingAllowed(false);//禁用表头的拖动
        resultTable.getTableHeader().setResizingAllowed(false);//禁用表头的的宽度可随意拖动
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(45);
        resultTable.getColumnModel().getColumn(0).setCellRenderer(r);
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        resultTable.getColumnModel().getColumn(1).setCellRenderer(r);
        resultTable.getColumnModel().getColumn(2).setPreferredWidth(250);
        resultTable.getColumnModel().getColumn(3).setPreferredWidth(200);

        scrollPane = new JScrollPane(resultTable);
        //总是显示垂直滚动条(默认)
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //水平滚动条不显示
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(search_area, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        return centerPanel;
    }


    //自查部分



    public static void main(String[] args) {
        new MainFrame_Visitor();
    }




}
