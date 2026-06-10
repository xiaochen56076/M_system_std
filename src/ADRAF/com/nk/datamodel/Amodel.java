package ADRAF.com.nk.datamodel;

import ADRAF.com.nk.bean.User;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class Amodel extends AbstractTableModel {
    private String[] header = {"ÓÃ»§Ãû", "ÃÜÂë", "×´Ì¬", "", ""};
    private List<User> data;

    public Amodel(List<User> data) {
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
            case 3: return "½ûÓÃ";
            case 4: return "É¾³ı";
            default: return null;
        }
    }

    public User getUserrow(int row) {
        return data.get(row);
    }
}
