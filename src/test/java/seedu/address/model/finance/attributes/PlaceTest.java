package seedu.address.model.finance.attributes;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PlaceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Place(null));
    }

    @Test
    public void constructor_invalidPlace_throwsIllegalArgumentException() {
        String invalidPlace = "";
        assertThrows(IllegalArgumentException.class, () -> new Place(invalidPlace));
    }

    @Test
    public void isValidPlace() {
        // null place
        assertThrows(NullPointerException.class, () -> Place.isValidPlace(null));

        // invalid places
        assertFalse(Place.isValidPlace("")); // empty string
        assertFalse(Place.isValidPlace(" ")); // spaces only

        // valid places
        assertTrue(Place.isValidPlace("Blk 456, Den Road, #01-355"));
        assertTrue(Place.isValidPlace("-")); // one character
        assertTrue(Place.isValidPlace("<FrOnt1Er")); // alphanumeric, with special chars
        assertTrue(Place.isValidPlace(
                "Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
