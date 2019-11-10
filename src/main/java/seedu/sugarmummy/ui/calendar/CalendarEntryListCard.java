package seedu.sugarmummy.ui.calendar;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.sugarmummy.model.calendar.CalendarEntry;
import seedu.sugarmummy.model.calendar.Event;
import seedu.sugarmummy.model.calendar.Reminder;
import seedu.sugarmummy.ui.UiPart;

/**
 * An UI component that displays information of a {@code CalendarEntry} on a certain date.
 */
public class CalendarEntryListCard extends UiPart<Region> {
    private static final String FXML = "CalendarEntryListCard.fxml";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public final CalendarEntry calendarEntry;

    @FXML
    private Label dateTime;

    @FXML
    private Label description;

    public CalendarEntryListCard(CalendarEntry calendarEntry) {
        super(FXML);
        this.dateTime.setWrapText(true);
        this.calendarEntry = calendarEntry;
        if (calendarEntry instanceof Event) {
            initializeEvent((Event) calendarEntry);
        } else {
            initializeReminder((Reminder) calendarEntry);
        }
    }

    /**
     * Initializes the card for an event entry.
     */
    private void initializeEvent(Event event) {
        if (event.getEndingDateTime().isPresent()) {
            if (event.getEndingDateTime().get().getDate().equals(event.getDate())) {
                this.dateTime.setText(dateTimeFormatter.format(event.getTime()) + " - "
                        + dateTimeFormatter.format(event.getEndingDateTime().get().getTime()));
            } else {
                this.dateTime.setText(dateTimeFormatter.format(event.getTime()) + " - "
                        + event.getEndingDateTime().get());
            }
        } else {
            this.dateTime.setText(DateTimeFormatter.ofPattern("HH:mm").format(calendarEntry.getTime()));
        }
        description.setText("Event: " + calendarEntry.getDescription().toString());
    }

    /**
     * Initializes the card for an reminder entry.
     */
    private void initializeReminder(Reminder reminder) {
        this.dateTime.setText(dateTimeFormatter.format(reminder.getTime()));
        description.setText("Reminder: " + reminder.getDescription());
    }
}
