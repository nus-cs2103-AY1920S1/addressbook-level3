package seedu.revision.ui.bar;

import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.logic.MainLogic;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.parser.exceptions.ParseException;

public class Timer {
    private static final Logger logger = LogsCenter.getLogger(Timer.class);

    private final Integer startTime;
    @FXML
    private final Label label;
    private final CommandExecutor commandExecutor;
    private ReadOnlyIntegerWrapper currentTime;
    private Timeline timeline;



    public Timer(Integer startTime, CommandExecutor commandExecutor) {
        this.startTime = startTime;
        this.currentTime = new ReadOnlyIntegerWrapper(
                this, "currentTime", startTime);
        this.label = new Label();
        this.commandExecutor = commandExecutor;

        currentTime.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                syncProgress();
            }
        });
    }

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

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    public Label getLabel() {
        return label;
    }

    private void syncProgress() {
        label.setText(((Integer) currentTime.get()).toString());
    }

    public void resetTimer() {
        currentTime.set(startTime);
        startTimer();
    }

    public void stopTimer() {
        timeline.stop();
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see MainLogic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException, IOException;
    }


}

