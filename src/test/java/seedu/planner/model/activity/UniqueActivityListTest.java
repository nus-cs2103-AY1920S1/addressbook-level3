package seedu.planner.model.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_ACTIVITY_ADDRESS_A;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_COST_TWO_HUNDRED;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_PRIORITY_TWENTY;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_TAG_HIKING;
import static seedu.planner.testutil.Assert.assertThrows;
import static seedu.planner.testutil.activity.TypicalActivity.ACTIVITYONE;
import static seedu.planner.testutil.activity.TypicalActivity.ACTIVITYTWO;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.planner.model.activity.exceptions.ActivityNotFoundException;
import seedu.planner.model.activity.exceptions.DuplicateActivityException;
import seedu.planner.model.contact.Contact;
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
        assertFalse(uniqueActivityList.contains(ACTIVITYONE));
    }

    @Test
    public void contains_activityInList_returnsTrue() {
        uniqueActivityList.add(ACTIVITYONE);
        assertTrue(uniqueActivityList.contains(ACTIVITYONE));
    }

    @Test
    public void contains_activityWithSameIdentityFieldsInList_returnsTrue() {
        uniqueActivityList.add(ACTIVITYONE);
        Activity editedActivity = new ActivityBuilder(ACTIVITYONE).withContact(new ContactBuilder().build())
                .withCost(VALID_COST_TWO_HUNDRED).withPriority(VALID_PRIORITY_TWENTY)
                .withTags(VALID_TAG_HIKING).build();
        assertTrue(uniqueActivityList.contains(editedActivity));
    }

    @Test
    public void add_nullActivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueActivityList.add(null));
    }

    @Test
    public void add_duplicateActivity_throwsDuplicateActivityException() {
        uniqueActivityList.add(ACTIVITYONE);
        assertThrows(DuplicateActivityException.class, () -> uniqueActivityList.add(ACTIVITYONE));
    }

    @Test
    public void setActivity_nullTargetActivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueActivityList.setActivity(null, ACTIVITYONE));
    }

    @Test
    public void setActivity_nullEditedActivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueActivityList.setActivity(ACTIVITYONE, null));
    }

    @Test
    public void setActivity_targetActivityNotInList_throwsActivityNotFoundException() {
        assertThrows(ActivityNotFoundException.class, () -> uniqueActivityList.setActivity(ACTIVITYONE, ACTIVITYONE));
    }

    @Test
    public void setActivity_editedActivityIsSameActivity_success() {
        uniqueActivityList.add(ACTIVITYONE);
        uniqueActivityList.setActivity(ACTIVITYONE, ACTIVITYONE);
        UniqueActivityList expectedUniqueActivityList = new UniqueActivityList();
        expectedUniqueActivityList.add(ACTIVITYONE);
        assertEquals(expectedUniqueActivityList, uniqueActivityList);
    }

    @Test
    public void setActivity_editedActivityHasSameIdentity_success() {
        uniqueActivityList.add(ACTIVITYONE);
        Activity editedActivity = new ActivityBuilder(ACTIVITYONE).withAddress(VALID_ACTIVITY_ADDRESS_A)
                .withTags(VALID_TAG_HIKING).build();
        uniqueActivityList.setActivity(ACTIVITYONE, editedActivity);
        UniqueActivityList expectedUniqueActivityList = new UniqueActivityList();
        expectedUniqueActivityList.add(editedActivity);
        assertEquals(expectedUniqueActivityList, uniqueActivityList);
    }

    @Test
    public void setActivity_editedActivityHasDifferentIdentity_success() {
        uniqueActivityList.add(ACTIVITYONE);
        uniqueActivityList.setActivity(ACTIVITYONE, ACTIVITYTWO);
        UniqueActivityList expectedUniqueActivityList = new UniqueActivityList();
        expectedUniqueActivityList.add(ACTIVITYTWO);
        assertEquals(expectedUniqueActivityList, uniqueActivityList);
    }

    @Test
    public void setActivity_editedActivityHasNonUniqueIdentity_throwsDuplicateActivityException() {
        uniqueActivityList.add(ACTIVITYONE);
        uniqueActivityList.add(ACTIVITYTWO);
        assertThrows(DuplicateActivityException.class, () -> uniqueActivityList.setActivity(ACTIVITYONE, ACTIVITYTWO));
    }

    @Test
    public void remove_nullActivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueActivityList.remove(null));
    }

    @Test
    public void remove_activityDoesNotExist_throwsActivityNotFoundException() {
        assertThrows(ActivityNotFoundException.class, () -> uniqueActivityList.remove(ACTIVITYONE));
    }

    @Test
    public void remove_existingActivity_removesActivity() {
        uniqueActivityList.add(ACTIVITYONE);
        uniqueActivityList.remove(ACTIVITYONE);
        UniqueActivityList expectedUniqueActivityList = new UniqueActivityList();
        assertEquals(expectedUniqueActivityList, uniqueActivityList);
    }

    @Test
    public void setActivities_nullUniqueActivityList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueActivityList.setActivities((UniqueActivityList) null));
    }

    @Test
    public void setActivities_uniqueActivityList_replacesOwnListWithProvidedUniqueActivityList() {
        uniqueActivityList.add(ACTIVITYONE);
        UniqueActivityList expectedUniqueActivityList = new UniqueActivityList();
        expectedUniqueActivityList.add(ACTIVITYTWO);
        uniqueActivityList.setActivities(expectedUniqueActivityList);
        assertEquals(expectedUniqueActivityList, uniqueActivityList);
    }

    @Test
    public void setActivities_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueActivityList.setActivities((List<Activity>) null));
    }

    @Test
    public void setActivities_list_replacesOwnListWithProvidedList() {
        uniqueActivityList.add(ACTIVITYONE);
        List<Activity> activityList = Collections.singletonList(ACTIVITYTWO);
        uniqueActivityList.setActivities(activityList);
        UniqueActivityList expectedUniqueActivityList = new UniqueActivityList();
        expectedUniqueActivityList.add(ACTIVITYTWO);
        assertEquals(expectedUniqueActivityList, uniqueActivityList);
    }

    @Test
    public void setActivities_listWithDuplicateActivities_throwsDuplicateActivityException() {
        List<Activity> listWithDuplicateActivities = Arrays.asList(ACTIVITYONE, ACTIVITYONE);
        assertThrows(DuplicateActivityException.class, ()
                -> uniqueActivityList.setActivities(listWithDuplicateActivities));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueActivityList.asUnmodifiableObservableList().remove(0));
    }
}
