package com.hypertars.neighborChat.dao;

import com.hypertars.neighborChat.model.Friends;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FriendsDAO {

    /**
     * get all friends of the user
     * @param uid user id
     * @return List<Friends> friends models
     */
    List<Friends> getFriends(@Param("uid") int uid);

    /**
     * get all friends of the user from current block
     * @param uid user id
     * @return List<Friends> friends models
     */
    List<Friends> getFriendsFromBlock(@Param("uid") int uid);

    /**
     * get all friends of the user from current hood
     * @param uid user id
     * @return List<Friends> friends models
     */
    List<Friends> getFriendsFromHood(@Param("uid") int uid);

    /**
     * get all friends of the user from old relationship
     * @param uid user id
     * @return List<Friends> friends models
     */
    List<Friends> getFriendsFromOld(@Param("uid") int uid);

    /**
     * check whether uid A and uid B are already friends
     * @param friends friends
     * @return count
     */
    int checkFriends(Friends friends);

    /**
     * add new friend
     * @param friends friends
     */
    void addFriend (Friends friends);

    /**
     * delete friend
     * @param friends friends
     */
    void deleteFriend(Friends friends);
}

