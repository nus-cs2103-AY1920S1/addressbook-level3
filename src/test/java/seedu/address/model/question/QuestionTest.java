package seedu.address.model.question;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_CONCEPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_CONCEPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_BODY_CONCEPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_CONCEPT;
import static seedu.address.testutil.TypicalAppData.ALGEBRA_QUESTION;
import static seedu.address.testutil.TypicalAppData.CONCEPT_QUESTION;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.QuestionBuilder;

class QuestionTest {

    @Test
    public void isSameQuestion() {
        // same object -> returns true
        assertTrue(ALGEBRA_QUESTION.isSameQuestion(ALGEBRA_QUESTION));

        // null -> returns false
        assertFalse(ALGEBRA_QUESTION.isSameQuestion(null));

        // different question body -> returns false
        Question editedAlgebra = new QuestionBuilder(ALGEBRA_QUESTION)
                .withQuestionBody(VALID_QUESTION_BODY_CONCEPT)
                .withAnswer(VALID_ANSWER_CONCEPT)
                .build();
        assertFalse(ALGEBRA_QUESTION.isSameQuestion(editedAlgebra));

        // same question body, different subject -> returns true
        editedAlgebra = new QuestionBuilder(ALGEBRA_QUESTION)
                .withAnswer(VALID_ANSWER_CONCEPT)
                .withSubject(VALID_SUBJECT_CONCEPT)
                .build();
        assertTrue(ALGEBRA_QUESTION.isSameQuestion(editedAlgebra));

        // same question body, different attributes -> returns true
        editedAlgebra = new QuestionBuilder(ALGEBRA_QUESTION)
                .withAnswer(VALID_ANSWER_CONCEPT)
                .withDifficulty(VALID_DIFFICULTY_CONCEPT)
                .build();
        assertTrue(ALGEBRA_QUESTION.isSameQuestion(editedAlgebra));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Question algebraCopy = new QuestionBuilder(ALGEBRA_QUESTION).build();
        assertTrue(ALGEBRA_QUESTION.equals(algebraCopy));

        // same object -> returns true
        assertTrue(ALGEBRA_QUESTION.equals(ALGEBRA_QUESTION));

        // null -> returns false
        assertFalse(ALGEBRA_QUESTION.equals(null));

        // different type -> returns false
        assertFalse(ALGEBRA_QUESTION.equals(5));

        // different question -> returns false
        assertFalse(ALGEBRA_QUESTION.equals(CONCEPT_QUESTION));

        // different question body -> returns false
        Question editedAlgebra = new QuestionBuilder(ALGEBRA_QUESTION)
                .withQuestionBody(VALID_QUESTION_BODY_CONCEPT)
                .build();
        assertFalse(ALGEBRA_QUESTION.equals(editedAlgebra));

        // different subject -> returns false
        editedAlgebra = new QuestionBuilder(ALGEBRA_QUESTION)
                .withSubject(VALID_SUBJECT_CONCEPT)
                .build();
        assertFalse(ALGEBRA_QUESTION.equals(editedAlgebra));
    }
}
