package cn.edu.ncu.liuqing.banksavingsystem.controllers.login;

import cn.edu.ncu.liuqing.banksavingsystem.connectDB.OperationsForDB;
import cn.edu.ncu.liuqing.banksavingsystem.tools.FileOP;
import cn.edu.ncu.liuqing.banksavingsystem.tools.Tip;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClerkLoginController {
    @FXML
    private StackPane stackPane;
    private Parent clerk;

    @FXML
    private TextField clerkNoTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label tips;

    @FXML
    public void initialize() throws IOException {
        clerk = FXMLLoader.load(getClass().
                getResource("../../view/management/clerk.fxml"));
    }

    @FXML
    private void login() {
        if (clerkNoTextField.getText().equals("")) {
            tips.setVisible(true);
            tips.setText("请输入工号！");
        }
        else if (clerkNoTextField.getText().length() != 5){
            tips.setVisible(true);
            tips.setText("请输入合法工号");
        }
        else {
            Connection connection = OperationsForDB.getConnection();
            try {
                PreparedStatement sta = connection.prepareStatement(
                        "select * from clerk where Cno = ?");
                sta.setShort(1,Short.parseShort(clerkNoTextField.getText()));
                ResultSet resultSet = sta.executeQuery();
                if (resultSet.next()) {
                    String password = resultSet.getString("Password");
                    if (passwordField.getText().equals(password)) {
                        FileOP.writeToFile(Short.parseShort(clerkNoTextField.getText()));
                        stackPane.getChildren().clear();
                        stackPane.getChildren().add(clerk);
                    } else if (passwordField.getText().equals("")){
                        tips.setVisible(true);
                        tips.setText("请输入密码！");
                    } else {
                        tips.setVisible(true);
                        tips.setText("密码错误！");
                    }
                } else {
                    tips.setVisible(true
                    );
                    tips.setText("该员工不存在！");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                OperationsForDB.closeConnection(connection);
            }
        }
    }

    @FXML
    private void loginEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            login();
    }

    @FXML
    private void forgetPassword() {
        Tip.tips("请到凭工号管理员处查\n询密码或修改密码！");
    }
}
