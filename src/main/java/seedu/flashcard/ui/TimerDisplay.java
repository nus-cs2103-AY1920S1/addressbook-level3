package seedu.flashcard.ui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.flashcard.logic.commands.CommandResult;
import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.logic.parser.exceptions.ParseException;


public class TimerDisplay extends UiPart<Region> {

    private static final String FXML = "TimerDisplay.fxml";
    private static final Integer STARTTIME = 15;

    private Timeline timeline;
    private IntegerProperty timeSeconds =
            new SimpleIntegerProperty(STARTTIME);

    private final CommandExecutor commandExecutor;

    @FXML
    private Label timerLabel;


    public TimerDisplay(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        timerLabel.textProperty().bind(timeSeconds.asString());
        initializeTimer();
    }

    void initializeTimer (){
        timeSeconds.set(STARTTIME);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(STARTTIME + 1), ae -> timerExpire(),
        new KeyValue(timeSeconds, 0)));
        timeline.playFromStart();

    }

    private void timerExpire() {
        try {
            commandExecutor.execute("flip 1");
        } catch (CommandException | ParseException e) {
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
