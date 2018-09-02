package cn.edu.ncu.liuqing.banksavingsystem.controllers.operation.clerksubcontroller;

import cn.edu.ncu.liuqing.banksavingsystem.entities.Clerk;
import cn.edu.ncu.liuqing.banksavingsystem.entities.DemandAccount;
import cn.edu.ncu.liuqing.banksavingsystem.entities.FixedAccount;
import cn.edu.ncu.liuqing.banksavingsystem.exception.MyException;
import cn.edu.ncu.liuqing.banksavingsystem.tools.FileOP;
import cn.edu.ncu.liuqing.banksavingsystem.tools.Tip;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ChangePasswordController {
    Clerk clerk = new Clerk((short)FileOP.readFromFile());

    @FXML
    private TextField accountNo;

    @FXML
    private PasswordField oldPassword;

    @FXML
    private PasswordField newPassword1;

    @FXML
    private PasswordField newPassword2;

    @FXML
    private void initialize() {

    }

    @FXML
    private void change() {
        int oPsw = Integer.parseInt(oldPassword.getText());
        int nPsw1 = Integer.parseInt(newPassword1.getText());
        int nPsw2 = Integer.parseInt(newPassword2.getText());
        try {
            if (accountNo.getText().length() == 17) {
                FixedAccount fixedAccount = new FixedAccount();
                fixedAccount.setNo(Long.parseLong(accountNo.getText()));
                fixedAccount.setPassword(oPsw);
                clerk.changePassword(fixedAccount,nPsw1,nPsw2);
                Tip.tips("密码修改成功!");
                reset();
            }
            else if (accountNo.getText().length() == 19) {
                DemandAccount demandAccount = new DemandAccount();
                demandAccount.setNo(accountNo.getText());
                demandAccount.setPassword(oPsw);
                clerk.changePassword(demandAccount,nPsw1,nPsw2);
                Tip.tips("密码修改成功!");
                reset();
            }
            else {
                Tip.tips("账号错误!");
            }
        } catch (MyException e) {
            Tip.tips(e.getMessage());
        }
    }

    private void reset() {
        accountNo.setText("");
        oldPassword.setText("");
        newPassword1.setText("");
        newPassword2.setText("");
    }
}
