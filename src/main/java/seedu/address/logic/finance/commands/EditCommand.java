package seedu.address.logic.finance.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_DAY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.model.finance.Model.PREDICATE_SHOW_ALL_LOG_ENTRIES;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.finance.commands.exceptions.CommandException;
import seedu.address.model.finance.Model;
import seedu.address.model.finance.logentry.Amount;
import seedu.address.model.finance.logentry.Description;
import seedu.address.model.finance.logentry.LogEntry;
import seedu.address.model.finance.logentry.TransactionDate;

/**
 * Edits the details of an existing log entry in the finance log.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the log entry identified "
            + "by the index number used in the displayed list of log entries. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_DAY + "TRANSACTION_DATE] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DAY + "91234567 "
            + PREFIX_DESCRIPTION + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited log entry: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<LogEntry> lastShownList = model.getFilteredLogEntryList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        LogEntry logEntryToEdit = lastShownList.get(index.getZeroBased());
        LogEntry editedLogEntry = createEditedPerson(logEntryToEdit, editPersonDescriptor);

        model.setLogEntry(logEntryToEdit, editedLogEntry);
        model.updateFilteredLogEntryList(PREDICATE_SHOW_ALL_LOG_ENTRIES);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedLogEntry));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static LogEntry createEditedPerson(LogEntry logEntryToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert logEntryToEdit != null;

        Amount updatedAmount = editPersonDescriptor.getAmount().orElse(logEntryToEdit.getAmount());
        TransactionDate updatedPhone = editPersonDescriptor.getTDate().orElse(logEntryToEdit.getTransactionDate());
        Description updatedEmail = editPersonDescriptor.getDesc().orElse(logEntryToEdit.getDescription());

        return new LogEntry(updatedAmount, updatedPhone, updatedEmail);
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
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Amount amount;
        private TransactionDate tDate;
        private Description desc;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setAmount(toCopy.amount);
            setTDate(toCopy.tDate);
            setDesc(toCopy.desc);
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

        public void setTDate(TransactionDate tDate) {
            this.tDate = tDate;
        }

        public Optional<TransactionDate> getTDate() {
            return Optional.ofNullable(tDate);
        }

        public void setDesc(Description desc) {
            this.desc = desc;
        }

        public Optional<Description> getDesc() {
            return Optional.ofNullable(desc);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getAmount().equals(e.getAmount())
                    && getTDate().equals(e.getTDate())
                    && getDesc().equals(e.getDesc());
        }
    }
}
