package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ABRA;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BUTTERFREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEANING_BUTTERFREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BUG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WORD_BUTTERFREE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.app.EditCommand;
import seedu.address.testutil.EditCardDescriptorBuilder;

public class EditCardDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditCardDescriptor descriptorWithSameValues = new EditCommand.EditCardDescriptor(DESC_ABRA);
        assertTrue(DESC_ABRA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ABRA.equals(DESC_ABRA));

        // null -> returns false
        assertFalse(DESC_ABRA.equals(null));

        // different types -> returns false
        assertFalse(DESC_ABRA.equals(5));

        // different values -> returns false
        assertFalse(DESC_ABRA.equals(DESC_BUTTERFREE));

        // different word -> returns false
        EditCommand.EditCardDescriptor editedAbra = new EditCardDescriptorBuilder(DESC_ABRA)
                .withWord(VALID_WORD_BUTTERFREE).build();
        assertFalse(DESC_ABRA.equals(editedAbra));

        // different meaning -> returns false
        editedAbra = new EditCardDescriptorBuilder(DESC_ABRA).withMeaning(VALID_MEANING_BUTTERFREE).build();
        assertFalse(DESC_ABRA.equals(editedAbra));

        // different tags -> returns false
        editedAbra = new EditCardDescriptorBuilder(DESC_ABRA).withTags(VALID_TAG_BUG).build();
        assertFalse(DESC_ABRA.equals(editedAbra));
    }
}
