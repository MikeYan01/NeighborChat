package com.hypertars.neighborChat.dao;

import com.hypertars.neighborChat.model.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UsersDAO {

    /**
     * check whether uname is already exists
     *
     * @param uname user name
     * @return Integer (count uname)
     */
    int checkUName(@Param("uname") String uname);

    /**
     * select user by uname to check if uname is available
     * @param uname user name
     * @return user
     */
    Users getUserByUName(@Param("uname") String uname);

    /**
     * select by user id
     *
     * @param uid user id
     * @return user
     */
    Users getUserByUid(@Param("uid") Integer uid);

    /**
     * register user
     *
     * @param user user model
     */
    void userReg(Users user);

    /**
     * user auth with uname or email
     *
     * @param user user model
     * @return uid & password
     */
    Users userAuth(Users user);

    /**
     * restore password with uname and email
     *
     * @param user user model
     */
    void resetPasswd(Users user);

    /**
     * update an user info into db
     *
     * @param user user model
     */
    void updateInfo(Users user);

    /**
     * update lastLog
     */
    void updateLastLog(@Param("uid") Integer uid);

    /**
     * Select users profiles from current user's same building
     * @param uid current user id
     * @return List<Users> Users models
     */
    List<Users> userFromSameBuilding(@Param("uid") Integer uid);

    /**
     * Select users profiles from current user's same block
     * @param uid current user id
     * @return List<Users> Users models
     */
    List<Users> userFromSameBlock(@Param("uid") Integer uid);

    /**
     * Select users profiles from current user's same hood
     * @param uid current user id
     * @return List<Users> Users models
     */
    List<Users> userFromSameHood(@Param("uid") Integer uid);

}


