package org.example;

import one.dao.ScheduleDao;
import one.dao.UserDao;
import one.domain.Schedule;
import one.domain.User;
import one.util.MyBatisUtils;
import one.util.ScheduleUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Test//查询用户密码测试
    public void testSelectUserPasswordById() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserDao dao = sqlSession.getMapper(UserDao.class);
        String student = dao.selectUserPasswordById("15935721360");
        System.out.println("student=" + student);
        sqlSession.commit();
        sqlSession.close();
    }
    @Test //注册用户信息测试
    public void testInsertUserById(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserDao dao = sqlSession.getMapper(UserDao.class);
        User user = new User();
        user.setUserID("12345678901");
        user.setUserName("测试");
        user.setUserPassword("ceshi");
        user.setUserDate("2021-04-06");
        user.setUserGender(1);
        user.setProblemOne("1");
        user.setAnswerOne("1");
        user.setProblemTwo("2");
        user.setAnswerTwo("2");
        user.setProblemThree("3");
        user.setAnswerThree("3");
        dao.insertUser(user);
        sqlSession.commit();
        sqlSession.close();
    }
    @Test    //查询用户信息密保测试
    public void testSelectUserDetailById(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserDao dao = sqlSession.getMapper(UserDao.class);
        User user =dao.selectUserDetailById("12345678901");
        System.out.println(user);
        sqlSession.commit();
        sqlSession.close();
    }
    @Test  //用户更改密码测试
    public void testUpdateUserById(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserDao dao = sqlSession.getMapper(UserDao.class);
          String student = dao.selectUserPasswordById("12345678901");
        System.out.println("student="+student);
        dao.updateUserPassword("12345678901","1234789");
        student = dao.selectUserPasswordById("12345678901");
        System.out.println("student="+student);
        sqlSession.commit();
        sqlSession.close();
    }

@Test //查询日程信息
    public void testSelectSchedulesByIDAndByDate(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ScheduleDao dao=sqlSession.getMapper(ScheduleDao.class);
        /*ArrayList<Schedule> schedules;
        schedules=dao.selectSchedulesByIDAndByDate("12345678901","2021-04-06","2021-04-10");
        for(Schedule schedule:schedules){
            System.out.println(schedule.toString());
        }*/
    ArrayList<Schedule> list ;
    list= ScheduleUtils.selectSchedulesByIDAndByDate("12345678901","2021-05","2021-06");
    Object[][] tableData=new Object[list.size()][5];
    for(int i=0;i<list.size();i++){
        tableData[i][0]=list.get(i).getScheduleTitle();
        tableData[i][1]=list.get(i).getScheduleDetail();
        tableData[i][2]=list.get(i).getStartTime();
        tableData[i][3]=list.get(i).getEndTime();
        tableData[i][4]=list.get(i).getIsAlert();
    }
        sqlSession.commit();
        sqlSession.close();
}

    @Test //插入日程信息
    public void testInsertScheduleByID(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ScheduleDao dao=sqlSession.getMapper(ScheduleDao.class);
        Schedule schedule=new Schedule();
        schedule.setUserID("12345678901");
        schedule.setScheduleTitle("查询测试");
        schedule.setScheduleDetail("查询测试");
        schedule.setStartTime("2021-04-06");
        schedule.setEndTime("2021-04-10");
        schedule.setIsAlert(1);
        dao.insertScheduleByID(schedule);
        sqlSession.commit();
        sqlSession.close();
    }
    @Test  //修改日程信息
    public void testUpdateScheduleByID(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ScheduleDao dao=sqlSession.getMapper(ScheduleDao.class);
        Schedule schedule=new Schedule();
        //schedule.setScheduleID("5");
        schedule.setScheduleTitle("xiugai测试3");
        schedule.setScheduleDetail("xiugai测试3");
        schedule.setStartTime("2021-04-06");
        schedule.setEndTime("2021-04-10");
        schedule.setIsAlert(1);
        dao.updateScheduleByID(schedule);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test   //删除日程信息
    public void testDeleteScheduleByID(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ScheduleDao dao=sqlSession.getMapper(ScheduleDao.class);
        dao.deleteScheduleByID("10");
        sqlSession.commit();
        sqlSession.close();
    }
    @Test    //查询用户所有信息
    public void testSelectUserById(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserDao dao = sqlSession.getMapper(UserDao.class);
        User user =dao.selectUserById("12345678901");
        System.out.println(user);
        sqlSession.commit();
        sqlSession.close();
    }
    @Test//比较时间
    public void testCompareTime(){
        String d1="2021-04-10";
        String d2=null;
        System.out.println(ScheduleUtils.compareDateIsError(d1,d2));
    }
    @Test//测试通过ScheduleID查询日程信息
    public void testScheduleByID(){
        String a="14";
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ScheduleDao dao=sqlSession.getMapper(ScheduleDao.class);
        Schedule schedule=dao.selectScheduleById(a);
        System.out.println(schedule);
        sqlSession.commit();
        sqlSession.close();
    }
    @Test//测试模糊查询
    public void testLikeSerach(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ScheduleDao dao=sqlSession.getMapper(ScheduleDao.class);
        ArrayList<Schedule> schedules;
        schedules=dao.selectScheduleByIDAndByDateAndByTitle("12345678901","2021-04-16","2021-04-17","安");
        for(Schedule schedule:schedules){
            System.out.println(schedule.toString());
        }
        sqlSession.commit();
        sqlSession.close();
    }
    //查询最近100条用户日程信息
    @Test
    public void selectUserByIdOrderByDateTest(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ScheduleDao dao=sqlSession.getMapper(ScheduleDao.class);
        ArrayList<Schedule> schedules;
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        schedules=dao.selectUserByIdOrderByDate("12345678901",formatter.format(date));
        System.out.println(schedules);
    }
    @Test
    public  void palySounds() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        // 获取音频输入流
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/images/sounds/Windows Background.au"));
        // 获取音频编码对象
        AudioFormat audioFormat = audioInputStream.getFormat();
        // 设置数据输入
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat, AudioSystem.NOT_SPECIFIED);
        SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);sourceDataLine.open(audioFormat);sourceDataLine.start();
        /*
         * 从输入流中读取数据发送到混音器
         */
        int count;
        byte tempBuffer[] = new byte[1024];
        while ((count = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
            if (count > 0) {
                sourceDataLine.write(tempBuffer, 0, count);
            }
        }
        // 清空数据缓冲,并关闭输入
        sourceDataLine.drain();
        sourceDataLine.close();
        }
        public static class TestSync {
            public synchronized void m1() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ie) {

                }

            }
            public synchronized void m2() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ie) {

                }

            }

            public static void main(String[] args) throws InterruptedException {
                final TestSync t = new TestSync();

                Thread t1 = new Thread() { public void run() { t.m1(); } };

                Thread t2 = new Thread() { public void run() { t.m2(); } };

                t1.start();

                Thread.sleep(500);

                t2.start();

                Thread.sleep(500);

                System.out.println(t2.getState());

            }

        }

    public static void main(String[] args) throws Exception {

        Thread threadOne = new Thread(new Runnable() {
            public void run() {
                methodOne();
            }
        });

        Thread threadTwo = new Thread(new Runnable() {
            public void run() {
                methodTwo();
            }
        });

        // 执行线程
        threadOne.start();
        threadTwo.start();

        Thread.sleep(1000);
    }

    public static void methodOne() {
        System.out.println("Method one is running!");
    }

    public static void methodTwo() {
        System.out.println("Method two is running!");
    }













}
