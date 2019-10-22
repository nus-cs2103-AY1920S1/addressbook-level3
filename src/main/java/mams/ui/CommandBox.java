package mams.ui;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import mams.logic.Logic;
import mams.logic.commands.CommandResult;
import mams.logic.commands.exceptions.CommandException;
import mams.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final List<String> commandHistory;

    private ListElementPointer commandHistoryPointer;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor, List<String> commandHistory) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.commandHistory = commandHistory;
        this.commandHistoryPointer = new ListElementPointer(commandHistory);
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
            initHistory();
            commandHistoryPointer.next();
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            initHistory();
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Handles {@code keyEvent} event for the {@code CommandBox}
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case UP:
            keyEvent.consume();
            getPreviousInput();
            break;
        case DOWN:
            keyEvent.consume();
            getNextInput();
            break;
        default:
            break;
        }
    }

    /**
     * Updates the text field with the previous input pointed to by {@code commandHistoryPointer} in
     * {@code commandHistory}, if there exists a previous input.
     */
    private void getPreviousInput() {
        if (!commandHistoryPointer.hasPrevious()) {
            return;
        }
        replaceText(commandHistoryPointer.previous());
    }

    /**
     * Updates the text field with the next input pointed to by {@code commandHistoryPointer} in
     * {@code commandHistory}, if there exists a next input.
     */
    private void getNextInput() {
        if (!commandHistoryPointer.hasNext()) {
            return;
        }
        replaceText(commandHistoryPointer.next());
    }

    /**
     * Sets {@code CommandBox}'s text field with {@code text} and
     * positions the caret to the end of the {@code text}.
     */
    private void replaceText(String text) {
        commandTextField.setText(text);
        commandTextField.positionCaret(commandTextField.getText().length());
    }

    /**
     * Initializes the history snapshot.
     */
    private void initHistory() {
        commandHistoryPointer = new ListElementPointer(commandHistory);
        // add an empty string to represent the most-recent end of historySnapshot, to be shown to
        // the user if she tries to navigate past the most-recent end of the historySnapshot.
        commandHistoryPointer.add("");
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
         * @see Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
