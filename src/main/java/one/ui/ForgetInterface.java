package one.ui;

import one.component.BackGroundPanel;
import one.domain.ForgetDetail;
import one.domain.User;
import one.util.PathUtils;
import one.util.ScreenUtils;
import one.util.UserUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ForgetInterface {
    JFrame jf ;
    final int WIDTH = 1000;
    final int HEIGHT = 600;
    int num=3;
    public void init(User user) throws IOException {
        jf= new JFrame("欢迎**"+user.getUserID()+"**请输入密保信息");
        //设置窗口的属性
        jf.setBounds((ScreenUtils.getScreenWidth()-WIDTH)/2,(ScreenUtils.getScreenHeight()-HEIGHT)/2,WIDTH,HEIGHT);
        jf.setResizable(false);
        jf.setIconImage(ImageIO.read(new File(PathUtils.getRealPath("logo.png"))));

        BackGroundPanel bgPanel = new BackGroundPanel(ImageIO.read(new File(PathUtils.getRealPath("regist.jpg"))));
        bgPanel.setBounds(0,0,WIDTH,HEIGHT);


        Box vBox = Box.createVerticalBox();

        //问题1
        Box proBox1 = Box.createHorizontalBox();
        JLabel proLabel1 = new JLabel("问  题  一：");
        JTextField proField1 = new JTextField(user.getProblemOne());
        proField1.setEnabled(false);
        proBox1.add(proLabel1);
        proBox1.add(Box.createHorizontalStrut(20));
        proBox1.add(proField1);

        //回答1
        Box rBox1 = Box.createHorizontalBox();
        JLabel rLabel1 = new JLabel("  答    案：  ");
        JTextField rField1 = new JTextField();
        rBox1.add(rLabel1);
        rBox1.add(Box.createHorizontalStrut(20));
        rBox1.add(rField1);

        //问题2
        Box proBox2 = Box.createHorizontalBox();
        JLabel proLabel2 = new JLabel("问  题  二：");
        JTextField proField2 = new JTextField(user.getProblemTwo());
        proField2.setEnabled(false);
        proBox2.add(proLabel2);
        proBox2.add(Box.createHorizontalStrut(20));
        proBox2.add(proField2);

        //回答2
        Box rBox2 = Box.createHorizontalBox();
        JLabel rLabel2 = new JLabel("  答    案：  ");
        JTextField rField2 = new JTextField();
        rBox2.add(rLabel2);
        rBox2.add(Box.createHorizontalStrut(20));
        rBox2.add(rField2);
        //问题3
        Box proBox3 = Box.createHorizontalBox();
        JLabel proLabel3 = new JLabel("问  题  三：");
        JTextField proField3 = new JTextField(user.getProblemThree());
        proField3.setEnabled(false);
        proBox3.add(proLabel3);
        proBox3.add(Box.createHorizontalStrut(20));
        proBox3.add(proField3);

        //回答3
        Box rBox3 = Box.createHorizontalBox();
        JLabel rLabel3 = new JLabel("  答    案：  ");
        JTextField rField3 = new JTextField();
        rBox3.add(rLabel3);
        rBox3.add(Box.createHorizontalStrut(20));
        rBox3.add(rField3);

        //组装密码
        Box pBox1 = Box.createHorizontalBox();
        JLabel pLabel1 = new JLabel("  密    码：  ");
        JPasswordField pField1 = new JPasswordField(15);

        pBox1.add(pLabel1);
        pBox1.add(Box.createHorizontalStrut(20));
        pBox1.add(pField1);
        //确认密码
        Box pBox2 = Box.createHorizontalBox();
        JLabel pLabel2 = new JLabel("确认密码：");
        JPasswordField pField2 = new JPasswordField(15);

        pBox2.add(pLabel2);
        pBox2.add(Box.createHorizontalStrut(20));
        pBox2.add(pField2);

        //密保问题1显示
        //密保答案输入框
        //密保问题2显示
        //密保答案输入框
        //密保问题3显示
        //密保问题输入框

        //组装按钮
        Box btnBox = Box.createHorizontalBox();
        JButton changeBtn = new JButton("确认更改密码");
        JButton backBtn = new JButton("返回登录页面");
        btnBox.add(changeBtn);
        btnBox.add(Box.createHorizontalStrut(80));
        btnBox.add(backBtn);
        /*
        * 监听器
        * */
        //更改密码监听
        changeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ForgetDetail forgetDetail=new ForgetDetail();
                forgetDetail.setResult1(rField1.getText());
                forgetDetail.setResult2(rField2.getText());
                forgetDetail.setResult3(rField3.getText());
                forgetDetail.setPassword1(pField1.getText());
                forgetDetail.setPassword2(pField2.getText());
                if(UserUtils.ChangeComplete(forgetDetail)){
                    if(UserUtils.GuardRight(forgetDetail,user)){
                        if(!forgetDetail.getPassword1().equals(forgetDetail.getPassword2())){
                            JOptionPane.showMessageDialog(null,"密码输入不一致，请重新输入！");
                            pField1.setText("");
                            pField2.setText("");
                        }else{
                            //更新用户信息
                            UserUtils.UpdateUserPassword(user.getUserID(),forgetDetail.getPassword1());
                            JOptionPane.showMessageDialog(null,"密码已更新，请重新登录");
                            try {
                                new LoginInterface().init();
                                jf.dispose();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"密保信息填写有误！请重新输入！剩余"+--num+"次");
                        rField1.setText("");
                        rField2.setText("");
                        rField3.setText("");
                        if(num<=0){
                            //返回到登录页面即可
                            try {
                                new LoginInterface().init();
                                jf.dispose();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            num=3;
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"密保信息填写不完整，请继续填写！");
                }
            }
        });
        //返回登录界面监听
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //返回到登录页面即可
                try {
                    new LoginInterface().init();
                    jf.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        vBox.add(Box.createVerticalStrut(20));
        vBox.add(proBox1);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(rBox1);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(proBox2);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(rBox2);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(proBox3);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(rBox3);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(pBox1);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(pBox2);
        vBox.add(Box.createVerticalStrut(20));
        //vBox.add(cBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(btnBox);

        bgPanel.add(vBox);
        jf.add(bgPanel);
        jf.setVisible(true);
    }
}
