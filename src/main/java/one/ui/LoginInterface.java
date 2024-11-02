package one.ui;

import one.component.BackGroundPanel;
import one.domain.User;
import one.util.PathUtils;
import one.util.ScreenUtils;
import one.util.UserUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class LoginInterface {
    JFrame jf = new JFrame("日程表系统");

    final int WIDTH = 500;
    final int HEIGHT = 300;
    public void init() throws IOException {
        //设置窗口相关的属性
        jf.setBounds((ScreenUtils.getScreenWidth() - WIDTH) / 2, (ScreenUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        jf.setResizable(false);
        jf.setIconImage(ImageIO.read(new File(PathUtils.getRealPath("logo.png"))));

        //设置窗口的内容

        BackGroundPanel bgPanel = new BackGroundPanel(ImageIO.read(new File(PathUtils.getRealPath("library.jpg"))));
        bgPanel.setBounds(0,0,WIDTH,HEIGHT);
        //组装登录相关的元素
        Box vBox = Box.createVerticalBox();
        //组装用户名
        Box uBox = Box.createHorizontalBox();
        JLabel uLabel = new JLabel("用户ID：");
        JTextField uField = new JTextField(15);
        uBox.add(uLabel);
        uBox.add(Box.createHorizontalStrut(20));
        uBox.add(uField);
        uBox.add(Box.createHorizontalStrut(65));

        //组装密码
        Box pBox = Box.createHorizontalBox();
        JLabel pLabel = new JLabel("密    码：");
        JPasswordField pField = new JPasswordField(15);
        pField.setEchoChar('*');
        //组装忘记密码按钮
        JLabel forgetLabel = new JLabel("忘记密码？");

        pBox.add(pLabel);
        pBox.add(Box.createHorizontalStrut(20));
        pBox.add(pField);
        pBox.add(forgetLabel);

        //组装按钮
        Box btnBox = Box.createHorizontalBox();
        JButton loginBtn = new JButton("登录");
        JButton registBtn = new JButton("注册");
        btnBox.add(loginBtn);
        btnBox.add(Box.createHorizontalStrut(100));
        btnBox.add(registBtn);
        vBox.add(Box.createVerticalStrut(50));
        vBox.add(uBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(pBox);
        vBox.add(Box.createVerticalStrut(40));
        vBox.add(btnBox);
        /*
        * 访问忘记密码接口监听
        */
        forgetLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //处理鼠标点击
                //跳转到更改密码界面
                new UserUtils().UserIDInput(jf);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                forgetLabel.setForeground(new Color(139,0,0));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                forgetLabel.setForeground(Color.red);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                forgetLabel.setForeground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                forgetLabel.setForeground(Color.black);
            }
        });
        /*
        * 访问登录接口监听
        */
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user =new User();
                user.setUserID(uField.getText());
                if(UserUtils.judgeUserExist(user)){
                    if(UserUtils.isLogin(uField.getText(),pField.getText())){
                        //跳转到主页面
                        user=UserUtils.SelectUserByID(user.getUserID());
                        try {
                            new MainInterface().init(user);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        //登录页面消失
                        jf.dispose();
                    }else{
                        JOptionPane.showMessageDialog(null,"密码错误，请重新输入！！");
                        pField.setText(null);
                    }

                }else{
                    JOptionPane.showMessageDialog(null,"用户ID不存在，请重新输入或注册！");
                    uField.setText(null);
                    pField.setText(null);
                }

            }
        });
        /*
        *跳转到注册页面监听
        */
        registBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //跳转到注册页面
                try {
                    new RegisterInterface().init();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                //当前界面消失
                jf.dispose();
            }
        });
        bgPanel.add(vBox);
        jf.add(bgPanel);
        jf.setVisible(true);
            }
            public static void main(String[] args) {
                try {
                    new LoginInterface().init();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
