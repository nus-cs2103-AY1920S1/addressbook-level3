package seedu.address.model.quiz.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_TYPE_BOB;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_CATEGORY_BOB;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_ANSWER_BOB;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_TAG_LECTURE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalQuestion.ALICE;
import static seedu.address.testutil.TypicalQuestion.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.quiz.person.Question;
import seedu.address.testutil.QuestionBuilder;



public class QuestionTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Question person = new QuestionBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSameQuestion() {
        // same object -> returns true
        assertTrue(ALICE.isSameQuestion(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameQuestion(null));

        // different phone and email -> returns false
        Question editedAlice = new QuestionBuilder(ALICE).withAnswer(VALID_ANSWER_BOB).withCategory(VALID_CATEGORY_BOB).build();
        assertFalse(ALICE.isSameQuestion(editedAlice));

        // different name -> returns false
        editedAlice = new QuestionBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameQuestion(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new QuestionBuilder(ALICE).withCategory(VALID_CATEGORY_BOB).withType(VALID_TYPE_BOB)
                .withTags(VALID_TAG_LECTURE).build();
        assertTrue(ALICE.isSameQuestion(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new QuestionBuilder(ALICE).withAnswer(VALID_ANSWER_BOB).withType(VALID_TYPE_BOB)
                .withTags(VALID_TAG_LECTURE).build();
        assertTrue(ALICE.isSameQuestion(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new QuestionBuilder(ALICE).withType(VALID_TYPE_BOB).withTags(VALID_TAG_LECTURE).build();
        assertTrue(ALICE.isSameQuestion(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Question aliceCopy = new QuestionBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Question editedAlice = new QuestionBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new QuestionBuilder(ALICE).withAnswer(VALID_ANSWER_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new QuestionBuilder(ALICE).withCategory(VALID_CATEGORY_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new QuestionBuilder(ALICE).withType(VALID_TYPE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new QuestionBuilder(ALICE).withTags(VALID_TAG_LECTURE).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
