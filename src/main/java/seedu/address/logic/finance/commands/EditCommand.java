package seedu.address.logic.finance.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_DAY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_FROM;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_PLACE;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_TO;
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
import seedu.address.model.finance.attributes.Person;
import seedu.address.model.finance.attributes.Place;
import seedu.address.model.finance.attributes.RepaidDate;
import seedu.address.model.finance.attributes.TransactionDate;
import seedu.address.model.finance.attributes.TransactionMethod;
import seedu.address.model.finance.logentry.BorrowLogEntry;
import seedu.address.model.finance.logentry.IncomeLogEntry;
import seedu.address.model.finance.logentry.LendLogEntry;
import seedu.address.model.finance.logentry.LogEntry;
import seedu.address.model.finance.logentry.SpendLogEntry;

/**
 * Edits the details of an existing log entry in the finance log.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_EDIT_LOG_ENTRY_SUCCESS = "Edited log entry: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one relevant field to edit must be provided.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the log entry identified "
            + "by the index number used in the displayed list of log entries. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_DAY + "DAY] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_TRANSACTION_METHOD + "TRANSACTION_METHOD] "
            + "[" + PREFIX_CATEGORY + "CATEGORY]... "
            + "[" + PREFIX_PLACE + "PLACE] (only for Spend) "
            + "[" + PREFIX_FROM + "SOURCE/PERSON_BORROWED_FROM] (only for Income, Borrow) "
            + "[" + PREFIX_TO + "PERSON_LENT_TO] (only for Lend)\n"
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

        // If is borrow or lend log entry, cannot be edited
        if ((logEntryToEditType.equals("borrow")
                && ((BorrowLogEntry) logEntryToEdit).isRepaid())
                || (logEntryToEditType.equals("lend")
                && ((LendLogEntry) logEntryToEdit).isRepaid())) {
            throw new CommandException("Repaid entries cannot be edited.");
        }

        LogEntry editedLogEntry;
        editedLogEntry = createEditedLogEntry(logEntryToEdit, editLogEntryDescriptor, logEntryToEditType);

        model.setLogEntry(logEntryToEdit, editedLogEntry);
        model.updateFilteredLogEntryList(PREDICATE_SHOW_ALL_LOG_ENTRIES);
        return new CommandResult(String.format(MESSAGE_EDIT_LOG_ENTRY_SUCCESS, editedLogEntry));
    }

    /**
     * Creates and returns a {@code LogEntry} with the details of {@code logEntryToEdit}
     * edited with {@code editLogEntryDescriptor}.
     */
    private LogEntry createEditedLogEntry(LogEntry logEntryToEdit,
                                          EditLogEntryDescriptor editLogEntryDescriptor,
                                          String logEntryToEditType) throws CommandException {
        assert logEntryToEdit != null;

        LogEntry currLogEntryToEdit = logEntryToEdit;
        Amount updatedAmount = editLogEntryDescriptor.getAmount().orElse(currLogEntryToEdit.getAmount());
        TransactionDate updatedTransactionDate = editLogEntryDescriptor.getTransactionDate()
                .orElse(currLogEntryToEdit.getTransactionDate());
        Description updatedDescription = editLogEntryDescriptor.getDesc().orElse(currLogEntryToEdit.getDescription());
        TransactionMethod updatedTransactionMethod = editLogEntryDescriptor.getTransactionMethod()
                .orElse(currLogEntryToEdit.getTransactionMethod());
        Set<Category> updatedCategories = editLogEntryDescriptor.getCategories()
                .orElse(currLogEntryToEdit.getCategories());

        switch (logEntryToEditType) {
        case SpendLogEntry.LOG_ENTRY_TYPE:
            SpendLogEntry currSpendLogEntry = (SpendLogEntry) currLogEntryToEdit;
            Place updatedPlace = editLogEntryDescriptor.getPlace().orElse(currSpendLogEntry.getPlace());
            return new SpendLogEntry(updatedAmount, updatedTransactionDate, updatedDescription,
                    updatedTransactionMethod, updatedCategories, updatedPlace);
        case IncomeLogEntry.LOG_ENTRY_TYPE:
            IncomeLogEntry currIncomeLogEntry = (IncomeLogEntry) currLogEntryToEdit;
            Person updatedFrom = editLogEntryDescriptor.getFrom().orElse(currIncomeLogEntry.getFrom());
            return new IncomeLogEntry(updatedAmount, updatedTransactionDate, updatedDescription,
                    updatedTransactionMethod, updatedCategories, updatedFrom);
        case BorrowLogEntry.LOG_ENTRY_TYPE:
            BorrowLogEntry currBorrowLogEntry = (BorrowLogEntry) currLogEntryToEdit;
            updatedFrom = editLogEntryDescriptor.getFrom().orElse(currBorrowLogEntry.getFrom());
            boolean isRepaid = currBorrowLogEntry.isRepaid();
            // Cannot edit transaction date if is repaid
            BorrowLogEntry updatedBorrowLogEntry;
            if (isRepaid) {
                TransactionDate transactionDate = currBorrowLogEntry.getTransactionDate();
                RepaidDate repaidDate = currBorrowLogEntry.getRepaidDate();
                updatedBorrowLogEntry = new BorrowLogEntry(
                        updatedAmount, transactionDate, updatedDescription,
                        updatedTransactionMethod, updatedCategories, updatedFrom);
                updatedBorrowLogEntry.markAsRepaid();
                updatedBorrowLogEntry.setRepaidDate(repaidDate.value, transactionDate.value);
            } else {
                updatedBorrowLogEntry = new BorrowLogEntry(
                        updatedAmount, updatedTransactionDate, updatedDescription,
                        updatedTransactionMethod, updatedCategories, updatedFrom);
            }
            return updatedBorrowLogEntry;
        case LendLogEntry.LOG_ENTRY_TYPE:
            LendLogEntry currLendLogEntry = (LendLogEntry) currLogEntryToEdit;
            Person updatedTo = editLogEntryDescriptor.getTo().orElse(currLendLogEntry.getTo());
            isRepaid = currLendLogEntry.isRepaid();
            LendLogEntry updatedLendLogEntry;
            if (isRepaid) {
                TransactionDate transactionDate = currLendLogEntry.getTransactionDate();
                RepaidDate repaidDate = currLendLogEntry.getRepaidDate();
                updatedLendLogEntry = new LendLogEntry(
                        updatedAmount, transactionDate, updatedDescription,
                        updatedTransactionMethod, updatedCategories, updatedTo);
                updatedLendLogEntry.markAsRepaid();
                updatedLendLogEntry.setRepaidDate(repaidDate.value, transactionDate.value);
            } else {
                updatedLendLogEntry = new LendLogEntry(
                        updatedAmount, updatedTransactionDate, updatedDescription,
                        updatedTransactionMethod, updatedCategories, updatedTo);
            }
            return updatedLendLogEntry;
        default:
            throw new CommandException("Error in editing log entry!");
        }
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
        private Person from;
        private Person to;

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
            setFrom(toCopy.from);
            setTo(toCopy.to);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(amount, tDate, desc, tMethod,
                    cats, place, from, to);
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
        public void setCategories(Set<Category> cats) {
            this.cats = (cats != null) ? new HashSet<>(cats) : null;
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

        public void setFrom(Person from) {
            this.from = from;
        }

        public Optional<Person> getFrom() {
            return Optional.ofNullable(from);
        }

        public void setTo(Person to) {
            this.to = to;
        }

        public Optional<Person> getTo() {
            return Optional.ofNullable(to);
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
                    && getPlace().equals(e.getPlace())
                    && getFrom().equals(e.getFrom())
                    && getTo().equals(e.getTo());
        }
    }
}
