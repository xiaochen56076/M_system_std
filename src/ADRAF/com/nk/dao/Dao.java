package ADRAF.com.nk.dao;


import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class Dao {


    public static Connection getConn() {
        try {
            Connection conn = null; // 定义数据库连接
            String url = "jdbc:mysql://127.0.0.1:3306/adarf"; // 数据库db_Express的URL
            String username = "root"; // 数据库的用户名
            String password = "123456"; // 数据库密码
            conn = DriverManager.getConnection(url, username, password); // 建立连接
            return conn; // 返回连接
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "数据库连接失败。\n请检查是否安装了SP4补丁，\n以及数据库用户名和密码是否正确。"
                            + e.getMessage());
            return null;
        }
    }
    public static void main(String[] args) {
        System.out.println(getConn());
    }
}
