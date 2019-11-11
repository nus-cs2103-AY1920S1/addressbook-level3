package seedu.planner.testutil.day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.planner.model.Itinerary;
import seedu.planner.model.day.Day;

/**
 * A utility class containing a list of {@code Activity} objects to be used in tests.
 */
public class TypicalDays {
    public static final Day CONFLICTLESS_DAY1 = new Day(TypicalActivityWithTime.getTypicalActivityWithTime1());

    public static final Day CONFLICTLESS_DAY2 = new Day(TypicalActivityWithTime.getTypicalActivityWithTime2());

    public static final Day CONFLICTLESS_DAY3 = new Day(TypicalActivityWithTime.getTypicalActivityWithTime3());

    public static final Day CONFLICTLESS_DAY4 = new Day(TypicalActivityWithTime.getTypicalActivityWithTime4());

    public static final Day CONFLICTLESS_DAY5 = new Day(TypicalActivityWithTime.getTypicalActivityWithTime5());

    public static final Day CONFLICTED_DAY1 = new Day(TypicalActivityWithTime
            .getTypicalActivityWithTimeWithConflict1());

    public static final Day CONFLICTED_DAY2 = new Day(TypicalActivityWithTime
            .getTypicalActivityWithTimeWithConflict2());

    private TypicalDays() {} // prevents instantiation

    public static Itinerary getTypicalItinerary() {
        Itinerary i = new Itinerary();
        for (Day day : getTypicalDays()) {
            i.addDay(day);
        }
        return i;
    }

    public static List<Day> getTypicalDays() {
        return new ArrayList<>(Arrays.asList(CONFLICTLESS_DAY1, CONFLICTLESS_DAY2, CONFLICTLESS_DAY3, CONFLICTLESS_DAY4,
                CONFLICTLESS_DAY5));
    }
}
