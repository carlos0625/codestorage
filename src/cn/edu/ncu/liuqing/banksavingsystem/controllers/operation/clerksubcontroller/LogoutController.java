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

public class LogoutController {
    Clerk clerk = new Clerk((short) FileOP.readFromFile());

    @FXML
    private TextField accountNo;

    @FXML
    private PasswordField password;

    @FXML
    private void initialize() {
    }

    @FXML
    private void logout() {
        String no = accountNo.getText();
        String pwd = password.getText();
        try {
            if (no.length() == 17) {
                FixedAccount fixedAccount = new FixedAccount();
                fixedAccount.setNo(Long.parseLong(no));
                fixedAccount.setPassword(Integer.parseInt(pwd));
                clerk.deleteFixedAccount(fixedAccount);
                Tip.tips("销户成功!");
                reset();
            } else if (no.length() == 19) {
                DemandAccount demandAccount = new DemandAccount();
                demandAccount.setNo(no);
                demandAccount.setPassword(Integer.parseInt(pwd));
                clerk.deleteDemandAccount(demandAccount);
                Tip.tips("销户成功!");
                reset();
            }
        } catch (MyException e) {
            Tip.tips(e.getMessage());
        }
    }

    private void reset() {
        accountNo.setText("");
        password.setText("");
    }
}
