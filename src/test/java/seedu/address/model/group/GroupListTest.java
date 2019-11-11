package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP2;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.group.exceptions.NoGroupFieldsEditedException;
import seedu.address.testutil.grouputil.TypicalGroups;

class GroupListTest {

    private GroupList groupList;

    @BeforeEach
    void init() {
        groupList = new GroupList();
    }

    @Test
    void addGroup() throws DuplicateGroupException {
        Group group1 = groupList.addGroup(GROUP1);

        assertEquals(group1.getGroupName().toString(), GROUP1.getGroupName().toString());

        assertNotEquals(group1.getGroupName().toString(), GROUP2.getGroupName().toString());
    }

    @Test
    void deleteGroup() throws GroupNotFoundException, DuplicateGroupException {
        groupList = TypicalGroups.generateTypicalGroupList();
        Group group = groupList.findGroup(GROUP_NAME1);
        assertNotNull(group);

        assertDoesNotThrow(() -> groupList.deleteGroup(group.getGroupId()));
        assertThrows(GroupNotFoundException.class, () -> groupList.deleteGroup(group.getGroupId()));
    }

    @Test
    void editGroup() throws DuplicateGroupException, GroupNotFoundException, NoGroupFieldsEditedException {
        Group group1 = groupList.addGroup(GROUP1);
        Group group2 = groupList.editGroup(group1.getGroupName(), GROUP2);

        assertEquals(group2.getGroupName(), GROUP2.getGroupName());
        assertEquals(group1.getGroupId(), group2.getGroupId());
    }

    @Test
    void findGroup() throws GroupNotFoundException, DuplicateGroupException {
        assertThrows(GroupNotFoundException.class, () -> groupList.findGroup(GROUP1.getGroupName()));
        groupList.addGroup(GROUP1);
        assertDoesNotThrow(() -> groupList.findGroup(GROUP1.getGroupName()));
    }

    @Test
    void testFindGroup() throws DuplicateGroupException, GroupNotFoundException {
        Group group = groupList.addGroup(GROUP1);
        assertNotNull(groupList.findGroup(group.getGroupId()));
    }
}
