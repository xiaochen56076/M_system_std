package ADRAF.com.nk.tool;

import ADRAF.com.nk.bean.Records;
import ADRAF.com.nk.dao.RecordDao;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrecDialog extends JDialog {
    private boolean updated = false;

    public boolean isUpdated(){
        return updated;
    }


    public PrecDialog (Records record, String status){
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


        JLabel lbl5 = new JLabel("医生建议");
        lbl5.setBounds(20, 230, 70, 25);
        add(lbl5);
        JTextArea Opinion = new JTextArea();
        Opinion.setLineWrap(true);
        Opinion.setWrapStyleWord(true);
        JScrollPane spOpinion = new JScrollPane(Opinion);
        spOpinion.setBounds(100, 230, 310, 60);
        add(spOpinion);

        JButton closeBtn = new JButton("返回");
        closeBtn.setBounds(135, 305, 80, 30);
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //仅此关闭这个窗口，就是停止此次操作
                dispose();
            }
        });


        JButton submitBtn = new JButton("确认");
        submitBtn.setBounds(225, 305, 80, 30);
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                record.setDoctorOpinion(Opinion.getText().trim());
                RecordDao.updateDoctorrecord(record, status, PrecDialog.this);
                updated = true;
                dispose();
            }
        });




        setModal(true);
        setAlwaysOnTop(true);
        setResizable(false);
        add(closeBtn);
        add(submitBtn);
        setVisible(true);
    }
}
