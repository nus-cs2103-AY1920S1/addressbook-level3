package seedu.address.ui.calendar;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

/**
 * The type Controller.
 */
public class Controller {

    // Get the pane to put the calendar on
    @FXML private Pane calendarPane;

    public Pane getCalendarPane() {
        return this.calendarPane;
    }

}
