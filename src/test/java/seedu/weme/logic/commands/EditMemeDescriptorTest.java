package seedu.weme.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.logic.commands.CommandTestUtil.DESC_CHARMANDER;
import static seedu.weme.logic.commands.CommandTestUtil.DESC_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_DESCRIPTION_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_FILEPATH_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_JOKER;

import org.junit.jupiter.api.Test;

import seedu.weme.testutil.EditMemeDescriptorBuilder;

public class EditMemeDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditMemeDescriptor descriptorWithSameValues = new EditCommand.EditMemeDescriptor(DESC_CHARMANDER);
        assertTrue(DESC_CHARMANDER.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_CHARMANDER.equals(DESC_CHARMANDER));

        // null -> returns false
        assertFalse(DESC_CHARMANDER.equals(null));

        // different types -> returns false
        assertFalse(DESC_CHARMANDER.equals(5));

        // different values -> returns false
        assertFalse(DESC_CHARMANDER.equals(DESC_JOKER));

        // different url -> returns false
        EditCommand.EditMemeDescriptor editedAmy = new EditMemeDescriptorBuilder(DESC_CHARMANDER)
                .withFilePath(VALID_FILEPATH_JOKER).build();
        assertFalse(DESC_CHARMANDER.equals(editedAmy));

        // different weme -> returns false
        editedAmy = new EditMemeDescriptorBuilder(DESC_CHARMANDER).withDescription(VALID_DESCRIPTION_JOKER).build();
        assertFalse(DESC_CHARMANDER.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditMemeDescriptorBuilder(DESC_CHARMANDER).withTags(VALID_TAG_JOKER).build();
        assertFalse(DESC_CHARMANDER.equals(editedAmy));
    }
}
