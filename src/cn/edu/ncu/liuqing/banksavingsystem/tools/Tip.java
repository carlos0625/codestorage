package cn.edu.ncu.liuqing.banksavingsystem.tools;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * 小窗口提示功能
 */
public class Tip {
    public static void tips(String message) {
        Label label = new Label(message);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(label);
        Stage stage = new Stage();
        stage.setScene(new Scene(stackPane,200,100));
        stage.show();
    }
}
