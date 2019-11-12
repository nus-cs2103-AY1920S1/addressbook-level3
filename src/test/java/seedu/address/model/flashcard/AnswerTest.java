package seedu.address.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AnswerTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Answer(null));
    }

    @Test
    public void constructor_invalidAnswer_throwsIllegalArgumentException() {
        String invalidAnswer = "";
        assertThrows(IllegalArgumentException.class, () -> new Answer(invalidAnswer));
    }

    @Test
    public void method_toString() {
        assertEquals("This is an answer.", new Answer("This is an answer.").toString());
        assertNotEquals(123, new Answer("123").toString());
    }

    @Test
    public void equals() {
        Answer answer = new Answer("An answer?");
        Answer anotherAnswer = new Answer("Another answer...");

        assertTrue(answer.equals(answer));
        assertTrue(answer.equals(new Answer("An answer?")));

        assertFalse(answer.equals(anotherAnswer));
        assertFalse(answer.equals(null));
    }

    @Test
    public void isValidAnswer() {
        // null question
        assertThrows(NullPointerException.class, () -> Answer.isValidAnswer(null));

        // invalid questions
        assertFalse(Answer.isValidAnswer("")); // empty string
        assertFalse(Answer.isValidAnswer(" ")); // spaces only
        assertFalse(Answer.isValidAnswer("This answer is a very "
                + "Loooooooooooooooooooooooooooo0000oooooooooooooooooooooooooooooooooooooooong "
                + "Loooooooooooooooooooooooooooo0000oooooooooooooooooooooooooooooooooooooooong "
                + "Loooooooooooooooooooooooooooo0000oooooooooooooooooooooooooooooooooooooooong "
                + "Loooooooooooooooooooooooooooo0000oooooooooooooooooooooooooooooooooooooooong "
                + "answer")); // more than max length answer

        // valid questions
        assertTrue(Answer.isValidAnswer("a")); // one character
        assertTrue(Answer.isValidAnswer("123456")); // numbers only
        assertTrue(Answer.isValidAnswer("+-*&")); // only non-alphanumeric
        assertTrue(Answer.isValidAnswer("Hydrogen-99")); // alphanumeric and non-alphanumeric mix
        assertTrue(Answer.isValidAnswer("Weak van der Walls force.")); // short answer
        assertTrue(Question.isValidQuestion("This question is exactly 300 characters long! "
                + "This question is exactly 300 characters long! Filler..This question is exactly 300 characters long!"
                + "This question is exactly 300 characters long! Filler..This question is exactly 300 characters long!"
                + "This question is exactly 300 characters long! Filler....")); // max length answer
    }
}
