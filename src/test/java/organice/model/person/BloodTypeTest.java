package organice.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BloodTypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BloodType(null));
    }

    @Test
    public void constructor_invalidBloodType_throwsIllegalArgumentException() {
        String invalidBloodType = "";
        assertThrows(IllegalArgumentException.class, () -> new BloodType(invalidBloodType));
    }

    @Test
    public void isValidBloodType() {
        // null blood type
        assertThrows(NullPointerException.class, () -> BloodType.isValidBloodType(null));

        // invalid blood type
        assertFalse(BloodType.isValidBloodType("")); // empty string
        assertFalse(BloodType.isValidBloodType(" ")); // spaces only
        assertFalse(BloodType.isValidBloodType("C")); // wrong bloodtype
        assertFalse(BloodType.isValidBloodType("1")); // numeric
        assertFalse(BloodType.isValidBloodType("C+C")); // + within alphabets
        assertFalse(BloodType.isValidBloodType("A B")); // spaces within alphabets

        // valid blood type
        assertTrue(BloodType.isValidBloodType("B")); // exactly 1 alphabet
        assertTrue(BloodType.isValidBloodType("AB")); //2 alphabets
        assertTrue(BloodType.isValidBloodType("Ab")); //case insensitive
        assertTrue(BloodType.isValidBloodType("aB+")); // with positive and case insensitive
        assertTrue(BloodType.isValidBloodType("O+")); // with positive and capital
        assertTrue(BloodType.isValidBloodType("o+")); // with positive and non capital
    }

    @Test
    public void toStringTest() {
        assertEquals(new BloodType("Ab+").toString(), "AB+");
        assertEquals(new BloodType("ab+").toString(), "AB+");
    }

    @Test
    public void equals() {
        BloodType bloodType = new BloodType("AB+");

        assertFalse(bloodType.equals(null));
        assertFalse(bloodType.equals(new BloodType("A+")));
        assertTrue(bloodType.equals(bloodType));
        assertTrue(bloodType.equals(new BloodType("AB+")));
    }

    @Test
    public void hashCodeTest() {
        BloodType bloodType = new BloodType("AB+");

        assertEquals(bloodType.hashCode(), new BloodType("Ab+").hashCode());
        assertNotEquals(bloodType.hashCode(), new BloodType("A+").hashCode());
    }
}
