package cn.edu.ncu.liuqing.banksavingsystem.controllers.operation.clerksubcontroller;

import cn.edu.ncu.liuqing.banksavingsystem.entities.Clerk;
import cn.edu.ncu.liuqing.banksavingsystem.entities.DemandAccount;
import cn.edu.ncu.liuqing.banksavingsystem.entities.FixedAccount;
import cn.edu.ncu.liuqing.banksavingsystem.tools.FileOP;
import cn.edu.ncu.liuqing.banksavingsystem.tools.Tip;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.util.Date;

public class RegisterController {
    public Clerk clerk = new Clerk();
    @FXML
    private TextField nameTF;

    @FXML
    private PasswordField passwordPF;

    @FXML
    private CheckBox cb1;

    @FXML
    private CheckBox cb2;

    @FXML
    private Label tipLabel;

    @FXML
    private void initialize() {
        clerk.setNumber((short) FileOP.readFromFile());
    }

    @FXML
    private void checkBox2() {
        if (cb2.isSelected())
            cb1.setSelected(false);
    }

    @FXML
    private void checkBox1() {
        if (cb1.isSelected())
            cb2.setSelected(false);
    }

    @FXML
    private void openAccount() {
        String name = nameTF.getText();
        String password = passwordPF.getText();
        if (name.length()<2||name.length()>4){
            Tip.tips("姓名不少于两个汉字，不多余4个汉字！");
        } else if (password.length() != 6) {
            Tip.tips("密码为6位数字!");
        } else {
            try {
                int pw = Integer.parseInt(password);
                if (cb1.isSelected()){
                    DemandAccount demandAccount = new DemandAccount();
                    demandAccount.setName(name);
                    demandAccount.setPassword(pw);
                    demandAccount.setOpenTime(new Date());
                    clerk.addDemandAccount(demandAccount);
                    tipLabel.setText("活期账户开户成功，账号为：" + demandAccount.getNo());
                }
                if (cb2.isSelected()){
                    FixedAccount fixedAccount = new FixedAccount();
                    fixedAccount.setName(name);
                    fixedAccount.setPassword(pw);
                    fixedAccount.setSaving(false);
                    fixedAccount.setOpenTime(new Date());
                    clerk.addFixedAccount(fixedAccount);
                    tipLabel.setText("定期账户开户成功，账号为：" + fixedAccount.getNo());
                }
                reset();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void reset() {
        nameTF.setText("");
        passwordPF.setText("");
        cb1.setSelected(false);
        cb2.setSelected(false);
    }
}
