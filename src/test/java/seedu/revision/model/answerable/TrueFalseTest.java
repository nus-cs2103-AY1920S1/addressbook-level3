package seedu.revision.model.answerable;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_GREENFIELD;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BETA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_TF_QUESTION_1;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_TF_QUESTION_2;
import static seedu.revision.testutil.Assert.assertThrows;
import static seedu.revision.testutil.TypicalTrueFalse.TF_A;
import static seedu.revision.testutil.TypicalTrueFalse.TF_ANSWER_FALSE;
import static seedu.revision.testutil.TypicalTrueFalse.TF_ANSWER_TRUE;
import static seedu.revision.testutil.TypicalTrueFalse.TF_B;
import static seedu.revision.testutil.TypicalTrueFalse.TF_C;
import static seedu.revision.testutil.TypicalTrueFalse.TF_TRUE_ANSWER_LIST;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.revision.testutil.builder.TrueFalseBuilder;

public class TrueFalseTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TrueFalseBuilder().withQuestion(null));
    }

    @Test
    public void isValidTrueFalse() {
        //Boundary Value Analysis. EPs:
        // Number of Correct answers: [0], [1]* [2, INT_MAX]
        // Number of Wrong answers: [0]*, [1, INT_MAX] no way to create a TrueFalse object with non-zero wrong answers
        // * represents the valid input.

        //Valid TrueFalse question
        TrueFalse validTrueFalse = new TrueFalseBuilder().withCorrectAnswerList(TF_TRUE_ANSWER_LIST).build();
        assertTrue(TrueFalse.isValidTrueFalse(validTrueFalse));

        //Invalid TrueFalse questions
        //Boundary Value 0 Correct Answers i.e. Empty List
        TrueFalse trueFalseWithNoCorrectAnswers = new TrueFalseBuilder()
                .withCorrectAnswerList(new ArrayList<>()).build();
        assertFalse(TrueFalse.isValidTrueFalse(trueFalseWithNoCorrectAnswers));

        //Boundary Value 2 Correct Answers
        TrueFalse trueFalseWithTwoTruesAsCorrectAnswers = new TrueFalseBuilder()
                .withCorrectAnswerList(new ArrayList<>(Arrays.asList(TF_ANSWER_TRUE, TF_ANSWER_TRUE)))
                .build();
        assertFalse(TrueFalse.isValidTrueFalse(trueFalseWithTwoTruesAsCorrectAnswers));

        //Null Value
        assertThrows(NullPointerException.class, () -> TrueFalse.isValidTrueFalse(null));
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        TrueFalse trueFalse = new TrueFalseBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> trueFalse.getCategories().remove(0));
    }

    @Test
    public void isAnswerCorrect() {
        assertTrue(TF_A.isAnswerCorrect(TF_ANSWER_TRUE));
        assertFalse(TF_A.isAnswerCorrect(TF_ANSWER_FALSE));
        assertThrows(NullPointerException.class, () -> TF_A.isAnswerCorrect(null));
    }

    //Concrete method implemented by abstract class Answerable.
    @Test
    public void isSameAnswerable() {
        // same object -> returns true
        assertTrue(TF_B.isSameAnswerable(TF_B));

        // null -> returns false
        assertFalse(TF_B.isSameAnswerable(null));

        // different question -> returns false
        Answerable editedAnswerable = new TrueFalseBuilder(TF_B).withQuestion(VALID_TF_QUESTION_1).build();
        assertFalse(TF_B.isSameAnswerable(editedAnswerable));

        // same question, same difficulty, different attributes -> returns false
        editedAnswerable = new TrueFalseBuilder(TF_B).withCategories(VALID_CATEGORY_GREENFIELD).build();
        assertTrue(TF_B.isSameAnswerable(editedAnswerable));

        // same question, different difficulty, same attributes -> returns false
        editedAnswerable = new TrueFalseBuilder(TF_B).withDifficulty(VALID_DIFFICULTY_BETA).build();
        assertTrue(TF_B.isSameAnswerable(editedAnswerable));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Answerable answerableCopy = new TrueFalseBuilder(TF_C).build();
        assertTrue(TF_C.equals(answerableCopy));

        // same object -> returns true
        assertTrue(TF_C.equals(TF_C));


        // different type -> returns false
        assertFalse(TF_C.equals(5));

        // different answerable -> returns false
        assertFalse(TF_C.equals(TF_B));

        // different question -> returns false
        Answerable editedAnswerable = new TrueFalseBuilder(TF_C).withQuestion(VALID_TF_QUESTION_2).build();
        assertFalse(TF_C.equals(editedAnswerable));

        // different difficulty -> returns false
        editedAnswerable = new TrueFalseBuilder(TF_C).withDifficulty(VALID_DIFFICULTY_BETA).build();
        assertFalse(TF_C.equals(editedAnswerable));

        // different categories -> returns false
        editedAnswerable = new TrueFalseBuilder(TF_C).withCategories(VALID_CATEGORY_GREENFIELD).build();
        assertFalse(TF_C.equals(editedAnswerable));
    }
}
