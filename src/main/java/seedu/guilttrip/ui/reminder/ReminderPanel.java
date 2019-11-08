package seedu.guilttrip.ui.reminder;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.ui.UiPart;


/**
 * Side panel for reminders.
 */
public class ReminderPanel extends UiPart<Region> {
    private static final String FXML = "/reminder/ReminderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ReminderPanel.class);

    @FXML
    private ListView<Reminder> reminderListView1;
    @FXML
    private ListView<Reminder> reminderListView2;

    public ReminderPanel(ObservableList<Reminder> remindersList) {
        super(FXML);
        reminderListView1.setItems(remindersList);
        reminderListView1.setCellFactory(listView -> new ReminderListViewCell());
        reminderListView2.setItems(remindersList);
        reminderListView2.setCellFactory(listView -> new ReminderListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code reminder} using a {@code ReminderCard}.
     */
    class ReminderListViewCell extends ListCell<Reminder> {
        @Override
        protected void updateItem(Reminder reminder, boolean empty) {
            super.updateItem(reminder, empty);
            if (empty || reminder == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ReminderCard(reminder, getIndex() + 1).getRoot());
            }
        }
    }
}

