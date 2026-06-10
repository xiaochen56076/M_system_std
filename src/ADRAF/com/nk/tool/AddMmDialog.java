package ADRAF.com.nk.tool;

import ADRAF.com.nk.bean.Medicine;
import ADRAF.com.nk.dao.MedicineDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddMmDialog extends JFrame {

    public AddMmDialog() throws HeadlessException {
        setTitle("记录详情");
        setSize(450, 380);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lbl1 = new JLabel("药物名称");
        lbl1.setBounds(50, 20, 70, 25);
        add(lbl1);

        JTextField jtf1 = new JTextField();
        jtf1.setBounds(138, 20, 200, 25);
        add(jtf1);

        JLabel lbl2 = new JLabel("国字号");
        lbl2.setBounds(50, 60, 70, 25);
        add(lbl2);

        JTextField jtf2 = new JTextField();
        jtf2.setBounds(138, 60, 200, 25);
        add(jtf2);

        JLabel lbl3 = new JLabel("不良反应");
        lbl3.setBounds(50, 100, 70, 25);
        add(lbl3);

        JTextArea jta1 = new JTextArea();
        jta1.setLineWrap(true);
        jta1.setWrapStyleWord(true);
        JScrollPane jsl1 = new JScrollPane(jta1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jsl1.setBounds(140, 105, 200, 60);
        add(jsl1);


        JLabel lbl4 = new JLabel("禁忌");
        lbl4.setBounds(50, 180, 70, 25);
        add(lbl4);

        JTextArea jta2 = new JTextArea();
        jta2.setLineWrap(true);
        jta2.setWrapStyleWord(true);
        JScrollPane jsl2 = new JScrollPane(jta2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jsl2.setBounds(140, 185, 200, 70);
        add(jsl2);



        JButton closeBtn = new JButton("关闭");
        closeBtn.setBounds(150, 270, 80, 30);
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JButton yesBtn = new JButton("确认");
        yesBtn.setBounds(240, 270, 80, 30);
        yesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Medicine m = new Medicine(jtf1.getText().trim(),
                        jtf2.getText().trim(),
                        jta1.getText().trim(),
                        jta2.getText().trim());
                MedicineDao.insertMedicine(m);
            }
        });





        setAlwaysOnTop(true);
        setResizable(false);
        add(yesBtn);
        add(closeBtn);
        setVisible(true);
    }


    public static void main(String[] args) {
        new AddMmDialog();
    }




}
