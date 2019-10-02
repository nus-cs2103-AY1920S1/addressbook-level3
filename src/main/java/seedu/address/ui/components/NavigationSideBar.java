package seedu.address.ui.components;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

public class NavigationSideBar extends UiPart<Region> {

    private static final String FXML = "Sidebar.fxml";

    public NavigationSideBar() {
        super(FXML);
    }

    @FXML
    private void handleButton1() {
        // switch to trip planner page
    }

    @FXML
    private void handleButton2() {
        // switch to bookings page
    }
}
