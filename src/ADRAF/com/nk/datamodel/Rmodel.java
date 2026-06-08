package ADRAF.com.nk.datamodel;

// @Author：nskdf
// @Time：2026-06-08-20-14
// @Project：ADARF_P

import ADRAF.com.nk.bean.Records;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class Rmodel extends AbstractTableModel {
    private String[] header = {"药品名称", "不良反应", "用药时间", "记录时间", "操作"};
    private List<Records> rdata;

    public Rmodel(List<Records> list) {
        this.rdata = list;
    }

    @Override
    public int getRowCount() {
        return rdata.size();
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
        Records m = rdata.get(rowIndex);
        switch (columnIndex) {
            case 0: return m.getMeName();
            case 1: return m.getSymptom();
            case 2: return m.getDays();
            case 3: return m.getReportTime();
            case 4: return "查看";
            default: return null;
        }
    }

    public Records getRecordrow(int row) {
        return rdata.get(row);
    }
}
