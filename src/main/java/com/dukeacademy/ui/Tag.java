package com.dukeacademy.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for the tags displayed in QuestionCards.
 */
public class Tag extends UiPart<Region> {
    private static final String FXML = "Tag.fxml";

    @FXML
    private Label label;

    public Tag(String s) {
        super(FXML);
        label.setText(s);
    }
}
