package com.hypertars.neighborChat.service.Message;

import com.hypertars.neighborChat.model.MailBox;
import com.hypertars.neighborChat.model.Message;
import com.hypertars.neighborChat.model.Recipient;
import com.hypertars.neighborChat.model.Reply;

import java.util.List;

public interface MessageService {

    /** message */
    boolean addNewMsg(int recipient, int author, int rRange, String title, String sub, String txt, String coord);
    Message getMessageByMsgid (int msgid);
    List<Message> getMessageByRecipient (int recipient);
    List<Message> getMessageByAuthor (int author);
    Recipient getRecipientByMsgid (int msgid);

    /** mailbox */
    List<MailBox> getMailBoxByUid (int uid);
    List<Integer> getMsgidUnread (int uid);
    List<Integer> getMsgidRead (int uid);
    boolean setMsgidUnread (int uid, int msgid);
    boolean setMsgidRead (int uid, int msgid);

    /** reply */
    boolean addReply (int msgid, int uid, String txt, String coord);
    List<Reply> getReplyByMsgid (int msgid);

    /** notification */
    List<Message> notifyNewMessage (int uid);
    List<Message> notifyNewReply (int uid);
    List<Message> notifyNewMsgthread (int uid);
}
