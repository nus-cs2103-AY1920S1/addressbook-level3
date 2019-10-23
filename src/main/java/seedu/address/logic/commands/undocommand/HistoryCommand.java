package seedu.address.logic.commands.undocommand;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.PrefixType;

/**
 * Command that undoes the effects of the previous command, returning the model to its previous state.
 */
public class HistoryCommand extends Command {
    public static final String COMMAND_WORD = "history";
    public static final String MESSAGE_SUCCESS = "History shown";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the history of (undo-able) past commands";

    /**
     * Executes the command and returns a CommandResult with a message.
     * @param model contains the state of the data in memory.
     * @return feedback message of the operation result for display.
     * @throws CommandException
     */
    public CommandResult execute(Model model) throws CommandException {
        String commandHistory = model.getCommandHistory();
        return new CommandResult(commandHistory, PrefixType.P); //TODO: Check whether PrefixType is appropriate
    }
}
