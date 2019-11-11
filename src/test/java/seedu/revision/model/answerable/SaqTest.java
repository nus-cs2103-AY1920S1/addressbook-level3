package seedu.revision.model.answerable;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_GREENFIELD;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BETA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_SAQ_QUESTION_1;
import static seedu.revision.testutil.Assert.assertThrows;
import static seedu.revision.testutil.TypicalSaqs.SAQ_A;
import static seedu.revision.testutil.TypicalSaqs.SAQ_B;
import static seedu.revision.testutil.TypicalSaqs.SAQ_CORRECT_ANSWER_LIST_A;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.revision.testutil.builder.SaqBuilder;

public class SaqTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SaqBuilder().withQuestion(null));
    }

    @Test
    public void isValidSaq() {
        //Boundary Value Analysis. EPs:
        // Number of Correct answers: [0], [1, INT_MAX]*
        // Number of Wrong answers: [0]*, [1, INT_MAX] no way to create am Saq object with non-zero wrong answers
        // * represents the valid input.

        //Valid Saq with multiple answers
        Saq validSaq = new SaqBuilder().withCorrectAnswerList(SAQ_CORRECT_ANSWER_LIST_A).build();
        assertTrue(Saq.isValidSaq(validSaq));

        //Invalid Saqs
        //Boundary Value 0 Correct Answers i.e. Empty List
        Saq saqWithNoCorrectAnswers = new SaqBuilder()
                .withCorrectAnswerList(new ArrayList<>()).build();
        assertFalse(Saq.isValidSaq(saqWithNoCorrectAnswers));

        //Null Value
        assertThrows(NullPointerException.class, () -> Saq.isValidSaq(null));
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Saq saq = new SaqBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> saq.getCategories().remove(0));
    }

    @Test
    public void isCorrect() {
        assertTrue(SAQ_A.isCorrect(new Answer("Unit Testing")));
        assertFalse(SAQ_A.isCorrect(new Answer("Definitely wrong")));
        assertThrows(NullPointerException.class, () -> SAQ_A.isCorrect(null));
    }

    //Concrete method implemented by abstract class Answerable.
    @Test
    public void isSameAnswerable() {
        // same object -> returns true
        assertTrue(SAQ_B.isSameAnswerable(SAQ_B));

        // null -> returns false
        assertFalse(SAQ_B.isSameAnswerable(null));

        // different question -> returns false
        Answerable editedAnswerable = new SaqBuilder(SAQ_B).withQuestion(VALID_SAQ_QUESTION_1).build();
        assertFalse(SAQ_B.isSameAnswerable(editedAnswerable));

        // same question, same difficulty, different attributes -> returns false
        editedAnswerable = new SaqBuilder(SAQ_B).withCategories(VALID_CATEGORY_GREENFIELD).build();
        assertTrue(SAQ_B.isSameAnswerable(editedAnswerable));

        // same question, different difficulty, same attributes -> returns false
        editedAnswerable = new SaqBuilder(SAQ_B).withDifficulty(VALID_DIFFICULTY_BETA).build();
        assertTrue(SAQ_B.isSameAnswerable(editedAnswerable));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Answerable answerableCopy = new SaqBuilder(SAQ_B).build();
        assertTrue(SAQ_B.equals(answerableCopy));

        // same object -> returns true
        assertTrue(SAQ_B.equals(SAQ_B));


        // different type -> returns false
        assertFalse(SAQ_B.equals(5));

        // different answerable -> returns false
        assertFalse(SAQ_B.equals(SAQ_A));

        // different question -> returns false
        Answerable editedAnswerable = new SaqBuilder(SAQ_B).withQuestion(VALID_SAQ_QUESTION_1).build();
        assertFalse(SAQ_B.equals(editedAnswerable));

        // different difficulty -> returns false
        editedAnswerable = new SaqBuilder(SAQ_B).withDifficulty(VALID_DIFFICULTY_BETA).build();
        assertFalse(SAQ_B.equals(editedAnswerable));

        // different categories -> returns false
        editedAnswerable = new SaqBuilder(SAQ_B).withCategories(VALID_CATEGORY_GREENFIELD).build();
        assertFalse(SAQ_B.equals(editedAnswerable));
    }
}
