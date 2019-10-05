package seedu.address.logic.commands.trips.edit;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.model.Model;
import seedu.address.model.itinerary.Expenditure;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.day.DayList;
import seedu.address.model.trip.Trip;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

/**
 * Constructs a command that attempts to modify the current values in the edit trip page.
 * It overwrites each of the values of the current pageStatus editTripDescriptor with
 * the provided editTripDescriptor's values if they are specified.
 */
public class EditTripFieldCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of your form "
            + "by the index of the form field as displayed, or by the various prefixes of the fields. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DATE_START + "START DATE] "
            + "[" + PREFIX_DATE_END + "END DATE] "
            + "[" + PREFIX_LOCATION + "DESTINATION] "
            + "[" + PREFIX_BUDGET + "TOTAL BUDGET]...\n"
            + "Example: " + COMMAND_WORD + " 1 Thailand trip"
            + PREFIX_BUDGET + "3000";

    public static final String MESSAGE_NOT_EDITED = "At least one field to must be provided!";
    public static final String MESSAGE_EDIT_SUCCESS = "Edited the current form:%1$s";

    private final EditTripDescriptor editTripDescriptor;

    /**
     * @param editTripDescriptor details to edit the person with
     */
    public EditTripFieldCommand(EditTripDescriptor editTripDescriptor) {
        requireNonNull(editTripDescriptor);

        this.editTripDescriptor = editTripDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        EditTripDescriptor currentDescriptor = model.getPageStatus().getEditTripDescriptor();
        EditTripDescriptor newEditTripDescriptor = currentDescriptor == null
                ? new EditTripDescriptor(editTripDescriptor)
                : new EditTripDescriptor(currentDescriptor, editTripDescriptor);

        model.setPageStatus(
                model.getPageStatus().withNewEditTripDescriptor(newEditTripDescriptor));

        return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS, editTripDescriptor));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTripFieldCommand)) {
            return false;
        }

        // state check
        EditTripFieldCommand e = (EditTripFieldCommand) other;
        return editTripDescriptor.equals(e.editTripDescriptor);
    }

    /**
     * Stores the details to edit the trip with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditTripDescriptor {
        private Name name;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Location destination;
        private Expenditure totalBudget;

        public EditTripDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTripDescriptor(EditTripDescriptor toCopy) {
            setName(toCopy.name);
            setStartDate(toCopy.startDate);
            setEndDate(toCopy.endDate);
            setDestination(toCopy.destination);
            setBudget(toCopy.totalBudget);
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTripDescriptor(Trip toCopy) {
            setName(toCopy.getName());
            setStartDate(toCopy.getStartDate());
            setEndDate(toCopy.getEndDate());
            setDestination(toCopy.getDestination());
            setBudget(toCopy.getBudget());
        }

        /**
         * Overwrite constructor.
         * Constructs a new {@code EditTripDescriptor} using an {@code oldDescriptor}, overwritten with
         * values of the {@code newDescriptor} where they exist.
         *
         * @param oldDescriptor Old {@code EditTripDescriptor} to use.
         * @param newDescriptor New {@code EditTripDescriptor} to use.
         */
        public EditTripDescriptor(EditTripDescriptor oldDescriptor, EditTripDescriptor newDescriptor) {
            setName(newDescriptor.name == null
                    ? oldDescriptor.name
                    : newDescriptor.name);
            setStartDate(newDescriptor.startDate == null
                    ? oldDescriptor.startDate
                    : newDescriptor.startDate);
            setEndDate(newDescriptor.endDate == null
                    ? oldDescriptor.endDate
                    : newDescriptor.endDate);
            setDestination(newDescriptor.destination == null
                    ? oldDescriptor.destination
                    : newDescriptor.destination);
            setBudget(newDescriptor.totalBudget == null
                    ? oldDescriptor.totalBudget
                    : newDescriptor.totalBudget);
        }

        /**
         * Builds a new {@code Trip} instance.
         * Requires name, startDate, destination and budget to have been set minimally.
         *
         * @return New {@code Trip} created.
         */
        public Trip buildTrip() {
            requireAllNonNull(name, startDate, endDate, destination, totalBudget);

            return new Trip(name, startDate, endDate, destination, totalBudget, new DayList());
        }

        /**
         * Builds an edited {@code Trip} instance from this {@code EditTripDescriptor}.
         * Uses the original trip information first, overwriting where the values exist.
         *
         * @param trip Source {@code Trip} instance.
         * @return Edited {@code Trip} instance.
         */
        public Trip buildTrip(Trip trip) {
            Name tripName = trip.getName();
            LocalDateTime startDate = trip.getStartDate();
            LocalDateTime endDate = trip.getEndDate();
            Location destination = trip.getDestination();
            Expenditure budget = trip.getBudget();

            if (this.name != null) {
                tripName = this.name;
            }
            if (this.startDate != null) {
                startDate = this.startDate;
            }
            if (this.endDate != null) {
                endDate = this.endDate;
            }
            if (this.destination != null) {
                destination = this.destination;
            }
            if (this.totalBudget != null) {
                budget = this.totalBudget;
            }

            return new Trip(tripName, startDate, endDate, destination, budget, trip.getDayList());
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, startDate, endDate, destination, totalBudget);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Name getName() {
            return name;
        }

        public void setStartDate(LocalDateTime startDate) {
            this.startDate = startDate;
        }

        public LocalDateTime getStartDate() {
            return startDate;
        }

        public void setEndDate(LocalDateTime endDate) {
            this.endDate = endDate;
        }

        public LocalDateTime getEndDate() {
            //end date is optional, but return null to conform with the rest.
            return endDate;
        }

        public void setDestination(Location destination) {
            this.destination = destination;
        }

        public Location getDestination() {
            return destination;
        }

        public void setBudget(Expenditure totalBudget) {
            this.totalBudget = totalBudget;
        }

        public Expenditure getBudget() {
            return totalBudget;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCommand.EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditTripDescriptor e = (EditTripDescriptor) other;

            return getName().equals(e.getName())
                    && getStartDate().equals(e.getStartDate())
                    && getEndDate().equals(e.getEndDate())
                    && getDestination().equals(e.getDestination())
                    && getBudget().equals(e.getBudget());
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (name != null) {
                builder.append(" Name of trip: ").append(name);
            }
            if (startDate != null) {
                builder.append(" Start date: ").append(ParserDateUtil.getDisplayTime(startDate));
            }
            if (endDate != null) {
                builder.append(" End date: ").append(ParserDateUtil.getDisplayTime(endDate));
            }
            if (destination != null) {
                builder.append(" Destination: ").append(destination);
            }
            if (totalBudget != null) {
                builder.append(" Total Budget: ").append(totalBudget);
            }

            return builder.toString();
        }
    }
}
