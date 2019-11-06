package seedu.sugarmummy.ui.calendar;

import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.sugarmummy.commons.core.LogsCenter;
import seedu.sugarmummy.model.calendar.CalendarEntry;
import seedu.sugarmummy.model.calendar.Reminder;
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
    private ListView<CalendarEntry> reminderListView;

    public ReminderListPanel(ObservableList<CalendarEntry> reminders) {
        super(FXML);
        reminderListView.setItems(reminders);
        reminderListView.setCellFactory(listView -> new ReminderListViewCell());
        reminderListView.prefHeightProperty().bind(Bindings.size(reminders).multiply(54));
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
