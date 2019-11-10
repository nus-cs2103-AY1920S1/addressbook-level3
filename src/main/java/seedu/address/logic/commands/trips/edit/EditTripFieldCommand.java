package seedu.address.logic.commands.trips.edit;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.isAllPresent;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATA_FILE_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.model.Model;
import seedu.address.model.diary.Diary;
import seedu.address.model.expense.ExpenseList;
import seedu.address.model.inventory.InventoryList;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.day.DayList;
import seedu.address.model.trip.Photo;
import seedu.address.model.trip.Trip;

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
            + "[" + PREFIX_BUDGET + "TOTAL BUDGET] "
            + "[" + PREFIX_DATA_FILE_PATH + "FILE PATH TO PHOTO]\n"
            + "Example: " + COMMAND_WORD + " 1 Thailand trip"
            + PREFIX_BUDGET + "3000";

    public static final String MESSAGE_NOT_EDITED = "At least one field must be provided!\n" + MESSAGE_USAGE;
    private static final String MESSAGE_EDIT_SUCCESS = "Edited the current form: %1$s";

    private final EditTripDescriptor editTripDescriptor;

    /**
     * @param editTripDescriptor details to edit the trip with
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
     * corresponding field value of the trip.
     */
    public static class EditTripDescriptor {
        private Optional<Name> name;
        private Optional<LocalDateTime> startDate;
        private Optional<LocalDateTime> endDate;
        private Optional<Location> destination;
        private Optional<Budget> totalBudget;
        private Optional<Photo> photo;

        public EditTripDescriptor() {
            name = Optional.empty();
            startDate = Optional.empty();
            endDate = Optional.empty();
            destination = Optional.empty();
            totalBudget = Optional.empty();
            photo = Optional.empty();
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        EditTripDescriptor(EditTripDescriptor toCopy) {
            name = toCopy.getName();
            startDate = toCopy.getStartDate();
            endDate = toCopy.getEndDate();
            destination = toCopy.getDestination();
            totalBudget = toCopy.getBudget();
            photo = toCopy.getPhoto();
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
            setPhoto(toCopy.getPhoto());
        }

        /**
         * Overwrite constructor.
         * Constructs a new {@code EditTripDescriptor} using an {@code oldDescriptor}, overwritten with
         * values of the {@code newDescriptor} where they exist.
         *
         * @param oldDescriptor Old {@code EditTripDescriptor} to use.
         * @param newDescriptor New {@code EditTripDescriptor} to use.
         */
        EditTripDescriptor(EditTripDescriptor oldDescriptor, EditTripDescriptor newDescriptor) {
            this();
            newDescriptor.name.ifPresentOrElse(this::setName, () ->
                    oldDescriptor.name.ifPresent(this::setName));

            newDescriptor.startDate.ifPresentOrElse(this::setStartDate, () ->
                    oldDescriptor.startDate.ifPresent(this::setStartDate));

            newDescriptor.endDate.ifPresentOrElse(this::setEndDate, () ->
                    oldDescriptor.endDate.ifPresent(this::setEndDate));

            newDescriptor.destination.ifPresentOrElse(this::setDestination, () ->
                    oldDescriptor.destination.ifPresent(this::setDestination));

            newDescriptor.totalBudget.ifPresentOrElse(this::setBudget, () ->
                    oldDescriptor.totalBudget.ifPresent(this::setBudget));

            newDescriptor.photo.ifPresentOrElse(this::setPhoto, () ->
                    oldDescriptor.photo.ifPresent(this::setPhoto));
        }


        /**
         * Builds a new {@code Trip} instance.
         * Requires name, startDate, destination and budget to have been set minimally.
         *
         * @return New {@code Trip} created.
         * @throws NullPointerException If any of the fields are empty.
         */
        public Trip buildTrip() {
            if (isAllPresent(name, startDate, endDate, destination, totalBudget)) {
                Trip trip = new Trip(name.get(), startDate.get(), endDate.get(),
                        destination.get(), totalBudget.get(), new DayList(startDate.get(), endDate.get()),
                        new ExpenseList(), new Diary(), new InventoryList(), photo);
                trip.initializeDayList();
                return trip;
            } else {
                throw new NullPointerException();
            }
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
            Budget budget = trip.getBudget();
            Optional<Photo> photo = trip.getPhoto();
            if (this.name.isPresent()) {
                tripName = this.name.get();
            }
            if (this.startDate.isPresent()) {
                startDate = this.startDate.get();
            }
            if (this.endDate.isPresent()) {
                endDate = this.endDate.get();
            }
            if (this.destination.isPresent()) {
                destination = this.destination.get();
            }
            if (this.totalBudget.isPresent()) {
                budget = this.totalBudget.get();
            }
            if (this.photo.isPresent()) {
                photo = this.photo;
            }

            Trip newTrip = new Trip(tripName, startDate, endDate, destination, budget,
                    trip.getDayList(), trip.getExpenseList(), trip.getDiary(), trip.getInventoryList(), photo);
            newTrip.initializeDayList();

            return newTrip;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(name, startDate, endDate, destination, totalBudget, photo);
        }

        public void setName(Name name) {
            this.name = Optional.of(name);
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setStartDate(LocalDateTime startDate) {
            this.startDate = Optional.of(startDate);
        }

        public Optional<LocalDateTime> getStartDate() {
            return startDate;
        }

        public void setEndDate(LocalDateTime endDate) {
            this.endDate = Optional.of(endDate);
        }

        public void setPhoto(Optional<Photo> photo) {
            this.photo = photo;
        }

        public void setPhoto(Photo photo) {
            this.photo = Optional.ofNullable(photo);
        }

        public Optional<LocalDateTime> getEndDate() {
            //end date is optional, but return null to conform with the rest.
            return endDate;
        }

        public void setDestination(Location destination) {
            this.destination = Optional.of(destination);
        }

        public Optional<Location> getDestination() {
            return destination;
        }

        public void setBudget(Budget totalBudget) {
            this.totalBudget = Optional.of(totalBudget);
        }

        public Optional<Budget> getBudget() {
            return totalBudget;
        }

        public Optional<Photo> getPhoto() {
            return photo;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTripFieldCommand.EditTripDescriptor)) {
                return false;
            }

            // state check
            EditTripDescriptor e = (EditTripDescriptor) other;

            return getName().equals(e.getName())
                    && getStartDate().equals(e.getStartDate())
                    && getEndDate().equals(e.getEndDate())
                    && getDestination().equals(e.getDestination())
                    && getBudget().equals(e.getBudget())
                    && getPhoto().equals(e.getPhoto());
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();

            this.name.ifPresent(name -> builder.append(" Name of trip: ").append(name));
            this.startDate.ifPresent(startDate ->
                    builder.append(" Start date: ").append(ParserDateUtil.getDisplayDateTime(startDate)));
            this.endDate.ifPresent(endDate ->
                    builder.append(" End date: ").append(ParserDateUtil.getDisplayDateTime(endDate)));
            this.destination.ifPresent(destination -> builder.append(" Destination: ").append(destination));
            this.totalBudget.ifPresent(totalBudget -> builder.append(" Total Budget: ").append(totalBudget));
            this.photo.ifPresent(photo -> builder.append(" Photo path: ").append(photo));

            return builder.toString();
        }
    }
}
