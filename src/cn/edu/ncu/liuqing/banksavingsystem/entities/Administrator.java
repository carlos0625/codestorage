package cn.edu.ncu.liuqing.banksavingsystem.entities;

import cn.edu.ncu.liuqing.banksavingsystem.connectDB.OperationsForDB;
import cn.edu.ncu.liuqing.banksavingsystem.dao.AdministratorDAO;
import cn.edu.ncu.liuqing.banksavingsystem.exception.MyException;

import javax.crypto.spec.OAEPParameterSpec;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *    系统管理员（唯一）：
 *          特征:  账号：Administrator
 *                 密码:******************（8-18位随机数字字母）
 *          功能：为职员注册
 *               删除职员
 *               查询所有职员
 *               查询职员信息
 *               为职工修改密码
 */
public class Administrator implements AdministratorDAO {
    public static Administrator instance;
    public  List<Clerk> clerks;

    private Administrator() {
        clerks = new ArrayList<>();
    }

    public static Administrator getInstance() {
        if ( instance == null)
            instance = new Administrator();
        return instance;
    }

    @Override
    public void login(Clerk clerk) {
        Connection connection = OperationsForDB.getConnection();
        String sql1 = "select max(Cno) from Clerk";
        String sql2 = "insert into Clerk values(?,?,?,?,?,?,?)";
        short maxCno = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql1);
            PreparedStatement preparedStatement = connection.prepareStatement(sql2);
            while (resultSet.next())
                maxCno = resultSet.getShort(1);
            if (maxCno == 0)
                maxCno = 10000;
            short cno = (short) (maxCno + (short)1);
            clerk.setNumber(cno);
            preparedStatement.setShort(1,clerk.getNumber());
            preparedStatement.setString(2,clerk.getPassword());
            preparedStatement.setString(3,clerk.getIdCardNo());
            preparedStatement.setString(4,clerk.getName());
            preparedStatement.setDate(5,new Date(clerk.getInTime().getTime()));
            preparedStatement.setString(6,clerk.getAddress());
            preparedStatement.setLong(7,clerk.getPhoneNumber());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            OperationsForDB.closeConnection(connection);
        }
    }

    @Override
    public void delete(Clerk clerk) {
        Connection connection = OperationsForDB.getConnection();
        String sql = "delete from clerk where cno = ?";
        try {
            PreparedStatement sta = connection.prepareStatement(sql);
            sta.setShort(1,clerk.getNumber());
            sta.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            OperationsForDB.closeConnection(connection);
        }
    }

    @Override
    public List<Clerk> queryAll() throws MyException {
        List<Clerk> list = new ArrayList<>();
        Connection connection = OperationsForDB.getConnection();
        String sql = "select * from Clerk";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Clerk clerk = new Clerk();
                clerk.setNumber(resultSet.getShort("Cno"));
                clerk.setPassword(resultSet.getString("Password"));
                clerk.setIdCardNo(resultSet.getString("IDCardNo"));
                clerk.setName(resultSet.getString("Name"));
                clerk.setInTime(resultSet.getDate("InTime"));
                clerk.setAddress(resultSet.getString("Address"));
                clerk.setPhoneNumber(resultSet.getLong("PhoneNo"));
                list.add(clerk);
            }
        } catch (SQLException e) {
            throw new MyException("没有员工注册！");
        } finally {
            OperationsForDB.closeConnection(connection);
        }
        return list;
    }

    @Override
    public void queryInfo(Clerk clerk) throws MyException {
        Connection connection = OperationsForDB.getConnection();
        String sql = "select * from Clerk where Cno = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setShort(1,clerk.getNumber());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                clerk.setPassword(resultSet.getString("Password"));
                clerk.setIdCardNo(resultSet.getString("IDCardNo"));
                clerk.setName(resultSet.getString("Name"));
                clerk.setInTime(resultSet.getDate("InTime"));
                clerk.setAddress(resultSet.getString("Address"));
                clerk.setPhoneNumber(resultSet.getLong("PhoneNo"));
            }
            else
                throw new MyException("该员工不存在！");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            OperationsForDB.closeConnection(connection);
        }
    }

    @Override
    public void changePassword(Clerk clerk, String newPassword) {
        Connection connection = OperationsForDB.getConnection();
        String sql = "update Clerk set password=?";
        try {
            PreparedStatement sta = connection.prepareStatement(sql);
            sta.setString(1,newPassword);
            sta.execute();
            clerk.setPassword(newPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            OperationsForDB.closeConnection(connection);
        }
    }
}
