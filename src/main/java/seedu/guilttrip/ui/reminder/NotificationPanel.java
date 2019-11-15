package seedu.guilttrip.ui.reminder;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.model.reminders.messages.Notification;
import seedu.guilttrip.ui.UiPart;


/**
 * Side panel for reminders.
 */
public class NotificationPanel extends UiPart<Region> {
    private static final String FXML = "reminder/NotificationListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(NotificationPanel.class);

    @FXML
    private ListView<Notification> notificationListView;

    public NotificationPanel(ObservableList<Notification> notifications) {
        super(FXML);
        notificationListView.setItems(notifications);
        notificationListView.setCellFactory(listView -> new ReminderListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class ReminderListViewCell extends ListCell<Notification> {
        @Override
        protected void updateItem(Notification notification, boolean empty) {
            super.updateItem(notification, empty);
            if (empty || notification == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new NotificationCard(notification, getIndex() + 1).getRoot());
            }
        }
    }
}

