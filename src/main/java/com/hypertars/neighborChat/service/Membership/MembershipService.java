package com.hypertars.neighborChat.service.Membership;

import com.hypertars.neighborChat.model.BlockApplication;
import com.hypertars.neighborChat.model.Blocks;
import com.hypertars.neighborChat.model.Hoods;
import com.hypertars.neighborChat.model.UserBlock;

import java.util.List;

public interface MembershipService {

    /** apply for block */
    List<UserBlock> getUserBlocksByUid (int uid);
    boolean checkBlockApplicationExist (BlockApplication ba);
    boolean addBlockApplication(int applicant, int bid, String txt);

    /** execute application */
    boolean acceptBlockApplication (int applicant, int uid);
    boolean rejectBlockApplication (int applicant, int uid);
    boolean deleteBlockApplication (int applicant, int bid);

    /** check application */
    List<BlockApplication> getBlockApplicationByApplicant (int applicant);
    boolean checkBlockApplicationAcceptance (BlockApplication ba);
    boolean checkBlockApplicationFailure (BlockApplication ba);

    /** block member */
    boolean joinBlock (int uid, int bid);
    boolean quitBlock (int uid);

    /** notification */
    List<BlockApplication> notifyPassedBlockApplicationFromApplicant (int applicant);
    List<BlockApplication> notifyFailedBlockApplicationFromApplicant (int applicant);
    List<BlockApplication> notifyNewBlockApplicationToRecipient (int recipient);
    List<UserBlock> notifyNewBlockMember (int uid);

    /** basic loads */
    Blocks getBlockByUid (int uid);
    Blocks getBlockByBid (int bid);
    List<Blocks> getBlockByHid (int uid);
    List<Blocks> getAllBlocks ();
    Hoods getHoodByUid (int uid);
    Hoods getHoodByBid (int bid);
    Hoods getHoodByHid (int hid);
    List<Hoods> getAllHoods();
    List<Blocks> getBlocksInSameHoodByUid (int uid);
    UserBlock getCurrentMember (int uid);
}
