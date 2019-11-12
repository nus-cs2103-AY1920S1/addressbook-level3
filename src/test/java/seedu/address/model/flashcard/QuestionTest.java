package seedu.address.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

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
    public void method_toString() {
        assertEquals("Is this a question?", new Question("Is this a question?").toString());
        assertNotEquals(123, new Question("123").toString());
    }

    @Test
    public void equals() {
        Question question = new Question("A question.");
        Question anotherQuestion = new Question("Another question...");

        assertTrue(question.equals(question));
        assertTrue(question.equals(new Question("A question.")));

        assertFalse(question.equals(anotherQuestion));
        assertFalse(question.equals(null));
    }

    @Test
    public void isValidQuestion() {
        // null question
        assertThrows(NullPointerException.class, () -> Question.isValidQuestion(null));

        // invalid questions
        assertFalse(Question.isValidQuestion("")); // empty string
        assertFalse(Question.isValidQuestion(" ")); // spaces only
        assertFalse(Question.isValidQuestion("This question is a very "
                + "Loooooooooooooooooooooooooooo0000oooooooooooooooooooooooooooooooooooooooong "
                + "Loooooooooooooooooooooooooooo0000oooooooooooooooooooooooooooooooooooooooong "
                + "Loooooooooooooooooooooooooooo0000oooooooooooooooooooooooooooooooooooooooong "
                + "Loooooooooooooooooooooooooooo0000oooooooooooooooooooooooooooooooooooooooong "
                + "question")); // more than max length question

        // valid questions
        assertTrue(Question.isValidQuestion("a")); // one character
        assertTrue(Question.isValidQuestion("123456")); // numbers only
        assertTrue(Question.isValidQuestion("+-*&")); // only non-alphanumeric
        assertTrue(Question.isValidQuestion("What is my name?")); // alphanumeric and non-alphanumeric mix
        assertTrue(Question.isValidQuestion("What is my name")); // short question
        assertTrue(Question.isValidQuestion("This question is exactly 300 characters long! "
                + "This question is exactly 300 characters long! Filler..This question is exactly 300 characters long!"
                + "This question is exactly 300 characters long! Filler..This question is exactly 300 characters long!"
                + "This question is exactly 300 characters long! Filler....")); // max length question
    }
}
