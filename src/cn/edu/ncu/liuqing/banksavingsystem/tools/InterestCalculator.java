package cn.edu.ncu.liuqing.banksavingsystem.tools;

import cn.edu.ncu.liuqing.banksavingsystem.connectDB.OperationsForDB;
import cn.edu.ncu.liuqing.banksavingsystem.entities.DemandAccount;
import cn.edu.ncu.liuqing.banksavingsystem.entities.DemandDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 利息计算器
 */
public class InterestCalculator {
    /* 活期利息计算器 */
    public static double CalculateDemandInterest(DemandAccount account) {
        Connection connection = OperationsForDB.getConnection();
        List<DemandDetails> list = new ArrayList<>();
        double interest = 0;
        try {
            PreparedStatement sta = connection.prepareStatement("select * from demanddetails where No=?");
            sta.setString(1,account.getNo());
            ResultSet resultSet = sta.executeQuery();
            while (resultSet.next()) {
                DemandDetails details = new DemandDetails();
                details.setNo(resultSet.getString(1));
                details.setOperationTime(resultSet.getTimestamp(2));
                details.setBalance(resultSet.getDouble("balance"));
            }
            if (list.size() >= 2){
                DemandDetails details1 = list.get(list.size() - 1);
                double balance = details1.getBalance();
                java.sql.Date dateStart = new java.sql.Date(details1.getOperationTime().getTime());
                java.sql.Date dateEnd = new java.sql.Date(new Date().getTime());
                interest =  getDemandInterest(balance,dateStart,dateEnd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            OperationsForDB.closeConnection(connection);
        }
        return interest;
    }

    public static double CalculateFixedInterest(double money, String savingTime) {
        double interest = 0;
        if (savingTime.equals("三个月")) {
            interest = money * 3 * (1.43/100/12);
        } else if (savingTime.equals("六个月")) {
            interest = money * 6 * (1.69/100/12);
        } else if (savingTime.equals("一年")) {
            interest = money * 1 * (2.10/100);
        } else if (savingTime.equals("二年")) {
            interest = money * 2 * (2.52/100);
        } else if (savingTime.equals("三年")) {
            interest = money * 3 * (3.00/100);
        } else if (savingTime.equals("五年")) {
            interest = money * 5 * (3.00/100);
        }
        return interest;
    }

    public static double getDemandInterest(double balance, java.sql.Date date1, java.sql.Date date2) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateStart = df.parse(date1.toString());
        Date dateEnd = df.parse(date2.toString());
        long betweenDay = (dateStart.getTime() - dateEnd.getTime()) / (1000 * 60 * 60 * 24);
        return  (double) betweenDay * (0.3 / 360 / 100);
    }
}
