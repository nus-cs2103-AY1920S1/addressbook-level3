package seedu.address.model.cheatsheet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

public class ContentTest {

    private final Set<Tag> defaultTags = new HashSet<>();
    private final String defaultQuestion = "question";
    private final String defaultAnswer = "answer";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Content(null, null));
        assertThrows(NullPointerException.class, () -> new Content("Has Content", null));
        assertThrows(NullPointerException.class, () -> new Content(null, defaultTags));

        assertThrows(NullPointerException.class, () -> new Content(null, null, null));

        assertThrows(NullPointerException.class, () -> new Content(defaultQuestion, null, null));
        assertThrows(NullPointerException.class, () -> new Content(null, defaultAnswer, null));
        assertThrows(NullPointerException.class, () -> new Content(null, null, defaultTags));

        assertThrows(NullPointerException.class, () -> new Content(null, defaultAnswer, defaultTags));
        assertThrows(NullPointerException.class, () -> new Content(defaultQuestion, null, defaultTags));
        assertThrows(NullPointerException.class, () -> new Content(defaultQuestion, defaultAnswer, null));
    }

    @Test
    public void constructor_invalidContent_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Content("", defaultTags));

        assertThrows(IllegalArgumentException.class, () -> new Content("", "", defaultTags));
        assertThrows(IllegalArgumentException.class, () -> new Content("", defaultAnswer, defaultTags));
        assertThrows(IllegalArgumentException.class, () -> new Content(defaultQuestion, "", defaultTags));
    }

    @Test
    public void isValidContent() {
        // null name
        assertThrows(NullPointerException.class, () -> Content.isValidContent(null));

        // invalid Content
        assertFalse(Content.isValidContent("")); // empty string
        assertFalse(Content.isValidContent(" ")); // spaces only

        // valid Content
        assertTrue(Content.isValidContent("^")); // only non-alphanumeric characters
        assertTrue(Content.isValidContent("content*")); // contains non-alphanumeric characters
        assertTrue(Content.isValidContent("this is a content")); // alphabets only
        assertTrue(Content.isValidContent("12345")); // numbers only
        assertTrue(Content.isValidContent("this is the 2nd content")); // alphanumeric characters
        assertTrue(Content.isValidContent("The Capital Content")); // with capital letters
        assertTrue(Content.isValidContent(
                "This Title is a very "
                        + "Loooooooooooooooooooooooooooo00oooooooooooooooooooooooooooooooooooooooong "
                        + "tiTle")); // long content
    }

    @Test
    public void method_equals() {
        Content content = new Content("A content", defaultTags);
        Content anotherContent = new Content("Another content", defaultTags);

        assertTrue(content.equals(content));
        assertTrue(content.equals(new Content("A content", defaultTags)));

        assertFalse(content.equals(anotherContent));
        assertFalse(content.equals(null));
        assertFalse(new Content(defaultQuestion, defaultAnswer, defaultTags).equals(
                new Content(defaultQuestion, "some other answer", defaultTags)));
    }

    @Test
    public void method_formatToList() {
        assertEquals("The truncated conten...",
                new Content("The truncated content that is very long", defaultTags).formatToList());

        assertEquals("Question: question; ...",
                new Content(defaultQuestion, defaultAnswer, defaultTags).formatToList());
    }
}
