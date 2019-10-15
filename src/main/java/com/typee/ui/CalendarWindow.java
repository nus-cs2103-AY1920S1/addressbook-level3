package com.typee.ui;

import javafx.scene.layout.Region;

/**
 * Controller class which handles Calendar Window.
 */
public class CalendarWindow extends UiPart<Region> {
    public static final String FXML = "CalendarWindow.fxml";

    public CalendarWindow() {
        super(FXML);
    }
}
