package seedu.mark.ui;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.mark.commons.core.LogsCenter;
import seedu.mark.model.reminder.Reminder;
import seedu.mark.storage.JsonMarkStorage;

import java.util.logging.Logger;

public class ReminderListPanel extends UiPart<Region> {
    private static final String FXML = "ReminderList.fxml";

    @FXML
    private VBox reminderList;
    private ObservableList<Reminder> reminders;
    private ObservableList<Node> reminderItems;

    private static final Logger logger = LogsCenter.getLogger(JsonMarkStorage.class);

    public ReminderListPanel(ObservableList<Reminder> reminders) {
        super(FXML);
        this.reminders = reminders;
        this.reminderItems = FXCollections.observableArrayList();
        setReminderListItems();

        logger.info("run init of reminderListView");
        reminders.addListener((ListChangeListener<? super Reminder>) change -> {
            while (change.next()) {
                setReminderListItems();
                reminderList.getChildren().setAll(reminderItems);
            }
        });

        reminderList.getChildren().addAll(reminderItems);
    }

    public void setReminderListItems() {
        reminderItems.clear();

        for (int i = 0; i < reminders.size(); i++) {
            Label reminder = new Label(reminders.get(i).toString());
            reminderItems.add(reminder);
        }
        logger.info("run setting of reminderListItems");
    }
}
