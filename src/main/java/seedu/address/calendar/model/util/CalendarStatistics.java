package seedu.address.calendar.model.util;

import seedu.address.calendar.model.util.exceptions.NoVacationException;

/**
 * Represents a statistics object which contains summary of {@code Calendar}.
 */
public interface CalendarStatistics {
    /**
     * Gets the number of days of vacation (i.e. school breaks and holidays)
     *
     * @return Number of days of vacation (i.e. school breaks and holidays)
     */
    long getNumDaysVacation();

    /**
     * Gets the number of days spent on trips
     *
     * @return Number of days spent on trips
     */
    long getNumDaysTrip();

    /**
     * Gets the number of trips.
     *
     * @return Absolute number of trips
     */
    long getNumTrip();

    /**
     * Gets the percentage of vacation that is spent on trips.
     *
     * @return Percentage of vacation that is spent on trips
     * @throws NoVacationException If there is no vacation
     */
    double getPercentageTrip() throws NoVacationException;
}
