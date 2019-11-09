package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.testutil.group.TypicalGroups;

/**
 * Test for the ListOfGroups Model.
 */
public class ListOfGroupTest {
    private final ListOfGroups listOfGroups = new ListOfGroups();

    @Test
    public void contains_groupNotInList_returnsFalse() {
        assertFalse(listOfGroups.contains(TypicalGroups.GROUP_ONE));
    }

    @Test
    public void removeGroup_targetGroupNotInList_throwsGroupNotFoundException() {
        assertThrows(GroupNotFoundException.class, () -> listOfGroups
                .removeGroup(TypicalGroups.GROUP_ONE.getGroupId()));
    }

    @Test
    public void getGroupIndex_targetGroupNotInList_throwsGroupNotFoundException() {
        assertThrows(GroupNotFoundException.class, () -> listOfGroups
                .getGroupIndex(TypicalGroups.GROUP_ONE.getGroupId()));
    }

    @Test
    public void remove_nullGroup_throwsGroupNotFoundException() {
        assertThrows(GroupNotFoundException.class, () -> listOfGroups.removeGroup(null));
    }

    @Test
    public void remove_existingGroup_removesGroup() {
        listOfGroups.addGroup(TypicalGroups.GROUP_ONE);
        listOfGroups.removeGroup(TypicalGroups.GROUP_ONE.getGroupId());
        ListOfGroups expectedGroupList = new ListOfGroups();
        assertEquals(expectedGroupList, listOfGroups);
    }

    @Test
    public void setGroups_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> listOfGroups.setGroups((List<Group>) null));
    }

    @Test
    public void addDuplicateGroup_throwsDuplicateGroupException() {
        listOfGroups.addGroup(TypicalGroups.GROUP_ONE);
        assertThrows(DuplicateGroupException.class, () -> listOfGroups.addGroup(TypicalGroups.GROUP_ONE));
    }

    @Test
    public void containsGroup_groupInList_returnsTrue() {
        listOfGroups.addGroup(TypicalGroups.GROUP_ONE);
        assertTrue(listOfGroups.contains(TypicalGroups.GROUP_ONE));
    }

    @Test
    public void exportGroup_groupNotInList_throwsGroupNotFoundException() {
        assertThrows(GroupNotFoundException.class, () -> listOfGroups.exportGroup(TypicalGroups
                .GROUP_ONE.getGroupId()));
    }

    @Test
    public void twoGroupsWithSameGroupList_equals() {
        listOfGroups.addGroup(TypicalGroups.GROUP_ONE);
        ListOfGroups newGroupList = new ListOfGroups();
        newGroupList.addGroup(TypicalGroups.GROUP_ONE);
        assertEquals(listOfGroups.getGroupList(), newGroupList.getGroupList());
    }

    @Test
    public void setGroups_list_replacesOwnListWithProvidedList() {
        listOfGroups.addGroup(TypicalGroups.GROUP_ONE);
        List<Group> groupList = Collections.singletonList(TypicalGroups.GROUP_TWO);
        listOfGroups.setGroups(groupList);
        ListOfGroups expectedGroupList = new ListOfGroups();
        expectedGroupList.addGroup(TypicalGroups.GROUP_TWO);
        assertEquals(expectedGroupList, listOfGroups);
    }

    @Test
    public void setGroups_listWithDuplicateGroups_throwsDuplicateGroupException() {
        List<Group> listWithDuplicateGroups = Arrays
                .asList(TypicalGroups.GROUP_ONE, TypicalGroups.GROUP_ONE);
        assertThrows(DuplicateGroupException.class, () -> listOfGroups.setGroups(listWithDuplicateGroups));
    }
}
