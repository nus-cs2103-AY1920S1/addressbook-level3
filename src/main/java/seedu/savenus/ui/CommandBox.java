package seedu.savenus.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.savenus.logic.commands.CommandResult;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.CommandHistory;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final CommandHistory commandHistory;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.commandHistory = new CommandHistory();
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    /**
     * Handle Up/Down Key Press
     */
    @FXML
    private void handleKeyPress(KeyEvent e) {
        if (e.getCode().equals(KeyCode.UP)) {
            String prevCommand = commandHistory.getPrev();
            if (prevCommand != null) {
                commandTextField.setText(prevCommand);
            }
            commandTextField.end();
            e.consume(); // this stop propagating the event
        }
        if (e.getCode().equals(KeyCode.DOWN)) {
            String nextCommand = commandHistory.getNext();
            if (nextCommand != null) {
                commandTextField.setText(nextCommand);
            }
            commandTextField.end();
            e.consume(); // this stop propagating the event
        }
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
            commandHistory.storeValidCommand(commandTextField.getText());
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            commandHistory.storeInvalidCommand(commandTextField.getText());
            setStyleToIndicateCommandFailure();
        }
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
         * @see seedu.savenus.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
