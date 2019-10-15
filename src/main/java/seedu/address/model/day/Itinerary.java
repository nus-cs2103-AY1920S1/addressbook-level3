package seedu.address.model.day;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Itinerary class helps to manage the list of days in an Planner.
 */
public class Itinerary {
    private List<Day> days;
    private int numberOfDays;

    /**
     * Creates a new instance of Itinerary with the necessary fields instantiated.
     */
    public Itinerary() {
        days = new ArrayList<>();
        numberOfDays = 0;
    }

    public List<Day> getDays() {
        return days;
    }

    /**
     * Sets the total number of days in the planner.
     * @param n number of days.
     */
    public void setDays(int n) {
        numberOfDays = 0;
        days.clear();
        addDays(n);
    }

    /**
     * Set the days in an planner.
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Itinerary)) {
            return false;
        }

        //subjected to changes
        Itinerary otherItinerary = (Itinerary) other;
        List<Day> listCurrentDay = this.getDays();
        List<Day> listOtherDay = otherItinerary.getDays();

        boolean result = true;

        for (int i = 0; i < listCurrentDay.size(); i++) {
            result = result && (listCurrentDay.get(i).equals(listOtherDay.get(i)));
        }

        return result;
    }
}
