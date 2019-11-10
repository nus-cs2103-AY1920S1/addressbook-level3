package seedu.address.model.cap.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TitleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Title(null));
    }

    @Test
    public void constructor_invalidTitle_throwsIllegalArgumentException() {
        String invalidTitle = "";
        assertThrows(IllegalArgumentException.class, () -> new Title(invalidTitle));
    }


    @Test
    public void isValidTitle() {
        // null Title
        assertThrows(NullPointerException.class, () -> Title.isValidTitle(null));

        // blank Title
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // spaces only

        // invalid parts
        assertFalse(Title.isValidTitle("1234"));
        assertFalse(Title.isValidTitle("!4351"));

        // valid Title
        assertTrue(Title.isValidTitle("Programming Methodology"));
        assertTrue(Title.isValidTitle("Financial Accounting"));
        assertTrue(Title.isValidTitle("Managerial Accounting"));
        assertTrue(Title.isValidTitle("Contemporary Music Performance"));
        assertTrue(Title.isValidTitle("Text Analytics"));
        assertTrue(Title.isValidTitle("Advanced Concrete Technology"));
        assertTrue(Title.isValidTitle("German 6"));
        assertTrue(Title.isValidTitle("How the Ocean Works"));
    }
}
