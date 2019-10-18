package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.HistoryRecord;
import seedu.address.model.Model;

/**
 * Undos a designated command in the history, or the previous one if no argument is specified
 */
public class UndoCommand extends Command implements MutatorCommand {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_SUCCESS = "Undid command:\n%s";
    public static final String MESSAGE_NO_MORE_HISTORY = "Cannot undo: no more undo history";

    private Index targetIndex;

    public UndoCommand() {
        targetIndex = null;
    }

    public UndoCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<HistoryRecord> history = model.getHistory();

        if (history.isEmpty()) {
            throw new CommandException(MESSAGE_NO_MORE_HISTORY);
        }

        // No arguments specified: undo the previous command
        if (targetIndex == null) {
            targetIndex = Index.fromZeroBased(history.size() - 1);
        }

        HistoryRecord targetRecord = history.get(targetIndex.getZeroBased());
        List<HistoryRecord> poppedRecords = model.revertTo(targetRecord);
        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS,
                CollectionUtil.collectionToStringShowingIndexes(poppedRecords)));
    }
}
