package thrift.logic.commands;

import static java.util.Objects.requireNonNull;

import thrift.logic.commands.exceptions.CommandException;
import thrift.model.Model;

/**
 * Undo the previous undoable command in THRIFT.
 */
public class UndoCommand extends NonScrollingCommand {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo the previous undoable command.\n"
            + "Example: " + COMMAND_WORD;

    public static final String HELP_MESSAGE = COMMAND_WORD + ": Undo the previous undoable command.\n"
            + "Undoable commands are: add_expense, add_income, budget, clone, delete, tag, untag, update.\n"
            + "Format: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Undo successful.";

    public static final String NO_UNDOABLE_COMMAND = "No valid command to undo.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Undoable command = model.getPreviousUndoableCommand();
        String outputMessage = command.undo(model);
        return new CommandResult(MESSAGE_SUCCESS + "\n" + outputMessage);
    }
}
