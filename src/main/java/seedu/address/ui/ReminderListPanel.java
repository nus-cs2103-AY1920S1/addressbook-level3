package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.reminder.Reminder;

/**
 * Panel containing the list of earnings.
 */
public class ReminderListPanel extends UiPart<Region> {
    private static final String FXML = "ReminderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ReminderListPanel.class);

    @FXML
    private ListView<Reminder> reminderListView;

    public ReminderListPanel(ObservableList<Reminder> remindersList) {
        super(FXML);
        reminderListView.setItems(remindersList);
        reminderListView.setCellFactory(listView -> new ReminderListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Reminders} using a {@code RemindersCard}.
     */
    class ReminderListViewCell extends ListCell<Reminder> {
        @Override
        protected void updateItem(Reminder reminders, boolean empty) {
            super.updateItem(reminders, empty);

            if (empty || reminders == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ReminderCard(reminders, getIndex() + 1).getRoot());
            }
        }
    }

}
