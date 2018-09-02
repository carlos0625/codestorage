package cn.edu.ncu.liuqing.banksavingsystem.controllers.operation.adminsubcontroller;

import cn.edu.ncu.liuqing.banksavingsystem.entities.Administrator;
import cn.edu.ncu.liuqing.banksavingsystem.entities.Clerk;
import cn.edu.ncu.liuqing.banksavingsystem.exception.MyException;
import cn.edu.ncu.liuqing.banksavingsystem.tools.Tip;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SearchInfoController {
    public Administrator administrator = Administrator.getInstance();

    @FXML
    private TextField idTextField;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label nameLabel;

    @FXML
    private Label idCardLabel;

    @FXML
    private Label inTimeLabel;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Label addressLabel;

    private boolean isNumber(String no) {
        for (int i = 0; i < no.length(); i++) {
            if (!Character.isDigit(no.charAt(i)))
                return false;
        }
        return true;
    }

    @FXML
    private void search() {
        String no = idTextField.getText();
        if (no.length() != 5 || !isNumber(no)) {
            Tip.tips("工号不合法！");
        } else {
            Clerk clerk = new Clerk(Short.parseShort(no));
            try {
                administrator.queryInfo(clerk);
            } catch (MyException e) {
                Tip.tips("该员工不存在！");
                return;
            }
            anchorPane.setVisible(true);
            nameLabel.setText(clerk.getName());
            idCardLabel.setText(clerk.getIdCardNo());
            inTimeLabel.setText(clerk.getInTime().toString());
            phoneNumberLabel.setText(clerk.getPhoneNumber() + "");
            addressLabel.setText(clerk.getAddress());
        }
    }

    @FXML
    private void reset() {
        idTextField.setText("");
        nameLabel.setText("");
        idCardLabel.setText("");
        inTimeLabel.setText("");
        phoneNumberLabel.setText("");
        addressLabel.setText("");
        anchorPane.setVisible(false);
    }
}
