package seedu.address.logic.commands.itinerary.events.edit;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.itinerary.Expenditure;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.event.Event;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.isAllPresent;
import static seedu.address.logic.parser.CliSyntax.*;

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

    public static final String MESSAGE_NOT_EDITED = "At least one field to must be provided!";
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
        if (!(other instanceof EditEventDescriptor)) {
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
        private Optional<LocalDateTime> startDate;
        private Optional<LocalDateTime> endDate;
        private Optional<Location> destination;
        private Optional<Expenditure> totalBudget;

        private Optional<Inventory> inventory;
        private Optional<Booking> booking;

        public EditEventDescriptor() {
            name = Optional.empty();
            startDate = Optional.empty();
            endDate = Optional.empty();
            destination = Optional.empty();
            totalBudget = Optional.empty();

        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            name = toCopy.getName();
            startDate = toCopy.getStartDate();
            endDate = toCopy.getEndDate();
            destination = toCopy.getDestination();
            totalBudget = toCopy.getBudget();
            inventory = toCopy.getInventory();
            booking = toCopy.getBooking();
        }


        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEventDescriptor(Event toCopy) {
            setName(toCopy.getName());
            setStartDate(toCopy.getStartDate());
            setEndDate(toCopy.getEndDate());
            setDestination(toCopy.getDestination());
            setBudget(toCopy.getTotalBudget());
            setInventory(toCopy.getInventory());
            setBooking(toCopy.getBooking());
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
            newDescriptor.name.ifPresentOrElse(this::setName,
                    () -> oldDescriptor.name.ifPresent(this::setName));

            newDescriptor.startDate.ifPresentOrElse(this::setStartDate,
                    () -> oldDescriptor.startDate.ifPresent(this::setStartDate));

            newDescriptor.endDate.ifPresentOrElse(this::setEndDate,
                    () -> oldDescriptor.endDate.ifPresent(this::setEndDate));

            newDescriptor.destination.ifPresentOrElse(this::setDestination,
                    () -> oldDescriptor.destination.ifPresent(this::setDestination));

            newDescriptor.totalBudget.ifPresentOrElse(this::setBudget,
                    () -> oldDescriptor.totalBudget.ifPresent(this::setBudget));

            newDescriptor.inventory.ifPresentOrElse(this::setInventory,
                    () -> oldDescriptor.inventory.ifPresent(this::setInventory));

            newDescriptor.booking.ifPresentOrElse(this::setBooking,
                    () -> oldDescriptor.booking.ifPresent(this::setBooking));
        }


        /**
         * Builds a new {@code Event} instance.
         * Requires name, startDate, destination to have been set minimally.
         * Uses the Optional constructor for event to accommodate missing optional fields.
         *
         * @return New {@code Event} created.
         * @throws NullPointerException If any of the fields are empty.
         */
        public Event buildEvent() {
            if (isAllPresent(name, startDate, endDate, destination)) {
                return new Event(name.get(), startDate.get(), endDate.get(), totalBudget, destination.get());
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
         * @return Edited {@code Event} instance.
         * @param event
         */
        public Event buildEvent(Event event) {
            Name eventName = event.getName();
            LocalDateTime startDate = event.getStartDate();
            LocalDateTime endDate = event.getEndDate();
            Location destination = event.getDestination();
            Optional<Expenditure> budget = event.getTotalBudget();
            Optional<Booking> booking = event.getBooking();
            Optional<Inventory> inventory = event.getInventory();

            if (this.name.isPresent()) {
                eventName = this.name.get();
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
                budget = this.totalBudget;
            }
            if (this.inventory.isPresent()) {
                inventory = this.inventory;
            }
            if(this.booking.isPresent()) {
                booking = this.booking;
            }


            return new Event(eventName, startDate, endDate, totalBudget, destination);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(name, startDate, endDate, destination, totalBudget, booking, inventory);
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

        public void setBudget(Expenditure totalBudget) {
            this.totalBudget = Optional.of(totalBudget);
        }

        public Optional<Expenditure> getBudget() {
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

        private void setBooking(Booking booking) {
            this.booking = Optional.of(booking);
        }

        // Support optional fields from Event
        public void setBudget(Optional<Expenditure> totalBudget) {
            this.totalBudget = totalBudget;
        }

        public void setInventory(Optional<Inventory> inventory) {
            this.inventory = inventory;
        }



        public void setBooking(Optional<Booking> booking) {
            this.booking = booking;
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
            EditEventDescriptor e = (EditEventDescriptor) other;

            return getName().equals(e.getName())
                    && getStartDate().equals(e.getStartDate())
                    && getEndDate().equals(e.getEndDate())
                    && getDestination().equals(e.getDestination())
                    && getBudget().equals(e.getBudget());
//                    && getInventory().equals((e.getInventory()))
//                    && getBooking().equals((e.getBooking()));
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();

            this.name.ifPresent(name -> builder.append(" Name of event: ").append(name));
            this.startDate.ifPresent(startDate ->
                    builder.append(" Start date: ").append(ParserDateUtil.getDisplayTime(startDate)));
            this.endDate.ifPresent(endDate ->
                    builder.append(" End date: ").append(ParserDateUtil.getDisplayTime(endDate)));
            this.destination.ifPresent(destination -> builder.append(" Destination: ").append(destination));
            this.totalBudget.ifPresent(totalBudget -> builder.append(" Total Budget: ").append(totalBudget));
//            this.inventory.ifPresent(inventory -> builder.append(" Inventory: ").append(inventory));
//            this.booking.ifPresent(booking -> builder.append(" Booking/s: ").append(booking));

            return builder.toString();
        }
    }


}
