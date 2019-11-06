package seedu.planner.model.activity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_ACTIVITY_COUNT;
import static seedu.planner.testutil.Assert.assertThrows;
import static seedu.planner.testutil.activity.TypicalActivity.ACTIVITYONE;

import org.junit.jupiter.api.Test;

import seedu.planner.testutil.activity.ActivityBuilder;

public class ActivityWithCountTest {
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        ActivityWithCount activityWithCount = new ActivityWithCount(ACTIVITYONE, Long.parseLong(VALID_ACTIVITY_COUNT));
        assertThrows(UnsupportedOperationException.class, () -> activityWithCount.getActivity().getTags().remove(0));
    }

    @Test
    public void equals() {
        // same values -> returns true
        ActivityWithCount activityCopy = new ActivityWithCount(new ActivityBuilder(ACTIVITYONE).build(),
                Long.parseLong(VALID_ACTIVITY_COUNT));
        assertTrue(ACTIVITYONE.equals(activityCopy));

        // same object -> returns true
        assertTrue(ACTIVITYONE.equals(ACTIVITYONE));

        // null -> returns false
        assertFalse(ACTIVITYONE.equals(null));

        // different type -> returns false
        assertFalse(ACTIVITYONE.equals(5));

        // different activities -> returns false
        assertFalse(ACTIVITYONE.equals(ACTIVITYTWO));

        // different name -> returns false
        Activity editedActivity = new ActivityBuilder(ACTIVITYONE).withName(VALID_ACTIVITY_NAME_A).build();
        assertFalse(ACTIVITYONE.equals(editedActivity));

        // different contact -> returns false
        editedActivity = new ActivityBuilder(ACTIVITYONE).withContact(new ContactBuilder().build()).build();
        assertFalse(ACTIVITYONE.equals(editedActivity));

        // different address -> returns false
        editedActivity = new ActivityBuilder(ACTIVITYONE).withAddress(VALID_ACTIVITY_ADDRESS_A).build();
        assertFalse(ACTIVITYONE.equals(editedActivity));

        // different tags -> returns false
        editedActivity = new ActivityBuilder(ACTIVITYONE).withTags(VALID_TAG_HIKING).build();
        assertFalse(ACTIVITYONE.equals(editedActivity));

        // different cost -> returns false
        editedActivity = new ActivityBuilder(ACTIVITYONE).withCost(VALID_COST_HUNDRED).build();
        assertFalse(ACTIVITYONE.equals(editedActivity));

        // different duration -> returns false
        editedActivity = new ActivityBuilder(ACTIVITYONE).withDuration(VALID_DURATION_A).build();
        assertFalse(ACTIVITYONE.equals(editedActivity));

        // different priorities -> returns false
        editedActivity = new ActivityBuilder(ACTIVITYONE).withPriority(VALID_PRIORITY_TWENTY).build();
        assertFalse(ACTIVITYONE.equals(editedActivity));
    }
}
