package seedu.tarence.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.tarence.testutil.TypicalStudents.ALICE;
import static seedu.tarence.testutil.TypicalStudents.BOB;

import org.junit.jupiter.api.Test;

import seedu.tarence.model.builder.StudentBuilder;

public class StudentTest {

    @Test
    public void isSameStudent() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // different email -> returns false
        Student editedAlice = new StudentBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertTrue(ALICE.isSameStudent(editedAlice));

        // different name -> returns false
        editedAlice = new StudentBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameStudent(editedAlice));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Student aliceCopy = new StudentBuilder(ALICE).build();
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
        Student editedAlice = new StudentBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new StudentBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different matric num -> returns false
        editedAlice = new StudentBuilder(ALICE).withMatricNum("A1234567A").build();
        assertFalse(ALICE.equals(editedAlice));

        // different NUSNET id -> returns false
        editedAlice = new StudentBuilder(ALICE).withNusnetId("e1234567").build();
        assertFalse(ALICE.equals(editedAlice));

    }
}
