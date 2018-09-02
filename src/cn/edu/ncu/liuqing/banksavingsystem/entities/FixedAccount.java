package cn.edu.ncu.liuqing.banksavingsystem.entities;

import java.util.Date;

/**
 * 定期账户类
 */
public class FixedAccount {
    private long no;
    private int password;
    private String name;
    private Date openTime;
    private boolean isSaving;

    public FixedAccount() {
    }

    public FixedAccount(long no, int password, String name, Date openTime, boolean isSaving) {
        this.no = no;
        this.password = password;
        this.name = name;
        this.openTime = openTime;
        this.isSaving = isSaving;
    }

    public long getNo() {
        return no;
    }

    public void setNo(long no) {
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

    public boolean isSaving() {
        return isSaving;
    }

    public void setSaving(boolean saving) {
        isSaving = saving;
    }
}
