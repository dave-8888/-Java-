package one.util;

import one.dao.UserDao;
import one.domain.ForgetDetail;
import one.domain.User;
import one.ui.ForgetInterface;
import org.apache.ibatis.session.SqlSession;

import javax.swing.*;
import java.io.IOException;

public class UserUtils {
    //添加用户信息到user数据库
    public static void insertUserUtil(User user){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserDao dao =sqlSession.getMapper(UserDao.class);
        dao.insertUser(user);
        sqlSession.commit();
        sqlSession.close();

    }
    //判断用户是否已存在
    public static Boolean judgeUserExist(User user){
        SqlSession sqlSession =MyBatisUtils.getSqlSession();
        UserDao dao =sqlSession.getMapper(UserDao.class);
        String s =dao.selectUserPasswordById(user.getUserID());
        if(s==null||s.equals("")){
            sqlSession.close();
            return false;
        }else{
            sqlSession.close();
            return true;
        }

    }
    //判断用户信息填写是否完整
    public static Boolean isJudgeNull(User user){
        if(user.getUserID()==null||user.getUserID().equals("")
                ||user.getUserDate()==null||user.getUserDate().equals("")
                ||user.getUserPassword()==null||user.getUserPassword().equals("")
                ||user.getUserName()==null||user.getUserName().equals("")
                ||user.getProblemOne()==null||user.getProblemOne().equals("")
                ||user.getProblemTwo()==null||user.getProblemTwo().equals("")
                ||user.getProblemThree()==null||user.getProblemThree().equals("")
                ||user.getAnswerOne()==null||user.getAnswerOne().equals("")
                ||user.getAnswerTwo()==null||user.getAnswerTwo().equals("")
                ||user.getAnswerThree()==null||user.getAnswerThree().equals("")){
            return true;
        }else{
            return false;
        }
    }
    //判断用户名是否存在且与对应密码是否一致
    public static Boolean isLogin(String userID,String userPassword){
        SqlSession sqlSession =MyBatisUtils.getSqlSession();
        UserDao dao =sqlSession.getMapper(UserDao.class);
        String password =dao.selectUserPasswordById(userID);
        sqlSession.close();
        if(password.equals(userPassword)){
            return true;
        }else{
            return false;
        }
    }
    //输入用户ID，如果存在返回更改密码界面，否则提示重新输入
    public static void UserIDInput(JFrame jf){
        String result = JOptionPane.showInputDialog(jf, "请填写您的用户ID：", "用户ID", JOptionPane.INFORMATION_MESSAGE);
        User user =new User();
        user.setUserID(result);
        if(judgeUserExist(user)){
            SqlSession sqlSession = MyBatisUtils.getSqlSession();
            UserDao dao =sqlSession.getMapper(UserDao.class);
            try {
                new ForgetInterface().init(dao.selectUserDetailById(result));
                sqlSession.commit();
                sqlSession.close();
                //登录页面消失
                jf.dispose();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }else{
            JOptionPane.showMessageDialog(null,"用户名不存在！");
        }
    }
    //判断更改密码信息是否完整
    public static Boolean ChangeComplete(ForgetDetail forgetDetail){
        if(forgetDetail.getResult1()==null||forgetDetail.getResult1().equals("")
                ||forgetDetail.getResult2()==null||forgetDetail.getResult2().equals("")
                ||forgetDetail.getResult3()==null||forgetDetail.getResult3().equals("")
                ||forgetDetail.getPassword1()==null||forgetDetail.getPassword1().equals("")
                ||forgetDetail.getPassword2()==null||forgetDetail.getPassword2().equals("")){
            return false;
        }else{
            return true;
        }
    }
    //判断密保信息是否正确
    public static Boolean GuardRight(ForgetDetail forgetDetail,User user){

        if(forgetDetail.getResult1().equals(user.getAnswerOne())
                &&forgetDetail.getResult2().equals(user.getAnswerTwo())
                &&forgetDetail.getResult3().equals(user.getAnswerThree())){
            return true;
        }else{
            return false;
        }
    }
    //更新用户密码
    public static void UpdateUserPassword(String ID,String Password){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserDao dao =sqlSession.getMapper(UserDao.class);
        dao.updateUserPassword(ID,Password);
        sqlSession.commit();
        sqlSession.close();
    }
    //查询用户所有信息
    public static User SelectUserByID(String ID){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserDao dao = sqlSession.getMapper(UserDao.class);
        User user =dao.selectUserById(ID);
        sqlSession.commit();
        sqlSession.close();
        return user;
    }
    //修改用户所有信息
    public static void updateUserAllDetailUtil(User user){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserDao dao = sqlSession.getMapper(UserDao.class);
        dao.updateUserAllDetail(user);
        sqlSession.commit();
        sqlSession.close();
    }

}
