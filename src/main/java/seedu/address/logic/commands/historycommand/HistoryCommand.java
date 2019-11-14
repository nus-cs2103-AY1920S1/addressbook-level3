package seedu.address.logic.commands.historycommand;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;

/**
 * Command that undoes the effects of the previous command, returning the model to its previous state.
 */
public class HistoryCommand extends Command {
    public static final String COMMAND_WORD = "history";
    public static final String MESSAGE_SUCCESS = "Showing the history of your commands.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the history of previously-executed commands "
                                                            + "that are undo-able and redo-able.\n"
                                                            + "Format: history\n"
                                                            + "Note: this command takes no additional parameters.\n";

    /**
     * Executes the command and returns a CommandResult with a message.
     * @param model contains the state of the data in memory.
     * @return feedback message of the operation result for display.
     * @throws CommandException
     */
    public CommandResult execute(Model model) throws CommandException {
        model.updateHistory(this);
        model.recordCommandExecution(this.getCommandInputString());
        return new CommandResult(MESSAGE_SUCCESS, CommandType.H);
    }
}
