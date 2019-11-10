package seedu.scheduler.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DEPARTMENT_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_FACULTY_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_NUS_WORK_EMAIL_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_PERSONAL_EMAIL_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_SLOT_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_YEAR_OF_STUDY_BOB;
import static seedu.scheduler.testutil.Assert.assertThrows;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWEE;
import static seedu.scheduler.testutil.TypicalPersons.BENSON_INTERVIEWEE;

import java.util.Objects;

import org.junit.jupiter.api.Test;

import seedu.scheduler.testutil.IntervieweeBuilder;

class IntervieweeTest {

    @Test
    public void getTags_modifyUnderlyingTagSet_failure() {
        // The underlying tag set is unmodifiable, and will throw an exception if we try to change it.
        Interviewee interviewee = new IntervieweeBuilder(ALICE_INTERVIEWEE)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertThrows(UnsupportedOperationException.class, () -> interviewee.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> true
        assertTrue(ALICE_INTERVIEWEE.isSamePerson(ALICE_INTERVIEWEE));

        // null -> returns false
        assertFalse(ALICE_INTERVIEWEE.isSamePerson(null));

        // different name -> returns false
        Interviewee editedAlice = new IntervieweeBuilder(ALICE_INTERVIEWEE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE_INTERVIEWEE.isSamePerson(editedAlice));

        // same name, different attributes (faculty and email) -> returns true
        editedAlice = new IntervieweeBuilder(ALICE_INTERVIEWEE).withFaculty(VALID_FACULTY_BOB)
                .withPersonalEmail(VALID_PERSONAL_EMAIL_BOB).build();
        assertTrue(ALICE_INTERVIEWEE.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Interviewee aliceCopy = new IntervieweeBuilder(ALICE_INTERVIEWEE).build();
        assertTrue(ALICE_INTERVIEWEE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE_INTERVIEWEE.equals(ALICE_INTERVIEWEE));

        // null -> returns false
        assertFalse(ALICE_INTERVIEWEE.equals(null));

        // different type -> returns false
        assertFalse(ALICE_INTERVIEWEE.equals(5));

        // different person -> returns false
        assertFalse(ALICE_INTERVIEWEE.equals(BENSON_INTERVIEWEE));

        // different name -> returns false
        Interviewee editedAlice = new IntervieweeBuilder(ALICE_INTERVIEWEE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE_INTERVIEWEE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new IntervieweeBuilder(ALICE_INTERVIEWEE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE_INTERVIEWEE.equals(editedAlice));

        // different personal email -> returns false
        editedAlice = new IntervieweeBuilder(ALICE_INTERVIEWEE).withPersonalEmail(VALID_PERSONAL_EMAIL_BOB).build();
        assertFalse(ALICE_INTERVIEWEE.equals(editedAlice));

        // different work email -> returns false
        editedAlice = new IntervieweeBuilder(ALICE_INTERVIEWEE).withNusWorkEmail(VALID_NUS_WORK_EMAIL_BOB).build();
        assertFalse(ALICE_INTERVIEWEE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new IntervieweeBuilder(ALICE_INTERVIEWEE).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertFalse(ALICE_INTERVIEWEE.equals(editedAlice));

        // different faculty -> returns false
        editedAlice = new IntervieweeBuilder(ALICE_INTERVIEWEE).withFaculty(VALID_FACULTY_BOB).build();
        assertFalse(ALICE_INTERVIEWEE.equals(editedAlice));

        // different year of study -> returns false
        editedAlice = new IntervieweeBuilder(ALICE_INTERVIEWEE).withYearOfStudy(VALID_YEAR_OF_STUDY_BOB).build();
        assertFalse(ALICE_INTERVIEWEE.equals(editedAlice));

        // different departments -> returns false
        editedAlice = new IntervieweeBuilder(ALICE_INTERVIEWEE).withDepartmentChoices(VALID_DEPARTMENT_BOB).build();
        assertFalse(ALICE_INTERVIEWEE.equals(editedAlice));

        // different slots -> returns false
        editedAlice = new IntervieweeBuilder(ALICE_INTERVIEWEE).withTimeslots(VALID_SLOT_BOB).build();
        assertFalse(ALICE_INTERVIEWEE.equals(editedAlice));
    }

    @Test
    public void hashCode_validInterviewee_success() {
        int expectedHash = Objects.hash(ALICE_INTERVIEWEE.getEmails(), ALICE_INTERVIEWEE.getFaculty(),
                ALICE_INTERVIEWEE.getYearOfStudy(), ALICE_INTERVIEWEE.getDepartmentChoices(),
                ALICE_INTERVIEWEE.getAvailableTimeslots(), ALICE_INTERVIEWEE.getName(),
                ALICE_INTERVIEWEE.getPhone(), ALICE_INTERVIEWEE.getTags());

        Interviewee interviewee = new IntervieweeBuilder(ALICE_INTERVIEWEE).build();

        assertEquals(expectedHash, interviewee.hashCode());
    }
}
