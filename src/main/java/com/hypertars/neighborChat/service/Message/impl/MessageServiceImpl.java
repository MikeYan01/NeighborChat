package com.hypertars.neighborChat.service.Message.impl;

import com.hypertars.neighborChat.dao.*;
import com.hypertars.neighborChat.model.*;
import com.hypertars.neighborChat.service.Message.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
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
     * add new Message, to Message / Recipient / MessageBox
     * @param recipient recipient uid (if particular)
     * @param author author uid
     * @param rRange rRange 0: particular / 1: friends / 2: neighbors / 3: block / 4: hood
     * @param title message title
     * @param sub message sub
     * @param txt message text
     * @param coord message coordinates
     * @return true or false
     */
    @Override
    public boolean addNewMsg(int recipient, int author, int rRange, String title, String sub, String txt, String coord) {
        List<Integer> recipientList = new ArrayList<>();
        int msgid = messageDAO.getLastMsgid() + 1;
        Message msg = new Message();
        msg.setAuthor(author);
        msg.setCoord(coord);
        msg.setMsgid(msgid);
        msg.setrRange(rRange);
        msg.setSub(sub);
        msg.setTitle(title);
        msg.setTxt(txt);
        messageDAO.addNewMsg(msg);

        // construct recipient list
        if (rRange == 0) {
            Recipient rec = new Recipient();
            rec.setUid(recipient);
            rec.setMsgid(msgid);
            recipientDAO.addRecipient(rec);
            recipientList.add(recipient);
        } else if (rRange == 1) {
            List<Friends> friendship = friendsDAO.getFriends(author);
            for (Friends f : friendship) {
                int friendID;
                if (f.getUidA() == author)
                    friendID = f.getUidB();
                else
                    friendID = f.getUidA();
                recipientList.add(friendID);
            }
        } else if (rRange == 2) {
            List<Neighbors> neighborShip = neighborsDAO.getNeighbors(author);
            for (Neighbors n : neighborShip) {
                recipientList.add(n.getUidB());
            }
        } else if (rRange == 3) {
            List<Users> sameBlock = usersDAO.userFromSameBlock(author);
            for (Users user : sameBlock) {
                recipientList.add(user.getUid());
            }
        } else if (rRange == 4) {
            List<Users> sameHood = usersDAO.userFromSameHood(author);
            for (Users user : sameHood) {
                recipientList.add(user.getUid());
            }
        }

        // put into MailBox
        for (Integer recip : recipientList) {
            MailBox mailBox = new MailBox();
            mailBox.setMsgid(msgid);
            mailBox.setUid(recip);
            mailBox.setRd(false);
            mailBoxDAO.addToMailBox(mailBox);
        }
        // including author self
        MailBox mailBox = new MailBox();
        mailBox.setMsgid(msgid);
        mailBox.setUid(author);
        mailBox.setRd(true);
        mailBoxDAO.addToMailBox(mailBox);

        return true;
    }

    /**
     * get message by msgid
     * @param msgid msgid
     * @return message
     */
    @Override
    public Message getMessageByMsgid(int msgid) {
        return messageDAO.getMsgByMsgid(msgid);
    }

    /**
     * get message as recipient (query mailbox)
     * @param recipient uid
     * @return list of messages
     */
    @Override
    public List<Message> getMessageByRecipient(int recipient) {
        return messageDAO.getAllMsgByUid(recipient);
    }

    /**
     * get message as author
     * @param author uid
     * @return list of messages
     */
    @Override
    public List<Message> getMessageByAuthor(int author) {
        return messageDAO.getMsgByAuthor(author);
    }

    @Override
    public Recipient getRecipientByMsgid(int msgid) {
        return recipientDAO.getRecipientByMsgid(msgid);
    }

    @Override
    public List<MailBox> getMailBoxByUid(int uid) {
        return mailBoxDAO.getAllMsgthreadByUid(uid);
    }

    /**
     * get msgid unread
     * @param uid uid
     * @return list of msgid
     */
    @Override
    public List<Integer> getMsgidUnread(int uid) {
        List<Integer> msgidList = new ArrayList<>();
        List<MailBox> allMsgthread = getMailBoxByUid(uid);
        for (MailBox msg : allMsgthread) {
            if (!msg.getRd())
                msgidList.add(msg.getMsgid());
        }
        return msgidList;
    }

    /**
     * get msgid read
     * @param uid uid
     * @return list of msgid
     */
    @Override
    public List<Integer> getMsgidRead(int uid) {
        List<Integer> msgidList = new ArrayList<>();
        List<MailBox> allMsgthread = getMailBoxByUid(uid);
        for (MailBox msg : allMsgthread) {
            if (msg.getRd())
                msgidList.add(msg.getMsgid());
        }
        return msgidList;
    }

    /**
     * set msgthread unread
     * @param uid uid
     * @param msgid msgid
     * @return true or false
     */
    @Override
    public boolean setMsgidUnread(int uid, int msgid) {
        MailBox mailbox = new MailBox();
        mailbox.setUid(uid);
        mailbox.setMsgid(msgid);
        mailBoxDAO.setMsgthreadUnread(mailbox);
        return true;
    }

    /**
     * set msgthread read
     * @param uid uid
     * @param msgid msgid
     * @return true or false
     */
    @Override
    public boolean setMsgidRead(int uid, int msgid) {
        MailBox mailbox = new MailBox();
        mailbox.setUid(uid);
        mailbox.setMsgid(msgid);
        mailBoxDAO.setMsgthreadRead(mailbox);
        return true;
    }

    /**
     * add new reply
     * @param msgid msgid
     * @param uid user id
     * @param txt text
     * @param coord coordinates
     * @return true or false
     */
    @Override
    public boolean addReply (int msgid, int uid, String txt, String coord) {
        Reply reply = new Reply();
        reply.setMsgid(msgid);
        reply.setUid(uid);
        reply.setTxt(txt);
        reply.setCoord(coord);
        replyDAO.addNewReply(reply);

        List<MailBox> mailBoxes = mailBoxDAO.getMailBoxByMsgid(msgid);
        for (MailBox mailbox : mailBoxes) {
            if (mailbox.getUid() != uid)
                mailBoxDAO.setMsgthreadUnread(mailbox);
        }
        return false;
    }

    /**
     * get reply by msgid
     * @param msgid msgid
     * @return list of reply
     */
    @Override
    public List<Reply> getReplyByMsgid(int msgid) {
        return replyDAO.getReplyByMsgid(msgid);
    }

    /**
     * notify new message
     * @param uid uid
     * @return list of message
     */
    @Override
    public List<Message> notifyNewMessage(int uid) {
        List<Message> returns = new ArrayList<>();
        List<Message> messages = messageDAO.notifyNewMessage(uid);
        for (Message msg: messages) {
            if (msg.getAuthor() != uid)
                returns.add(msg);
        }
        return returns;
    }

    /**
     * notify message having new reply
     * @param uid uid
     * @return list of message
     */
    @Override
    public List<Message> notifyNewReply(int uid) {
        return messageDAO.notifyNewReply(uid);
    }

    /**
     * notify message that is new or old with new reply
     * @param uid uid
     * @return list of message
     */
    @Override
    public List<Message> notifyNewMsgthread(int uid) {
        List<Message> newMsg = notifyNewMessage(uid);
        List<Message> newReply = notifyNewReply(uid);
        newMsg.addAll(newReply);
        return newMsg;
    }
}
