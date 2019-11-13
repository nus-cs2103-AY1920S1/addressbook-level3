package seedu.planner.testutil.day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.planner.model.day.ActivityWithTime;
import seedu.planner.testutil.activity.TypicalActivity;

/**
 * A utility class containing a list of {@code Activity} objects to be used in tests.
 */
public class TypicalActivityWithTime {
    public static final ActivityWithTime ACTIVITY_WITH_TIME_A1 = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_ONE)
            .withStartDateTime(2019, 11, 1, 9, 30)
            .build();

    public static final ActivityWithTime ACTIVITY_WITH_TIME_A2 = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_TWO)
            .withStartDateTime(2019, 11, 1, 9, 30)
            .build();

    public static final ActivityWithTime ACTIVITY_WITH_TIME_A3 = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_THREE)
            .withStartDateTime(2019, 11, 1, 11, 30)
            .build();

    public static final ActivityWithTime ACTIVITY_WITH_TIME_A4 = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_FOUR)
            .withStartDateTime(2019, 11, 1, 2, 00)
            .build();

    public static final ActivityWithTime ACTIVITY_WITH_TIME_B1 = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_TWO)
            .withStartDateTime(2019, 11, 2, 1, 30)
            .build();

    public static final ActivityWithTime ACTIVITY_WITH_TIME_B2 = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_THREE)
            .withStartDateTime(2019, 11, 2, 2, 00)
            .build();

    public static final ActivityWithTime ACTIVITY_WITH_TIME_B3 = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_FOUR)
            .withStartDateTime(2019, 11, 2, 3, 30)
            .build();

    public static final ActivityWithTime ACTIVITY_WITH_TIME_B4 = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_FIVE)
            .withStartDateTime(2019, 11, 2, 6, 00)
            .build();

    public static final ActivityWithTime ACTIVITY_WITH_TIME_C1 = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_THREE)
            .withStartDateTime(2019, 11, 3, 10, 30)
            .build();

    public static final ActivityWithTime ACTIVITY_WITH_TIME_C2 = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_FOUR)
            .withStartDateTime(2019, 11, 3, 16, 00)
            .build();

    public static final ActivityWithTime ACTIVITY_WITH_TIME_C3 = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_FIVE)
            .withStartDateTime(2019, 11, 3, 20, 00)
            .build();

    public static final ActivityWithTime ACTIVITY_WITH_TIME_D1 = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_FIVE)
            .withStartDateTime(2019, 11, 4, 6, 30)
            .build();

    public static final ActivityWithTime ACTIVITY_WITH_TIME_D2 = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_SIX)
            .withStartDateTime(2019, 11, 4, 10, 00)
            .build();

    public static final ActivityWithTime ACTIVITY_WITH_TIME_D3 = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_SEVEN)
            .withStartDateTime(2019, 11, 4, 14, 30)
            .build();

    //Manually added
    public static final ActivityWithTime ACTIVITY_WITH_TIME_F = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_EIGHT)
            .withStartDateTime(2019, 11, 5, 17, 30)
            .build();

    public static final ActivityWithTime ACTIVITY_WITH_TIME_G = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_NINE)
            .withStartDateTime(2019, 11, 5, 11, 00)
            .build();

    // Manually added - Activity's details found in {@code CommandTestUtil}
    public static final ActivityWithTime ACTIVITY_WITH_TIME_J = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_A)
            .withStartDateTime(2019, 11, 5, 5, 0)
            .build();

    public static final ActivityWithTime ACTIVITY_WITH_TIME_K = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_B)
            .withStartDateTime(2019, 11, 5, 7, 0)
            .build();

    private TypicalActivityWithTime() {
    } // prevents instantiation

    public static List<ActivityWithTime> getTypicalActivityWithTime1() {
        return new ArrayList<>(Arrays.asList(ACTIVITY_WITH_TIME_A1, ACTIVITY_WITH_TIME_A3, ACTIVITY_WITH_TIME_A4));
    }

    public static List<ActivityWithTime> getTypicalActivityWithTime2() {
        return new ArrayList<>(Arrays.asList(ACTIVITY_WITH_TIME_B1, ACTIVITY_WITH_TIME_B3, ACTIVITY_WITH_TIME_B4));
    }

    public static List<ActivityWithTime> getTypicalActivityWithTime3() {
        return new ArrayList<>(Arrays.asList(ACTIVITY_WITH_TIME_C1, ACTIVITY_WITH_TIME_C2, ACTIVITY_WITH_TIME_C3));
    }

    public static List<ActivityWithTime> getTypicalActivityWithTime4() {
        return new ArrayList<>(Arrays.asList(ACTIVITY_WITH_TIME_D1, ACTIVITY_WITH_TIME_D2, ACTIVITY_WITH_TIME_D3));
    }

    public static List<ActivityWithTime> getTypicalActivityWithTime5() {
        return new ArrayList<>(Arrays.asList(ACTIVITY_WITH_TIME_F, ACTIVITY_WITH_TIME_G));
    }

    public static List<ActivityWithTime> getTypicalActivityWithTimeWithConflict1() {
        return new ArrayList<>(Arrays.asList(ACTIVITY_WITH_TIME_A1, ACTIVITY_WITH_TIME_A2, ACTIVITY_WITH_TIME_A3,
                ACTIVITY_WITH_TIME_A4));
    }

    public static List<ActivityWithTime> getTypicalActivityWithTimeWithConflict2() {
        return new ArrayList<>(Arrays.asList(ACTIVITY_WITH_TIME_B1, ACTIVITY_WITH_TIME_B2, ACTIVITY_WITH_TIME_B3,
                ACTIVITY_WITH_TIME_B4));
    }
}
