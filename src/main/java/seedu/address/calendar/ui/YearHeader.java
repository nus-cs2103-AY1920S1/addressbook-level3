package seedu.address.calendar.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

public class YearHeader extends UiPart<Region> {
    private static final String FXML = "CalendarYearHeader.fxml";

    @FXML
    private Label yearHeader;

    public YearHeader(int year) {
        super(FXML);
        setYearHeader(year);
    }

    public void setYearHeader(int year) {
        yearHeader.setText(String.format("%04d", year));
    }
}
