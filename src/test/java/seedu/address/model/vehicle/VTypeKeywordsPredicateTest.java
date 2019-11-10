package seedu.address.model.vehicle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalVehicles.V1;

public class VTypeKeywordsPredicateTest {

    VTypeKeywordsPredicate p1 = new VTypeKeywordsPredicate(new VehicleType("Ambulance"));
    VTypeKeywordsPredicate p2 = new VTypeKeywordsPredicate(new VehicleType("Ambulance"));
    VTypeKeywordsPredicate p3 = new VTypeKeywordsPredicate(new VehicleType("Patrol Car"));

    @Test
    public void equals() {
        // same object -> returns true
        assertEquals(p1, p1);

        // same keyword -> returns true
        assertEquals(p1, p2);

        // diff keywords -> returns false
        assertFalse(p1.equals(p3));
    }

    @Test
    public void test_sameVType_returnsTrue() {
        assertTrue(p1.test(V1));
    }

    @Test
    public void test_diffVType_returnsFalse() {
        assertFalse(p3.test(V1));
    }
}
