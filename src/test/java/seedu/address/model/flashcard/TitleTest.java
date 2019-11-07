package seedu.address.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
    public void method_toString() {
        assertEquals("This is a title", new Title("This is a title").toString());
        assertNotEquals(123, new Title("123").toString());
    }

    @Test
    public void method_equals() {
        Title title = new Title("A title");
        Title anotherTitle = new Title("Another title");

        assertTrue(title.equals(title));
        assertTrue(title.equals(new Title("A title")));

        assertFalse(title.equals(anotherTitle));
        assertFalse(title.equals(null));
    }

    @Test
    public void isValidTitle() {
        // null name
        assertThrows(NullPointerException.class, () -> Title.isValidTitle(null));

        // invalid Title
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // spaces only
        assertFalse(Title.isValidTitle("^")); // only non-alphanumeric characters
        assertFalse(Title.isValidTitle("title*")); // contains non-alphanumeric characters
        assertFalse(Title.isValidTitle("This Title is a very "
                + "Loooooooooooooooooooooooooooo0000oooooooooooooooooooooooooooooooooooooooong "
                + "tiTle")); // more than max length titles

        // valid Title
        assertTrue(Title.isValidTitle("this is a title")); // alphabets only
        assertTrue(Title.isValidTitle("12345")); // numbers only
        assertTrue(Title.isValidTitle("this is the 2nd title")); // alphanumeric characters
        assertTrue(Title.isValidTitle("The Capital Title")); // with capital letters
        assertTrue(Title.isValidTitle(
                "This Title is a very "
                        + "Loooooooooooooooooooooooooooo00oooooooooooooooooooooooooooooooooooooooong"
                        + " tiTle")); // max length titles
    }
}
