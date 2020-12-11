package com.hypertars.neighborChat.model;

import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Data
public class Friends {

    /** private attributes */
    private int uidA;

    /** uid B */
    private int uidB;

    /** ftime */
    private Date fTime;

    /** public get */
    public int getUidA() {
        return uidA;
    }

    public int getUidB() {
        return uidB;
    }

    public Date getfTime() {
        return fTime;
    }

    /** public set */
    public void setUidA(int uidA) {
        this.uidA = uidA;
    }

    public void setUidB(int uidB) {
        this.uidB = uidB;
    }

    public void setfTime(Date fTime) {
        this.fTime = fTime;
    }

    @Override
    public String toString() {
        return "Friends{" +
                "uidA=" + uidA +
                ", uidB=" + uidB +
                ", fTime=" + fTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Friends)) return false;
        Friends friends = (Friends) o;
        return getUidA() == friends.getUidA() &&
                getUidB() == friends.getUidB() &&
                Objects.equals(getfTime(), friends.getfTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUidA(), getUidB(), getfTime());
    }
}
