package seedu.address.testutil.day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Itinerary;
import seedu.address.model.day.Day;

/**
 * A utility class containing a list of {@code Activity} objects to be used in tests.
 */
public class TypicalDays {
    public static final Day DAY1 = new Day(TypicalActivityWithTime.getTypicalActivityWithTime1());

    public static final Day DAY2 = new Day(TypicalActivityWithTime.getTypicalActivityWithTime2());

    public static final Day DAY3 = new Day(TypicalActivityWithTime.getTypicalActivityWithTime3());

    public static final Day DAY4 = new Day(TypicalActivityWithTime.getTypicalActivityWithTime4());

    public static final Day DAY5 = new Day(TypicalActivityWithTime.getTypicalActivityWithTime5());

    public static final Day DAY6 = new Day(TypicalActivityWithTime.getTypicalActivityWithTime6());

    public static final Day DAY7 = new Day(TypicalActivityWithTime.getTypicalActivityWithTime7());

    private TypicalDays() {} // prevents instantiation

    public static Itinerary getTypicalItinerary() {
        Itinerary i = new Itinerary();
        for (Day day : getTypicalDays()) {
            i.addDay(day);
        }
        return i;
    }

    public static List<Day> getTypicalDays() {
        return new ArrayList<>(Arrays.asList(DAY1, DAY2, DAY3, DAY4, DAY5, DAY6, DAY7));
    }
}
