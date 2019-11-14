package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;



/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    private List<String> commandHistory = new ArrayList<>();
    private int commandHistoryPointer = 0;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());

        EventHandler<KeyEvent> handleArrowKey = new EventHandler<>() {
            @Override
            public void handle(KeyEvent event) {
                if (commandHistory.size() < 1) {
                    return;
                }
                if (event.getCode() == KeyCode.UP) {

                    commandTextField.setText(commandHistory.get(commandHistoryPointer));
                    if (commandHistoryPointer > 0) {
                        commandHistoryPointer--;
                    }

                } else if (event.getCode() == KeyCode.DOWN) {
                    commandTextField.setText(commandHistory.get(commandHistoryPointer));
                    if (commandHistoryPointer < commandHistory.size() - 1) {
                        commandHistoryPointer++;
                    }

                }
            }
        };
        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, handleArrowKey);
    }


    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandHistory.add(commandTextField.getText());
            commandHistoryPointer = commandHistory.size() - 1; //resets pointer
            commandExecutor.execute(commandTextField.getText());
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
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
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
