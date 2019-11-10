//@@author CarbonGrid
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    private static final String FXML = "CommandBox.fxml";
    private static final KeyCombination KEY_COMBINATION_CONTROL_A = new KeyCodeCombination(KeyCode.A,
            KeyCombination.CONTROL_DOWN);

    private final CommandExecutor commandExecutor;
    private final CommandBoxManager commandBoxManager;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor, CommandBoxManager commandBoxManager, AutoCompleteOverlay aco) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.commandBoxManager = commandBoxManager;

        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> handleTextChanged());
        commandTextField.focusedProperty().addListener((unused1, unused2, newValue) -> {
            if (!newValue) {
                aco.hide();
            }
        });

        // EventFilter was used as FXML callback onKeyPressed cannot consume keyEvent.
        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            switch (keyEvent.getCode()) {
            case UP:
            case DOWN:
            case ENTER:
                commandBoxManager.handleCommandBoxKeyPressed(keyEvent.getCode());
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
        commandExecutor.execute(commandText);
        commandTextField.setText("");
        return commandText;
    }

    /**
     * Handles the Text Change event.
     */
    private void handleTextChanged() {
        commandBoxManager.handleCommandBoxTextChanged(commandTextField.getText());
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
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command.
         */
        void execute(String commandText);
    }
}
