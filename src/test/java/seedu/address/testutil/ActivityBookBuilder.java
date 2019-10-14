package seedu.address.testutil;

import seedu.address.model.ActivityBook;
import seedu.address.model.activity.Activity;

/**
 * A utility class to help with building ActivityBook objects.
 * Example usage: <br>
 *     {@code ActivityBook ab = new ActivityBookBuilder().withPerson("John", "Doe").build();}
 */
public class ActivityBookBuilder {

    private ActivityBook activityBook;

    public ActivityBookBuilder() {
        activityBook = new ActivityBook();
    }

    public ActivityBookBuilder(ActivityBook activityBook) {
        this.activityBook = activityBook;
    }

    /**
     * Adds a new {@code Activity} to the {@code ActivityBook} that we are building.
     */
    public ActivityBookBuilder withActivity(Activity activity) {
        activityBook.addActivity(activity);
        return this;
    }

    public ActivityBook build() {
        return activityBook;
    }
}
