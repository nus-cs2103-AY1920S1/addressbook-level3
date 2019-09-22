package seedu.algobase.model.problem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.algobase.testutil.Assert.assertThrows;
import static seedu.algobase.testutil.TypicalProblems.ALICE;
import static seedu.algobase.testutil.TypicalProblems.BOB;

import org.junit.jupiter.api.Test;

import seedu.algobase.testutil.ProblemBuilder;

public class ProblemTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Problem problem = new ProblemBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> problem.getTags().remove(0));
    }

    @Test
    public void isSameProblem() {
        // same object -> returns true
        assertTrue(ALICE.isSameProblem(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameProblem(null));

        // different author and weblink -> returns false
        Problem editedAlice =
                new ProblemBuilder(ALICE).withAuthor(VALID_PHONE_BOB).withWeblink(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameProblem(editedAlice));

        // different name -> returns false
        editedAlice = new ProblemBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameProblem(editedAlice));

        // same name, same author, different attributes -> returns true
        editedAlice = new ProblemBuilder(ALICE).withWeblink(VALID_EMAIL_BOB).withDescription(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameProblem(editedAlice));

        // same name, same weblink, different attributes -> returns true
        editedAlice = new ProblemBuilder(ALICE).withAuthor(VALID_PHONE_BOB).withDescription(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameProblem(editedAlice));

        // same name, same author, same weblink, different attributes -> returns true
        editedAlice = new ProblemBuilder(ALICE).withDescription(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameProblem(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Problem aliceCopy = new ProblemBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different Problem -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Problem editedAlice = new ProblemBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different author -> returns false
        editedAlice = new ProblemBuilder(ALICE).withAuthor(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different weblink -> returns false
        editedAlice = new ProblemBuilder(ALICE).withWeblink(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different description -> returns false
        editedAlice = new ProblemBuilder(ALICE).withDescription(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ProblemBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
