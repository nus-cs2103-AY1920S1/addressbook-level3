package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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
