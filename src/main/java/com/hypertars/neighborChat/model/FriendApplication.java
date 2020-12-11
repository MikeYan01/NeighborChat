package com.hypertars.neighborChat.model;

import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Data
public class FriendApplication {

    /** private attributes */
    private int applicant;

    /** recip id */
    private int recipient;

    /** txt */
    private String txt;

    /** fatime */
    private Date faTime;

    /** decision */
    private int decision;

    /** public get */
    public int getApplicant() {
        return applicant;
    }

    public int getRecipient() {
        return recipient;
    }

    public String getTxt() {
        return txt;
    }

    public Date getFaTime() {
        return faTime;
    }

    public int getDecision() {
        return decision;
    }

    /** public set */
    public void setApplicant(int applicant) {
        this.applicant = applicant;
    }

    public void setRecipient(int recipient) {
        this.recipient = recipient;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public void setFaTime(Date faTime) {
        this.faTime = faTime;
    }

    public void setDecision(int decision) {
        this.decision = decision;
    }

    @Override
    public String toString() {
        return "FriendApplication{" +
                "applicant=" + applicant +
                ", recipient=" + recipient +
                ", txt='" + txt + '\'' +
                ", faTime=" + faTime +
                ", decision=" + decision +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FriendApplication)) return false;
        FriendApplication that = (FriendApplication) o;
        return getApplicant() == that.getApplicant() &&
                getRecipient() == that.getRecipient() &&
                getDecision() == that.getDecision() &&
                Objects.equals(getTxt(), that.getTxt()) &&
                Objects.equals(getFaTime(), that.getFaTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getApplicant(), getRecipient(), getTxt(), getFaTime(), getDecision());
    }
}
