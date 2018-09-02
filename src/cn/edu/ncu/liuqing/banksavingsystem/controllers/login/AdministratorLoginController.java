package cn.edu.ncu.liuqing.banksavingsystem.controllers.login;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class AdministratorLoginController {
    private Parent administrator;

    @FXML
    private Label tips;

    @FXML
    private StackPane stackPane;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField adminTextField;

    @FXML
    private void initialize() throws IOException {
        administrator = FXMLLoader.load(getClass()
                .getResource("../../view/management/administrator.fxml"));
    }

    @FXML
    private void login() {
        if (!adminTextField.getText().equals("Administrator")) {
            tips.setText("提示：用户名错误！");
            tips.setVisible(true);
        }
        else if (!passwordField.getText().equals("123456")) {
            tips.setVisible(true);
            tips.setText("提示：密码错误！");
        }
        else {
            tips.setStyle("-fx-progress-color: green");
            tips.setText("登陆成功！");
            stackPane.getChildren().clear();
            stackPane.getChildren().add(administrator);
        }
    }

    @FXML
    private void loginEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            login();
    }
}
