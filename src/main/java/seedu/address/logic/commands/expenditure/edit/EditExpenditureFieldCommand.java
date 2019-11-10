package seedu.address.logic.commands.expenditure.edit;

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
import seedu.address.model.expenditure.DayNumber;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Name;

/**
 *
 * Constructs a command that attempts to modify the current values in the edit expenditure page.
 *
 */
public class EditExpenditureFieldCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of your form "
            + "by the index of the form field as displayed, or by the various prefixes of the fields. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_BUDGET + "TOTAL BUDGET] "
            + "[" + PREFIX_DAY_NUMBER + "DAY NUMBER] "
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_BUDGET + "10" + PREFIX_DAY_NUMBER + "1";

    public static final String MESSAGE_NOT_EDITED = "At least one field must be provided!";
    public static final String MESSAGE_EDIT_SUCCESS = "Edited the current form:%1$s";
    public static final String MESSAGE_NOT_EDITABLE = "The expenditure is linked to an event, only the amount can be "
            + "edited. To change name and day number, please edit the corresponding event";

    private final EditExpenditureDescriptor editExpenditureDescriptor;

    /**
     * @param editExpenditureDescriptor details to edit the expenditure with
     */
    public EditExpenditureFieldCommand(EditExpenditureDescriptor editExpenditureDescriptor) {
        requireNonNull(editExpenditureDescriptor);

        this.editExpenditureDescriptor = editExpenditureDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Expenditure expenditure = model.getPageStatus().getExpenditure();
        boolean hasName = editExpenditureDescriptor.getName().isPresent();
        boolean hasDayNumber = editExpenditureDescriptor.getDayNumber().isPresent();
        if (expenditure != null) {
            if (!expenditure.getRemovability() && (hasName || hasDayNumber)) {
                throw new CommandException(MESSAGE_NOT_EDITABLE);
            }
        }
        EditExpenditureDescriptor currentDescriptor = model.getPageStatus().getEditExpenditureDescriptor();
        EditExpenditureDescriptor newEditExpenditureDescriptor = currentDescriptor == null
                ? new EditExpenditureDescriptor(editExpenditureDescriptor)
                : new EditExpenditureDescriptor(currentDescriptor, editExpenditureDescriptor);

        model.setPageStatus(
                model.getPageStatus().withNewEditExpenditureDescriptor(newEditExpenditureDescriptor));

        return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS, editExpenditureDescriptor));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditExpenditureFieldCommand)) {
            return false;
        }

        // state check
        EditExpenditureFieldCommand e = (EditExpenditureFieldCommand) other;
        return editExpenditureDescriptor.equals(e.editExpenditureDescriptor);
    }

    /**
     * Stores the details to edit the expenditure with. Each non-empty field value will replace the
     * corresponding field value of the expenditure.
     */
    public static class EditExpenditureDescriptor {
        private Optional<Name> name;
        private Optional<Budget> budget;
        private Optional<DayNumber> dayNumber;

        public EditExpenditureDescriptor() {
            name = Optional.empty();
            budget = Optional.empty();
            dayNumber = Optional.empty();
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditExpenditureDescriptor(EditExpenditureDescriptor toCopy) {
            name = toCopy.getName();
            budget = toCopy.getBudget();
            dayNumber = toCopy.getDayNumber();
        }


        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditExpenditureDescriptor(Expenditure toCopy) {
            setName(toCopy.getName());
            setBudget(toCopy.getBudget());
            setDayNumber(toCopy.getDayNumber());
        }


        /**
         * Overwrite constructor.
         * Constructs a new {@code EditEventDescriptor} using an {@code oldDescriptor}, overwritten with
         * values of the {@code newDescriptor} where they exist.
         *
         * @param oldDescriptor Old {@code EditEventDescriptor} to use.
         * @param newDescriptor New {@code EditEventDescriptor} to use.
         */
        public EditExpenditureDescriptor(EditExpenditureDescriptor oldDescriptor,
                                 EditExpenditureDescriptor newDescriptor) {
            this();
            newDescriptor.name.ifPresentOrElse(this::setName, () ->
                    oldDescriptor.name.ifPresent(this::setName));

            newDescriptor.budget.ifPresentOrElse(this::setBudget, () ->
                    oldDescriptor.budget.ifPresent(this::setBudget));

            newDescriptor.dayNumber.ifPresentOrElse(this::setDayNumber, () ->
                    oldDescriptor.dayNumber.ifPresent(this::setDayNumber));
        }


        /**
         * Builds a new {@code Expenditure} instance.
         * Requires name and budget to have been set minimally.
         * Uses the Optional constructor for event to accommodate missing optional fields.
         *
         * @return New {@code Expenditure} created.
         * @throws NullPointerException If any of the fields are empty.
         */
        public Expenditure buildExpenditure() {
            if (isAllPresent(name, budget)) {
                return new Expenditure(name.get(), budget.get(), dayNumber, true);
            } else {
                throw new NullPointerException();
            }
        }

        /**
         * Builds an edited {@code Expenditure} instance from this {@code EditExpenditureDescriptor}.
         * Uses the original expenditure information first, overwriting where the values exist.
         *
         * @param expenditure Source {@code Expenditure} instance.
         * @param expenditure
         * @return Edited {@code Expenditure} instance.
         */
        public Expenditure buildExpenditure(Expenditure expenditure) {
            Name expenditureName = expenditure.getName();
            Budget budget = expenditure.getBudget();
            Optional<DayNumber> dayNumber = expenditure.getDayNumber();
            boolean isRemovable = expenditure.getRemovability();

            if (this.name.isPresent()) {
                expenditureName = this.name.get();
            }
            if (this.budget.isPresent()) {
                budget = this.budget.get();
            }
            if (this.dayNumber.isPresent()) {
                dayNumber = this.dayNumber;
            }

            return new Expenditure(expenditureName, budget, dayNumber, isRemovable);
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

        // Support optional fields from Expenditure
        public void setBudget(Optional<Budget> budget) {
            this.budget = budget;
        }

        public Optional<Budget> getBudget() {
            return budget;
        }

        public void setDayNumber(DayNumber dayNumber) {
            this.dayNumber = Optional.of(dayNumber);
        }

        public void setDayNumber(Optional<DayNumber> dayNumber) {
            this.dayNumber = dayNumber;
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
            if (!(other instanceof EditExpenditureDescriptor)) {
                return false;
            }

            // state check
            EditExpenditureDescriptor e = (EditExpenditureDescriptor) other;

            return getName().equals(e.getName())
                    && getBudget().equals(e.getBudget())
                    && getDayNumber().equals(e.getDayNumber());
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();

            this.name.ifPresent(name -> builder.append(" Name of expenditure: ").append(name));
            this.budget.ifPresent(budget -> builder.append(" Total Budget: ").append(budget));
            this.dayNumber.ifPresent(dayNumber -> builder.append(" Day ").append(dayNumber));

            return builder.toString();
        }
    }
}
