package com.hypertars.neighborChat.dao;

import com.hypertars.neighborChat.model.Recipient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecipientDAO {

    /**
     * get recipient by msgid
     * @param msgid message id
     * @return Recipient recipient model
     */
    Recipient getRecipientByMsgid(@Param("msgid") int msgid);

    /**
     * add recipient (particular)
     * @param recipient uid
     */
    void addRecipient(Recipient recipient);
}
