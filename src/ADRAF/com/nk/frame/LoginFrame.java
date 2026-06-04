package ADRAF.com.nk.frame;

import ADRAF.com.nk.bean.User;
import ADRAF.com.nk.dao.UserDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;

/**
 * 登陆面板
 *
 */
public class LoginFrame extends JFrame {
    private JLabel titleLabel;
    private JLabel userLabel;
    private JLabel pwdLabel;
    private JTextField usertext;
    private JPasswordField pwktext;
    private JButton Btn_login;
    private JButton Btn_sign;
    private URL url;
    private JLabel bg;

    public static void main(String[] args) {
        LoginFrame thisclass = new LoginFrame();
    }

    //	初始化一些必要设置
    private void initialize() {
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("登录");
        this.setLayout(null);
    }


    private void inittext() {
//        Font labelFont = new Font("PingFang SC", Font.PLAIN, 14);
//        Font titleFont = new Font("PingFang SC", Font.BOLD, 20);

//        //  标题
//        titleLabel = new JLabel("欢迎来到药物不良反应查询反馈平台", SwingConstants.CENTER);
////        titleLabel.setFont(titleFont);
//        titleLabel.setBounds(0, 30, 400, 30);
//        this.add(titleLabel);





        userLabel = new JLabel("用户名：");
//        userLabel.setFont(labelFont);
        userLabel.setBounds(100, 85, 70, 25);
        this.add(userLabel);

        usertext = new JTextField();
//        usertext.setFont(labelFont);
        usertext.setBounds(175, 85, 180, 25);
        this.add(usertext);


        pwdLabel = new JLabel("密  码：");
//        pwdLabel.setFont(labelFont);
        pwdLabel.setBounds(100, 125, 70, 25);
        this.add(pwdLabel);

        pwktext = new JPasswordField();
//        pwktext.setFont(labelFont);
        pwktext.setBounds(175, 125, 180, 25);
        this.add(pwktext);





        Btn_login = new JButton("登 录");
//        Btn_login.setFont(labelFont);
        Btn_login.setBounds(130, 180, 90, 30);


        Btn_login.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userlogin();
            }
        });
        this.add(Btn_login);




        Btn_sign = new JButton("注 册");
//        Btn_sign.setFont(labelFont);
        Btn_sign.setBounds(240, 180, 90, 30);

        Btn_sign.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register register = new Register();
                register.setTitle("注册");
            }
        });
        this.add(Btn_sign);
    }

    private void intigraph() {
        url = getClass().getResource("/ADRAF/images/001.jpg");
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
            bg = new JLabel(new ImageIcon(img));
            bg.setBounds(0, 0, 400, 300);
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
            MainFrame mf = new MainFrame();
            dispose();
        }


    }




    public LoginFrame() throws HeadlessException {
        initialize();
        inittext();
        intigraph();
        this.setVisible(true);
    }


}
