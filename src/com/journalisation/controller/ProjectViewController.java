package com.journalisation.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.journalisation.dao.bean.Livres;

public class ProjectViewController extends ControllerManager {

    @FXML
    private Label title;

    @FXML
    private Label subtitle;

    @FXML
    private Label created;

    private Livres livre;
    @Override
    protected void init() {
        livre=new Livres();
    }

    public Livres getLivre() {
        return livre;
    }

    public void setLivre(Livres livre) {
        this.livre = livre;
        title.setText(livre.getTitre());
        subtitle.setText(livre.getSoustitre());
        created.setText(livre.getReceipt().toString());
    }
}
