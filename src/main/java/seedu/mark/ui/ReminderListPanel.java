package seedu.mark.ui;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import seedu.mark.commons.core.LogsCenter;
import seedu.mark.model.reminder.Reminder;
import seedu.mark.storage.JsonMarkStorage;

/**
 * The Reminder List Panel of Mark.
 */
public class ReminderListPanel extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(JsonMarkStorage.class);

    private static final String FXML = "ReminderList.fxml";

    @FXML
    private VBox reminderList;
    private ObservableList<Reminder> reminders;
    private ObservableList<Node> reminderItems;
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool (1);
    Runnable r = () -> showDueReminder();

    public ReminderListPanel(ObservableList<Reminder> reminders) {
        super(FXML);
        reminderList.setBackground(new Background(
                new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.reminders = reminders;
        executor.scheduleAtFixedRate( r , 0L , 5L , TimeUnit.SECONDS);
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

    private long compareTime(LocalDateTime before, LocalDateTime after) {
        return Duration.between(before, after).toHours();
    }

    private void showDueReminder() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("running show");

        for (int i = 0; i < reminders.size(); i++) {
            Reminder reminder = reminders.get(i);
            LocalDateTime remindTime = reminder.getRemindTime();
            System.out.println(compareTime(now, remindTime));
            System.out.println(now.isBefore(remindTime) && compareTime(now, remindTime) < 5);
            if (now.isBefore(remindTime) && compareTime(now, remindTime) < 5) {
                System.out.println(true);
                ReminderWindow window = new ReminderWindow(reminder.getUrl(), reminder.getNote());
                window.show();
            }
        }
    }
}
