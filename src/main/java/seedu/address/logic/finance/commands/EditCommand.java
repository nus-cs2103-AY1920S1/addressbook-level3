package seedu.address.logic.finance.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_DAY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_PLACE;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_TRANSACTION_METHOD;
import static seedu.address.model.finance.Model.PREDICATE_SHOW_ALL_LOG_ENTRIES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.finance.commands.exceptions.CommandException;
import seedu.address.model.finance.Model;
import seedu.address.model.finance.attributes.Amount;
import seedu.address.model.finance.attributes.Category;
import seedu.address.model.finance.attributes.Description;
import seedu.address.model.finance.attributes.Place;
import seedu.address.model.finance.attributes.TransactionDate;
import seedu.address.model.finance.attributes.TransactionMethod;
import seedu.address.model.finance.logentry.LogEntry;
import seedu.address.model.finance.logentry.SpendLogEntry;

/**
 * Edits the details of an existing log entry in the finance log.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_EDIT_LOG_ENTRY_SUCCESS = "Edited log entry: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the log entry identified "
            + "by the index number used in the displayed list of log entries. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_DAY + "DAY] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_TRANSACTION_METHOD + "TRANSACTION_METHOD] "
            + "[" + PREFIX_CATEGORY + "CATEGORY]..."
            + "[" + PREFIX_PLACE + "PLACE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DAY + "12-02-2019 "
            + PREFIX_CATEGORY + "Gift";

    private final Index index;
    private final EditLogEntryDescriptor editLogEntryDescriptor;

    /**
     * @param index of the log entry in the filtered list of log entries to edit
     * @param editLogEntryDescriptor details to edit the log entry with
     */
    public EditCommand(Index index, EditLogEntryDescriptor editLogEntryDescriptor) {
        requireNonNull(index);
        requireNonNull(editLogEntryDescriptor);

        this.index = index;
        this.editLogEntryDescriptor = new EditLogEntryDescriptor(editLogEntryDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<LogEntry> lastShownList = model.getFilteredLogEntryList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOG_ENTRY_DISPLAYED_INDEX);
        }

        LogEntry logEntryToEdit = lastShownList.get(index.getZeroBased());

        // Depending on log entry type, create appropriate edited log entry
        String logEntryToEditType = logEntryToEdit.getLogEntryType();
        LogEntry editedLogEntry;

        switch (logEntryToEditType) {
        case SpendLogEntry.LOG_ENTRY_TYPE:
            editedLogEntry = createEditedSpendLogEntry(logEntryToEdit, editLogEntryDescriptor);
            break;
        default:
            throw new CommandException("Error occurred in editing log entry!");
        }

        model.setLogEntry(logEntryToEdit, editedLogEntry);
        model.updateFilteredLogEntryList(PREDICATE_SHOW_ALL_LOG_ENTRIES);
        return new CommandResult(String.format(MESSAGE_EDIT_LOG_ENTRY_SUCCESS, editedLogEntry));
    }

    /**
     * Creates and returns a {@code LogEntry} with the details of {@code logEntryToEdit}
     * edited with {@code editLogEntryDescriptor}.
     */
    private LogEntry createEditedSpendLogEntry(LogEntry logEntryToEdit, EditLogEntryDescriptor editLogEntryDescriptor) {
        assert logEntryToEdit != null;

        SpendLogEntry currLogEntryToEdit = (SpendLogEntry) logEntryToEdit;
        Amount updatedAmount = editLogEntryDescriptor.getAmount().orElse(currLogEntryToEdit.getAmount());
        TransactionDate updatedTransactionDate = editLogEntryDescriptor.getTransactionDate()
                .orElse(currLogEntryToEdit.getTransactionDate());
        Description updatedDescription = editLogEntryDescriptor.getDesc().orElse(currLogEntryToEdit.getDescription());
        TransactionMethod updatedTransactionMethod = editLogEntryDescriptor.getTransactionMethod()
                .orElse(currLogEntryToEdit.getTransactionMethod());
        Set<Category> updatedCategories = editLogEntryDescriptor.getCategories()
                .orElse(currLogEntryToEdit.getCategories());
        Place updatedPlace = editLogEntryDescriptor.getPlace().orElse(currLogEntryToEdit.getPlace());

        return new SpendLogEntry(updatedAmount, updatedTransactionDate, updatedDescription,
                    updatedTransactionMethod, updatedCategories, updatedPlace);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editLogEntryDescriptor.equals(e.editLogEntryDescriptor);
    }

    /**
     * Stores the details to edit the log entry with. Each non-empty field value will replace the
     * corresponding field value of the log entry.
     */
    public static class EditLogEntryDescriptor {
        private Amount amount;
        private TransactionDate tDate;
        private Description desc;
        private TransactionMethod tMethod;
        private Set<Category> cats;
        private Place place;

        public EditLogEntryDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code cats} is used internally.
         */
        public EditLogEntryDescriptor(EditLogEntryDescriptor toCopy) {
            setAmount(toCopy.amount);
            setTransactionDate(toCopy.tDate);
            setDesc(toCopy.desc);
            setTMethod(toCopy.tMethod);
            setCategories(toCopy.cats);
            setPlace(toCopy.place);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(amount, tDate, desc);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setTransactionDate(TransactionDate tDate) {
            this.tDate = tDate;
        }

        public Optional<TransactionDate> getTransactionDate() {
            return Optional.ofNullable(tDate);
        }

        public void setDesc(Description desc) {
            this.desc = desc;
        }

        public Optional<Description> getDesc() {
            return Optional.ofNullable(desc);
        }

        public void setTMethod(TransactionMethod transactionMethod) {
            this.tMethod = transactionMethod;
        }

        public Optional<TransactionMethod> getTransactionMethod() {
            return Optional.ofNullable(tMethod);
        }

        /**
         * Sets {@code cats} to this object's {@code cats}.
         * A defensive copy of {@code cats} is used internally.
         */
        public void setCategories(Set<Category> taskTags) {
            this.cats = (taskTags != null) ? new HashSet<>(taskTags) : null;
        }

        /**
         * Returns an unmodifiable set of categories, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code cats} is null.
         */
        public Optional<Set<Category>> getCategories() {
            return (cats != null) ? Optional.of(Collections.unmodifiableSet(cats)) : Optional.empty();
        }

        public void setPlace(Place place) {
            this.place = place;
        }

        public Optional<Place> getPlace() {
            return Optional.ofNullable(place);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditLogEntryDescriptor)) {
                return false;
            }

            // state check
            EditLogEntryDescriptor e = (EditLogEntryDescriptor) other;

            return getAmount().equals(e.getAmount())
                    && getTransactionDate().equals(e.getTransactionDate())
                    && getDesc().equals(e.getDesc())
                    && getTransactionMethod().equals(e.getTransactionMethod())
                    && getCategories().equals(e.getCategories())
                    && getPlace().equals(e.getPlace());
        }
    }
}
