package cn.edu.ncu.liuqing.banksavingsystem.controllers.operation.clerksubcontroller;

import cn.edu.ncu.liuqing.banksavingsystem.connectDB.OperationsForDB;
import cn.edu.ncu.liuqing.banksavingsystem.entities.Clerk;
import cn.edu.ncu.liuqing.banksavingsystem.entities.FixedAccount;
import cn.edu.ncu.liuqing.banksavingsystem.tools.FileOP;
import cn.edu.ncu.liuqing.banksavingsystem.tools.InterestCalculator;
import cn.edu.ncu.liuqing.banksavingsystem.tools.Tip;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;

public class FixedDepositController {
    Clerk clerk = new Clerk((short)FileOP.readFromFile());

    @FXML
    private AnchorPane anchorPane;
    @FXML

    private TextField accountNo;

    @FXML
    private PasswordField password;

    @FXML
    private TextField savingMoney;

    @FXML
    private CheckBox depositClass;

    @FXML
    private ComboBox<String> savingPeriod = new ComboBox<>();

    private ObservableList<String> items;

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

    private String[] periods;
    private String savingTime;
    private FixedAccount account;

    @FXML
    private void initialize() {
        periods = new String[]{"三个月","六个月","一年","二年","三年","五年"};
        items = FXCollections.observableArrayList(periods);
        savingPeriod.getItems().addAll(items);
    }

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
                                    anchorPane.setVisible(true);
                                    deposited.setText("" + set.getDouble("Money"));
                                    interest.setText(""+ set.getDouble("Benifition"));
                                    startDay.setText("" + set.getDate("Begin"));
                                    endDay.setText("" + set.getDate("End"));
                                    period.setText("" + set.getString("SavingTime"));
                                }
                            } else {
                                anchorPane.setVisible(true);
                                resetLabel();
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

    @FXML
    private void commit() {
        if (accountNo.getText().length() == 0 ||
                password.getText().length() == 0 ||
                savingMoney.getText().length() == 0) {
            Tip.tips("无法提交!");
        } else if (isSaving.getText().equals("是")){
            Tip.tips("账户已办理一笔定期存款，无法继续办理!");
        } else {
            java.util.Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if (savingTime.equals("三个月")) {
                calendar.add(Calendar.MONTH,3);
            } else if (savingTime.equals("六个月")) {
                calendar.add(Calendar.MONTH,6);
            } else if (savingTime.equals("一年")) {
                calendar.add(Calendar.YEAR,1);
            } else if (savingTime.equals("二年")) {
                calendar.add(Calendar.YEAR,2);
            } else if (savingTime.equals("三年")) {
                calendar.add(Calendar.YEAR,3);
            } else if (savingTime.equals("五年")){
                calendar.add(Calendar.YEAR,5);
            }
            Date endDate = calendar.getTime();
            double money = Double.parseDouble(savingMoney.getText());
            clerk.insertIntoFixedDetails(account.getNo(),timestamp,"定期",savingTime,
                    new java.sql.Date(date.getTime()),new java.sql.Date(endDate.getTime()),money
                    ,InterestCalculator.CalculateFixedInterest(money,savingTime),clerk.getNumber());
            update();
            Tip.tips("办理成功!");
            select();
        }
    }

    private void update() {
        Connection connection = OperationsForDB.getConnection();
        try {
             PreparedStatement sta = connection.prepareStatement("update fixedaccount set IsSaving = true" +
             " where No = ?");
             sta.setLong(1,account.getNo());
             sta.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void resetLabel() {
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
        savingMoney.setText("");
        resetLabel();
    }

    @FXML
    private void choosePeriod() {
        savingTime = periods[items.indexOf(savingPeriod.getValue())];
    }
}
