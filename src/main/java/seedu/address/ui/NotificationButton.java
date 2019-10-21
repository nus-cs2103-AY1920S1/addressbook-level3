package seedu.address.ui;

import java.util.logging.Logger;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
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
 * Notification button to open up alerts view.
 */
public class NotificationButton extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(NotificationButton.class);
    private static final String FXML = "NotificationButton.fxml";

    @FXML
    private ImageView buttonIcon;

    @FXML
    private Button notifButton;

    @FXML
    private StackPane buttonPane;

    private Label iconNumber;

    public NotificationButton() {
        super(FXML);
        notifButton.setGraphic(buttonIcon);
        notifButton.setStyle("-fx-border-width: 0");
        initIconNumber();
    }

    /**
     * Initializes the icon number.
     */
    private void initIconNumber() {
        iconNumber = new Label();
        iconNumber.setText("0");
        iconNumber.getStyleClass().add("notificationButtonLabel");
        addJumpingAnimation();
        addIconNumberSizeBindingsToButtonSize();
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

    private void addIconNumberSizeBindingsToButtonSize() {
        ReadOnlyDoubleProperty widthProperty = notifButton.widthProperty();
        ReadOnlyDoubleProperty heightProperty = notifButton.heightProperty();
        DoubleBinding widthBinding = widthProperty.divide(4.0);
        DoubleBinding heightBinding = heightProperty.divide(4.0);
        iconNumber.setPrefWidth(widthBinding.doubleValue());
        iconNumber.setPrefWidth(heightBinding.doubleValue());
        widthBinding.addListener((ChangeListener<? super Number>) (o, oldVal, newVal) ->
                iconNumber.setPrefWidth(widthBinding.doubleValue()));
        heightBinding.addListener((ChangeListener<? super Number>) (o, oldVal, newVal) ->
                iconNumber.setPrefWidth(heightBinding.doubleValue()));
    }

    private void bindIconNumberToStackPane() {
        buttonPane.setAlignment(iconNumber, Pos.TOP_RIGHT);
        buttonPane.setMargin(iconNumber, new Insets(4, 12, 4, 0));
        buttonPane.getChildren().addAll(iconNumber);
    }

}
