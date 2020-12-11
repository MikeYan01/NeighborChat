package com.hypertars.neighborChat.web.Controller;

import com.alibaba.fastjson.JSON;
import com.hypertars.neighborChat.model.BlockApplication;
import com.hypertars.neighborChat.model.UserBlock;
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

@RestController
@RequestMapping("membership")
public class MembershipController extends NBCBaseController {

    @Resource
    private UserAccountService userAccountService;

    @Resource
    private MessageService messageService;

    @Resource
    private MembershipService membershipService;

    @Resource
    private RelationshipService relationshipService;

    @RequestMapping(value = "getCurrentBlockInfo", produces = "text/script;charset=UTF-8")
    public String getCurrentBlockInfo(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                res.setResultObj(membershipService.getBlockByUid(user.getUid()));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "getCurrentHoodInfo", produces = "text/script;charset=UTF-8")
    public String getCurrentHoodInfo(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                int bid = membershipService.getCurrentMember(user.getUid()).getBid();
                res.setResultObj(membershipService.getHoodByBid(bid));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "getAllUserBlocksForCurrentUser", produces = "text/script;charset=UTF-8")
    public String getAllUserBlocksForCurrentUser(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                res.setResultObj(membershipService.getUserBlocksByUid(user.getUid()));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "getBlockByUid", produces = "text/script;charset=UTF-8")
    public String getBlockByUid(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                int uid = Integer.parseInt(request.getParameter("uid"));
                res.setResultObj(membershipService.getBlockByUid(uid));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "getBlockByBid", produces = "text/script;charset=UTF-8")
    public String getBlockByBid(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                int bid = Integer.parseInt(request.getParameter("bid"));
                res.setResultObj(membershipService.getBlockByBid(bid));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "getBlocksByHid", produces = "text/script;charset=UTF-8")
    public String getBlocksByHid(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                int hid = Integer.parseInt(request.getParameter("hid"));
                res.setResultObj(membershipService.getBlockByHid(hid));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "blocksAvailable", produces = "text/script;charset=UTF-8")
    public String blocksAvailable(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                res.setResultObj(membershipService.getAllBlocks());
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "applyBlock", produces = "text/script;charset=UTF-8")
    public String applyBlock(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                int bid = Integer.parseInt(request.getParameter("bid"));
                String txt = request.getParameter("txt");
                BlockApplication ba = new BlockApplication();
                ba.setBid(bid);
                ba.setApplicant(user.getUid());
                if (bid == membershipService.getCurrentMember(user.getUid()).getBid()){
                    res.setResultObj("You already in this block");
                } else if (membershipService.checkBlockApplicationExist(ba)) {
                    res.setResultObj("Application Already Exists");
                } else {
                    membershipService.addBlockApplication(user.getUid(), bid, txt);
                }
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "decideBlockApplication", produces = "text/script;charset=UTF-8")
    public String decideBlockApplication(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                int applicant = Integer.parseInt(request.getParameter("applicant"));
                short decision = Short.parseShort(request.getParameter("decision"));
                BlockApplication ba = new BlockApplication();
                ba.setApplicant(applicant);
                ba.setBid(membershipService.getCurrentMember(user.getUid()).getBid());
                if (!membershipService.checkBlockApplicationExist(ba)) {
                    res.setResultObj("block application not exists");
                } else if (decision == 1) {
                    membershipService.acceptBlockApplication(applicant, user.getUid());
                    res.setResultObj("accept succeeded");
                } else if (decision == 0) {
                    membershipService.rejectBlockApplication(applicant, user.getUid());
                    res.setResultObj("reject succeeded");
                } else {
                    res.setResultObj("failure");
                }
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "exitCurrentBlock", produces = "text/script;charset=UTF-8")
    public String exitCurrentBlock(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                UserBlock cub = membershipService.getCurrentMember(user.getUid());
                if (cub == null) {
                    res.setResultObj("You do not belong to any block currently");
                }
                if (membershipService.quitBlock(user.getUid())) {
                    relationshipService.deleteAllNeighbors(user.getUid());
                    res.setResultObj("success");
                } else {
                    res.setResultObj("failure");
                }
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }


    @RequestMapping(value = "getHoodByUid", produces = "text/script;charset=UTF-8")
    public String getHoodByUid(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                int uid = Integer.parseInt(request.getParameter("uid"));
                res.setResultObj(membershipService.getHoodByUid(uid));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "getHoodByBid", produces = "text/script;charset=UTF-8")
    public String getHoodByBid(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                int bid = Integer.parseInt(request.getParameter("bid"));
                res.setResultObj(membershipService.getHoodByBid(bid));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "getHoodByHid", produces = "text/script;charset=UTF-8")
    public String getHoodByHid(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                int hid = Integer.parseInt(request.getParameter("hid"));
                res.setResultObj(membershipService.getHoodByHid(hid));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "getAllHoods", produces = "text/script;charset=UTF-8")
    public String getAllHoods(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                res.setResultObj(membershipService.getAllHoods());
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "getBlocksInSameHoodByUid", produces = "text/script;charset=UTF-8")
    public String getBlocksInSameHoodByUid(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                int uid = Integer.parseInt(request.getParameter("uid"));
                UserBlock cub = membershipService.getCurrentMember(uid);
                if (cub == null) {
                    res.setResultObj("user not in any block");
                } else {
                    res.setResultObj(membershipService.getBlocksInSameHoodByUid(uid));
                }
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }
}
