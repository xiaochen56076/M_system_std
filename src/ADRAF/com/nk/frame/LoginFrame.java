package ADRAF.com.nk.frame;

// @Author：nskdf
// @Time：2026-06-04-16-39
// @Project：ADARF_P

import ADRAF.com.nk.bean.User;
import ADRAF.com.nk.dao.UserDao;
import ADRAF.com.nk.tool.font;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;


public class LoginFrame extends JFrame {



    private JLabel userLabel;
    private JLabel pwdLabel;
    private JTextField usertext;
    private JPasswordField pwktext;
    private JButton Btn_login;
    private JButton Btn_sign;
    private JButton Btn_temp;
    private URL url;
    private JLabel bg;

    public static void main(String[] args) {
        LoginFrame thisclass = new LoginFrame();
    }

    //	初始化一些必要设置
    private void init() {
        this.setSize(550, 350);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("登录");
        this.setLayout(null);
    }


    private void inittext() {


        userLabel = new JLabel("用户名：");
        userLabel.setFont(font.ft);//设置字体
        userLabel.setBounds(260, 155, 70, 26);
        this.add(userLabel);

        usertext = new JTextField();
        usertext.setFont(font.ft);
        usertext.setBounds(335, 155, 160, 26);
        this.add(usertext);


        pwdLabel = new JLabel("密  码：");
        pwdLabel.setFont(font.ft);
        pwdLabel.setBounds(260, 200, 70, 26);
        this.add(pwdLabel);

        pwktext = new JPasswordField();
        pwktext.setFont(font.ft);
        pwktext.setBounds(335, 200, 160, 26);
        this.add(pwktext);


        Btn_login = new JButton("登 录");
        Btn_login.setBounds(260, 260, 70, 32);


        Btn_login.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userlogin();
            }
        });

        Btn_temp = new JButton("访客登录");
        Btn_temp.setBounds(440, 260, 90, 32);


        Btn_temp.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DisplayFrame_Visitor mfs = new DisplayFrame_Visitor();
                dispose();
            }
        });


        Btn_sign = new JButton("注 册");
        Btn_sign.setBounds(350, 260, 70, 32);

        Btn_sign.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterFrame register = new RegisterFrame();
                dispose();
            }
        });

        this.add(Btn_temp);
        this.add(Btn_login);
        this.add(Btn_sign);
    }

    private void initigraph() {
        url = getClass().getResource("/ADRAF/images/bg_in.png");
        if (url != null) {
            Image img = new ImageIcon(url).getImage().getScaledInstance(550, 300, Image.SCALE_SMOOTH);
            bg = new JLabel(new ImageIcon(img));
            bg.setBounds(0, 0, 550, 300);
            this.add(bg);
        }
    }

    private void userlogin(){
        String name = usertext.getText().trim();
        String password = new String(pwktext.getPassword());
        User user = new User();
        user.setName(name);
        user.setPwd(password);
        if(UserDao.userLogin(user)){
            DisplayFrame_Visitor mf = new DisplayFrame_Visitor();
            dispose();
        }


    }




    public LoginFrame() throws HeadlessException {
        init();
        inittext();
        initigraph();
        this.setVisible(true);
    }


}
