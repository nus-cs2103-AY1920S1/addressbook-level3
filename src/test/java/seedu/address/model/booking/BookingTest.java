package seedu.address.model.booking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AFRICA;

import org.junit.jupiter.api.Test;

import seedu.address.model.itinerary.Budget;
import seedu.address.testutil.BookingBuilder;

public class BookingTest {
    public static final Booking BOOKING_A = BookingBuilder.newInstance().setName(new seedu.address.model
            .booking.Name("Expense A"))
            .setContact("99999999")
            .setBudget(new Budget(123))
            .build();
    public static final Booking BOOKING_B = BookingBuilder.newInstance().setName(new seedu.address.model
            .booking.Name("Expense A"))
            .setContact("11111111")
            .setBudget(new Budget(123.12))
            .build();

    @Test
    public void isSameBooking() {
        // same object -> returns true
        assertTrue(BOOKING_A.isSameBooking(BOOKING_A));

        // null -> returns false
        assertFalse(BOOKING_A.isSameBooking(null));

        // different name -> returns false
        Booking editedBookingA = BookingBuilder.of(BOOKING_A)
                .setName(new Name(VALID_NAME_AFRICA)).build();
        assertFalse(BOOKING_A.isSameBooking(editedBookingA));

        // same name, same contact, same budget -> returns true
        editedBookingA = BookingBuilder.of(BOOKING_A).build();
        assertTrue(BOOKING_A.isSameBooking(editedBookingA));
    }
}
