package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.HistoryRecord;
import seedu.address.model.Model;

/**
 * Undos a designated command in the history, or the previous one if no argument is specified
 */
public class UndoCommand extends Command implements MutatorCommand {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_SUCCESS = "Undid command '%s'";
    public static final String MESSAGE_NO_MORE_HISTORY = "Cannot undo: no more undo history";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<HistoryRecord> history = model.getHistory();

        if (history.isEmpty()) {
            throw new CommandException(MESSAGE_NO_MORE_HISTORY);
        }

        HistoryRecord targetRecord = history.get(history.size() - 1);
        model.revertTo(targetRecord);
        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS,
                targetRecord.getCommand().getClass().getSimpleName()));
    }
}
