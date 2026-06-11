package ADRAF.com.nk.datamodel;

import ADRAF.com.nk.bean.User;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class Pmodel extends AbstractTableModel {
    private String[] header = {"用户名", "密码", "状态", "过敏史", "", "", ""};
    private List<User> data;

    public Pmodel(List<User> data) {
        this.data = (data != null) ? data : new ArrayList<>();
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
        User u = data.get(rowIndex);
        switch (columnIndex) {
            case 0: return u.getName();
            case 1: return u.getPwd();
            case 2: return u.getStatus();
            case 3:
                String allergy = u.getAllergy();
                return (allergy == null || allergy.isEmpty()) ? "无" : allergy;
            case 4: return "编辑资料";
            case 5: return u.getStatus().equals("禁用") ? "启动":"禁用";
            case 6: return "删除";
            default: return null;
        }
    }

    public User getUserrow(int row) {
        return data.get(row);
    }
}
