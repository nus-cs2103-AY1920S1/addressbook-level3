package com.dukeacademy.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class RunCodeResult extends UiPart<Region> {
    private static final String FXML = "RunCodeResult.fxml";

    @FXML
    private Label hi;

    public RunCodeResult() {
        super(FXML);
    }
}
