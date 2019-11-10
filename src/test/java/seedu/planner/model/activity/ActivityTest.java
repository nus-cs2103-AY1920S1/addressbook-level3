package seedu.planner.model.activity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_ACTIVITY_ADDRESS_A;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_ACTIVITY_NAME_A;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_ACTIVITY_NAME_B;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_COST_HUNDRED;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_DURATION_A;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_PRIORITY_SEVEN;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_PRIORITY_SIX;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_TAG_HIKING;
import static seedu.planner.testutil.Assert.assertThrows;
import static seedu.planner.testutil.activity.TypicalActivity.ACTIVITY_ONE;
import static seedu.planner.testutil.activity.TypicalActivity.ACTIVITY_TWO;

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
        assertTrue(ACTIVITY_ONE.isSameActivity(ACTIVITY_ONE));

        // null -> returns false
        assertFalse(ACTIVITY_ONE.isSameActivity((Activity) null));

        // different address -> returns false
        Activity editedActivity = new ActivityBuilder(ACTIVITY_ONE).withAddress(VALID_ACTIVITY_ADDRESS_A).build();
        assertFalse(ACTIVITY_ONE.isSameActivity(editedActivity));

        // different name -> returns false
        editedActivity = new ActivityBuilder(ACTIVITY_ONE).withName(VALID_ACTIVITY_NAME_B).build();
        assertFalse(ACTIVITY_ONE.isSameActivity(editedActivity));

        // same name, different attributes -> returns false
        editedActivity = new ActivityBuilder(ACTIVITY_ONE).withAddress(VALID_ACTIVITY_ADDRESS_A)
                .withContact(new ContactBuilder().build()).withCost(VALID_COST_HUNDRED)
                .withPriority(VALID_PRIORITY_SEVEN).withTags(VALID_TAG_HIKING).build();
        assertFalse(ACTIVITY_ONE.isSameActivity(editedActivity));

        //same address, different attributes -> returns false
        editedActivity = new ActivityBuilder(ACTIVITY_ONE).withName(VALID_ACTIVITY_NAME_B)
                .withContact(new ContactBuilder().build()).withCost(VALID_COST_HUNDRED)
                .withPriority(VALID_PRIORITY_SIX).withTags(VALID_TAG_HIKING).build();
        assertFalse(ACTIVITY_ONE.isSameActivity(editedActivity));

        // same name, same address, different attributes -> returns true
        editedActivity = new ActivityBuilder(ACTIVITY_ONE).withPriority(VALID_PRIORITY_SEVEN)
                .withCost(VALID_COST_HUNDRED).withContact(new ContactBuilder().build())
                .withTags(VALID_TAG_HIKING).build();
        assertTrue(ACTIVITY_ONE.isSameActivity(editedActivity));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Activity activityCopy = new ActivityBuilder(ACTIVITY_ONE).build();
        assertTrue(ACTIVITY_ONE.equals(activityCopy));

        // same object -> returns true
        assertTrue(ACTIVITY_ONE.equals(ACTIVITY_ONE));

        // null -> returns false
        assertFalse(ACTIVITY_ONE.equals(null));

        // different type -> returns false
        assertFalse(ACTIVITY_ONE.equals(5));

        // different activities -> returns false
        assertFalse(ACTIVITY_ONE.equals(ACTIVITY_TWO));

        // different name -> returns false
        Activity editedActivity = new ActivityBuilder(ACTIVITY_ONE).withName(VALID_ACTIVITY_NAME_A).build();
        assertFalse(ACTIVITY_ONE.equals(editedActivity));

        // different contact -> returns false
        editedActivity = new ActivityBuilder(ACTIVITY_ONE).withContact(new ContactBuilder().build()).build();
        assertFalse(ACTIVITY_ONE.equals(editedActivity));

        // different address -> returns false
        editedActivity = new ActivityBuilder(ACTIVITY_ONE).withAddress(VALID_ACTIVITY_ADDRESS_A).build();
        assertFalse(ACTIVITY_ONE.equals(editedActivity));

        // different tags -> returns false
        editedActivity = new ActivityBuilder(ACTIVITY_ONE).withTags(VALID_TAG_HIKING).build();
        assertFalse(ACTIVITY_ONE.equals(editedActivity));

        // different cost -> returns false
        editedActivity = new ActivityBuilder(ACTIVITY_ONE).withCost(VALID_COST_HUNDRED).build();
        assertFalse(ACTIVITY_ONE.equals(editedActivity));

        // different duration -> returns false
        editedActivity = new ActivityBuilder(ACTIVITY_ONE).withDuration(VALID_DURATION_A).build();
        assertFalse(ACTIVITY_ONE.equals(editedActivity));

        // different priorities -> returns false
        editedActivity = new ActivityBuilder(ACTIVITY_ONE).withPriority(VALID_PRIORITY_SEVEN).build();
        assertFalse(ACTIVITY_ONE.equals(editedActivity));
    }

    @Test
    public void compareTo() {
        //activity having same priority
        assertTrue(ACTIVITY_ONE.compareTo(ACTIVITY_ONE) == 0);

        //activity having higher priority
        assertTrue(ACTIVITY_ONE.compareTo(ACTIVITY_TWO) == -1);

        //activity having lower priority
        assertTrue(ACTIVITY_TWO.compareTo(ACTIVITY_ONE) == 1);

        //activity having lowest priority
        assertTrue(new ActivityBuilder().withPriority("0").build().compareTo(ACTIVITY_ONE) == 1);

        //other activity that are comparing to has lowest priority
        assertTrue(ACTIVITY_ONE.compareTo(new ActivityBuilder().withPriority("0").build()) == -1);

    }
}
