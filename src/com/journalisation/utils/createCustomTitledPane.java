package com.journalisation.utils;

import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;

public class createCustomTitledPane extends TitledPane {
    private String title,label;
    private Pane content;

    public createCustomTitledPane() {
    }

    public createCustomTitledPane(String s, Node node) {
        super(s, node);
        content=(Pane)node;
    }
}
