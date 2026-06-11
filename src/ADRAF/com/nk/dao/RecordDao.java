package ADRAF.com.nk.dao;

// @Author：nskdf
// @Time：2026-06-08-19-51
// @Project：ADARF_P

import ADRAF.com.nk.bean.Records;
import ADRAF.com.nk.tool.UserStateTool;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RecordDao {

    public static List<Records> searchMyRecords(String keyword) {
        List<Records> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Dao.getConn();
            ps = conn.prepareStatement(
                    "SELECT drug_name, symptom, days, report_time, status, doctor_opinion FROM ad_record WHERE username = ? AND (drug_name LIKE ? OR symptom LIKE ?) ORDER BY report_time DESC");
            ps.setString(1, UserStateTool.getUsername());
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            while (rs.next()) {
                list.add(new Records(
                        UserStateTool.getUsername(),
                        rs.getString("drug_name"),
                        rs.getString("symptom"),
                        rs.getString("days"),
                        sdf.format(rs.getTimestamp("report_time")),
                        rs.getString("doctor_opinion")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return list;
    }

    public static List<Records> getnoPatientRecords() {
        List<Records> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Dao.getConn();
            ps = conn.prepareStatement(
                    "SELECT id,username ,drug_name, symptom, days, report_time, status, doctor_opinion FROM ad_record where status = '待审核' ORDER BY report_time DESC");
            ResultSet rs = ps.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            while (rs.next()) {
                list.add(new Records(
                        rs.getString("username"),
                        rs.getInt("id"),
                        rs.getString("drug_name"),
                        rs.getString("symptom"),
                        rs.getString("days"),
                        sdf.format(rs.getTimestamp("report_time")),
                        rs.getString("status"),
                        rs.getString("doctor_opinion")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return list;
    }


    public static List<Records> getyesPatientRecords() {
        List<Records> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Dao.getConn();
            ps = conn.prepareStatement(
                    "SELECT id, username ,drug_name, symptom, days, report_time, status, doctor_opinion FROM ad_record where status = '已通过' or status = '已驳回' ORDER BY report_time DESC");
            ResultSet rs = ps.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            while (rs.next()) {
                list.add(new Records(
                        rs.getString("username"),
                        rs.getInt("id"),
                        rs.getString("drug_name"),
                        rs.getString("symptom"),
                        rs.getString("days"),
                        sdf.format(rs.getTimestamp("report_time")),
                        rs.getString("status"),
                        rs.getString("doctor_opinion")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return list;
    }

    public static List<Records> getAllRecords() {
        List<Records> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Dao.getConn();
            ps = conn.prepareStatement(
                    "SELECT id,username ,drug_name, symptom, days, report_time, status, doctor_opinion, doctorname FROM ad_record ORDER BY report_time DESC");
            ResultSet rs = ps.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            while (rs.next()) {
                list.add(new Records(
                        rs.getString("doctorname"),
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("drug_name"),
                        rs.getString("symptom"),
                        rs.getString("days"),
                        sdf.format(rs.getTimestamp("report_time")),
                        rs.getString("status"),
                        rs.getString("doctor_opinion")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return list;
    }





    public static List<Records> getMyRecords() {
        List<Records> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Dao.getConn();
            ps = conn.prepareStatement(
                    "SELECT id,username ,drug_name, symptom, days, report_time, status, doctor_opinion FROM ad_record WHERE username = ? ORDER BY report_time DESC");
            ps.setString(1, UserStateTool.getUsername());
            ResultSet rs = ps.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            while (rs.next()) {
                list.add(new Records(
                        rs.getString("username"),
                        rs.getInt("id"),
                        rs.getString("drug_name"),
                        rs.getString("symptom"),
                        rs.getString("days"),
                        sdf.format(rs.getTimestamp("report_time")),
                        rs.getString("status"),
                        rs.getString("doctor_opinion")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return list;
    }



    public static void updateDoctorrecord(Records record, String status,String docname , Component PrecDialog) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Dao.getConn();
            ps = conn.prepareStatement("update ad_record set doctor_opinion=?,status =? ,doctorname = ? where username = ? and id = ?");
            ps.setString(1,record.getDoctorOpinion());
            ps.setString(2, status);
            ps.setString(3, docname);
            ps.setString(4, record.getUsername());
            ps.setInt(5, record.getId());
            int flag1 = ps.executeUpdate();
            if (flag1 > 0) {
                JOptionPane.showMessageDialog(PrecDialog, "修改成功。");
            } else {
                JOptionPane.showMessageDialog(PrecDialog, "修改失败。");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close(); if (conn != null) conn.close();
            } catch (Exception e) {}
        }
    }






    public static void insertMyrecord(Records r) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Dao.getConn();
            ps = conn.prepareStatement(
                    "INSERT INTO ad_record (username, drug_name, symptom, days, report_time, status) VALUES (?, ?, ?, ?, NOW(), '待审核')");
            ps.setString(1, UserStateTool.getUsername());
            ps.setString(2, r.getMeName());
            ps.setString(3, r.getSymptom());
            ps.setString(4, r.getDays());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close(); if (conn != null) conn.close();
            } catch (Exception e) {}
        }
    }

}
