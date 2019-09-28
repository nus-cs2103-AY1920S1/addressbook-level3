package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME2;

import org.junit.jupiter.api.Test;

class GroupNameTest {

    @Test
    void testToString() {
        GroupName groupName = new GroupName("name");

        assertEquals("name", groupName.toString());
        assertNotEquals("other", groupName.toString());
    }

    @Test
    void testEquals() {
        assertTrue(GROUPNAME1.equals(GROUPNAME1));
        assertFalse(GROUPNAME1.equals(null));
        assertFalse(GROUPNAME2.equals(GROUPNAME1));
    }
}
