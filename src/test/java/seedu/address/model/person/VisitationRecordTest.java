package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class VisitationRecordTest {

    @Test
    public void equals() {
        VisitationRecord visitationRecord = new VisitationRecord("Hello");

        // same object -> returns true
        assertTrue(visitationRecord.equals(visitationRecord));

        // same values -> returns true
        VisitationRecord visitationRecordCopy = new VisitationRecord(visitationRecord.value);
        assertTrue(visitationRecord.equals(visitationRecordCopy));

        // different types -> returns false
        assertFalse(visitationRecord.equals(1));

        // null -> returns false
        assertFalse(visitationRecord.equals(null));

        // different visitationRecord -> returns false
        VisitationRecord differentVisitationRecord = new VisitationRecord("Bye");
        assertFalse(visitationRecord.equals(differentVisitationRecord));
    }
}
