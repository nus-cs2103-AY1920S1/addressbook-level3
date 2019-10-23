package seedu.address.ui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import seedu.address.model.flashcard.Flashcard;

/**
 * Controller class that handles what happens within the Flashcard Tab within the Activity Window.
 */
public class FlashcardTabWindowController {
    @FXML
    private Label timerLabel;

    @FXML
    private StackPane rightPane;

    @FXML
    private TextArea qnsTextArea;

    @FXML
    private TextArea ansTextArea;

    private static final Integer TIMER_DURATION = 15;

    private IntegerProperty currentSeconds;

    private Timeline timeline;


    @FXML
    public void initialize() {
        currentSeconds = new SimpleIntegerProperty(TIMER_DURATION);
        timerLabel.textProperty().bind(currentSeconds.asString());
    }

    /**
     * Displays the question of the flashcard specified in the flashcard tab window.
     * @param flashcard flashcard to be displayed
     */
    public void loadFlashcard(Flashcard flashcard) {
        qnsTextArea.setText(flashcard.getQuestion().toString());
        ansTextArea.setVisible(false);
        ansTextArea.setText(flashcard.getAnswer().toString());
        startTimer();
    }

    public void showFlashcardAns() {
//        rightPane.getChildren().
        ansTextArea.setVisible(true);
    }


    /**
     * Starts the timer countdown.
     */
    private void startTimer() {
        if (timeline != null) {
            timeline.stop();
        }
        currentSeconds.set(TIMER_DURATION);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(TIMER_DURATION + 1),
                        new KeyValue(currentSeconds, 0)));
        timeline.playFromStart();
    }
}
