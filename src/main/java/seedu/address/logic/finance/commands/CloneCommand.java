package seedu.address.logic.finance.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.finance.commands.exceptions.CommandException;
import seedu.address.model.finance.Model;
import seedu.address.model.finance.attributes.Amount;
import seedu.address.model.finance.attributes.Category;
import seedu.address.model.finance.attributes.Description;
import seedu.address.model.finance.attributes.Person;
import seedu.address.model.finance.attributes.Place;
import seedu.address.model.finance.attributes.TransactionDate;
import seedu.address.model.finance.attributes.TransactionMethod;
import seedu.address.model.finance.logentry.BorrowLogEntry;
import seedu.address.model.finance.logentry.IncomeLogEntry;
import seedu.address.model.finance.logentry.LendLogEntry;
import seedu.address.model.finance.logentry.LogEntry;
import seedu.address.model.finance.logentry.SpendLogEntry;

/**
 * Clones a log entry identified using it's displayed index from the finance log.
 */
public class CloneCommand extends Command {

    public static final String COMMAND_WORD = "clone";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clones the log entry identified by the index number used "
            + "in the displayed list of log entries.\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_CLONED_LOG_ENTRY_SUCCESS = "Cloned log entry: %1$s";

    private final Index targetIndex;

    public CloneCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<LogEntry> lastShownList = model.getFilteredLogEntryList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOG_ENTRY_DISPLAYED_INDEX);
        }

        LogEntry logEntryToBeCloned = lastShownList.get(targetIndex.getZeroBased());
        // Clone details of log entry
        String logEntryType = logEntryToBeCloned.getLogEntryType();
        Amount amount = logEntryToBeCloned.getAmount();
        TransactionMethod tMethod = logEntryToBeCloned.getTransactionMethod();
        Set<Category> catSet = logEntryToBeCloned.getCategories();
        Description desc = logEntryToBeCloned.getDescription();
        TransactionDate tDate = new TransactionDate(); // Set transaction date as date of cloning

        LogEntry logEntryToAdd;
        switch (logEntryType) {
        case SpendLogEntry.LOG_ENTRY_TYPE:
            Place place = ((SpendLogEntry) logEntryToBeCloned).getPlace();
            logEntryToAdd = new SpendLogEntry(amount, tDate, desc, tMethod, catSet, place);
            break;
        case IncomeLogEntry.LOG_ENTRY_TYPE:
            Person from = ((IncomeLogEntry) logEntryToBeCloned).getFrom();
            logEntryToAdd = new IncomeLogEntry(amount, tDate, desc, tMethod, catSet, from);
            break;
        case BorrowLogEntry.LOG_ENTRY_TYPE:
            from = ((BorrowLogEntry) logEntryToBeCloned).getFrom();
            logEntryToAdd = new BorrowLogEntry(amount, tDate, desc, tMethod, catSet, from);
            break;
        case LendLogEntry.LOG_ENTRY_TYPE:
        default:
            Person to = ((LendLogEntry) logEntryToBeCloned).getTo();
            logEntryToAdd = new LendLogEntry(amount, tDate, desc, tMethod, catSet, to);
        }
        model.addLogEntry(logEntryToAdd);
        return new CommandResult(String.format(MESSAGE_CLONED_LOG_ENTRY_SUCCESS, logEntryToAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CloneCommand // instanceof handles nulls
                && targetIndex.equals(((CloneCommand) other).targetIndex)); // state check
    }
}
