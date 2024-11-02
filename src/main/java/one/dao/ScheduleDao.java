package one.dao;

import one.domain.Schedule;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface ScheduleDao {
    //查询用户日程信息
    /*某天*/
    /*某周*/
    /*某月*/
    /*某年*/
    ArrayList<Schedule> selectSchedulesByIDAndByDate(@Param("userID") String userID, @Param("startTime") String startTime, @Param("endTime") String endTime);
    Schedule selectScheduleById ( String scheduleID);
    //使用userID,scheduleID和时间模糊查询日程信息
    ArrayList<Schedule> selectScheduleByIDAndByDateAndByTitle(@Param("userID") String userID, @Param("startTime") String startTime, @Param("endTime") String endTime,@Param("title") String title);
    //添加日程信息
    /*
     * 用户ID：userID
     * 日程ID：scheduleID
     * 日程标题：scheduleTitle
     * 日程详情：scheduleDetail
     * 开始时间：年/月/日/小时/分钟  startTime
     * 结束时间：年/月/日/小时/分钟  endTime
     * 是否提醒：isAlert
     * */
    void insertScheduleByID(Schedule schedule);
    //修改日程信息
    /*
     * 用户ID
     * 日程ID
     * 日程标题
     * 日程详情
     * 开始时间
     * 结束时间
     * 是否提醒
     * */
    void updateScheduleByID(Schedule schedule);
    //删除日程信息
    void deleteScheduleByID(String scheduleID);
    //查询最近100条日程信息
    ArrayList<Schedule> selectUserByIdOrderByDate(@Param("userID") String userID,@Param("date") String date);
    //设置是否已完成
    void updateIsOkByID(@Param("isOk") int isOk,@Param("scheduleID") String scheduleID);
    //查询未完成的日程表信息
    ArrayList<Schedule> selectSchedulesIsOkByIDAndByDate(@Param("userID") String userID, @Param("startTime") String startTime, @Param("endTime") String endTime,@Param("isOk") int Ok);
    //模糊查询未完成的日程表信息
    ArrayList<Schedule> selectScheduleIsOkByIDAndByDateAndByTitle(@Param("userID") String userID, @Param("startTime") String startTime, @Param("endTime") String endTime,@Param("title") String title,@Param("isOk") int isOk);

}
