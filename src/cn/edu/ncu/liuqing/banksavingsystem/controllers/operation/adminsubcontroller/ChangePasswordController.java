package cn.edu.ncu.liuqing.banksavingsystem.controllers.operation.adminsubcontroller;

import cn.edu.ncu.liuqing.banksavingsystem.entities.Administrator;
import cn.edu.ncu.liuqing.banksavingsystem.entities.Clerk;
import cn.edu.ncu.liuqing.banksavingsystem.exception.MyException;
import cn.edu.ncu.liuqing.banksavingsystem.tools.Tip;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ChangePasswordController {
    public Administrator administrator = Administrator.getInstance();
    private Clerk clerk;

    @FXML
    private TextField idTextField;

    @FXML
    private Label passwordLabel;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private PasswordField newPassword;

    private boolean isNumber(String no) {
        for (int i = 0; i < no.length(); i++) {
            if (!Character.isDigit(no.charAt(i)))
                return false;
        }
        return true;
    }

    @FXML
    private void select() {
        String no = idTextField.getText();
        if (no.length() != 5 || !isNumber(no)) {
            Tip.tips("工号不合法！");
        } else {
            clerk = new Clerk(Short.parseShort(no));
            try {
                administrator.queryInfo(clerk);
            } catch (MyException e) {
                Tip.tips("该员工不存在！");
                return;
            }
            passwordLabel.setText(clerk.getPassword());
            anchorPane.setVisible(true);
        }
    }

    @FXML
    private void reset() {
        anchorPane.setVisible(false);
        idTextField.setText("");
        newPassword.setText("");
    }

    @FXML
    private void sure() {
        if (newPassword.getText().length()<8||newPassword.getText().length()>18)
            Tip.tips("新密码不合法！必须为\n"+"8-18位数字或字母组合");
        else {
            administrator.changePassword(clerk, newPassword.getText());
            Tip.tips("修改成功！");
            reset();
        }
    }
}
