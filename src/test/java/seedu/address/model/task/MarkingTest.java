package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MARKING_N;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MARKING_Y;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MarkingTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Marking(null));
    }

    @Test
    public void constructor_invalidMark_throwsIllegalArgumentException() {
        String invalidMark = "";
        assertThrows(IllegalArgumentException.class, () -> new Marking(invalidMark));
    }

    @Test
    public void isValidMark() {
        // null marking
        assertThrows(NullPointerException.class, () -> Marking.isValidMark(null));

        //valid marking
        assertTrue(Marking.isValidMark(VALID_MARKING_N));
        assertTrue(Marking.isValidMark(VALID_MARKING_Y));

        //invalid marking
        assertFalse(Marking.isValidMark("123"));
        assertFalse(Marking.isValidMark("y"));
        assertFalse(Marking.isValidMark("marked"));
        assertFalse(Marking.isValidMark("$%#%%"));
    }


}
