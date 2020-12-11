package com.hypertars.neighborChat.dao;

import com.hypertars.neighborChat.model.Message;
import com.hypertars.neighborChat.model.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ReplyDAO {

    /**
     * add new reply
     * @param reply reply model
     * @return 1 or 0
     */
    boolean addNewReply(Reply reply);

    /**
     * get reply my msgid
     * @param msgid message thread id
     * @return Reply reply model
     */
    List<Reply> getReplyByMsgid(@Param("msgid") int msgid);

}
