package seedu.scheduler.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWEE;
import static seedu.scheduler.testutil.TypicalPersons.BOB_INTERVIEWEE_MANUAL;

import org.junit.jupiter.api.Test;

import seedu.scheduler.testutil.TestUtil;

/**
 * Unit tests for EditIntervieweeDescriptor.
 */
public class EditIntervieweeDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditIntervieweeCommand.EditIntervieweeDescriptor aliceDescriptor =
                TestUtil.getDescriptorFromInterviewee(ALICE_INTERVIEWEE);
        EditIntervieweeCommand.EditIntervieweeDescriptor aliceDescriptorDuplicate =
                TestUtil.getDescriptorFromInterviewee(ALICE_INTERVIEWEE);
        EditIntervieweeCommand.EditIntervieweeDescriptor bobDescriptor =
                TestUtil.getDescriptorFromInterviewee(BOB_INTERVIEWEE_MANUAL);
        assertTrue(aliceDescriptor.equals(aliceDescriptorDuplicate));

        // same object -> returns true
        assertTrue(aliceDescriptor.equals(aliceDescriptor));

        // null -> returns false
        assertFalse(aliceDescriptor.equals(null));

        // different types -> returns false
        assertFalse(aliceDescriptor.equals(5));

        // different values -> returns false
        assertFalse(aliceDescriptor.equals(bobDescriptor));
    }
}
