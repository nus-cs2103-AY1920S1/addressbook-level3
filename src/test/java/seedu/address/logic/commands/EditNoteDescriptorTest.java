package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.NotesCommandTestUtil.DESC_DIARYONE;
import static seedu.address.logic.commands.NotesCommandTestUtil.DESC_DIARYTWO;
import static seedu.address.logic.commands.NotesCommandTestUtil.VALID_CONTENT_DIARYTWO;
import static seedu.address.logic.commands.NotesCommandTestUtil.VALID_DESCRIPTION_DIARYTWO;
import static seedu.address.logic.commands.NotesCommandTestUtil.VALID_TITLE_DIARYONE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditNoteCommand.EditNoteDescriptor;
import seedu.address.testutil.EditNoteDescriptorBuilder;

public class EditNoteDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditNoteDescriptor descriptorWithSameValues = new EditNoteDescriptor(DESC_DIARYONE);
        assertTrue(DESC_DIARYONE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_DIARYONE.equals(DESC_DIARYONE));

        // null -> returns false
        assertFalse(DESC_DIARYONE.equals(null));

        // different types -> returns false
        assertFalse(DESC_DIARYONE.equals(5));

        // different values -> returns false
        assertFalse(DESC_DIARYONE.equals(DESC_BOB));

        // different title -> returns false
        EditNoteDescriptor editedDiaryOne = new EditNoteDescriptorBuilder(DESC_DIARYONE)
                .withTitle(VALID_TITLE_DIARYONE).build();
        assertFalse((DESC_DIARYTWO).equals(editedDiaryOne));

        // different description -> returns false
        editedDiaryOne = new EditNoteDescriptorBuilder(DESC_DIARYONE).withDescription(VALID_DESCRIPTION_DIARYTWO)
                .build();
        assertFalse(DESC_DIARYONE.equals(editedDiaryOne));

        // different content -> returns false
        editedDiaryOne = new EditNoteDescriptorBuilder(DESC_DIARYONE).withContent(VALID_CONTENT_DIARYTWO).build();
        assertFalse(DESC_DIARYONE.equals(editedDiaryOne));

        // different tags -> returns false
        editedDiaryOne = new EditNoteDescriptorBuilder(DESC_DIARYONE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_DIARYONE.equals(editedDiaryOne));
    }
}
