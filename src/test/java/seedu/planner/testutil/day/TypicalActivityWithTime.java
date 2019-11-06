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
    public static final ActivityWithTime ACTIVITYWITHTIME_A = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYONE)
            .withStartDateTime(2019, 11, 5, 9, 30)
            .build();

    public static final ActivityWithTime ACTIVITYWITHTIME_B = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYTWO)
            .withStartDateTime(2019, 11, 4, 1, 30)
            .build();

    public static final ActivityWithTime ACTIVITYWITHTIME_C = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYTHREE)
            .withStartDateTime(2019, 11, 6, 20, 00)
            .build();

    public static final ActivityWithTime ACTIVITYWITHTIME_D = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYFOUR)
            .withStartDateTime(2019, 11, 6, 10, 30)
            .build();

    public static final ActivityWithTime ACTIVITYWITHTIME_E = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYFIVE)
            .withStartDateTime(2019, 11, 7, 6, 30)
            .build();

    public static final ActivityWithTime ACTIVITYWITHTIME_F = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYSIX)
            .withStartDateTime(2019, 11, 7, 9, 30)
            .build();

    public static final ActivityWithTime ACTIVITYWITHTIME_G = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYSEVEN)
            .withStartDateTime(2019, 11, 8, 14, 30)
            .build();

    //Manually added
    public static final ActivityWithTime ACTIVITYWITHTIME_H = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYEIGHT)
            .withStartDateTime(2019, 11, 9, 17, 30)
            .build();

    public static final ActivityWithTime ACTIVITYWITHTIME_I = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYNINE)
            .withStartDateTime(2019, 11, 9, 11, 00)
            .build();

    // Manually added - Activity's details found in {@code CommandTestUtil}
    public static final ActivityWithTime ACTIVITYWITHTIME_J = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_A)
            .withStartDateTime(2019, 11, 9, 5, 0)
            .build();

    public static final ActivityWithTime ACTIVITYWITHTIME_K = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_B)
            .withStartDateTime(2019, 11, 10, 7, 0)
            .build();

    private TypicalActivityWithTime() {
    } // prevents instantiation

    public static List<ActivityWithTime> getTypicalActivityWithTime1() {
        return new ArrayList<>(Arrays.asList(ACTIVITYWITHTIME_A, ACTIVITYWITHTIME_B, ACTIVITYWITHTIME_C));
    }

    public static List<ActivityWithTime> getTypicalActivityWithTime2() {
        return new ArrayList<>(Arrays.asList(ACTIVITYWITHTIME_D, ACTIVITYWITHTIME_E, ACTIVITYWITHTIME_F));
    }

    public static List<ActivityWithTime> getTypicalActivityWithTime3() {
        return new ArrayList<>(Arrays.asList(ACTIVITYWITHTIME_G, ACTIVITYWITHTIME_H, ACTIVITYWITHTIME_I));
    }

    public static List<ActivityWithTime> getTypicalActivityWithTime4() {
        return new ArrayList<>(Arrays.asList(ACTIVITYWITHTIME_A, ACTIVITYWITHTIME_B, ACTIVITYWITHTIME_D));
    }

    public static List<ActivityWithTime> getTypicalActivityWithTime5() {
        return new ArrayList<>(Arrays.asList(ACTIVITYWITHTIME_A, ACTIVITYWITHTIME_F, ACTIVITYWITHTIME_D));
    }

    public static List<ActivityWithTime> getTypicalActivityWithTime6() {
        return new ArrayList<>(Arrays.asList(ACTIVITYWITHTIME_C, ACTIVITYWITHTIME_G, ACTIVITYWITHTIME_D));
    }

    public static List<ActivityWithTime> getTypicalActivityWithTime7() {
        return new ArrayList<>(Arrays.asList(ACTIVITYWITHTIME_A, ACTIVITYWITHTIME_I, ACTIVITYWITHTIME_D));
    }
}
