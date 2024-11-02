package one.component;

import one.domain.Schedule;
import one.domain.User;
import one.ui.MainInterface;
import one.util.ScheduleUtils;
import one.util.ScreenUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class EditScheduleJDialog extends JDialog
{
    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;
    private boolean isAdd = false;

    public void init(User user, Schedule schedule, Boolean isUpdate, Boolean isView){
        this.setSize(WIDTH, HEIGHT);
        this.setBounds((ScreenUtils.getScreenWidth() - WIDTH) / 2, (ScreenUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        Box JBox =Box.createVerticalBox();
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
        detailJTextArea.setLineWrap(true);
        detailJTextArea.setWrapStyleWord(true);
        detailBox.add(detailJLabel);
        detailBox.add(new JScrollPane(detailJTextArea));
        detailBox.add(Box.createHorizontalStrut(10));
        //开始时间
        Box startBox=Box.createHorizontalBox();
        JLabel startDateLable=new JLabel("    开始日期：");
        JTextField startDateField=new JTextField(10);
        int month=MainInterface.calendar.get(Calendar.MONTH)+1;
        String smonth;
        if(month<10){
            smonth="0"+month;
        }else{
            smonth=month+"";
        }
        int day=MainInterface.calendar.get(Calendar.DAY_OF_MONTH);
        String sday;
        if(day<10){
            sday="0"+day;
        }else{
            sday=day+"";
        }
        startDateField.setText(MainInterface.calendar.get(Calendar.YEAR)+"-"+smonth+"-"+sday);
        JLabel startTimeLable=new JLabel("时间：");
        SpinnerNumberModel startHourModel=new SpinnerNumberModel(new GregorianCalendar().get(Calendar.HOUR_OF_DAY) ,0,23,1);
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
                if(!isView){
                    JDateChooser gg = new JDateChooser();
                    gg.showDateChooser();
                    if(gg.getFlag()){
                        startDateField.setText(gg.getDateFormat("yyyy-MM-dd"));
                    }else{
                        startDateField.setText("");
                    }
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
                if (!isView){
                    JDateChooser gg = new JDateChooser();
                    gg.showDateChooser();
                    if(gg.getFlag()){
                        endDateField.setText(gg.getDateFormat("yyyy-MM-dd"));
                    }else{
                        endDateField.setText("");
                    }
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

        //如果编辑日程，则
        if(isUpdate){
            titleJTextField.setText(schedule.getScheduleTitle());
            detailJTextArea.setText(schedule.getScheduleDetail());

            if(schedule.getIsAlert()==1){
                System.out.println("schedule.getIsAlert==1");
                yes.setSelected(true);
            }else{
                System.out.println("schedule.getIsAlert==0");
                no.setSelected(true);
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date startDate=dateFormat.parse(schedule.getStartTime());
                Calendar time = Calendar.getInstance();
                time.setTime(startDate);
                startDateField.setText(time.get(Calendar.YEAR)+"-"+(time.get(Calendar.MONTH)+1)+"-"+time.get(Calendar.DAY_OF_MONTH));
                startHourSpinner.setValue(time.get(Calendar.HOUR_OF_DAY));
                startMinuteSpinner.setValue(time.get(Calendar.MINUTE));
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            if(schedule.getEndTime()!=null&&!schedule.getEndTime().equals("")){
                try {
                    Date endDate=dateFormat.parse(schedule.getEndTime());
                    Calendar time = Calendar.getInstance();
                    time.setTime(endDate);
                    endDateField.setText(time.get(Calendar.YEAR)+"-"+(time.get(Calendar.MONTH)+1)+"-"+time.get(Calendar.DAY_OF_MONTH));
                    endHourSpinner.setValue(time.get(Calendar.HOUR_OF_DAY));
                    endMinuteSpinner.setValue(time.get(Calendar.MINUTE));
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            }

        }
        //如果查看日程
        if(isView){
            titleJTextField.setEnabled(false);
            detailJTextArea.setEnabled(false);
            startDateField.setEnabled(false);
            startHourSpinner.setEnabled(false);
            startMinuteSpinner.setEnabled(false);
            endDateField.setEnabled(false);
            endHourSpinner.setEnabled(false);
            endMinuteSpinner.setEnabled(false);
            yes.setEnabled(false);
            no.setEnabled(false);
        }


        //设置添加按钮
        Box addBox = Box.createHorizontalBox();
        JButton addJButton = new JButton("确认");
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
        addJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                schedule.setUserID(user.getUserID());
                schedule.setScheduleTitle(titleJTextField.getText());
                schedule.setScheduleDetail(detailJTextArea.getText());
                //比较时间
                String startTime=startDateField.getText()+" "+startHourSpinner.getValue()+":"+startMinuteSpinner.getValue();
                schedule.setStartTime(startTime);
                String endTime=null;
                if(endDateField.getText()==null||endDateField.getText().equals("")){
                    endTime=null;
                }else{
                    endTime=endDateField.getText()+" "+endHourSpinner.getValue()+":"+endMinuteSpinner.getValue();
                }
                schedule.setEndTime(endTime);
                if(yes.isSelected()){
                    schedule.setIsAlert(1);
                }else{
                    schedule.setIsAlert(0);
                }
                if(ScheduleUtils.completeScheduleUtil(schedule)&&!ScheduleUtils.compareDateIsError(startTime,endTime)){
                    if(isUpdate){
                        ScheduleUtils.UpdateScheduleUtil(schedule);
                    }else{
                        ScheduleUtils.insertScheduleUtil(schedule);
                    }
                    isAdd=true;
                    dispose();
                }else if(ScheduleUtils.compareDateIsError(startTime,endTime)){
                    JOptionPane.showMessageDialog(null,"日期格式填写有误，请重新填写");
                }else{
                    JOptionPane.showMessageDialog(null,"日程标题字数大于12或为空，请重新填写");
                }
            }
        });
        this.add(JBox);
        this.setModalityType(ModalityType.APPLICATION_MODAL);

    }
    public void showAddScheduleFrame(){
        this.setVisible(true);
    }

    public boolean getIsAdd(){
        return isAdd;
    }
}
