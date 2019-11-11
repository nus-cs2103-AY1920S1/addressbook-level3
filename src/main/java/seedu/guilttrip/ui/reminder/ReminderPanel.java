package seedu.guilttrip.ui.reminder;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.reminders.GeneralReminder;
import seedu.guilttrip.model.reminders.IewReminder;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.ui.UiPart;
import seedu.guilttrip.ui.condition.ConditionCard;

/**
 * Side panel for reminders.
 */
public class ReminderPanel extends UiPart<Region> {
    private static final String FXML = "/reminder/ReminderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ReminderPanel.class);
    private Reminder reminderSelected;

    @FXML
    private ListView<Reminder> reminderListView1;
    @FXML
    private ListView<Condition> reminderListView2;

    public ReminderPanel(ObservableList<Reminder> remindersList) {
        super(FXML);
        reminderListView1.setItems(remindersList);
        reminderListView1.setCellFactory(listView -> new ReminderListViewCell());
    }

    public void updateReminderSelected(Reminder reminder) {
        if (reminder != null) {
            reminderSelected = reminder;
            if (reminder instanceof GeneralReminder) {
                GeneralReminder generalReminder = (GeneralReminder) reminder;
                ObservableList<Condition> conditionList = FXCollections.observableArrayList(generalReminder.getConditions());
                reminderListView2.setItems(conditionList);
                reminderListView2.setCellFactory(listView -> new ConditionsListViewCell());
            }
        } else {
            reminderListView2.getItems().clear();
        }
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

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Condition} using a {@code ConditionCard}.
     */
    class ConditionsListViewCell extends ListCell<Condition> {
        @Override
        protected void updateItem(Condition condition, boolean empty) {
            super.updateItem(condition, empty);

            if (empty || condition == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ConditionCard(condition, getIndex() + 1).getRoot());
            }
        }
    }
}

