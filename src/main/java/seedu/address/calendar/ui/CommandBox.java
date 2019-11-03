package seedu.address.calendar.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.ListElementPointer;
import seedu.address.ui.UiPart;

import java.util.List;

public class CommandBox extends UiPart<Region> {
    private static final String FXML = "CommandBox.fxml";
    private CommandExecutor commandExecutor;
    private final List<String> history = null;
    private ListElementPointer historySnapshot;
    @FXML
    private TextField commandTextField;

    CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String userInput = commandTextField.getText();
        try {
            commandExecutor.execute(userInput);
        } catch (ParseException e) {
            System.out.println(e);
        } catch (CommandException e) {
            System.out.println(e);
        }
        commandTextField.setText("");
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                // As up and down buttons will alter the position of the caret,
                // consuming it causes the caret's position to remain unchanged
                keyEvent.consume();

                navigateToPreviousInput();
                break;
            case DOWN:
                keyEvent.consume();
                navigateToNextInput();
                break;
            default:
                // let JavaFx handle the keypress
        }
    }

    /**
     * Updates the text field with the previous input in {@code historySnapshot},
     * if there exists a previous input in {@code historySnapshot}
     */
    private void navigateToPreviousInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasPrevious()) {
            return;
        }

        replaceText(historySnapshot.previous());
    }

    /**
     * Updates the text field with the next input in {@code historySnapshot},
     * if there exists a next input in {@code historySnapshot}
     */
    private void navigateToNextInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasNext()) {
            return;
        }

        replaceText(historySnapshot.next());
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
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command.
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }
}
