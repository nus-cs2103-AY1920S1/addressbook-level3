package seedu.address.ui;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * An UI component that displays week day in a monthly calendar .
 */
public class CalendarMonthHeader extends UiPart<Node> {
    private static final String FXML = "CalendarMonthHeader.fxml";
    private DayOfWeek day;

    @FXML
    private Label dayOfWeek;

    public CalendarMonthHeader(int day) {
        super(FXML);
        this.day = DayOfWeek.of(day);
        dayOfWeek.setText(this.day.getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
    }
}
