package seedu.address.model.vehicle;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DistrictTest {


    @Test
    public void constructor_invalidDistrict_throwsIllegalArgumentException() {
        int invalidDistrict = 0;
        assertThrows(IllegalArgumentException.class, () -> new District(invalidDistrict));
    }

    @Test
    public void isValidDistrict() {

        // invalid district
        assertFalse(District.isValidDistrict(-1)); // negative number
        assertFalse(District.isValidDistrict(29)); // out of range

        // valid name
        assertTrue(District.isValidDistrict(1)); // lower bound
        assertTrue(District.isValidDistrict(12)); // middle
        assertTrue(District.isValidDistrict(28)); // upper bound
    }
}
