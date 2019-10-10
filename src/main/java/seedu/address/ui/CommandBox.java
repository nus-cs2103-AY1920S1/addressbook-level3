package seedu.address.ui;

import java.util.LinkedList;
import java.util.ListIterator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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
    private static final int MAX_HISTORY_BUFFER_SIZE = 200;

    private final CommandExecutor commandExecutor;

    @FXML
    private TextField commandTextField;

    private LinkedList<String> commandHistory = new LinkedList<String>();
    private ListIterator<String> historyIterator = commandHistory.listIterator(0);

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty()
            .addListener((unused1, unused2, unused3) -> setStyleToDefault());
        commandTextField.setOnKeyPressed(event -> keyPressedEvent(event));
    }

    /**
    * Event handler for the text field.
    * @param e The KeyEvent to process.
    */
    private void keyPressedEvent(KeyEvent e) {
        switch (e.getCode()) {
        case UP:
            traverseHistory(-1);
            break;
        case DOWN:
            traverseHistory(1);
            break;
        default:
            break;
        }
    }

    /**
    * Goes up or down the text field history.
    * @param i Positive to go down in history, and negative to go up. Anything
    * else resets the text field.
    */
    private void traverseHistory(int i) {
        String text;
        if (i > 0 && historyIterator.hasNext()) {
            text = historyIterator.next();
        } else if (i < 0 && historyIterator.hasPrevious()) {
            text = historyIterator.previous();
        } else {
            text = "";
        }
        commandTextField.setText(text);
    }

    /**
    * Handles the Enter button pressed event.
    */
    @FXML
    private void handleCommandEntered() {
        try {
            String text = commandTextField.getText();
            if (commandHistory.size() >= MAX_HISTORY_BUFFER_SIZE) {
                commandHistory.pop();
            }
            commandHistory.offer(text);

            // This is just to reset it to the last item for up/down arrow.
            historyIterator = commandHistory.listIterator(commandHistory.size());

            commandExecutor.execute(text);
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
