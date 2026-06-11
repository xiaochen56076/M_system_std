package ADRAF.com.nk.dao;


import ADRAF.com.nk.bean.Medicine;
import ADRAF.com.nk.tool.UserStateTool;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.List;
import java.util.Vector;


public class MedicineDao {

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
            return flag > 0;
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "´íÎó");
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


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


    public static void updateMedicine(Medicine m) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Dao.getConn();
            ps = conn.prepareStatement(
                    "update ad_medicine set name=?, adverseReaction=?, contraindication=? WHERE encoding=?"
            );
            ps.setString(1, m.getName());
            ps.setString(2, m.getAdverseReaction());
            ps.setString(3, m.getContraindication());
            ps.setString(4, m.getEncoding());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

}
