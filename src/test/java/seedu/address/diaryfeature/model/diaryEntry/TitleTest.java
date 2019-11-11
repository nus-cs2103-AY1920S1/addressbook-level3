package seedu.address.diaryfeature.model.diaryEntry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;



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
    public void to_string_test() {
        Title test = new Title("Hello");
        assertEquals("Hello", test.toString());
        assertNotEquals("", test.toString());
    }




}






