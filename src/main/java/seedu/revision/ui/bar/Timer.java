package seedu.revision.ui.bar;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.logic.Logic;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.ui.UiPart;

/**
 * Timer class which handles the animations and implementation of the quiz session timer.
 */
public class Timer extends UiPart<Region> {
    public static final String MESSAGE_CONSTRAINTS = "Timer must be a number that is greater 1, and "
            + "double values will be truncated.";
    private static final String FXML = "Timer.fxml";
    private static final Logger logger = LogsCenter.getLogger(Timer.class);

    private final Integer startTime;
    @FXML
    private final Label timerLabel;
    private final CommandExecutor commandExecutor;
    private ReadOnlyIntegerWrapper currentTime;
    private Timeline timeline;

    /**
     * Initialises a {@Timer} object
     * @param startTime start time of the countdown timer.
     * @param commandExecutor command that will be executed at the end of the countdown.
     */
    public Timer(Integer startTime, CommandExecutor commandExecutor) {
        super(FXML);
        this.startTime = startTime;
        this.currentTime = new ReadOnlyIntegerWrapper(
                this, "currentTime", startTime);
        this.timerLabel = new Label();
        this.commandExecutor = commandExecutor;

        currentTime.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                syncProgress();
            }
        });
    }

    /**
     * Starts the timer and initiates the {@CommandExecutor}.
     */
    public void startTimer() {
        timeline = new Timeline();

        KeyFrame frame = new KeyFrame(Duration.seconds(1), event -> {
            currentTime.set(currentTime.get() - 1);
            if (currentTime.get() <= 0) {
                timeline.stop();
                try {
                    commandExecutor.execute("n");
                } catch (CommandException | ParseException | IOException e) {
                    logger.severe("Timer failed to go next question");
                }
            }
        });

        timeline.setCycleCount(startTime);
        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    /** Syncs the observable value with the progress. **/
    private void syncProgress() {
        //Run on the JavaFX Application Thread.
        Platform.runLater(() -> {
            timerLabel.setText(((Integer) currentTime.get()).toString());
        });
    }

    /** Resets the timer and starts a new timer.**/
    public void resetTimer() {
        timeline.stop();
        currentTime.set(startTime);
        startTimer();
    }

    /** Stops the timer. **/
    public void stopTimer() {
        timeline.stop();
    }

    public Label getTimerLabel() {
        return timerLabel;
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException, IOException;
    }


}

