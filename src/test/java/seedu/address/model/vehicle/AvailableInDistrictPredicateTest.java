package seedu.address.model.vehicle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.TypicalVehicles.V6;

import org.junit.jupiter.api.Test;

public class AvailableInDistrictPredicateTest {
    private AvailableInDistrictPredicate p1 = new AvailableInDistrictPredicate(new District(1));
    private AvailableInDistrictPredicate p2 = new AvailableInDistrictPredicate(new District(1));
    private AvailableInDistrictPredicate p3 = new AvailableInDistrictPredicate(new District(10));

    @Test
    public void equals() {

        // same object -> returns true
        assertEquals(p1, p1);

        // same keyword -> returns true
        assertEquals(p1, p2);

        // diff keywords -> return false
        assertFalse(p1.equals(p3));
    }

    @Test
    public void test_vehicleNotAvailableInDistrict_returnsFalse() {
        assertFalse(p1.test(V6));
    }
}
