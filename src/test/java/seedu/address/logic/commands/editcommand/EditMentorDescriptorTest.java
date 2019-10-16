package seedu.address.logic.commands.editcommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.MENTOR_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MENTOR_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORGANIZATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.editcommand.EditMentorCommand.EditMentorDescriptor;
import seedu.address.testutil.EditMentorDescriptorBuilder;

public class EditMentorDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditMentorDescriptor descriptorWithSameValues = new EditMentorDescriptor(MENTOR_DESC_AMY);
        assertTrue(MENTOR_DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(MENTOR_DESC_AMY.equals(MENTOR_DESC_AMY));

        // null -> returns false
        assertFalse(MENTOR_DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(MENTOR_DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(MENTOR_DESC_AMY.equals(MENTOR_DESC_BOB));

        // different name -> returns false
        EditMentorDescriptor editedAmy = new EditMentorDescriptorBuilder(MENTOR_DESC_AMY)
                .withName(VALID_NAME_BOB).build();
        assertFalse(MENTOR_DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditMentorDescriptorBuilder(MENTOR_DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(MENTOR_DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditMentorDescriptorBuilder(MENTOR_DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(MENTOR_DESC_AMY.equals(editedAmy));

        // different organization -> returns false
        editedAmy = new EditMentorDescriptorBuilder(MENTOR_DESC_AMY).withOrganization(VALID_ORGANIZATION_BOB).build();
        assertFalse(MENTOR_DESC_AMY.equals(editedAmy));

        // different subject name -> returns false
        editedAmy = new EditMentorDescriptorBuilder(MENTOR_DESC_AMY).withSubject(VALID_SUBJECT_BOB).build();
        assertFalse(MENTOR_DESC_AMY.equals(editedAmy));
    }

}
