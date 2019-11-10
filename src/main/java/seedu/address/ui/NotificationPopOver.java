package seedu.address.ui;

import org.controlsfx.control.PopOver;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import seedu.address.MainApp;
import seedu.address.model.notif.Notif;

//@@author shaoyi1997
/**
 * Represents the notification popover that contains the list of active notifications.
 */
public class NotificationPopOver {

    private PopOver notificationPopOver;
    private ObservableList<Notif> notifObservableList;

    public NotificationPopOver(ObservableList<Notif> notifList) {
        this.notificationPopOver = new PopOver();
        this.notifObservableList = notifList;
        setUpPopOver(notifList);
    }

    /**
     * Sets up the Ui properties of the PopOver
     */
    private void setUpPopOver(ObservableList<Notif> notifList) {
        notificationPopOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        notificationPopOver.setOpacity(0.95);
        notificationPopOver.setHeaderAlwaysVisible(true);
        notificationPopOver.setCloseButtonEnabled(false);
        notificationPopOver.setTitle("To Contact Police:");
        notificationPopOver.getRoot().getStylesheets().add(MainApp.class.getResource("/view/PopOver.css")
            .toExternalForm());
        notificationPopOver.getRoot().getStyleClass().add("popover");
        initContentsOfPopOver(notifList);
    }

    /**
     * Fills the popover with a listview of the notifications.
     */
    private void initContentsOfPopOver(ObservableList<Notif> notifList) {
        ListView<Notif> notifListView = new ListView<>();
        notifListView.setItems(notifList);
        notifListView.setCellFactory(listView -> new NotifListCell());
        notifListView.setPrefSize(200, 200);
        notifListView.setStyle("-fx-padding: 9 0 0 0");
        notificationPopOver.setContentNode(notifListView);
    }

    /**
     * Displays the popover.
     */
    public void show(Node node) {
        notificationPopOver.show(node);
    }

    /**
     * Custom {@code ListCell} that displays the body ID of a {@code Notif}.
     */
    class NotifListCell extends ListCell<Notif> {
        @Override
        public void updateItem(Notif notif, boolean empty) {
            super.updateItem(notif, empty);

            if (empty || notif == null) {
                setGraphic(null);
                setText(null);
            } else {
                StackPane pane = new StackPane();
                Label bodyIdLabel = new Label(notif.getBody().getIdNum().toString());
                bodyIdLabel.getStyleClass().add("selectedBodyId");
                pane.setStyle("-fx-padding: 6 0 9 0");
                pane.getChildren().add(bodyIdLabel);
                setGraphic(pane);
            }
        }
    }
}
//@@author
