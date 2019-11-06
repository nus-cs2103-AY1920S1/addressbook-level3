package seedu.planner.testutil.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.planner.model.activity.ActivityWithCount;

public class TypicalActivityWithCount {
    public static final ActivityWithCount ACTIVITYWITHCOUNT_A = new ActivityWithCountBuilder()
            .withActivity(TypicalActivity.ACTIVITYONE)
            .withCount(1)
            .build();

    public static final ActivityWithCount ACTIVITYWITHCOUNT_B = new ActivityWithCountBuilder()
            .withActivity(TypicalActivity.ACTIVITYTWO)
            .withCount(2)
            .build();

    public static final ActivityWithCount ACTIVITYWITHCOUNT_C = new ActivityWithCountBuilder()
            .withActivity(TypicalActivity.ACTIVITYTHREE)
            .withCount(3)
            .build();

    public static final ActivityWithCount ACTIVITYWITHCOUNT_D = new ActivityWithCountBuilder()
            .withActivity(TypicalActivity.ACTIVITYFOUR)
            .withCount(4)
            .build();

    public static final ActivityWithCount ACTIVITYWITHCOUNT_E = new ActivityWithCountBuilder()
            .withActivity(TypicalActivity.ACTIVITYFIVE)
            .withCount(5)
            .build();

    public static final ActivityWithCount ACTIVITYWITHCOUNT_F = new ActivityWithCountBuilder()
            .withActivity(TypicalActivity.ACTIVITYSIX)
            .withCount(6)
            .build();

    public static final ActivityWithCount ACTIVITYWITHCOUNT_G = new ActivityWithCountBuilder()
            .withActivity(TypicalActivity.ACTIVITYSEVEN)
            .withCount(7)
            .build();

    //Manually added
    public static final ActivityWithCount ACTIVITYWITHCOUNT_H = new ActivityWithCountBuilder()
            .withActivity(TypicalActivity.ACTIVITYEIGHT)
            .withCount(8)
            .build();

    public static final ActivityWithCount ACTIVITYWITHCOUNT_I = new ActivityWithCountBuilder()
            .withActivity(TypicalActivity.ACTIVITYNINE)
            .withCount(9)
            .build();

    // Manually added - Activity's details found in {@code CommandTestUtil}
    public static final ActivityWithCount ACTIVITYWITHCOUNT_J = new ActivityWithCountBuilder()
            .withActivity(TypicalActivity.ACTIVITY_A)
            .withCount(10)
            .build();

    public static final ActivityWithCount ACTIVITYWITHCOUNT_K = new ActivityWithCountBuilder()
            .withActivity(TypicalActivity.ACTIVITY_B)
            .withCount(11)
            .build();

    private TypicalActivityWithCount() {
    } // prevents instantiation

    public static List<ActivityWithCount> getTypicalActivityWithCount1() {
        return new ArrayList<>(Arrays.asList(ACTIVITYWITHCOUNT_A, ACTIVITYWITHCOUNT_B, ACTIVITYWITHCOUNT_C));
    }

    public static List<ActivityWithCount> getTypicalActivityWithCount2() {
        return new ArrayList<>(Arrays.asList(ACTIVITYWITHCOUNT_D, ACTIVITYWITHCOUNT_E, ACTIVITYWITHCOUNT_F));
    }

    public static List<ActivityWithCount> getTypicalActivityWithCount3() {
        return new ArrayList<>(Arrays.asList(ACTIVITYWITHCOUNT_G, ACTIVITYWITHCOUNT_H, ACTIVITYWITHCOUNT_I));
    }

    public static List<ActivityWithCount> getTypicalActivityWithCount4() {
        return new ArrayList<>(Arrays.asList(ACTIVITYWITHCOUNT_A, ACTIVITYWITHCOUNT_B, ACTIVITYWITHCOUNT_D));
    }

    public static List<ActivityWithCount> getTypicalActivityWithCount5() {
        return new ArrayList<>(Arrays.asList(ACTIVITYWITHCOUNT_A, ACTIVITYWITHCOUNT_F, ACTIVITYWITHCOUNT_D));
    }

    public static List<ActivityWithCount> getTypicalActivityWithCount6() {
        return new ArrayList<>(Arrays.asList(ACTIVITYWITHCOUNT_C, ACTIVITYWITHCOUNT_G, ACTIVITYWITHCOUNT_D));
    }

    public static List<ActivityWithCount> getTypicalActivityWithCount7() {
        return new ArrayList<>(Arrays.asList(ACTIVITYWITHCOUNT_A, ACTIVITYWITHCOUNT_I, ACTIVITYWITHCOUNT_D));
    }
}
