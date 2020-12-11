package com.hypertars.neighborChat.service.Relationship;

import com.hypertars.neighborChat.model.FriendApplication;
import com.hypertars.neighborChat.model.Users;

import java.util.List;

public interface RelationshipService {

    /** friend application*/
    boolean checkFriendApplicationExist (int applicant, int recipient);
    boolean addFriendApplication(int applicant, int recipient, String txt);
    boolean acceptFriendApplication (int applicant, int recipient);
    boolean rejectFriendApplication (int applicant, int recipient);
    boolean deleteFriendApplication (int applicant, int recipient);

    List<FriendApplication> getFriendApplicationByApplicant (int applicant);
    List<FriendApplication> getFriendApplicationByRecipient (int recipient);

    /** friend */
    boolean checkFriendship (int uidA, int uidB);
    boolean deleteFriend (int uidA, int uidB);
    boolean addFriends (int uidA, int uidB);

    /** neighbor */
    boolean checkNeighborExist (int uidA, int uidB);
    boolean addNeighbor (int uidA, int uidB);
    boolean deleteNeighbor (int uidA, int uidB);
    boolean deleteAllNeighbors (int uid);

    /** notification */
    List<FriendApplication> notifyNewFriendApplicationToRecipient (int recipient);
    List<FriendApplication> notifyNewFriendApplicationMadeToApplicant (int applicant);
    List<FriendApplication> notifyPassedFriendApplicationFromApplicant (int applicant);
    List<FriendApplication> notifyFailedFriendApplicationFromApplicant (int applicant);

    /** basic loads */
    List<Users> getUsersByBid (int bid);
    List<Users> getUsersInSameBuildingByUid (int uid);
    List<Users> getUsersInSameBlockByUid (int uid);
    List<Users> getUsersInSameHoodByUid (int uid);

    List<Users> getFriendsByUid (int uid);
    List<Users> getFriendsFromBlockByUid (int uid);
    List<Users> getFriendsFromHoodByUid (int uid);
    List<Users> getFriendsFromOldByUid (int uid);

    List<Users> getNeighborsByUid (int uid);
    List<Users> getNeighborsFromBlockByUid (int uid);
    List<Users> getNeighborsFromHoodByUid (int uid);

    List<Users> getStrangersFromSameBuilding (int uid);
    List<Users> getStrangersFromSameBlock (int uid);
    List<Users> getStrangersFromSameHood (int uid);
}
