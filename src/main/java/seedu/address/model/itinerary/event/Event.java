package seedu.address.model.itinerary.event;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.model.booking.Booking;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.inventory.InventoryList;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;

/**
 * Represents a Event in TravelPal.
 * Compulsory fields: name, startDate, endDate, destination.
 * Optional fields: expenditure, booking, inventoryList.
 */
public class Event {
    public static final String MESSAGE_INVALID_DATETIME = "Start date should be before end date";

    // Compulsory fields
    private final Name name;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Location destination;

    // Optional fields
    private final Optional<List<Inventory>> inventoryList;
    private final Expenditure expenditure;
    private final Booking booking;

    /**
     * Constructs an {@code Event}.
     */
    public Event(Name name, LocalDateTime startDate, LocalDateTime endDate, Booking booking,
                 Expenditure expenditure, Optional<List<Inventory>> inventoryList, Location destination) {
        requireAllNonNull(name, startDate, endDate);
        checkArgument(isValidDuration(startDate, endDate), MESSAGE_INVALID_DATETIME);

        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.booking = booking;
        this.destination = destination;
        this.expenditure = expenditure;
        this.inventoryList = inventoryList;
    }

    // temporary constructor until we implement booking and inventoryList, accepts null for now
    public Event(Name name, LocalDateTime startDate, LocalDateTime endDate,
                 Expenditure expenditure, Location destination, Optional<List<Inventory>> inventoryList) {
        requireAllNonNull(name, startDate, endDate, expenditure);
        checkArgument(isValidDuration(startDate, endDate), MESSAGE_INVALID_DATETIME);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.booking = null;
        this.destination = destination;
        this.expenditure = expenditure;
        this.inventoryList = inventoryList;

    }

    /**
     * Constructs a trip with optional expenditure field.
     */
    public Event(Name name, LocalDateTime startDate, LocalDateTime endDate,
                 Optional<Expenditure> expenditure, Location destination, Optional<List<Inventory>> inventoryList) {
        requireAllNonNull(name, startDate, endDate, expenditure);
        checkArgument(isValidDuration(startDate, endDate), MESSAGE_INVALID_DATETIME);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.booking = null;
        this.destination = destination;
        this.expenditure = expenditure.orElse(null);

        this.inventoryList = inventoryList;
    }


    public boolean isValidDuration(LocalDateTime startDate, LocalDateTime endDate) {
        return startDate.isBefore(endDate);
    }

    // Compulsory Field getters
    public Name getName() {
        return name;
    }

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
    public Optional<Expenditure> getExpenditure() {
        return Optional.ofNullable(expenditure);
    }

    public Optional<List<Inventory>> getInventoryList() {
        return inventoryList;
    }

    public Optional<Booking> getBooking() {
        return Optional.ofNullable(booking);
    }

    /**
     * Returns true if both {@link Event} contain the same booking and their endDate and startDate time are the same.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }
        return otherEvent != null
                && otherEvent.getName().equals(getName())
                && (otherEvent.getEndDate().equals(getEndDate()) && otherEvent.getStartDate().equals(getStartDate()));

    }

    /**
     * Checks whether this event is clashing with another.
     *
     * @param other The other event to check.
     * @return Boolean of whether the events clash.
     */
    public boolean isClashingWith(Event other) {
        return (this.getStartDate().compareTo(other.getStartDate()) >= 0
                && this.getStartDate().compareTo(other.getEndDate()) <= 0)
                || (this.getEndDate().compareTo(other.getStartDate()) >= 0
                && this.getEndDate().compareTo(other.getEndDate()) <= 0);
    }

    /**
     * Checks whether this event is has the same name with another.
     *
     * @param other The other event to check.
     * @return Boolean of whether the events has the same name.
     */
    public boolean hasSameName(Event other) {
        return this.getName().equals(other.getName());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherTrip = (Event) other;
        return otherTrip.getName().equals(getName())
                && otherTrip.getStartDate().equals(getStartDate())
                && otherTrip.getEndDate().equals(getEndDate())
                && otherTrip.getDestination().equals(getDestination())
                && otherTrip.getBooking().equals(getBooking())
                && otherTrip.getExpenditure().equals(getExpenditure())
                && otherTrip.getInventoryList().equals(getInventoryList());
    }

}
