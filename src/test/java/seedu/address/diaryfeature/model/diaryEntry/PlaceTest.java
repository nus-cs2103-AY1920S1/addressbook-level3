package seedu.address.diaryfeature.model.diaryEntry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.diaryfeature.logic.parser.Validators;

public class PlaceTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Place test = new Place(null);
        assertThrows(NullPointerException.class, () -> { test.toString().length();});    }

    @Test
    public void copy_is_different_from_original() {
        Place test = new Place("Hello");
        assertFalse(test == test.copy());
    }

    @Test
    public void to_string_test() {
        Place test = new Place("Hello");
        assertEquals("Hello", test.toString());
        assertNotEquals("", test.toString());
    }

}
