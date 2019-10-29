package seedu.address.ui;

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

/**
 * Singleton notification button to open up alerts view.
 */
public class NotificationButton extends UiPart<Region> {

    private static NotificationButton notificationButton = null;
    private static final Logger logger = LogsCenter.getLogger(NotificationButton.class);
    private static final String FXML = "NotificationButton.fxml";

    @FXML
    private ImageView buttonIcon;

    @FXML
    private Button notifButton;

    @FXML
    private StackPane buttonPane;

    private Label iconNumber;

    private NotificationButton() {
        super(FXML);
        notifButton.setGraphic(buttonIcon);
        notifButton.setStyle("-fx-border-width: 0");
        initIconNumber();
    }

    /**
     * Returns the single instance of the notification button.
     */
    public static NotificationButton getInstanceOfNotifButton() {
        if (notificationButton == null) {
            return new NotificationButton();
        }
        return notificationButton;
    }

    /**
     * Initializes the icon number.
     */
    private void initIconNumber() {
        iconNumber = new Label();
        iconNumber.setText("0");
        iconNumber.getStyleClass().add("notificationButtonLabel");
        addJumpingAnimation();
        bindIconNumberToStackPane();
    }

    /**
     * Sets up the jumping animation for the icon number.
     */
    private void addJumpingAnimation() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200), iconNumber);
        double start = 0.0;
        double end = start - 4.0;
        translateTransition.setFromY(start);
        translateTransition.setToY(end);
        translateTransition.setCycleCount(-1);
        translateTransition.setAutoReverse(true);
        translateTransition.setInterpolator(Interpolator.EASE_BOTH);
        translateTransition.play();
    }

    private void bindIconNumberToStackPane() {
        buttonPane.setAlignment(iconNumber, Pos.TOP_RIGHT);
        buttonPane.setMargin(iconNumber, new Insets(6, 6, 0, 0));
        buttonPane.getChildren().addAll(iconNumber);
    }

    public void setIconNumber(int num) {
        iconNumber.setText(num + "");
    }
}
