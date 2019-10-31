package seedu.address.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
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
    private static final KeyCombination KEY_COMBINATION_CONTROL_A = new KeyCodeCombination(KeyCode.A,
            KeyCombination.CONTROL_DOWN);

    private final CommandExecutor commandExecutor;
    private final AutoComplete autoComplete;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor, AutoComplete autoComplete) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.autoComplete = autoComplete;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box. (NO LONGER REQUIRED)
        // commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());

        // EventFilter was used as FXML callback onKeyPressed cannot consume keyEvent.
        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            switch (keyEvent.getCode()) {
            case UP:
            case DOWN:
                keyEvent.consume();
                break;
            default:
            }
            autoComplete.updateSelectionKeyPressedCommandBox(keyEvent.getCode());
        });

        commandTextField.setOnKeyPressed(keyEvent -> {
            if (KEY_COMBINATION_CONTROL_A.match(keyEvent)) {
                commandTextField.selectAll();
            }
        });
    }

    /**
     * Handles Command Entered.
     */
    public String handleCommandEntered() {
        String commandText = commandTextField.getText();
        try {
            commandExecutor.execute(commandText);
        } catch (CommandException | ParseException e) {
            //setStyleToIndicateCommandFailure();
        } finally {
            commandTextField.setText("");
        }
        return commandText;
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
        if (suggestion == null) {
            return;
        }
        commandTextField.setText(suggestion);
        commandTextField.positionCaret(commandTextField.getLength());
        handleTextChanged();
    }

    /**
     * Appends given suggestion to existing command in Command Box.
     */
    public void appendCommandTextField(String suggestion) {
        if (suggestion == null) {
            return;
        }
        String curr = commandTextField.getText();
        commandTextField.setText(curr.substring(0, curr.lastIndexOf(' ') + 1) + suggestion);
        commandTextField.positionCaret(commandTextField.getLength());
        handleTextChanged();
    }

    /**
     * Restores focus later.
     */
    public void restoreFocusLater() {
        int currPos = commandTextField.getCaretPosition();
        Platform.runLater(() -> {
            commandTextField.requestFocus();
            commandTextField.positionCaret(currPos);
        });
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
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }
}
