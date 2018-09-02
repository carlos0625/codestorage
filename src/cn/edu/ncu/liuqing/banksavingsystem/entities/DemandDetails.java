package cn.edu.ncu.liuqing.banksavingsystem.entities;

import java.sql.Timestamp;

/**
 * 活期详单类
 */
public class DemandDetails {
    private String no;
    private Timestamp operationTime;
    private String abstracts;
    private double in;
    private double out;
    private double balance;
    private double interest;
    private short clerkNo;

    public DemandDetails() {

    }

    public DemandDetails(String no, Timestamp operationTime, String abstracts,
                         double in, double out, double balance, double interest, short clerkNo) {
        this.no = no;
        this.operationTime = operationTime;
        this.abstracts = abstracts;
        this.in = in;
        this.out = out;
        this.balance = balance;
        this.interest = interest;
        this.clerkNo = clerkNo;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
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

    public double getIn() {
        return in;
    }

    public void setIn(double in) {
        this.in = in;
    }

    public double getOut() {
        return out;
    }

    public void setOut(double out) {
        this.out = out;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public short getClerkNo() {
        return clerkNo;
    }

    public void setClerkNo(short clerkNo) {
        this.clerkNo = clerkNo;
    }
}
