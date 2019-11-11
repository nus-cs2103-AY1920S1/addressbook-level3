package seedu.address.logic.finance.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.finance.commands.exceptions.CommandException;
import seedu.address.model.finance.Model;
import seedu.address.model.finance.logentry.BorrowLogEntry;
import seedu.address.model.finance.logentry.LendLogEntry;
import seedu.address.model.finance.logentry.LogEntry;

/**
 * Marks a log entry of type Borrow of Lend as repaid,
 * identified using it's displayed index from the finance log.
 */
public class RepaidCommand extends Command {

    public static final String COMMAND_WORD = "repaid";
    public static final String MESSAGE_REPAID_LOGENTRY =
            "This log entry has already been marked repaid in the finance log.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the log entry identified by the index number used "
            + "in the displayed list of log entries.\n"
            + "Log entry must be of type BORROW or LEND.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_REPAID_LOG_ENTRY_SUCCESS = "Marked log entry as repaid: %1$s";

    private final Index targetIndex;

    public RepaidCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<LogEntry> lastShownList = model.getFilteredLogEntryList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOG_ENTRY_DISPLAYED_INDEX);
        }

        LogEntry logEntryToMarkRepaid = lastShownList.get(targetIndex.getZeroBased());

        boolean isBorrowLogEntryType = logEntryToMarkRepaid instanceof BorrowLogEntry;
        boolean isLendLogEntryType = logEntryToMarkRepaid instanceof LendLogEntry;
        if (!isBorrowLogEntryType && !isLendLogEntryType) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOG_ENTRY_DISPLAYED_INDEX);
        }

        assert logEntryToMarkRepaid instanceof BorrowLogEntry
               || logEntryToMarkRepaid instanceof LendLogEntry;

        if (isBorrowLogEntryType && ((BorrowLogEntry) logEntryToMarkRepaid).isRepaid()) {
            throw new CommandException(MESSAGE_REPAID_LOGENTRY);
        }
        if (isLendLogEntryType && ((LendLogEntry) logEntryToMarkRepaid).isRepaid()) {
            throw new CommandException(MESSAGE_REPAID_LOGENTRY);
        }

        model.markLogEntryAsRepaid(
                isBorrowLogEntryType ? (BorrowLogEntry) logEntryToMarkRepaid
                        : (LendLogEntry) logEntryToMarkRepaid);
        return new CommandResult(String.format(MESSAGE_REPAID_LOG_ENTRY_SUCCESS, logEntryToMarkRepaid));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RepaidCommand // instanceof handles nulls
                && targetIndex.equals(((RepaidCommand) other).targetIndex)); // state check
    }
}
