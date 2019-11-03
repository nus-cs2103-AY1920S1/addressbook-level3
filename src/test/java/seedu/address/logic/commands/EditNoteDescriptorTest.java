package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.note.EditNoteCommand;
import seedu.address.logic.commands.note.EditNoteCommand.EditNoteDescriptor;
import seedu.address.testutil.EditNoteDescriptorBuilder;

class EditNoteDescriptorTest {
    @Test
    void equals() {
        // same values -> returns true
        EditNoteDescriptor descriptorWithSameValues = new EditNoteDescriptor(DESC_AMY);
        assertEquals(DESC_AMY, descriptorWithSameValues);

        // same object -> returns true
        assertEquals(DESC_AMY, DESC_AMY);

        // null -> returns false
        assertNotEquals(null, DESC_AMY);

        // different types -> returns false
        assertNotEquals(5, DESC_AMY);

        // different values -> returns false
        assertNotEquals(DESC_AMY, DESC_BOB);

        // different title -> returns false
        EditNoteCommand.EditNoteDescriptor editedAmy = new EditNoteDescriptorBuilder(DESC_AMY)
                .withTitle(VALID_TITLE_BOB).build();
        assertNotEquals(DESC_AMY, editedAmy);

        // different content -> returns false
        editedAmy = new EditNoteDescriptorBuilder(DESC_AMY).withContent(VALID_CONTENT_BOB).build();
        assertNotEquals(DESC_AMY, editedAmy);
    }
}
