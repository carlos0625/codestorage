package cn.edu.ncu.liuqing.banksavingsystem.dao;

import cn.edu.ncu.liuqing.banksavingsystem.entities.DemandAccount;
import cn.edu.ncu.liuqing.banksavingsystem.entities.DemandDetails;
import cn.edu.ncu.liuqing.banksavingsystem.entities.FixedAccount;
import cn.edu.ncu.liuqing.banksavingsystem.entities.FixedDetails;
import cn.edu.ncu.liuqing.banksavingsystem.exception.MyException;

import java.util.List;

public interface ClerkDAO {

    /* 开户 */
    void addFixedAccount(FixedAccount account);
    void addDemandAccount(DemandAccount account);

    /* 销户 */
    void deleteFixedAccount(FixedAccount account) throws MyException;
    void deleteDemandAccount(DemandAccount account) throws MyException;

    /* 定期存取款办理 */
    void fixedDeposit(FixedAccount account, double money);
    void fixedWithdraw(FixedAccount account, double money);

    /* 修改密码 */
    void changePassword(FixedAccount account, int newPassword1, int newPassword2) throws MyException;
    void changePassword(DemandAccount account, int newPassword1,int newPassword2) throws MyException;

    /* 活期存取款办理 */
    void demandDeposit(DemandAccount account, double money,double balance);
    void demandWithdraw(DemandAccount account, double money,double balance);

    /* 查询明细 */
    List<FixedDetails> queryDetails(FixedAccount account) throws MyException;
    List<DemandDetails> queryDetails(DemandAccount account) throws MyException;

}
