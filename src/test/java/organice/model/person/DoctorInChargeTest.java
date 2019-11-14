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
        assertTrue(DoctorInCharge.isValidDoctorInCharge("f1289064t")); // should be case insensitive
        assertTrue(DoctorInCharge.isValidDoctorInCharge("F1289064T")); // should be case insensitive
        assertTrue(DoctorInCharge.isValidDoctorInCharge("F1289064t")); // should be case insensitive
        assertTrue(DoctorInCharge.isValidDoctorInCharge("S4681130E")); // starts with 'S'
        assertTrue(DoctorInCharge.isValidDoctorInCharge("T3685500I")); // starts with 'T'
        assertTrue(DoctorInCharge.isValidDoctorInCharge("F9418981X")); // starts with 'F'
        assertTrue(DoctorInCharge.isValidDoctorInCharge("G1146086R")); // starts with 'G'
    }

    @Test
    public void toStringTest() {
        assertEquals(new DoctorInCharge("S6906071D").toString(), "S6906071D");
    }

    @Test
    public void equals() {
        DoctorInCharge doctorInCharge = new DoctorInCharge("S6906071D");

        assertFalse(doctorInCharge.equals(null));
        assertFalse(doctorInCharge.equals(new DoctorInCharge("S0038935H")));
        assertTrue(doctorInCharge.equals(doctorInCharge));
        assertTrue(doctorInCharge.equals(new DoctorInCharge("S6906071D")));
    }

    @Test
    public void hashCodeTest() {
        DoctorInCharge doctorInCharge = new DoctorInCharge("S6906071D");

        assertEquals(doctorInCharge.hashCode(), new DoctorInCharge("S6906071D").hashCode());
        assertNotEquals(doctorInCharge.hashCode(), new DoctorInCharge("S0038935H").hashCode());
    }
}
