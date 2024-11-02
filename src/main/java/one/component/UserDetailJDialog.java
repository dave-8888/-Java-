package one.component;

import one.domain.User;
import one.util.ScreenUtils;
import one.util.UserUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserDetailJDialog extends JDialog {
    public static final int WIDTH = 400;
    public static final int HEIGHT =250;
    public void init(User user){
        this.setTitle("个人信息");
        this.setSize(WIDTH, HEIGHT);
        this.setBounds((ScreenUtils.getScreenWidth() - WIDTH) / 2, (ScreenUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        Box userBox =Box.createVerticalBox();
        userBox.add(Box.createVerticalStrut(20));
        //用户昵称
        Box nameBox =Box.createHorizontalBox();
        JLabel userName =new JLabel("用户昵称：");
        JTextField userNameText=new JTextField(user.getUserName());
        nameBox.add(Box.createHorizontalStrut(100));
        nameBox.add(userName);
        nameBox.add(userNameText);
        nameBox.add(Box.createHorizontalStrut(100));
        userBox.add(nameBox);
        userBox.add(Box.createVerticalStrut(20));
        //用户性别
        Box genderBox =Box.createHorizontalBox();
        JLabel userGender = new JLabel("性        别：");
        JRadioButton maleBtn = new JRadioButton("男");
        JRadioButton femaleBtn = new JRadioButton("女");
        if(user.getUserGender()==1){
            maleBtn.setSelected(true);
        }else{
            femaleBtn.setSelected(true);
        }
        //实现单选的效果
        ButtonGroup bg = new ButtonGroup();
        bg.add(maleBtn);
        bg.add(femaleBtn);
        genderBox.add(Box.createHorizontalStrut(100));
        genderBox.add(userGender);
        genderBox.add(maleBtn);
        genderBox.add(femaleBtn);
        genderBox.add(Box.createHorizontalStrut(150));
        userBox.add(genderBox);
        userBox.add(Box.createVerticalStrut(20));

        //用户日期
        Box dateBox =Box.createHorizontalBox();
        JLabel userDateJlabel = new JLabel("出生日期：");
        JTextField userDateField = new JTextField(user.getUserDate());
        userDateField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    JDateChooser gg = new JDateChooser();
                    gg.showDateChooser();
                    userDateField.setText(gg.getDateFormat("yyyy-MM-dd"));

            }
        });
        dateBox.add(Box.createHorizontalStrut(100));
        dateBox.add(userDateJlabel);
        dateBox.add(userDateField);
        dateBox.add(Box.createHorizontalStrut(100));
        userBox.add(dateBox);
        userBox.add(Box.createVerticalStrut(20));

        //修改个人信息
        Box jBox =Box.createHorizontalBox();
        JButton updateJButton = new JButton("修改个人信息");
        JButton fixJButton=new JButton("确定修改");
        jBox.add(updateJButton);
        jBox.add(fixJButton);
        userBox.add(jBox);
        userBox.add(Box.createVerticalStrut(20));


        fixJButton.setVisible(false);
        userNameText.setEnabled(false);
        maleBtn.setEnabled(false);
        femaleBtn.setEnabled(false);
        userDateField.setEnabled(false);
        updateJButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateJButton.setVisible(false);
                fixJButton.setVisible(true);
                userNameText.setEnabled(true);
                maleBtn.setEnabled(true);
                femaleBtn.setEnabled(true);
                userDateField.setEnabled(true);

            }
        });
        fixJButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User u = new User();
                u.setUserID(user.getUserID());
                u.setUserName(userNameText.getText());
                u.setUserPassword(user.getUserPassword());
                if(maleBtn.isSelected()){
                    u.setUserGender(1);
                }else{
                    u.setUserGender(0);
                }
                u.setUserDate(userDateField.getText());
                u.setProblemOne(user.getProblemOne());
                u.setAnswerOne(user.getAnswerOne());
                u.setProblemTwo(user.getProblemTwo());
                u.setAnswerTwo(user.getAnswerTwo());
                u.setProblemThree(user.getProblemThree());
                u.setAnswerThree(user.getAnswerThree());
                if(!UserUtils.isJudgeNull(u)){
                    //更新用户信息
                    int result=JOptionPane.showConfirmDialog(null, "您是否修改用户信息？", "确认对话框",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (result == JOptionPane.YES_OPTION){
                        UserUtils.updateUserAllDetailUtil(u);
                        JOptionPane.showMessageDialog(null,"修改个人信息成功！");
                        dispose();
                        new UserDetailJDialog().init(u);
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"用户信息填写不完善，请补充信息");
                }

            }
        });
        this.add(userBox);
        this.setResizable(false);
        this.setVisible(true);

    }
    public static void main(String args[]){
        User user = UserUtils.SelectUserByID("12345678901");
        new UserDetailJDialog().init(user);
    }
}
