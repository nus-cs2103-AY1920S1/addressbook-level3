package seedu.address.logic.commands.itinerary.events.edit;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.isAllPresent;
import static seedu.address.logic.parser.CliSyntax.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalTime;

import java.util.Optional;
import seedu.address.commons.core.index.Index;
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
import seedu.address.model.inventory.InventoryList;
import seedu.address.model.inventory.exceptions.InventoryNotFoundException;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Description;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.day.Day;
import seedu.address.model.itinerary.day.DayList;
import seedu.address.model.itinerary.event.Event;

import javax.swing.text.html.Option;

/**
 * Placeholder.
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
            + "[" + PREFIX_ADD_INVENTORY + "ADD INVENTORY ITEM "
            + "[" + PREFIX_DELETE_INVENTORY + "DELETE INVENTORY ITEM BY INDEX "
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

        //System.out.println("model's event's inventoryList's size is " + model.getPageStatus().getEvent().getInventoryList().get().getSize());

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

        private Optional<List<Index>> inventoriesToDelete;
        private Optional<InventoryList> inventoryList;

        private Optional<Booking> booking;
        private Optional<Description> description;

        public EditEventDescriptor() {
            name = Optional.empty();
            startTime = Optional.empty();
            endTime = Optional.empty();
            destination = Optional.empty();
            totalBudget = Optional.empty();
            inventoriesToDelete = Optional.empty();
            inventoryList = Optional.empty();
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
            inventoriesToDelete = toCopy.getInventoriesToDelete();
            inventoryList = toCopy.getInventoryList();
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

            setInventoriesToDelete(Optional.empty());

            if (toCopy.getInventoryList().isPresent()) {
                InventoryList inventoryList = new InventoryList();
                inventoryList.set(toCopy.getInventoryList().get());
                setInventoryList(inventoryList);
            } else {
                setInventoryList(Optional.empty());
            }

            setInventoryList(inventoryList);

            setBudget(toCopy.getExpense().get().getBudget());

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


            newDescriptor.inventoriesToDelete.ifPresent(inventoriesToDelete -> {

                oldDescriptor.inventoryList.ifPresent(inventoryList -> {

                    for (Index index : inventoriesToDelete) {

                        try {
                            inventoryList.remove(index);
                        } catch (InventoryNotFoundException e) {

                        }
                    }
                });
            });

            newDescriptor.inventoryList.ifPresentOrElse(inventoryList -> {

                    if (oldDescriptor.inventoryList.isPresent()) {

                        oldDescriptor.inventoryList.get().addAll(inventoryList);

                        setInventoryList(oldDescriptor.inventoryList.get());

                        //inventoryList.getList().addAll(0, oldDescriptor.getInventoryList().get().getList());
                    } else {
                        setInventoryList(inventoryList);
                    }

                    //setInventoryList(inventoryList);


            }, () -> oldDescriptor.inventoryList.ifPresent(this::setInventoryList));


            /*
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



                Optional<List<Inventory>> inventoryList = Optional.empty();

                if (this.inventoryList.isPresent()) {

                    inventoryList = Optional.of(new ArrayList<Inventory>());

                    for (int i =0; i<this.inventoryList.get().getSize(); i++){
                        inventoryList.get().add(this.inventoryList.get().getList().get(i));
                    }

                } else {
                    this.inventoryList = Optional.empty();
                }




                LocalDate currentDay = model.getPageStatus().getDay().getStartDate().toLocalDate();
                LocalDateTime newStartDate = LocalDateTime.of(currentDay, startTime.get());
                LocalDateTime newEndDate = LocalDateTime.of(currentDay, endTime.get());

                return new Event(name.get(), newStartDate, newEndDate, expense, destination.get(), description, inventoryList);


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

            Optional<List<Inventory>> inventoryList = event.getInventoryList();

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
            if (this.inventoryList.isPresent()) {
                inventoryList = Optional.of(new ArrayList<Inventory>());

                for (int i =0; i<this.inventoryList.get().getSize(); i++){
                    inventoryList.get().add(this.inventoryList.get().getList().get(i));
                }
            }
            if (this.booking.isPresent()) {
                booking = this.booking;
            }
            if (this.description.isPresent()) {
                description = this.description;
            }


            return new Event(eventName, startDate, endDate, expense, destination, description, inventoryList);

        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {

            return CollectionUtil.isAnyPresent(name, startTime, endTime, destination, totalBudget,
                    booking, inventoryList, description, inventoriesToDelete);

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

        public Optional<InventoryList> getInventoryList() {
            return inventoryList;
        }

        public Optional<List<Index>> getInventoriesToDelete() {
            return inventoriesToDelete;
        }

        public void setInventoryList(InventoryList inventoryList) {
            this.inventoryList = Optional.of(inventoryList);
        }

        // Support optional fields from Event
        public void setInventoryList(Optional<InventoryList> inventoryList) {
            this.inventoryList = inventoryList;
        }

        public void setInventoriesToDelete (List<Index> inventoriesToDelete) {
            this.inventoriesToDelete = Optional.of(inventoriesToDelete);
        }

        public void setInventoriesToDelete (Optional<List<Index>> inventoriesToDelete) {
            this.inventoriesToDelete = inventoriesToDelete;
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

//                    && getInventoriesToDelete().equals(e.getInventoriesToDelete())
                    && getInventoryList().equals((e.getInventoryList()))

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


            this.inventoriesToDelete.ifPresent(inventoriesToDelete -> builder.append(" Inventory Items Deleted: ").append(inventoriesToDelete.size()));
            this.inventoryList.ifPresent(inventoryList -> builder.append(" Inventory Items Added: ").append(inventoryList.getSize()));


            this.description.ifPresent(description -> builder.append(" Description: ").append(description));

            /*
            this.booking.ifPresent(booking -> builder.append(" Booking/s: ").append(booking));
             */

            return builder.toString();
        }
    }


}
