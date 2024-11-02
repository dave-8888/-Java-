package one.dao;

import one.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {

    //查询用户密码
    String selectUserPasswordById(String ID);
    //添加用户信息
    int insertUser(User user);
    //查询用户密保信息信息
    User selectUserDetailById(String ID);
    //修改用户密码
    void updateUserPassword(@Param("userID") String ID, @Param("userPassword") String password);
    //修改基本用户信息
    void updateUserAllDetail(User user);
    //查询用户所有信息
    User selectUserById(String ID);

}
