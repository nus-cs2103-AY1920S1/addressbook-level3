package seedu.address.model.vehicle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.TypicalVehicles.V1;
import static seedu.address.testutil.TypicalVehicles.V2;
import static seedu.address.testutil.TypicalVehicles.V3;
import static seedu.address.testutil.TypicalVehicles.V4;
import static seedu.address.testutil.TypicalVehicles.V5;
import static seedu.address.testutil.TypicalVehicles.V6;

import org.junit.jupiter.api.Test;

public class VehicleTest {

    @Test
    public void equals() {
        // same object -> return true
        assertEquals(V1, V1);

        // same number -> return true
        assertEquals(V6, V2);

        // same district -> return false
        assertFalse(V1.equals(V6));

        // same type -> return false
        assertFalse(V4.equals(V5));

        // same availability -> return false
        assertFalse(V1.equals(V3));
    }

    @Test
    public void getVehicleType_success() {
        assertEquals(V1.getVehicleType().toString(), "Ambulance");
    }

    @Test
    public void getVehicleNumber_success() {
        assertEquals(V2.getVehicleNumber().toString(), "BBA2222F");
    }

    @Test
    public void getDistrict_success() {
        assertEquals(V3.getDistrict().getDistrictNum(), 20);
    }

    @Test
    public void getAvailability_success() {
        assertEquals(V4.getAvailability().getAvailabilityTag(), "BUSY");
    }

    @Test
    public void setAvailability_success() {
        assertEquals(V5.getAvailability().getAvailabilityTag(), "AVAILABLE");
        V5.setAvailability(new Availability("BUSY"));
        assertEquals(V5.getAvailability().getAvailabilityTag(), "BUSY");
    }

    @Test
    public void isAvailabile_false() {
        assertFalse(V5.isAvailable());
    }
}
