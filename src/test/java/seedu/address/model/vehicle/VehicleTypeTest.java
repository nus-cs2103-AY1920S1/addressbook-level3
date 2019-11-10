package seedu.address.model.vehicle;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

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
        // null vtype
        assertThrows(NullPointerException.class, () -> VehicleType.isValidVehicleType(null));

        // invalid vtype
        assertFalse(VehicleType.isValidVehicleType("")); // empty string
        assertFalse(VehicleType.isValidVehicleType(" ")); // spaces only
        assertFalse(VehicleType.isValidVehicleType("amulance")); // non-existent vtype

        // valid vtypes
        assertTrue(VehicleType.isValidVehicleType("Ambulance"));
        assertTrue(VehicleType.isValidVehicleType("Patrol Car"));
    }
}
