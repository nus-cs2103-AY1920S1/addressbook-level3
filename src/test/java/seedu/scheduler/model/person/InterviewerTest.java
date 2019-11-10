package seedu.scheduler.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DEPARTMENT_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_SLOT_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.scheduler.testutil.Assert.assertThrows;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWER;
import static seedu.scheduler.testutil.TypicalPersons.BENSON_INTERVIEWER;

import java.util.Objects;

import org.junit.jupiter.api.Test;

import seedu.scheduler.testutil.InterviewerBuilder;

class InterviewerTest {

    @Test
    public void getTags_modifyUnderlyingTagSet_failure() {
        // The underlying tag set is unmodifiable, and will throw an exception if we try to change it.
        Interviewer interviewer = new InterviewerBuilder(ALICE_INTERVIEWER)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertThrows(UnsupportedOperationException.class, () -> interviewer.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> true
        assertTrue(ALICE_INTERVIEWER.isSamePerson(ALICE_INTERVIEWER));

        // null -> returns false
        assertFalse(ALICE_INTERVIEWER.isSamePerson(null));

        // different name -> returns false
        Interviewer editedAlice = new InterviewerBuilder(ALICE_INTERVIEWER).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE_INTERVIEWER.isSamePerson(editedAlice));

        // same name, different attributes (department) -> returns true
        editedAlice = new InterviewerBuilder(ALICE_INTERVIEWER).withDepartment(VALID_DEPARTMENT_BOB).build();
        assertTrue(ALICE_INTERVIEWER.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Interviewer aliceCopy = new InterviewerBuilder(ALICE_INTERVIEWER).build();
        assertTrue(ALICE_INTERVIEWER.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE_INTERVIEWER.equals(ALICE_INTERVIEWER));

        // null -> returns false
        assertFalse(ALICE_INTERVIEWER.equals(null));

        // different type -> returns false
        assertFalse(ALICE_INTERVIEWER.equals(5));

        // different person -> returns false
        assertFalse(ALICE_INTERVIEWER.equals(BENSON_INTERVIEWER));

        // different name -> returns false
        Interviewer editedAlice = new InterviewerBuilder(ALICE_INTERVIEWER).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE_INTERVIEWER.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new InterviewerBuilder(ALICE_INTERVIEWER).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE_INTERVIEWER.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new InterviewerBuilder(ALICE_INTERVIEWER).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertFalse(ALICE_INTERVIEWER.equals(editedAlice));

        // different department -> returns false
        editedAlice = new InterviewerBuilder(ALICE_INTERVIEWER).withDepartment(VALID_DEPARTMENT_BOB).build();
        assertFalse(ALICE_INTERVIEWER.equals(editedAlice));

        // different slots -> returns false
        editedAlice = new InterviewerBuilder(ALICE_INTERVIEWER).withAvailabilities(VALID_SLOT_BOB).build();
        assertFalse(ALICE_INTERVIEWER.equals(editedAlice));
    }

    @Test
    public void hashCode_validInterviewer_success() {
        int expectedHash = Objects.hash(ALICE_INTERVIEWER.getDepartment(), ALICE_INTERVIEWER.getEmail(),
                ALICE_INTERVIEWER.getAvailabilities(), ALICE_INTERVIEWER.getName(), ALICE_INTERVIEWER.getPhone(),
                ALICE_INTERVIEWER.getTags());

        Interviewer interviewer = new InterviewerBuilder(ALICE_INTERVIEWER).build();

        assertEquals(expectedHash, interviewer.hashCode());
    }
}
