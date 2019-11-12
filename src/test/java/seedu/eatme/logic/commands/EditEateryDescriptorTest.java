package seedu.eatme.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eatme.logic.commands.CommandTestUtil.DESC_KFC;
import static seedu.eatme.logic.commands.CommandTestUtil.DESC_MAC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_ADDRESS_NO_PREFIX_KFC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_CATEGORY_NO_PREFIX;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_NAME_NO_PREFIX_KFC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_TAG_NO_PREFIX_CHEAP;

import org.junit.jupiter.api.Test;

import seedu.eatme.logic.commands.EditCommand.EditEateryDescriptor;
import seedu.eatme.testutil.EditEateryDescriptorBuilder;

public class EditEateryDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditEateryDescriptor descriptorWithSameValues = new EditEateryDescriptor(DESC_MAC);
        assertTrue(DESC_MAC.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_MAC.equals(DESC_MAC));

        // null -> returns false
        assertFalse(DESC_MAC.equals(null));

        // different types -> returns false
        assertFalse(DESC_MAC.equals(5));

        // different values -> returns false
        assertFalse(DESC_MAC.equals(DESC_KFC));

        // different name -> returns false
        EditEateryDescriptor editedAmy = new EditEateryDescriptorBuilder(DESC_MAC)
                .withName(VALID_NAME_NO_PREFIX_KFC).build();
        assertFalse(DESC_MAC.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditEateryDescriptorBuilder(DESC_MAC).withAddress(VALID_ADDRESS_NO_PREFIX_KFC).build();
        assertFalse(DESC_MAC.equals(editedAmy));

        // different category -> returns false
        editedAmy = new EditEateryDescriptorBuilder(DESC_MAC).withCategory(VALID_CATEGORY_NO_PREFIX).build();
        assertFalse(DESC_MAC.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditEateryDescriptorBuilder(DESC_MAC).withTags(VALID_TAG_NO_PREFIX_CHEAP).build();
        assertFalse(DESC_MAC.equals(editedAmy));
    }
}
