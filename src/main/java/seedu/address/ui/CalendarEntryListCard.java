package seedu.address.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.calendar.CalendarEntry;

/**
 * An UI component that displays information of a {@code CalendarEntry} on a certain date.
 */
public class CalendarEntryListCard extends UiPart<Region> {
    private static final String FXML = "CalendarEntryListCard.fxml";

    public final CalendarEntry calendarEntry;

    @FXML
    private Label dateTime;

    @FXML
    private Label description;

    public CalendarEntryListCard(CalendarEntry calendarEntry) {
        super(FXML);
        this.dateTime.setWrapText(true);
        this.dateTime.setText(DateTimeFormatter.ofPattern("HH:mm").format(calendarEntry.getTime()));
        this.calendarEntry = calendarEntry;
        description.setText(calendarEntry.getDescription().toString());
    }

}
