package ADRAF.com.nk.frame;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JMenuBar jMenuBar;
    private JMenu jMenu;
    private JMenuItem jMenuItem;

    public MainFrame() throws HeadlessException {
        inti();
//        intibar();


        this.setVisible(true);
    }

    private void inti() {
        this.setSize(1400, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setTitle("药物不良反应查询反馈平台");
    }


    private void intimain(){

    }

//    private void intibar(){
//        jMenuBar = new JMenuBar();
//        jMenu = new JMenu("查询");
//        jMenuItem = new JMenuItem("药物查询");
//
//        jMenu.add(jMenuItem);
//        jMenuBar.add(jMenu);
//        this.setJMenuBar(jMenuBar);
//
//
//    }
}
