package one.component;

import one.domain.User;
import one.util.ScreenUtils;
import one.util.UserUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UserPasswordDetailDialog extends JDialog {
    public static final int WIDTH = 400;
    public static final int HEIGHT =500;

    public void init(User user){
        this.setTitle("修改密码信息:  <<"+user.getUserName()+">>");
        this.setSize(WIDTH, HEIGHT);
        this.setBounds((ScreenUtils.getScreenWidth() - WIDTH) / 2, (ScreenUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        Box userBox =Box.createVerticalBox();
        userBox.add(Box.createVerticalStrut(20));
        //用户旧密码
        Box oldPasswordBox =Box.createHorizontalBox();
        JLabel userOldPassword=new JLabel("旧  密  码：");
        JTextField userOldPasswordText = new JTextField(15);
        oldPasswordBox.add(Box.createHorizontalStrut(100));
        oldPasswordBox.add(userOldPassword);
        oldPasswordBox.add(userOldPasswordText);
        oldPasswordBox.add(Box.createHorizontalStrut(100));
        userBox.add(oldPasswordBox);
        userBox.add(Box.createVerticalStrut(20));
        //用户新密码
        Box newPasswordBox =Box.createHorizontalBox();
        JLabel newPassword = new JLabel("新  密  码：");
        JTextField newPasswordText = new JTextField(15);
        newPasswordBox.add(Box.createHorizontalStrut(100));
        newPasswordBox.add(newPassword);
        newPasswordBox.add(newPasswordText);
        newPasswordBox.add(Box.createHorizontalStrut(100));
        userBox.add(newPasswordBox);
        userBox.add(Box.createVerticalStrut(20));
        //密保问题1
        Box problemOneBox =Box.createHorizontalBox();
        JLabel problemOne=new JLabel("问  题  一：");
        JTextField problemOneText=new JTextField(user.getProblemOne());
        problemOneBox.add(Box.createHorizontalStrut(100));
        problemOneBox.add(problemOne);
        problemOneBox.add(problemOneText);
        problemOneBox.add(Box.createHorizontalStrut(100));
        userBox.add(problemOneBox);
        userBox.add(Box.createVerticalStrut(20));

        //密保答案1
        Box answerOneBox =Box.createHorizontalBox();
        JLabel answerOne=new JLabel("答        案：");
        JTextField answerOneText = new JTextField(user.getAnswerOne());
        answerOneBox.add(Box.createHorizontalStrut(100));
        answerOneBox.add(answerOne);
        answerOneBox.add(answerOneText);
        answerOneBox.add(Box.createHorizontalStrut(100));
        userBox.add(answerOneBox);
        userBox.add(Box.createVerticalStrut(20));

        //密保问题2
        Box problemTwoBox =Box.createHorizontalBox();
        JLabel problemTwo=new JLabel("问  题  二：");
        JTextField problemTwoText=new JTextField(user.getProblemTwo());
        problemTwoBox.add(Box.createHorizontalStrut(100));
        problemTwoBox.add(problemTwo);
        problemTwoBox.add(problemTwoText);
        problemTwoBox.add(Box.createHorizontalStrut(100));
        userBox.add(problemTwoBox);
        userBox.add(Box.createVerticalStrut(20));

        //密保答案2
        Box answerTwoBox =Box.createHorizontalBox();
        JLabel answerTwo=new JLabel("答        案：");
        JTextField answerTwoText = new JTextField(user.getAnswerTwo());
        answerTwoBox.add(Box.createHorizontalStrut(100));
        answerTwoBox.add(answerTwo);
        answerTwoBox.add(answerTwoText);
        answerTwoBox.add(Box.createHorizontalStrut(100));
        userBox.add(answerTwoBox);
        userBox.add(Box.createVerticalStrut(20));

        //密保问题3
        Box problemThreeBox =Box.createHorizontalBox();
        JLabel problemThree=new JLabel("问  题  三：");
        JTextField problemThreeText=new JTextField(user.getProblemThree());
        problemThreeBox.add(Box.createHorizontalStrut(100));
        problemThreeBox.add(problemThree);
        problemThreeBox.add(problemThreeText);
        problemThreeBox.add(Box.createHorizontalStrut(100));
        userBox.add(problemThreeBox);
        userBox.add(Box.createVerticalStrut(20));

        //密保答案3
        Box answerThreeBox =Box.createHorizontalBox();
        JLabel answerThree=new JLabel("答        案：");
        JTextField answerThreeText = new JTextField(user.getAnswerThree());
        answerThreeBox.add(Box.createHorizontalStrut(100));
        answerThreeBox.add(answerThree);
        answerThreeBox.add(answerThreeText);
        answerThreeBox.add(Box.createHorizontalStrut(100));
        userBox.add(answerThreeBox);
        userBox.add(Box.createVerticalStrut(20));

        //修改个人信息
        Box jBox =Box.createHorizontalBox();
        JButton jButton = new JButton("修改密码信息");
        jBox.add(jButton);
        userBox.add(jBox);
        userBox.add(Box.createVerticalStrut(20));
        jButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(userOldPasswordText.getText().equals(user.getUserPassword())){
                    User u = new User();
                    u.setUserID(user.getUserID());
                    u.setUserName(user.getUserName());
                    u.setUserPassword(newPasswordText.getText());
                    u.setUserGender(user.getUserGender());
                    u.setUserDate(user.getUserDate());
                    u.setProblemOne(problemOneText.getText());
                    u.setAnswerOne(answerOneText.getText());
                    u.setProblemTwo(problemTwoText.getText());
                    u.setAnswerTwo(answerTwoText.getText());
                    u.setProblemThree(problemThreeText.getText());
                    u.setAnswerThree(answerThreeText.getText());
                    if(!UserUtils.isJudgeNull(u)){
                        //更新用户信息
                        int result=JOptionPane.showConfirmDialog(null, "是否修改密码信息？", "确认对话框",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (result == JOptionPane.YES_OPTION){
                            UserUtils.updateUserAllDetailUtil(u);
                            JOptionPane.showMessageDialog(null,"修改密码信息成功！");
                            dispose();
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"密码信息填写不完善，请补充信息");
                    }

                }else{
                    JOptionPane.showMessageDialog(null,"输入旧密码有误，请重新输入！");
                    userOldPasswordText.setText("");
                }
            }
        });
        this.add(userBox);
        this.setResizable(false);
        this.setVisible(true);

    }
    public static void main(String args[]){
        User user = UserUtils.SelectUserByID("12345678901");
        new UserPasswordDetailDialog().init(user);
    }
}
