package com.hypertars.neighborChat.model;

import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Data
public class Message {

    /** private attributes */
    private int msgid;

    private int author;

    private int rRange;

    private Date mtime;

    private String title;

    private String sub;

    private String txt;

    private String coord;

    /** public get */
    public int getMsgid() {
        return msgid;
    }

    public int getAuthor() {
        return author;
    }

    public int getrRange() {
        return rRange;
    }

    public Date getMtime() {
        return mtime;
    }

    public String getTitle() {
        return title;
    }

    public String getSub() {
        return sub;
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

    public void setAuthor(int author) {
        this.author = author;
    }

    public void setrRange(int rRange) {
        this.rRange = rRange;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public void setCoord(String coord) {
        this.coord = coord;
    }

    @Override
    public String toString() {
        return "Message{" +
                "msgid=" + msgid +
                ", author=" + author +
                ", rRange=" + rRange +
                ", mtime=" + mtime +
                ", title='" + title + '\'' +
                ", sub='" + sub + '\'' +
                ", txt='" + txt + '\'' +
                ", coord='" + coord + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return getMsgid() == message.getMsgid();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMsgid(), getAuthor(), getrRange(), getMtime(), getTitle(), getSub(), getTxt(), getCoord());
    }
}
