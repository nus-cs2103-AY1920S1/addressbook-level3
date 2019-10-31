package seedu.address.ui;

import javafx.application.Platform;
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

        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> handleTextChanged());

        // EventFilter was used as FXML callback onKeyPressed cannot consume keyEvent.
        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            switch (keyEvent.getCode()) {
            case UP:
            case DOWN:
            case ENTER:
                autoComplete.updateSelectionKeyPressedCommandBox(keyEvent.getCode());
                keyEvent.consume();
                break;
            default:
                if (KEY_COMBINATION_CONTROL_A.match(keyEvent)) {
                    commandTextField.selectAll();
                }
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
            //do nothing since result display handles error message
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

    public void setCommandTextField(String suggestion) {
        if (suggestion == null) {
            return;
        }
        commandTextField.setText(suggestion);
        commandTextField.positionCaret(suggestion.length());
    }

    /**
     * Appends given suggestion to existing command in Command Box.
     */
    public void appendCommandTextField(String suggestion) {
        if (suggestion == null) {
            return;
        }
        String curr = commandTextField.getText();
        curr = curr.substring(0, curr.lastIndexOf(' ') + 1) + suggestion;
        commandTextField.setText(curr);
        commandTextField.positionCaret(curr.length());
    }

    /**
     * Restores focus later.
     */
    public void restoreFocusLater() {
        if (commandTextField.getSelectedText() != "") {
            return;
        }
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
