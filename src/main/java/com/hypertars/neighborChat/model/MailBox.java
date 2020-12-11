package com.hypertars.neighborChat.model;

import lombok.Data;

import java.util.Objects;


@Data
public class MailBox {

    /** private attributes */
    private int uid;

    private int msgid;

    private boolean rd;

    /** public get */
    public int getUid() {
        return uid;
    }

    public int getMsgid() {
        return msgid;
    }

    public boolean getRd() {
        return rd;
    }

    /** public set */
    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setMsgid(int msgid) {
        this.msgid = msgid;
    }

    public void setRd(boolean rd) {
        this.rd = rd;
    }

    @Override
    public String toString() {
        return "MailBox{" +
                "uid=" + uid +
                ", msgid=" + msgid +
                ", rd=" + rd +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MailBox)) return false;
        MailBox mailBox = (MailBox) o;
        return getUid() == mailBox.getUid() &&
                getMsgid() == mailBox.getMsgid() &&
                getRd() == mailBox.getRd();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUid(), getMsgid(), getRd());
    }
}
