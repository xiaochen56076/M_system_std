package ADRAF.com.nk.dao;


import ADRAF.com.nk.bean.Medicine;
import ADRAF.com.nk.tool.UserStateTool;

import javax.swing.*;
import java.sql.*;
import java.util.List;
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
                m.setEncoding(rs.getString(1));
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


    public static boolean insertMedicine(Medicine m){
        Connection conn = null;
        PreparedStatement ps = null;
        conn = Dao.getConn();
        try {
            ps = conn.prepareStatement("insert into ad_medicine (encoding, name , adverseReaction, contraindication) values (?, ?, ?, ?)");
            ps.setString(1, m.getEncoding());
            ps.setString(2, m.getName());
            ps.setString(3, m.getAdverseReaction());
            ps.setString(4, m.getContraindication());
            int flag = ps.executeUpdate();
            if (flag > 0) {// 如果被影响行数大于0
                UserStateTool.setRight(1);
                JOptionPane.showMessageDialog(null, "添加成功。");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "添加失败。");
                return false;
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "测试");
            return false;
        }
    }


//    搜索药物
    public static List<Medicine> seacrchMedicine(String keyword){
        Connection conn = null;
        PreparedStatement ps = null;
        conn = Dao.getConn();
        try {
            ps = conn.prepareStatement("select encoding, name, adverseReaction, contraindication from ad_medicine where name like ?");
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            List<Medicine> list = new Vector<>();
            while(rs.next()){
                Medicine m = new Medicine();
                m.setEncoding(rs.getString(1));
                m.setName(rs.getString(2));
                m.setAdverseReaction(rs.getString(3));
                m.setContraindication(rs.getString(4));
                list.add(m);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
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


    public static void deleteMedicine(Medicine m){
        Connection conn = null;
        PreparedStatement ps = null;
        conn = Dao.getConn();
        try {
            ps = conn.prepareStatement("delete from ad_medicine where name = ?");
            ps.setString(1, m.getName());
            int flag = ps.executeUpdate();
            if(flag > 0){
                JOptionPane.showMessageDialog(null, "删除成功");
            }
            else {
                JOptionPane.showMessageDialog(null, "删除失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
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

}
