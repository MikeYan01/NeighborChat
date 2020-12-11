package com.hypertars.neighborChat.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Blocks {
    /** private attributes */
    private int bid;

    private String bname;

    private int hid;

    private String SW;

    private String NE;

    /** public get */
    public int getBid() {
        return bid;
    }

    public String getBname() {
        return bname;
    }

    public int getHid() {
        return hid;
    }

    public String getSW() {
        return SW;
    }

    public String getNE() {
        return NE;
    }

    /** public set */
    public void setBid(int bid) {
        this.bid = bid;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }

    public void setSW(String SW) {
        this.SW = SW;
    }

    public void setNE(String NE) {
        this.NE = NE;
    }

    @Override
    public String toString() {
        return "Blocks{" +
                "bid=" + bid +
                ", bname='" + bname + '\'' +
                ", hid=" + hid +
                ", SW='" + SW + '\'' +
                ", NE='" + NE + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Blocks)) return false;
        Blocks blocks = (Blocks) o;
        return getBid() == blocks.getBid() &&
                getHid() == blocks.getHid() &&
                Objects.equals(getBname(), blocks.getBname()) &&
                Objects.equals(getSW(), blocks.getSW()) &&
                Objects.equals(getNE(), blocks.getNE());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBid(), getBname(), getHid(), getSW(), getNE());
    }
}
