package seedu.address.model.booking;

/**
 * Placeholder javadoc.
 */
public abstract class BookingRed {
    public final String name;

    public BookingRed(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns true if both {@link Trip} contain the same booking and their to and from time are the same.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameBooking(BookingRed otherBooking) {
        if (otherBooking == this) {
            return true;
        } else {
            return otherBooking != null
                    && otherBooking.getName().equals(getName());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Booking)) {
            return false;
        }

        Booking otherBooking = (Booking) other;
        return otherBooking.getName().equals(getName());
    }
}
