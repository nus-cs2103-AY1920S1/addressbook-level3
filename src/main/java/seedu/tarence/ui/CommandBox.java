package seedu.tarence.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.tarence.logic.commands.CommandResult;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final CommandExecutor autocompleteExecutor;
    private final CommandExecutor inputChangedExecutor;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor, CommandExecutor autocompleteExecutor,
                      CommandExecutor inputChangedExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.autocompleteExecutor = autocompleteExecutor;
        this.inputChangedExecutor = inputChangedExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    public void setInput(String autocompletedString) {
        commandTextField.setText(autocompletedString);
        commandTextField.requestFocus();
        commandTextField.positionCaret(autocompletedString.length());
    }

    /**
     * Helper method to reset focus to CommandBox.
     */
    public void setFocus() {
        commandTextField.requestFocus();
        commandTextField.positionCaret(commandTextField.getText().length());
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Handles the Tab button pressed event.
     */
    @FXML
    private void handleAutocomplete() throws CommandException, ParseException {
        autocompleteExecutor.execute(commandTextField.getText());
    }

    /**
     * Default handler for button pressed events.
     */
    @FXML
    private void handleOtherInput() throws CommandException, ParseException {
        inputChangedExecutor.execute("");
    }

    /**
     * Handles button press inputs from the user.
     */
    @FXML
    private void handleKeyPressed(KeyEvent keyEvent) throws CommandException, ParseException {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            handleCommandEntered();
        } else if (keyEvent.getCode().equals(KeyCode.TAB)) {
            handleAutocomplete();
        } else {
            handleOtherInput();
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
         * @see seedu.tarence.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
