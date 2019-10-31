package cs.f10.t1.nursetraverse.logic.commands;

import cs.f10.t1.nursetraverse.logic.commands.exceptions.CommandException;
import cs.f10.t1.nursetraverse.model.HistoryRecord;
import cs.f10.t1.nursetraverse.model.Model;

/**
 * Redoes the previous command if it was an undo.
 */
public class RedoCommand extends MutatorCommand {

    public static final String COMMAND_WORD = "app-redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Redoes the previous data-modifying command if it was an undo.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_REDO_SUCCESS = "Redid command:\n%s";
    public static final String MESSAGE_NO_MORE_HISTORY = "No more redo history.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            HistoryRecord redoneRecord = model.redo();
            return new CommandResult(String.format(MESSAGE_REDO_SUCCESS,
                    redoneRecord.getCommand().getCommandText().orElse(redoneRecord.toString())));
        } catch (IllegalStateException e) {
            throw new CommandException(MESSAGE_NO_MORE_HISTORY);
        }
    }
}
