package seedu.mark.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
    @FXML
    private VBox panel;
    @FXML
    private Label title;
    @FXML
    private ScrollPane scrollPane;
    private ObservableList<Reminder> reminders;
    private ObservableList<Node> reminderItems;


    public ReminderListPanel(ObservableList<Reminder> reminders) {
        super(FXML);
        reminderList.setBackground(new Background(
                new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
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
        title.setStyle("-fx-background-color: #07847A; -fx-text-fill: white; -fx-padding: 5px;"
                );
        title.setMinWidth(Region.USE_PREF_SIZE);
        scrollPane.setStyle("-fx-border-color: #383838 -fx-cyan-line-color #07847A #383838; " +
                "-fx-background-color: #1d1d1d;");
        reminderList.getChildren().addAll(reminderItems);

    }



    public void setReminderListItems() {
        reminderItems.clear();

        for (int i = 0; i < reminders.size(); i++) {
            String index = String.format("%d. ", i + 1);
            Label reminder = new Label(index + reminders.get(i).toString());
            reminder.setStyle("-fx-text-fill: white; -fx-padding: 2px;");
            reminder.setFont(new Font("Arial", 18));
            reminder.setMinWidth(Region.USE_PREF_SIZE);
            reminderItems.add(reminder);
        }
        logger.info("run setting of reminderListItems");
    }


}
