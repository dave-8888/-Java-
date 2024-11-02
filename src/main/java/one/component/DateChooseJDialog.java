package one.component;

import one.domain.Schedule;
import one.domain.User;
import one.util.ExportExcel;
import one.util.ScheduleUtils;
import one.util.ScreenUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DateChooseJDialog extends JDialog {
    public static final int WIDTH = 300;
    public static final int HEIGHT =200;
    public void init(User user){
        this.setTitle("导出日程信息:  <<"+user.getUserName()+">>");
        this.setSize(WIDTH, HEIGHT);
        this.setBounds((ScreenUtils.getScreenWidth() - WIDTH) / 2, (ScreenUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);

        Box dateChooseAllBox=Box.createVerticalBox();
        dateChooseAllBox.add(Box.createVerticalStrut(30));

        Box startTimeBox=Box.createHorizontalBox();
        JLabel startjLable=new JLabel("开始时间：");
        JTextField startjTextField =new JTextField(15);
        startTimeBox.add(Box.createHorizontalStrut(10));
        startTimeBox.add(startjLable);
        startTimeBox.add(startjTextField);
        dateChooseAllBox.add(startTimeBox);
        startTimeBox.add(Box.createHorizontalStrut(10));
        startjTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JDateChooser gg = new JDateChooser();
                gg.showDateChooser();
                if(gg.getFlag()){
                    startjTextField.setText(gg.getDateFormat("yyyy-MM-dd"));
                }else{
                    startjTextField.setText("");
                }
            }
        });

        Box endTimeBox =Box.createHorizontalBox();
        JLabel endjLabel=new JLabel("结束时间：");
        JTextField endjTextField=new JTextField(15);
        endTimeBox.add(Box.createHorizontalStrut(10));
        endTimeBox.add(endjLabel);
        endTimeBox.add(endjTextField);
        endTimeBox.add(Box.createHorizontalStrut(10));
        dateChooseAllBox.add(Box.createVerticalStrut(20));
        dateChooseAllBox.add(endTimeBox);
        endjTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    JDateChooser gg = new JDateChooser();
                    gg.showDateChooser();
                    if(gg.getFlag()){
                        endjTextField.setText(gg.getDateFormat("yyyy-MM-dd"));
                    }else{
                        endjTextField.setText("");
                    }
                }
        });
        Box exportBox=Box.createHorizontalBox();
        JButton jButton = new JButton("确定导出");
        exportBox.add(jButton);
        dateChooseAllBox.add(Box.createVerticalStrut(20));
        dateChooseAllBox.add(exportBox);
        dateChooseAllBox.add(Box.createVerticalStrut(10));
        this.add(dateChooseAllBox);
        this.setResizable(false);
        this.setVisible(true);

        jButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(startjTextField.getText()!=null&&!startjTextField.getText().equals("")
                        &&ScheduleUtils.compareDateIsError2(startjTextField.getText(),endjTextField.getText()) ){
                    ArrayList<Schedule> schedules=ScheduleUtils.selectSchedulesByIDAndByDate(user.getUserID(),startjTextField.getText(),endjTextField.getText());

                    //定义一个一维数组，作为列标题
                    Object[] columnTitle = new Object[]{"日程标题", "日程详情", "开始时间", "结束时间", "是否提醒","是否完成"};
                    Object[][] tableData = new Object[schedules.size()][6];
                    for (int i = 0; i < schedules.size(); i++) {
                        tableData[i][0] = schedules.get(i).getScheduleTitle();
                        tableData[i][1] = schedules.get(i).getScheduleDetail();
                        tableData[i][2]=schedules.get(i).getStartTime();
                        if(schedules.get(i).getEndTime()==null){
                            schedules.get(i).setEndTime("");
                        }
                        tableData[i][3]=schedules.get(i).getEndTime();
                        if (schedules.get(i).getIsAlert() == 1) {
                            tableData[i][4] = "是";
                        } else {
                            tableData[i][4] = "否";
                        }
                        if(schedules.get(i).getIsOk()==1){
                            tableData[i][5]=true;
                        }else{
                            tableData[i][5]=false;
                        }
                    }
                    JTable jTable=new JTable(tableData,columnTitle);
                    //先实例化这个类 传参数 得到一个对象j
                    new ExportExcel(jTable,"new.xls").export();
                    dispose();
                }else if(endjTextField.getText()==null||endjTextField.getText().equals("")||startjTextField.getText()==null||startjTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,"输入时间段不完整，请继续输入");
                }else{
                    JOptionPane.showMessageDialog(null,"输入时间段不合法，请继续输入");
                }
            }
        });

    }
    public static void main(String args[]){
        User user=new User();
        user.setUserID("12345678901");
        new DateChooseJDialog().init(user);
    }
}
