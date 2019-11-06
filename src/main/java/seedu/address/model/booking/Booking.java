package seedu.address.model.booking;

/**
 * Abstract Class Booking
 */

public abstract class Booking {
    public final Name name;
    private String contact;

    public Booking(Name name, String contact) {
        this.name = name;
        //this.description = description;
        this.contact = contact;
        //this.expenditure = expenditure;
    }

    public Name getName() {
        return this.name;
    }

    public String getContact() {
        return this.contact;
    }

    /*
    public String description;

    public String getDescription() {
        return this.description;
    }

    public String expenditure;

    public String getExpenditure() {
        return this.expenditure;
    }
    */

    /**
     * Check if two bookings are the same
     *
     * @param otherBooking The other booking to check.
     * @return Boolean of whether the bookings are the same.
     */
    public boolean isSameBooking(Booking otherBooking) {
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
