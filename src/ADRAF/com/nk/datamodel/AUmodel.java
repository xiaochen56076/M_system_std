package ADRAF.com.nk.datamodel;

import ADRAF.com.nk.bean.User;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AUmodel extends AbstractTableModel {
    private String[] header = {"用户名", "密码", "状态", "", ""};
    private List<User> data;

    public AUmodel(List<User> data) {
        this.data = data;
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
            case 3: return u.getStatus().equals("启用") ? "禁用" :"启用";
            case 4: return "删除";
            default: return null;
        }
    }

    public User getUserrow(int row) {
        return data.get(row);
    }
}
