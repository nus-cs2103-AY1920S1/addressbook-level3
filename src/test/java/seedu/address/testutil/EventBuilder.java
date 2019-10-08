package seedu.address.testutil;

import seedu.address.model.booking.Booking;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.itinerary.Expenditure;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.event.Event;

import java.time.LocalDateTime;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Builder class to accommodate optional properties using builder pattern.
 * Can be used to construct {@link Event} without optional fields.
 */
class EventBuilder {
    private Name name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Booking booking;
    private Location destination;
    private Expenditure totalBudget;
    private Inventory inventory;

    public static EventBuilder newInstance (){
        return new EventBuilder();
    }

    private EventBuilder(){
    }

    public EventBuilder setName(Name name){
        this.name = name;
        return this;
    }

    public static EventBuilder of (Event day){
        requireAllNonNull(day.getName(), day.getStartDate(), day.getEndDate(), day.getDestination());
        return EventBuilder.newInstance()
                .setName(day.getName())
                .setStartDate(day.getStartDate())
                .setEndDate(day.getEndDate())
                .setLocation(day.getDestination())
                .setTotalBudget(day.getTotalBudget().get())
                .setInventory(day.getInventory().get())
                .setBooking(day.getBooking().get());
    }

    public EventBuilder setStartDate(LocalDateTime startTime) {
        this.startDate = startTime;
        return this;
    }

    public EventBuilder setEndDate (LocalDateTime endTime) {
        this.endDate = endTime;
        return this;
    }

    public EventBuilder setBooking (Booking booking){
        this.booking = booking;
        return this;
    }

    public EventBuilder setLocation (Location location) {
        this.destination = location;
        return this;
    }

    public EventBuilder setTotalBudget (Expenditure totalBudget){
        this.totalBudget = totalBudget;
        return this;
    }

    public EventBuilder setInventory (Inventory inventory){
        this.inventory = inventory;
        return this;
    }

    public Event build(){
        return new Event(name, startDate, endDate, booking, totalBudget, inventory, destination);
    }

}
