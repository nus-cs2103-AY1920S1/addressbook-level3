package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.group.GroupBuilder;
import seedu.address.testutil.group.TypicalGroups;

/**
 * Test for the Group Model.
 */
public class GroupTest {

    private static final Group EMPTY_GROUP = new Group("empty");
    private static final Group GROUP_ONE = TypicalGroups.GROUP_ONE;
    private static final Group GROUP_TWO = TypicalGroups.GROUP_TWO;

    @Test
    public void sameName_consideredEquals() {
        assertTrue(GROUP_ONE.equals(GROUP_ONE));
        assertTrue(GROUP_TWO.equals(GROUP_TWO));
        assertTrue(EMPTY_GROUP.equals(EMPTY_GROUP));
    }

    @Test
    public void sameName_differentStudents_consideredEquals() {
        Group editedGroupOne = new GroupBuilder(GROUP_ONE).withGroupId("empty").build();
        assertTrue(editedGroupOne.equals(EMPTY_GROUP));
        Group editedGroupTwo = new GroupBuilder(GROUP_TWO).withGroupId("empty").build();
        assertTrue(editedGroupTwo.equals(EMPTY_GROUP));
    }

    @Test
    public void differentName_consideredUnique() {
        assertFalse(GROUP_ONE.equals(GROUP_TWO));
        assertFalse(GROUP_TWO.equals(EMPTY_GROUP));
        assertFalse(GROUP_ONE.equals(EMPTY_GROUP));
    }

    @Test
    public void differentName_sameStudents_consideredUnique() {
        Group editedGroup = new GroupBuilder(GROUP_TWO).withGroupId("EditedGroup").build();
        assertFalse(editedGroup.equals(GROUP_TWO));
    }
}
