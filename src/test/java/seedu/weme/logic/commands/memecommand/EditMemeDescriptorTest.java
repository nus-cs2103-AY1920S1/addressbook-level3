package seedu.weme.logic.commands.memecommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.logic.commands.CommandTestUtil.DESC_CHARMANDER;
import static seedu.weme.logic.commands.CommandTestUtil.DESC_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_DESCRIPTION_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_FILEPATH_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_JOKER;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.testutil.EditMemeDescriptorBuilder;

public class EditMemeDescriptorTest extends ApplicationTest {

    @Test
    public void equals() {
        // same values -> returns true
        MemeEditCommand.EditMemeDescriptor descriptorWithSameValues =
                new MemeEditCommand.EditMemeDescriptor(DESC_CHARMANDER);
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
        MemeEditCommand.EditMemeDescriptor editedAmy = new EditMemeDescriptorBuilder(DESC_CHARMANDER)
                .withFilePath(VALID_FILEPATH_JOKER).build();
        assertFalse(DESC_CHARMANDER.equals(editedAmy));

        // different description -> returns false
        editedAmy = new EditMemeDescriptorBuilder(DESC_CHARMANDER).withDescription(VALID_DESCRIPTION_JOKER).build();
        assertFalse(DESC_CHARMANDER.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditMemeDescriptorBuilder(DESC_CHARMANDER).withTags(VALID_TAG_JOKER).build();
        assertFalse(DESC_CHARMANDER.equals(editedAmy));
    }
}
