package seedu.flashcard.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        // null Question
        assertThrows(NullPointerException.class, () -> Question.isValidQuestion(null));

        // invalid Question
        assertFalse(Question.isValidQuestion("")); // empty string
        assertFalse(Question.isValidQuestion(" ")); // spaces only

        // valid Question
        assertTrue(Question.isValidQuestion("3"));
        assertTrue(Question.isValidQuestion("/")); // one character
        assertTrue(Question.isValidQuestion("Builds applications by combining functionalities"
                + " packaged as programmatically")); // long answer
    }

    @Test
    public void shortenLabel_shortenQuestion_success() {
        //Question length is 6
        assertEquals("There are six words in this ...",
                new Question("There are six words in this").shortenForLabel());

        //Question length is 5
        assertEquals("There are five words here ",
                new Question("There are five words here").shortenForLabel());

        //Question length is 7
        assertEquals("There are seven words in this ... Question",
                new Question("There are seven words in this Question").shortenForLabel());
    }
}
