package seedu.address.model.itinerary.event;

import seedu.address.model.booking.Booking;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.itinerary.Expenditure;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.event.exceptions.CompulsoryFieldEmptyException;

import java.time.LocalDateTime;
import java.util.Optional;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Event in TravelPal.
 * Compulsory fields: name, startDate, endDate, destination.
 * Optional fields: totalBudget, booking, inventory.
 */
public class Event {
    // Compulsory fields
    private final Name name;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Location destination;

    // Optional fields
    private final Inventory inventory;
    private final Expenditure totalBudget;
    private final Booking booking;

    /**
     * Constructs an {@code event}.
     */
    public Event(Name name, LocalDateTime startDate, LocalDateTime endDate, Booking booking, Expenditure totalBudget, Inventory inventory, Location destination) {
        requireAllNonNull(name, startDate, endDate, booking, totalBudget, inventory);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.booking = booking;
        this.destination = destination;
        this.totalBudget = totalBudget;
        this.inventory = inventory;
    }

    // temporary constructor until we implement booking and inventory, accepts null for now
    public Event(Name name, LocalDateTime startDate, LocalDateTime endDate, Expenditure totalBudget, Location destination) {
        requireAllNonNull(name, startDate, endDate, totalBudget);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.booking = null;
        this.destination = destination;
        this.totalBudget = totalBudget;
        this.inventory = null;
    }
    /**
     * Constructs a trip with optional totalBudget field.
     */
    public Event(Name name, LocalDateTime startDate, LocalDateTime endDate, Optional<Expenditure> totalBudget, Location destination) {
        requireAllNonNull(name, startDate, endDate, totalBudget);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.booking = null;
        this.destination = destination;
        if(totalBudget.isPresent()) {
            this.totalBudget = totalBudget.get();
        } else{
            this.totalBudget = null;
        }
        this.inventory = null;
    }

    /**Constructor for {@link Builder} */
    private Event(Builder builder){
        try {
            requireAllNonNull(builder.name, builder.startTime, builder.endTime);
        } catch (NullPointerException e) {
            throw new CompulsoryFieldEmptyException();
        }
        this.name = builder.name;
        this.startDate = builder.startTime;
        this.endDate = builder.endTime;
        this.booking = builder.booking;
        this.destination = builder.destination;
        this.totalBudget = builder.totalBudget;
        this.inventory = builder.inventory;
    }

    public Name getName() {
        return name;
    }


    // Compulsory Field getters
    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Location getDestination() {
        return destination;
    }

    // Optional field getters
    public Optional<Expenditure> getTotalBudget() {
        return Optional.ofNullable(totalBudget);
    }

    public Optional<Inventory> getInventory() {
        return Optional.ofNullable(inventory);
    }

    public Optional<Booking> getBooking() {
        return Optional.ofNullable(booking);
    }

    /**
     * Returns true if both {@link Event} contain the same booking and their endDate and startDate time are the same.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent){
        if (otherEvent == this) {
            return true;
        }
        return otherEvent != null
                && otherEvent.getBooking().equals(getBooking())
                && (otherEvent.getEndDate().equals(getEndDate()) || otherEvent.getStartDate().equals(getStartDate()));

    }

    public boolean isClashingWith(Event other){
        return (this.getStartDate().compareTo(other.getStartDate()) >= 0
                && this.getStartDate().compareTo(other.getEndDate()) <= 0)
                || (this.getEndDate().compareTo(other.getStartDate()) >= 0
                && this.getEndDate().compareTo(other.getEndDate()) <= 0);
    }


    /**
     * Builder class to accommodate optional properties using builder pattern.
     * Can be used to construct {@link Event} without optional fields.
     */
    private static class Builder {
        private Name name;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Booking booking;
        private Location destination;
        private Expenditure totalBudget;
        private Inventory inventory;

        public static Builder newInstance (){
            return new Builder();
        }

        private Builder(){
        }

        public Builder setName(Name name){
            this.name = name;
            return this;
        }

        public Builder setStartTime (LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder setEndTime (LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder setBooking (Booking booking){
            this.booking = booking;
            return this;
        }

        public Builder setLocation (Location location) {
            this.destination = location;
            return this;
        }

        public Builder setTotalBudget (Expenditure totalBudget){
            this.totalBudget = totalBudget;
            return this;
        }

        public Builder setInventory (Inventory inventory){
            this.inventory = inventory;
            return this;
        }

        public Event build(){
            return new Event(this);
        }

    }
}
