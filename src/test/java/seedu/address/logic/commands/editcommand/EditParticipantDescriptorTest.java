package seedu.address.logic.commands.editcommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.PARTICIPANT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PARTICIPANT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.editcommand.EditParticipantCommand.EditParticipantDescriptor;
import seedu.address.testutil.EditParticipantDescriptorBuilder;

public class EditParticipantDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditParticipantDescriptor descriptorWithSameValues = new EditParticipantDescriptor(PARTICIPANT_DESC_AMY);
        assertTrue(PARTICIPANT_DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(PARTICIPANT_DESC_AMY.equals(PARTICIPANT_DESC_AMY));

        // null -> returns false
        assertFalse(PARTICIPANT_DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(PARTICIPANT_DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(PARTICIPANT_DESC_AMY.equals(PARTICIPANT_DESC_BOB));

        // different name -> returns false
        EditParticipantDescriptor editedAmy = new EditParticipantDescriptorBuilder(PARTICIPANT_DESC_AMY)
                .withName(VALID_NAME_BOB).build();
        assertFalse(PARTICIPANT_DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditParticipantDescriptorBuilder(PARTICIPANT_DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(PARTICIPANT_DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditParticipantDescriptorBuilder(PARTICIPANT_DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(PARTICIPANT_DESC_AMY.equals(editedAmy));
    }

}
