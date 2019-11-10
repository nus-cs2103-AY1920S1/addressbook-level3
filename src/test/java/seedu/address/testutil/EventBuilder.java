package seedu.address.testutil;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import seedu.address.model.booking.Booking;
import seedu.address.model.expense.Expense;
import seedu.address.model.inventory.Inventory;
<<<<<<< HEAD
import seedu.address.model.inventory.InventoryList;
=======
import seedu.address.model.itinerary.Description;
>>>>>>> af43df1e8b6160ddf82ae3e7121d8e7e74d6f158
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.event.Event;

import javax.swing.text.html.Option;

/**
 * Builder class to accommodate optional properties using builder pattern.
 * Can be used to construct {@link Event} without optional fields.
 */
public class EventBuilder {
    private Name name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Booking booking;
    private Location destination;
<<<<<<< HEAD
    private Expenditure totalBudget;
    private Optional<List<Inventory>> inventoryList;
=======
    private Expense totalBudget;
    private Inventory inventory;
    private Description description;
>>>>>>> af43df1e8b6160ddf82ae3e7121d8e7e74d6f158

    /**
     * Constructs an empty {@code EventBuilder}.
     */
    private EventBuilder() {
    }

    public EventBuilder setName(Name name) {
        this.name = name;
        return this;
    }

    public static EventBuilder newInstance() {
        return new EventBuilder();
    }

    /**
     * Constructs an {@code EventBuilder} from the specified event.
     *
     * @param event {@code Event} to use.
     * @return new EventBuilder instance.
     */
    public static EventBuilder of(Event event) {
        requireAllNonNull(event.getName(), event.getStartDate(), event.getEndDate(), event.getDestination());
        EventBuilder e = EventBuilder.newInstance()
                .setName(event.getName())
                .setStartDate(event.getStartDate())
                .setEndDate(event.getEndDate())
                .setLocation(event.getDestination());
<<<<<<< HEAD
        event.getExpenditure().ifPresent(e::setTotalBudget);
        event.getInventoryList().ifPresent(e::setInventoryList);
=======
        event.getExpense().ifPresent(e::setTotalBudget);
        event.getInventory().ifPresent(e::setInventory);
>>>>>>> af43df1e8b6160ddf82ae3e7121d8e7e74d6f158
        event.getBooking().ifPresent(e::setBooking);
        event.getDescription().ifPresent(e::setDescription);
        return e;
    }

    public EventBuilder setDescription(Description description) {
        this.description = description;
        return this;
    }

    public EventBuilder setStartDate(LocalDateTime startTime) {
        this.startDate = startTime;
        return this;
    }

    public EventBuilder setEndDate(LocalDateTime endTime) {
        this.endDate = endTime;
        return this;
    }

    public EventBuilder setBooking(Booking booking) {
        this.booking = booking;
        return this;
    }

    public EventBuilder setLocation(Location location) {
        this.destination = location;
        return this;
    }

    public EventBuilder setTotalBudget(Expense totalBudget) {
        this.totalBudget = totalBudget;
        return this;
    }

    public EventBuilder setInventoryList(List<Inventory> inventoryList) {
        this.inventoryList = Optional.of(inventoryList);
        return this;
    }

    public Event build() {
<<<<<<< HEAD
        return new Event(name, startDate, endDate, booking, totalBudget, inventoryList, destination);
=======
        return new Event(name, startDate, endDate, booking, totalBudget, inventory, destination, description);
>>>>>>> af43df1e8b6160ddf82ae3e7121d8e7e74d6f158
    }

}
