package seedu.address.logic.commands.expense.edit;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.isAllPresent;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expense.DayNumber;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.MiscExpense;
import seedu.address.model.expense.PlannedExpense;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Name;

/**
 *
 * Command that attempts to modify the current values in the expense setup page.
 *
 */
public class EditExpenseFieldCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of your form "
            + "by the index of the form field as displayed, or by the various prefixes of the fields. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_BUDGET + "AMOUNT IN SGD] "
            + "[" + PREFIX_DAY_NUMBER + "DAY NUMBER] "
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_BUDGET + "10" + PREFIX_DAY_NUMBER + "1";

    public static final String MESSAGE_NOT_EDITED = "At least one field must be provided!";
    public static final String MESSAGE_EDIT_SUCCESS = "Edited the current form:%1$s";
    public static final String MESSAGE_NOT_EDITABLE = "The expense is linked to an event, only the amount can be "
            + "edited. To change name and day number, please edit the corresponding event";
    public static final String MESSAGE_BUILD_FAILED = "Error in building a new expense";

    private final EditExpenseDescriptor editExpenseDescriptor;

    /**
     * @param editExpenseDescriptor details to edit the expense with
     */
    public EditExpenseFieldCommand(EditExpenseDescriptor editExpenseDescriptor) {
        requireNonNull(editExpenseDescriptor);

        this.editExpenseDescriptor = editExpenseDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Expense expense = model.getPageStatus().getExpense();
        boolean hasName = editExpenseDescriptor.getName().isPresent();
        boolean hasDayNumber = editExpenseDescriptor.getDayNumber().isPresent();
        if (expense != null) {
            if (expense instanceof PlannedExpense && (hasName || hasDayNumber)) {
                throw new CommandException(MESSAGE_NOT_EDITABLE);
            }
        }
        EditExpenseDescriptor currentDescriptor = model.getPageStatus().getEditExpenseDescriptor();
        EditExpenseDescriptor newEditExpenseDescriptor = currentDescriptor == null
                ? new EditExpenseDescriptor(editExpenseDescriptor)
                : new EditExpenseDescriptor(currentDescriptor, editExpenseDescriptor);

        model.setPageStatus(
                model.getPageStatus().withNewEditExpenseDescriptor(newEditExpenseDescriptor));

        return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS, editExpenseDescriptor));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditExpenseFieldCommand)) {
            return false;
        }

        // state check
        EditExpenseFieldCommand e = (EditExpenseFieldCommand) other;
        return editExpenseDescriptor.equals(e.editExpenseDescriptor);
    }

    /**
     * Stores the details to edit the expense with. Each non-empty field value will replace the
     * corresponding field value of the expense.
     */
    public static class EditExpenseDescriptor {
        private Optional<Name> name;
        private Optional<Budget> budget;
        private Optional<DayNumber> dayNumber;

        public EditExpenseDescriptor() {
            name = Optional.empty();
            budget = Optional.empty();
            dayNumber = Optional.empty();
        }

        /**
         * Copy constructor from an {@code EditExpenseDescriptor}.
         */
        public EditExpenseDescriptor(EditExpenseDescriptor toCopy) {
            name = toCopy.getName();
            budget = toCopy.getBudget();
            dayNumber = toCopy.getDayNumber();
        }


        /**
         * Copy constructor from another {@code Expense}.
         */
        public EditExpenseDescriptor(Expense toCopy) {
            setName(toCopy.getName());
            setBudget(toCopy.getBudget());
            setDayNumber(toCopy.getDayNumber());
        }


        /**
         * Overwrite constructor.
         * Constructs a new {@code EditExpenseDescriptor} using an {@code oldDescriptor}, overwritten with
         * values of the {@code newDescriptor} where they exist.
         *
         * @param oldDescriptor Old {@code EditExpenseDescriptor} to use.
         * @param newDescriptor New {@code EditExpenseDescriptor} to use.
         */
        public EditExpenseDescriptor(EditExpenseDescriptor oldDescriptor,
                                 EditExpenseDescriptor newDescriptor) {
            this();
            newDescriptor.name.ifPresentOrElse(this::setName, () ->
                    oldDescriptor.name.ifPresent(this::setName));

            newDescriptor.budget.ifPresentOrElse(this::setBudget, () ->
                    oldDescriptor.budget.ifPresent(this::setBudget));

            newDescriptor.dayNumber.ifPresentOrElse(this::setDayNumber, () ->
                    oldDescriptor.dayNumber.ifPresent(this::setDayNumber));
        }


        /**
         * Builds a new {@code Expense} instance.
         * Requires name, budget and day number to have been set minimally.
         *
         * @return New {@code Expense} created.
         * @throws NullPointerException If any of the fields are empty.
         */
        public Expense buildExpense() throws NullPointerException {
            if (isAllPresent(name, budget, dayNumber)) {
                return new MiscExpense(name.get(), budget.get(), dayNumber.get());
            } else {
                throw new NullPointerException();
            }
        }

        /**
         * Builds an edited {@code Expense} instance from this {@code EditExpenseDescriptor}.
         * Uses the original expense information first, overwriting where the values exist.
         *
         * @param expense Source {@code Expense} instance.
         * @return Edited {@code Expense} instance.
         */
        public Expense buildExpense(Expense expense) {
            Name expenseName = expense.getName();
            Budget budget = expense.getBudget();
            DayNumber dayNumber = expense.getDayNumber();

            if (this.name.isPresent()) {
                expenseName = this.name.get();
            }
            if (this.budget.isPresent()) {
                budget = this.budget.get();
            }
            if (this.dayNumber.isPresent()) {
                dayNumber = this.dayNumber.get();
            }

            if (expense instanceof PlannedExpense) {
                return new PlannedExpense(expenseName, budget, dayNumber);
            } else if (expense instanceof MiscExpense) {
                return new MiscExpense(expenseName, budget, dayNumber);
            } else {
                throw new AssertionError(MESSAGE_BUILD_FAILED);
            }
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(name, budget, dayNumber);
        }


        public void setName(Name name) {
            this.name = Optional.of(name);
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setBudget(Budget budget) {
            this.budget = Optional.of(budget);
        }

        public Optional<Budget> getBudget() {
            return budget;
        }

        public void setDayNumber(DayNumber dayNumber) {
            this.dayNumber = Optional.of(dayNumber);
        }

        public Optional<DayNumber> getDayNumber() {
            return dayNumber;
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

            return getName().equals(e.getName())
                    && getBudget().equals(e.getBudget())
                    && getDayNumber().equals(e.getDayNumber());
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();

            this.name.ifPresent(name -> builder.append(" Name of expense: ").append(name));
            this.budget.ifPresent(budget -> builder.append(" Amount of expense ( in SGD): $").append(budget));
            this.dayNumber.ifPresent(dayNumber -> builder.append(" Day ").append(dayNumber));

            return builder.toString();
        }
    }
}
