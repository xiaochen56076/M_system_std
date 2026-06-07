package ADRAF.com.nk.datamodel;

import ADRAF.com.nk.bean.Medicine;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class Mmodel extends AbstractTableModel {
    private String[] header = {"国药准字", "名称", "不良反应", "禁忌", "操作"};
    private List<Medicine> mdata;


    public Mmodel(List<Medicine> list) {
        this.mdata = list;
    }

    //行数
    @Override
    public int getRowCount() {
        return mdata.size();
    }

    //获取列数
    @Override
    public int getColumnCount() {
        return header.length;
    }

    //就哪一个列可以操作的
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 5 || columnIndex == 6;
    }

    //获取表头
    @Override
    public String getColumnName(int column) {
        return header[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Medicine m = mdata.get(rowIndex);
        switch (columnIndex) {
            case 0: return m.getId();
            case 1: return m.getName();
            case 2: return m.getAdverseReaction();
            case 3: return m.getContraindication();
            case 4: return "查看";   // 返回固定字符串，不再是 JButton
            default: return null;
        }

    }


    public Medicine getMedicinerow(int row){
        return mdata.get(row);
    }


}
