package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalActivities.BREAKFAST;
import static seedu.address.testutil.TypicalActivities.BREAKFAST_EMPTY;
import static seedu.address.testutil.TypicalActivities.BREAKFAST_SECOND;
import static seedu.address.testutil.TypicalActivities.LUNCH;
import static seedu.address.testutil.TypicalActivities.getTypicalActivityBook;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.exceptions.ActivityNotFoundException;

public class ActivityBookTest {

    private final ActivityBook activityBook = new ActivityBook();

    @Test
    public void constructor() {
        assertEquals(
            FXCollections.unmodifiableObservableList(FXCollections.observableArrayList()),
            activityBook.getActivityList());
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

    @Test
    public void addActivity_nullActivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> activityBook.addActivity(null));
    }

    @Test
    public void removeActivity_nullActivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> activityBook.addActivity(null));
    }

    @Test
    public void removeActivity_activityNotInList_throwsActivityNotFoundException() {
        ActivityBook activities = getTypicalActivityBook();
        assertThrows(ActivityNotFoundException.class, () -> activities.removeActivity(BREAKFAST_EMPTY));
    }

    @Test
    public void setActivity_eitherNullActivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> activityBook.setActivity(null, BREAKFAST));
        assertThrows(NullPointerException.class, () -> activityBook.setActivity(LUNCH, null));
    }

    @Test
    public void setActivity_targetActivityNotInList_throwsActivityNotFoundException() {
        ActivityBook activities = getTypicalActivityBook();
        assertThrows(ActivityNotFoundException.class, ()
            -> activities.setActivity(BREAKFAST_SECOND, BREAKFAST_EMPTY));
    }

    @Test
    public void getActivityList_modifyList_throwsUnsupportedOperationException() {
        ActivityBook activities = getTypicalActivityBook();
        assertThrows(UnsupportedOperationException.class, ()
            -> activities.getActivityList().remove(1));
    }

    /**
     * A stub ReadOnlyActivityBook whose activity list can violate interface constraints.
     */
    private static class ActivityBookStub extends ActivityBook {
        private final ObservableList<Activity> activityList =
                FXCollections.observableArrayList();
        private final ObservableList<Activity> unmodifiableActivityList =
                FXCollections.unmodifiableObservableList(activityList);

        ActivityBookStub(Collection<Activity> activities) {
            this.activityList.addAll(activities);
        }

        @Override
        public ObservableList<Activity> getActivityList() {
            return unmodifiableActivityList;
        }
    }

}
