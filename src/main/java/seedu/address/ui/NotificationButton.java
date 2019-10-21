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

public class NotificationButton extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(NotificationButton.class);
    private static final String FXML = "NotificationButton.fxml";
    private static final String ICON_URL = "/images/bell_icon.png";

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
        iconNumber = new Label();
        iconNumber.setText("1");
        addJumpingAnimation();
        addImageViewSizeBindings();
        bindToStack();
    }

    /**
     * the jump animation changes the position of the mini-icon
     */
    private void addJumpingAnimation() {
        final TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200), iconNumber);
        final double start = 0.0;
        final double end = start - 4.0;
        translateTransition.setFromY(start);
        translateTransition.setToY(end);
        translateTransition.setCycleCount(-1);
        translateTransition.setAutoReverse(true);
        translateTransition.setInterpolator(Interpolator.EASE_BOTH);
        translateTransition.play();
    }

    private void addImageViewSizeBindings() {
        final ReadOnlyDoubleProperty widthProperty = notifButton.widthProperty();
        final ReadOnlyDoubleProperty heightProperty = notifButton.heightProperty();
        final DoubleBinding widthBinding = widthProperty.divide(4.0);
        final DoubleBinding heightBinding = heightProperty.divide(4.0);
        iconNumber.setPrefWidth(widthBinding.doubleValue());
        iconNumber.setPrefWidth(heightBinding.doubleValue());
        widthBinding.addListener((ChangeListener) (o, oldVal, newVal) ->
                iconNumber.setPrefWidth(widthBinding.doubleValue()));
        heightBinding.addListener((ChangeListener) (o, oldVal, newVal) ->
                iconNumber.setPrefWidth(heightBinding.doubleValue()));
    }

    private void bindToStack() {
        buttonPane.setAlignment(iconNumber, Pos.TOP_RIGHT);
        buttonPane.setMargin(iconNumber, new Insets(4, 6, 4, 4));
        buttonPane.getChildren().addAll(iconNumber);
    }

}
