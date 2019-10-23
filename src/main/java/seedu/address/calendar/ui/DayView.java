package seedu.address.calendar.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

public class DayView extends UiPart<Region> {
    private static final String FXML = "CalendarDayView.fxml";
    @FXML
    private Label dayOfMonth;

    public DayView(int date) {
        super(FXML);
        dayOfMonth.setText(Integer.toString(date));
    }
}
