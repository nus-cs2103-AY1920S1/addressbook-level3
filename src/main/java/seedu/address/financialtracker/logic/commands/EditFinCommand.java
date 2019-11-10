package seedu.address.financialtracker.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.financialtracker.logic.parser.CliSyntax;
import seedu.address.financialtracker.model.Model;
import seedu.address.financialtracker.model.expense.Amount;
import seedu.address.financialtracker.model.expense.Country;
import seedu.address.financialtracker.model.expense.Date;
import seedu.address.financialtracker.model.expense.Description;
import seedu.address.financialtracker.model.expense.Expense;
import seedu.address.financialtracker.model.expense.Time;
import seedu.address.financialtracker.model.expense.Type;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Edits the details of an existing expense in the itinerary.
 */
public class EditFinCommand extends Command<Model> {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the expense identified "
            + "by the index number used in the displayed expense list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + CliSyntax.PREFIX_AMOUNT + "AMOUNT] "
            + "[" + CliSyntax.PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + CliSyntax.PREFIX_TYPE + "TYPE] "
            + "[" + CliSyntax.PREFIX_DATE + "DATE] "
            + "[" + CliSyntax.PREFIX_TIME + "TIME] "
            + "Example: " + COMMAND_WORD + " 1 "
            + CliSyntax.PREFIX_AMOUNT + "4"
            + CliSyntax.PREFIX_TYPE + "dinner";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided. ";
    private static final String MESSAGE_EDIT_EXPENSE_SUCCESS = "The expense updated successfully!";
    private static final String MESSAGE_DUPLICATE = "You have not changed anything compared to the expense!";

    private final Index index;
    private final EditExpenseDescriptor editExpenseDescriptor;

    /**
     * @param index of the expense in the expense list to edit
     * @param editExpenseDescriptor details to edit the expense with
     */
    public EditFinCommand(Index index, EditExpenseDescriptor editExpenseDescriptor) {
        requireNonNull(index);
        requireNonNull(editExpenseDescriptor);

        this.index = index;
        this.editExpenseDescriptor = new EditExpenseDescriptor(editExpenseDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Expense> lastShownList = model.getExpenseList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException("The expense index provided doesn't exist!");
        }

        Expense expenseToEdit = lastShownList.get(index.getZeroBased());
        Expense editedExpense = createEditedExpense(expenseToEdit, editExpenseDescriptor);

        if (expenseToEdit.equals(editedExpense)) { //can have duplicate
            throw new CommandException(MESSAGE_DUPLICATE);
        }

        model.setExpense(expenseToEdit, editedExpense);
        return new CommandResult(String.format(MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Expense createEditedExpense(Expense expenseToEdit, EditExpenseDescriptor editExpenseDescriptor) {
        assert expenseToEdit != null;

        Date updatedDate = editExpenseDescriptor.getDate().orElse(expenseToEdit.getDate());
        Time updatedTime = editExpenseDescriptor.getTime().orElse(expenseToEdit.getTime());
        Amount updatedAmount = editExpenseDescriptor.getAmount().orElse(expenseToEdit.getAmount());
        Description updatedDescription = editExpenseDescriptor.getDescription().orElse(expenseToEdit.getDescription());
        Type updatedType = editExpenseDescriptor.getType().orElse(expenseToEdit.getType());

        Expense expense = new Expense(updatedDate, updatedTime, updatedAmount, updatedDescription,
                updatedType, expenseToEdit.getCountry());

        return expense;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditFinCommand)) {
            return false;
        }

        // state check
        EditFinCommand e = (EditFinCommand) other;
        return index.equals(e.index)
                && editExpenseDescriptor.equals(e.editExpenseDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the expense.
     */
    public static class EditExpenseDescriptor {
        private Date date;
        private Time time;
        private Amount amount;
        private Description description;
        private Type type;

        public EditExpenseDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        EditExpenseDescriptor(EditExpenseDescriptor toCopy) {
            setDate(toCopy.date);
            setTime(toCopy.time);
            setAmount(toCopy.amount);
            setDescription(toCopy.description);
            setType(toCopy.type);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(date, time, amount, description, type);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setTime(Time time) {
            this.time = time;
        }

        public Optional<Time> getTime() {
            return Optional.ofNullable(time);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription () {
            return Optional.ofNullable(description);
        }

        public void setType(Type type) {
            this.type = type;
        }

        public Optional<Type> getType() {
            return Optional.ofNullable(type);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditExpenseDescriptor)) {
                return false;
            }

            // state check
            EditExpenseDescriptor e = (EditExpenseDescriptor) other;

            return getAmount().equals(e.getAmount())
                    && getDate().equals(e.getDate())
                    && getTime().equals(e.getTime())
                    && getDescription().equals(e.getDescription())
                    && getType().equals(e.getType());
        }
    }
}
