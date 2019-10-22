package seedu.billboard.logic.commands;

import seedu.billboard.logic.commands.exceptions.CommandException;
import seedu.billboard.model.Model;
import seedu.billboard.model.history.CommandHistory;

/**
 * Displays all history command.
 */
public class HistoryCommand extends Command {
    public static final String COMMAND_WORD = "history";
    public static final String EMPTY_HISTORY = "There is no commandHistory.";


    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!CommandHistory.hasCommand()) {
            CommandResult cmdResult = new CommandResult(EMPTY_HISTORY);
        }

        CommandResult cmdResult = new CommandResult(CommandHistory.getCmdHistory());
        return cmdResult;
    }
}
