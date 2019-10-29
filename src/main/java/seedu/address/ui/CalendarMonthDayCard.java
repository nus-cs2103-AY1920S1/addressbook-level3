package seedu.address.ui;

import java.time.LocalDate;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.calendar.CalendarEntry;

/**
 * An UI component that displays information of a date.
 */
public class CalendarMonthDayCard extends UiPart<Region> {
    private static final String FXML = "CalendarMonthDayCard.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarMonthDayCard.class);

    @FXML
    private Label date;
    @FXML
    private Label entryNumber;

    public CalendarMonthDayCard(LocalDate date, ObservableList<CalendarEntry> calendarEntries) {
        super(FXML);
        this.date.setWrapText(true);
        this.date.setTextFill(Color.rgb(255, 0, 0));
        this.date.setText(date.getDayOfMonth() + "");
        StringBinding stringBinding = Bindings.size(
            calendarEntries.filtered(calendarEntry -> calendarEntry.isOnDate(date))).asString();
        entryNumber.textProperty().bind(stringBinding);
    }

    public CalendarMonthDayCard() {
        super(FXML);
        this.date.setText("");
        this.entryNumber.setText("");
    }
}
