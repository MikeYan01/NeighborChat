package com.hypertars.neighborChat.dao;

import com.hypertars.neighborChat.model.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageDAO {

    /**
     * add new message
     * @param message message model
     */
    void addNewMsg(Message message);

    /**
     * get message my msgid
     * @param msgid message id
     * @return Message message model
     */
    Message getMsgByMsgid(@Param("msgid") int msgid);

    /**
     * get messages by uid (as recipient, from MailBox)
     * @param uid user id
     * @return List<Message> messages models
     */
    List<Message> getAllMsgByUid(@Param("uid") int uid);

    /**
     * get messages by author
     * @param author author uid
     * @return List<Message> messages models
     */
    List<Message> getMsgByAuthor(@Param("author") int author);

    int getLastMsgid();

    List<Message> notifyNewMessage(@Param("uid") int uid);

    List<Message> notifyNewReply(@Param("uid") int uid);

    List<Message> searchMsgByKeyword(Message message);
}
