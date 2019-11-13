package seedu.planner.model.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_ACTIVITY_ADDRESS_A;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_COST_TWO_HUNDRED;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_PRIORITY_SEVEN;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_TAG_HIKING;
import static seedu.planner.testutil.Assert.assertThrows;
import static seedu.planner.testutil.activity.TypicalActivity.ACTIVITY_ONE;
import static seedu.planner.testutil.activity.TypicalActivity.ACTIVITY_TWO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.planner.model.activity.exceptions.ActivityNotFoundException;
import seedu.planner.model.activity.exceptions.DuplicateActivityException;
import seedu.planner.testutil.activity.ActivityBuilder;
import seedu.planner.testutil.contact.ContactBuilder;

public class UniqueActivityListTest {
    private final UniqueActivityList uniqueActivityList = new UniqueActivityList();

    @Test
    public void contains_nullActivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueActivityList.contains(null));
    }

    @Test
    public void contains_activityNotInList_returnsFalse() {
        assertFalse(uniqueActivityList.contains(ACTIVITY_ONE));
    }

    @Test
    public void contains_activityInList_returnsTrue() {
        uniqueActivityList.add(ACTIVITY_ONE);
        assertTrue(uniqueActivityList.contains(ACTIVITY_ONE));
    }

    @Test
    public void contains_activityWithSameIdentityFieldsInList_returnsTrue() {
        uniqueActivityList.add(ACTIVITY_ONE);
        Activity editedActivity = new ActivityBuilder(ACTIVITY_ONE).withContact(new ContactBuilder().build())
                .withCost(VALID_COST_TWO_HUNDRED).withPriority(VALID_PRIORITY_SEVEN)
                .withTags(VALID_TAG_HIKING).build();
        assertTrue(uniqueActivityList.contains(editedActivity));
    }

    @Test
    public void add_nullActivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueActivityList.add(null));
    }

    @Test
    public void add_duplicateActivity_throwsDuplicateActivityException() {
        uniqueActivityList.add(ACTIVITY_ONE);
        assertThrows(DuplicateActivityException.class, () -> uniqueActivityList.add(ACTIVITY_ONE));
    }

    @Test
    public void setActivity_nullTargetActivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueActivityList.setActivity(null, ACTIVITY_ONE));
    }

    @Test
    public void setActivity_nullEditedActivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueActivityList.setActivity(ACTIVITY_ONE, null));
    }

    @Test
    public void setActivity_targetActivityNotInList_throwsActivityNotFoundException() {
        assertThrows(ActivityNotFoundException.class, () -> uniqueActivityList.setActivity(ACTIVITY_ONE, ACTIVITY_ONE));
    }

    @Test
    public void setActivity_editedActivityIsSameActivity_success() {
        uniqueActivityList.add(ACTIVITY_ONE);
        uniqueActivityList.setActivity(ACTIVITY_ONE, ACTIVITY_ONE);
        UniqueActivityList expectedUniqueActivityList = new UniqueActivityList();
        expectedUniqueActivityList.add(ACTIVITY_ONE);
        assertEquals(expectedUniqueActivityList, uniqueActivityList);
    }

    @Test
    public void setActivity_editedActivityHasSameIdentity_success() {
        uniqueActivityList.add(ACTIVITY_ONE);
        Activity editedActivity = new ActivityBuilder(ACTIVITY_ONE).withAddress(VALID_ACTIVITY_ADDRESS_A)
                .withTags(VALID_TAG_HIKING).build();
        uniqueActivityList.setActivity(ACTIVITY_ONE, editedActivity);
        UniqueActivityList expectedUniqueActivityList = new UniqueActivityList();
        expectedUniqueActivityList.add(editedActivity);
        assertEquals(expectedUniqueActivityList, uniqueActivityList);
    }

    @Test
    public void setActivity_editedActivityHasDifferentIdentity_success() {
        uniqueActivityList.add(ACTIVITY_ONE);
        uniqueActivityList.setActivity(ACTIVITY_ONE, ACTIVITY_TWO);
        UniqueActivityList expectedUniqueActivityList = new UniqueActivityList();
        expectedUniqueActivityList.add(ACTIVITY_TWO);
        assertEquals(expectedUniqueActivityList, uniqueActivityList);
    }

    @Test
    public void setActivity_editedActivityHasNonUniqueIdentity_throwsDuplicateActivityException() {
        uniqueActivityList.add(ACTIVITY_ONE);
        uniqueActivityList.add(ACTIVITY_TWO);
        assertThrows(DuplicateActivityException.class, () ->
                uniqueActivityList.setActivity(ACTIVITY_ONE, ACTIVITY_TWO));
    }

    @Test
    public void remove_nullActivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueActivityList.remove(null));
    }

    @Test
    public void remove_activityDoesNotExist_throwsActivityNotFoundException() {
        assertThrows(ActivityNotFoundException.class, () -> uniqueActivityList.remove(ACTIVITY_ONE));
    }

    @Test
    public void remove_existingActivity_removesActivity() {
        uniqueActivityList.add(ACTIVITY_ONE);
        uniqueActivityList.remove(ACTIVITY_ONE);
        UniqueActivityList expectedUniqueActivityList = new UniqueActivityList();
        assertEquals(expectedUniqueActivityList, uniqueActivityList);
    }

    @Test
    public void setActivities_nullUniqueActivityList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueActivityList.setActivities((UniqueActivityList) null));
    }

    @Test
    public void setActivities_uniqueActivityList_replacesOwnListWithProvidedUniqueActivityList() {
        uniqueActivityList.add(ACTIVITY_ONE);
        UniqueActivityList expectedUniqueActivityList = new UniqueActivityList();
        expectedUniqueActivityList.add(ACTIVITY_TWO);
        uniqueActivityList.setActivities(expectedUniqueActivityList);
        assertEquals(expectedUniqueActivityList, uniqueActivityList);
    }

    @Test
    public void setActivities_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueActivityList.setActivities((List<Activity>) null));
    }

    @Test
    public void setActivities_list_replacesOwnListWithProvidedList() {
        uniqueActivityList.add(ACTIVITY_ONE);
        List<Activity> activityList = Collections.singletonList(ACTIVITY_TWO);
        uniqueActivityList.setActivities(activityList);
        UniqueActivityList expectedUniqueActivityList = new UniqueActivityList();
        expectedUniqueActivityList.add(ACTIVITY_TWO);
        assertEquals(expectedUniqueActivityList, uniqueActivityList);
    }

    @Test
    public void setActivities_listWithDuplicateActivities_throwsDuplicateActivityException() {
        List<Activity> listWithDuplicateActivities = Arrays.asList(ACTIVITY_ONE, ACTIVITY_ONE);
        assertThrows(DuplicateActivityException.class, ()
            -> uniqueActivityList.setActivities(listWithDuplicateActivities));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueActivityList.asUnmodifiableObservableList().remove(0));
    }
}
