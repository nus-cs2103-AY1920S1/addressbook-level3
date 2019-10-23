package seedu.travezy.calendar.ui;

import javafx.scene.control.TextField;
import seedu.travezy.logic.commands.exceptions.CommandException;
import seedu.travezy.logic.parser.exceptions.ParseException;

public class CommandBox {
    private TextField commandBox;

    CommandBox(CommandExecutor commandExecutor) {
        commandBox = new TextField();
        commandBox.setOnAction(event -> {
            String userInput = commandBox.getText();
            try {
                commandExecutor.execute(userInput);
            } catch (ParseException e) {
                System.out.println(e);
            } catch (CommandException e) {
                System.out.println(e);
            }
            commandBox.setText("");
        });
    }

    public TextField getCommandBox() {
        return commandBox;
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command.
         */
        void execute(String commandText) throws CommandException, ParseException;
    }
}
