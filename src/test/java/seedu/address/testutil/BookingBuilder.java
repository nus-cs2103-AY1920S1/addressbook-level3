package seedu.address.testutil;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.booking.Booking;
import seedu.address.model.booking.Name;
import seedu.address.model.itinerary.Budget;

/**
 * Builder class to accommodate optional properties using builder pattern.
 */
public class BookingBuilder {
    private Name name;
    private String contact;
    private Budget budget;

    private BookingBuilder() {}

    public static BookingBuilder newInstance() {
        return new BookingBuilder();
    }

    /**
     * Constructs a BookingBuilder instance from the specified booking.
     *
     * @param booking Booking to use.
     * @return new bookingBuilder instance.
     */
    public static BookingBuilder of(Booking booking) {
        requireAllNonNull(booking.getName(), booking.getContact(), booking.getBudget());
        return BookingBuilder.newInstance()
                .setName(booking.getName())
                .setContact(booking.getContact())
                .setBudget(booking.getBudget());
    }

    public BookingBuilder setName(Name name) {
        this.name = name;
        return this;
    }

    public BookingBuilder setBudget(Budget budget) {
        this.budget = budget;
        return this;
    }

    public BookingBuilder setContact(String contact) {
        this.contact = contact;
        return this;
    }

    /**
     * Terminal method to construct new booking.
     */
    public Booking build() {
        return new Booking(name, contact, budget);
    }
}
