package seedu.address.ui;

import java.util.ArrayList;
import java.util.Optional;

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

    private static final Integer TIMER_DURATION = 5;
    private static final Integer SHOW_ANSWER_DURATION = 3;
    private static final Integer ONE_FLASHCARD_DURATION = TIMER_DURATION + SHOW_ANSWER_DURATION;

    private static Optional<Flashcard> currFlashcard;
    private static boolean isAnswerShown;

    @FXML
    private Label timerLabel;

    @FXML
    private StackPane rightPane;

    @FXML
    private TextArea qnsTextArea;

    @FXML
    private TextArea ansTextArea;

    private IntegerProperty currentSeconds;
    private Timeline timeline;

    /**
     *
     */
    @FXML
    public void initialize() {
        currentSeconds = new SimpleIntegerProperty(TIMER_DURATION);
        timerLabel.textProperty().bind(currentSeconds.asString());
        timerLabel.setVisible(false);
        currFlashcard = Optional.empty();
    }

    /**
     * Displays the question of the flashcard specified in the flashcard tab window.
     * @param flashcard flashcard to be displayed
     */
    public void loadTimetrialFlashcard(Flashcard flashcard) {
        flashcard.updateStatistics();
        qnsTextArea.setText(flashcard.getQuestion().toString());
        ansTextArea.setText(flashcard.getAnswer().toString());
        ansTextArea.setVisible(false);
        timerLabel.setVisible(true);
        startTimer();
    }

    /**
     * Displays the question of the flashcard specified in the flashcard tab window.
     * @param flashcard flashcard to be displayed
     */
    public void loadFlashcard(Flashcard flashcard) {
        qnsTextArea.setText(flashcard.getQuestion().toString());
        ansTextArea.setText(flashcard.getAnswer().toString());
        ansTextArea.setVisible(false);
        currFlashcard = Optional.of(flashcard);
        isAnswerShown = false;
    }

    /**
     * Flashes the answer of the flashcard upon the show command.
     */
    public void showFlashcardAns() {
        currFlashcard.get().updateStatistics();
        ansTextArea.setVisible(true);
        timerLabel.setVisible(false);
        isAnswerShown = true;
    }

    /**
     * Hides the timer and flashes the answer of the flashcard.
     */
    public void showTimetrialFlashcardAns() {
        ansTextArea.setVisible(true);
        timerLabel.setVisible(false);
        isAnswerShown = true;
    }



    /**
     * Starts the timer countdown.
     */
    private void startTimer() {
        // Adapted from https://asgteach.com/2011/10/javafx-animation-and-binding-simple-countdown-timer-2/
        if (timeline != null) {
            timeline.stop();
        }
        currentSeconds.set(TIMER_DURATION);
        timeline = new Timeline(new KeyFrame(Duration.seconds(TIMER_DURATION + 1),
                new KeyValue(currentSeconds, 0)),
                new KeyFrame(Duration.seconds(TIMER_DURATION), e -> showTimetrialFlashcardAns()));
        timeline.play();
    }

    /**
     * Starts the time trial based with the deck passed.
     * @param deck deck of flashcards to be tested
     */
    public void startTimeTrial(Optional<ArrayList<Flashcard>> deck) {
        currFlashcard = Optional.empty();
        Timeline timeline = new Timeline();
        int cardCount = 0;
        for (Flashcard fc: deck.get()) {
            timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.seconds(cardCount * ONE_FLASHCARD_DURATION), e -> loadTimetrialFlashcard(fc),
                new KeyValue(currentSeconds, 0)),
                new KeyFrame(Duration.seconds(cardCount * ONE_FLASHCARD_DURATION + TIMER_DURATION),
                    e -> showTimetrialFlashcardAns()));
            cardCount++;
        }
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(cardCount * ONE_FLASHCARD_DURATION),
                e -> resetTexts()));
        timeline.play();
    }

    /**
     * Empties the qnsTextArea and ansTextArea.
     */
    private void resetTexts() {
        qnsTextArea.setText("");
        ansTextArea.setText("");
    }

    public static Optional<Flashcard> getCurrFlashcard() {
        return currFlashcard;
    }

    public static boolean isAnswerShown() {
        return isAnswerShown;
    }
}
