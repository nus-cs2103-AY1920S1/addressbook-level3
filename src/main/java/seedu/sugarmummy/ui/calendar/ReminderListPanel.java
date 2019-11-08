package seedu.sugarmummy.ui.calendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.sugarmummy.commons.core.LogsCenter;
import seedu.sugarmummy.model.calendar.CalendarEntry;
import seedu.sugarmummy.model.calendar.Reminder;
import seedu.sugarmummy.model.time.DateTime;
import seedu.sugarmummy.model.time.Today;
import seedu.sugarmummy.ui.UiPart;

/**
 * Panel containing the list of reminders.
 */
public class ReminderListPanel extends UiPart<Region> {
    private static final String FXML = "ReminderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ReminderListPanel.class);

    @FXML
    private VBox reminderListVBox;
    @FXML
    private Label today;
    @FXML
    private ListView<CalendarEntry> reminderListView;
    @FXML
    private ListView<CalendarEntry> missedReminders;

    public ReminderListPanel(ObservableList<CalendarEntry> reminders, ObservableList<CalendarEntry> calendarEntries,
                             Today today) {
        super(FXML);
        initializeTodayDate(today);
        initializeMissedReminders(calendarEntries);
        initializePastReminders(reminders);
    }

    /**
     * Initializes today label.
     * Binds its value to a today object.
     */
    private void initializeTodayDate(Today today) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy MMM dd EEE");
        StringBinding stringBinding = Bindings.createStringBinding(()->
                dateTimeFormatter.format(today.dateProperty().get()), today.dateProperty());
        this.today.textProperty().bind(stringBinding);
        this.today.setStyle("-fx-background-color: #818A9E; -fx-text-fill: white; ");
    }

    /**
     * Initializes missed reminder list.
     */
    private void initializeMissedReminders(ObservableList<CalendarEntry> calendarEntries) {
        DateTime start = new DateTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0)));
        DateTime end = new DateTime(LocalDateTime.now().minusMinutes(1));
        missedReminders.setItems(calendarEntries.filtered(calendarEntry -> calendarEntry.isBetween(start, end)));
        missedReminders.setCellFactory(listView -> new ReminderListViewCell());
        missedReminders.prefHeightProperty().bind(Bindings.size(
                calendarEntries.filtered(calendarEntry -> calendarEntry.isBetween(start, end))).multiply(54));
    }

    /**
     * Initializes past reminder list.
     */
    private void initializePastReminders(ObservableList<CalendarEntry> reminders) {
        reminderListView.setItems(reminders);
        reminderListView.setCellFactory(listView -> new ReminderListViewCell());
        reminderListView.prefHeightProperty().bind(Bindings.size(reminders).multiply(54));
        reminderListView.setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: #818A9E");
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Reminder} using a {@code ReminderCard}.
     */
    class ReminderListViewCell extends ListCell<CalendarEntry> {
        @Override
        protected void updateItem(CalendarEntry calendarEntry, boolean empty) {
            super.updateItem(calendarEntry, empty);

            if (empty || calendarEntry == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ReminderCard((Reminder) calendarEntry).getRoot());
            }
        }
    }
}
