package cn.edu.ncu.liuqing.banksavingsystem.controllers.operation.adminsubcontroller;

import cn.edu.ncu.liuqing.banksavingsystem.connectDB.OperationsForDB;
import cn.edu.ncu.liuqing.banksavingsystem.entities.Administrator;
import cn.edu.ncu.liuqing.banksavingsystem.entities.Clerk;
import cn.edu.ncu.liuqing.banksavingsystem.tools.RandomPassword;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Date;


public class LoginClerkController {
    public Administrator administrator = Administrator.getInstance();

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private Label tips;

    @FXML
    private void login() {
        String name = nameTextField.getText();
        String id = idTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String address = addressTextField.getText();
        if (name.length()<2 || name.length()>4)
            tip("提示：姓名不合法，规定2-四个字符！");
        else if (id.length() != 18)
            tip("提示：身份证号不合法，规定18个字符");
        else if (phoneNumber.length() != 11) {
            tip("提示：手机号码不合规定，规定位11位数字！");
        }
        else if (address.equals("")) {
            tip("提示：请填入地址！");
        }
        else {
            String password = RandomPassword.getRandomPassword(8,18);
            Clerk clerk = new Clerk(password,id,name,new Date(),address,Long.parseLong(phoneNumber));
            administrator.login(clerk);
            tip("注册成功，该员工的工号为：" + clerk.getNumber() +
                    "；密码为： " + clerk.getPassword() + "。请尽快更改密码！");
        }
    }

    @FXML
    private void reset() {
        nameTextField.setText("");
        idTextField.setText("");
        phoneNumberTextField.setText("");
        addressTextField.setText("");
        tips.setVisible(false);
    }

    private void tip(String message) {
        tips.setText(message);
        tips.setVisible(true);
    }
}
