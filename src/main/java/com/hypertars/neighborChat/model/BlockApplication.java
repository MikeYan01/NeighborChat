package com.hypertars.neighborChat.model;

import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Data
public class BlockApplication {

    /** private attributes */
    private int applicant;

    private int bid;

    private Date baTime;

    private String txt;

    private int accepts;

    private int decisions;

    /** public get */
    public int getApplicant() {
        return applicant;
    }

    public int getBid() {
        return bid;
    }

    public Date getBaTime() {
        return baTime;
    }

    public String getTxt() {
        return txt;
    }

    public int getAccepts() {
        return accepts;
    }

    public int getDecisions() {
        return decisions;
    }

    /** public set */
    public void setApplicant(int applicant) {
        this.applicant = applicant;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public void setBaTime(Date baTime) {
        this.baTime = baTime;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public void setAccepts(int accepts) {
        this.accepts = accepts;
    }

    public void setDecisions(int decisions) {
        this.decisions = decisions;
    }


    @Override
    public String toString() {
        return "BlockApplication{" +
                "applicant=" + applicant +
                ", bid=" + bid +
                ", baTime=" + baTime +
                ", txt='" + txt + '\'' +
                ", accepts=" + accepts +
                ", decisions=" + decisions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlockApplication)) return false;
        BlockApplication that = (BlockApplication) o;
        return getApplicant() == that.getApplicant() &&
                getBid() == that.getBid() &&
                getAccepts() == that.getAccepts() &&
                getDecisions() == that.getDecisions() &&
                Objects.equals(getBaTime(), that.getBaTime()) &&
                Objects.equals(getTxt(), that.getTxt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getApplicant(), getBid(), getBaTime(), getTxt(), getAccepts(), getDecisions());
    }
}
