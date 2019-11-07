package seedu.address.ui;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.CommandWordException;
import seedu.address.logic.commands.exceptions.DuplicatePersonWithMergeException;
import seedu.address.logic.commands.exceptions.DuplicatePersonWithoutMergeException;
import seedu.address.logic.commands.exceptions.DuplicatePolicyWithMergeException;
import seedu.address.logic.commands.exceptions.DuplicatePolicyWithoutMergeException;
import seedu.address.logic.commands.merge.DoNotMergePersonCommand;
import seedu.address.logic.commands.merge.DoNotMergePolicyCommand;
import seedu.address.logic.commands.merge.MergePersonCommand;
import seedu.address.logic.commands.merge.MergePolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final String MERGE_PERSON = "person";
    private static final String MERGE_POLICY = "policy";

    private final CommandExecutor commandExecutor;

    /* Command history to help during navigation of commands by pressing up/down keys */
    private final ArrayList<String> history = new ArrayList<>();
    private int currentOffset = 0;

    private String mergeCommand;
    private String doNotMergeCommand;
    private boolean isOnMergeStandby = false;
    private String mergeType;

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
            history.add(command);
            currentOffset = 0;
            try {
                commandExecutor.execute(command + " ", false);
                commandTextField.setText("");
            } catch (CommandWordException e) {
                String commandSuggestion = e.getCommandSuggestion();
                commandTextField.setText(commandSuggestion);
                commandTextField.positionCaret(commandSuggestion.length());
                setStyleToIndicateCommandFailure();
            } catch (DuplicatePersonWithMergeException e) {
                commandTextField.setText("");
                setStyleToIndicateCommandFailure();
                standByForMerge(command, MERGE_PERSON);
            } catch (DuplicatePolicyWithMergeException e) {
                commandTextField.setText("");
                setStyleToIndicateCommandFailure();
                standByForMerge(command, MERGE_POLICY);
            } catch (DuplicatePersonWithoutMergeException | DuplicatePolicyWithoutMergeException e) {
                setStyleToIndicateCommandFailure();
                commandTextField.setText("");
            } catch (CommandException | ParseException e) {
                setStyleToIndicateCommandFailure();
            }
        }
    }

    /**
     * Handles key presses other than 'Enter' in CommandBox.
     * @param event event representing the key which was pressed.
     */
    @FXML
    private void keyListener(KeyEvent event) {
        assert currentOffset >= 0 : "Offset cannot be negative";

        if (event.getCode() == KeyCode.UP && currentOffset < history.size()) {
            currentOffset++;
            String command = history.get(history.size() - currentOffset);
            commandTextField.setText(command);
            commandTextField.positionCaret(command.length());
        } else if (event.getCode() == KeyCode.DOWN && currentOffset > 0) {
            currentOffset--;
            String command = (currentOffset > 0) ? history.get(history.size() - currentOffset) : "";
            commandTextField.setText(command);
            commandTextField.positionCaret(command.length());
        }
    }

    /**
     * Creates a new merge command and do not merge command and puts command box on standby to accept a merge command.
     * @param command Input command by user.
     * @throws CommandException Should not be thrown.
     * @throws ParseException Should not be thrown.
     */
    private void standByForMerge(String command, String mergeType) throws CommandException, ParseException {
        if (mergeType.equals(MERGE_PERSON)) {
            this.mergeCommand = MergePersonCommand.COMMAND_WORD + " " + command;
            this.doNotMergeCommand = DoNotMergePersonCommand.COMMAND_WORD + " " + command;
            this.isOnMergeStandby = true;
            this.mergeType = MERGE_PERSON;
        } else {
            this.mergeCommand = MergePolicyCommand.COMMAND_WORD + " " + command;
            this.doNotMergeCommand = DoNotMergePolicyCommand.COMMAND_WORD + " " + command;
            this.isOnMergeStandby = true;
            this.mergeType = MERGE_POLICY;
        }
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
