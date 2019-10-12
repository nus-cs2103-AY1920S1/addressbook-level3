package thrift.logic.commands;

import static java.util.Objects.requireNonNull;

import thrift.logic.commands.exceptions.CommandException;
import thrift.model.Model;

/**
 * Redo undone command in THRIFT.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redo undone command"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Redo successful";

    public static final String NO_REDOABLE_COMMAND = "No valid command to redo";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasUndoneCommand()) {
            Undoable undoable = model.getUndoneCommand();
            undoable.redo(model);
            return new CommandResult(MESSAGE_SUCCESS);
        }
        throw new CommandException(NO_REDOABLE_COMMAND);
    }
}
