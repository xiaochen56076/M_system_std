package ADRAF.com.nk.frame;

import ADRAF.com.nk.bean.Medicine;
import ADRAF.com.nk.bean.Records;
import ADRAF.com.nk.dao.MedicineDao;
import ADRAF.com.nk.dao.RecordDao;
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

        JPanel reviewpage = initReviewPanel(RecordDao.getnoPatientRecords());
        JPanel recordquerypage = initRecordQueryPanel(RecordDao.getyesPatientRecords());

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
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            setText(value.toString());
            return this;
        }
    }






    private JPanel initReviewPanel(List<Records> list) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("反馈审核");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 0));
        titleLabel.setFont(new Font("null", Font.BOLD, 22));
        panel.add(titleLabel, BorderLayout.NORTH);

        reviewTable = new JTable(new Rmodel(list));


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

        reviewTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = reviewTable.columnAtPoint(e.getPoint());
                int row = reviewTable.rowAtPoint(e.getPoint());
                if (col == 5) {
                    Rmodel rm = (Rmodel) reviewTable.getModel();
                    Records r = rm.getRecordrow(row);
                    new PrecDialog(r, "已驳回");
                }
            }
        });


        reviewTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = reviewTable.columnAtPoint(e.getPoint());
                int row = reviewTable.rowAtPoint(e.getPoint());
                if(col == 6){
                    Rmodel rm = (Rmodel) reviewTable.getModel();
                    Records r = rm.getRecordrow(row);
                    new PrecDialog(r,"已通过");
                }
            }
        });

        reviewScrollPane = new JScrollPane(reviewTable);
        reviewScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        reviewScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(reviewScrollPane, BorderLayout.CENTER);
        return panel;
    }







    //记录查询
    private JPanel initRecordQueryPanel(List<Records> list) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("记录查询");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 0));
        titleLabel.setFont(new Font("null", Font.BOLD, 22));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("关键词："));
        recordSearchField = new JTextField(20);
        recordSearchField.setPreferredSize(new Dimension(80, 28));
        JButton btnRecordSearch = new JButton("筛选");
        btnRecordSearch.setPreferredSize(new Dimension(80, 28));
        btnRecordSearch.setBackground(new Color(60, 179, 113));
        btnRecordSearch.setForeground(Color.WHITE);


        recordQueryTable = new JTable(new Rmodel(list));




//        btnRecordSearch.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String keyword = recordSearchField.getText().trim().toLowerCase();
//                if (keyword.isEmpty()) {
//                    refreshRecordQueryTable(recordQueryData);
//                    return;
//                }
//                ArrayList<String[]> filteredList = new ArrayList<>();
//                for (String[] row : recordQueryData) {
//                    if (row[0].toLowerCase().contains(keyword) || row[1].toLowerCase().contains(keyword)) {
//                        filteredList.add(row);
//                    }
//                }
//                refreshRecordQueryTable(filteredList.toArray(new String[0][]));
//            }
//        });



        searchPanel.add(recordSearchField);
        searchPanel.add(btnRecordSearch);

        panel.add(searchPanel, BorderLayout.NORTH);


        initRecordQuerytable();

        recordQueryScrollPane = new JScrollPane(recordQueryTable);
        recordQueryScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        recordQueryScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(recordQueryScrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void initRecordQuerytable() {
        recordQueryTable.setRowHeight(35);
        recordQueryTable.getTableHeader().setReorderingAllowed(false);
        recordQueryTable.getTableHeader().setResizingAllowed(false);
        recordQueryTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        recordQueryTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        recordQueryTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        recordQueryTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        recordQueryTable.getColumnModel().getColumn(4).setPreferredWidth(100);
    }

    private void refreshRecordQueryTable(List<Records> list) {
        recordQueryTable.setModel(new Rmodel(list));
        initRecordQuerytable();
    }

    public static void main(String[] args) {
        new DisplayFrame_Doctor();
    }
}
