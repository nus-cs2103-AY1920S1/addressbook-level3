package seedu.address.model.booking;

import seedu.address.model.expense.Expense;

import java.util.Optional;

/**
 * Abstract Class Booking
 */

public abstract class Booking {
    private final Name name;
    private final String contact;
    private final Expense expense;

    public Booking(Name name, String contact, Expense expense) {
        this.name = name;
        this.contact = contact;
        this.expense = expense;
    }

    public Name getName() {
        return this.name;
    }

    public String getContact() {
        return this.contact;
    }

    public Expense getExpense() { return expense; }

    /*
    public String description;

    public String getDescription() {
        return this.description;
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
