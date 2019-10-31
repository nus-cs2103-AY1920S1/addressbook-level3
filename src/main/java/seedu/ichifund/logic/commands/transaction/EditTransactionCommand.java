package seedu.ichifund.logic.commands.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_TRANSACTION_TYPE;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.List;
import java.util.Optional;

import seedu.ichifund.commons.core.Messages;
import seedu.ichifund.commons.core.index.Index;
import seedu.ichifund.commons.util.CollectionUtil;
import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.date.Day;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.repeater.RepeaterUniqueId;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.Transaction;
import seedu.ichifund.model.transaction.TransactionType;

/**
 * Edits the details of an existing transaction in IchiFund.
 */
public class EditTransactionCommand extends Command {

    public static final String COMMAND_WORD = "edittx";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the transaction identified "
            + "by the index number used in the displayed transaction list. "
            + "Existing values will be overwritten by the input values. "
            + "Transaction must not be from a repeatable.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_CATEGORY + "CATEGORY] "
            + "[" + PREFIX_DAY + "DAY] "
            + "[" + PREFIX_MONTH + "MONTH] "
            + "[" + PREFIX_YEAR + "YEAR] "
            + "[" + PREFIX_TRANSACTION_TYPE + "TRANSACTION_TYPE] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + "Buy lunch "
            + PREFIX_AMOUNT + "5.28 "
            + PREFIX_DAY + "5 "
            + PREFIX_MONTH + "10 ";

    public static final String MESSAGE_EDIT_TRANSACTION_SUCCESS = "Edited Transaction: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditTransactionDescriptor editTransactionDescriptor;

    /**
     * @param index of the transaction in the filtered transaction list to edit
     * @param editTransactionDescriptor details to edit the transaction with
     */
    public EditTransactionCommand(Index index, EditTransactionDescriptor editTransactionDescriptor) {
        requireNonNull(index);
        requireNonNull(editTransactionDescriptor);

        this.index = index;
        this.editTransactionDescriptor = new EditTransactionDescriptor(editTransactionDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Transaction> lastShownList = model.getFilteredTransactionList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        Transaction transactionToEdit = lastShownList.get(index.getZeroBased());

        if (transactionToEdit.isFromRepeater()) {
            throw new CommandException(Messages.MESSAGE_TRANSACTION_FROM_REPEATER);
        }

        Transaction editedTransaction = createEditedTransaction(transactionToEdit, editTransactionDescriptor);

        model.setTransaction(transactionToEdit, editedTransaction);
        model.updateTransactionContext(editedTransaction);
        return new CommandResult(String.format(MESSAGE_EDIT_TRANSACTION_SUCCESS, editedTransaction));
    }

    /**
     * Creates and returns a {@code Transaction} with the details of {@code transactionToEdit}
     * edited with {@code editTransactionDescriptor}.
     */
    private static Transaction createEditedTransaction(
            Transaction transactionToEdit, EditTransactionDescriptor editTransactionDescriptor)
            throws CommandException {
        assert transactionToEdit != null;

        Description updatedDescription = editTransactionDescriptor.getDescription()
                .orElse(transactionToEdit.getDescription());
        Amount updatedAmount = editTransactionDescriptor.getAmount().orElse(transactionToEdit.getAmount());
        Day updatedDay = editTransactionDescriptor.getDay().orElse(transactionToEdit.getDay());
        Month updatedMonth = editTransactionDescriptor.getMonth().orElse(transactionToEdit.getMonth());
        Year updatedYear = editTransactionDescriptor.getYear().orElse(transactionToEdit.getYear());
        Category updatedCategory = editTransactionDescriptor.getCategory().orElse(transactionToEdit.getCategory());
        TransactionType updatedTransactionType = editTransactionDescriptor.getTransactionType()
                .orElse(transactionToEdit.getTransactionType());

        if (!Date.isValidDate(updatedDay, updatedMonth, updatedYear)) {
            throw new CommandException(Date.MESSAGE_CONSTRAINTS);
        }

        Date updatedDate = new Date(updatedDay, updatedMonth, updatedYear);

        return new Transaction(updatedDescription, updatedAmount, updatedCategory,
                updatedDate, updatedTransactionType, new RepeaterUniqueId(""));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTransactionCommand)) {
            return false;
        }

        // state check
        EditTransactionCommand e = (EditTransactionCommand) other;
        return index.equals(e.index)
                && editTransactionDescriptor.equals(e.editTransactionDescriptor);
    }

    /**
     * Stores the details to edit the transaction with. Each non-empty field value will replace the
     * corresponding field value of the transaction.
     */
    public static class EditTransactionDescriptor {
        private Description description;
        private Amount amount;
        private Day day;
        private Month month;
        private Year year;
        private Category category;
        private TransactionType transactionType;

        public EditTransactionDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditTransactionDescriptor(EditTransactionDescriptor toCopy) {
            setDescription(toCopy.description);
            setAmount(toCopy.amount);
            setDay(toCopy.day);
            setMonth(toCopy.month);
            setYear(toCopy.year);
            setCategory(toCopy.category);
            setTransactionType(toCopy.transactionType);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(description, amount, day, month, year, category, transactionType);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setDay(Day day) {
            this.day = day;
        }

        public Optional<Day> getDay() {
            return Optional.ofNullable(day);
        }

        public void setMonth(Month month) {
            this.month = month;
        }

        public Optional<Month> getMonth() {
            return Optional.ofNullable(month);
        }

        public void setYear(Year year) {
            this.year = year;
        }

        public Optional<Year> getYear() {
            return Optional.ofNullable(year);
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public Optional<Category> getCategory() {
            return Optional.ofNullable(category);
        }

        public void setTransactionType(TransactionType transactionType) {
            this.transactionType = transactionType;
        }

        public Optional<TransactionType> getTransactionType() {
            return Optional.ofNullable(transactionType);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTransactionDescriptor)) {
                return false;
            }

            // state check
            EditTransactionDescriptor e = (EditTransactionDescriptor) other;

            return getDescription().equals(e.getDescription())
                    && getAmount().equals(e.getAmount())
                    && getDay().equals(e.getDay())
                    && getMonth().equals(e.getMonth())
                    && getYear().equals(e.getYear())
                    && getCategory().equals(e.getCategory())
                    && getTransactionType().equals(e.getTransactionType());
        }
    }
}
