package com.hypertars.neighborChat.dao;

import com.hypertars.neighborChat.model.BlockApplication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlockApplicationDAO {

    /**
     * get all applications belong to the Applicant
     * @param applicant applicant uid
     * @return List<BlockApplication> models
     */
    List<BlockApplication> getBlockApplicationByApplicant(@Param("applicant") int applicant);

    /**
     * add new block application
     * @param blockApplication blockApplication model
     */
    void addBlockApplication(BlockApplication blockApplication);

    /** Block member accepts application
     */
    void acceptBlockApplication(BlockApplication blockApplication);

    /** Block member rejects application
     */
    void rejectBlockApplication(BlockApplication blockApplication);

    /**
     * Notify recipient if new application in his/her block
     * @param uid recipient uid
     * @return List<BlockApplication> BlockApplications models
     */
    List<BlockApplication> notifyNewBlockApplication (@Param("uid") int uid);

    /**
     * If notified block application decision, delete it
     * @param blockApplication blockApplication model
     */
    void deleteBlockApplication(BlockApplication blockApplication);

    /**
     * check block application exists
     * @param blockApplication - applicant & bid
     * @return count
     */
    int checkBlockApplicationExist(BlockApplication blockApplication);

    /**
     * get exact block application info
     * @param blockApplication - applicant & bid
     * @return block application
     */
    BlockApplication getExactBlockApplication(BlockApplication blockApplication);
}
