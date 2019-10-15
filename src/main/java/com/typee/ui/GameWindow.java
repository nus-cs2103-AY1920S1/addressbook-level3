package com.typee.ui;

import javafx.scene.layout.Region;

/**
 * Controller class which handles Game Window.
 */
public class GameWindow extends UiPart<Region> {
    public static final String FXML = "GameWindow.fxml";

    public GameWindow() {
        super(FXML);
    }
}
