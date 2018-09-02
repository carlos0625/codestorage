package cn.edu.ncu.liuqing.banksavingsystem.controllers.operation.clerksubcontroller;

import cn.edu.ncu.liuqing.banksavingsystem.entities.Clerk;
import cn.edu.ncu.liuqing.banksavingsystem.entities.DemandAccount;
import cn.edu.ncu.liuqing.banksavingsystem.exception.MyException;
import cn.edu.ncu.liuqing.banksavingsystem.tools.FileOP;
import cn.edu.ncu.liuqing.banksavingsystem.tools.Tip;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class DemandWithdrawController {
    Clerk clerk = new Clerk((short) FileOP.readFromFile());

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField accountNo;

    @FXML
    private PasswordField password;

    @FXML
    private Label name;

    @FXML
    private Label balance;

    @FXML
    private TextField money;

    private DemandAccount account;

    @FXML
    private void select() {
        String no = accountNo.getText();
        String psw = password.getText();
        try {
            if (no.length() == 19) {
                account = new DemandAccount();
                account.setNo(no);
                account.setPassword(Integer.parseInt(psw));
                double bl = clerk.selectBalance(account);
                anchorPane.setVisible(true);
                name.setText(account.getName());
                balance.setText("" + bl);
            } else {
                Tip.tips("账号不合法!");
            }
        } catch (MyException e) {
            Tip.tips(e.getMessage());
        }
    }

    @FXML
    private void commit() {
        if (accountNo.getText().length() == 0) {
            Tip.tips("请输入账号!");
        } else if (password.getText().length() == 0) {
            Tip.tips("请输入密码!");
        } else if (money.getText().length() == 0){
            Tip.tips("请输入存款!");
        } else {
            double money = Double.parseDouble(this.money.getText());
            double balance = Double.parseDouble(this.balance.getText());
            if (money > balance) {
                Tip.tips("余额不足!");
            } else{
                clerk.demandWithdraw(account,money,balance);
                select();
                Tip.tips("取款成功!");
            }
        }
    }

    @FXML
    private void reset() {
        anchorPane.setVisible(false);
        accountNo.setText("");
        password.setText("");
        money.setText("");
        balance.setText("");
        name.setText("");
    }
}
