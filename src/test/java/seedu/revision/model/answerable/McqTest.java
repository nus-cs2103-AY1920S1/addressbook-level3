package seedu.revision.model.answerable;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_GREENFIELD;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BETA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_MCQ_QUESTION_2;
import static seedu.revision.testutil.Assert.assertThrows;
import static seedu.revision.testutil.TypicalMcqs.MCQ_B;
import static seedu.revision.testutil.TypicalMcqs.MCQ_C;
import static seedu.revision.testutil.TypicalMcqs.MCQ_CORRECT_ANSWER_A;
import static seedu.revision.testutil.TypicalMcqs.MCQ_VALID_CORRECT_ANSWER_LIST;
import static seedu.revision.testutil.TypicalMcqs.MCQ_VALID_WRONG_ANSWER_LIST;
import static seedu.revision.testutil.TypicalMcqs.MCQ_WRONG_ANSWER_A;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.revision.testutil.McqBuilder;

public class McqTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new McqBuilder().withQuestion(null));
    }

    @Test
    public void isValidMcq() {
        //Boundary Value Analysis. EPs:
        // Number of Correct answers: [0], [1]* [2, INT_MAX]
        // Number of Wrong answers: [0-2], [3]*, [4, INT_MAX]
        // * represents the valid input.

        //Valid Mcq
        Mcq validMcq = new McqBuilder().withCorrectAnswerList(MCQ_VALID_CORRECT_ANSWER_LIST)
                .withWrongAnswerList(MCQ_VALID_WRONG_ANSWER_LIST).build();
        assertTrue(Mcq.isValidMcq(validMcq));

        //Invalid Mcqs
        //Boundary Value 0 Correct Answers i.e. Empty List
        Mcq mcqWithNoCorrectAnswers = new McqBuilder().withCorrectAnswerList(new ArrayList<>())
                .withWrongAnswerList(MCQ_VALID_WRONG_ANSWER_LIST).build();
        assertFalse(Mcq.isValidMcq(mcqWithNoCorrectAnswers));

        //Boundary Value 2 Correct Answers i.e. Empty List
        Mcq mcqWithTwoCorrectAnswers = new McqBuilder().withCorrectAnswerList(new ArrayList<>(
                Arrays.asList(new Answer("one"), new Answer("two"))))
                .withWrongAnswerList(MCQ_VALID_WRONG_ANSWER_LIST).build();
        assertFalse(Mcq.isValidMcq(mcqWithTwoCorrectAnswers));

        //Boundary Value 2 wrong answers
        Mcq mcqWithTwoWrongAnswers = new McqBuilder().withCorrectAnswerList(MCQ_VALID_CORRECT_ANSWER_LIST)
                .withWrongAnswerList(new ArrayList<>(Arrays.asList(new Answer("one"), new Answer("two")))).build();
        assertFalse(Mcq.isValidMcq(mcqWithTwoWrongAnswers));

        //Boundary Value 4 wrong answers
        Mcq mcqWithFourWrongAnswers = new McqBuilder().withCorrectAnswerList(MCQ_VALID_CORRECT_ANSWER_LIST)
                .withWrongAnswerList(new ArrayList<>(Arrays.asList(
                        new Answer("one"),
                        new Answer("two"),
                        new Answer("three"),
                        new Answer("four")
                ))).build();
        assertFalse(Mcq.isValidMcq(mcqWithFourWrongAnswers));

        //Null Value
        assertThrows(NullPointerException.class, () -> Mcq.isValidMcq(null));
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Mcq mcq = new McqBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> mcq.getCategories().remove(0));
    }

    @Test
    public void isCorrect() {
        assertTrue(MCQ_C.isAnswerCorrect(MCQ_CORRECT_ANSWER_A));
        assertFalse(MCQ_C.isAnswerCorrect(MCQ_WRONG_ANSWER_A));
        assertThrows(NullPointerException.class, () -> MCQ_C.isAnswerCorrect(null));
    }

    //Concrete method implemented by abstract alass Answerable.
    @Test
    public void isSameAnswerable() {
        // same object -> returns true
        assertTrue(MCQ_C.isSameAnswerable(MCQ_C));

        // null -> returns false
        assertFalse(MCQ_C.isSameAnswerable(null));

        // different question -> returns false
        Answerable editedAnswerable = new McqBuilder(MCQ_C).withQuestion(VALID_MCQ_QUESTION_2).build();
        assertFalse(MCQ_C.isSameAnswerable(editedAnswerable));

        // same question, same difficulty, different attributes -> returns true
        editedAnswerable = new McqBuilder(MCQ_C).withCategories(VALID_CATEGORY_GREENFIELD).build();
        assertTrue(MCQ_C.isSameAnswerable(editedAnswerable));

        // same question, same difficulty, different attributes -> returns true
        editedAnswerable = new McqBuilder(MCQ_C).withCategories(VALID_CATEGORY_GREENFIELD).build();
        assertTrue(MCQ_C.isSameAnswerable(editedAnswerable));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Answerable answerableCopy = new McqBuilder(MCQ_C).build();
        assertTrue(MCQ_C.equals(answerableCopy));

        // same object -> returns true
        assertTrue(MCQ_C.equals(MCQ_C));

        assertFalse(MCQ_C.equals(null));

        // different type -> returns false
        assertFalse(MCQ_C.equals(5));

        // different answerable -> returns false
        assertFalse(MCQ_C.equals(MCQ_B));

        // different question -> returns false
        Answerable editedAnswerable = new McqBuilder(MCQ_C).withQuestion(VALID_MCQ_QUESTION_2).build();
        assertFalse(MCQ_C.equals(editedAnswerable));

        // different difficulty -> returns false
        editedAnswerable = new McqBuilder(MCQ_C).withDifficulty(VALID_DIFFICULTY_BETA).build();
        assertFalse(MCQ_C.equals(editedAnswerable));

        // different categories -> returns false
        editedAnswerable = new McqBuilder(MCQ_C).withCategories(VALID_CATEGORY_GREENFIELD).build();
        assertFalse(MCQ_C.equals(editedAnswerable));
    }
}
