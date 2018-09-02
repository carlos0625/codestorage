package cn.edu.ncu.liuqing.banksavingsystem.controllers.operation.clerksubcontroller;

import cn.edu.ncu.liuqing.banksavingsystem.entities.*;
import cn.edu.ncu.liuqing.banksavingsystem.exception.MyException;
import cn.edu.ncu.liuqing.banksavingsystem.tools.FileOP;
import cn.edu.ncu.liuqing.banksavingsystem.tools.Tip;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SearchDetailsController {
    Clerk clerk = new Clerk((short) FileOP.readFromFile());

    @FXML
    private TextField accountNo;

    @FXML
    private TableView<FixedDetails> tableView1;

    private ObservableList<FixedDetails> items1;

    @FXML
    private TableView<DemandDetails> tableView2;

    private ObservableList<DemandDetails> items2;

    @FXML
    private void search() {
        String no = accountNo.getText();
        try {
            if (no.length() == 17) {
                tableView2.setVisible(false);
                FixedAccount fixedAccount = new FixedAccount();
                fixedAccount.setNo(Long.parseLong(no));
                items1 = FXCollections.observableArrayList(clerk.queryDetails(fixedAccount));
                tableView1.setItems(items1);
                tableView1.setVisible(true);
            }
            else if (no.length() == 19) {
                tableView1.setVisible(false);
                DemandAccount demandAccount = new DemandAccount();
                demandAccount.setNo(no);
                items2 = FXCollections.observableArrayList(clerk.queryDetails(demandAccount));
                tableView2.setItems(items2);
                tableView2.setVisible(true);
            }
            else
                Tip.tips("账号不合法!");
        } catch (MyException e) {
            Tip.tips(e.getMessage());
        }
    }

    @FXML
    private void reset() {
        accountNo.setText("");
        tableView1.setVisible(false);
        tableView2.setVisible(false);
    }
}
