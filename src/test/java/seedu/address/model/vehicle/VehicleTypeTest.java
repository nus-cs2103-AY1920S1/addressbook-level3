package seedu.address.model.vehicle ;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class VehicleTypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new VehicleType(null));
    }

    @Test
    public void constructor_invalidVehicleType_throwsIllegalArgumentException() {
        String invalidVehicleType = "";
        assertThrows(IllegalArgumentException.class, () -> new VehicleType(invalidVehicleType));
    }

    @Test
    public void isValidVehicleType() {
        // null phone number
        assertThrows(NullPointerException.class, () -> VehicleType.isValidVehicleType(null));

        // invalid phone numbers
        assertFalse(VehicleType.isValidVehicleType("")); // empty string
        assertFalse(VehicleType.isValidVehicleType(" ")); // spaces only
        assertFalse(VehicleType.isValidVehicleType("1234567")); // less than 8 numbers
        assertFalse(VehicleType.isValidVehicleType("123456789")); // more than 8 numbers
        assertFalse(VehicleType.isValidVehicleType("phone")); // non-numeric
        assertFalse(VehicleType.isValidVehicleType("9011p041")); // alphabets within digits
        assertFalse(VehicleType.isValidVehicleType("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(VehicleType.isValidVehicleType("12345678")); // exactly 8 numbers
    }
}
