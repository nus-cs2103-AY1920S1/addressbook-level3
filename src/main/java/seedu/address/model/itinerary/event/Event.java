package seedu.address.model.itinerary.event;

import seedu.address.model.booking.Booking;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.itinerary.Date;
import seedu.address.model.itinerary.Expenditure;
import seedu.address.model.itinerary.Name;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Event {
    private final Name name;
    private final Date from;
    private final Date to;
    private final Booking booking;
    private final Expenditure expenditure;
    private final Inventory inventory;

    public Event(Name name, Date from, Date to, Booking booking, Expenditure expenditure, Inventory inventory) {
        requireAllNonNull(name, from, to, booking, expenditure, inventory);
        this.name = name;
        this.from = from;
        this.to = to;
        this.booking = booking;
        this.expenditure = expenditure;
        this.inventory = inventory;
    }

    public Name getName() {
        return name;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public Booking getBooking() {
        return booking;
    }

    public Expenditure getExpenditure() {
        return expenditure;
    }

    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Returns true if both {@link Event} contain the same booking and their to and from time are the same.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent){
        if (otherEvent == this) {
            return true;
        }
        return otherEvent != null
                && otherEvent.getBooking().equals(getBooking())
                && (otherEvent.getTo().equals(getTo()) || otherEvent.getFrom().equals(getFrom()));

    }

    public boolean isClashingWith(Event other){
        return (this.getFrom().compareTo(other.getTo()) == -1 && this.getTo().compareTo(other.getFrom()) == 1)
                || (this.getTo().compareTo(other.getFrom()) == -1 && this.getFrom().compareTo(other.getTo()) == 1);
    }
}
