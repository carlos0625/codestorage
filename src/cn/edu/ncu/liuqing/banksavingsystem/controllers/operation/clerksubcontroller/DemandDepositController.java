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


public class DemandDepositController {
    Clerk clerk = new Clerk((short) FileOP.readFromFile());

    @FXML
    private TextField accountNo;

    @FXML
    private PasswordField password;

    @FXML
    private TextField money;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label name;

    @FXML
    private Label balance;

    private DemandAccount account;

    @FXML
    void select() {
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
            clerk.demandDeposit(account,Double.parseDouble(money.getText()),Double.parseDouble(balance.getText()));
            Tip.tips("存款成功!");
            select();
        }
    }

    @FXML
    void reset() {
        anchorPane.setVisible(false);
        accountNo.setText("");
        password.setText("");
        money.setText("");
        name.setText("");
        balance.setText("");
    }
}
