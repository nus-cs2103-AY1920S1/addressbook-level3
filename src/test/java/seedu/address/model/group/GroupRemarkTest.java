package seedu.address.model.group;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupRemarkTest {

    @Test
    void testToString() {
        GroupRemark groupRemark = new GroupRemark("remark");

        assertEquals("remark", groupRemark.toString());
        assertNotEquals("remarkk", groupRemark.toString());
    }
}