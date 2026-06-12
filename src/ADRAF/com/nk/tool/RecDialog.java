package ADRAF.com.nk.tool;

import ADRAF.com.nk.bean.Records;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecDialog extends JDialog {

    public RecDialog(Records record) {
        setTitle("记录详情");
        setSize(450, 380);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lbl1 = new JLabel("药物名称");
        lbl1.setBounds(20, 20, 70, 25);
        add(lbl1);
        JLabel val1 = new JLabel(record.getMeName());
        val1.setBounds(100, 20, 310, 25);
        add(val1);

        JLabel lbl2 = new JLabel("症状");
        lbl2.setBounds(20, 55, 70, 25);
        add(lbl2);
        JTextArea taSymptom = new JTextArea(record.getSymptom());
        taSymptom.setLineWrap(true);
        taSymptom.setWrapStyleWord(true);
        taSymptom.setEditable(false);
        JScrollPane spSymptom = new JScrollPane(taSymptom);
        spSymptom.setBounds(100, 55, 310, 60);
        add(spSymptom);

        JLabel lbl3 = new JLabel("使用时间");
        lbl3.setBounds(20, 125, 70, 25);
        add(lbl3);
        JLabel val3 = new JLabel(record.getDays() + "天");
        val3.setBounds(100, 125, 310, 25);
        add(val3);

        JLabel lbl4 = new JLabel("上报时间");
        lbl4.setBounds(20, 160, 70, 25);
        add(lbl4);
        JLabel val4 = new JLabel(record.getReportTime());
        val4.setBounds(100, 160, 310, 25);
        add(val4);

        JLabel lbl5 = new JLabel("审核状态");
        lbl5.setBounds(20, 195, 70, 25);
        add(lbl5);
        JLabel val5 = new JLabel(record.getStatus());
        val5.setBounds(100, 195, 70, 25);
        if(record.getStatus().equals("已通过")){
            val5.setForeground(new Color(80, 200, 0));
        }
        else if(record.getStatus().equals("已驳回")){
            val5.setForeground(new Color(200, 100, 0));
        }
        else {
            val5.setForeground(Color.BLACK);
        }
        add(val5);

        JLabel lbl6 = new JLabel("医生建议");
        lbl6.setBounds(20, 230, 70, 25);
        add(lbl6);
        JTextArea taOpinion = new JTextArea(record.getDoctorOpinion() != null ? record.getDoctorOpinion() : "无");
        taOpinion.setLineWrap(true);
        taOpinion.setWrapStyleWord(true);
        taOpinion.setEditable(false);
        JScrollPane spOpinion = new JScrollPane(taOpinion);
        spOpinion.setBounds(100, 230, 310, 60);
        add(spOpinion);

        JButton closeBtn = new JButton("关闭");
        closeBtn.setBounds(185, 305, 80, 30);
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
