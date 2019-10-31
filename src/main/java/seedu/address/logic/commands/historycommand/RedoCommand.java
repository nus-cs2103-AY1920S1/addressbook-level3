package seedu.address.logic.commands.historycommand;

import seedu.address.commons.exceptions.AlfredModelHistoryException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;

/**
 * Command that redoes the effects of the previous command, returning the model to the state after re-doing the command.
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Re-did 1 command";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redoes the previous command";

    /**
     * Executes the command and returns a CommandResult with a message.
     * @param model contains the state of the data in memory.
     * @return feedback message of the operation result for display.
     * @throws CommandException
     */
    public CommandResult execute(Model model) throws CommandException {
        try {
            model.redo();
            model.updateHistory(this);
            model.recordCommandExecution(this.getCommandInputString());
            return new CommandResult(MESSAGE_SUCCESS, CommandType.H);
        } catch (AlfredModelHistoryException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
