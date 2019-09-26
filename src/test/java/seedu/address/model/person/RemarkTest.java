package seedu.address.model.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.TypicalPersons.ALICE;

class RemarkTest {

    Remark remark = new Remark("Remark");

    @Test
    void testToString() {
        assertEquals("Remark", remark.toString());
        assertNotEquals("Remark2", remark.toString());
    }

    @Test
    void testEquals() {
        assertTrue(remark.equals(remark));
        assertFalse(remark.equals(ALICE.getRemark()));
    }
}