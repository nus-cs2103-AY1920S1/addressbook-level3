package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditNoteDescriptor;
import seedu.address.testutil.EditNoteDescriptorBuilder;

public class EditNoteDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditNoteDescriptor descriptorWithSameValues = new EditNoteDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different title -> returns false
        EditCommand.EditNoteDescriptor editedAmy = new EditNoteDescriptorBuilder(DESC_AMY)
                .withTitle(VALID_TITLE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different content -> returns false
        editedAmy = new EditNoteDescriptorBuilder(DESC_AMY).withContent(VALID_CONTENT_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
