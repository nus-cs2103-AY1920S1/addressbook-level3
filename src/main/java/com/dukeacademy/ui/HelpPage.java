package com.dukeacademy.ui;

import javafx.scene.layout.Region;

/**
 * Controller class for Help Page. Help Page acts as a summarised User Guide within the application itself
 * as it lists out and describes all the commands available in Duke Academy.
 */
public class HelpPage extends UiPart<Region> {
    private static final String FXML = "HelpPage.fxml";

    public HelpPage() {
        super(FXML);
    }

}
