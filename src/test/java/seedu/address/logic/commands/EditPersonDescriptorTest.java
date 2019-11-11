package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PICTURE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESULT_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.util.EditPersonDescriptor;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditPersonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPersonDescriptor descriptorWithSameValues = new EditPersonDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditPersonDescriptor editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different picture -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withPicture(VALID_PICTURE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different result -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withResult(VALID_RESULT_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different class -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withClassId(VALID_CLASSID_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

    }
}
