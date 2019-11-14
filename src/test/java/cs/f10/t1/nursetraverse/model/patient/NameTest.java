package cs.f10.t1.nursetraverse.model.patient;

import static cs.f10.t1.nursetraverse.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void isSameName() {
        // Different names
        assertFalse(new Name("Peter Jack").isSameName(new Name("Peter Jackson")));

        // Same names
        assertTrue(new Name("Peter Jack").isSameName(new Name("Peter Jack")));
        assertTrue(new Name("Peter Jack").isSameName(new Name("pETER jACK")));
        assertTrue(new Name("peter jack").isSameName(new Name("PETER JACK")));
        assertTrue(new Name("12345").isSameName(new Name("12345")));
        assertTrue(new Name("Peter the 2nd").isSameName(new Name("peter the 2nd")));
        assertTrue(new Name("David Roger Jackson Ray Jr 2nd").isSameName(new Name("david roger jackson ray jr 2nd")));
    }

    @Test
    public void equals() {
        // Non-equal names
        assertNotEquals(new Name("Peter Jack"), new Name("Peter Jackson"));
        assertNotEquals(new Name("Peter Jack"), new Name("pETER jACK"));
        assertNotEquals(new Name("peter jack"), new Name("PETER JACK"));
        assertNotEquals(new Name("Peter the 2nd"), new Name("peter the 2nd"));
        assertNotEquals(new Name("David Roger Jackson Ray Jr 2nd"), new Name("david roger jackson ray jr 2nd"));

        // Equal names
        assertEquals(new Name("Peter Jack"), new Name("Peter Jack"));
        assertEquals(new Name("12345"), new Name("12345"));
        assertEquals(new Name("Peter the 2nd"), new Name("Peter the 2nd"));
        assertEquals(new Name("David Roger Jackson Ray Jr 2nd"), new Name("David Roger Jackson Ray Jr 2nd"));
    }
}
