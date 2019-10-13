package seedu.address.model.day;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * DayManager class helps to manage the list of days in an Itinerary.
 */
public class DayManager {
    private List<Day> days;
    private int numberOfDays;

    /**
     * Creates a new instance of DayManager with the necessary fields instantiated.
     */
    public DayManager() {
        days = new ArrayList<>();
        numberOfDays = 0;
    }

    /**
     * Sets the total number of days in the itinerary.
     * @param n number of days.
     */
    public void setDays(int n) {
        numberOfDays = 0;
        days.clear();
        addDays(n);
    }

    /**
     * Set the days in an itinerary.
     * @param days list of days to be imported.
     */
    public void setDays(List<Day> days) {
        requireNonNull(days);
        this.days = days;
    }

    /**
     * Adds additional days to the current list.
     * @param n number of days to add.
     */
    public void addDays(int n) {
        numberOfDays += n;
        for (int i = 0; i < n; i++) {
            days.add(new Day());
        }
    }

    /**
     * Removes a specific Day from the list.
     * @param n index of the Day to be removed, with respect to the list of Days.
     */
    public void removeDay(int n) {
        days.remove(n - 1);
    }

}
