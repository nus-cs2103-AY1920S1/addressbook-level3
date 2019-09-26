package seedu.address.model.group;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupIdTest {

    @Test
    void getIdentifier() {
        GroupId groupId = new GroupId(1);
        assertEquals(1, groupId.getIdentifier());
    }

    @Test
    void testEquals() {
        GroupId groupId = new GroupId(1);
        GroupId groupId2 = new GroupId(2);

        assertTrue(groupId.equals(groupId));
        assertFalse(groupId.equals(groupId2));
    }
}