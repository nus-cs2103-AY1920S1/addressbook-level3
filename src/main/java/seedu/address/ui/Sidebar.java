package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;

import java.net.URL;

public class Sidebar extends UiPart<Region> {

    private static final String FXML = "Sidebar.fxml";

    public Sidebar() {
        super(FXML);
    }

    @javafx.fxml.FXML
    private void handleButton1() {
        // switch to trip planner page
    }

    @FXML
    private void handleButton2() {
        // switch to bookings page
    }
}
