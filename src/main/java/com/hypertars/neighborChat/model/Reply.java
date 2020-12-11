package com.hypertars.neighborChat.model;

import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Data
public class Reply {

    /** private attributes */
    private int msgid;

    private int uid;

    private Date rTime;

    private String txt;

    private String coord;

    /** public get */
    public int getMsgid() {
        return msgid;
    }

    public int getUid() {
        return uid;
    }

    public Date getrTime() {
        return rTime;
    }

    public String getTxt() {
        return txt;
    }

    public String getCoord() {
        return coord;
    }

    /** public set */
    public void setMsgid(int msgid) {
        this.msgid = msgid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setrTime(Date rTime) {
        this.rTime = rTime;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public void setCoord(String coord) {
        this.coord = coord;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "msgid=" + msgid +
                ", uid=" + uid +
                ", rTime=" + rTime +
                ", txt='" + txt + '\'' +
                ", coord='" + coord + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reply)) return false;
        Reply reply = (Reply) o;
        return getMsgid() == reply.getMsgid() &&
                getUid() == reply.getUid() &&
                Objects.equals(getrTime(), reply.getrTime()) &&
                Objects.equals(getTxt(), reply.getTxt()) &&
                Objects.equals(getCoord(), reply.getCoord());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMsgid(), getUid(), getrTime(), getTxt(), getCoord());
    }
}
