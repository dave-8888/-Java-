package one.util;

import one.dao.ScheduleDao;
import one.domain.Schedule;
import org.apache.ibatis.session.SqlSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ScheduleUtils {
    //查询日程表信息
    public  static ArrayList<Schedule> selectSchedulesByIDAndByDate(String userID, String startTime, String endTime){
        ArrayList<Schedule> schedules;
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ScheduleDao dao=sqlSession.getMapper(ScheduleDao.class);
        schedules=dao.selectSchedulesByIDAndByDate(userID,startTime,endTime);
        sqlSession.commit();
        sqlSession.close();
        return schedules;
    }
    //通过日程表ID查询日程信息
    public static Schedule selectScheduleById(String scheduleID){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ScheduleDao dao=sqlSession.getMapper(ScheduleDao.class);
        Schedule schedule=dao.selectScheduleById(scheduleID);
        sqlSession.close();
        return schedule;

    }
    //添加日程信息
    public static void insertScheduleUtil(Schedule schedule){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ScheduleDao dao=sqlSession.getMapper(ScheduleDao.class);
        dao.insertScheduleByID(schedule);
        sqlSession.commit();
        sqlSession.close();
    }
    //修改日程信息
    public static void UpdateScheduleUtil(Schedule schedule){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ScheduleDao dao=sqlSession.getMapper(ScheduleDao.class);
        dao.updateScheduleByID(schedule);
        sqlSession.commit();
        sqlSession.close();
    }
    //删除日程信息
    public static void deleteScheduleUtil(String scheduleID){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ScheduleDao dao=sqlSession.getMapper(ScheduleDao.class);
        dao.deleteScheduleByID(scheduleID);
        sqlSession.commit();
        sqlSession.close();
    }
    //判断添加信息是否完整
    public static Boolean completeScheduleUtil(Schedule schedule){
        if(schedule.getScheduleTitle().length()>12
                ||schedule.getScheduleTitle()==null||schedule.getScheduleTitle().equals("")){
            return false;
        }else{
            return true;
        }
    }
    //判断时间大小
    public static Boolean compareDateIsError(String startTime,String endTime)  {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if(endTime==null||startTime.equals(endTime)){
            return false;
        }else{
            Date dStartTime = null;
            try {
                dStartTime = df.parse(startTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date dEndTime= null;
            try {
                dEndTime = df.parse(endTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(dEndTime.before(dStartTime)){
                return true;
            }else{
                return false;
            }
        }
    }
    //判断时间大小
    public static Boolean compareDateIsError2(String startTime,String endTime)  {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if(endTime==null){
            return false;
        }else{
            Date dStartTime = null;
            try {
                dStartTime = df.parse(startTime);
            } catch (ParseException e) {
                return false;
            }
            Date dEndTime= null;
            try {
                dEndTime = df.parse(endTime);
            } catch (ParseException e) {
                return false;
            }
            if(dStartTime.before(dEndTime)){
                return true;
            }else{
                return false;
            }
        }
    }
    //模糊查询
    public static ArrayList<Schedule> selectScheduleByIDAndByDateAndByTitleUtil(String userID,String startTime,String endTime,String title){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ScheduleDao dao=sqlSession.getMapper(ScheduleDao.class);
        ArrayList<Schedule> schedules;
        schedules=dao.selectScheduleByIDAndByDateAndByTitle(userID,startTime,endTime,title);
        sqlSession.commit();
        sqlSession.close();
        return schedules;
    }
    //查询最近100条用户日程信息
    public static ArrayList<Schedule> selectUserByIdOrderByDateUtil(String userID,String date){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ScheduleDao dao=sqlSession.getMapper(ScheduleDao.class);
        ArrayList<Schedule> schedules;
        schedules=dao.selectUserByIdOrderByDate(userID,date);
        sqlSession.close();
        return schedules;
    }
    //设置是否已完成
    public static void updateIsOkByIDUtil(int isOk,String scheduleID){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ScheduleDao dao=sqlSession.getMapper(ScheduleDao.class);
        dao.updateIsOkByID(isOk,scheduleID);
        sqlSession.commit();
        sqlSession.close();
    }
    //查询未完成的日程表信息
    public  static ArrayList<Schedule> selectSchedulesIsOkByIDAndByDate(String userID, String startTime, String endTime,int isOk){
        ArrayList<Schedule> schedules;
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ScheduleDao dao=sqlSession.getMapper(ScheduleDao.class);
        schedules=dao.selectSchedulesIsOkByIDAndByDate(userID,startTime,endTime,isOk);
        sqlSession.commit();
        sqlSession.close();
        return schedules;
    }
    //模糊查询未完成日程表信息
    public static ArrayList<Schedule> selectScheduleIsOkByIDAndByDateAndByTitleUtil(String userID,String startTime,String endTime,String title,int isOk){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        ScheduleDao dao=sqlSession.getMapper(ScheduleDao.class);
        ArrayList<Schedule> schedules;
        schedules=dao.selectScheduleIsOkByIDAndByDateAndByTitle(userID,startTime,endTime,title,isOk);
        sqlSession.commit();
        sqlSession.close();
        return schedules;
    }
}
