package thrift.logic.commands;

import static java.util.Objects.requireNonNull;

import thrift.logic.commands.exceptions.CommandException;
import thrift.model.Model;

/**
 * Redo undone command in THRIFT.
 */
public class RedoCommand extends NonScrollingCommand {

    public static final String COMMAND_WORD = "redo";

    public static final String HELP_MESSAGE = COMMAND_WORD + ": Redo undone command.\n"
            + "Undoable commands are: add_expense, add_income, budget, clone, delete, tag, untag, update.\n"
            + "Format: " + COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redo undone command.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Redo successful.";

    public static final String NO_REDOABLE_COMMAND = "No valid command to redo";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Undoable undoable = model.getUndoneCommand();
        String outputMessage = undoable.redo(model);
        return new CommandResult(MESSAGE_SUCCESS + "\n" + outputMessage);
    }
}
