package seedu.address.ui;

import java.util.logging.Logger;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.notif.Notif;

//@@author shaoyi1997
/**
 * Singleton notification button to open up the notification popover containing the all {@code notifs}.
 */

public class NotificationButton extends UiPart<Region> {

    private static NotificationButton notificationButton = null;
    private static NotificationPopOver notificationPopOver;
    private static TranslateTransition translateTransition;
    private static final Logger logger = LogsCenter.getLogger(NotificationButton.class);
    private static final String FXML = "NotificationButton.fxml";

    @FXML
    private ImageView buttonIcon;

    @FXML
    private Button notifButton;

    @FXML
    private StackPane buttonPane;

    private Label iconNumber;

    private NotificationButton(ObservableList<Notif> filteredListNotif) {
        super(FXML);
        buttonIcon.setImage(new Image(MainApp.class.getResourceAsStream("/images/bell_icon.png")));
        notifButton.setGraphic(buttonIcon);
        notifButton.setStyle("-fx-border-width: 0");
        initIconNumber(filteredListNotif);
        initPopOver(filteredListNotif);
    }

    /**
     * Returns the single instance of the notification button.
     */
    public static NotificationButton getInstance(ObservableList<Notif> filteredListNotif) {
        if (notificationButton == null) {
            notificationButton = new NotificationButton(filteredListNotif);
        }
        return notificationButton;
    }

    /**
     * Displays the notification popover to the user.
     */
    public void showNotifications() {
        notificationPopOver.show(notifButton);
    }

    /**
     * Initializes the icon number which indicates the number of active notifications.
     */
    private void initIconNumber(ObservableList<Notif> filteredListNotif) {
        iconNumber = new Label();
        iconNumber.textProperty().bind(Bindings.size(filteredListNotif).asString());
        iconNumber.getStyleClass().add("notificationButtonLabel");
        bindIconNumberToStackPane();
        addJumpingAnimation();
        if (filteredListNotif.size() != 0) {
            translateTransition.play();
        }
        iconNumber.textProperty().addListener((observable, old, current) -> {
            if (current.equals("0")) {
                translateTransition.stop();
            } else {
                translateTransition.play();
            }
        });
    }

    /**
     * Initialises the popover that contains a listview of notifications.
     */
    private void initPopOver(ObservableList<Notif> filteredListNotif) {
        notificationPopOver = new NotificationPopOver(filteredListNotif);
        notifButton.setOnMouseClicked(mouseEvent -> {
            //Show PopOver when button is clicked
            notificationPopOver.show(notifButton);
        });
    }

    /**
     * Sets up the jumping animation for the icon number.
     */
    private void addJumpingAnimation() {
        translateTransition = new TranslateTransition(Duration.millis(400), iconNumber);
        translateTransition.setFromY(0.0);
        translateTransition.setToY(-4.0);
        translateTransition.setCycleCount(-1);
        translateTransition.setAutoReverse(true);
        translateTransition.setInterpolator(Interpolator.EASE_BOTH);
    }

    /**
     * Positions the icon number relative to its parent stack pane.
     */
    private void bindIconNumberToStackPane() {
        buttonPane.setAlignment(iconNumber, Pos.TOP_RIGHT);
        buttonPane.setMargin(iconNumber, new Insets(6, 2, 0, 0));
        buttonPane.getChildren().addAll(iconNumber);
    }

}
//@@author
