package cn.edu.ncu.liuqing.banksavingsystem.entities;

import java.util.Date;

/**
 * 活期账户类
 */
public class DemandAccount {
    private String no;
    private int password;
    private String name;
    private Date openTime;

    public DemandAccount() {
    }

    public DemandAccount(String no, int password, String name, Date openTime) {
        this.no = no;
        this.password = password;
        this.name = name;
        this.openTime = openTime;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }
}
