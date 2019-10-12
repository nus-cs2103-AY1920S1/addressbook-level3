package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ActivityBook;
import seedu.address.model.activity.Activity;

/**
 * A utility class containing a list of {@code Activity} objects to be used in tests.
 */
public class TypicalActivities {

    public static final Activity BREAKFAST = new ActivityBuilder()
            .withTitle("Breakfast")
            .build();
    public static final Activity LUNCH = new ActivityBuilder()
            .withTitle("Lunch")
            .build();

    private TypicalActivities() {} // prevents instantiation

    /**
     * Returns an {@code ActivityBook} with all the typical activities.
     */
    public static ActivityBook getTypicalActivityBook() {
        ActivityBook ab = new ActivityBook();
        for (Activity activity : getTypicalActivities()) {
            ab.addActivity(activity);
        }
        return ab;
    }

    public static List<Activity> getTypicalActivities() {
        return new ArrayList<>(Arrays.asList(BREAKFAST));
    }
}
