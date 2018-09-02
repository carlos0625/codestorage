package cn.edu.ncu.liuqing.banksavingsystem.controllers.operation.adminsubcontroller;

import cn.edu.ncu.liuqing.banksavingsystem.entities.Administrator;
import cn.edu.ncu.liuqing.banksavingsystem.entities.Clerk;
import cn.edu.ncu.liuqing.banksavingsystem.exception.MyException;
import cn.edu.ncu.liuqing.banksavingsystem.tools.Tip;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.util.List;

public class SearchAllClerksController {
    public Administrator administrator = Administrator.getInstance();

    private ObservableList<Clerk> items;


    @FXML
    private TableView tableView;

    @FXML
    private void load() {
        List<Clerk> list = null;
        try {
            list = administrator.queryAll();
        } catch (MyException e) {
            Tip.tips(e.getMessage());
            return;
        }
        items = FXCollections.observableArrayList(list);
        tableView.setItems(items);
    }
}
