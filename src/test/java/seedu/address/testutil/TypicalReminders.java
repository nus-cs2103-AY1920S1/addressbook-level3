package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.calendar.Reminder;
import seedu.address.model.calendar.Repetition;

/**
 * A utility class containing a list of {@code Reminder} objects to be used in tests.
 */
public class TypicalReminders {
    public static final LocalDate DATE_DEC20 = LocalDate.of(2019, 12, 20);
    public static final LocalDate DATE_JAN04 = LocalDate.of(2020, 1, 4);
    public static final LocalTime TIME_MORN = LocalTime.of(7, 45);
    public static final LocalTime TIME_NOON = LocalTime.of(11, 30);
    public static final LocalTime TIME_EVEN = LocalTime.of(18, 0);
    public static final Repetition DAILY = Repetition.EveryDay;
    public static final Repetition WEEKLY = Repetition.EveryWeek;
    public static final Repetition ONCE = Repetition.Once;

    public static final Reminder LUNCH_INSULIN_INJECTION = new ReminderBuilder()
            .withDescription("Lunch insulin injection")
            .withDateTime(DATE_DEC20, TIME_NOON).withRepetition(DAILY).build();
    public static final Reminder DINNER_INSULIN_INJECTION = new ReminderBuilder()
            .withDescription("Dinner insulin injection").withDateTime(DATE_DEC20, TIME_EVEN)
            .withRepetition(DAILY).build();
    public static final Reminder BUY_INSULIN = new ReminderBuilder().withDescription("Buy insulin")
            .withDateTime(DATE_JAN04, TIME_MORN).withRepetition(ONCE).build();
    public static final Reminder CHECK_WEEKLY_SUMMARY = new ReminderBuilder().withDescription("Check weekly summary")
            .withDateTime(DATE_DEC20, TIME_EVEN).withRepetition(WEEKLY).build();

    private TypicalReminders() {} // prevents instantiation

    public static List<Reminder> getTypicalReminders() {
        return new ArrayList<>(Arrays.asList(LUNCH_INSULIN_INJECTION, DINNER_INSULIN_INJECTION, BUY_INSULIN,
                CHECK_WEEKLY_SUMMARY));
    }
}
