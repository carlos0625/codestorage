package cn.edu.ncu.liuqing.banksavingsystem.dao;

import cn.edu.ncu.liuqing.banksavingsystem.entities.Clerk;
import cn.edu.ncu.liuqing.banksavingsystem.exception.MyException;

import java.util.List;

public interface AdministratorDAO {

    void login(Clerk clerk); //注册员工

    void delete(Clerk clerk) throws MyException; //删除员工

    List<Clerk> queryAll() throws MyException; //查询所有员工

    void queryInfo(Clerk clerk) throws MyException; //查询员工信息

    void changePassword(Clerk clerk, String newPassword); //修改员工密码

}

