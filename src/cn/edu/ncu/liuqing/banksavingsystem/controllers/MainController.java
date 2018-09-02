package cn.edu.ncu.liuqing.banksavingsystem.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {
    @FXML
    private StackPane stackPane;

    private Parent administratorUI;
    private Parent clerkUI;

    public void initialize() throws IOException {
        administratorUI = FXMLLoader.load(getClass()
                .getResource("../view/login/administratorlogin.fxml"));
        clerkUI = FXMLLoader.load(getClass()
                .getResource("../view/login/clerklogin.fxml"));
    }

    @FXML
    private void administratorLogin() {
        stackPane.getChildren().clear();
        stackPane.getChildren().add(administratorUI);
    }

    @FXML
    private void clerkLogin() {
        stackPane.getChildren().clear();
        stackPane.getChildren().add(clerkUI);
    }
}
