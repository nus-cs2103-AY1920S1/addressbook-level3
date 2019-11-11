package cs.f10.t1.nursetraverse.model.patient;

import static cs.f10.t1.nursetraverse.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 3 numbers
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("9312 1534")); // spaces within digits
        assertFalse(Phone.isValidPhone("124293842033123")); // long phone numbers

        // valid phone numbers
        assertTrue(Phone.isValidPhone("911")); // exactly 3 numbers
        assertTrue(Phone.isValidPhone("420420")); // in between 3 and 8 numbers
        assertTrue(Phone.isValidPhone("93121534")); //exactly 8 numbers
    }

    @Test
    public void equals() {
        // Unequal phone numbers
        assertNotEquals(new Phone("911"), new Phone("901"));
        assertNotEquals(new Phone("420420"), new Phone("429420"));
        assertNotEquals(new Phone("9312534"), new Phone("93121535"));

        // Equal phone numbers
        assertEquals(new Phone("911"), new Phone("911"));
        assertEquals(new Phone("420420"), new Phone("420420"));
        assertEquals(new Phone("93121534"), new Phone("93121534"));
    }
}
