package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPREMARK1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPREMARK2;

import org.junit.jupiter.api.Test;

class GroupRemarkTest {

    @Test
    void testToString() {
        GroupRemark groupRemark = GROUPREMARK1;

        assertEquals(groupRemark.toString(), GROUPREMARK1.toString());
        assertNotEquals(groupRemark.toString(), GROUPREMARK2.toString());
    }

    @Test
    void testEquals() {
        assertTrue(GROUPREMARK1.equals(GROUPREMARK1));

        assertFalse(GROUPREMARK1.equals(null));
        assertFalse(GROUPREMARK1.equals(GROUPREMARK2));
    }
}
