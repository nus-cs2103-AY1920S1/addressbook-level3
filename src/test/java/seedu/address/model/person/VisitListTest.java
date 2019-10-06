package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class VisitListTest {

    @Test
    public void equals() {
        VisitList visitList = new VisitList("Hello");

        // same object -> returns true
        assertTrue(visitList.equals(visitList));

        // same values -> returns true
        VisitList visitListCopy = new VisitList(visitList.value);
        assertTrue(visitList.equals(visitListCopy));

        // different types -> returns false
        assertFalse(visitList.equals(1));

        // null -> returns false
        assertFalse(visitList.equals(null));

        // different visitList -> returns false
        VisitList differentVisitList = new VisitList("Bye");
        assertFalse(visitList.equals(differentVisitList));
    }
}
