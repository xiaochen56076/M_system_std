package ADRAF.com.nk.datamodel;

import ADRAF.com.nk.bean.Medicine;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class Mmodel extends AbstractTableModel {
    private String[] header = {"国字号", "药品名称", "不良反应", "禁忌", ""};
    private List<Medicine> mdata;

    public Mmodel(List<Medicine> list) {
        this.mdata = list;
    }

    @Override
    public int getRowCount() {
        return mdata.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 5 || columnIndex == 6;
    }

    @Override
    public String getColumnName(int column) {
        return header[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Medicine m = mdata.get(rowIndex);
        switch (columnIndex) {
            case 0: return m.getEncoding();
            case 1: return m.getName();
            case 2: return m.getAdverseReaction();
            case 3: return m.getContraindication();
            case 4: return "查看";
            default: return null;
        }
    }

    public Medicine getMedicinerow(int row) {
        return mdata.get(row);
    }
}
