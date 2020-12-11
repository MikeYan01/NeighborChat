package com.hypertars.neighborChat.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Hoods {

    /** private attributes */
    private int hid;

    private String hname;

    private String SW;

    private String NE;

    /** public get */
    public int getHid() {
        return hid;
    }

    public String getHname() {
        return hname;
    }

    public String getSW() {
        return SW;
    }

    public String getNE() {
        return NE;
    }

    /** public set */
    public void setHid(int hid) {
        this.hid = hid;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }

    public void setSW(String SW) {
        this.SW = SW;
    }

    public void setNE(String NE) {
        this.NE = NE;
    }

    @Override
    public String toString() {
        return "Hoods{" +
                "hid=" + hid +
                ", hname='" + hname + '\'' +
                ", SW='" + SW + '\'' +
                ", NE='" + NE + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hoods)) return false;
        Hoods hoods = (Hoods) o;
        return getHid() == hoods.getHid() &&
                Objects.equals(getHname(), hoods.getHname()) &&
                Objects.equals(getSW(), hoods.getSW()) &&
                Objects.equals(getNE(), hoods.getNE());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHid(), getHname(), getSW(), getNE());
    }
}
