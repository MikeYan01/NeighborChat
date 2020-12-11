package com.hypertars.neighborChat.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Recipient {

    /** private attributes */
    private int msgid;

    private int uid;

    /** public get */
    public int getMsgid() {
        return msgid;
    }

    public int getUid() {
        return uid;
    }

    /** public set */
    public void setMsgid(int msgid) {
        this.msgid = msgid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Recipient{" +
                "msgid=" + msgid +
                ", uid=" + uid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipient)) return false;
        Recipient recipient = (Recipient) o;
        return getMsgid() == recipient.getMsgid() &&
                getUid() == recipient.getUid();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMsgid(), getUid());
    }
}
