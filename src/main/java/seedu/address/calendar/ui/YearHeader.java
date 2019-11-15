package seedu.address.calendar.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.calendar.model.date.Year;
import seedu.address.ui.UiPart;

/**
 * Represents a year header.
 */
public class YearHeader extends UiPart<Region> {
    private static final String FXML = "CalendarYearHeader.fxml";

    @FXML
    private Label yearHeader;

    public YearHeader(Year year) {
        super(FXML);
        setYearHeader(year);
    }

    public void setYearHeader(Year year) {
        yearHeader.setText(year.toString());
    }
}
