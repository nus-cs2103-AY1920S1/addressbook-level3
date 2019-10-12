package seedu.address.logic.commands;

import java.util.EmptyStackException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

//@@author ambervoong
/**
 * Undoes a command that was previously executed.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_UNDO_FAILURE = "There was an error undoing the previous command!";
    public static final String MESSAGE_EMPTY_UNDO_HISTORY= "There are no more commands to undo!";

    public UndoCommand() {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            UndoableCommand command = model.getExecutedCommand();
            return command.undo(model);
        } catch (EmptyStackException e) {
            throw new CommandException(MESSAGE_EMPTY_UNDO_HISTORY);

        }
    }
}
