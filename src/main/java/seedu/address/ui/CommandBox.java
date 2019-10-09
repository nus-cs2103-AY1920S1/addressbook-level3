package seedu.address.ui;

import javafx.collections.ObservableList;
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
    private final AutoCompleterUpdater autoCompleterUpdater;
    private final AutoCompleterSelector autoCompleterSelector;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor, AutoCompleterUpdater autoCompleterUpdater,
                      AutoCompleterSelector autoCompleterSelector) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.autoCompleterUpdater = autoCompleterUpdater;
        this.autoCompleterSelector = autoCompleterSelector;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
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
        commandTextField.positionCaret(commandTextField.getLength());
        autoCompleterUpdater.update(commandTextField.getText());
    }

    /**
     * Handles the Key Pressed event.
     */
    @FXML
    private void handleKeyPressed(KeyEvent e) {
        switch (e.getCode()) {
        case UP:
            autoCompleterSelector.notify(true);
            break;
        case DOWN:
            autoCompleterSelector.notify(false);
            break;
        case ENTER:
            autoCompleterSelector.notify(null);
            break;
        default:
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

    public void setCommandTextField(String suggestion) {
        commandTextField.setText(suggestion);
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
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

    /**
     * Represents a function that updates the AutoCompleter.
     */
    @FunctionalInterface
    public interface AutoCompleterUpdater {
        /**
         * Updates AutoCompleter of the command text.
         */
        void update(String commandText);
    }

    /**
     * Represents a function that updates the AutoCompleter.
     */
    @FunctionalInterface
    public interface AutoCompleterSelector {
        /**
         * Updates AutoCompleter of the command text.
         */
        void notify(Boolean traverseUp);
    }
}
