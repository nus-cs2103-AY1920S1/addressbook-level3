package seedu.address.model.group;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupNameTest {

    @Test
    void testToString() {
        GroupName groupName = new GroupName("name");

        assertEquals("name", groupName.toString());
        assertNotEquals("other", groupName.toString());
    }
}