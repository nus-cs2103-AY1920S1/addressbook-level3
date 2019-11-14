package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TravelPal;
import seedu.address.model.booking.BookingList;
import seedu.address.model.diary.Diary;
import seedu.address.model.expense.ExpenseList;
import seedu.address.model.inventory.InventoryList;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.day.DayList;
import seedu.address.model.trip.Trip;
import seedu.address.model.trip.exceptions.ClashingTripException;

/**
 * Collection of typical trip test cases.
 */
public class TypicalTrips {

    public static final Trip TRIP_A = TripBuilder.newInstance().setName(new Name("Australia Trip"))
            .setStartDate(LocalDateTime.of(2019, 2, 6, 8, 0))
            .setEndDate(LocalDateTime.of(2019, 2, 20, 9, 0))
            .setLocation(new Location("Australia"))
            .setTotalBudget(new Budget(123))
            .setDayList(new DayList(LocalDateTime.of(2019, 2, 6, 8, 0),
                    LocalDateTime.of(2019, 2, 20, 9, 0)))
            .setExpenseList(new ExpenseList())
            .setDiary(new Diary())
            .setBookingList(new BookingList())
            .setInventoryList(new InventoryList())
            .build();
    public static final Trip TRIP_B = TripBuilder.newInstance().setName(new Name("Bangkok Trip"))
            .setStartDate(LocalDateTime.of(2019, 1, 4, 15, 0))
            .setEndDate(LocalDateTime.of(2019, 1, 16, 0, 0))
            .setLocation(new Location("Bangkok"))
            .setDayList(new DayList(LocalDateTime.of(2019, 1, 4, 15, 0),
                    LocalDateTime.of(2019, 1, 16, 0, 0)))
            .setTotalBudget(new Budget(1234))
            .setExpenseList(new ExpenseList())
            .setDiary(new Diary())
            .setBookingList(new BookingList())
            .setInventoryList(new InventoryList())
            .build();
    public static final Trip TRIP_C = TripBuilder.newInstance().setName(new Name("Canada Trip"))
            .setStartDate(LocalDateTime.of(2019, 3, 5, 10, 0))
            .setEndDate(LocalDateTime.of(2019, 3, 10, 6, 0))
            .setLocation(new Location("Canada"))
            .setDayList(new DayList(LocalDateTime.of(2019, 3, 5, 10, 0),
                    LocalDateTime.of(2019, 3, 10, 6, 0)))
            .setExpenseList(new ExpenseList())
            .setDiary(new Diary())
            .setInventoryList(new InventoryList())
            .setBookingList(new BookingList())
            .setTotalBudget(new Budget(12345))
            .build();
    public static final Trip TRIP_D = TripBuilder.newInstance().setName(new Name("Denmark Trip"))
            .setStartDate(LocalDateTime.of(2019, 4, 4, 15, 0))
            .setEndDate(LocalDateTime.of(2019, 4, 16, 0, 0))
            .setLocation(new Location("Denmark"))
            .setDayList(new DayList(LocalDateTime.of(2019, 4, 4, 15, 0),
                    LocalDateTime.of(2019, 4, 16, 0, 0)))
            .setExpenseList(new ExpenseList())
            .setDiary(new Diary())
            .setInventoryList(new InventoryList())
            .setBookingList(new BookingList())
            .setTotalBudget(new Budget(123456))
            .build();
    public static final Trip TRIP_E = TripBuilder.newInstance().setName(new Name("Ethiopia Trip"))
            .setStartDate(LocalDateTime.of(2019, 5, 2, 3, 0))
            .setEndDate(LocalDateTime.of(2019, 5, 7, 9, 0))
            .setLocation(new Location("Ethiopia"))
            .setDayList(new DayList(LocalDateTime.of(2019, 5, 7, 3, 0),
                    LocalDateTime.of(2019, 5, 2, 9, 0)))
            .setExpenseList(new ExpenseList())
            .setDiary(new Diary())
            .setInventoryList(new InventoryList())
            .setBookingList(new BookingList())
            .setTotalBudget(new Budget(1234567))
            .build();
    public static final Trip TRIP_F = TripBuilder.newInstance().setName(new Name("Finland Trip"))
            .setStartDate(LocalDateTime.of(2019, 6, 2, 19, 0))
            .setEndDate(LocalDateTime.of(2019, 6, 4, 17, 0))
            .setLocation(new Location("Finland"))
            .setDayList(new DayList(LocalDateTime.of(2019, 6, 2, 19, 0),
                    LocalDateTime.of(2019, 6, 4, 17, 0)))
            .setExpenseList(new ExpenseList())
            .setDiary(new Diary())
            .setInventoryList(new InventoryList())
            .setBookingList(new BookingList())
            .setTotalBudget(new Budget(12345678))
            .build();
    public static final Trip TRIP_G = TripBuilder.newInstance().setName(new Name("Germany Trip"))
            .setStartDate(LocalDateTime.of(2019, 7, 3, 4, 0))
            .setEndDate(LocalDateTime.of(2019, 7, 17, 11, 0))
            .setLocation(new Location("Germany"))
            .setDayList(new DayList(LocalDateTime.of(2019, 7, 3, 4, 0),
                    LocalDateTime.of(2019, 7, 17, 11, 0)))
            .setExpenseList(new ExpenseList())
            .setDiary(new Diary())
            .setInventoryList(new InventoryList())
            .setBookingList(new BookingList())
            .setTotalBudget(new Budget(123456789))
            .build();

    private TypicalTrips() {
    }

    public static TravelPal getTypicalTravelPal() {
        TravelPal tp = new TravelPal();
        try {
            for (Trip trip : getTypicalTrips()) {
                tp.addTrip(trip);
            }
        } catch (ClashingTripException e) {
            return tp;
        }
        return tp;
    }

    public static List<Trip> getTypicalTrips() {
        return new ArrayList<>(Arrays.asList(TRIP_A, TRIP_B, TRIP_C, TRIP_D, TRIP_E, TRIP_F));
    }
}
