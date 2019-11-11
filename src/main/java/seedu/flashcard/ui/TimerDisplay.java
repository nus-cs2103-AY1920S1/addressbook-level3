package seedu.flashcard.ui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.flashcard.logic.commands.CommandResult;
import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.logic.parser.exceptions.ParseException;


/**
 * Represents the quiz timer, it handles the quiz mode timer and skipping questions.
 */
public class TimerDisplay extends UiPart<Region> {

    private static final String FXML = "TimerDisplay.fxml";
    private static Integer defaultTime = 15;
    private static Integer startTime = 0;

    private static IntegerProperty userPrefTime;


    private Timeline timeline;
    private IntegerProperty timeSeconds =
            new SimpleIntegerProperty(startTime);


    private final CommandExecutor commandExecutor;

    @FXML
    private Label timerLabel;

    @FXML
    private Label currentQuestion;

    @FXML
    private Label totalQuestions;


    public TimerDisplay(CommandExecutor commandExecutor, IntegerProperty duration,
                        IntegerProperty totalCards, IntegerProperty remainingCards) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        timeline = new Timeline();
        timerLabel.textProperty().bind(timeSeconds.asString());
        currentQuestion.textProperty().bind(remainingCards.asString());
        totalQuestions.textProperty().bind(totalCards.asString());
        userPrefTime = duration;

    }

    /**
     * Starts/Restarts the timer with a user defined duration
     */
    void initializeTimer () {
        timeline.stop();
        if (userPrefTime.getValue() == 0) {
            startTime = defaultTime;
        } else {
            startTime = userPrefTime.getValue();
        }
        timeSeconds.set(startTime);
        timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(startTime + 1), ae -> timerExpire(),
                new KeyValue(timeSeconds, 0)));
        timeline.playFromStart();
    }

    /**
     * Handles the timer expiring event by skipping the current flashcard
     */
    private void timerExpire() {
        try {
            commandExecutor.execute("skip");
        } catch (CommandException | ParseException e) {
            //shouldn't ever occur in this usage
        }
    }

    /**
     * stops the timer and prevents it from counting down further
     */
    public void stopTimer() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    /**
     * Represent a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {

        /**
         * Executes the command and returns result.
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }



}
