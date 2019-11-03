package seedu.address.model.group;

import org.junit.jupiter.api.Test;
import seedu.address.testutil.group.GroupBuilder;
import seedu.address.testutil.group.TypicalGroups;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroupTest {
    Group emptyGroup = new Group("empty");
    Group groupOne = TypicalGroups.groupOne;
    Group groupTwo = TypicalGroups.groupTwo;

    @Test
    public void sameName_consideredEquals() {
        assertTrue(groupOne.equals(groupOne));
        assertTrue(groupTwo.equals(groupTwo));
        assertTrue(emptyGroup.equals(emptyGroup));
    }

    @Test
    public void sameName_differentStudents_consideredEquals(){
        Group editedGroupOne = new GroupBuilder(groupOne).withGroupId("empty").build();
        assertTrue(editedGroupOne.equals(emptyGroup));
        Group editedGroupTwo = new GroupBuilder(groupTwo).withGroupId("empty").build();
        assertTrue(editedGroupTwo.equals(emptyGroup));
    }

    @Test
    public void differentName_consideredUnique() {
        assertFalse(groupOne.equals(groupTwo));
        assertFalse(groupTwo.equals(emptyGroup));
        assertFalse(groupOne.equals(emptyGroup));
    }

    @Test
    public void differentName_SameStudents_consideredUnique(){
        Group editedNameGroupTwo = new GroupBuilder(groupTwo).withGroupId("EditedGroup").build();
        assertFalse(editedNameGroupTwo.equals(groupTwo));
    }
}
