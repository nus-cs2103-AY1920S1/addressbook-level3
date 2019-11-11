package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP2;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GroupTest {

    private Group group1;
    private Group group2;

    @BeforeEach
    void init() {
        group1 = new Group(GROUP1);
        group2 = new Group(GROUP2);
    }

    @Test
    void getGroupName() {
        assertTrue(GROUP_NAME1.equals(group1.getGroupName()));

        assertFalse(group1.getGroupName().equals(null));
        assertFalse(group1.getGroupName().equals(GROUP_NAME2));
    }

    @Test
    void getGroupId() {
        assertNotNull(group1.getGroupId());
        assertNotNull(group2.getGroupId());
        assertFalse(group1.getGroupId().equals(group2.getGroupId()));
    }

    @Test
    void testEquals() {
        assertTrue(group1.equals(group1));
        assertFalse(group1.equals(null));
        assertFalse(group1.equals(group2));
    }

    @Test
    void isSameGroup() {
        Group other = new Group(GROUP1);
        assertTrue(group1.isSameGroup(other));
        assertTrue(group1.isSameGroup(group1));
        assertFalse(group1.isSameGroup(null));
        assertFalse(group1.isSameGroup(group2));
    }

}
