package seedu.scheduler.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_SLOT_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_SLOT_BOB;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWEE;
import static seedu.scheduler.testutil.TypicalPersons.BENSON_INTERVIEWEE;

import org.junit.jupiter.api.Test;

public class IntervieweeSlotTest {

    @Test
    public void compareTo() {
        IntervieweeSlot intervieweeSlotAlpha = new IntervieweeSlot(ALICE_INTERVIEWEE, Slot.fromString(VALID_SLOT_AMY));
        IntervieweeSlot intervieweeSlotBravo = new IntervieweeSlot(ALICE_INTERVIEWEE, Slot.fromString(VALID_SLOT_BOB));
        IntervieweeSlot intervieweeSlotCargo = new IntervieweeSlot(BENSON_INTERVIEWEE, Slot.fromString(VALID_SLOT_AMY));
        IntervieweeSlot intervieweeSlotDelta = new IntervieweeSlot(BENSON_INTERVIEWEE, Slot.fromString(VALID_SLOT_BOB));

        // Same Interviewee but different Slot -> x < y
        assertTrue(intervieweeSlotAlpha.compareTo(intervieweeSlotBravo) < 0);

        // Different Interviewee but same Slot -> x == y
        assertTrue(intervieweeSlotAlpha.compareTo(intervieweeSlotCargo) == 0);

        // Same Interviewee and same Slot -> x == y
        assertTrue(intervieweeSlotAlpha.compareTo(intervieweeSlotAlpha) == 0);

        // Different Interviewee and different Slot -> x < y
        assertTrue(intervieweeSlotAlpha.compareTo(intervieweeSlotDelta) < 0);
    }

    @Test
    public void toString_validInputs_success() {
        String expected = ALICE_INTERVIEWEE.toString() + ", " + VALID_SLOT_AMY;
        IntervieweeSlot intervieweeSlot = new IntervieweeSlot(ALICE_INTERVIEWEE, Slot.fromString(VALID_SLOT_AMY));
        assertEquals(expected, intervieweeSlot.toString());
    }

}
