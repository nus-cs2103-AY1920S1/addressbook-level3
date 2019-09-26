package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class GroupNameTest {

    @Test
    void testToString() {
        GroupName groupName = new GroupName("name");

        assertEquals("name", groupName.toString());
        assertNotEquals("other", groupName.toString());
    }
}
