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
        return columnIndex == 5;
    }

    //获取表头
    @Override
    public String getColumnName(int column) {
        return header[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Medicine m = mdata.get(rowIndex);//行
        Object[] data = {m.getId(), m.getName(), m.getAdverseReaction(), m.getContraindication(), ""};
        return data[columnIndex];

    }


}
