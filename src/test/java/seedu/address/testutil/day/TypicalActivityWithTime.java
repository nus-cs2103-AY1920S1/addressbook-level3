package seedu.address.testutil.day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.day.ActivityWithTime;
import seedu.address.testutil.activity.TypicalActivity;

/**
 * A utility class containing a list of {@code Activity} objects to be used in tests.
 */
public class TypicalActivityWithTime {
    public static final ActivityWithTime ACTIVITYONE = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYONE).withStartTime(9, 30).withEndTime(13, 30).build();

    public static final ActivityWithTime ACTIVITYTWO = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYTWO).withStartTime(1, 30).withEndTime(3, 30).build();

    public static final ActivityWithTime ACTIVITYTHREE = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYTHREE).withStartTime(20, 00).withEndTime(00, 30).build();

    public static final ActivityWithTime ACTIVITYFOUR = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYFOUR).withStartTime(10, 30).withEndTime(13, 30).build();

    public static final ActivityWithTime ACTIVITYFIVE = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYFIVE).withStartTime(6, 30).withEndTime(9, 15).build();

    public static final ActivityWithTime ACTIVITYSIX = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYSIX).withStartTime(9, 30).withEndTime(13, 30).build();

    public static final ActivityWithTime ACTIVITYSEVEN = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYSEVEN).withStartTime(14, 30).withEndTime(15, 30).build();

    //Manually added
    public static final ActivityWithTime ACTIVITYEIGHT = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYEIGHT).withStartTime(17, 30).withEndTime(18, 30).build();

    public static final ActivityWithTime ACTIVITYNINE = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITYNINE).withStartTime(11, 00).withEndTime(23, 30).build();

    // Manually added - Activity's details found in {@code CommandTestUtil}
    public static final ActivityWithTime ACTIVITY_A = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_A)
            .withStartTime(5, 00).withEndTime(10, 00).build();

    public static final ActivityWithTime ACTIVITY_B = new ActivityWithTimeBuilder()
            .withActivity(TypicalActivity.ACTIVITY_B)
            .withStartTime(7, 00).withEndTime(10, 00).build();

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
