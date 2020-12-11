package com.hypertars.neighborChat.model;

import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Data
public class UserBlock {

    /** private attributes */
    private int uid;

    private int bid;

    private Date ubTime;

    private boolean status;

    /** public get */
    public int getUid() {
        return uid;
    }

    public int getBid() {
        return bid;
    }

    public Date getUbTime() {
        return ubTime;
    }

    public boolean getStatus() {
        return status;
    }

    /** public set */
    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public void setUbTime(Date ubTime) {
        this.ubTime = ubTime;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserBlock{" +
                "uid=" + uid +
                ", bid=" + bid +
                ", ubTime=" + ubTime +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserBlock)) return false;
        UserBlock userBlock = (UserBlock) o;
        return getUid() == userBlock.getUid() &&
                getBid() == userBlock.getBid() &&
                getStatus() == userBlock.getStatus() &&
                Objects.equals(getUbTime(), userBlock.getUbTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUid(), getBid(), getUbTime(), getStatus());
    }
}
