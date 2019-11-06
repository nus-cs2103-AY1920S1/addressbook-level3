package seedu.planner.model.activity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_ACTIVITY_ADDRESS_A;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_ACTIVITY_NAME_A;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_ACTIVITY_NAME_B;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_COST_HUNDRED;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_DURATION_A;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_PRIORITY_TWENTY;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_TAG_HIKING;
import static seedu.planner.testutil.Assert.assertThrows;
import static seedu.planner.testutil.activity.TypicalActivity.ACTIVITYONE;
import static seedu.planner.testutil.activity.TypicalActivity.ACTIVITYTWO;

import org.junit.jupiter.api.Test;

import seedu.planner.testutil.activity.ActivityBuilder;
import seedu.planner.testutil.contact.ContactBuilder;

public class ActivityTest {
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Activity activity = new ActivityBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> activity.getTags().remove(0));
    }

    @Test
    public void isSameActivity() {
        // same object -> returns true
        assertTrue(ACTIVITYONE.isSameActivity(ACTIVITYONE));

        // null -> returns false
        assertFalse(ACTIVITYONE.isSameActivity(null));

        // different address -> returns false
        Activity editedActivity = new ActivityBuilder(ACTIVITYONE).withAddress(VALID_ACTIVITY_ADDRESS_A).build();
        assertFalse(ACTIVITYONE.isSameActivity(editedActivity));

        // different name -> returns false
        editedActivity = new ActivityBuilder(ACTIVITYONE).withName(VALID_ACTIVITY_NAME_B).build();
        assertFalse(ACTIVITYONE.isSameActivity(editedActivity));

        // same name, different attributes -> returns false
        editedActivity = new ActivityBuilder(ACTIVITYONE).withAddress(VALID_ACTIVITY_ADDRESS_A)
                .withContact(new ContactBuilder().build()).withCost(VALID_COST_HUNDRED)
                .withPriority(VALID_PRIORITY_TWENTY).withTags(VALID_TAG_HIKING).build();
        assertFalse(ACTIVITYONE.isSameActivity(editedActivity));

        //same address, different attributes -> returns false
        editedActivity = new ActivityBuilder(ACTIVITYONE).withName(VALID_ACTIVITY_NAME_B)
                .withContact(new ContactBuilder().build()).withCost(VALID_COST_HUNDRED)
                .withPriority(VALID_PRIORITY_TWENTY).withTags(VALID_TAG_HIKING).build();
        assertFalse(ACTIVITYONE.isSameActivity(editedActivity));

        // same name, same address, different attributes -> returns true
        editedActivity = new ActivityBuilder(ACTIVITYONE).withPriority(VALID_PRIORITY_TWENTY)
                .withCost(VALID_COST_HUNDRED).withContact(new ContactBuilder().build())
                .withTags(VALID_TAG_HIKING).build();
        assertTrue(ACTIVITYONE.isSameActivity(editedActivity));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Activity activityCopy = new ActivityBuilder(ACTIVITYONE).build();
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

    @Test
    public void compareTo() {
        //activity having same priority
        assertTrue(ACTIVITYONE.compareTo(ACTIVITYONE) == 0);

        //activity having higher priority
        assertTrue(ACTIVITYONE.compareTo(ACTIVITYTWO) == -1);

        //activity having lower priority
        assertTrue(ACTIVITYTWO.compareTo(ACTIVITYONE) == 1);

        //activity having lowest priority
        assertTrue(new ActivityBuilder().withPriority("0").build().compareTo(ACTIVITYONE) == 1);

        //other activity that are comparing to has lowest priority
        assertTrue(ACTIVITYONE.compareTo(new ActivityBuilder().withPriority("0").build()) == -1);

    }
}
