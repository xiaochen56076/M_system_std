package ADRAF.com.nk.datamodel;

// @Author：nskdf
// @Time：2026-06-10-18-53
// @Project：ADARF_P

import ADRAF.com.nk.bean.Records;
import ADRAF.com.nk.bean.User;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class Dmodel extends AbstractTableModel {
    private String[] header = {"用户名", "密码", "状态", "", "", ""};
    private List<User> data;


    public Dmodel(List<User> data) {
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
        switch (columnIndex){
            case 0: return u.getName();
            case 1: return u.getPwd();
            case 2: return u.getStatus();
            case 3: return "编辑资料";
            case 4: return "重置密码";
            case 5: return "禁用";
            default: return null;
        }
    }

    public User getUserrow(int row){
        return data.get(row);
    }
}
