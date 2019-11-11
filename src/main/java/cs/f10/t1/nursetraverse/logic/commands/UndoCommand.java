package cs.f10.t1.nursetraverse.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.logic.commands.exceptions.CommandException;
import cs.f10.t1.nursetraverse.model.HistoryRecord;
import cs.f10.t1.nursetraverse.model.Model;
import javafx.collections.ObservableList;

/**
 * Undos a designated command in the history, or the previous one if no argument is specified
 */
public class UndoCommand extends MutatorCommand {

    public static final String COMMAND_WORD = "app-undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undoes the previous data-modifying command. If an index is specified, undoes commands up to"
            + "and including the command identified by that index as used in the displayed history list.\n"
            + "Parameters: [INDEX (must be a positive integer)]\n"
            + "Examples:\n"
            + COMMAND_WORD + "\n"
            + COMMAND_WORD + " 3";

    public static final String MESSAGE_UNDO_SUCCESS = "Undid %d command%s:\n%s";
    public static final String MESSAGE_NO_MORE_HISTORY = "No more undo history.";
    public static final String MESSAGE_NO_SUCH_INDEX = "There is no command with that index.";

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

        HistoryRecord targetRecord;
        try {
            targetRecord = history.get(targetIndex.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_NO_SUCH_INDEX);
        }

        List<HistoryRecord> poppedRecords = model.undoTo(targetRecord);
        return new CommandResult(makeResultString(poppedRecords));
    }

    /**
     * Creates a formatted String listing the undone commands.
     */
    public static String makeResultString(List<HistoryRecord> undoneRecords) {
        StringBuilder sb = new StringBuilder();
        for (HistoryRecord record : undoneRecords) {
            sb.append(record.getCommand().getCommandText().orElse(record.toString()))
                    .append("\n");
        }
        return String.format(MESSAGE_UNDO_SUCCESS,
                undoneRecords.size(), undoneRecords.size() > 1 ? "s" : "", sb.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UndoCommand)) {
            return false;
        }
        UndoCommand otherCommand = (UndoCommand) obj;
        return targetIndex == otherCommand.targetIndex // handle null
                || targetIndex.equals(otherCommand.targetIndex);
    }
}
