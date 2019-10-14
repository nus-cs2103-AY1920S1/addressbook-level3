package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalActivities.BREAKFAST;
import static seedu.address.testutil.TypicalActivities.LUNCH;
import static seedu.address.testutil.TypicalActivities.getTypicalActivityBook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.activity.Activity;

public class ActivityBookTest {

    private final ActivityBook activityBook = new ActivityBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), activityBook.getActivityList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> activityBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyActivityBook_replacesData() {
        ActivityBook newData = getTypicalActivityBook();
        activityBook.resetData(newData);
        assertEquals(newData, activityBook);
    }

    @Test
    public void setActivities_withValidActivityArrayList_replacesData() {
        ActivityBook newData = getTypicalActivityBook();
        activityBook.resetData(new ActivityBook());
        ArrayList<Activity> activities = new ArrayList<Activity>();
        activities.add(BREAKFAST);
        activities.add(LUNCH);

        activityBook.setActivities(activities);

        assertEquals(newData, activityBook);
    }

    /**
     * A stub ReadOnlyActivityBook whose persons list can violate interface constraints.
     */
    private static class ActivityBookStub extends ActivityBook {
        private final ArrayList<Activity> activityList = new ArrayList<Activity>();

        ActivityBookStub(Collection<Activity> activities) {
            this.activityList.addAll(activities);
        }

        @Override
        public ArrayList<Activity> getActivityList() {
            return activityList;
        }
    }

}
