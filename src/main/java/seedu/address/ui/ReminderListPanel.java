package seedu.address.ui;

import static java.util.stream.Collectors.toList;

import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.calendar.CalendarEntry;
import seedu.address.model.calendar.Reminder;

/**
 * Panel containing the list of reminders.
 */
public class ReminderListPanel extends UiPart<Region> {
    private static final String FXML = "ReminderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ReminderListPanel.class);

    @FXML
    private ListView<Reminder> reminderListView;

    public ReminderListPanel(ObservableList<CalendarEntry> calendarEntries) {
        super(FXML);
        updateReminderListView(calendarEntries);
        calendarEntries.addListener(new ListChangeListener<CalendarEntry>() {
            @Override
            public void onChanged(Change<? extends CalendarEntry> c) {
                updateReminderListView(calendarEntries);
            }
        });

    }

    /**
     * Update reminder list view.
     */
    private void updateReminderListView(ObservableList<CalendarEntry> calendarEntries) {
        ObservableList<Reminder> reminderList = calendarEntries.stream()
                .filter(calendarEntry -> calendarEntry instanceof Reminder)
                .map(calendarEntry -> (Reminder) calendarEntry)
                .collect(Collectors.collectingAndThen(toList(), l -> FXCollections.observableArrayList(l)));
        reminderListView.setItems(reminderList);
        reminderListView.setCellFactory(listView -> new ReminderListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Reminder} using a {@code ReminderCard}.
     */
    class ReminderListViewCell extends ListCell<Reminder> {
        @Override
        protected void updateItem(Reminder reminder, boolean empty) {
            super.updateItem(reminder, empty);

            if (empty || reminder == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ReminderCard(reminder).getRoot());
            }
        }
    }
}
