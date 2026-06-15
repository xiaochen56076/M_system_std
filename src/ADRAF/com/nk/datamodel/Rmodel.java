package ADRAF.com.nk.datamodel;

// @Author：nskdf
// @Time：2026-06-08-20-14
// @Project：ADARF_P

import ADRAF.com.nk.bean.Records;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class Rmodel extends AbstractTableModel {
    private String[] header = {"药品名称", "不良反应描述", "用药时间（天）", "上报时间", "审核状态", ""};
    private List<Records> data;

    public Rmodel(List<Records> list) {
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
    public String getColumnName(int column) {
        return header[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Records m = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return m.getMeName();
            case 1:
                return m.getSymptom();
            case 2:
                return m.getDays();
            case 3:
                return m.getReportTime();
            case 4:
                return m.getStatus();
            case 5:
                return "查看";
            default:
                return null;
        }
    }

    public Records getRecordrow(int row) {
        return data.get(row);
    }
}
