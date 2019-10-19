package seedu.address.ui;

import java.util.function.Consumer;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final AutoComplete autoComplete;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor, AutoComplete autoComplete) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.autoComplete = autoComplete;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());

        // EventFilter was used as FXML callback onKeyPressed cannot consume keyEvent.
        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                case UP:
                case DOWN:
                    keyEvent.consume();
                    break;
                default:
                }
                autoComplete.updateSelectionKeyPressedCommandBox(keyEvent.getCode());
            }
        });
    }

    /**
     * Handles Command Entered.
     */
    public void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Handles the Text Change event.
     */
    @FXML
    private void handleTextChanged() {
        autoComplete.updateCommandAutoComplete(commandTextField.getText());
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

    public void setCommandTextField(String suggestion) {
        commandTextField.setText(suggestion);
        commandTextField.positionCaret(commandTextField.getLength());
        handleTextChanged();
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String, Consumer)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }
}
