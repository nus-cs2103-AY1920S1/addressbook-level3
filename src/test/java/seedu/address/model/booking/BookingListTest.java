package seedu.address.model.booking;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.itinerary.Budget;
import seedu.address.testutil.BookingBuilder;

public class BookingListTest {

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
    void contains_nullBooking_throwsNullPointerException() {
        BookingList bookingList = new BookingList();
        assertThrows(NullPointerException.class, () -> bookingList.contains(null));
    }

    @Test
    void contains_bookingNotInList_returnsFalse() {
        BookingList bookingList = new BookingList();
        assertFalse(bookingList.contains(BOOKING_A));
    }

    @Test
    void contains_bookingInList_returnsTrue() {
        BookingList bookingList = new BookingList();
        assertDoesNotThrow(() -> {
            bookingList.add(BOOKING_A);
            assertTrue(bookingList.contains(BOOKING_A));
        });
    }

    @Test
    void contains_bookingWithSameIdentityFieldsInList_returnsTrue() {
        BookingList bookingList = new BookingList();
        assertDoesNotThrow(() -> {
            bookingList.add(BOOKING_A);
            Booking editedBookingA = BookingBuilder.of(BOOKING_A).setBudget(new Budget(10))
                    .build();
            assertTrue(bookingList.contains(editedBookingA));
        });
    }

    @Test
    void add_nullBooking_throwsNullPointerException() {
        BookingList bookingList = new BookingList();
        assertThrows(NullPointerException.class, () -> bookingList.add(null));
    }

    @Test
    void setBooking_editedBookingIsSameBooking_success() {
        BookingList bookingList = new BookingList();
        assertDoesNotThrow(() -> {
            bookingList.add(BOOKING_A);
            bookingList.set(BOOKING_A, BOOKING_A);
            BookingList expectedUniqueBookingList = new BookingList();
            expectedUniqueBookingList.add(BOOKING_A);
            assertEquals(expectedUniqueBookingList, bookingList);
        });
    }

    @Test
    void setExpense_editedBookingHasSameIdentity_success() {
        BookingList bookingList = new BookingList();
        assertDoesNotThrow(() -> {
            bookingList.add(BOOKING_A);
            Booking editedBooking = BookingBuilder.of(BOOKING_A)
                    .setBudget(new Budget("100"))
                    .build();
            bookingList.set(BOOKING_A, editedBooking);
            BookingList expectedUniqueBookingList = new BookingList();
            expectedUniqueBookingList.add(editedBooking);
            assertEquals(expectedUniqueBookingList, bookingList);
        });
    }

    @Test
    void setExpense_editedBookingHasDifferentIdentity_success() {
        BookingList bookingList = new BookingList();
        assertDoesNotThrow(() -> {
            bookingList.add(BOOKING_A);
            bookingList.set(BOOKING_A, BOOKING_B);
            BookingList expectedUniqueBookingList = new BookingList();
            expectedUniqueBookingList.add(BOOKING_B);
            assertEquals(expectedUniqueBookingList, bookingList);
        });
    }

    @Test
    public void setBooking_editedBookingHasNonUniqueIdentity_throwsDuplicateExpenseException() {
        BookingList bookingList = new BookingList();
        assertDoesNotThrow(() -> {
            bookingList.add(BOOKING_A);
            bookingList.add(BOOKING_B);
            assertThrows(DuplicateBookingException.class, () -> bookingList.set(BOOKING_A, BOOKING_B));
        });
    }

    @Test
    public void remove_nullExpense_throwsNullPointerException() {
        BookingList bookingList = new BookingList();
        assertThrows(NullPointerException.class, () -> bookingList.remove((Booking) null));
    }

    @Test
    public void remove_bookingDoesNotExist_throwsBookingNotFoundException() {
        BookingList bookingList = new BookingList();
        assertThrows(BookingNotFoundException.class, () -> bookingList.remove(BOOKING_A));
    }


    /*
    //note list references in these two tests were originally of type BookingList
    @Test
    public void setExpenses_nullUniqueBookingList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookingList.set(null));
    }
    */

    @Test
    public void setBookings_uniqueBookingList_replacesOwnListWithProvidedUniqueBookingList() {
        BookingList bookingList = new BookingList();
        assertDoesNotThrow(() -> {
            bookingList.add(BOOKING_A);
            List<Booking> expectedUniqueBookingList = new ArrayList<Booking>();
            expectedUniqueBookingList.add(BOOKING_B);
            bookingList.set(expectedUniqueBookingList);
            assertEquals(expectedUniqueBookingList, bookingList.asUnmodifiableObservableList());
        });
    }
    //-------------------------------------------------------------------

    @Test
    public void setExpenses_nullList_throwsNullPointerException() {
        BookingList bookingList = new BookingList();
        assertThrows(NullPointerException.class, () -> bookingList.set((List<Booking>) null));
    }

    @Test
    public void setExpenses_list_replacesOwnListWithProvidedList() {
        BookingList bookingList = new BookingList();
        assertDoesNotThrow(() -> {
            bookingList.add(BOOKING_A);
            List<Booking> bookings = Collections.singletonList(BOOKING_B);
            bookingList.set(bookings);
            BookingList expectedUniqueBookingList = new BookingList();
            expectedUniqueBookingList.add(BOOKING_B);
            assertEquals(expectedUniqueBookingList, bookingList);
        });
    }

    @Test
    public void setExpenses_listWithDuplicateExpenses_throwsDuplicateExpenseException() {
        BookingList bookingList = new BookingList();
        List<Booking> listWithDuplicateBookings = Arrays.asList(BOOKING_A, BOOKING_A);
        assertThrows(DuplicateBookingException.class, () -> bookingList.set(listWithDuplicateBookings));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        BookingList bookingList = new BookingList();
        assertThrows(UnsupportedOperationException.class, () ->
                bookingList.asUnmodifiableObservableList().remove(0));
    }

}
