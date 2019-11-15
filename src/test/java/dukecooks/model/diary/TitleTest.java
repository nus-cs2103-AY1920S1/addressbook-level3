package dukecooks.model.diary;

import static dukecooks.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.model.diary.components.Title;

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

        // null title
        assertThrows(NullPointerException.class, () -> Title.isValidTitle(null));

        // invalid titles
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // spaces only
        assertFalse(Title.isValidTitle("@Invalid")); // invalid symbols

        // valid title
        assertTrue(Title.isValidTitle("Hello"));
        assertTrue(Title.isValidTitle("Hello123"));

    }

    @Test
    void testTitleEquals() {
        Title title1 = new Title("test");
        Title title2 = new Title("test");
        Title title3 = new Title("different");

        // same object
        assertTrue(title1.equals(title1));

        // different object, same fields
        assertTrue(title1.equals(title2));

        // different objects and fields
        assertFalse(title1.equals(null));
        assertFalse(title1.equals(title3));
    }

    @Test
    public void testTitleToString() {
        Title title = new Title("hello");
        String expected = "hello";
        assertEquals(title.toString(), expected);
    }
}
