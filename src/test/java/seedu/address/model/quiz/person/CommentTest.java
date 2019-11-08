package seedu.address.model.quiz.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CommentTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Comment(null));
    }

    @Test
    public void isValidComment() {
        // null answer number
        assertThrows(NullPointerException.class, () -> Comment.isValidComment(null));

        assertFalse(Comment.isValidComment("asd<val> asd"));

        // valid answer numbers
        assertTrue(Comment.isValidComment("")); // empty string
        assertTrue(Comment.isValidComment(" ")); // spaces only
        assertTrue(Comment.isValidComment("My Comment")); // alphabets within digits
        assertTrue(Comment.isValidComment("123 456")); // spaces within digits
        assertTrue(Comment.isValidComment("124293842033123")); // long answer numbers
    }
}
