package seedu.address.logic.finance.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.finance.commands.exceptions.CommandException;
import seedu.address.model.finance.Model;
import seedu.address.model.finance.logentry.LogEntry;

/**
 * Deletes a log entry identified using it's displayed index from the finance log.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the log entry identified by the index number used "
            + "in the displayed list of log entries.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_LOG_ENTRY_SUCCESS = "Deleted log entry: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<LogEntry> lastShownList = model.getFilteredLogEntryList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOG_ENTRY_DISPLAYED_INDEX);
        }

        LogEntry logEntryToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteLogEntry(logEntryToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_LOG_ENTRY_SUCCESS, logEntryToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
