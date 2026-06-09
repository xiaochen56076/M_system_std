package ADRAF.com.nk.datamodel;

import ADRAF.com.nk.bean.Records;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class DRmodel extends AbstractTableModel {
    private final String[] header = {"药品名称", "不良反应", "用药时间", "记录时间", "审核状态", "", ""};
    private final List<Records> data;

    public DRmodel(List<Records> list) {

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
        Records r = data.get(row);
        switch (col) {
            case 0: return r.getMeName();
            case 1: return r.getSymptom();
            case 2: return r.getDays();
            case 3: return r.getReportTime();
            case 4: return r.getStatus();
            case 5: return "驳回";
            case 6: return "通过";
            default: return null;
        }
    }

    public Records getDRecordrow(int row) {
        return data.get(row);
    }
}
