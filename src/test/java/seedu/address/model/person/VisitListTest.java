package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TypicalVisits;

public class VisitListTest {

    @Test
    public void equals() {
        VisitList visitList = TypicalVisits.getLongTypicalVisitList("peter");

        // same object -> returns true
        assertTrue(visitList.equals(visitList));

        // same values -> returns true
        VisitList visitListCopy = new VisitList(visitList.getRecords());
        assertTrue(visitList.equals(visitListCopy));

        // different types -> returns false
        assertFalse(visitList.equals(1));

        // null -> returns false
        assertFalse(visitList.equals(null));

        // different visitList -> returns false
        VisitList differentVisitList = TypicalVisits.getShortTypicalVisitList("peter");
        assertFalse(visitList.equals(differentVisitList));
    }
}
