package com.hypertars.neighborChat.dao;

import com.hypertars.neighborChat.model.MailBox;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MailBoxDAO {

    /**
     * get all mailbox by uid
     * @param uid uid
     * @return List<MailBox> mailbox models
     */
    List<MailBox> getAllMsgthreadByUid(@Param("uid") int uid);

    /**
     * set messages unread
     * @param mailBox mailbox models
     */
    void setMsgthreadUnread(MailBox mailBox);

    /**
     * set messages read
     * @param mailBox mailbox models
     */
    void setMsgthreadRead(MailBox mailBox);

    void addToMailBox(MailBox mailBox);

    List<MailBox> getMailBoxByMsgid(@Param("msgid") int msgid);
}
