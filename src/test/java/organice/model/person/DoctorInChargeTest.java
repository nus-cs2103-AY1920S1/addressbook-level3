package organice.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DoctorInChargeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DoctorInCharge(null));
    }

    @Test
    public void constructor_invalidDoctorInCharge_throwsIllegalArgumentException() {
        String invalidDoctorInCharge = "";
        assertThrows(IllegalArgumentException.class, () -> new DoctorInCharge(invalidDoctorInCharge));
    }

    @Test
    public void isValidDoctorInCharge() {
        // null doctor in charge
        assertThrows(NullPointerException.class, () -> DoctorInCharge.isValidDoctorInCharge(null));

        // invalid doctor in charge
        assertFalse(DoctorInCharge.isValidDoctorInCharge("")); // empty string
        assertFalse(DoctorInCharge.isValidDoctorInCharge(" ")); // spaces only
        assertFalse(DoctorInCharge.isValidDoctorInCharge("1234567")); // contains only number
        assertFalse(DoctorInCharge.isValidDoctorInCharge("9312930R")); // begins with a number
        assertFalse(DoctorInCharge.isValidDoctorInCharge("S123456A")); // contains less than 7 numbers
        assertFalse(DoctorInCharge.isValidDoctorInCharge("S1234567")); // ends with a number
        assertFalse(DoctorInCharge.isValidDoctorInCharge("AAAAAAAAA")); // contains only letters
        assertFalse(DoctorInCharge.isValidDoctorInCharge("N1234567A")); // starts with letter other than S/T/F/G

        // valid doctor in charge
        assertTrue(DoctorInCharge.isValidDoctorInCharge("s1234512b")); // should be case insensitive
        assertTrue(DoctorInCharge.isValidDoctorInCharge("s1234512R")); // should be case insensitive
        assertTrue(DoctorInCharge.isValidDoctorInCharge("T1234512a")); // should be case insensitive
        assertTrue(DoctorInCharge.isValidDoctorInCharge("S1234567B")); // starts with 'S'
        assertTrue(DoctorInCharge.isValidDoctorInCharge("T2222222A")); // starts with 'T'
        assertTrue(DoctorInCharge.isValidDoctorInCharge("F3333333A")); // starts with 'F'
        assertTrue(DoctorInCharge.isValidDoctorInCharge("G4444444A")); // starts with 'G'
    }

    @Test
    public void toStringTest() {
        assertEquals(new DoctorInCharge("S1111111A").toString(), "S1111111A");
    }

    @Test
    public void equals() {
        DoctorInCharge doctorInCharge = new DoctorInCharge("S1111111A");

        assertFalse(doctorInCharge.equals(null));
        assertFalse(doctorInCharge.equals(new DoctorInCharge("S1111111B")));
        assertTrue(doctorInCharge.equals(doctorInCharge));
        assertTrue(doctorInCharge.equals(new DoctorInCharge("S1111111A")));
    }

    @Test
    public void hashCodeTest() {
        DoctorInCharge doctorInCharge = new DoctorInCharge("S1111111A");

        assertEquals(doctorInCharge.hashCode(), new DoctorInCharge("S1111111A").hashCode());
        assertNotEquals(doctorInCharge.hashCode(), new DoctorInCharge("S1111111B").hashCode());
    }
}
