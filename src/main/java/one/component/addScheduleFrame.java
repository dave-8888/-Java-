package one.component;

import one.domain.Schedule;
import one.domain.User;
import one.util.ScheduleUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class addScheduleFrame
{
    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;
    public static void main(String[] args) {
        User user = new User();
        user.setUserID("12345678901");
        new addScheduleFrame().init(user);
    }

    JFrame jFrame;
    public void init(User user){
        jFrame=new JFrame("添加日程");
        jFrame.setSize(WIDTH, HEIGHT);
        Box JBox =Box.createVerticalBox();
        Schedule schedule = new Schedule();
        //日程标题
        Box titleBox=Box.createHorizontalBox();
        JLabel titleJLabel=new JLabel("    日程标题：");
        JTextField titleJTextField=new JTextField(15);
        titleBox.add(titleJLabel);
        titleBox.add(titleJTextField);
        titleBox.add(Box.createHorizontalStrut(150));
        //日程详情
        Box detailBox = Box.createHorizontalBox();
        JLabel detailJLabel =new JLabel("    日程详情：");
        JTextArea detailJTextArea=new JTextArea(5,15);
        //detailJTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        detailBox.add(detailJLabel);
        detailBox.add(detailJTextArea);
        detailBox.add(Box.createHorizontalStrut(10));
        //开始时间
        Box startBox=Box.createHorizontalBox();
        JLabel startDateLable=new JLabel("    开始日期：");
        JTextField startDateField=new JTextField(10);
        startDateField.setText( new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        JLabel startTimeLable=new JLabel("时间：");
        SpinnerNumberModel startHourModel=new SpinnerNumberModel(new GregorianCalendar().get(Calendar.HOUR_OF_DAY) ,new GregorianCalendar().get(Calendar.HOUR_OF_DAY),23,1);
        JSpinner startHourSpinner=new JSpinner(startHourModel);
        SpinnerNumberModel startMinuteModel=new SpinnerNumberModel(new GregorianCalendar().get(Calendar.MINUTE),0,60,1);
        JSpinner startMinuteSpinner=new JSpinner(startMinuteModel);
        startBox.add(startDateLable);
        startBox.add(startDateField);
        startBox.add(Box.createHorizontalStrut(20));
        startBox.add(startTimeLable);
        startBox.add(startHourSpinner);
        startBox.add(new JLabel("时"));
        startBox.add(Box.createHorizontalStrut(10));
        startBox.add(startMinuteSpinner);
        startBox.add(new JLabel("分"));
        startBox.add(Box.createHorizontalStrut(10));
        startDateField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JDateChooser gg = new JDateChooser();
                gg.showDateChooser();
                if(gg.getFlag()){
                    startDateField.setText(gg.getDateFormat("yyyy-MM-dd"));
                }else{
                    startDateField.setText("");
                }
            }
        });
        //结束时间
        Box endBox=Box.createHorizontalBox();
        JLabel endDateLable=new JLabel("    结束日期：");
        JTextField endDateField=new JTextField(10);
        JLabel endTimeLable=new JLabel("时间：");
        SpinnerNumberModel endHourModel=new SpinnerNumberModel(0 ,0,23,1);
        JSpinner endHourSpinner=new JSpinner(endHourModel);
        SpinnerNumberModel endMinuteModel=new SpinnerNumberModel(0,0,60,1);
        JSpinner endMinuteSpinner=new JSpinner(endMinuteModel);
        endBox.add(endDateLable);
        endBox.add(endDateField);
        endBox.add(Box.createHorizontalStrut(20));
        endBox.add(endTimeLable);
        endBox.add(endHourSpinner);
        endBox.add(new JLabel("时"));
        endBox.add(Box.createHorizontalStrut(10));
        endBox.add(endMinuteSpinner);
        endBox.add(new JLabel("分"));
        endBox.add(Box.createHorizontalStrut(10));
        endDateField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JDateChooser gg = new JDateChooser();
                gg.showDateChooser();
                if(gg.getFlag()){
                    endDateField.setText(gg.getDateFormat("yyyy-MM-dd"));
                }else{
                    endDateField.setText("");
                }

            }
        });
        //是否设置提醒
        Box isAlertBox=Box.createHorizontalBox();
        JLabel isAlertJLabel=new JLabel("    是否提醒：");
        JRadioButton yes=new JRadioButton("是");
        JRadioButton no = new JRadioButton("否",true);
        ButtonGroup isAlertGroup = new ButtonGroup();
        isAlertGroup.add(yes);
        isAlertGroup.add(no);
        isAlertBox.add(isAlertJLabel);
        isAlertBox.add(yes);
        isAlertBox.add(no);
        isAlertBox.add(Box.createHorizontalStrut(250));
        //设置添加按钮
        Box addBox = Box.createHorizontalBox();
        JButton addJButton = new JButton("确认添加日程");
        addBox.add(addJButton);
        JBox.add(Box.createVerticalStrut(20));
        JBox.add(titleBox);
        JBox.add(Box.createVerticalStrut(20));
        JBox.add(detailBox);
        JBox.add(Box.createVerticalStrut(20));
        JBox.add(startBox);
        JBox.add(Box.createVerticalStrut(20));
        JBox.add(endBox);
        JBox.add(Box.createVerticalStrut(20));
        JBox.add(isAlertBox);
        JBox.add(Box.createVerticalStrut(20));
        JBox.add(addBox);
        //单选按钮监听事件
        ActionListener buttonLister = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if(command.equals("是")){
                    schedule.setIsAlert(1);
                }else{
                    schedule.setIsAlert(0);
                }
            }
        };
        yes.addActionListener(buttonLister);
        no.addActionListener(buttonLister);
        addJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                schedule.setUserID(user.getUserID());
                schedule.setScheduleTitle(titleJTextField.getText());
                schedule.setScheduleDetail(detailJTextArea.getText());
                schedule.setStartTime(startDateField.getText()+" "+startHourSpinner.getValue()+":"+startMinuteSpinner.getValue());
                if(endDateField.getText()==null||endDateField.getText().equals("")){
                    schedule.setEndTime(null);
                }else{
                    schedule.setEndTime(endDateField.getText()+" "+endHourSpinner.getValue()+":"+endMinuteSpinner.getValue());
                }
                if(ScheduleUtils.completeScheduleUtil(schedule)){
                    ScheduleUtils.insertScheduleUtil(schedule);
                }else{
                    JOptionPane.showMessageDialog(null,"信息填写不完整，请继续填写");
                }
            }
        });
        jFrame.add(JBox);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
}