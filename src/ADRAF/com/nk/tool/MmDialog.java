package ADRAF.com.nk.tool;

// @Author：nskdf
// @Time：2026-06-07-21-10
// @Project：ADARF_P
import ADRAF.com.nk.bean.Medicine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MmDialog extends JDialog {

    public MmDialog(Medicine mm) {
        setTitle("药物详情");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lbl1 = new JLabel("国药准字：");
        lbl1.setBounds(20, 20, 70, 25);
        add(lbl1);
        JLabel val1 = new JLabel(mm.getId());
        val1.setBounds(100, 20, 310, 25);
        add(val1);

        JLabel lbl2 = new JLabel("名称：");
        lbl2.setBounds(20, 55, 70, 25);
        add(lbl2);
        JLabel val2 = new JLabel(mm.getName());
        val2.setBounds(100, 55, 310, 25);
        add(val2);

        JLabel lbl3 = new JLabel("不良反应：");
        lbl3.setBounds(20, 90, 70, 25);
        add(lbl3);
        JTextArea taAdverse = new JTextArea(mm.getAdverseReaction());
        taAdverse.setLineWrap(true);
        taAdverse.setWrapStyleWord(true);
        taAdverse.setEditable(false);
        JScrollPane spAdverse = new JScrollPane(taAdverse);
        spAdverse.setBounds(100, 90, 310, 60);
        add(spAdverse);


        JLabel lbl4 = new JLabel("禁忌：");
        lbl4.setBounds(20, 165, 70, 25);
        add(lbl4);
        JTextArea taContra = new JTextArea(mm.getContraindication());
        taContra.setLineWrap(true);
        taContra.setWrapStyleWord(true);
        taContra.setEditable(false);
        JScrollPane spContra = new JScrollPane(taContra);
        spContra.setBounds(100, 165, 310, 60);
        add(spContra);


        JButton closeBtn = new JButton("关闭");
        closeBtn.setBounds(185, 250, 80, 30);
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setModal(true);
        setAlwaysOnTop(true);
        setResizable(false);
        add(closeBtn);
        setVisible(true);
    }
}