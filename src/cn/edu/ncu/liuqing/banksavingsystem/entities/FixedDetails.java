package cn.edu.ncu.liuqing.banksavingsystem.entities;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 定期详单类
 */
public class FixedDetails {
    private long no;
    private Timestamp operationTime;
    private String abstracts;
    private String savingTime;
    private Date begin;
    private Date end;
    private double money;
    private double benefit;
    private short clerkNo;

    public FixedDetails() {

    }

    public FixedDetails(long no, Timestamp operationTime, String abstracts, String savingTime,
                        Date begin, Date end, double money, double benefit, short clerkNo) {
        this.no = no;
        this.operationTime = operationTime;
        this.abstracts = abstracts;
        this.savingTime = savingTime;
        this.begin = begin;
        this.end = end;
        this.money = money;
        this.benefit = benefit;
        this.clerkNo = clerkNo;
    }

    public long getNo() {
        return no;
    }

    public void setNo(long no) {
        this.no = no;
    }

    public Timestamp getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Timestamp operationTime) {
        this.operationTime = operationTime;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public String getSavingTime() {
        return savingTime;
    }

    public void setSavingTime(String savingTime) {
        this.savingTime = savingTime;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getBenefit() {
        return benefit;
    }

    public void setBenefit(double benefit) {
        this.benefit = benefit;
    }

    public short getClerkNo() {
        return clerkNo;
    }

    public void setClerkNo(short clerkNo) {
        this.clerkNo = clerkNo;
    }
}