package seedu.planner.model.activity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_ACTIVITY_COUNT;
import static seedu.planner.testutil.Assert.assertThrows;
import static seedu.planner.testutil.activity.TypicalActivityWithCount.ACTIVITYWITHCOUNT_A;
import static seedu.planner.testutil.activity.TypicalActivityWithCount.ACTIVITYWITHCOUNT_B;

import org.junit.jupiter.api.Test;

import seedu.planner.testutil.activity.ActivityWithCountBuilder;

public class ActivityWithCountTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        ActivityWithCount activityWithCount = new ActivityWithCountBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> activityWithCount.getActivity().getTags().remove(0));
    }

    @Test
    public void equals() {
        // same values -> returns true
        ActivityWithCount activityCopy = new ActivityWithCountBuilder(ACTIVITYWITHCOUNT_A).build();
        assertTrue(ACTIVITYWITHCOUNT_A.equals(activityCopy));

        // same object -> returns true
        assertTrue(ACTIVITYWITHCOUNT_A.equals(ACTIVITYWITHCOUNT_A));

        // null -> returns false
        assertFalse(ACTIVITYWITHCOUNT_A.equals(null));

        // different type -> returns false
        assertFalse(ACTIVITYWITHCOUNT_A.equals(5));

        // different activities -> returns false
        assertFalse(ACTIVITYWITHCOUNT_A.equals(ACTIVITYWITHCOUNT_B));

        // different count -> returns false
        ActivityWithCount editedActivity = new ActivityWithCountBuilder(ACTIVITYWITHCOUNT_A)
                .withCount(VALID_ACTIVITY_COUNT).build();
        assertFalse(ACTIVITYWITHCOUNT_A.equals(editedActivity));

    }
}
