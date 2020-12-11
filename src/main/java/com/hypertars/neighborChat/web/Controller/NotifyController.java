package com.hypertars.neighborChat.web.Controller;

import com.alibaba.fastjson.JSON;
import com.hypertars.neighborChat.model.BlockApplication;
import com.hypertars.neighborChat.model.FriendApplication;
import com.hypertars.neighborChat.model.Users;
import com.hypertars.neighborChat.service.Membership.MembershipService;
import com.hypertars.neighborChat.service.Message.MessageService;
import com.hypertars.neighborChat.service.Relationship.RelationshipService;
import com.hypertars.neighborChat.service.UserAccount.UserAccountService;
import com.hypertars.neighborChat.web.NBCBaseController;
import com.hypertars.neighborChat.web.NBCLogicCallBack;
import com.hypertars.neighborChat.web.NBCResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("notify")
public class NotifyController extends NBCBaseController {

    @Resource
    private UserAccountService userAccountService;

    @Resource
    private MessageService messageService;

    @Resource
    private MembershipService membershipService;

    @Resource
    private RelationshipService relationshipService;

    @RequestMapping(value = "notifyNewBlockApplicationAsRecipient", produces = "text/script;charset=UTF-8")
    public String notifyNewBlockApplicationAsRecipient (HttpServletRequest request, String callback){
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                List<BlockApplication> newBA = membershipService.notifyNewBlockApplicationToRecipient(user.getUid());
                res.setResultObj(newBA);
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "notifyAcceptedBlockApplicationAsApplicant", produces = "text/script;charset=UTF-8")
    public String notifyAcceptedBlockApplicationAsApplicant (HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                List<BlockApplication> newBA = membershipService.notifyPassedBlockApplicationFromApplicant(user.getUid());
                res.setResultObj(newBA);
                for (BlockApplication ba : newBA) {
                    if (!addUserBlock(ba))
                        System.out.println("add user block failed");
                    membershipService.deleteBlockApplication(ba.getApplicant(), ba.getBid());
                }
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "notifyRejectedBlockApplicationAsApplicant", produces = "text/script;charset=UTF-8")
    public String notifyRejectedBlockApplicationAsApplicant (HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                List<BlockApplication> newBA = membershipService.notifyFailedBlockApplicationFromApplicant(user.getUid());
                res.setResultObj(newBA);
                for (BlockApplication ba : newBA)
                    membershipService.deleteBlockApplication(ba.getApplicant(), ba.getBid());
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "notifyNewFriendApplicationAsRecipient", produces = "text/script;charset=UTF-8")
    public String notifyNewFriendApplicationAsRecipient (HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                List<FriendApplication> newFA = (relationshipService.notifyNewFriendApplicationToRecipient(user.getUid()));
                res.setResultObj(newFA);
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "notifyNewFriendApplicationMadeAsApplicant", produces = "text/script;charset=UTF-8")
    public String notifyNewFriendApplicationMadeAsApplicant (HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                List<FriendApplication> newFA = (relationshipService.notifyNewFriendApplicationMadeToApplicant(user.getUid()));
                res.setResultObj(newFA);
                for (FriendApplication fa: newFA) {
                    if (fa.getDecision() == 1)
                        if (!addFriend(fa))
                            System.out.println("add friends to table failed");
                    relationshipService.deleteFriendApplication(fa.getApplicant(), fa.getRecipient());
                }
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "notifyAcceptedFriendApplicationAsApplicant", produces = "text/script;charset=UTF-8")
    public String notifyAcceptedFriendApplicationAsApplicant (HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                List<FriendApplication> newFA = (relationshipService.notifyPassedFriendApplicationFromApplicant(user.getUid()));
                res.setResultObj(newFA);
                for (FriendApplication fa: newFA) {
                    if (!addFriend(fa))
                        System.out.println("add friends to table failed");
                    relationshipService.deleteFriendApplication(fa.getApplicant(), fa.getRecipient());
                }
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "notifyRejectedFriendApplicationAsApplicant", produces = "text/script;charset=UTF-8")
    public String notifyRejectedFriendApplicationAsApplicant (HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                List<FriendApplication> newFA = (relationshipService.notifyFailedFriendApplicationFromApplicant(user.getUid()));
                res.setResultObj(newFA);
                for (FriendApplication fa: newFA)
                    relationshipService.deleteFriendApplication(fa.getApplicant(), fa.getRecipient());
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "notifyNewMessage", produces = "text/script;charset=UTF-8")
    public String notifyNewMessage (HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                res.setResultObj(messageService.notifyNewMessage(user.getUid()));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "notifyNewReply", produces = "text/script;charset=UTF-8")
    public String notifyNewReply (HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                res.setResultObj(messageService.notifyNewReply(user.getUid()));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "notifyNewMsgOrReplyAsThread", produces = "text/script;charset=UTF-8")
    public String notifyNewMsgOrReplyAsThread (HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                res.setResultObj(messageService.notifyNewMsgthread(user.getUid()));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "notifyNewBlockMember", produces = "text/script;charset=UTF-8")
    public String notifyNewBlockMember (HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                res.setResultObj(membershipService.notifyNewBlockMember(user.getUid()));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "updateLastLog", produces = "text/script;charset=UTF-8")
    public String updateLastLog (HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                if (!userAccountService.updateLastLog(user.getUid()))
                    res.setResultObj("update last log failed");
                else res.setResultObj("update last log succeeded");
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    private boolean addUserBlock (BlockApplication ba) {
        return membershipService.joinBlock(ba.getApplicant(), ba.getBid());
    }

    private boolean addFriend (FriendApplication fa) {
        return relationshipService.addFriends(fa.getApplicant(), fa.getRecipient());
    }
}
