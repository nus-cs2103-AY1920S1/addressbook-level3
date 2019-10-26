package seedu.address.logic.commands.undocommand;

import seedu.address.commons.exceptions.AlfredModelHistoryException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;

/**
 * Command that undoes the effects of the previous command, returning the model to its previous state.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undid 1 command";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes the previous command";

    /**
     * Executes the command and returns a CommandResult with a message.
     * @param model contains the state of the data in memory.
     * @return feedback message of the operation result for display.
     * @throws CommandException
     */
    public CommandResult execute(Model model) throws CommandException {
        try {
            model.undo();
            return new CommandResult(String.format(MESSAGE_SUCCESS),
                    CommandType.P); //Debug: Check PrefixType
        } catch (AlfredModelHistoryException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
