package com.hypertars.neighborChat.web.Controller;

import com.alibaba.fastjson.JSON;
import com.hypertars.neighborChat.model.Users;
import com.hypertars.neighborChat.service.Message.MessageService;
import com.hypertars.neighborChat.service.Membership.MembershipService;
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
@RequestMapping("relationship")
public class RelationshipController extends NBCBaseController {

    @Resource
    private UserAccountService userAccountService;

    @Resource
    private MessageService messageService;

    @Resource
    private MembershipService membershipService;

    @Resource
    private RelationshipService relationshipService;

    @RequestMapping(value = "getAllFriends", produces = "text/script;charset=UTF-8")
    public String getAllFriends(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                res.setResultObj(relationshipService.getFriendsByUid(user.getUid()));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "getAllNeighbors", produces = "text/script;charset=UTF-8")
    public String getAllNeighbors(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                res.setResultObj(relationshipService.getNeighborsByUid(user.getUid()));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "getAllStrangersFromBlock", produces = "text/script;charset=UTF-8")
    public String getAllStrangersFromBlock(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                res.setResultObj(relationshipService.getStrangersFromSameBlock(user.getUid()));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "getAllStrangersFromHood", produces = "text/script;charset=UTF-8")
    public String getAllStrangersFromHood(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                res.setResultObj(relationshipService.getStrangersFromSameHood(user.getUid()));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "applyFriend", produces = "text/script;charset=UTF-8")
    public String applyFriend(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                int recipient = Integer.parseInt(request.getParameter("recipient"));
                String txt = request.getParameter("txt");
                if (relationshipService.checkFriendship(user.getUid(), recipient)) {
                    res.setResultObj("Friend already exists");
                } else if (relationshipService.checkFriendApplicationExist(user.getUid(), recipient)) {
                    res.setResultObj("friend application already exists");
                } else if (relationshipService.addFriendApplication(user.getUid(), recipient, txt)) {
                    res.setResultObj("success");
                } else res.setResultObj("failure");
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    /**
     * decide friend application as recipient
     * @param request applicant - uid / decision: true or false
     * @param callback callback
     * @return result
     */
    @RequestMapping(value = "decideFriendApplication", produces = "text/script;charset=UTF-8")
    public String decideFriendApplication(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                int applicant = Integer.parseInt(request.getParameter("applicant"));
                boolean decision = Boolean.parseBoolean(request.getParameter("decision"));
                if (!relationshipService.checkFriendApplicationExist(applicant, user.getUid())) {
                    res.setResultObj("friend application does not exist");
                    return res;
                }
                if (!decision) {
                    if (relationshipService.rejectFriendApplication(applicant, user.getUid()))
                        res.setResultObj("reject application succeeded");
                } else if (decision) {
                    if (relationshipService.acceptFriendApplication(applicant, user.getUid()))
                        res.setResultObj("accept application succeeded");
                } else {
                    res.setResultObj("failure");
                }
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "addNeighbor", produces = "text/script;charset=UTF-8")
    public String addNeighbor(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                int neighbor = Integer.parseInt(request.getParameter("neighborUid"));
                if (relationshipService.checkNeighborExist(user.getUid(), neighbor)) {
                    res.setResultObj("Neighbor already exists");
                } else if (relationshipService.addNeighbor(user.getUid(), neighbor)) {
                    res.setResultObj("success");
                } else {
                    res.setResultObj("failure");
                }
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "deleteFriend", produces = "text/script;charset=UTF-8")
    public String deleteFriend(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                int friend = Integer.parseInt(request.getParameter("friendUid"));
                if (!relationshipService.checkFriendship(user.getUid(), friend)) {
                    res.setResultObj("Not your friend");
                } else if (relationshipService.deleteFriend(user.getUid(), friend)) {
                    res.setResultObj("success");
                } else res.setResultObj("failure");
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "deleteNeighbor", produces = "text/script;charset=UTF-8")
    public String deleteNeighbor(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                int neighbor = Integer.parseInt(request.getParameter("neighborUid"));
                if (!relationshipService.checkNeighborExist(user.getUid(), neighbor)) {
                    res.setResultObj("Not your neighbor");
                } else if (relationshipService.deleteNeighbor(user.getUid(), neighbor)) {
                    res.setResultObj("success");
                } else res.setResultObj("failure");
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "getAllFriendApplicationAsApplicant", produces = "text/script;charset=UTF-8")
    public String getAllFriendApplicationAsApplicant(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                res.setResultObj(relationshipService.getFriendApplicationByApplicant(user.getUid()));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "getAllFriendApplicationAsRecipient", produces = "text/script;charset=UTF-8")
    public String getAllFriendApplicationAsRecipient(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                res.setResultObj(relationshipService.getFriendApplicationByRecipient(user.getUid()));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }
}

