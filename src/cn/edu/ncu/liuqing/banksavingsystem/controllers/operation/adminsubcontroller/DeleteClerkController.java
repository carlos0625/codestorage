package cn.edu.ncu.liuqing.banksavingsystem.controllers.operation.adminsubcontroller;

import cn.edu.ncu.liuqing.banksavingsystem.entities.Administrator;
import cn.edu.ncu.liuqing.banksavingsystem.entities.Clerk;
import cn.edu.ncu.liuqing.banksavingsystem.exception.MyException;
import cn.edu.ncu.liuqing.banksavingsystem.tools.Tip;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class DeleteClerkController {
    public Administrator administrator = Administrator.getInstance();

    @FXML
    private TextField cnoTextField;

    @FXML
    private void delete() {
        Clerk clerk = new Clerk(Short.parseShort(cnoTextField.getText()));
        try {
            administrator.queryInfo(clerk);
        } catch (MyException e) {
            Tip.tips(e.getMessage());
            return;
        }
        administrator.delete(clerk);
        Tip.tips("删除成功！");
    }

    @FXML
    private void reset() {
        cnoTextField.setText("");
    }
}
