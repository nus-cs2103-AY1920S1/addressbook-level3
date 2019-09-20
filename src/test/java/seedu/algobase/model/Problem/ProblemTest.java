package seedu.algobase.model.Problem;

import org.junit.jupiter.api.Test;
import seedu.algobase.testutil.ProblemBuilder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.logic.commands.CommandTestUtil.*;
import static seedu.algobase.testutil.Assert.assertThrows;
import static seedu.algobase.testutil.TypicalProblems.ALICE;
import static seedu.algobase.testutil.TypicalProblems.BOB;

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

        // different phone and email -> returns false
        Problem editedAlice = new ProblemBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameProblem(editedAlice));

        // different name -> returns false
        editedAlice = new ProblemBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameProblem(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new ProblemBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameProblem(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new ProblemBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameProblem(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new ProblemBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
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

        // different phone -> returns false
        editedAlice = new ProblemBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new ProblemBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new ProblemBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ProblemBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
