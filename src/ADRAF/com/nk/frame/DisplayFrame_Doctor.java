package ADRAF.com.nk.frame;

import ADRAF.com.nk.bean.Medicine;
import ADRAF.com.nk.bean.Records;
import ADRAF.com.nk.dao.MedicineDao;
import ADRAF.com.nk.dao.RecordDao;
import ADRAF.com.nk.datamodel.DRmodel;
import ADRAF.com.nk.datamodel.Mmodel;
import ADRAF.com.nk.datamodel.Rmodel;
import ADRAF.com.nk.tool.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class DisplayFrame_Doctor extends JFrame {

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

    private JTable reviewTable;
    private JScrollPane reviewScrollPane;

    private JTextField recordSearchField;
    private JTable recordQueryTable;
    private JScrollPane recordQueryScrollPane;

    public DisplayFrame_Doctor() {
        init();
        inittext();
        setVisible(true);
    }

    private void init() {
        setSize(1400, 800);
        setTitle("药物不良反应查询分析平台(医护模式)");
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
        JLabel doctorLabel = new JLabel("医生："+ UserStateTool.getUsername());
        doctorLabel.setFont(font.ft);
        doctorLabel.setForeground(Color.WHITE);
        doctorLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        btn_panel.add(doctorLabel);

        header.add(btn_panel, BorderLayout.EAST);
        header.add(title, BorderLayout.WEST);

        initsearch();
        add(initlrpanel());
        add(header, BorderLayout.NORTH);
    }

    //依旧左右布局
    private JSplitPane initlrpanel() {
        leftpanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        leftpanel.setBackground(Color.WHITE);

        JButton btn1 = new JButton("药品查询");
        JButton btn2 = new JButton("反馈审核");
        JButton btn3 = new JButton("记录查询");

        btn1.setPreferredSize(new Dimension(180, 60));
        btn2.setPreferredSize(new Dimension(180, 60));
        btn3.setPreferredSize(new Dimension(180, 60));

        leftpanel.add(btn1);
        leftpanel.add(btn2);
        leftpanel.add(btn3);

        cardLayout = new CardLayout();
        cardpaanel = new JPanel(cardLayout);

        JPanel querypage = new JPanel();
        querypage.setBackground(Color.white);
        querypage.setLayout(new BorderLayout());
        querypage.add(inittable(MedicineDao.getAllmedicine()));

        JPanel reviewpage = initReviewTable(RecordDao.getnoPatientRecords());
        JPanel recordquerypage = initRecordQueryTable(RecordDao.getyesPatientRecords());

        cardpaanel.add(querypage, "query");
        cardpaanel.add(reviewpage, "review");
        cardpaanel.add(recordquerypage, "recordquery");

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
                cardLayout.show(cardpaanel, "review");
            }
        });
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardpaanel, "recordquery");
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



    //药物查询
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



    //审核部分
    private void refreshReviewTable(List<Records> list){
        reviewTable.setModel(new DRmodel(list));
        reviewTable();
    }

    private JPanel initReviewTable(List<Records> list) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);


        JPanel wordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        wordPanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("反馈审核");
        titleLabel.setFont(font.ft);
        wordPanel.add(titleLabel);
        panel.add(wordPanel, BorderLayout.NORTH);

        reviewTable = new JTable(new DRmodel(list));
        reviewTable();

        reviewTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = reviewTable.columnAtPoint(e.getPoint());
                int row = reviewTable.rowAtPoint(e.getPoint());
                if (col == 5) {
                    DRmodel rm = (DRmodel) reviewTable.getModel();
                    Records r = rm.getDRecordrow(row);
                    test(r);
                    PrecDialog pd = new PrecDialog(r, "已驳回");
                    if(pd.isUpdated()){
                        refreshRecordQueryTable(RecordDao.getyesPatientRecords());
                        refreshReviewTable(RecordDao.getnoPatientRecords());
                    }
                }
            }
        });


        reviewTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = reviewTable.columnAtPoint(e.getPoint());
                int row = reviewTable.rowAtPoint(e.getPoint());
                if(col == 6){
                    DRmodel rm = (DRmodel) reviewTable.getModel();
                    Records r = rm.getDRecordrow(row);
                    test(r);
                    PrecDialog pd =  new PrecDialog(r,"已通过");
                    if(pd.isUpdated()){
                        refreshRecordQueryTable(RecordDao.getyesPatientRecords());
                        refreshReviewTable(RecordDao.getnoPatientRecords());
                    }
                }
            }
        });

        reviewScrollPane = new JScrollPane(reviewTable);
        reviewScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        reviewScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(reviewScrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void reviewTable() {
        reviewTable.setRowHeight(35);
        reviewTable.getTableHeader().setReorderingAllowed(false);
        reviewTable.getTableHeader().setResizingAllowed(false);
        reviewTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        reviewTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        reviewTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        reviewTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        reviewTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        reviewTable.getColumnModel().getColumn(5).setCellRenderer(new BtnRenderer());
        reviewTable.getColumnModel().getColumn(6).setCellRenderer(new BtnRenderer());
    }

    //这个是用来测试的，看看数据获取有没有问题
    public void test(Records r){
        System.out.println(r.getId());
        System.out.println(r.getUsername());
        System.out.println(r.getSymptom());
        System.out.println(r.getMeName());
        System.out.println(r.getDoctorOpinion());
    }



    //记录查询
    private JPanel initRecordQueryTable(List<Records> list) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);


        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBackground(Color.WHITE);
        JLabel jLabel = new JLabel("关键词：");
        jLabel.setFont(font.ft);
        searchPanel.add(jLabel);
        recordSearchField = new JTextField(20);
        recordSearchField.setPreferredSize(new Dimension(80, 28));
        JButton btnRecordSearch = new JButton("筛选");


        recordQueryTable = new JTable(new Rmodel(list));


        recordQueryTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = recordQueryTable.rowAtPoint(e.getPoint());
                int col = recordQueryTable.columnAtPoint(e.getPoint());
                if(col == 5){
                    Rmodel drm = (Rmodel) recordQueryTable.getModel();
                    Records r = drm.getRecordrow(row);
                    new RecDialog(r);
                }
            }
        });



        btnRecordSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = recordSearchField.getText().trim().toLowerCase();
                System.out.println("测试股");
            }
        });


        searchPanel.add(recordSearchField);
        searchPanel.add(btnRecordSearch);

        panel.add(searchPanel, BorderLayout.NORTH);


        initRecordQueryTable();

        recordQueryScrollPane = new JScrollPane(recordQueryTable);
        recordQueryScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        recordQueryScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(recordQueryScrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void initRecordQueryTable() {
        recordQueryTable.setRowHeight(35);
        recordQueryTable.getTableHeader().setReorderingAllowed(false);
        recordQueryTable.getTableHeader().setResizingAllowed(false);
        recordQueryTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        recordQueryTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        recordQueryTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        recordQueryTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        recordQueryTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        recordQueryTable.getColumnModel().getColumn(5).setCellRenderer(new BtnRenderer());
    }

    private void refreshRecordQueryTable(List<Records> list) {
        recordQueryTable.setModel(new Rmodel(list));
        initRecordQueryTable();
    }


    //按钮渲染器
    public class BtnRenderer extends JButton implements TableCellRenderer {
        public BtnRenderer() {
            setOpaque(true);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            setText(value.toString());
            return this;
        }
    }



    public static void main(String[] args) {
        new DisplayFrame_Doctor();
    }
}
