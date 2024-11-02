package one.ui;

import one.component.BackGroundPanel;
import one.component.JDateChooser;
import one.domain.CheckCode;
import one.domain.User;
import one.util.CheckCodeUtil;
import one.util.PathUtils;
import one.util.ScreenUtils;
import one.util.UserUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class RegisterInterface {
    JFrame jf = new JFrame("注册");

    final int WIDTH = 600;
    final int HEIGHT = 800;
    CheckCode checkCode=new CheckCode();
    String checkString;
    public void init() throws IOException {
        //设置窗口的属性
        jf.setBounds((ScreenUtils.getScreenWidth()-WIDTH)/2,(ScreenUtils.getScreenHeight()-HEIGHT)/2,WIDTH,HEIGHT);
        jf.setResizable(false);
        jf.setIconImage(ImageIO.read(new File(PathUtils.getRealPath("logo.png"))));

        BackGroundPanel bgPanel = new BackGroundPanel(ImageIO.read(new File(PathUtils.getRealPath("regist.jpg"))));
        bgPanel.setBounds(0,0,WIDTH,HEIGHT);


        Box vBox = Box.createVerticalBox();

        //组装用户名
        Box uBox = Box.createHorizontalBox();
        JLabel uLabel = new JLabel(" 用   户   ID：");
        JTextField uField = new JTextField(15);
        //限制只能输入数字
        uField.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                String s = uField.getText();
                if(s.length() >= 11) {
                    e.consume();
                }else{
                    if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){

                    }else{
                        e.consume(); //关键，屏蔽掉非法输入
                    }
                }

            }
        });
        uBox.add(uLabel);
        uBox.add(Box.createHorizontalStrut(20));
        uBox.add(uField);
        //组装昵称
        Box nBox = Box.createHorizontalBox();
        JLabel nLabel =new JLabel("用 户 昵 称：");
        JTextField nFiled = new JTextField(15);
        //限制用户昵称最多20个字符
        nFiled.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                String s = nFiled.getText();
                if(s.length() >=20){
                    e.consume();
                }
            }
        });
        nBox.add(nLabel);
        nBox.add(Box.createHorizontalStrut(20));
        nBox.add(nFiled);
        //组装性别
        Box gBox = Box.createHorizontalBox();
        JLabel gLabel = new JLabel("  性       别：");
        JRadioButton maleBtn = new JRadioButton("男",true);
        JRadioButton femaleBtn = new JRadioButton("女",false);

        //实现单选的效果
        ButtonGroup bg = new ButtonGroup();
        bg.add(maleBtn);
        bg.add(femaleBtn);

        gBox.add(gLabel);
        gBox.add(Box.createHorizontalStrut(20));
        gBox.add(maleBtn);
        gBox.add(femaleBtn);
        gBox.add(Box.createHorizontalStrut(120));
        //组装年龄
        //？？？？？？？？？？？？？？年龄没有实现只输入数字功能！！！！！且每过一年，数据库年龄要加一！！
        Box aBox = Box.createHorizontalBox();
        JLabel aLabel =new JLabel("用 户 生 日：");
        JTextField aField = new JTextField(15);
        aField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JDateChooser gg = new JDateChooser();
                gg.showDateChooser();
                aField.setText(gg.getDateFormat("yyyy-MM-dd"));


            }
        });

        aBox.add(aLabel);
        aBox.add(Box.createHorizontalStrut(20));
        aBox.add(aField);
        //组装密码
        Box pBox1 = Box.createHorizontalBox();
        JLabel pLabel1 = new JLabel("用 户 密 码：");
        JPasswordField pField1 = new JPasswordField(15);
        pField1.setEchoChar('*');

        pBox1.add(pLabel1);
        pBox1.add(Box.createHorizontalStrut(20));
        pBox1.add(pField1);
        //组装密码
        Box pBox2 = Box.createHorizontalBox();
        JLabel pLabel2 = new JLabel("确 认 密 码：");
        JPasswordField pField2 = new JPasswordField(15);
        pField2.setEchoChar('*');

        pBox2.add(pLabel2);
        pBox2.add(Box.createHorizontalStrut(20));
        pBox2.add(pField2);

        //组装密保问题1
        Box proBox1 = Box.createHorizontalBox();
        JLabel proLabel1 = new JLabel("密保问题 1：");
        JTextField proField1 = new JTextField(20);

        proBox1.add(proLabel1);
        proBox1.add(Box.createHorizontalStrut(20));
        proBox1.add(proField1);
        //组装密保答案1
        Box rBox1 = Box.createHorizontalBox();
        JLabel rLabel1 = new JLabel("密保答案 1：");
        JTextField rField1 = new JTextField(20);

        rBox1.add(rLabel1);
        rBox1.add(Box.createHorizontalStrut(20));
        rBox1.add(rField1);
        //组装密保问题2
        Box proBox2 = Box.createHorizontalBox();
        JLabel proLabel2 = new JLabel("密保问题 2：");
        JTextField proField2 = new JTextField(20);

        proBox2.add(proLabel2);
        proBox2.add(Box.createHorizontalStrut(20));
        proBox2.add(proField2);
        //组装密保答案2
        Box rBox2 = Box.createHorizontalBox();
        JLabel rLabel2 = new JLabel("密保答案 2：");
        JTextField rField2 = new JTextField(20);

        rBox2.add(rLabel2);
        rBox2.add(Box.createHorizontalStrut(20));
        rBox2.add(rField2);

        //组装密保问题3
        Box proBox3 = Box.createHorizontalBox();
        JLabel proLabel3 = new JLabel("密保问题 3：");
        JTextField proField3 = new JTextField(20);

        proBox3.add(proLabel3);
        proBox3.add(Box.createHorizontalStrut(20));
        proBox3.add(proField3);

        //组装密保答案3
        Box rBox3 = Box.createHorizontalBox();
        JLabel rLabel3 = new JLabel("密保答案 3：");
        JTextField rField3 = new JTextField(20);

        rBox3.add(rLabel3);
        rBox3.add(Box.createHorizontalStrut(20));
        rBox3.add(rField3);

        //组装验证码
        Box cBox = Box.createHorizontalBox();
        JLabel cLabel = new JLabel("验证码：");
        JTextField cField = new JTextField(4);
        checkCode=new CheckCodeUtil().checkCodeImage();
        JLabel cImg = new JLabel(new ImageIcon(checkCode.getImage()));
        checkString=checkCode.getString();
        //给某个组件设置提示信息
        cImg.setToolTipText("点击刷新");
        cImg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                checkCode=new CheckCodeUtil().checkCodeImage();
                cImg.setIcon(new ImageIcon(checkCode.getImage()));
                checkString=checkCode.getString();
                cImg.updateUI();
            }
        });
        cBox.add(cLabel);
        cBox.add(Box.createHorizontalStrut(20));
        cBox.add(cField);
        cBox.add(cImg);

        //组装按钮
        Box btnBox = Box.createHorizontalBox();
        JButton registBtn = new JButton("注册");
        JButton backBtn = new JButton("返回登录页面");
        btnBox.add(registBtn);
        btnBox.add(Box.createHorizontalStrut(80));
        btnBox.add(backBtn);
        /*
        * 注册按钮监听
        * */
        registBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(pField1.getText().equals(pField2.getText())){
                    User user = new User();
                    user.setUserID(uField.getText());
                    user.setUserName(nFiled.getText());
                    user.setUserPassword(pField1.getText());

                    if(maleBtn.isSelected()){
                        user.setUserGender(1);
                    }else{
                        user.setUserGender(0);
                    }
                    user.setProblemOne(proField1.getText());
                    user.setAnswerOne(rField1.getText());
                    user.setProblemTwo(proField2.getText());
                    user.setAnswerTwo(rField2.getText());
                    user.setProblemThree(proField3.getText());
                    user.setAnswerThree(rField3.getText());
                    user.setUserDate(aField.getText());

                    /*
                    *
                    *
                    * 添加用户信息，没有判断用户ID是否存在，存在选择返回登录或者重新注册！
                    *
                    *
                    * */
                    if(!UserUtils.judgeUserExist(user)&& !UserUtils.isJudgeNull(user)){
                        if(cField.getText().equalsIgnoreCase(checkString)){
                            UserUtils.insertUserUtil(user);
                            JOptionPane.showMessageDialog(null,"用户注册成功，请返回登录！");
                            try {
                                new LoginInterface().init();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                            jf.dispose();
                        }else{
                            cField.setText(null);
                            JOptionPane.showMessageDialog(null,"验证码错误！！请重新输入！");
                        }


                    }else if(!UserUtils.isJudgeNull(user)){
                        uField.setText(null);
                        JOptionPane.showMessageDialog(null,"用户ID已存在，请重新输入用户ID或返回登录！！");

                    }else{
                        JOptionPane.showMessageDialog(null,"注册信息填写不完善，请继续填写！！");
                    }

                }else{
                    pField1.setText(null);
                    pField2.setText(null);
                    JOptionPane.showMessageDialog(null, "密码输入不一致请重新输入");
                }


            }
        });
        /*
        * 返回登录页面监听
        * */
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
        vBox.add(Box.createVerticalStrut(50));
        vBox.add(uBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(nBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(gBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(aBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(pBox1);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(pBox2);
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
        vBox.add(cBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(btnBox);

        bgPanel.add(vBox);
        jf.add(bgPanel);
        jf.setVisible(true);
    }
}
