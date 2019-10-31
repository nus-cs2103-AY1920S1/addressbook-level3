package seedu.scheduler.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWER;
import static seedu.scheduler.testutil.TypicalPersons.BENSON_INTERVIEWER;

import org.junit.jupiter.api.Test;

import seedu.scheduler.testutil.TestUtil;

public class EditInterviewerDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditInterviewerCommand.EditInterviewerDescriptor aliceDescriptor =
                TestUtil.getDescriptorFromInterviewer(ALICE_INTERVIEWER);
        EditInterviewerCommand.EditInterviewerDescriptor aliceDescriptorDuplicate =
                TestUtil.getDescriptorFromInterviewer(ALICE_INTERVIEWER);
        EditInterviewerCommand.EditInterviewerDescriptor bensonDescriptor =
                TestUtil.getDescriptorFromInterviewer(BENSON_INTERVIEWER);
        assertTrue(aliceDescriptor.equals(aliceDescriptorDuplicate));

        // same object -> returns true
        assertTrue(aliceDescriptor.equals(aliceDescriptor));

        // null -> returns false
        assertFalse(aliceDescriptor.equals(null));

        // different types -> returns false
        assertFalse(aliceDescriptor.equals(5));

        // different values -> returns false
        assertFalse(aliceDescriptor.equals(bensonDescriptor));
    }
}
