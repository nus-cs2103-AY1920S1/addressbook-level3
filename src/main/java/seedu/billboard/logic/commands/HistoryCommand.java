package seedu.billboard.logic.commands;

import seedu.billboard.model.Model;
import seedu.billboard.model.history.CommandHistory;

/**
 * Displays all history command.
 */
public class HistoryCommand extends Command {
    public static final String COMMAND_WORD = "history";
    public static final String MESSAGE_EMPTY_HISTORY = "There is no command history.";


    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     */
    @Override
    public CommandResult execute(Model model) {
        if (!CommandHistory.hasCommand()) {
            return new CommandResult(MESSAGE_EMPTY_HISTORY);
        }

        CommandResult cmdResult = new CommandResult(CommandHistory.getCmdHistory());
        return cmdResult;
    }
}
