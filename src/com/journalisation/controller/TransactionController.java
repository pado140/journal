package com.journalisation.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import com.journalisation.dao.bean.Transaction;
import com.journalisation.dao.bean.Users;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;

import java.time.LocalDateTime;

public class TransactionController extends ControllerManager {
    @FXML
    private TableView<Transaction> transactions;

    @FXML
    private TableColumn<Transaction, String> transac;

    @FXML
    private TableColumn<Transaction, LocalDateTime> date;

    @FXML
    private TableColumn<Transaction, Users> user;

    private final ObservableList<Transaction> transaction = FXCollections.observableArrayList();
    private ObservableList<Transaction> transactionList =DAOFactory.createModel(DAOName.transac).selectAll();

    @Override
    protected void init() {
        transaction.addAll(transactionList);
        transac.setCellValueFactory(data->data.getValue().actionProperty());
        date.setCellValueFactory(data->data.getValue().createdProperty());
        user.setCellValueFactory(data->data.getValue().usersProperty());
        transactions.itemsProperty().bind(new SimpleObjectProperty<>(transaction));
    }

    @FXML
    void filter(KeyEvent event) {
        transaction.clear();
        String value=((TextField)event.getSource()).getText().trim();
        transactionList.forEach(tr->{
            if(tr.getUsers().getUsername().contains(value)||tr.getAction().contains(value)){
                transaction.add(tr);
            }
        });
    }
}
