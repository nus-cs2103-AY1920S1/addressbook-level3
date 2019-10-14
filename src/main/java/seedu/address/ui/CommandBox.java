package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DoNotMergeCommand;
import seedu.address.logic.commands.MergeCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.DuplicatePersonWithMergeException;
import seedu.address.logic.commands.exceptions.DuplicatePersonWithoutMergeException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    private String mergeCommand;
    private String doNotMergeCommand;
    private boolean isOnMergeStandby = false;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() throws CommandException, ParseException {
        String command = commandTextField.getText();
        if (isOnMergeStandby) {
            handleInitialisingMergeCommand(command);
        } else {
            try {
                commandExecutor.execute(command + " ", false);
                commandTextField.setText("");
            } catch (DuplicatePersonWithMergeException e) {
                commandTextField.setText("");
                setStyleToIndicateCommandFailure();
                standByForMerge(command);
            } catch (DuplicatePersonWithoutMergeException e) {
                setStyleToIndicateCommandFailure();
                commandTextField.setText("");
            } catch (CommandException | ParseException e) {
                setStyleToIndicateCommandFailure();
            }
        }
    }

    /**
     * Creates a new merge command and do not merge command and puts command box on standby to accept a merge command.
     * @param command Input command by user.
     * @throws CommandException Should not be thrown.
     * @throws ParseException Should not be thrown.
     */
    private void standByForMerge(String command) throws CommandException, ParseException {
        this.mergeCommand = MergeCommand.COMMAND_WORD + " " + command;
        this.doNotMergeCommand = DoNotMergeCommand.COMMAND_WORD + " " + command;
        this.isOnMergeStandby = true;
    }

    /**
     * Calls {@code commandExecutor} to execute a merge if user inputs a "yes" and calls {@code commandExecutor} to
     * execute a do not merge command if user inputs "no". This method then performs a clean up to remove the
     * "on-standby" boolean flag.
     * @param command Add command input by the user that throws a duplicate person error.
     * @throws CommandException Should not be thrown.
     * @throws ParseException Should not be thrown.
     */
    private void handleInitialisingMergeCommand(String command) throws CommandException, ParseException {
        if (command.equals("no")) {
            commandExecutor.execute(doNotMergeCommand, true);
            commandTextField.setText("");
        } else if (command.equals("yes") || command.equals("")) {
            commandExecutor.execute(mergeCommand, true);
            commandTextField.setText("");
        } else {
            try {
                commandExecutor.execute(command, false);
                commandTextField.setText("");
            } catch (CommandException | ParseException e) {
                setStyleToIndicateCommandFailure();
            }
        }
        this.mergeCommand = null;
        this.isOnMergeStandby = false;
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText, boolean isSystemInput) throws CommandException, ParseException;
    }

}
