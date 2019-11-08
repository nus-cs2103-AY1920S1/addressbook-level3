package com.typee.ui.game;

import com.typee.ui.UiPart;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

/**
 * Controller class which handles Start window.
 */
public class StartWindow extends UiPart<Region> {

    public static final String FXML = "StartWindow.fxml";

    @FXML
    private AnchorPane startWindow;

    public StartWindow() {
        super(FXML);
    }

    @FXML
    private void handleStart() {
        GameWindow gameWindow = GameWindow.getInstance();
        gameWindow.show();
    }
}
