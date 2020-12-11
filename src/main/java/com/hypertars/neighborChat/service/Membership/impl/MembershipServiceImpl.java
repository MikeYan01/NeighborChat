package com.hypertars.neighborChat.service.Membership.impl;

import com.hypertars.neighborChat.dao.*;
import com.hypertars.neighborChat.model.BlockApplication;
import com.hypertars.neighborChat.model.Blocks;
import com.hypertars.neighborChat.model.Hoods;
import com.hypertars.neighborChat.model.UserBlock;
import com.hypertars.neighborChat.service.Membership.MembershipService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MembershipServiceImpl implements MembershipService {

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
     * get all userblock information for a user
     * @param uid user id
     * @return List<UserBlock>
     */
    @Override
    public List<UserBlock> getUserBlocksByUid(int uid) {
        return userBlockDAO.getUserBlocksByUid(uid);
    }

    /**
     * check whether blockapplication already exists
     * @param ba blockapplication
     * @return 1: exist, 0: no
     */
    @Override
    public boolean checkBlockApplicationExist(BlockApplication ba) {
        return blockApplicationDAO.checkBlockApplicationExist(ba) > 0;
    }

    /**
     * add new block application
     * @param applicant user id
     * @param bid block id
     * @param txt text
     * @return true or false
     */
    @Override
    public boolean addBlockApplication(int applicant, int bid, String txt) {
        BlockApplication ba = new BlockApplication();
        ba.setBid(bid);
        ba.setApplicant(applicant);
        ba.setTxt(txt);
        if (checkBlockApplicationExist(ba)) {
            return false;
        }
        blockApplicationDAO.addBlockApplication(ba);
        return true;
    }

    /**
     * quit current block
     * @param uid user id
     * @return true or false
     */
    @Override
    public boolean quitBlock(int uid) {
        userBlockDAO.setAllUserBlocksInactive(uid);
        return true;
    }

    /**
     * accept block application
     * @param applicant applicant uid
     * @param uid decision maker uid
     * @return true or false
     */
    @Override
    public boolean acceptBlockApplication(int applicant, int uid) {
        int bid = 0;
        List<UserBlock> ubs = userBlockDAO.getUserBlocksByUid(uid);
        for (UserBlock ub : ubs)
            if (ub.getStatus()) { bid = ub.getBid(); }
        if (bid == 0) { return false; }
        BlockApplication ba = new BlockApplication();
        ba.setApplicant(applicant);
        ba.setBid(bid);
        blockApplicationDAO.acceptBlockApplication(ba);
        return true;
    }

    /**
     * reject block application
     * @param applicant applicant uid
     * @param uid decision maker uid
     * @return rue or false
     */
    @Override
    public boolean rejectBlockApplication(int applicant, int uid) {
        int bid = 0;
        List<UserBlock> ubs = userBlockDAO.getUserBlocksByUid(uid);
        for (UserBlock ub : ubs)
            if (ub.getStatus()) { bid = ub.getBid(); }
        if (bid == 0) { return false; }
        BlockApplication ba = new BlockApplication();
        ba.setApplicant(applicant);
        ba.setBid(bid);
        blockApplicationDAO.rejectBlockApplication(ba);
        return true;
    }

    @Override
    public boolean deleteBlockApplication(int applicant, int bid) {
        BlockApplication ba = new BlockApplication();
        ba.setApplicant(applicant);
        ba.setBid(bid);
        if (!checkBlockApplicationExist(ba))
            return true;
        blockApplicationDAO.deleteBlockApplication(ba);
        return true;
    }

    /**
     * get all block applications from the applicant
     * @param applicant user id
     * @return list of block applications
     */
    @Override
    public List<BlockApplication> getBlockApplicationByApplicant(int applicant) {
        return blockApplicationDAO.getBlockApplicationByApplicant(applicant);
    }

    /**
     * check whether given block application is accepted
     * @param ba block application
     * @return true or false
     */
    @Override
    public boolean checkBlockApplicationAcceptance(BlockApplication ba) {
        if (ba == null) return false;
        int countMembers = userBlockDAO.countUsersByBid(ba.getBid());
        BlockApplication completeBA = blockApplicationDAO.getExactBlockApplication(ba);
        int accepts = completeBA.getAccepts();
        if (countMembers == 0) {
            return true;
        } else if (countMembers <= 3 && accepts == countMembers) {
            return true;
        } else return accepts >= 3;
    }

    /**
     * check whether given block application is rejected
     * @param ba block application
     * @return true or false
     */
    @Override
    public boolean checkBlockApplicationFailure(BlockApplication ba) {
        if (ba == null || checkBlockApplicationAcceptance(ba)) return false;
        int countMembers = userBlockDAO.countUsersByBid(ba.getBid());
        BlockApplication completeBA = blockApplicationDAO.getExactBlockApplication(ba);
        int decisions = completeBA.getDecisions();
        Date applyDate = completeBA.getBaTime();
        Date current = new Date();
        if (countMembers == decisions)
            return true;
        else return ((current.getTime() - applyDate.getTime()) / (1000 * 60 * 60 * 24)) >= 14;
    }

    /**
     * notify accepted block application by applicant
     * @param applicant user id
     * @return list of accepted block application
     */
    @Override
    public List<BlockApplication> notifyPassedBlockApplicationFromApplicant(int applicant) {
        List<BlockApplication> passed = new ArrayList<>();
        List<BlockApplication> bas = blockApplicationDAO.getBlockApplicationByApplicant(applicant);
        for (BlockApplication ba : bas) {
            if (checkBlockApplicationAcceptance(ba))
                passed.add(ba);
        }
        return passed;
    }

    /**
     * notify rejected block application by applicant
     * @param applicant user id
     * @return list of rejected block application
     */
    @Override
    public List<BlockApplication> notifyFailedBlockApplicationFromApplicant(int applicant) {
        List<BlockApplication> failed = new ArrayList<>();
        List<BlockApplication> bas = blockApplicationDAO.getBlockApplicationByApplicant(applicant);
        for (BlockApplication ba : bas) {
            if (checkBlockApplicationFailure(ba))
                failed.add(ba);
        }
        return failed;
    }

    /**
     * notify block application by recipient
     * @param recipient uid
     * @return list of new block applications
     */
    @Override
    public List<BlockApplication> notifyNewBlockApplicationToRecipient(int recipient) {
        return blockApplicationDAO.notifyNewBlockApplication(recipient);
    }

    /**
     * user join block, first check whether such application exists, then delete it and update old info and join new
     * @param uid user id
     * @param bid block id
     * @return true or false
     */
    @Override
    public boolean joinBlock(int uid, int bid) {
        // first check whether given block application information is right (exists)
        BlockApplication ba = new BlockApplication();
        ba.setApplicant(uid);
        ba.setBid(bid);
        if (blockApplicationDAO.checkBlockApplicationExist(ba) == 0)
            return false;

        // update old info
        List<UserBlock> userBlocksList = userBlockDAO.getUserBlocksByUid(uid);
        if (!userBlocksList.isEmpty()) {
            // old user
            deleteBlockApplication(uid, bid);
            userBlockDAO.setAllUserBlocksInactive(uid);
        }
        blockApplicationDAO.deleteBlockApplication(ba);

        // join Block
        UserBlock userBlock = new UserBlock();
        userBlock.setBid(ba.getBid());
        userBlock.setUid(ba.getApplicant());
        userBlockDAO.addUserBlock(userBlock);
        return true;
    }

    /**
     * notify users there exist new members in his / her block
     * @param uid user id
     * @return list of new members
     */
    @Override
    public List<UserBlock> notifyNewBlockMember(int uid) {
        return userBlockDAO.notifyNewBlockMember(uid);
    }

    /**
     * get block by bid
     * @param bid block id
     * @return block
     */
    @Override
    public Blocks getBlockByBid(int bid) {
        return blocksDAO.getBlockByBid(bid);
    }

    /**
     * get block by hid
     * @param hid hood id
     * @return list of blocks
     */
    @Override
    public List<Blocks> getBlockByHid(int hid) {
        return blocksDAO.getBlocksByHid(hid);
    }

    /**
     * get block info by uid
     * @param uid user id
     * @return blocks
     */
    @Override
    public Blocks getBlockByUid(int uid) {
        int bid = 0;
        List<UserBlock>ubs = userBlockDAO.getUserBlocksByUid(uid);
        for (UserBlock ub : ubs)
            if (ub.getStatus())
                bid = ub.getBid();
        if (bid == 0) return null;
        return blocksDAO.getBlockByBid(bid);
    }

    /**
     * get all blocks information in database
     * @return List of blocks
     */
    @Override
    public List<Blocks> getAllBlocks() {
        return blocksDAO.getAllBlocks();
    }

    /**
     * get hood by uid
     * @param uid uid
     * @return hood
     */
    @Override
    public Hoods getHoodByUid(int uid) {
        return hoodsDAO.getHoodByUid(uid);
    }

    /**
     * get hood by bid
     * @param bid block id
     * @return hood
     */
    @Override
    public Hoods getHoodByBid(int bid) {
        return hoodsDAO.getHoodByBid(bid);
    }

    /**
     * get hood by hid
     * @param hid hood id
     * @return hood
     */
    @Override
    public Hoods getHoodByHid(int hid) {
        return hoodsDAO.getHoodByHid(hid);
    }

    /**
     * get all hoods in database
     * @return list of hoods
     */
    @Override
    public List<Hoods> getAllHoods() {
        return hoodsDAO.getAllHoods();
    }

    /**
     * get blocks in same hood by uid
     * @param uid user id
     * @return list of blocks
     */
    @Override
    public List<Blocks> getBlocksInSameHoodByUid(int uid) {
        Hoods hood = getHoodByUid(uid);
        return blocksDAO.getBlocksByHid(hood.getHid());
    }

    /**
     * current user block
     * @param uid user id
     * @return user block
     */
    @Override
    public UserBlock getCurrentMember(int uid) {
        List<UserBlock> ubs = userBlockDAO.getUserBlocksByUid(uid);
        for (UserBlock ub : ubs)
            if (ub.getStatus())
                return ub;
        return null;
    }
}
