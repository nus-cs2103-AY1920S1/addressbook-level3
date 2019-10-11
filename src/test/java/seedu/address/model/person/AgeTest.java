package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.person.Age.AGE_MAX;
import static seedu.address.model.person.Age.AGE_MIN;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AgeTest {
    static final Integer AGE_MAX_ONE_MORE = AGE_MAX + 1;
    static final Integer AGE_MAX_ONE_LESS = AGE_MAX - 1;
    static final Integer AGE_MAX_TEN_MORE = AGE_MAX + 10;
    static final Integer AGE_MIN_ONE_LESS = AGE_MIN - 1;
    static final Integer AGE_MIN_ONE_MORE = AGE_MIN + 1;

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Age(null));
    }

    @Test
    public void constructor_invalidAge_throwsIllegalArgumentException() {
        String invalidAge = "";
        assertThrows(IllegalArgumentException.class, () -> new Age(invalidAge));
    }

    @Test
    public void isValidAge() {
        // invalid age
        assertFalse(Age.isValidAge("")); // empty string
        assertFalse(Age.isValidAge(" ")); // spaces only
        assertFalse(Age.isValidAge("^")); // only non-alphanumeric characters
        assertFalse(Age.isValidAge("peter*")); // contains non-alphanumeric characters
        assertFalse(Age.isValidAge("Peter")); //alphabets
        assertFalse(Age.isValidAge("-74")); //negative numbers
        assertFalse(Age.isValidAge("-150")); //negative 3 digits
        assertFalse(Age.isValidAge(null)); //null age
        assertFalse(Age.isValidAge("0.8")); //decimals
        assertFalse(Age.isValidAge("1.9")); //decimals
        assertFalse(Age.isValidAge("-82.9")); //negative decimals
        assertFalse(Age.isValidAge(AGE_MIN.toString())); //min valid age
        assertFalse(Age.isValidAge(AGE_MAX.toString())); //max valid age
        assertFalse(Age.isValidAge(AGE_MAX_ONE_MORE.toString())); //max age + 1
        assertFalse(Age.isValidAge(AGE_MAX_TEN_MORE.toString())); //max age + 10
        assertFalse(Age.isValidAge(AGE_MIN_ONE_LESS.toString())); //min age - 1

        // valid age
        for (int age = AGE_MIN + 1; age < AGE_MAX; age++) {
            Integer currentAge = age;
            assertTrue(Age.isValidAge(currentAge.toString()));
        }
    }

    @Test
    public void toStringTest() {
        assertEquals(new Age(AGE_MAX_ONE_LESS.toString()).toString(), AGE_MAX_ONE_LESS.toString());
        assertEquals(new Age(AGE_MIN_ONE_MORE.toString()).toString(), AGE_MIN_ONE_MORE.toString());
    }

    @Test
    public void equals() {
        Age maxValidAge = new Age((AGE_MAX_ONE_LESS.toString()));

        assertFalse(maxValidAge.equals(null));
        assertFalse(maxValidAge.equals(new Age(AGE_MIN_ONE_MORE.toString())));
        assertTrue(maxValidAge.equals(maxValidAge));
        assertTrue(maxValidAge.equals(new Age(AGE_MAX_ONE_LESS.toString())));
    }

    @Test
    public void hashCodeTest() {
        Age maxValidAge = new Age((AGE_MAX_ONE_LESS.toString()));

        assertEquals(maxValidAge.hashCode(), new Age((AGE_MAX_ONE_LESS.toString())).hashCode());
        assertNotEquals(maxValidAge.hashCode(), new Age((AGE_MIN_ONE_MORE.toString())).hashCode());
    }
}
