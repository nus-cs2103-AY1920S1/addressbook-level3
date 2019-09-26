package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class GroupRemarkTest {

    @Test
    void testToString() {
        GroupRemark groupRemark = new GroupRemark("remark");

        assertEquals("remark", groupRemark.toString());
        assertNotEquals("remarkk", groupRemark.toString());
    }
}
