package com.hypertars.neighborChat.dao;

import com.hypertars.neighborChat.model.FriendApplication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FriendApplicationDAO {

    /**
     * get all friend applications belong to the applicant
     * @param applicant uid applicant
     * @return List<FriendApplication> models
     */
    List<FriendApplication> getFriendApplicationByApplicant(@Param("applicant") int applicant);

    /**
     * get all friend applications belong to the recipient
     * @param recipient uid
     * @return List<FriendApplication> models
     */
    List<FriendApplication> getFriendApplicationByRecipient(@Param("recipient") int recipient);

    /**
     * update timestamp of the friend application (if exists)
     */
    void updateFriendApplication(FriendApplication friendApplication);

    /**
     * accept friend application (mark decision as True)
     */
    void acceptFriendApplication(FriendApplication friendApplication);

    /**
     * reject friend application (mark decision as False)
     */
    void rejectFriendApplication(FriendApplication friendApplication);

    /**
     * Notify applicant if friend applications made
     * @param applicant uid
     * @return List<FriendApplication> friendApplications models
     */
    List<FriendApplication> notifyFriendApplicationMade (@Param("applicant") int applicant);

    /**
     * notify recipient if new friend application
     * @param recipient recipient uid
     */
    List<FriendApplication> notifyNewFriendApplication(@Param("recipient") int recipient);

    /**
     * add new friend application (if not exists)
     * @param friendApplication friend application
     */
    void addFriendApplication(FriendApplication friendApplication);

    /**
     * If notified friend application decision, delete it
     * @param friendApplication friendApplication model
     */
    void deleteFriendApplication(FriendApplication friendApplication);

    /**
     * check whether friend application already exists
     * @param friendApplication fa
     * @return count
     */
    int checkFriendApplication(FriendApplication friendApplication);
}
