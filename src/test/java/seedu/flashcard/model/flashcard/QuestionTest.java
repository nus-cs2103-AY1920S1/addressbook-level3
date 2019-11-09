package seedu.flashcard.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.flashcard.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class QuestionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Question(null));
    }

    @Test
    public void constructor_invalidQuestion_throwsIllegalArgumentException() {
        String invalidQuestion = "";
        assertThrows(IllegalArgumentException.class, () -> new Question(invalidQuestion));
    }

    @Test
    public void isValidQuestion() {
        assertThrows(NullPointerException.class, () -> Question.isValidQuestion(null));
        assertFalse(Question.isValidQuestion(""));
        assertFalse(Question.isValidQuestion(" "));
        assertTrue(Question.isValidQuestion("When are we going to eat breakfast?"));
    }
}
