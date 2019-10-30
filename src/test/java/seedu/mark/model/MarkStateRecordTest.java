package seedu.mark.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalBookmarks.AMY;
import static seedu.mark.testutil.TypicalBookmarks.BOB;

import org.junit.jupiter.api.Test;

import seedu.mark.model.VersionedMark.MarkStateRecord;
import seedu.mark.testutil.MarkBuilder;

public class MarkStateRecordTest {

    private final ReadOnlyMark markWithAmy = new MarkBuilder().withBookmark(AMY).build();
    private final ReadOnlyMark markWithBob = new MarkBuilder().withBookmark(BOB).build();

    @Test
    public void constructor_nullState_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MarkStateRecord("", null));
    }

    @Test
    public void constructor_nullRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MarkStateRecord(null, new Mark()));
    }

    @Test
    public void equals() {
        MarkStateRecord firstMarkStateRecord = new MarkStateRecord("record", markWithAmy);
        MarkStateRecord secondMarkStateRecord = new MarkStateRecord("record", markWithBob);
        MarkStateRecord thirdMarkStateRecord = new MarkStateRecord("record1", markWithAmy);

        // same object -> returns true
        assertTrue(firstMarkStateRecord.equals(firstMarkStateRecord));

        // same values -> returns true
        MarkStateRecord firstMarkStateRecordCopy = new MarkStateRecord("record", markWithAmy);
        assertTrue(firstMarkStateRecord.equals(firstMarkStateRecordCopy));

        // different types -> returns false
        assertFalse(firstMarkStateRecord.equals(1));

        // null -> returns false
        assertFalse(firstMarkStateRecord.equals(null));

        // different state -> returns false
        assertFalse(firstMarkStateRecord.equals(secondMarkStateRecord));

        // different record -> returns false
        assertFalse(firstMarkStateRecord.equals(thirdMarkStateRecord));
    }
}
