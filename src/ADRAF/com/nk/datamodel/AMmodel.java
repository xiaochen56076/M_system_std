package ADRAF.com.nk.datamodel;

// @Author：nskdf
// @Time：2026-06-10-13-22
// @Project：ADARF_P

import ADRAF.com.nk.bean.Medicine;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AMmodel extends AbstractTableModel {
    private final String[] header = {"国字号", "药品名称", "不良反应", "禁忌", "", ""};
    private final List<Medicine> data;

    public AMmodel(List<Medicine> list) {

        this.data = list;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public String getColumnName(int col) {
        return header[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Medicine m = data.get(row);
        switch (col) {
            case 0:
                return m.getEncoding();
            case 1:
                return m.getName();
            case 2:
                return m.getAdverseReaction();
            case 3:
                return m.getContraindication();
            case 4:
                return "修改";
            case 5:
                return "删除";
            default:
                return null;
        }
    }

    public Medicine getAMecordrow(int row) {
        return data.get(row);
    }
}
