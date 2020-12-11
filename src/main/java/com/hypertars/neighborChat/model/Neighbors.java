package com.hypertars.neighborChat.model;

import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Data
public class Neighbors {

    /** private attributes */
    private int uidA;

    private int uidB;

    private Date nTime;

    /** public get */
    public int getUidA() {
        return uidA;
    }

    public int getUidB() {
        return uidB;
    }

    public Date getnTime() {
        return nTime;
    }

    /** public set */
    public void setUidA(int uidA) {
        this.uidA = uidA;
    }

    public void setUidB(int uidB) {
        this.uidB = uidB;
    }

    public void setnTime(Date nTime) {
        this.nTime = nTime;
    }

    @Override
    public String toString() {
        return "Neighbors{" +
                "uidA=" + uidA +
                ", uidB=" + uidB +
                ", nTime=" + nTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Neighbors)) return false;
        Neighbors neighbors = (Neighbors) o;
        return getUidA() == neighbors.getUidA() &&
                getUidB() == neighbors.getUidB() &&
                Objects.equals(getnTime(), neighbors.getnTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUidA(), getUidB(), getnTime());
    }
}
