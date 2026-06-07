package ADRAF.com.nk.frame;

// @Author：nskdf
// @Time：2026-06-05-13-39
// @Project：ADARF_P

import ADRAF.com.nk.bean.User;
import ADRAF.com.nk.dao.UserDao;
import ADRAF.com.nk.tool.Radom_code_tool;
import ADRAF.com.nk.tool.UserStateTool;
import ADRAF.com.nk.tool.font;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;

public class RegisterFrame extends JFrame {

    private JLabel userLabel;
    private JLabel pwdLabel;
    private JTextField usertext;
    private JPasswordField pwktext;
    private JButton Btn_sign;
    private JButton Btn_temp;
    private JButton Btn_exit;
    private JLabel okpwdLabel;
    private JPasswordField okpwdtext;
    private JLabel alLable;
    private JTextArea altext;
    private JLabel vcodeLable;
    private JTextField vcode;
    private JCheckBox okpwdbox;
    private JCheckBox pwdbox;
    private JLabel codegraph;
    private URL url;
    private JLabel bg;
    Object[] temp;






    public RegisterFrame() throws HeadlessException {
        init();
        inittext();
        intigraph();


        this.setVisible(true);
    }

    private void init(){
        this.setSize(550, 350);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("注册");
        this.setLayout(null);
    }


    private void inittext(){
        userLabel = new JLabel("用户名:");
        userLabel.setFont(font.ft);//设置字体
        userLabel.setBounds(30, 20, 80, 30);
        this.add(userLabel);

        usertext = new JTextField();
        usertext.setFont(font.ft);
        usertext.setBounds(100, 20, 180, 30);
        this.add(usertext);


        pwdLabel = new JLabel("密  码:");
        pwdLabel.setFont(font.ft);
        pwdLabel.setBounds(30, 65, 80, 30);
        this.add(pwdLabel);

        pwktext = new JPasswordField();
        pwktext.setFont(font.ft);
        pwktext.setBounds(100, 65, 180, 30);
        this.add(pwktext);


        okpwdLabel = new JLabel("确认密码:");
        okpwdLabel.setFont(font.ft);
        okpwdLabel.setBounds(30, 110, 80, 30);
        this.add(okpwdLabel);

        okpwdtext = new JPasswordField();
        okpwdtext.setFont(font.ft);
        okpwdtext.setBounds(100, 110, 180, 30);
        this.add(okpwdtext);


        alLable = new JLabel("过敏史(选填):");
        alLable.setFont(font.ft);
        alLable.setBounds(310, 20, 100, 30);
        this.add(alLable);

        altext = new JTextArea();
        altext.setFont(font.ft);
        altext.setWrapStyleWord(true);
        altext.setLineWrap(true);
        JScrollPane jsp = new JScrollPane(altext);
        jsp.setBounds(310, 50, 200, 90);
        this.add(jsp);



        vcodeLable = new JLabel("验证码:");
        vcodeLable.setFont(font.ft);
        vcodeLable.setBounds(30, 155, 80, 30);
        this.add(vcodeLable);

        vcode = new JTextField();
        vcode.setFont(font.ft);
        vcode.setBounds(100, 155, 180, 30);
        this.add(vcode);



        temp =Radom_code_tool.createImage();
        ImageIcon img = new ImageIcon((BufferedImage)temp[1]);
        UserStateTool.setvcode((String)temp[0]);
        codegraph = new JLabel(img);
        codegraph.setBounds(280, 155, 100, 30);
        codegraph.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 1){
                    temp =Radom_code_tool.createImage();
                    ImageIcon img = new ImageIcon((BufferedImage)temp[1]);
                    codegraph.setIcon((Icon) img);
                    UserStateTool.setvcode((String)temp[0]);
                }
            }
        });
        this.add(codegraph);



        this.add(pwdeye());
        this.add(okpwdeye());
        this.add(Btn_e());
        this.add(Btn_t());
        this.add(Btn_s());
    }

    private JCheckBox okpwdeye() {
        okpwdbox = new JCheckBox();
        okpwdbox.setFont(font.ft);
        okpwdbox.setBounds(275, 105,80, 40);
        okpwdbox.setBorderPainted(false);
        okpwdbox.setContentAreaFilled(false);
        okpwdbox.setFocusPainted(false);
        okpwdbox.setIcon(new ImageIcon(getClass().getResource("/ADRAF/images/close.png")));
        okpwdbox.setSelectedIcon(new ImageIcon(getClass().getResource("/ADRAF/images/open.png")));
        okpwdbox.setOpaque(false);
        okpwdbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(okpwdbox.isSelected()){
                    okpwdtext.setEchoChar((char)0);
                }
                else{
                    okpwdtext.setEchoChar('*');
                }
            }
        });
        return okpwdbox;
    }

    private JCheckBox pwdeye() {
        pwdbox = new JCheckBox();
        pwdbox.setFont(font.ft);
        pwdbox.setBounds(275, 60,80, 40);
        pwdbox.setBorderPainted(false);
        pwdbox.setContentAreaFilled(false);
        pwdbox.setFocusPainted(false);
        pwdbox.setIcon(new ImageIcon(getClass().getResource("/ADRAF/images/close.png")));
        pwdbox.setSelectedIcon(new ImageIcon(getClass().getResource("/ADRAF/images/open.png")));
        pwdbox.setOpaque(false);
        pwdbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(pwdbox.isSelected()){
                    pwktext.setEchoChar((char)0);

                }
                else{
                    pwktext.setEchoChar('*');

                }
            }
        });
        return pwdbox;
    }

    private JButton Btn_s(){
        Btn_sign = new JButton("重置");
        Btn_sign.setBounds(220, 220, 85, 35);

        Btn_sign.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usertext.setText("");
                pwktext.setText("");
                okpwdtext.setText("");
                vcode.setText("");
                altext.setText("");
            }
        });
        return Btn_sign;
    }

    private JButton Btn_t(){
        Btn_temp = new JButton("确认注册");
        Btn_temp.setBounds(315, 220, 85, 35);

        Btn_temp.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User(usertext.getText().trim(), new String(pwktext.getPassword()), new String(okpwdtext.getPassword()), altext.getText().trim());
                if(UserDao.insertUser(user, vcode.getText().trim())){
                    DisplayFrame_Patient dfv = new DisplayFrame_Patient();
                    dispose();
                }
            }
        });
        return Btn_temp;
    }

    private JButton Btn_e(){
        Btn_exit = new JButton("返回");
        Btn_exit.setBounds(125, 220, 85, 35);

        Btn_exit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginFrame lf = new LoginFrame();
                dispose();
            }
        });
        return Btn_exit;
    }

    private void intigraph() {
        //读取图片资源
        url = getClass().getResource("/ADRAF/images/bg_up.png");
        //对象中取出原始图片对象,进行设置参数
        Image img = new ImageIcon(url).getImage().getScaledInstance(550, 300, Image.SCALE_SMOOTH);
        bg = new JLabel(new ImageIcon(img));
        //设置显示坐标位置
        bg.setBounds(0, 0, 550, 300);
        this.add(bg);
    }




    public static void main(String[] args) {
        new RegisterFrame();
    }
}
