package seedu.address.calendar.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.UiPart;

public class CommandBox extends UiPart<Region> {
    private static final String FXML = "CommandBox.fxml";
    private CommandExecutor commandExecutor;
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
