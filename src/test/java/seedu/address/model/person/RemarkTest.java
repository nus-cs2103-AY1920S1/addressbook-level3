package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

class RemarkTest {

    private Remark remark = new Remark("Remark");

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

    @Test
    void testEquals_null() {
        assertFalse(remark.equals(ALICE.getRemark()));
    }
}
