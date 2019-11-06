package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME2;

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
        assertTrue(GROUP_NAME1.equals(GROUP_NAME1));
        assertFalse(GROUP_NAME1.equals(null));
        assertFalse(GROUP_NAME2.equals(GROUP_NAME1));
    }
}
