package com.hypertars.neighborChat.dao;

import com.hypertars.neighborChat.model.UserBlock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserBlockDAO {

    List<UserBlock> getUserBlocksByBid(@Param("bid") int bid);

    List<UserBlock> getUserBlocksByUid(@Param("uid") int uid);

    /**
     * set all userBlock inactive (exit all blocks)
     * @param uid user id
     */
    void setAllUserBlocksInactive(@Param("uid") int uid);

    /**
     * notify new block member
     * @param uid current user id
     * @return List<UserBlock> UserBlock models
     */
    List<UserBlock> notifyNewBlockMember(@Param("uid") int uid);

    /**
     * add user to new userblock
     * @param userBlock userBlock model
     */
    void addUserBlock(UserBlock userBlock);

    int countUsersByBid(@Param("bid") int bid);
}
