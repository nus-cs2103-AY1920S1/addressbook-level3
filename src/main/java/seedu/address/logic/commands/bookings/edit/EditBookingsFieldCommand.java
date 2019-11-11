package seedu.address.logic.commands.bookings.edit;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.isAllPresent;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.Name;
import seedu.address.model.itinerary.Budget;

/**
 *
 * Constructs a command that attempts to modify the current values in the edit expenditure page.
 *
 */
public class EditBookingsFieldCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of your form "
            + "by the index of the form field as displayed, or by the various prefixes of the fields. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_CONTACT + "CONTACT] "
            + "[" + PREFIX_BUDGET + "BUDGET] "
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_CONTACT + "10";

    public static final String MESSAGE_NOT_EDITED = "At least one field must be provided!";
    public static final String MESSAGE_EDIT_SUCCESS = "Edited the current form:%1$s";

    private final EditBookingsDescriptor editBookingsDescriptor;

    /**
     * @param editBookingsDescriptor details to edit the expenditure with
     */
    public EditBookingsFieldCommand(EditBookingsDescriptor editBookingsDescriptor) {
        requireNonNull(editBookingsDescriptor);

        this.editBookingsDescriptor = editBookingsDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        EditBookingsDescriptor currentDescriptor = model.getPageStatus().getEditBookingsDescriptor();
        EditBookingsDescriptor newEditBookingsDescriptor = currentDescriptor == null
                ? new EditBookingsDescriptor(editBookingsDescriptor)
                : new EditBookingsDescriptor(currentDescriptor, editBookingsDescriptor);

        model.setPageStatus(
                model.getPageStatus().withNewEditBookingsDescriptor(newEditBookingsDescriptor));

        return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS, editBookingsDescriptor));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditBookingsFieldCommand)) {
            return false;
        }

        // state check
        EditBookingsFieldCommand e = (EditBookingsFieldCommand) other;
        return editBookingsDescriptor.equals(e.editBookingsDescriptor);
    }

    /**
     * Stores the details to edit the expenditure with. Each non-empty field value will replace the
     * corresponding field value of the expenditure.
     */
    public static class EditBookingsDescriptor {
        private Optional<Name> name;
        private Optional<String> contact;
        private Optional<Budget> budget;

        public EditBookingsDescriptor() {
            name = Optional.empty();
            contact = Optional.empty();
            budget = Optional.empty();
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditBookingsDescriptor(EditBookingsDescriptor toCopy) {
            name = toCopy.getName();
            contact = toCopy.getContact();
            budget = toCopy.getBudget();
        }


        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditBookingsDescriptor(Booking toCopy) {
            setName(toCopy.getName());
            setContact(toCopy.getContact());
            setBudget(toCopy.getBudget());
        }


        /**
         * Overwrite constructor.
         * Constructs a new {@code EditEventDescriptor} using an {@code oldDescriptor}, overwritten with
         * values of the {@code newDescriptor} where they exist.
         *
         * @param oldDescriptor Old {@code EditEventDescriptor} to use.
         * @param newDescriptor New {@code EditEventDescriptor} to use.
         */
        public EditBookingsDescriptor(EditBookingsDescriptor oldDescriptor,
                                         EditBookingsDescriptor newDescriptor) {
            this();
            newDescriptor.name.ifPresentOrElse(this::setName, () ->
                    oldDescriptor.name.ifPresent(this::setName));

            newDescriptor.contact.ifPresentOrElse(this::setContact, () ->
                    oldDescriptor.contact.ifPresent(this::setContact));

            newDescriptor.budget.ifPresentOrElse(this::setBudget, () ->
                    oldDescriptor.budget.ifPresent(this::setBudget));
        }


        /**
         * Builds a new {@code Expenditure} instance.
         * Requires name and budget to have been set minimally.
         * Uses the Optional constructor for event to accommodate missing optional fields.
         *
         * @return New {@code Expenditure} created.
         * @throws NullPointerException If any of the fields are empty.
         */
        public Booking buildBooking() {
            if (isAllPresent(name, contact, budget)) {
                return new Booking(name.get(), contact.get(), budget.get()) {
                };
            } else {
                throw new NullPointerException();
            }
        }

        /**
         * Builds an edited {@code Expenditure} instance from this {@code EditBookingsDescriptor}.
         * Uses the original expenditure information first, overwriting where the values exist.
         *
         * @param booking Source {@code Expenditure} instance.
         * @param booking
         * @return Edited {@code Expenditure} instance.
         */
        public Booking buildBooking(Booking booking) {
            Name bookingName = booking.getName();
            String contact = booking.getContact();
            Budget budget = booking.getBudget();

            if (this.name.isPresent()) {
                bookingName = this.name.get();
            }
            if (this.contact.isPresent()) {
                contact = this.contact.get();
            }
            if (this.budget.isPresent()) {
                budget = this.budget.get();
            }

            return new Booking(bookingName, contact, budget) {
            };
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(name, contact, budget);
        }


        public void setName(Name name) {
            this.name = Optional.of(name);
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setContact(String contact) {
            this.contact = Optional.of(contact);
        }

        public Optional<String> getContact() {
            return contact;
        }

        public void setBudget(Budget budget) {
            this.budget = Optional.of(budget);
        }

        public Optional<Budget> getBudget() {
            return budget;
        }

        /*
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
        */

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditBookingsDescriptor)) {
                return false;
            }

            // state check
            EditBookingsDescriptor e = (EditBookingsDescriptor) other;

            return getName().equals(e.getName())
                    && getContact().equals(e.getContact())
                    && getBudget().equals(e.getBudget());
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();

            this.name.ifPresent(name -> builder.append(" Name of booking: ").append(name));
            this.contact.ifPresent(contact -> builder.append(" Contact of the booking: ").append(contact));
            this.budget.ifPresent(budget -> builder.append(" Total Budget: ").append(budget));

            return builder.toString();
        }
    }
}
