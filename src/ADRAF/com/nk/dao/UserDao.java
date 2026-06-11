package ADRAF.com.nk.dao;


import ADRAF.com.nk.bean.User;
import ADRAF.com.nk.tool.UserStateTool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;


public class UserDao {

    public static boolean userLogin(User user) {
        Connection conn = null;
        try {
            String username = user.getName();
            String pwd = user.getPwd();
            if(username.isEmpty() || pwd.isEmpty()){
                JOptionPane.showMessageDialog(null, "用户名或密码不能为空");
                return false;
            }

            conn = Dao.getConn(); // 获得数据库连接
            // 创建PreparedStatement对象，并传递SQL语句
            PreparedStatement ps = conn
                    .prepareStatement("select password, role from ad_user where username=?");
            ps.setString(1, username); // 为参数赋值
            ResultSet rs = ps.executeQuery(); // 执行SQL语句，获得查询结果集
            if (rs.next()) { // 查询到用户信息
                String password = rs.getString(1); // 获得密码
                int right = rs.getInt(2);//获取权限
                if (password.equals(pwd)) {// 如果密码相同
                    UserStateTool.setUsername(username);// 记录账号
                    UserStateTool.setPassword(pwd);// 记录密码
                    UserStateTool.setRight(right);
                    return true; // 密码正确返回true
                } else {
                    JOptionPane.showMessageDialog(null, "密码错误。");
                    return false; // 密码错误返回false
                }
            } else {
                JOptionPane.showMessageDialog(null, "用户不存在。");
                return false; // 用户不存在返回false
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "数据库异常！\n" + ex.getMessage());
            return false; // 数据库异常返回false
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static boolean insertUser(User user, String vcode) {
        Connection conn = null;
        try {
            String username = user.getName();
            String pwd = user.getPwd();
            String okPwd = user.getOkpwd();
            String allergy = user.getAllergy();
            System.out.println(username + "" +  pwd+ "" +okPwd);
            if (username.isEmpty() || pwd.isEmpty() || okPwd.isEmpty()) {
                JOptionPane.showMessageDialog(null, "用户名或密码不能为空。");
                return false;
            }
            if (!pwd.trim().equals(okPwd.trim())) {
                JOptionPane.showMessageDialog(null, "两次输入的密码不一致。");
                return false;
            }
            System.out.println(UserStateTool.getvcode());
            if(!UserStateTool.getvcode().equals(vcode)){
                JOptionPane.showMessageDialog(null, "验证码错误");
                return false;
            }

            conn = Dao.getConn(); // 获得数据库连接
            // 创建PreparedStatement对象，并传递SQL语句
            PreparedStatement ps = conn
                    .prepareStatement("insert into ad_user (username,password, allergy, role)  values(?,?,?, 1)");
            ps.setString(1, username.trim()); // 为参数赋值
            ps.setString(2, pwd.trim());
            ps.setString(3, allergy.trim());
            int flag = ps.executeUpdate();// 执行sql
            if (flag > 0) {// 如果被影响行数大于0
                UserStateTool.setRight(1);
                JOptionPane.showMessageDialog(null, "添加成功。");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "添加失败。");
                return false;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "用户名重复，请换个名称！");
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {

            }
        }
    }


    public static void updateUser(String oldPwd, String newPwd, String okPwd) {
        try {
            if (!newPwd.trim().equals(okPwd.trim())) {
                JOptionPane.showMessageDialog(null, "两次输入的密码不一致。");
                return;
            }
            Connection conn = Dao.getConn();
            PreparedStatement ps = conn
                    .prepareStatement("select password from ad_user where username = ?");
            ps.setString(1, UserStateTool.getUsername());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String password = rs.getString(1);
                if (password.equals(oldPwd)) {
                    PreparedStatement ps1 = conn.prepareStatement("update ad_user set password = ? where username = ?");
                    ps1.setString(1, newPwd.trim());
                    ps1.setString(2, UserStateTool.getUsername());
                    int flag1 = ps1.executeUpdate();
                    if (flag1 > 0) {
                        JOptionPane.showMessageDialog(null, "修改成功。");
                    } else {
                        JOptionPane.showMessageDialog(null, "修改失败。");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "原密码不正确。");
                    return;
                }
            }
            ps.close();
            conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "数据库异常！" + ex.getMessage());
            return;
        }
    }

    public static List<User> getPuser(){
        Connection conn = null;
        List<User> users = new ArrayList<>();
        try {
            conn = Dao.getConn(); // 获得数据库连接
            // 创建PreparedStatement对象，并传递SQL语句
            PreparedStatement ps = conn.prepareStatement("select username, password, status from ad_user where role = 1");
            ResultSet rs = ps.executeQuery(); // 执行SQL语句，获得查询结果集
            while (rs.next()) { // 查询到用户信息
                users.add(new User(rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("status")));
            }
            return users;
        }
        catch (Exception e){
            System.out.println("");
            return null;
        }
    }

    public static List<User> getDuser(){
        Connection conn = null;
        List<User> users = new ArrayList<>();
        try {
            conn = Dao.getConn(); // 获得数据库连接
            // 创建PreparedStatement对象，并传递SQL语句
            PreparedStatement ps = conn.prepareStatement("select username, password, status from ad_user where role = 2");
            ResultSet rs = ps.executeQuery(); // 执行SQL语句，获得查询结果集
            while (rs.next()) { // 查询到用户信息
                users.add(new User(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)));
            }
            return users;
        }
        catch (Exception e){
            System.out.println("test");
            return users;
        }
    }

    public static List<User> getAuser(){
        Connection conn = null;
        List<User> users = new ArrayList<>();
        try {
            conn = Dao.getConn(); // 获得数据库连接
            // 创建PreparedStatement对象，并传递SQL语句
            PreparedStatement ps = conn.prepareStatement("select username, password, status from ad_user where role = 3 or role = 4");
            ResultSet rs = ps.executeQuery(); // 执行SQL语句，获得查询结果集
            while (rs.next()) { // 查询到用户信息
                users.add(new User(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)));
            }
            return users;
        }
        catch (Exception e){
            System.out.println("报错啦");
            return users;
        }
    }

    public static List<User> getAllUser() {
        Connection conn = null;
        List<User> users = new ArrayList<>();
        try {
            conn = Dao.getConn(); // 获得数据库连接
            // 创建PreparedStatement对象，并传递SQL语句
            PreparedStatement ps = conn.prepareStatement("select username, password, status from ad_user");
            ResultSet rs = ps.executeQuery(); // 执行SQL语句，获得查询结果集
            while (rs.next()) { // 查询到用户信息
                users.add(new User(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)));
            }
            return users;
        }
        catch (Exception e){
            System.out.println("报错啦");
            return users;
        }
    }

    public static void setRole(User user) {
        try {
            Connection conn = Dao.getConn();
                    PreparedStatement ps1 = conn.prepareStatement("update ad_user set status = ? where username = ?");
                    ps1.setString(1, user.getStatus());
                    ps1.setString(2, user.getName());
                    ps1.executeUpdate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "数据库异常！" + ex.getMessage());
            return;
        }
    }

    public static void resetPwd(User user, AbstractTableModel model) {
        try {
            Connection conn = Dao.getConn();
            PreparedStatement ps1 = conn.prepareStatement("update ad_user set password = ? where username = ?");
            ps1.setString(1, "123456");
            ps1.setString(2, user.getName());
            model.fireTableDataChanged();
            ps1.executeUpdate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "数据库异常！" + ex.getMessage());
            return;
        }
    }


    public static void setPwd(User user) {
        try {
            Connection conn = Dao.getConn();
            PreparedStatement ps1 = conn.prepareStatement("update ad_user set password = ? where username = ?");
            ps1.setString(1, user.getPwd());
            ps1.setString(2, user.getName());
            ps1.executeUpdate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "数据库异常！" + ex.getMessage());
            return;
        }
    }


    public static void deleteUser(User user) {
        Connection conn = null;
        try {
            conn = Dao.getConn(); // 获得数据库连接
            // 创建PreparedStatement对象，并传递SQL语句
            PreparedStatement ps = conn
                    .prepareStatement("delete from ad_user where username = ?");
            ps.setString(1, user.getName()); // 为参数赋值
            ps.executeUpdate();// 执行sql
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "用户名重复，请换个名称！");
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {

            }
        }
    }


}

