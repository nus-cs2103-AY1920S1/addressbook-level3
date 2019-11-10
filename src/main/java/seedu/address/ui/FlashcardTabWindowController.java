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
    private Timeline timelineHelper;

    /**
     * This method is called after the FlashcardTabWindowController has been injected.
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
    private void loadTimetrialFlashcard(Flashcard flashcard) {
        flashcard.updateStatistics();
        qnsTextArea.setText(flashcard.getQuestion().toString());
        ansTextArea.setText(flashcard.getAnswer().toString());
        ansTextArea.setVisible(false);
        timerLabel.setVisible(true);
        currFlashcard = Optional.of(flashcard);
        isAnswerShown = false;
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
        assert (currFlashcard.isPresent());
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
     * This method is called when a command is executed during a time trial - terminates the time trial.
     */
    public void terminateTimeTrial() {
        timeline.stop();
        timelineHelper.stop();
        resetTextsAfterTimeTrial();
    }

    /**
     * Hides the timer and flashes the answer of the flashcard.
     */
    private void showTimetrialFlashcardAns() {
        ansTextArea.setVisible(true);
        timerLabel.setVisible(false);
        isAnswerShown = true;
    }

    /**
     * Starts the timer countdown.
     */
    private void startTimer() {
        //

        currentSeconds.set(TIMER_DURATION);
        timelineHelper = new Timeline(new KeyFrame(Duration.seconds(TIMER_DURATION + 1),
                new KeyValue(currentSeconds, 0)),
                new KeyFrame(Duration.seconds(TIMER_DURATION), e -> showTimetrialFlashcardAns()));
        timelineHelper.play();
    }

    /**
     * Starts the time trial based with the deck passed.
     * @param deck deck of flashcards to be tested
     */
    public void startTimeTrial(Optional<ArrayList<Flashcard>> deck) {
        resetTexts();
        currFlashcard = Optional.empty();
        timeline = new Timeline();
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
            e -> resetTextsAfterTimeTrial()));
        timeline.play();
    }

    /**
     * Empties the qnsTextArea and ansTextArea.
     */
    private void resetTextsAfterTimeTrial() {
        qnsTextArea.setText("");
        ansTextArea.setText("");
        timerLabel.setVisible(false);
        currFlashcard = Optional.empty();
        MainWindow.setIsTimeTrialOngoing(false);
    }

    /**
     * Empties the qnsTextArea and ansTextArea after a time trial.
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
