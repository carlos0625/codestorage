package cn.edu.ncu.liuqing.banksavingsystem.controllers.operation.clerksubcontroller;

import cn.edu.ncu.liuqing.banksavingsystem.connectDB.OperationsForDB;
import cn.edu.ncu.liuqing.banksavingsystem.entities.Clerk;
import cn.edu.ncu.liuqing.banksavingsystem.entities.FixedAccount;
import cn.edu.ncu.liuqing.banksavingsystem.entities.FixedDetails;
import cn.edu.ncu.liuqing.banksavingsystem.tools.FileOP;
import cn.edu.ncu.liuqing.banksavingsystem.tools.InterestCalculator;
import cn.edu.ncu.liuqing.banksavingsystem.tools.Tip;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;



import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FixedWithdrawController {
    Clerk clerk = new Clerk((short)FileOP.readFromFile());

    @FXML
    private TextField accountNo;

    @FXML
    private PasswordField password;

    @FXML
    private TextField withdrawedMoney;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label isSaving;

    @FXML
    private Label deposited;

    @FXML
    private Label interest;

    @FXML
    private Label startDay;

    @FXML
    private Label endDay;

    @FXML
    private Label period;

    private FixedAccount account;
    private FixedDetails details;

    @FXML
    private void select() {
        if (accountNo.getText().length() == 0) {
            Tip.tips("请输入账号!");
        } else if (accountNo.getText().length() != 17) {
            Tip.tips("账号不合法!");
        } else {
            account = new FixedAccount();
            account.setNo(Long.parseLong(accountNo.getText()));
            if (password.getText().length() == 0) {
                Tip.tips("请输入密码!");
            } else {
                account.setPassword(Integer.parseInt(password.getText()));
                Connection con = OperationsForDB.getConnection();
                try {
                    PreparedStatement sta = con.prepareStatement("select * from fixedaccount where No=?");
                    sta.setLong(1,account.getNo());
                    ResultSet resultSet = sta.executeQuery();
                    if (resultSet.next()) {
                        if (resultSet.getInt("Password") == account.getPassword()) {
                            if (resultSet.getBoolean("IsSaving")){
                                isSaving.setText("是");
                                PreparedStatement sta2 = con.prepareStatement("select * from fixeddetails where"
                                        + " No=? and OperationTime= (select max(OperationTime) from fixeddetails where No=?)");
                                sta2.setLong(1,account.getNo());
                                sta2.setLong(2,account.getNo());
                                ResultSet set = sta2.executeQuery();
                                while (set.next()) {
                                    details = new FixedDetails();
                                    details.setMoney(set.getDouble("Money"));
                                    details.setBegin(set.getDate("Begin"));
                                    details.setEnd(set.getDate("End"));
                                    details.setBenefit(set.getDouble("Benifition"));
                                    details.setSavingTime(set.getString("SavingTime"));
                                    anchorPane.setVisible(true);
                                    deposited.setText("" + set.getDouble("Money"));
                                    interest.setText(""+ set.getDouble("Benifition"));
                                    startDay.setText("" + set.getDate("Begin"));
                                    endDay.setText("" + set.getDate("End"));
                                    period.setText("" + set.getString("SavingTime"));
                                }
                            } else {
                                anchorPane.setVisible(true);
                                labelReset();
                                isSaving.setText("否");
                            }
                        } else {
                            Tip.tips("密码错误!");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    OperationsForDB.closeConnection(con);
                }
            }
        }
    }

    private void labelReset() {
        isSaving.setText("");
        deposited.setText("");
        interest.setText("");
        startDay.setText("");
        endDay.setText("");
        period.setText("");
    }

    @FXML
    private void reset() {
        anchorPane.setVisible(false);
        accountNo.setText("");
        password.setText("");
        withdrawedMoney.setText("");
        labelReset();
    }

    @FXML
    private void commit() throws ParseException, InterruptedException {
        if (accountNo.getText().length() == 0) {
            Tip.tips("请输入账号!");
        } else if (password.getText().length() == 0) {
            Tip.tips("请输入密码!");
        } else if (withdrawedMoney.getText().length() == 0) {
            Tip.tips("请输入取款金额!");
        } else {
            if (isSaving.getText().equals("否")) {
                Tip.tips("当前无存款，无法取款!");
            } else if (isSaving.getText().equals("是")){
                Date now = new Date();
                java.sql.Date date1 = new java.sql.Date(now.getTime());
                java.sql.Date date2 = new java.sql.Date(details.getEnd().getTime());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date dateStart = df.parse(date1.toString());
                Date dateEnd = df.parse(date2.toString());
                //取款金额
                double money = Double.parseDouble(withdrawedMoney.getText());
                if (dateEnd.getTime() - dateStart.getTime() > 0) {
                    if (details.getMoney() < money) {
                        Tip.tips("存款不足，无法取款!");
                    } else if (details.getMoney() == money){
                        clerk.insertIntoFixedDetails(account.getNo(),new Timestamp(new Date().getTime()),
                                "提前全额支取",null,null,null,
                                money,InterestCalculator.getDemandInterest(money,
                                        new java.sql.Date(details.getBegin().getTime()),new java.sql.Date(now.getTime())),
                                clerk.getNumber());
                        update();
                        Tip.tips("取款成功!");
                    } else if (money <= 0){
                        Tip.tips("无效的取款金额!");
                    } else {
                        clerk.insertIntoFixedDetails(account.getNo(),new Timestamp(new Date().getTime()),
                                "提前部分支取",null,null,null,
                                money,InterestCalculator.getDemandInterest(money,
                                        new java.sql.Date(details.getBegin().getTime()),new java.sql.Date(now.getTime())),
                                clerk.getNumber());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        clerk.insertIntoFixedDetails(account.getNo(), new Timestamp(new Date().getTime()),
                                "定期", details.getSavingTime(), details.getBegin(), details.getEnd(),
                                details.getMoney() - money,
                                InterestCalculator.CalculateFixedInterest(details.getMoney() - money, details.getSavingTime()),
                                clerk.getNumber());
                        Tip.tips("提前部分支取成功!");
                    }
                } else if (dateEnd.getTime() - dateStart.getTime() == 0) {
                    clerk.insertIntoFixedDetails(account.getNo(),new Timestamp(new Date().getTime()),
                            "定期全额支取",null,null,null,
                            money,InterestCalculator.getDemandInterest(money,
                                    new java.sql.Date(details.getBegin().getTime()),new java.sql.Date(now.getTime())),
                            clerk.getNumber());
                    update();
                    Tip.tips("全额提前支取成功!");
                } else {
                    Double newBalance = details.getMoney() + details.getBenefit();
                    clerk.insertIntoFixedDetails(account.getNo(),new Timestamp(details.getEnd().getTime()),
                            "结息",null,null,null,newBalance,
                            details.getBenefit(), clerk.getNumber());
                    Thread.sleep(1000);
                    Date date = new Date();
                    java.sql.Date startDay = new java.sql.Date(details.getEnd().getTime());
                    java.sql.Date endDay = new java.sql.Date(date.getTime());
                    Double liXi = InterestCalculator.getDemandInterest(newBalance,startDay,endDay);
                    clerk.insertIntoFixedDetails(account.getNo(),new Timestamp(date.getTime()),"逾期支取",
                            null,startDay,endDay,newBalance,liXi,clerk.getNumber());
                    update();
                    Tip.tips("逾期支取成功!");
                }
                select();
            }
        }
    }

    private void update() {
        Connection con = OperationsForDB.getConnection();
        try {
            PreparedStatement sta = con.prepareStatement("update fixedaccount set IsSaving = false" +
                    " where No = ?");
            sta.setLong(1,account.getNo());
            sta.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
