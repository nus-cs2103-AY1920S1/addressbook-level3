package seedu.address.testutil;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;

import seedu.address.model.booking.BookingList;
import seedu.address.model.common.Photo;
import seedu.address.model.diary.Diary;
import seedu.address.model.expense.ExpenseList;
import seedu.address.model.inventory.InventoryList;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.day.DayList;
import seedu.address.model.trip.Trip;

/**
 * Builder class to accommodate optional properties using builder pattern.
 * Can be used to construct {@link Trip} without optional fields.
 */
public class TripBuilder {
    private Name name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Location destination;
    private Budget totalBudget;
    private DayList dayList;
    private ExpenseList expenseList;
    private Diary diary;
    private BookingList bookingList;
    private Photo photo;
    private InventoryList inventoryList;

    private TripBuilder() {
        dayList = new DayList(startDate, endDate);
    }

    public static TripBuilder newInstance() {
        return new TripBuilder();
    }

    /**
     * Constructs a tripBuilder instance from the specified trip.
     *
     * @param trip Trip to use.
     * @return new TripBuilder instance.
     */
    public static TripBuilder of(Trip trip) {
        requireAllNonNull(trip.getName(), trip.getStartDate(), trip.getEndDate(), trip.getDestination());
        return TripBuilder.newInstance()
                .setName(trip.getName())
                .setStartDate(trip.getStartDate())
                .setEndDate(trip.getEndDate())
                .setLocation(trip.getDestination())
                .setTotalBudget(trip.getBudget())
                .setDayList(trip.getDayList())
                .setDiary(trip.getDiary())
                .setInventoryList(trip.getInventoryList())
                .setExpenseList(trip.getExpenseList())
                .setDiary(trip.getDiary())
                .setBookingList(trip.getBookingList());
        //.setDayList(trip.getDayList());
    }

    public TripBuilder setName(Name name) {
        this.name = name;
        return this;
    }

    public TripBuilder setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public TripBuilder setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public TripBuilder setLocation(Location location) {
        this.destination = location;
        return this;
    }

    public TripBuilder setTotalBudget(Budget totalBudget) {
        this.totalBudget = totalBudget;
        return this;
    }

    public TripBuilder setDayList(DayList dayList) {
        this.dayList = dayList;
        return this;
    }

    public TripBuilder setPhoto(Photo photo) {
        this.photo = photo;
        return this;
    }
    public TripBuilder setExpenseList(ExpenseList expenseList) {
        this.expenseList = expenseList;
        return this;
    }

    public TripBuilder setDiary(Diary diary) {
        this.diary = diary;
        return this;
    }

    public TripBuilder setInventoryList(InventoryList inventoryList) {
        this.inventoryList = inventoryList;
        return this;
    }

    /**
     * Terminal method to construct new {@link Trip}.
     */
    public Trip build() {
        requireAllNonNull(name, startDate, endDate, destination, totalBudget, dayList,
                expenseList, diary, bookingList, inventoryList);
        return new Trip(name, startDate, endDate, destination, totalBudget,
                dayList, expenseList, diary, bookingList, inventoryList, photo);
    }

    public TripBuilder setBookingList(BookingList bookingList) {
        this.bookingList = bookingList;
        return this;
    }
}
