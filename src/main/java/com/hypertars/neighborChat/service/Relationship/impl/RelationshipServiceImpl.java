package com.hypertars.neighborChat.service.Relationship.impl;

import com.hypertars.neighborChat.dao.*;
import com.hypertars.neighborChat.model.*;
import com.hypertars.neighborChat.service.Relationship.RelationshipService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RelationshipServiceImpl implements RelationshipService {

    @Resource
    private BlockApplicationDAO blockApplicationDAO;

    @Resource
    private BlocksDAO blocksDAO;

    @Resource
    private FriendApplicationDAO friendApplicationDAO;

    @Resource
    private FriendsDAO friendsDAO;

    @Resource
    private HoodsDAO hoodsDAO;

    @Resource
    private MailBoxDAO mailBoxDAO;

    @Resource
    private MessageDAO messageDAO;

    @Resource
    private NeighborsDAO neighborsDAO;

    @Resource
    private RecipientDAO recipientDAO;

    @Resource
    private ReplyDAO replyDAO;

    @Resource
    private UserBlockDAO userBlockDAO;

    @Resource
    private UsersDAO usersDAO;

    /**
     * check whether friendship already exists
     * @param uidA uidA
     * @param uidB uidB
     * @return true or false
     */
    @Override
    public boolean checkFriendship(int uidA, int uidB) {
        Friends friends = new Friends();
        friends.setUidA(uidA);
        friends.setUidB(uidB);
        return friendsDAO.checkFriends(friends) > 0;
    }

    /**
     * check whether friend application exists
     * @param applicant user id
     * @param recipient user id
     * @return true or false
     */
    @Override
    public boolean checkFriendApplicationExist(int applicant, int recipient) {
        FriendApplication fa = new FriendApplication();
        fa.setApplicant(applicant);
        fa.setRecipient(recipient);
        int count = friendApplicationDAO.checkFriendApplication(fa);
        return count > 0;
    }

    /**
     * add friend application, check exists before adding, if exists, update timestamp
     * @param applicant user id
     * @param recipient user id
     * @param txt text
     * @return true or false
     */
    @Override
    public boolean addFriendApplication(int applicant, int recipient, String txt) {
        FriendApplication fa = new FriendApplication();
        fa.setApplicant(applicant);
        fa.setRecipient(recipient);
        fa.setTxt(txt);
        if (checkFriendApplicationExist(applicant, recipient)) {
            friendApplicationDAO.updateFriendApplication(fa);
            return false;
        }
        friendApplicationDAO.addFriendApplication(fa);
        return true;
    }

    /**
     * accept friend application
     * @param applicant applicant uid
     * @param recipient recipient uid
     * @return true or false
     */
    @Override
    public boolean acceptFriendApplication(int applicant, int recipient) {
        FriendApplication fa = new FriendApplication();
        fa.setApplicant(applicant);
        fa.setRecipient(recipient);
        friendApplicationDAO.acceptFriendApplication(fa);
        return true;
    }

    /**
     * reject friend application
     * @param applicant applicant uid
     * @param recipient recipient uid
     * @return true or false
     */
    @Override
    public boolean rejectFriendApplication(int applicant, int recipient) {
        FriendApplication fa = new FriendApplication();
        fa.setApplicant(applicant);
        fa.setRecipient(recipient);
        friendApplicationDAO.rejectFriendApplication(fa);
        return true;
    }

    /**
     * delete friend application
     * @param applicant uid
     * @param recipient uid
     * @return true or false
     */
    @Override
    public boolean deleteFriendApplication(int applicant, int recipient) {
        FriendApplication fa = new FriendApplication();
        fa.setApplicant(applicant);
        fa.setRecipient(recipient);
        if (friendApplicationDAO.checkFriendApplication(fa) == 0)
            return true;
        friendApplicationDAO.deleteFriendApplication(fa);
        return true;
    }

    /**
     * get friend applications by applicant
     * @param applicant uid
     * @return list of friend applications
     */
    @Override
    public List<FriendApplication> getFriendApplicationByApplicant(int applicant) {
        return friendApplicationDAO.getFriendApplicationByApplicant(applicant);
    }

    /**
     * get friend applications by recipient
     * @param recipient uid
     * @return list of applications
     */
    @Override
    public List<FriendApplication> getFriendApplicationByRecipient(int recipient) {
        return friendApplicationDAO.getFriendApplicationByRecipient(recipient);
    }

    /**
     * delete friendship
     * @param uidA uid
     * @param uidB uid
     * @return true or false
     */
    @Override
    public boolean deleteFriend(int uidA, int uidB) {
        if (!checkFriendship(uidA, uidB)) return true;
        Friends friends = new Friends();
        friends.setUidA(uidA);
        friends.setUidB(uidB);
        friendsDAO.deleteFriend(friends);
        return true;
    }

    /**
     * add friendship
     * @param uidA uidA
     * @param uidB uidB
     * @return true or false
     */
    @Override
    public boolean addFriends(int uidA, int uidB) {
        if (checkFriendship(uidA, uidB)) return true;
        Friends friends = new Friends();
        int uidMin = Math.min(uidA, uidB);
        int uidMax = Math.max(uidA, uidB);
        friends.setUidA(uidMin);
        friends.setUidB(uidMax);
        friendsDAO.addFriend(friends);
        return true;
    }

    /**
     * check whether already neighbor
     * @param uidA uid
     * @param uidB uid
     * @return true or false
     */
    @Override
    public boolean checkNeighborExist(int uidA, int uidB) {
        Neighbors neighbors = new Neighbors();
        neighbors.setUidA(uidA);
        neighbors.setUidB(uidB);
        return neighborsDAO.checkNeighbor(neighbors) > 0;
    }

    /**
     * add new neighbors, check before adding
     * @param uidA uid
     * @param uidB uid
     * @return true or false
     */
    @Override
    public boolean addNeighbor(int uidA, int uidB) {
        if (checkNeighborExist(uidA, uidB)) {
            return true;
        }
        Neighbors neighbors = new Neighbors();
        neighbors.setUidA(uidA);
        neighbors.setUidB(uidB);
        neighborsDAO.addNeighbor(neighbors);
        return true;
    }

    /**
     * delete neighbor
     * @param uidA uid
     * @param uidB uid
     * @return true or false
     */
    @Override
    public boolean deleteNeighbor(int uidA, int uidB) {
        if (!checkNeighborExist(uidA, uidB)) {
            return true;
        }
        Neighbors neighbors = new Neighbors();
        neighbors.setUidA(uidA);
        neighbors.setUidB(uidB);
        neighborsDAO.deleteNeighbor(neighbors);
        return true;
    }

    /**
     * delete all neighbors belong to uid
     * @param uid user id
     * @return true or false
     */
    @Override
    public boolean deleteAllNeighbors(int uid) {
        neighborsDAO.deleteAllNeighbors(uid);
        return true;
    }

    /**
     * notify new friend application to recipient since lastLog
     * @param recipient uid
     * @return list of friend applications
     */
    @Override
    public List<FriendApplication> notifyNewFriendApplicationToRecipient(int recipient) {
        return friendApplicationDAO.notifyNewFriendApplication(recipient);
    }

    /**
     * notify new friend application made to applicant since lastLog
     * @param applicant uid
     * @return list of friend applications
     */
    @Override
    public List<FriendApplication> notifyNewFriendApplicationMadeToApplicant(int applicant) {
        return friendApplicationDAO.notifyFriendApplicationMade(applicant);
    }

    /**
     * notify new friend application accepted to applicant since lastLog
     * @param applicant uid
     * @return list of friend applications
     */
    @Override
    public List<FriendApplication> notifyPassedFriendApplicationFromApplicant(int applicant) {
        List<FriendApplication> passed = new ArrayList<>();
        List<FriendApplication> newMade = notifyNewFriendApplicationMadeToApplicant(applicant);
        for (FriendApplication fa: newMade) {
            if (fa.getDecision() == 1)
                passed.add(fa);
        }
        return passed;
    }

    /**
     * notify new friend application rejected to applicant since last log
     * @param applicant uid
     * @return list of friend applications
     */
    @Override
    public List<FriendApplication> notifyFailedFriendApplicationFromApplicant(int applicant) {
        List<FriendApplication> rejected = new ArrayList<>();
        List<FriendApplication> newMade = notifyNewFriendApplicationMadeToApplicant(applicant);
        for (FriendApplication fa: newMade) {
            if (fa.getDecision() == 0)
                rejected.add(fa);
        }
        return rejected;
    }

    /**
     * get users by bid (in user blocks, all active members)
     * @param bid bid
     * @return list of users
     */
    @Override
    public List<Users> getUsersByBid(int bid) {
        List<Users> usersList = new ArrayList<>();
        List<UserBlock> ubs = userBlockDAO.getUserBlocksByBid(bid);
        for (UserBlock ub : ubs) {
            int tempUid = ub.getUid();
            Users user = usersDAO.getUserByUid(tempUid);
            usersList.add(user);
        }
        return usersList;
    }

    /**
     * users living in same building as uid
     * @param uid uid
     * @return list of users
     */
    @Override
    public List<Users> getUsersInSameBuildingByUid(int uid) {
        return usersDAO.userFromSameBuilding(uid);
    }

    /**
     * get all users info in the same block with uid
     * @param uid user id
     * @return list of users
     */
    @Override
    public List<Users> getUsersInSameBlockByUid(int uid) {
        int bid = 0;
        List<UserBlock> ubs = userBlockDAO.getUserBlocksByUid(uid);
        for (UserBlock ub : ubs)
            if (ub.getStatus())
                bid = ub.getBid();
        if (bid == 0) return null;
        return getUsersByBid(bid);
    }

    /**
     * get all users info in the same hood with uid
     * @param uid user id
     * @return list of users
     */
    @Override
    public List<Users> getUsersInSameHoodByUid(int uid) {
        List<Users> usersList = new ArrayList<>();
        Hoods hood = hoodsDAO.getHoodByUid(uid);
        List<Blocks> blocksList = blocksDAO.getBlocksByHid(hood.getHid());
        for (Blocks block : blocksList) {
            List<Users> tempList = getUsersByBid(block.getBid());
            usersList.addAll(tempList);
        }
        return usersList;
    }

    /**
     * get friend info by uid
     * @param uid uid
     * @return user list
     */
    @Override
    public List<Users> getFriendsByUid(int uid) {
        List<Users> friends = new ArrayList<>();
        List<Friends> friendship = friendsDAO.getFriends(uid);
        for (Friends f : friendship) {
            int friendID;
            if (f.getUidA() == uid)
                friendID = f.getUidB();
            else
                friendID = f.getUidA();
            friends.add(usersDAO.getUserByUid(friendID));
        }
        return friends;
    }

    /**
     * Friends from same block
     * @param uid uid
     * @return list of users
     */
    @Override
    public List<Users> getFriendsFromBlockByUid(int uid) {
        List<Users> friends = new ArrayList<>();
        List<Friends> friendship = friendsDAO.getFriendsFromBlock(uid);
        for (Friends f : friendship) {
            int friendID;
            if (f.getUidA() == uid)
                friendID = f.getUidB();
            else
                friendID = f.getUidA();
            friends.add(usersDAO.getUserByUid(friendID));
        }
        return friends;
    }

    /**
     * Friends from same hood
     * @param uid uid
     * @return list of users
     */
    @Override
    public List<Users> getFriendsFromHoodByUid(int uid) {
        List<Users> friends = new ArrayList<>();
        List<Friends> friendship = friendsDAO.getFriendsFromHood(uid);
        for (Friends f : friendship) {
            int friendID;
            if (f.getUidA() == uid)
                friendID = f.getUidB();
            else
                friendID = f.getUidA();
            friends.add(usersDAO.getUserByUid(friendID));
        }
        return friends;
    }

    /**
     * Friends from old block
     * @param uid uid
     * @return list of users
     */
    @Override
    public List<Users> getFriendsFromOldByUid(int uid) {
        List<Users> friends = new ArrayList<>();
        List<Friends> friendship = friendsDAO.getFriendsFromOld(uid);
        for (Friends f : friendship) {
            int friendID;
            if (f.getUidA() == uid)
                friendID = f.getUidB();
            else
                friendID = f.getUidA();
            friends.add(usersDAO.getUserByUid(friendID));
        }
        return friends;
    }

    /**
     * neighbors
     * @param uid uid
     * @return list of users
     */
    @Override
    public List<Users> getNeighborsByUid(int uid) {
        List<Users> neighbors = new ArrayList<>();
        List<Neighbors> neighborShip = neighborsDAO.getNeighbors(uid);
        for (Neighbors n : neighborShip) {
            neighbors.add(usersDAO.getUserByUid(n.getUidB()));
        }
        return neighbors;
    }

    /**
     * neighbors from same block
     * @param uid uid
     * @return list of users
     */
    @Override
    public List<Users> getNeighborsFromBlockByUid(int uid) {
        List<Users> neighbors = new ArrayList<>();
        List<Neighbors> neighborShip = neighborsDAO.getNeighborsFromBlock(uid);
        for (Neighbors n : neighborShip) {
            neighbors.add(usersDAO.getUserByUid(n.getUidB()));
        }
        return neighbors;
    }

    /**
     * neighbors from same hood
     * @param uid uid
     * @return list of users
     */
    @Override
    public List<Users> getNeighborsFromHoodByUid(int uid) {
        List<Users> neighbors = new ArrayList<>();
        List<Neighbors> neighborShip = neighborsDAO.getNeighborsFromHood(uid);
        for (Neighbors n : neighborShip) {
            neighbors.add(usersDAO.getUserByUid(n.getUidB()));
        }
        return neighbors;
    }

    /**
     * strangers from same building
     * @param uid uid
     * @return list of users
     */
    @Override
    public List<Users> getStrangersFromSameBuilding(int uid) {
        List<Users> stranger = new ArrayList<>();
        List<Users> sameBuilding = usersDAO.userFromSameBuilding(uid);
        List<Users> friends = getFriendsByUid(uid);
        List<Users> neighbors = getNeighborsByUid(uid);
        for (Users user : sameBuilding) {
            if (!friends.contains(user) && !neighbors.contains(user))
                stranger.add(user);
        }
        return stranger;
    }

    /**
     * strangers from same block
     * @param uid uid
     * @return list of users
     */
    @Override
    public List<Users> getStrangersFromSameBlock(int uid) {
        List<Users> stranger = new ArrayList<>();
        List<Users> sameBlock = usersDAO.userFromSameBlock(uid);
        List<Users> friends = getFriendsByUid(uid);
        List<Users> neighbors = getNeighborsByUid(uid);
        for (Users user : sameBlock) {
            if (!friends.contains(user) && !neighbors.contains(user))
                stranger.add(user);
        }
        return stranger;
    }

    /**
     * strangers from same hood
     * @param uid uid
     * @return list of users
     */
    @Override
    public List<Users> getStrangersFromSameHood(int uid) {
        List<Users> stranger = new ArrayList<>();
        List<Users> sameHood = usersDAO.userFromSameHood(uid);
        List<Users> friends = getFriendsByUid(uid);
        List<Users> neighbors = getNeighborsByUid(uid);
        for (Users user : sameHood) {
            if (!friends.contains(user) && !neighbors.contains(user))
                stranger.add(user);
        }
        return stranger;
    }
}
