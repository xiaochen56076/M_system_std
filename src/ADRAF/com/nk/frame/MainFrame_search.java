package ADRAF.com.nk.frame;

import ADRAF.com.nk.bean.Medicine;
import ADRAF.com.nk.dao.MedicineDao;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Vector;

public class MainFrame_search extends JFrame {

    private JTextField jTextField;
    private JLabel title;
    private JPanel header; //设置窗口中头部部分的容器
    private JPanel search_area;
    private JButton btn_search;
    private JScrollPane scrollPane; //创建滚动区域
    private DefaultTableModel tableModel; //创建表格数据模型
    private JTable resultTable;

    public MainFrame_search() throws HeadlessException {

        init();
        inittext();

        this.setVisible(true);
    }

    private void init() {
        this.setSize(900, 700);
        this.setTitle("药物不良反应查询反馈平台(访客模式)");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口布局为BorderLayout
        this.setLayout(new BorderLayout());
    }



    private void inittext(){
//        上层部分
        header = new JPanel();
        header.setBackground(new Color(70, 130, 180));
        //封装这个的宽高，这个容器的哈
        header.setPreferredSize(new Dimension(0, 50));
        header.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 12));
        title = new JLabel("药物查询");
        title.setFont(new Font("null", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        header.add(title);


//        搜索框部分
        search_area = new JPanel();
        jTextField = new JTextField(30);
        jTextField.setPreferredSize(new Dimension(80, 28));
        btn_search = new JButton("搜索");
        btn_search.setPreferredSize(new Dimension(80, 28));
        btn_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String word = jTextField.getText().trim();
                tableModel.setColumnCount(0);//清空表格


            }
        });
        search_area.add(jTextField);
        search_area.add(btn_search);


//        查询结果区域
        final String[]  headname  = {"国药准字", "名称", "不良反应", "禁忌", "操作"};
        //创建表格模型（初始行为0）
        tableModel = new DefaultTableModel(headname, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };
        //设置宽度
        resultTable = new JTable(tableModel);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(SwingConstants.CENTER);
        resultTable.setRowHeight(35);
        resultTable.getTableHeader().setReorderingAllowed(false);//禁用表头的拖动
        resultTable.getTableHeader().setResizingAllowed(false);//禁用表头的的宽度可随意拖动
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        resultTable.getColumnModel().getColumn(0).setCellRenderer(r);
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        resultTable.getColumnModel().getColumn(2).setPreferredWidth(250);
        resultTable.getColumnModel().getColumn(3).setPreferredWidth(200);

        scrollPane = new JScrollPane(resultTable);
        //总是显示垂直滚动条(默认)
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //水平滚动条不显示
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


//        渲染按钮，因为默认是无法加入按钮的，所以这里需要加入渲染器和编辑器
        resultTable.getColumn("操作").setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                return new JButton("查看详细");
            }
        });
//        resultTable.getColumn("操作").setCellEditor(new DefaultCellEditor());


        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(search_area, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);


        this.add(centerPanel);
        this.add(header,BorderLayout.NORTH);

        loadData();
    }

//    加载数据
    private void loadData(){
        tableModel.setRowCount(0);
        List<Medicine> list = MedicineDao.getAllmedicine();
        if(list != null){
            for(Medicine m :list){
                tableModel.addRow(new Object[]{
                        m.getId(),
                        m.getName(),
                        m.getAdverseReaction(),
                        m.getContraindication()
                });
            }
        }
    }




    public static void main(String[] args) {
        new MainFrame_search();
    }

    public class MyButtonEditor extends DefaultCellEditor{
        private JPanel panel;

        public MyButtonEditor(JTextField textField) {
            super(textField);
        }

        public MyButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        public MyButtonEditor(JComboBox<?> comboBox) {
            super(comboBox);
        }
    }



}
