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
    public static final ActivityWithTime ACTIVITYONE = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYONE)
            .withStartDateTime(2019, 11, 5, 9, 30)
            .build();

    public static final ActivityWithTime ACTIVITYTWO = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYTWO)
            .withStartDateTime(2019, 11, 4, 1, 30)
            .build();

    public static final ActivityWithTime ACTIVITYTHREE = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYTHREE)
            .withStartDateTime(2019, 11, 6, 20, 00)
            .build();

    public static final ActivityWithTime ACTIVITYFOUR = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYFOUR)
            .withStartDateTime(2019, 11, 6, 10, 30)
            .build();

    public static final ActivityWithTime ACTIVITYFIVE = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYFIVE)
            .withStartDateTime(2019, 11, 7, 6, 30)
            .build();

    public static final ActivityWithTime ACTIVITYSIX = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYSIX)
            .withStartDateTime(2019, 11, 7, 9, 30)
            .build();

    public static final ActivityWithTime ACTIVITYSEVEN = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYSEVEN)
            .withStartDateTime(2019, 11, 8, 14, 30)
            .build();

    //Manually added
    public static final ActivityWithTime ACTIVITYEIGHT = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYEIGHT)
            .withStartDateTime(2019, 11, 9, 17, 30)
            .build();

    public static final ActivityWithTime ACTIVITYNINE = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYNINE)
            .withStartDateTime(2019, 11, 9, 11, 00)
            .build();

    // Manually added - Activity's details found in {@code CommandTestUtil}
    public static final ActivityWithTime ACTIVITY_A = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_A)
            .withStartDateTime(2019, 11, 9, 5, 0)
            .build();

    public static final ActivityWithTime ACTIVITY_B = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_B)
            .withStartDateTime(2019, 11, 10, 7, 0)
            .build();

    private TypicalActivityWithTime() {
    } // prevents instantiation

    public static List<ActivityWithTime> getTypicalActivityWithTime1() {
        return new ArrayList<>(Arrays.asList(ACTIVITYONE, ACTIVITYTWO, ACTIVITYTHREE));
    }

    public static List<ActivityWithTime> getTypicalActivityWithTime2() {
        return new ArrayList<>(Arrays.asList(ACTIVITYFOUR, ACTIVITYFIVE, ACTIVITYSIX));
    }

    public static List<ActivityWithTime> getTypicalActivityWithTime3() {
        return new ArrayList<>(Arrays.asList(ACTIVITYSEVEN, ACTIVITYEIGHT, ACTIVITYNINE));
    }

    public static List<ActivityWithTime> getTypicalActivityWithTime4() {
        return new ArrayList<>(Arrays.asList(ACTIVITYONE, ACTIVITYTWO, ACTIVITYFOUR));
    }

    public static List<ActivityWithTime> getTypicalActivityWithTime5() {
        return new ArrayList<>(Arrays.asList(ACTIVITYONE, ACTIVITYSIX, ACTIVITYFOUR));
    }

    public static List<ActivityWithTime> getTypicalActivityWithTime6() {
        return new ArrayList<>(Arrays.asList(ACTIVITYTHREE, ACTIVITYSEVEN, ACTIVITYFOUR));
    }

    public static List<ActivityWithTime> getTypicalActivityWithTime7() {
        return new ArrayList<>(Arrays.asList(ACTIVITYONE, ACTIVITYNINE, ACTIVITYFOUR));
    }
}
