package ADRAF.com.nk.datamodel;

import ADRAF.com.nk.bean.Records;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ARmodel extends AbstractTableModel {
    private final String[] header = {"药物名称", "不良反应描述", "用药时间(天)", "上报时间", "患者", "审核状态", "审核医生", ""};
    private final List<Records> data;

    public ARmodel(List<Records> list) {

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
        Records m = data.get(row);
        switch (col) {
            case 0:
                return m.getMeName();
            case 1:
                return m.getSymptom();
            case 2:
                return m.getDays();
            case 3:
                return m.getReportTime();
            case 4:
                return m.getUsername();
            case 5:
                return m.getStatus();
            case 6:
                return m.getDocname();
            case 7:
                return "查看详情";
            default:
                return null;
        }
    }

    public Records getARecordrow(int row) {
        return data.get(row);
    }
}
