package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.LinkedList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;

/**
 * Shows the history of commands.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";
    public static final String MESSAGE_SUCCESS = "Here are your commands: \n%1$s";
    public static final String MESSAGE_FAILURE = "There are no commands to display.\n";


    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        requireNonNull(model);

        LinkedList<String> commandList = new LinkedList<>(CommandHistory.getCommandHistory().getCommandHistoryList());

        if (commandList.isEmpty()) {
            return new CommandResult(MESSAGE_FAILURE);
        }

        Collections.reverse(commandList);

        StringBuilder sb = new StringBuilder();
        for (String s: commandList) {
            sb.append(s);
            sb.append(System.lineSeparator());
        }


        return new CommandResult(String.format(MESSAGE_SUCCESS, sb.toString().trim()));
    }
}
