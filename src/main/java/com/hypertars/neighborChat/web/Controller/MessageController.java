package com.hypertars.neighborChat.web.Controller;

import com.alibaba.fastjson.JSON;
import com.hypertars.neighborChat.dao.MessageDAO;
import com.hypertars.neighborChat.model.Message;
import com.hypertars.neighborChat.model.Users;
import com.hypertars.neighborChat.service.Membership.MembershipService;
import com.hypertars.neighborChat.service.Message.MessageService;
import com.hypertars.neighborChat.service.Relationship.RelationshipService;
import com.hypertars.neighborChat.service.UserAccount.UserAccountService;
import com.hypertars.neighborChat.web.NBCBaseController;
import com.hypertars.neighborChat.web.NBCLogicCallBack;
import com.hypertars.neighborChat.web.NBCResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController extends NBCBaseController {

    @Resource
    private UserAccountService userAccountService;

    @Resource
    private MessageService messageService;

    @Resource
    private MembershipService membershipService;

    @Resource
    private RelationshipService relationshipService;

    @Resource
    private MessageDAO messageDAO;

    @RequestMapping(value = "getAllMessageThreads", produces = "text/script;charset=UTF-8")
    public String getAllMessageThreads(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                res.setResultObj(messageService.getMessageByRecipient(user.getUid()));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "getMsgidUnread", produces = "text/script;charset=UTF-8")
    public String getMsgidUnread(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                res.setResultObj(messageService.getMsgidUnread(user.getUid()));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "getMsgidRead", produces = "text/script;charset=UTF-8")
    public String getMsgidRead(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                res.setResultObj(messageService.getMsgidRead(user.getUid()));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "getMessageByAuthor", produces = "text/script;charset=UTF-8")
    public String getMessageByAuthor(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                int author = 0;
                author = Integer.parseInt(request.getParameter("author"));
                res.setResultObj(messageService.getMessageByAuthor(author));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "getMessageByThread", produces = "text/script;charset=UTF-8")
    public String getMessageByThread(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                int msgid = Integer.parseInt(request.getParameter("msgid"));
                res.setResultObj(messageService.getMessageByMsgid(msgid));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "getReplyByThread", produces = "text/script;charset=UTF-8")
    public String getReplyByThread(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                int msgid = Integer.parseInt(request.getParameter("msgid"));
                res.setResultObj(messageService.getReplyByMsgid(msgid));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @Transactional
    @RequestMapping(value = "addMessage", produces = "text/script;charset=UTF-8")
    public String addMessage(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                // input recipient = 0 if not particular
                int recipient = Integer.parseInt(request.getParameter("recipient"));
                int rRange = Integer.parseInt(request.getParameter("rRange"));
                String title = request.getParameter("title");
                String sub = request.getParameter("sub");
                String txt = request.getParameter("txt");
                String coord = request.getParameter("coord");
                if (!coord.isEmpty())
                    coord = '('+coord+')';
                if (!messageService.addNewMsg(recipient, user.getUid(), rRange, title, sub, txt, coord)) {
                    res.setResultObj("insert new message error");
                } else res.setResultObj("success");
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "addReply", produces = "text/script;charset=UTF-8")
    public String addReply(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                int msgid = Integer.parseInt(request.getParameter("msgid"));
                Users user = loginUsers.get();
                String txt = request.getParameter("txt");
                String coord = request.getParameter("coord");
                if(!messageService.addReply(msgid, user.getUid(), txt, coord)) {
                    res.setResultObj("insert new reply error");
                } else res.setResultObj("success");
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "setMessageRead", produces = "text/script;charset=UTF-8")
    public String setMessageRead(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                int msgid = Integer.parseInt(request.getParameter("msgid"));
                Users user = loginUsers.get();
                if(!messageService.setMsgidRead(user.getUid(), msgid)) {
                    res.setResultObj("set msg read error");
                } else res.setResultObj("success");
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "setMessageUnread", produces = "text/script;charset=UTF-8")
    public String setMessageUnread(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                int msgid = Integer.parseInt(request.getParameter("msgid"));
                Users user = loginUsers.get();
                if(!messageService.setMsgidUnread(user.getUid(), msgid)) {
                    res.setResultObj("set msg unread error");
                } else res.setResultObj("success");
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "countNewMessageFromHood", produces = "text/script;charset=UTF-8")
    public String countNewMessageFromHood(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                int msgSameHood = 0;
                List<Users> usersSameHood = relationshipService.getUsersInSameHoodByUid(user.getUid());
                for (Users userSameHood : usersSameHood) {
                    List<Message> tmsg = messageService.getMessageByAuthor(userSameHood.getUid());
                    for (Message msg : tmsg) {
                        if (msg.getrRange() == 4 && msg.getMtime().after(user.getLastLog()));
                            msgSameHood++;
                    }
                }
                res.setResultObj(msgSameHood);
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "countNewMessageFromBlock", produces = "text/script;charset=UTF-8")
    public String countNewMessageFromBlock(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                int msgSameBlock = 0;
                List<Users> usersSameBlock = relationshipService.getUsersInSameBlockByUid(user.getUid());
                for (Users userSameBlock : usersSameBlock) {
                    List<Message> tmsg = messageService.getMessageByAuthor(userSameBlock.getUid());
                    for (Message msg : tmsg) {
                        if (msg.getrRange() == 3 && msg.getMtime().after(user.getLastLog()));
                        msgSameBlock++;
                    }
                }
                res.setResultObj(msgSameBlock);
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

    @RequestMapping(value = "searchMsgByKeyword", produces = "text/script;charset=UTF-8")
    public String searchMsgByKeyword(HttpServletRequest request, String callback) {
        NBCResult<Object> result = new NBCResult<>();
        result = protectController(request, null, new NBCLogicCallBack() {
            @Override
            public NBCResult<Object> execute() throws Exception {
                NBCResult<Object> res = new NBCResult<>();
                Users user = loginUsers.get();
                String keyword = request.getParameter("keyword");
                if (keyword.isEmpty()) {
                    res.setResultObj("keyword empty!");
                    return res;
                }
                keyword = "%" + keyword + "%";
                Message message = new Message();
                message.setAuthor(user.getUid());
                message.setTxt(keyword);
                res.setResultObj(messageDAO.searchMsgByKeyword(message));
                return res;
            }
        });
        return callback + "(" + JSON.toJSONString(result) + ")";
    }

}
