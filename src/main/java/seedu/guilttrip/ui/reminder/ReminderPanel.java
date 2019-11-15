package seedu.guilttrip.ui.reminder;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.entry.Income;
import seedu.guilttrip.model.entry.Wish;
import seedu.guilttrip.model.reminders.EntryReminder;
import seedu.guilttrip.model.reminders.GeneralReminder;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.ui.UiPart;
import seedu.guilttrip.ui.condition.ConditionCard;

/**
 * Side panel for reminders.
 */
public class ReminderPanel extends UiPart<Region> {
    private static final String FXML = "reminder/ReminderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ReminderPanel.class);
    private Reminder reminderSelected;

    @FXML
    private ListView<Reminder> reminderListView1;
    @FXML
    private ListView<ReminderListEntry> reminderListView2;

    public ReminderPanel(ObservableList<Reminder> remindersList) {
        super(FXML);
        reminderListView1.setItems(remindersList);
        reminderListView1.setCellFactory(listView -> new ReminderListViewCell());
    }

    /**
     * Updates reminder pannel with details of reminder selected.
     */
    public void updateReminderSelected(Reminder reminder) {
        if (reminder != null) {
            reminderSelected = reminder;
            if (reminder instanceof GeneralReminder) {
                GeneralReminder generalReminder = (GeneralReminder) reminder;
                ObservableList<ReminderListEntry> conditionList =
                        FXCollections.observableArrayList(generalReminder.getConditions());
                reminderListView2.setItems(conditionList);
                reminderListView2.setCellFactory(listView -> new ReminderDetailsListViewCell());
            } else {
                EntryReminder entryReminder = (EntryReminder) reminder;
                ObservableList<ReminderListEntry> reminderList =
                        FXCollections.observableArrayList(List.of(entryReminder));
                reminderListView2.setItems(reminderList);
                reminderListView2.setCellFactory(listView -> new ReminderDetailsListViewCell());
            }
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
    class ReminderDetailsListViewCell extends ListCell<ReminderListEntry> {
        @Override
        protected void updateItem(ReminderListEntry entry, boolean empty) {
            super.updateItem(entry, empty);

            if (empty || entry == null) {
                setGraphic(null);
                setText(null);
            } else if (entry instanceof Condition) {
                setGraphic(new ConditionCard((Condition) entry, getIndex() + 1).getRoot());
            } else if (entry instanceof EntryReminder) {
                EntryReminder reminder = (EntryReminder) entry;
                if (reminder.getEntry() instanceof Expense) {
                    setGraphic(new ExpenseReminderCard(reminder, getIndex() + 1).getRoot());
                } else if (reminder.getEntry() instanceof Income) {
                    setGraphic(new IncomeReminderCard(reminder, getIndex() + 1).getRoot());
                } else if (reminder.getEntry() instanceof Wish) {
                    setGraphic(new WishReminderCard(reminder, getIndex() + 1).getRoot());
                }
            }
        }
    }
}

