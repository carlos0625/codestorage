package cn.edu.ncu.liuqing.banksavingsystem.controllers.operation;

import cn.edu.ncu.liuqing.banksavingsystem.tools.FileOP;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ClerkController {
    private Parent changePassword;
    private Parent demandDeposit;
    private Parent demandWithdraw;
    private Parent fixedDeposit;
    private Parent fixedWithdraw;
    private Parent logout;
    private Parent register;
    private Parent searchDetails;

    @FXML
    private Label clerkIdLabel;

    @FXML
    private VBox vBox;

    @FXML
    private void initialize() throws IOException {
        clerkIdLabel.setText(FileOP.readFromFile() + "");
        changePassword = FXMLLoader.load(getClass()
                .getResource("../../view/management/clerksubUI/changepassword.fxml"));
        demandDeposit = FXMLLoader.load(getClass()
                .getResource("../../view/management/clerksubUI/demanddeposit.fxml"));
        demandWithdraw = FXMLLoader.load(getClass()
                .getResource("../../view/management/clerksubUI/demandwithdraw.fxml"));
        fixedDeposit = FXMLLoader.load(getClass()
                .getResource("../../view/management/clerksubUI/fixeddeposit.fxml"));
        fixedWithdraw = FXMLLoader.load(getClass()
                .getResource("../../view/management/clerksubUI/fixedwithdraw.fxml"));
        logout = FXMLLoader.load(getClass()
                .getResource("../../view/management/clerksubUI/logout.fxml"));
        register = FXMLLoader.load(getClass()
                .getResource("../../view/management/clerksubUI/register.fxml"));
        searchDetails = FXMLLoader.load(getClass()
                .getResource("../../view/management/clerksubUI/searchdetails.fxml"));
    }

    @FXML
    private void register() {
        vBox.getChildren().clear();
        vBox.getChildren().add(register);
    }

    @FXML
    private void logout() {
        vBox.getChildren().clear();
        vBox.getChildren().add(logout);
    }

    @FXML
    private void fixedDeposit() {
        vBox.getChildren().clear();
        vBox.getChildren().add(fixedDeposit);
    }

    @FXML
    private void fixedWithdraw() {
        vBox.getChildren().clear();
        vBox.getChildren().add(fixedWithdraw);
    }

    @FXML
    private void demandDeposit() {
        vBox.getChildren().clear();
        vBox.getChildren().add(demandDeposit);
    }

    @FXML
    private void demandWithdraw() {
        vBox.getChildren().clear();
        vBox.getChildren().add(demandWithdraw);
    }

    @FXML
    private void searchDetails() {
        vBox.getChildren().clear();
        vBox.getChildren().add(searchDetails);
    }

    @FXML
    private void changePassword() {
        vBox.getChildren().clear();
        vBox.getChildren().add(changePassword);
    }
}
