package thrift.logic.commands;

import static java.util.Objects.requireNonNull;

import thrift.logic.commands.exceptions.CommandException;
import thrift.model.Model;

/**
 * Undo the previous undoable command in THRIFT.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo the previous undoable command"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Undo successfully";

    public static final String NO_UNDOABLE_COMMAND = "No valid command to undo";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasUndoableCommand()) {
            Undoable command = model.getPreviousUndoableCommand();
            command.undo(model);
            return new CommandResult(MESSAGE_SUCCESS);
        }
        throw new CommandException(NO_UNDOABLE_COMMAND);
    }
}
