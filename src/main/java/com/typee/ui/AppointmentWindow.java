package com.typee.ui;

import javafx.scene.layout.Region;

/**
 * Controller class for Appointment Window
 */
public class AppointmentWindow extends UiPart<Region> {
    public static final String FXML = "AppointmentWindow.fxml";

    public AppointmentWindow() {
        super(FXML);
    }
}
