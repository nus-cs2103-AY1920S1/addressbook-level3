package seedu.address.model.answerable;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAnswerables.ALICE;
import static seedu.address.testutil.TypicalAnswerables.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.AnswerableBuilder;

public class AnswerableTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Answerable answerable = new AnswerableBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> answerable.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSameAnswerable(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameAnswerable(null));

        // different difficulty -> returns false
        Answerable editedAlice = new AnswerableBuilder(ALICE).withDifficulty(VALID_DIFFICULTY_BOB).build();
        assertFalse(ALICE.isSameAnswerable(editedAlice));

        // different name -> returns false
        editedAlice = new AnswerableBuilder(ALICE).withQuestion(VALID_QUESTION_BOB).build();
        assertFalse(ALICE.isSameAnswerable(editedAlice));

        // same name, same difficulty, different attributes -> returns true
        editedAlice = new AnswerableBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameAnswerable(editedAlice));

        // same name, same difficulty, different attributes -> returns true
        editedAlice = new AnswerableBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameAnswerable(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Answerable aliceCopy = new AnswerableBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different answerable -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Answerable editedAlice = new AnswerableBuilder(ALICE).withQuestion(VALID_QUESTION_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different difficulty -> returns false
        editedAlice = new AnswerableBuilder(ALICE).withDifficulty(VALID_DIFFICULTY_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new AnswerableBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new AnswerableBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
