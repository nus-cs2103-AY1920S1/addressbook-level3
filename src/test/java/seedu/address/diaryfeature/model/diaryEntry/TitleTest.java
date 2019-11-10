package seedu.address.diaryfeature.model.diaryEntry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.diaryfeature.logic.parser.Validators;


public class TitleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Title test = new Title(null);
        assertThrows(NullPointerException.class, () -> { test.toString().length();});    }

    @Test
    public void copy_is_different_from_original() {
        Title test = new Title("Hello");
        assertFalse(test == test.copy());
    }

    @Test
    public void isValidTitle() {
        // invalid title
        assertFalse(Validators.isNotNull(null)); // null
        assertFalse(Validators.isValidTitle("")); // empty string
        assertFalse(Validators.isValidTitle("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh")); // >50 chars only

        // valid name
        assertTrue(Validators.isValidTitle("Singing in the rain")); // alphabets only
        assertTrue(Validators.isValidTitle("10203")); // numbers only
        assertTrue(Validators.isValidTitle(" oompa lommpa 10203")); // alphanum
        assertTrue(Validators.isValidTitle("HELLO hi")); // Caps
        assertTrue(Validators.isValidTitle("HELLO hi hello hi 12345678910")); // <50 chars
    }



}






