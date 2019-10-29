package seedu.exercise.testutil.typicalutil;

import java.util.Arrays;
import java.util.List;

import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Schedule;
import seedu.exercise.testutil.builder.ScheduleBuilder;

/**
 * Holds some schedule objects that can be used in testing.
 */
public class TypicalSchedule {

    public static final Schedule VALID_SCHEDULE_CARDIO_DATE;
    public static final Schedule VALID_SCHEDULE_LEG_DATE;
    public static final Schedule VALID_SCHEDULE_LEG_DATE_2;

    static {
        VALID_SCHEDULE_CARDIO_DATE = new ScheduleBuilder()
                .withRegime(TypicalRegime.VALID_REGIME_CARDIO)
                .withDate(TypicalDates.DATE_1)
                .build();

        VALID_SCHEDULE_LEG_DATE_2 = new ScheduleBuilder()
                .withRegime(TypicalRegime.VALID_REGIME_LEGS)
                .withDate(TypicalDates.DATE_2)
                .build();

        VALID_SCHEDULE_LEG_DATE = new ScheduleBuilder()
                .withRegime(TypicalRegime.VALID_REGIME_LEGS)
                .withDate(TypicalDates.DATE_1)
                .build();
    }

    private TypicalSchedule() {}

    public static ReadOnlyResourceBook<Schedule> getTypicalScheduleBook() {
        ReadOnlyResourceBook<Schedule> eb = new ReadOnlyResourceBook<>();
        for (Schedule schedule : getValidScheduleList()) {
            eb.addResource(schedule);
        }
        return eb;
    }

    public static List<Schedule> getValidScheduleList() {
        return Arrays.asList(VALID_SCHEDULE_CARDIO_DATE, VALID_SCHEDULE_LEG_DATE_2);
    }
}
