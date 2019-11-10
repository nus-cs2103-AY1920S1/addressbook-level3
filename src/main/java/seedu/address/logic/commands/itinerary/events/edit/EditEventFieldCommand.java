package seedu.address.logic.commands.itinerary.events.edit;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.isAllPresent;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOKING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INVENTORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;
import seedu.address.model.expense.DayNumber;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.PlannedExpense;
import seedu.address.model.expense.exceptions.ExpenseNotFoundException;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Description;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.day.Day;
import seedu.address.model.itinerary.day.DayList;
import seedu.address.model.itinerary.event.Event;

/**
 * Command to edit field(s) in a given event.
 */
public class EditEventFieldCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of your form "
            + "by the index of the form field as displayed, or by the various prefixes of the fields. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DATE_START + "START DATE] "
            + "[" + PREFIX_DATE_END + "END DATE] "
            + "[" + PREFIX_LOCATION + "DESTINATION] "
            + "[" + PREFIX_BUDGET + "TOTAL BUDGET "
            + "[" + PREFIX_INVENTORY + "<to be implemented> "
            + "|" + PREFIX_BOOKING + "<to be implemented>]...\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_LOCATION + " ABC Zoo "
            + PREFIX_BUDGET + "10";

    public static final String MESSAGE_NOT_EDITED = "At least one field must be provided!";
    public static final String MESSAGE_EDIT_SUCCESS = "Edited the current form:%1$s";

    private final EditEventDescriptor editEventDescriptor;

    /**
     * @param editEventDescriptor details to edit the person with
     */
    public EditEventFieldCommand(EditEventDescriptor editEventDescriptor) {
        requireNonNull(editEventDescriptor);

        this.editEventDescriptor = editEventDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        EditEventDescriptor currentDescriptor = model.getPageStatus().getEditEventDescriptor();
        EditEventDescriptor newEditEventDescriptor = currentDescriptor == null
                ? new EditEventDescriptor(editEventDescriptor)
                : new EditEventDescriptor(currentDescriptor, editEventDescriptor);

        model.setPageStatus(
                model.getPageStatus().withNewEditEventDescriptor(newEditEventDescriptor));

        return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS, editEventDescriptor));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEventFieldCommand)) {
            return false;
        }

        // state check
        EditEventFieldCommand e = (EditEventFieldCommand) other;
        return editEventDescriptor.equals(e.editEventDescriptor);
    }

    /**
     * Stores the details to edit the event with. Each non-empty field value will replace the
     * corresponding field value of the event.
     * WARNING: REFACTOR AFTER COMPLETING IMPLEMENTATION OF JSONADAPTED BOOKING/INVENTORY
     */
    public static class EditEventDescriptor {
        private Optional<Name> name;
        private Optional<LocalTime> startTime;
        private Optional<LocalTime> endTime;
        private Optional<Location> destination;
        private Optional<Budget> totalBudget;

        private Optional<Inventory> inventory;
        private Optional<Booking> booking;
        private Optional<Description> description;

        public EditEventDescriptor() {
            name = Optional.empty();
            startTime = Optional.empty();
            endTime = Optional.empty();
            destination = Optional.empty();
            totalBudget = Optional.empty();
            inventory = Optional.empty();
            booking = Optional.empty();
            description = Optional.empty();
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            name = toCopy.getName();
            startTime = toCopy.getStartTime();
            endTime = toCopy.getEndTime();
            destination = toCopy.getDestination();
            totalBudget = toCopy.getBudget();
            inventory = toCopy.getInventory();
            booking = toCopy.getBooking();
            description = toCopy.getDescription();
        }


        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEventDescriptor(Event toCopy) {
            setName(toCopy.getName());
            setStartTime(toCopy.getStartDate().toLocalTime());
            setEndTime(toCopy.getEndDate().toLocalTime());
            setDestination(toCopy.getDestination());
            setBudget(toCopy.getExpense().get().getBudget());
            setInventory(toCopy.getInventory());
            setBooking(toCopy.getBooking());
            setDescription(toCopy.getDescription());
        }


        /**
         * Overwrite constructor.
         * Constructs a new {@code EditEventDescriptor} using an {@code oldDescriptor}, overwritten with
         * values of the {@code newDescriptor} where they exist.
         *
         * @param oldDescriptor Old {@code EditEventDescriptor} to use.
         * @param newDescriptor New {@code EditEventDescriptor} to use.
         */
        public EditEventDescriptor(EditEventDescriptor oldDescriptor, EditEventDescriptor newDescriptor) {
            this();
            newDescriptor.name.ifPresentOrElse(this::setName, () ->
                    oldDescriptor.name.ifPresent(this::setName));

            newDescriptor.startTime.ifPresentOrElse(this::setStartTime, () ->
                    oldDescriptor.startTime.ifPresent(this::setStartTime));

            newDescriptor.endTime.ifPresentOrElse(this::setEndTime, () ->
                    oldDescriptor.endTime.ifPresent(this::setEndTime));

            newDescriptor.destination.ifPresentOrElse(this::setDestination, () ->
                    oldDescriptor.destination.ifPresent(this::setDestination));

            newDescriptor.totalBudget.ifPresentOrElse(this::setBudget, () ->
                    oldDescriptor.totalBudget.ifPresent(this::setBudget));

            newDescriptor.description.ifPresentOrElse(this::setDescription, () ->
                    oldDescriptor.description.ifPresent(this::setDescription));
            /*
            newDescriptor.inventory.ifPresentOrElse(this::setInventory,
                    () -> oldDescriptor.inventory.ifPresent(this::setInventory));

            newDescriptor.booking.ifPresentOrElse(this::setBooking,
                    () -> oldDescriptor.booking.ifPresent(this::setBooking));
             */
        }


        /**
         * Builds a new {@code Event} instance.
         * Requires name, startDate, destination to have been set minimally.
         * Uses the Optional constructor for event to accommodate missing optional fields.
         *
         * @param model Source {@code Model} instance.
         * @return New {@code Event} created.
         * @throws NullPointerException If any of the fields are empty.
         */
        public Event buildEvent(Model model) {

            if (isAllPresent(name, startTime, endTime, destination)) {
                Optional<Expense> expense = Optional.empty();

                if (totalBudget.isPresent()) {
                    DayList list = model.getPageStatus().getTrip().getDayList();
                    Day day = model.getPageStatus().getDay();
                    int index = list.internalList.indexOf(day);
                    Expense newExpense = new PlannedExpense(name.get(), totalBudget.get(),
                            new DayNumber(Integer.toString(index + 1)));
                    expense = Optional.of(newExpense);
                }

                LocalDate currentDay = model.getPageStatus().getDay().getStartDate().toLocalDate();
                LocalDateTime newStartDate = LocalDateTime.of(currentDay, startTime.get());
                LocalDateTime newEndDate = LocalDateTime.of(currentDay, endTime.get());

                return new Event(name.get(), newStartDate, newEndDate, expense, destination.get(), description);

            } else {
                throw new NullPointerException();
            }
        }

        /**
         * Builds an edited {@code Event} instance from this {@code EditEventDescriptor}.
         * Uses the original eventrmation first, overwriting where the values exist.
         * WARNING: USING INCOMPLETE CONSTRUCTOR, REFACTOR AFTER IMPLEMENTING BOOKING AND INVENTORY
         *
         * @param event Source {@code Event} instance.
         * @param model Source {@code Model} instance.
         * @return Edited {@code Event} instance.
         */
        public Event buildEvent(Event event, Model model) throws ExpenseNotFoundException {
            LocalDate currentDay = model.getPageStatus().getDay().getStartDate().toLocalDate();

            Name eventName = event.getName();
            LocalTime startTime = event.getStartDate().toLocalTime();
            LocalTime endTime = event.getEndDate().toLocalTime();
            LocalDateTime startDate = currentDay.atTime(startTime);
            LocalDateTime endDate = currentDay.atTime(endTime);
            Location destination = event.getDestination();
            Optional<Booking> booking = event.getBooking();
            Optional<Inventory> inventory = event.getInventory();
            Optional<Description> description = event.getDescription();
            Optional<Expense> expense = event.getExpense();

            if (this.name.isPresent()) {
                eventName = this.name.get();
            }
            if (this.startTime.isPresent()) {
                startDate = this.startTime.get().atDate(currentDay);
            }
            if (this.endTime.isPresent()) {
                endDate = this.endTime.get().atDate(currentDay);
            }
            if (this.destination.isPresent()) {
                destination = this.destination.get();
            }
            if (this.totalBudget.isPresent()) {
                int index = model.getPageStatus().getTrip().getDayList()
                        .internalList.indexOf(model.getPageStatus().getDay());
                Expense newExpense = new PlannedExpense(eventName, this.totalBudget.get(),
                        new DayNumber(Integer.toString(index + 1)));
                expense = Optional.of(newExpense);
            }
            if (this.inventory.isPresent()) {
                inventory = this.inventory;
            }
            if (this.booking.isPresent()) {
                booking = this.booking;
            }
            if (this.description.isPresent()) {
                description = this.description;
            }

            return new Event(eventName, startDate, endDate, expense, destination, description);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(name, startTime, endTime, destination, totalBudget,
                    booking, inventory, description);
        }


        public void setName(Name name) {
            this.name = Optional.of(name);
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setStartTime(LocalTime startTime) {
            this.startTime = Optional.of(startTime);
        }

        public Optional<LocalTime> getStartTime() {
            return startTime;
        }

        public void setEndTime(LocalTime endTime) {
            this.endTime = Optional.of(endTime);
        }

        public Optional<LocalTime> getEndTime() {
            //end date is optional, but return null to conform with the rest.
            return endTime;
        }

        public void setDestination(Location destination) {
            this.destination = Optional.of(destination);
        }

        public Optional<Location> getDestination() {
            return destination;
        }

        public void setBudget(Budget totalBudget) {
            this.totalBudget = Optional.ofNullable(totalBudget);
        }

        public void setBudget(Optional<Budget> totalBudget) {
            this.totalBudget = totalBudget;
        }

        public Optional<Budget> getBudget() {
            return totalBudget;
        }

        public Optional<Booking> getBooking() {
            return booking;
        }

        public Optional<Inventory> getInventory() {
            return inventory;
        }

        private void setInventory(Inventory inventory) {
            this.inventory = Optional.of(inventory);
        }

        // Support optional fields from Event
        public void setInventory(Optional<Inventory> inventory) {
            this.inventory = inventory;
        }

        private void setBooking(Booking booking) {
            this.booking = Optional.of(booking);
        }

        public void setBooking(Optional<Booking> booking) {
            this.booking = booking;
        }

        public Optional<Description> getDescription() {
            return description;
        }

        public void setDescription(Optional<Description> description) {
            this.description = description;
        }

        public void setDescription(Description description) {
            this.description = Optional.of(description);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEventDescriptor)) {
                return false;
            }

            // state check
            EditEventDescriptor e = (EditEventDescriptor) other;

            return getName().equals(e.getName())
                    && getStartTime().equals(e.getStartTime())
                    && getEndTime().equals(e.getEndTime())
                    && getDestination().equals(e.getDestination())
                    && getBudget().equals(e.getBudget())
                    && getDescription().equals(e.getDescription());
//                    && getInventory().equals((e.getInventory()))
//                    && getBooking().equals((e.getBooking()));
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();

            this.name.ifPresent(name -> builder.append(" Name of event: ").append(name));
            this.startTime.ifPresent(startDate ->
                    builder.append(" Start time: ").append(ParserDateUtil.getDisplayTime(startDate)));
            this.endTime.ifPresent(endDate ->
                    builder.append(" End time: ").append(ParserDateUtil.getDisplayTime(endDate)));
            this.destination.ifPresent(destination -> builder.append(" Destination: ").append(destination));
            this.totalBudget.ifPresent(totalBudget -> builder.append(" Total Budget: ").append(totalBudget));
            this.description.ifPresent(description -> builder.append(" Description: ").append(description));
            /*
            this.inventory.ifPresent(inventory -> builder.append(" Inventory: ").append(inventory));
            this.booking.ifPresent(booking -> builder.append(" Booking/s: ").append(booking));
            */
            return builder.toString();
        }
    }


}
