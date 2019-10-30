package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;

//@@ author shaoyi1997
/**
 * Singleton notification button to open up alerts view.
 */
public class NotificationButton extends UiPart<Region> {

    private static NotificationButton notificationButton = null;
    private static NotificationPopOver notificationPopOver;
    private static final Logger logger = LogsCenter.getLogger(NotificationButton.class);
    private static final String FXML = "NotificationButton.fxml";

    @FXML
    private ImageView buttonIcon;

    @FXML
    private Button notifButton;

    @FXML
    private StackPane buttonPane;

    private Label iconNumber;

    private NotificationButton(Logic logic) {
        super(FXML);
        notifButton.setGraphic(buttonIcon);
        notifButton.setStyle("-fx-border-width: 0");
        initIconNumber();
        initPopOver(logic);
    }

    /**
     * Returns the single instance of the notification button.
     */
    public static NotificationButton getInstanceOfNotifButton(Logic logic) {
        if (notificationButton == null) {
            notificationButton = new NotificationButton(logic);
        }
        return notificationButton;
    }

    /**
     * Returns the single instantiated instance of the notification button.
     * Precondition: {@code notificationButton} is already instantiated.
     */
    public static NotificationButton getInstantiatedNotifButton() {
        requireNonNull(notificationButton);
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
    private void initIconNumber() {
        iconNumber = new Label();
        iconNumber.setText("0");
        iconNumber.getStyleClass().add("notificationButtonLabel");
        addJumpingAnimation();
        bindIconNumberToStackPane();
    }

    /**
     * Initialises the popover that contains a listview of notifications.
     */
    private void initPopOver(Logic logic) {
        notificationPopOver = new NotificationPopOver(logic.getFilteredNotifList());
        notifButton.setOnMouseClicked(mouseEvent -> {
            //Show PopOver when button is clicked
            notificationPopOver.show(notifButton);
        });
    }

    /**
     * Sets up the jumping animation for the icon number.
     */
    private void addJumpingAnimation() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(400), iconNumber);
        translateTransition.setFromY(0.0);
        translateTransition.setToY(-4.0);
        translateTransition.setCycleCount(-1);
        translateTransition.setAutoReverse(true);
        translateTransition.setInterpolator(Interpolator.EASE_BOTH);
        translateTransition.play();
    }

    private void bindIconNumberToStackPane() {
        buttonPane.setAlignment(iconNumber, Pos.TOP_RIGHT);
        buttonPane.setMargin(iconNumber, new Insets(6, 2, 0, 0));
        buttonPane.getChildren().addAll(iconNumber);
    }

    public void updateNotifCount(int num) {
        iconNumber.setText(num + "");
    }
}
//@@ author
