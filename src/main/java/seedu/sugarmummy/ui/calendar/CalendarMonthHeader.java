package seedu.sugarmummy.ui.calendar;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.sugarmummy.ui.UiPart;

/**
 * An UI component that displays week day in a monthly calendar .
 */
public class CalendarMonthHeader extends UiPart<Region> {
    private static final String FXML = "CalendarMonthHeader.fxml";
    private DayOfWeek day;

    @FXML
    private Label dayOfWeek;

    public CalendarMonthHeader(int day) {
        super(FXML);
        this.day = DayOfWeek.of(day);
        dayOfWeek.setText(this.day.getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
        this.getRoot().setStyle("-fx-background-color: #818A9E");
        this.dayOfWeek.setStyle("-fx-text-fill: white");
    }
}
