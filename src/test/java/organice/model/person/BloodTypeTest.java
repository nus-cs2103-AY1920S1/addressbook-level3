package organice.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.model.person.BloodType.BLOODTYPE_AB_MINUS;
import static organice.model.person.BloodType.BLOODTYPE_AB_PLUS;
import static organice.model.person.BloodType.BLOODTYPE_A_MINUS;
import static organice.model.person.BloodType.BLOODTYPE_A_PLUS;
import static organice.model.person.BloodType.BLOODTYPE_B_MINUS;
import static organice.model.person.BloodType.BLOODTYPE_B_PLUS;
import static organice.model.person.BloodType.BLOODTYPE_O_MINUS;
import static organice.model.person.BloodType.BLOODTYPE_O_PLUS;
import static organice.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class BloodTypeTest {
    public static final ArrayList<BloodType> VALID_DONOR_BLOODTYPES_TYPE_APLUS =
            new ArrayList<>(Arrays.asList(BLOODTYPE_A_PLUS, BLOODTYPE_A_MINUS, BLOODTYPE_O_PLUS, BLOODTYPE_O_MINUS));

    public static final ArrayList<BloodType> VALID_DONOR_BLOODTYPES_TYPE_AMINUS =
            new ArrayList<>(Arrays.asList(BLOODTYPE_A_MINUS, BLOODTYPE_O_MINUS));

    public static final ArrayList<BloodType> VALID_DONOR_BLOODTYPES_TYPE_BPLUS = new ArrayList<>(Arrays.asList(
            BLOODTYPE_B_PLUS, BLOODTYPE_O_PLUS, BLOODTYPE_B_MINUS, BLOODTYPE_O_MINUS));

    public static final ArrayList<BloodType> VALID_DONOR_BLOODTYPES_TYPE_BMINUS = new ArrayList<>(Arrays.asList(
            BLOODTYPE_B_MINUS, BLOODTYPE_O_MINUS));

    public static final ArrayList<BloodType> VALID_DONOR_BLOODTYPES_TYPE_ABPLUS = new ArrayList<>(Arrays.asList(
            BLOODTYPE_A_PLUS, BLOODTYPE_A_MINUS, BLOODTYPE_B_PLUS, BLOODTYPE_B_MINUS, BLOODTYPE_O_PLUS,
            BLOODTYPE_O_MINUS, BLOODTYPE_AB_PLUS, BLOODTYPE_AB_MINUS));

    public static final ArrayList<BloodType> VALID_DONOR_BLOODTYPES_TYPE_ABMINUS = new ArrayList<>(Arrays.asList(
            BLOODTYPE_A_MINUS, BLOODTYPE_B_MINUS, BLOODTYPE_O_MINUS, BLOODTYPE_AB_MINUS));

    public static final ArrayList<BloodType> VALID_DONOR_BLOODTYPES_TYPE_OPLUS = new ArrayList<>(Arrays.asList(
            BLOODTYPE_O_PLUS, BLOODTYPE_O_MINUS));

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

        //invalid blood type -- without signs
        assertFalse(BloodType.isValidBloodType("A"));
        assertFalse(BloodType.isValidBloodType("B"));
        assertFalse(BloodType.isValidBloodType("O"));
        assertFalse(BloodType.isValidBloodType("AB"));

        // valid blood type -- positive
        assertTrue(BloodType.isValidBloodType("A+"));
        assertTrue(BloodType.isValidBloodType("B+"));
        assertTrue(BloodType.isValidBloodType("O+"));
        assertTrue(BloodType.isValidBloodType("AB+"));

        //valid blood type -- negative
        assertTrue(BloodType.isValidBloodType("A-"));
        assertTrue(BloodType.isValidBloodType("B-"));
        assertTrue(BloodType.isValidBloodType("O-"));
        assertTrue(BloodType.isValidBloodType("AB-"));

        assertTrue(BloodType.isValidBloodType("Ab+")); //case insensitive - AB blood type
        assertTrue(BloodType.isValidBloodType("o-")); //case insensitive - O blood type
        assertTrue(BloodType.isValidBloodType("b+")); //case insensitive - B blood type
        assertTrue(BloodType.isValidBloodType("a-")); //case insensitive - A blood type
    }

    @Test
    public void isCompatibleBloodType_bloodTypesMatch_success() {
        for (BloodType donorBloodType : VALID_DONOR_BLOODTYPES_TYPE_APLUS) {
            assertTrue(BLOODTYPE_A_PLUS.isCompatibleBloodType(donorBloodType));
        }

        for (BloodType donorBloodType : VALID_DONOR_BLOODTYPES_TYPE_AMINUS) {
            assertTrue(BLOODTYPE_A_MINUS.isCompatibleBloodType(donorBloodType));
        }

        for (BloodType donorBloodType : VALID_DONOR_BLOODTYPES_TYPE_BPLUS) {
            assertTrue(BLOODTYPE_B_PLUS.isCompatibleBloodType(donorBloodType));
        }

        for (BloodType donorBloodType : VALID_DONOR_BLOODTYPES_TYPE_BMINUS) {
            assertTrue(BLOODTYPE_B_MINUS.isCompatibleBloodType(donorBloodType));
        }

        for (BloodType donorBloodType : VALID_DONOR_BLOODTYPES_TYPE_ABPLUS) {
            assertTrue(BLOODTYPE_AB_PLUS.isCompatibleBloodType(donorBloodType));
        }

        for (BloodType donorBloodType : VALID_DONOR_BLOODTYPES_TYPE_ABMINUS) {
            assertTrue(BLOODTYPE_AB_MINUS.isCompatibleBloodType(donorBloodType));
        }

        for (BloodType donorBloodType : VALID_DONOR_BLOODTYPES_TYPE_OPLUS) {
            assertTrue(BLOODTYPE_O_PLUS.isCompatibleBloodType(donorBloodType));
        }

        //Patients of blood type O- can only receive O- blood.
        assertTrue(BLOODTYPE_O_MINUS.isCompatibleBloodType(BLOODTYPE_O_MINUS));
    }

    @Test
    public void toStringTest() {
        assertEquals(new BloodType("Ab+").toString(), "AB+");
        assertEquals(new BloodType("ab-").toString(), "AB-");
    }

    @Test
    public void equals() {
        BloodType bloodType = new BloodType("AB+");

        assertFalse(bloodType.equals(null));
        assertFalse(bloodType.equals(new BloodType("A-")));
        assertTrue(bloodType.equals(bloodType));
        assertTrue(bloodType.equals(new BloodType("AB+")));
    }

    @Test
    public void hashCodeTest() {
        BloodType bloodType = new BloodType("O-");

        assertEquals(bloodType.hashCode(), new BloodType("O-").hashCode());
        assertNotEquals(bloodType.hashCode(), new BloodType("A-").hashCode());
    }
}
