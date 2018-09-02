package cn.edu.ncu.liuqing.banksavingsystem.controllers.operation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AdministratorController {
    private Parent loginClerk;
    private Parent deleteClerk;
    private Parent searchAllClerks;
    private Parent searchInfo;
    private Parent changePassword;

    @FXML
    private VBox vBox;

    @FXML
    public void initialize() throws IOException {
        loginClerk = FXMLLoader.load(getClass().getResource("../../view/management/adminsubUI/loginclerk.fxml"));
        deleteClerk = FXMLLoader.load(getClass().getResource("../../view/management/adminsubUI/deleteclerkUI.fxml"));
        searchAllClerks = FXMLLoader.load(getClass().getResource("../../view/management/adminsubUI/searchallclerks.fxml"));
        searchInfo = FXMLLoader.load(getClass().getResource("../../view/management/adminsubUI/searchinfo.fxml"));
        changePassword = FXMLLoader.load(getClass().getResource("../../view/management/adminsubUI/changepassword.fxml"));
    }

    @FXML
    private void loginClerk() {
        vBox.getChildren().clear();
        vBox.getChildren().add(loginClerk);
    }

    @FXML
    private void deleteClerk() {
        vBox.getChildren().clear();
        vBox.getChildren().add(deleteClerk);
    }

    @FXML
    private void searchAllClerks() {
        vBox.getChildren().clear();
        vBox.getChildren().add(searchAllClerks);
    }

    @FXML
    private void searchInfo() {
        vBox.getChildren().clear();
        vBox.getChildren().add(searchInfo);
    }

    @FXML
    private void changePassword() {
        vBox.getChildren().clear();
        vBox.getChildren().add(changePassword);
    }
}
