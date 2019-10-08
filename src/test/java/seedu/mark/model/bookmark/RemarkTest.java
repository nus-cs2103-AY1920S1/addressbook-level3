package seedu.mark.model.bookmark;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void constructor_invalidRemark_throwsIllegalArgumentException() {
        String invalidRemark = "";
        assertThrows(IllegalArgumentException.class, () -> new Remark(invalidRemark));
    }

    @Test
    public void isValidRemark() {
        // null remark
        assertThrows(NullPointerException.class, () -> Remark.isValidRemark(null));

        // invalid remarks
        assertFalse(Remark.isValidRemark("")); // empty string
        assertFalse(Remark.isValidRemark(" ")); // spaces only
        assertFalse(Remark.isValidRemark(" r/r")); // contains forward slash

        // valid remarks
        assertTrue(Remark.isValidRemark("Blk 456, Den Road, #01-355"));
        assertTrue(Remark.isValidRemark("-")); // one character
        assertTrue(Remark.isValidRemark("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long remark
    }

    @Test
    public void isEmptyRemark() {
        // null remark
        assertThrows(NullPointerException.class, () -> Remark.isEmptyRemark(null));

        // empty remarks
        assertTrue(Remark.isEmptyRemark("")); // empty string
        assertTrue(Remark.isEmptyRemark(" ")); // spaces only

        // non-empty remark
        assertFalse(Remark.isEmptyRemark("abc"));
    }
}
