package organice.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TissueTypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TissueType(null));
    }

    @Test
    public void constructor_invalidTissuetype_throwsIllegalArgumentException() {
        String invalidTissueType = "";
        assertThrows(IllegalArgumentException.class, () -> new TissueType(invalidTissueType));
    }

    @Test
    public void isValidTissuetype() {
        // null tissue type
        assertThrows(NullPointerException.class, () -> TissueType.isValidTissueType(null));

        // invalid tissue type
        assertFalse(TissueType.isValidTissueType("")); // empty string
        assertFalse(TissueType.isValidTissueType(" ")); // spaces only
        assertFalse(TissueType.isValidTissueType("a,b,c,d,e,f")); // tissue types must be Integers from 1..12.
        assertFalse(TissueType.isValidTissueType("1")); // single numeric
        assertFalse(TissueType.isValidTissueType("1,,2,,3,,4,,5,,6")); // wrong usage of commas
        assertFalse(TissueType.isValidTissueType("1 2 3 4 5 6")); // spaces within tissue type
        assertFalse(TissueType.isValidTissueType("1, 2, 3, 4, 5, 6")); // spaces within tissue type with commas
        assertFalse(TissueType.isValidTissueType("13,2,3,4,5,6")); // tissue types must be Integers from 1..12.
        assertFalse(TissueType.isValidTissueType("1,1,1,1,1,1")); // duplicates

        // valid tissue type
        assertTrue(TissueType.isValidTissueType("1,2,3,4,5,6")); // exactly 6 tissue type
        assertTrue(TissueType.isValidTissueType("12,7,8,9,10,11")); //2 digits tissue type
    }

    @Test
    public void toStringTest() {
        assertEquals(new TissueType("1,2,3,4,5,6").toString(), "1,2,3,4,5,6");
        assertEquals(new TissueType("10,11,12,3,4,5").toString(), "10,11,12,3,4,5");
    }

    @Test
    public void equals() {
        TissueType tissueType = new TissueType("1,2,3,4,5,6");

        assertFalse(tissueType.equals(null));
        assertFalse(tissueType.equals(new TissueType("1,2,3,4,5,7")));
        assertTrue(tissueType.equals(tissueType));
        assertTrue(tissueType.equals(new TissueType("1,2,3,4,5,6")));
    }

    @Test
    public void hashCodeTest() {
        TissueType tissueType = new TissueType("2,4,6,8,10,12");

        assertEquals(tissueType.hashCode(), new TissueType("2,4,6,8,10,12").hashCode());
        assertNotEquals(tissueType.hashCode(), new TissueType("1,3,5,7,9,11").hashCode());
    }
}
