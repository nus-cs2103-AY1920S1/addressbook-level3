package seedu.address.model.vehicle;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class VehicleNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new VehicleNumber(null));
    }

    @Test
    public void constructor_invalidVehicleNumber_throwsIllegalArgumentException() {
        String invalidVehicleNumber = "";
        assertThrows(IllegalArgumentException.class, () -> new VehicleNumber(invalidVehicleNumber));
    }

    @Test
    public void isValidVehicleNumber() {
        // null vehicle number
        assertThrows(NullPointerException.class, () -> VehicleNumber.isValidVehicleNumber(null));

        // blank vehicle number
        assertFalse(VehicleNumber.isValidVehicleNumber("")); // empty string
        assertFalse(VehicleNumber.isValidVehicleNumber(" ")); // spaces only

        // missing parts
        assertFalse(VehicleNumber.isValidVehicleNumber("ABC")); // only has the first part
        assertFalse(VehicleNumber.isValidVehicleNumber("1234")); // no letters only numbers
        assertFalse(VehicleNumber.isValidVehicleNumber("1234D")); // missing first part

        // invalid parts
        assertFalse(VehicleNumber.isValidVehicleNumber("ABCD1234E")); // more than 3 starting letters
        assertFalse(VehicleNumber.isValidVehicleNumber("AB1234CD")); // wrong alloc of letters
        assertFalse(VehicleNumber.isValidVehicleNumber("ABC123D")); // wrong number of num
        assertFalse(VehicleNumber.isValidVehicleNumber("abc123D")); // lowercase

        // valid email
        assertTrue(VehicleNumber.isValidVehicleNumber("ABC1234D")); // all caps
    }
}
