package ADRAF.com.nk.dao;


import ADRAF.com.nk.bean.Medicine;

import javax.swing.*;
import java.sql.*;
import java.util.List;
import java.util.Random;
import java.util.Vector;


public class MedicineDao {

//    获取所有的数据
    public static List<Medicine> getAllmedicine() {
        Connection conn = null;
        PreparedStatement ps = null;
        conn = Dao.getConn();
        try {
            ps = conn.prepareStatement("select encoding, name, adverseReaction, contraindication from ad_medicine");
            ResultSet rs = ps.executeQuery();
            List<Medicine> v = new Vector<>();
            while (rs.next()) {
                Medicine m = new Medicine();
                m.setId(rs.getString(1));
                m.setName(rs.getString(2));
                m.setAdverseReaction(rs.getString(3));
                m.setContraindication(rs.getString(4));
                v.add(m);
            }
            return v;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }






//    搜索药物
    public static List<Medicine> seacrchMedicine(String keyword){
        Connection conn = null;
        PreparedStatement ps = null;
        conn = Dao.getConn();
        try {
            ps = conn.prepareStatement("select encoding, name, adverseReaction, contraindication from ad_medicine where name = ?");
            ps.setString(1, keyword);
            ResultSet rs = ps.executeQuery();
            List<Medicine> list = new Vector<>();
            while(rs.next()){
                Medicine m = new Medicine();
                m.setId(rs.getString(1));
                m.setName(rs.getString(2));
                m.setAdverseReaction(rs.getString(3));
                m.setContraindication(rs.getString(4));
                list.add(m);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
