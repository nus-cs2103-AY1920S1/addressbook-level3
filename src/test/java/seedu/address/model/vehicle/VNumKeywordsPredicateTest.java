package seedu.address.model.vehicle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalVehicles.V1;
import static seedu.address.testutil.TypicalVehicles.V2;
import static seedu.address.testutil.TypicalVehicles.V3;
import static seedu.address.testutil.TypicalVehicles.V4;

import org.junit.jupiter.api.Test;

public class VNumKeywordsPredicateTest {

    private VNumKeywordsPredicate p1 = new VNumKeywordsPredicate("A");
    private VNumKeywordsPredicate p2 = new VNumKeywordsPredicate("2");
    private VNumKeywordsPredicate p3 = new VNumKeywordsPredicate("2");
    private VNumKeywordsPredicate p4 = new VNumKeywordsPredicate("FKH1221P");

    @Test
    public void equals() {
        // same object -> returns true
        assertEquals(p1, p1);

        // same keyword -> returns true
        assertEquals(p2, p3);

        // diff keywords -> returns false
        assertFalse(p1.equals(p3));
    }

    @Test
    public void test_vNumContainsChar_returnsTrue() {
        assertTrue(p1.test(V2));
    }

    @Test
    public void test_vNumContainsNum_returnsTrue() {
        assertTrue(p3.test(V1));
    }

    @Test
    public void test_vNumContainsVNum_returnsTrue() {
        assertTrue(p4.test(V3));
    }

    @Test
    public void test_vNumDoesNotContainChar_returnsFalse() {
        assertFalse(p1.test(V1));
    }

    @Test
    public void test_vNumDoesNotContainNum_returnsFalse() {
        assertFalse(p2.test(V4));
    }

    @Test
    public void test_vNumDoesNotContainVNum_returnsFalse() {
        assertFalse(p4.test(V1));
    }
}
